package com.lottery.controller;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lottery.annotation.Token;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.LotteryAwardRecord;
import com.lottery.bean.entity.LotteryPlayBonus;
import com.lottery.bean.entity.LotteryPlayModel;
import com.lottery.bean.entity.LotteryType;
import com.lottery.bean.entity.PlayModel;
import com.lottery.bean.entity.TaskConfig;
import com.lottery.bean.entity.vo.BetRecordListVO;
import com.lottery.bean.entity.vo.BetRecordVO;
import com.lottery.bean.entity.vo.LotteryAwardRecordVO;
import com.lottery.bean.entity.vo.LotteryPlayModelListVO;
import com.lottery.bean.entity.vo.LotteryPlayModelVO;
import com.lottery.bean.entity.vo.LotteryPlaySelectVO;
import com.lottery.bean.entity.vo.LotteryTypeVO;
import com.lottery.bean.entity.vo.TaskConfigVO;
import com.lottery.bean.entity.vo.TempMapVO;
import com.lottery.filter.BetFilter;
import com.lottery.filter.WebAppStaticCollection;
import com.lottery.service.IBetRecordService;
import com.lottery.service.ILotteryAwardRecordService;
import com.lottery.service.ILotteryPlayBonusService;
import com.lottery.service.ILotteryPlayModelService;
import com.lottery.service.ILotteryPlaySelectService;
import com.lottery.service.ILotteryTypeService;
import com.lottery.service.IPlayModelService;
import com.lottery.service.ITaskConfigService;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;
import com.xl.lottery.util.JsonUtil;


@Controller
public class BetController extends BaseController{
	
	@Autowired
	private ILotteryPlaySelectService selectService;
	
	@Autowired
	private ILotteryPlayModelService lotteryModelService;
	
	@Autowired
	private IPlayModelService  modelService;
	
	@Autowired
	private ITaskConfigService taskService;
	
	@Autowired
	private ILotteryPlayBonusService lpbService;
	
	@Autowired
	private IBetRecordService betRecordService;
	
	@Autowired
	private ILotteryAwardRecordService recordService;
	
	@Autowired
	private BetFilter betFilter;
	
	@Autowired
	private ILotteryTypeService lotteryService;
	
	@Autowired
	private IPlayModelService playService;
	
	@Autowired
	private ITaskConfigService taskConfigService;
	
	/**
	 * 11选5页面玩法初始化
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/show11x5")
	@Token(needSaveToken=true)
	public ModelAndView show11x5(LotteryTypeVO lotteryVo,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> param = new HashMap<String,Object>();
		Map<String,Object> model = new HashMap<String,Object>();
		if(StringUtils.isEmpty(lotteryVo.getLotteryCode())){
			lotteryVo.setLotteryCode(DataDictionaryUtil.SYXW_GD_LOTTERY_CODE);
		}
		lotteryVo.setLotteryLevel(DataDictionaryUtil.COMMON_FLAG_1);
		lotteryVo.setLotteryStatus(DataDictionaryUtil.STATUS_OPEN);
		
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		
		param.put("lotteryKey", lotteryVo);
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		List<LotteryPlayModel> lotteryModelList = null;
		TaskConfig task = null;
		Map<String,Object> bonusMap = null;
//		Map<String,Map<String,Integer>> hmMap = null;
		try {
			lotteryModelList = lotteryModelService.queryModelByLottery(param);
			model.put("lottery", lotteryModelList.get(0).getLottery());
			//查询当前奖期任务
			task = taskService.queryCurrentTask(param);
			if(task!=null){
				setBetEndTime(model,task);
				//查询历史开奖记录
				queryAwardRecords(param,model,task,lotteryVo);
			}
			//查询用户返点值及奖金组名称
			LotteryPlayModelVO lpmVo = new LotteryPlayModelVO();
			lpmVo.setLotteryCode(lotteryVo.getLotteryCode());
			lpmVo.setUserName(user.getCustomerName());
			param.put("lpmKey", lpmVo);
			bonusMap= lpbService.queryUserModelNoBonus(param);
			
			//查询彩种玩法对应设置的返点率
			LotteryPlayBonus lpbonus = lotteryModelList.get(0).getBonus();
			BigDecimal pxRb = (BigDecimal) bonusMap.get("pxRebates");
			//如果返点概率不一致，则用玩法配置的返点最高值减去点差
			if(lpbonus.getRebates().compareTo(pxRb)!=0){
				BigDecimal stepRb = (BigDecimal) bonusMap.get("stepRebates");
				bonusMap.remove("rebates");
				if(lpbonus.getRebates().subtract(stepRb).compareTo(BigDecimal.ZERO)==-1){
					bonusMap.put("rebates", BigDecimal.ZERO);
				}else{
					bonusMap.put("rebates", lpbonus.getRebates().subtract(stepRb));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
			return new ModelAndView("lottery/11x5",model);
		}
		//根据玩法组名去除重复的玩法组名，根据页面div结构构造list。
		Map<String,List<LotteryPlayModel>> map = new HashMap<String, List<LotteryPlayModel>>();
		for(LotteryPlayModel lpm : lotteryModelList){
			String key =lpm.getPlayModel().getGroupName();
			if(null==map.get(key)){
				List<LotteryPlayModel> tempList = new ArrayList<LotteryPlayModel>();
				tempList.add(lpm);
				map.put(lpm.getPlayModel().getGroupName(), tempList);
			}else{
				List<LotteryPlayModel> tempList = map.get(key);
				tempList.add(lpm);
			}
		}
		List<LotteryPlayModelListVO> playList = new ArrayList<LotteryPlayModelListVO>();
		for(String key : map.keySet()){
			LotteryPlayModelListVO vo = new LotteryPlayModelListVO();
			vo.setGroupName(key);
			
			//玩法组下的玩法，根据modelCode排序
			List<LotteryPlayModel> tempList = map.get(key);
			Compare2 cp2 = new Compare2();
			Collections.sort(tempList, cp2);
			
			vo.setLpmList(tempList);
			vo.setModelCode(map.get(key).get(0).getModelCode());
			//设置不同玩法组对应的选择球的球数
			vo.setBalCount(map.get(key).get(0).getPlayModel().getBalCount());
			vo.setSortIndex(map.get(key).get(0).getPlayModel().getSortIndex());
			playList.add(vo);
		}
		//玩法组，根据modelCode排序
		Compare cp = new Compare();
		Collections.sort(playList, cp);
		model.put("playList", playList);
		model.putAll(bonusMap);
		return new ModelAndView("lottery/11x5",model);
	}
	
	
	/**
	 * 时时彩页面玩法初始化
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/showssc")
	@Token(needSaveToken=true)
	public ModelAndView showSsc(LotteryTypeVO lotteryVo,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		if(StringUtils.isEmpty(lotteryVo.getLotteryCode())){
			lotteryVo.setLotteryCode(DataDictionaryUtil.SSC_CQ_LOTTERY_CODE);
		}
		model.put("lotteryCode", lotteryVo.getLotteryCode());
		
		TaskConfig task = null;
		try {
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(DataDictionaryUtil.STATUS_DELETE);
			statusList.add(DataDictionaryUtil.STATUS_OPEN);
			lotteryVo.setStatusList(statusList);
			param.put("lotteryKey", lotteryVo);
			List<LotteryType> lotterys = lotteryService.queryLotteryList(param);
			model.put("lotteryName", lotterys.get(0).getLotteryName());
			model.put("lotteryGroup", lotterys.get(0).getLotteryGroup());
			int totalTimes=0;
			String totalMunites ="";
			for(LotteryType t : lotterys){
				totalTimes += t.getTotalTimes();
				if(totalMunites==""){
					totalMunites = String.valueOf(t.getSpanTime()/60);
				}else{
					totalMunites += "或"+String.valueOf(t.getSpanTime()/60);
				}
			}
			model.put("totalMunites", totalMunites);
			model.put("totalTimes", totalTimes);
			model.put("startTime", lotterys.get(0).getStartTime());
			model.put("endTime", lotterys.get(lotterys.size()-1).getEndTime());
			model.put("lotteryName", lotterys.get(0).getLotteryName());
			//查询当前奖期任务
			task = taskService.queryCurrentTask(param);
			if(task!=null){
				setBetEndTime(model,task);
				//查询历史开奖记录
				queryAwardRecords(param,model,task,lotteryVo);
			}
		}catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("lottery/ssc",model);
	}
	
	@RequestMapping("/initSsc")
	@ResponseBody
	public Map<String,Object> initSsc(LotteryTypeVO lotteryVo,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> param = new HashMap<String,Object>();
		Map<String,Object> model = new HashMap<String,Object>();
		if(StringUtils.isEmpty(lotteryVo.getLotteryCode())){
			lotteryVo.setLotteryCode(DataDictionaryUtil.SSC_CQ_LOTTERY_CODE);
		}
		lotteryVo.setLotteryLevel(DataDictionaryUtil.COMMON_FLAG_1);

		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(DataDictionaryUtil.STATUS_DELETE);
		statusList.add(DataDictionaryUtil.STATUS_OPEN);
		lotteryVo.setStatusList(statusList);
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		
		param.put("lotteryKey", lotteryVo);
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		List<LotteryPlayModel> lotteryModelList = null;
		Map<String,Object> bonusMap = null;
		try {
			lotteryModelList = lotteryModelService.queryModelByLottery(param);
			model.put("lottery", lotteryModelList.get(0).getLottery());
			
			//查询用户返点值及奖金组名称
			LotteryPlayModelVO lpmVo = new LotteryPlayModelVO();
			lpmVo.setLotteryCode(lotteryVo.getLotteryCode());
			lpmVo.setUserName(user.getCustomerName());
			param.put("lpmKey", lpmVo);
			bonusMap= lpbService.queryUserModelNoBonus(param);
			
			//查询彩种玩法对应设置的返点率
			LotteryPlayBonus lpbonus = lotteryModelList.get(0).getBonus();
			BigDecimal pxRb = (BigDecimal) bonusMap.get("pxRebates");
			//如果返点概率不一致，则用玩法配置的返点最高值减去点差
			if(lpbonus.getRebates().compareTo(pxRb)!=0){
				BigDecimal stepRb = (BigDecimal) bonusMap.get("stepRebates");
				bonusMap.remove("rebates");
				if(lpbonus.getRebates().subtract(stepRb).compareTo(BigDecimal.ZERO)==-1){
					bonusMap.put("rebates", BigDecimal.ZERO);
				}else{
					bonusMap.put("rebates", lpbonus.getRebates().subtract(stepRb));
				}
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		//根据玩法组名去除重复的玩法组名，根据页面div结构构造list。
		long time1 = System.currentTimeMillis();
		Map<String,List<LotteryPlayModel>> map = new HashMap<String, List<LotteryPlayModel>>();
		for(LotteryPlayModel lpm : lotteryModelList){
			String key =lpm.getPlayModel().getGroupName();
			if(null==map.get(key)){
				List<LotteryPlayModel> tempList = new ArrayList<LotteryPlayModel>();
				tempList.add(lpm);
				map.put(lpm.getPlayModel().getGroupName(), tempList);
			}else{
				List<LotteryPlayModel> tempList = map.get(key);
				tempList.add(lpm);
			}
		}
		List<LotteryPlayModelListVO> playList = new ArrayList<LotteryPlayModelListVO>();
		for(String key : map.keySet()){
			LotteryPlayModelListVO vo = new LotteryPlayModelListVO();
			vo.setGroupName(key);
			
			//玩法组下的玩法，根据modelCode排序
			List<LotteryPlayModel> tempList = map.get(key);
			Compare2 cp2 = new Compare2();
			Collections.sort(tempList, cp2);
			
			vo.setLpmList(tempList);
			vo.setModelCode(map.get(key).get(0).getModelCode());
			//设置不同玩法组对应的选择球的球数
			vo.setBalCount(map.get(key).get(0).getPlayModel().getBalCount());
			vo.setSortIndex(map.get(key).get(0).getPlayModel().getSortIndex());
			playList.add(vo);
		}
		//玩法组，根据modelCode排序
		Compare cp = new Compare();
		Collections.sort(playList, cp);
		model.put("playList", playList);
		model.putAll(bonusMap);
		return model;
	}

	/**
	 * 内部list排序类，根据modelCode排序。
	 * @author CW-HP9
	 *
	 */
	class Compare implements Comparator<LotteryPlayModelListVO>{
			@Override
			public int compare(LotteryPlayModelListVO o1, LotteryPlayModelListVO o2) {
				int code1 = o1.getSortIndex();
				int code2 = o2.getSortIndex();
				if(code1>code2){
					return 1;
				}else if(code1<code2){
					return -1;
				}
				return 0;
			}
	}
	/**
	 * 内部list排序类，根据modelCode排序。
	 * @author CW-HP9
	 *
	 */
	class Compare2 implements Comparator<LotteryPlayModel>{
			@Override
			public int compare(LotteryPlayModel o1, LotteryPlayModel o2) {
				int code1 = Integer.parseInt(o1.getModelCode());
				int code2 = Integer.parseInt(o2.getModelCode());
				if(code1>code2){
					return 1;
				}else if(code1<code2){
					return -1;
				}
				return 0;
			}
	}
	
	/**
	 * 开奖号码走势图 内部list排序类，根据issueNo排序 asc。
	 * @author CW-HP9
	 *
	 */
	class Compare5 implements Comparator<LotteryAwardRecord>{
			@Override
			public int compare(LotteryAwardRecord o1, LotteryAwardRecord o2) {
				int code1 = Integer.parseInt(o1.getIssue());
				int code2 = Integer.parseInt(o2.getIssue());
				if(code1>code2){
					return 1;
				}else if(code1<code2){
					return -1;
				}
				return 0;
			}
	}
	
	
	/**
	 * 重新获取奖期信息，重新计算截至投注倒计时。
	 * @param lotteryVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/newCurrTask")
	@ResponseBody
	public Map<String,?> newCurrTask(LotteryTypeVO lotteryVo,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("lotteryKey", lotteryVo);
		TaskConfig task = null;
		try {
			task = taskService.queryCurrentTask(param);
			if(task!=null){
				setBetEndTime(model,task);
				/*先查询map看看最新的开奖记录是否有更新，有的话才查询数据库，没有的话最近的开奖记录信息页面无须刷新。
				TempMapVO mapvo = WebAppStaticCollection.getOpenNumMap().get(task.getLotteryGroup()+"-"+task.getLotteryCode());
				if(null!=mapvo){
					int issueNo = Integer.parseInt(mapvo.getValue());
					int lastIssueNo = Integer.parseInt(lotteryVo.getLastOpenIssue());
					if(lastIssueNo>=issueNo){
						model.put("refresh", "false");
						return model;
					}
				}
				*/
				//查询历史开奖记录
				queryAwardRecords(param,model,task,lotteryVo);
				model.put("refresh", "true");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
			return model;
		}
		return model;
	}
	
	/**
	 * 查询历史开奖记录
	 * @param param
	 * @param model
	 * @param task
	 * @param lotteryVo
	 * @throws Exception
	 */
	private void queryAwardRecords(Map<String,Object> param,Map<String,Object> model,
			TaskConfig task,LotteryTypeVO lotteryVo) throws Exception{
		//查询历史开奖记录
		List<LotteryAwardRecord> records = recordService.queryRecentAwardRecord(param);
		if(records.size()>0){
			int curIssue = Integer.parseInt(task.getLotterySeries());
			int issue = Integer.parseInt(records.get(0).getIssue());
			if(curIssue>issue&&curIssue-issue!=1){
				//未开始开奖的，且非当前销售的奖期，即等待开奖的奖期，需虚拟显示出来。
				LotteryAwardRecord tempRecord = new LotteryAwardRecord();
				tempRecord.setStatus(DataDictionaryUtil.STATUS_READY);
				tempRecord.setLotteryCode(records.get(0).getLotteryCode());
				LocalTime time = LocalTime.parse(task.getStartBetTime());
				time= time.plusSeconds(-lotteryVo.getSpanTime());
				tempRecord.setStartTime(time.toString("HH:mm:ss"));
				tempRecord.setEndTime(time.toString("HH:mm:ss"));
				tempRecord.setIssue(String.valueOf(curIssue-1));
				param.put("issueNo", tempRecord.getIssue());
				TaskConfig issueTask = taskService.queryTaskByCodeAndIssue(param);
				if(issueTask!=null){
					records.add(0,tempRecord);
				}
			}
			if(records.size()>9){
				model.put("records", records.subList(0, 9));
			}else{
				model.put("records", records);
			}
			for(LotteryAwardRecord record : records){
				if(record.getStatus()==DataDictionaryUtil.STATUS_SUCCESS
						||record.getStatus()==DataDictionaryUtil.HAND_STATUS_SUCCESS){
					//设置最近的开奖记录期号，方便前台判断是否需要刷新开奖记录的轮询。
					model.put("lastAwardNo", record.getIssue());
					break;
				}
			}
			
		}
	}
	
	/**
	 * 查询计算截止投注时间
	 * @param model
	 * @param task
	 */
	private void setBetEndTime(Map<String,Object> model,TaskConfig task){
		Date now = new Date();
		int hour = now.getHours();
		int minutes = now.getMinutes();
		int seconds = now.getSeconds();
		String endBetTime = task.getEndBetTime();
		int taskHour = Integer.parseInt(endBetTime.substring(0,2));
		int taskMinute = Integer.parseInt(endBetTime.substring(3,5));
		int  taskSeconds = Integer.parseInt(endBetTime.substring(6,8));
		
		int betHour = taskHour - hour;
		int betMinutes = taskMinute - minutes ;
		int betSeconds = taskSeconds-seconds;
		
		if(betSeconds<0){
			betSeconds = (taskSeconds-seconds)+60;
			betMinutes = betMinutes -1;
		}
		if(betMinutes<0){
			betMinutes = 60+betMinutes;
			betHour = betHour -1;
		}
		if(betHour<0){
			betHour = 24+betHour;
		}
		
		if(betHour<10){
			model.put("betHour", "0"+betHour);
		}else{
			model.put("betHour", betHour);
		}
		
		if(betMinutes<10){
			model.put("betMinutes", "0"+betMinutes);
		}else{
			model.put("betMinutes", betMinutes);
		}
		
		if(betSeconds<10){
			model.put("betSeconds", "0"+betSeconds);
		}else{
			model.put("betSeconds", betSeconds);
		}
		model.put("task", task);
	}
	/**
	 * 获取当天的追期表
	 * @param lotteryVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getTodayTask")
	@ResponseBody
	public Map<String,?> getTodayTask(TaskConfigVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		vo.setStartBetTime(DateUtil.getStringDate());
		param.put("taskKey", vo);
		List<TaskConfig> tasks = null;
		try {
			tasks = taskService.queryTaskByCode(param);
			if(tasks!=null&&tasks.size()>301){
				tasks = tasks.subList(0, 301);
			}
			model.put("tasks", tasks);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
			return model;
		}
		return model;
	}
	
	
	@RequestMapping("gd11x5")
	@Token(needRemoveToken=true)
	public ModelAndView gdBet11x5(BetRecordListVO vo,HttpServletRequest request,HttpServletResponse response,RedirectAttributes reAttributes){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			//betFilter.betTimeLimit(vo);
			CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
			betFilter.customerLevelLimit(user);
			betFilter.betNumberLimit(vo,vo.getIssueNos());
			param.put("volistkey", vo);
			param.put(CommonUtil.CUSTOMERUSERKEY,user);
			List<BetRecordVO> volist = vo.getVolist();
			if(volist.size()==0)throw new LotteryException("投注单不能为空");
			if(!checkBetRecordList(vo))throw new LotteryException("投注单数据异常");
			betRecordService.saveBetRecordOrder(param);
			reAttributes.addFlashAttribute("success", "投注成功");
		} catch (Exception e) {
			// TODO: handle exception
			LotteryExceptionLog.wirteLog(e, reAttributes);
		}
		return new ModelAndView("redirect:show11x5.html",model);
	}
	
	@RequestMapping(value="bet11x5",method=RequestMethod.POST)
	@Token(needRemoveToken=true)
	@ResponseBody
	public Map<String,Object> bet11x5(BetRecordListVO vo,HttpServletRequest request,HttpServletResponse response,RedirectAttributes reAttributes){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		if(null!=request.getAttribute("tokenError")){
			model.put("errorMsg", request.getAttribute("tokenError"));
			return model;
		}
		try {
			//betFilter.checkBlackList(vo);
			LotteryTypeVO typevo = new LotteryTypeVO();
			List<BetRecordVO> brs = vo.getVolist();
			typevo.setLotteryCode(brs.get(0).getLotteryCode());
			param.put("lotteryKey", typevo);
			TaskConfig taskConfig = taskConfigService.queryCurrentTask(param);
			//奖期检查
			betFilter.checkTaskStatus(taskConfig);
			
			betFilter.betTimeLimit(vo.getIssueNos(),taskConfig);
			CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
			param.put("volistkey", vo);

			//未充值用户，每笔订单最高2元投注限制
			betFilter.noRechargeLimit(vo, user);
			
			betFilter.customerLevelLimit(user);
			betFilter.betNumberLimit(vo,vo.getIssueNos());
			param.put(CommonUtil.CUSTOMERUSERKEY,user);
			List<BetRecordVO> volist = vo.getVolist();
			if(volist.size()==0)throw new LotteryException("投注单不能为空");
			if(!checkBetRecordList(vo))throw new LotteryException("投注单数据异常");
			betRecordService.saveBetRecordOrder(param);
			model.put("success", "投注成功");
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}finally{
			model.put("token", request.getSession().getAttribute("token"));
		}
		return model;
	}
	
	@RequestMapping(value="betSSC",method=RequestMethod.POST)
	@Token(needRemoveToken=true)
	@ResponseBody
	public Map<String,Object> betSSC(BetRecordListVO vo,HttpServletRequest request,HttpServletResponse response,RedirectAttributes reAttributes){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		if(null!=request.getAttribute("tokenError")){
			model.put("errorMsg", request.getAttribute("tokenError"));
			return model;
		}
		try {
			//betFilter.checkBlackList(vo);
			LotteryTypeVO typevo = new LotteryTypeVO();
			List<BetRecordVO> brs = vo.getVolist();
			typevo.setLotteryCode(brs.get(0).getLotteryCode());
			param.put("lotteryKey", typevo);
			TaskConfig taskConfig = taskConfigService.queryCurrentTask(param);
			//奖期检查
			betFilter.checkTaskStatus(taskConfig);
			
			betFilter.betTimeLimit(vo.getIssueNos(),taskConfig);
			CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
			param.put("volistkey", vo);
			
			//未充值用户，每笔订单最高2元投注限制
			betFilter.noRechargeLimit(vo, user);
			
			betFilter.customerLevelLimit(user);
			betFilter.sscBetNumberLimit(vo,vo.getIssueNos());
			param.put(CommonUtil.CUSTOMERUSERKEY,user);
			List<BetRecordVO> volist = vo.getVolist();
			if(volist.size()==0)throw new LotteryException("投注单不能为空");
			if(!checkBetRecordList(vo))throw new LotteryException("投注单数据异常");
			betRecordService.saveBetRecordOrder(param);
			model.put("success", "投注成功");
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}finally{
			model.put("token", request.getSession().getAttribute("token"));
		}
		return model;
	}
	
	private boolean checkBetRecordList(BetRecordListVO vo){
		List<BetRecordVO> volist = vo.getVolist();
		BigDecimal betMonery = BigDecimal.ZERO;
		double count = 0;
		for(BetRecordVO betRecord : volist){
			betMonery = betMonery.add(betRecord.getBetMoney().multiply(new BigDecimal(betRecord.getMultiple())));
			count+=betRecord.getMultiple()*betRecord.getBetModel().doubleValue()*betRecord.getBetCount();
		}
		if(vo.getOrderAmount().doubleValue() == betMonery.doubleValue())return true;
		if(count < vo.getOrderAmount().doubleValue()||count>vo.getOrderAmount().doubleValue())return true;
		if(vo.getOrderAmount().doubleValue() == 0)return true;
		return false;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unused" })
	private Object invoke(String className,String methodName,Object[] args) throws Exception{
		Class clz = Class.forName(className);
		Method[] methods = clz.getMethods();
		Object obj = null;
		for(Method method : methods){
			if(method.getName().equals(methodName)){
				obj = method.invoke(clz, args);
				break;
			}
		}
		return obj;
	}
	
	@RequestMapping("getLimits")
	@ResponseBody
	public Map<String, Object> getLimitNumbers(String key,HttpServletRequest request,HttpServletResponse response,RedirectAttributes reAttributes){
		Set<String> numbers = WebAppStaticCollection.getLimitNumberMap().get(key);
		Map<String, Object> model = new HashMap<>();
		model.put("limit", numbers);
		return model;
	}
	
	@RequestMapping("cancelBR")
	@ResponseBody
	public Map<String,Object> cancelBetRecord(BetRecordVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("brvo", vo);
		param.put(CommonUtil.CUSTOMERUSERKEY, request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY));
		try {
			betRecordService.cancelBetRecord(param);
			model.put("betId", vo.getId());
			model.put("success", "撤单成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	/**
	 * 首页热门彩种获取信息
	 * @param lotteryVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/hotLotteryTask")
	@ResponseBody
	public Map<String,?> hotLotteryTask(LotteryTypeVO lotteryVo,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("lotteryKey", lotteryVo);
		
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		try {
			//查询彩种信息
			lotteryVo.setLotteryLevel(DataDictionaryUtil.COMMON_FLAG_1);
			lotteryVo.setLotteryStatus(DataDictionaryUtil.STATUS_OPEN);
			param.put("lotteryKey", lotteryVo);
			LotteryType lottery = lotteryService.queryLotteryList(param).get(0);
			param.put("lotteryCodeKey", lottery.getLotteryCode());
			List<PlayModel> plays = playService.queryPlayModelByLotteryCode(param);
			//查询彩种当前期
			TaskConfig task = taskService.queryCurrentTask(param);
			//查询上期开奖结果
			List<LotteryAwardRecord> recorders = recordService.queryRecentAwardRecord(param);
			LotteryAwardRecord record = recorders.size()==0?new LotteryAwardRecord():recorders.get(0);
			//查询用户返点值及奖金组名称
			LotteryPlayModelVO lpmVo = new LotteryPlayModelVO();
			lpmVo.setLotteryCode(lotteryVo.getLotteryCode());
			lpmVo.setUserName(user.getCustomerName());
			param.put("lpmKey", lpmVo);
			Map<String,Object> bonusMap = lpbService.queryUserModelNoBonus(param);
			
			//查询彩种玩法对应设置的返点率
			LotteryPlayBonus lpbonus = lpbService.queryBonusByCode(param);
			BigDecimal pxRb = (BigDecimal) bonusMap.get("pxRebates");
			//如果返点概率不一致，则用玩法配置的返点最高值减去点差
			if(lpbonus.getRebates().compareTo(pxRb)!=0){
				BigDecimal stepRb = (BigDecimal) bonusMap.get("stepRebates");
				bonusMap.remove("rebates");
				bonusMap.put("rebates", lpbonus.getRebates().subtract(stepRb));
			}
			model.put("lottery", lottery);
			model.put("task", task);
			model.put("record", record);
			model.put("bonusMap", bonusMap);
			model.put("plays", plays);
			
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
			return model;
		}
		return model;
	}
	
	
	/**
	 * 冷热号码统计查询
	 * @param lotteryVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/hotMissNumCount")
	@ResponseBody
	public Map<String,?> hotMissNumCount(LotteryPlaySelectVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("lotSelectKey", vo);
		
		Map<String,Map<String,Integer>> hmMap = null;
		try {
			//查询冷热、遗漏号码
			hmMap = recordService.queryHotMissingRecord(param);
			List<TempMapVO> hList = new ArrayList<TempMapVO>();
			Map<String,Integer> hMap = hmMap.get("hMap")==null?new HashMap<String, Integer>():hmMap.get("hMap");
			for(String key : hMap.keySet()){
				TempMapVO temp = new TempMapVO();
				temp.setKey(key);
				temp.setValue(hMap.get(key).toString());
				hList.add(temp);
			}
			
			List<TempMapVO> mList = new ArrayList<TempMapVO>();
			Map<String,Integer> mMap = hmMap.get("mMap")==null?new HashMap<String, Integer>():hmMap.get("mMap");
			for(String key : mMap.keySet()){
				TempMapVO temp = new TempMapVO();
				temp.setKey(key);
				temp.setValue(mMap.get(key).toString());
				mList.add(temp);
			}
			
			model.put("hList",hList);
			model.put("mList",mList);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
			return model;
		}
		return model;
	}
	
	/**
	 * 十一选五号码基本走势
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/initNoTrend")
	public ModelAndView initNoTrend(LotteryTypeVO lotteryVo,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("lotteryCode", lotteryVo.getLotteryCode());
		return new ModelAndView("lottery/no11x5Trend",model);
	}
	
	/**
	 * 十一选五号码综合走势
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/initZhNoTrend")
	public ModelAndView initZhNoTrend(LotteryTypeVO lotteryVo,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("lotteryCode", lotteryVo.getLotteryCode());
		return new ModelAndView("lottery/no11x5zhTrend",model);
	}
	/**
	 * 时时彩号码基本走势
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/initSSCNoTrend")
	public ModelAndView initSSCNoTrend(LotteryTypeVO lotteryVo,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("lotteryCode", lotteryVo.getLotteryCode());
		return new ModelAndView("lottery/noSscTrend",model);
	}
	/**
	 * 时时彩号码5星综合走势
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/initSSCzh5NoTrend")
	public ModelAndView initSSCzh5NoTrend(LotteryTypeVO lotteryVo,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("lotteryCode", lotteryVo.getLotteryCode());
		return new ModelAndView("lottery/noSsczh5Trend",model);
	}
	
	/**
	 * 时时彩号码3星基本走势
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/initSSC3NoTrend")
	public ModelAndView initSSC3NoTrend(LotteryTypeVO lotteryVo,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("lotteryCode", lotteryVo.getLotteryCode());
		return new ModelAndView("lottery/noSsc3Trend",model);
	}
	
	/**
	 * 时时彩号码中3星基本走势
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/initSSC3NoTrend_z")
	public ModelAndView initSSC3NoTrend_z(LotteryTypeVO lotteryVo,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("lotteryCode", lotteryVo.getLotteryCode());
		return new ModelAndView("lottery/noSsc3Trend_z",model);
	}
	
	/**
	 * 时时彩号码前3星基本走势
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/initSSC3NoTrend_q")
	public ModelAndView initSSC3NoTrend_q(LotteryTypeVO lotteryVo,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("lotteryCode", lotteryVo.getLotteryCode());
		return new ModelAndView("lottery/noSsc3Trend_q",model);
	}
	/**
	 * 时时彩号码2星基本走势
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/initSSC2NoTrend")
	public ModelAndView initSSC2NoTrend(LotteryTypeVO lotteryVo,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("lotteryCode", lotteryVo.getLotteryCode());
		return new ModelAndView("lottery/noSsc2Trend",model);
	}
	
	/**
	 * 时时彩号码前2星基本走势
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/initSSC2NoTrend_q")
	public ModelAndView initSSC2NoTrend_q(LotteryTypeVO lotteryVo,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("lotteryCode", lotteryVo.getLotteryCode());
		return new ModelAndView("lottery/noSsc2Trend_q",model);
	}
	
	/**
	 * 冷热号码统计查询 走势图
	 * @param lotteryVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryRecords")
	@ResponseBody
	public Map<String,?> queryRecords(LotteryTypeVO lotteryVo,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		String today = DateUtil.getStringDateShort().concat(" 00:00:00");
		if(lotteryVo.getTotalTimes()==1){
			lotteryVo.setTotalTimes(0);
			lotteryVo.setStartTime(today);
			lotteryVo.setEndTime(DateUtil.getStringDate());
		}else if(lotteryVo.getTotalTimes()==2){
			lotteryVo.setTotalTimes(0);
			lotteryVo.setStartTime(DateUtil.getNextDay(today,"-1").concat(" 00:00:00"));
			lotteryVo.setEndTime(today);
		}else if(lotteryVo.getTotalTimes()==3){
			lotteryVo.setTotalTimes(0);
			lotteryVo.setStartTime(DateUtil.getNextDay(today, "-2").concat(" 00:00:00"));
			lotteryVo.setEndTime(DateUtil.getNextDay(today, "-1").concat(" 00:00:00"));
		}
		
		param.put("lotteryKey", lotteryVo);
		List<LotteryAwardRecordVO> records = new ArrayList<LotteryAwardRecordVO>(50);
		try {
			List<LotteryAwardRecord> awardRecords = recordService.queryLimitAwardRecord2(param);
			Compare5 c5 = new Compare5();
			//查询完成后倒为正序
			Collections.sort(awardRecords, c5);
			for(LotteryAwardRecord record : awardRecords){
				LotteryAwardRecordVO vo = new LotteryAwardRecordVO();
				vo.setLotteryNumber(record.getLotteryNumber());
				vo.setIssue(record.getIssue());
				vo.setOpenTime(record.getEndTime());
				records.add(vo);
			}
			if(awardRecords.size()>0){
				model.put("lotName", awardRecords.get(0).getLotteryName());
			}else{
				String lotteryName = lotteryService.queryLotteryTypeNameByCode(lotteryVo.getLotteryCode());
				model.put("lotName", lotteryName);
			}
			model.put("records", records);
			
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	
	/**
	 * 3D页面玩法初始化
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/show3d")
	@Token(needSaveToken=true)
	public ModelAndView show3d(LotteryTypeVO lotteryVo,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		if(StringUtils.isEmpty(lotteryVo.getLotteryCode())){
			lotteryVo.setLotteryCode(DataDictionaryUtil.FC3D_LOTTERY_CODE);
		}
		model.put("lotteryCode", lotteryVo.getLotteryCode());
		
		TaskConfig task = null;
		try {
			lotteryVo.setLotteryStatus(DataDictionaryUtil.STATUS_OPEN);
			param.put("lotteryKey", lotteryVo);
			List<LotteryType> lotterys = lotteryService.queryLotteryList(param);
			model.put("lotteryName", lotterys.get(0).getLotteryName());
			model.put("lotteryGroup", lotterys.get(0).getLotteryGroup());
			int totalTimes=0;
			String totalMunites ="";
			for(LotteryType t : lotterys){
				totalTimes += t.getTotalTimes();
				if(totalMunites==""){
					totalMunites = String.valueOf(t.getSpanTime()/60);
				}else{
					totalMunites += "或"+String.valueOf(t.getSpanTime()/60);
				}
			}
			model.put("totalMunites", totalMunites);
			model.put("totalTimes", totalTimes);
			model.put("startTime", lotterys.get(0).getStartTime());
			model.put("endTime", lotterys.get(lotterys.size()-1).getEndTime());
			model.put("lotteryName", lotterys.get(0).getLotteryName());
			//查询当前奖期任务
			task = taskService.queryCurrentTask(param);
			if(task!=null){
				setBetEndTime(model,task);
				//查询历史开奖记录
				queryAwardRecords(param,model,task,lotteryVo);
			}
		}catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("lowlottery/ssc",model);
	}
	
	@RequestMapping("/init3d")
	@ResponseBody
	public Map<String,Object> init3d(LotteryTypeVO lotteryVo,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> param = new HashMap<String,Object>();
		Map<String,Object> model = new HashMap<String,Object>();
		if(StringUtils.isEmpty(lotteryVo.getLotteryCode())){
			lotteryVo.setLotteryCode(DataDictionaryUtil.SSC_CQ_LOTTERY_CODE);
		}
		lotteryVo.setLotteryLevel(DataDictionaryUtil.COMMON_FLAG_1);
		
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(DataDictionaryUtil.STATUS_DELETE);
		statusList.add(DataDictionaryUtil.STATUS_OPEN);
		lotteryVo.setStatusList(statusList);
		
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		
		param.put("lotteryKey", lotteryVo);
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		List<LotteryPlayModel> lotteryModelList = null;
		Map<String,Object> bonusMap = null;
		try {
			lotteryModelList = lotteryModelService.queryModelByLottery(param);
			model.put("lottery", lotteryModelList.get(0).getLottery());
			
			//查询用户返点值及奖金组名称
			LotteryPlayModelVO lpmVo = new LotteryPlayModelVO();
			lpmVo.setLotteryCode(lotteryVo.getLotteryCode());
			lpmVo.setUserName(user.getCustomerName());
			param.put("lpmKey", lpmVo);
			bonusMap= lpbService.queryUserModelNoBonus(param);
			
			//查询彩种玩法对应设置的返点率
			LotteryPlayBonus lpbonus = lotteryModelList.get(0).getBonus();
			BigDecimal pxRb = (BigDecimal) bonusMap.get("pxRebates");
			//如果返点概率不一致，则用玩法配置的返点最高值减去点差
			if(lpbonus.getRebates().compareTo(pxRb)!=0){
				BigDecimal stepRb = (BigDecimal) bonusMap.get("stepRebates");
				bonusMap.remove("rebates");
				if(lpbonus.getRebates().subtract(stepRb).compareTo(BigDecimal.ZERO)==-1){
					bonusMap.put("rebates", BigDecimal.ZERO);
				}else{
					bonusMap.put("rebates", lpbonus.getRebates().subtract(stepRb));
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		//根据玩法组名去除重复的玩法组名，根据页面div结构构造list。
		long time1 = System.currentTimeMillis();
		Map<String,List<LotteryPlayModel>> map = new HashMap<String, List<LotteryPlayModel>>();
		for(LotteryPlayModel lpm : lotteryModelList){
			String key =lpm.getPlayModel().getGroupName();
			if(null==map.get(key)){
				List<LotteryPlayModel> tempList = new ArrayList<LotteryPlayModel>();
				tempList.add(lpm);
				map.put(lpm.getPlayModel().getGroupName(), tempList);
			}else{
				List<LotteryPlayModel> tempList = map.get(key);
				tempList.add(lpm);
			}
		}
		List<LotteryPlayModelListVO> playList = new ArrayList<LotteryPlayModelListVO>();
		for(String key : map.keySet()){
			LotteryPlayModelListVO vo = new LotteryPlayModelListVO();
			vo.setGroupName(key);
			
			//玩法组下的玩法，根据modelCode排序
			List<LotteryPlayModel> tempList = map.get(key);
			Compare2 cp2 = new Compare2();
			Collections.sort(tempList, cp2);
			
			vo.setLpmList(tempList);
			vo.setModelCode(map.get(key).get(0).getModelCode());
			//设置不同玩法组对应的选择球的球数
			vo.setBalCount(map.get(key).get(0).getPlayModel().getBalCount());
			vo.setSortIndex(map.get(key).get(0).getPlayModel().getSortIndex());
			playList.add(vo);
		}
		//玩法组，根据modelCode排序
		Compare cp = new Compare();
		Collections.sort(playList, cp);
		model.put("playList", playList);
		model.putAll(bonusMap);
		return model;
	}
	
	@RequestMapping(value="bet3D",method=RequestMethod.POST)
	@Token(needRemoveToken=true)
	@ResponseBody
	public Map<String,Object> bet3D(BetRecordListVO vo,HttpServletRequest request,HttpServletResponse response,RedirectAttributes reAttributes){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		if(null!=request.getAttribute("tokenError")){
			model.put("errorMsg", request.getAttribute("tokenError"));
			return model;
		}
		try {
			//betFilter.checkBlackList(vo);
			LotteryTypeVO typevo = new LotteryTypeVO();
			List<BetRecordVO> brs = vo.getVolist();
			typevo.setLotteryCode(brs.get(0).getLotteryCode());
			param.put("lotteryKey", typevo);
			TaskConfig taskConfig = taskConfigService.queryCurrentTask(param);
			//奖期检查
			betFilter.checkTaskStatus(taskConfig);
			
			betFilter.betTimeLimit(vo.getIssueNos(),taskConfig);
			CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
			param.put("volistkey", vo);

			//未充值用户，每笔订单最高2元投注限制
			betFilter.noRechargeLimit(vo, user);
			
			betFilter.customerLevelLimit(user);
			betFilter.p3BetNumberLimit(vo,vo.getIssueNos());
			param.put(CommonUtil.CUSTOMERUSERKEY,user);
			List<BetRecordVO> volist = vo.getVolist();
			if(volist.size()==0)throw new LotteryException("投注单不能为空");
			if(!checkBetRecordList(vo))throw new LotteryException("投注单数据异常");
			betRecordService.saveBetRecordOrder(param);
			model.put("success", "投注成功");
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}finally{
			model.put("token", request.getSession().getAttribute("token"));
		}
		return model;
	}
	
	/**
	 * 从map中获取最新的开奖号码。
	 * @param key
	 * @param request
	 * @param response
	 * @param reAttributes
	 * @return
	 */
	@RequestMapping("refreshOpenNumResult")
	@ResponseBody
	public String refreshOpenNumResult(String msg,HttpServletRequest request,HttpServletResponse response,RedirectAttributes reAttributes){
		msg = new String(Base64.decodeBase64(msg));
		TempMapVO vo = JsonUtil.jsonToObject(msg, TempMapVO.class);
		TempMapVO mapVo = WebAppStaticCollection.getOpenNumMap().get(vo.getKey());
		if(null==mapVo){
			WebAppStaticCollection.getOpenNumMap().put(vo.getKey(), vo);
		}else{
			WebAppStaticCollection.getOpenNumMap().remove(vo.getKey());
			WebAppStaticCollection.getOpenNumMap().put(vo.getKey(), vo);
		}
		return "success";
	}
}
