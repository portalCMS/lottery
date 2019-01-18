package com.lottery.phone.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.lottery.bean.entity.TaskConfig;
import com.lottery.bean.entity.vo.BetRecordListVO;
import com.lottery.bean.entity.vo.BetRecordVO;
import com.lottery.bean.entity.vo.LotteryAwardRecordVO;
import com.lottery.bean.entity.vo.LotteryPlayModelListVO;
import com.lottery.bean.entity.vo.LotteryPlayModelVO;
import com.lottery.bean.entity.vo.LotteryTypeVO;
import com.lottery.filter.BetFilter;
import com.lottery.service.IBetRecordService;
import com.lottery.service.ILotteryAwardRecordService;
import com.lottery.service.ILotteryPlayBonusService;
import com.lottery.service.ILotteryPlayModelService;
import com.lottery.service.ILotteryTypeService;
import com.lottery.service.ITaskConfigService;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;

@Controller
public class BetMobileController {
	
	@Autowired
	private ILotteryPlayBonusService lpbService;
	
	@Autowired
	private ILotteryPlayModelService lotteryModelService;
	
	@Autowired
	private ITaskConfigService taskConfigService;
	
	@Autowired
	private ILotteryTypeService lotteryService;
	
	@Autowired
	private ITaskConfigService taskService;
	
	@Autowired
	private BetFilter betFilter;
	
	@Autowired
	private IBetRecordService betRecordService;
	
	@Autowired
	private ILotteryAwardRecordService recordService;
	
	
	/**
	 * 时时彩页面玩法初始化
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/showopenssc")
	@Token(needSaveToken=true)
	public ModelAndView showSsc(LotteryAwardRecordVO lotteryVo,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		try {
			model.put("lotteryCode", lotteryVo.getLotteryCode());
		}catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("jspmp/ssc/openNumber",model);
	}
	
	
	/**
	 * 彩种首页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/indexmp")
//	@Token(needSaveToken=true)
	public ModelAndView showIndex(LotteryTypeVO vo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>(); 
		CustomerUser self = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		try {
			param.put("lotteryKey",vo);
			param.put("isMobile", true);
			List<LotteryType> lotteryList = lotteryService.queryLotteryList(param);
			model.put("lotteryList", lotteryList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("jspmp/index",model);
	}
	
	/**
	 * 进入彩种主页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/showshishicai")
	public ModelAndView showJiangxiCai(LotteryTypeVO lotteryVo,HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		model.put("lotteryCode", lotteryVo.getLotteryCode());
		TaskConfig task = null;
		try {
			lotteryVo.setLotteryStatus(DataDictionaryUtil.STATUS_OPEN);
			lotteryVo.setLotteryCode(lotteryVo.getLotteryCode());
			lotteryVo.setLotteryGroup(lotteryVo.getLotteryGroup());
			param.put("lotteryKey", lotteryVo);
			param.put("isMobile", true);
			List<LotteryType> lotterys = lotteryService.queryLotteryList(param);
			model.put("lotteryName", lotterys.get(0).getLotteryName());
			model.put("lotteryGroup", lotterys.get(0).getLotteryGroup());
			model.put("lotteryCode", lotterys.get(0).getLotteryCode());
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
		return new ModelAndView("jspmp/ssc/shishicai", model);
	}
	/**
	 * 确认投注
	 * @param vo
	 * @param request
	 * @param response
	 * @param reAttributes
	 * @return
	 */
	@RequestMapping(value="mobillSSC",method=RequestMethod.POST)
	//@Token(needRemoveToken=true)
	@ResponseBody
	public Map<String,Object> betSSC(BetRecordListVO vo,HttpServletRequest request,HttpServletResponse response,RedirectAttributes reAttributes){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		if(null!=request.getAttribute("tokenError")){
			model.put("success", request.getAttribute("tokenError"));
			return model;
		}
		try {
			//betFilter.checkBlackList(vo);
			LotteryTypeVO typevo = new LotteryTypeVO();
			List<BetRecordVO> brs = vo.getVolist();
			typevo.setLotteryCode(brs.get(0).getLotteryCode());
			param.put("lotteryKey", typevo);
			TaskConfig taskConfig = taskConfigService.queryCurrentTask(param);
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
			//LotteryExceptionLog.wirteLog(e, model);
			model.put("success", e.getMessage());
			e.printStackTrace();
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
	
	
	@RequestMapping("/initMobileSsc")
	@ResponseBody
	public Map<String,Object> initMobileSsc(LotteryTypeVO lotteryVo,HttpServletRequest request,HttpServletResponse response){
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
			param.put("isMobile", true);
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
	
}
