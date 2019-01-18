package com.lottery.admin.controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.BonusGroup;
import com.lottery.bean.entity.CustomerOrder;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.DomainUrl;
import com.lottery.bean.entity.LotteryAwardRecord;
import com.lottery.bean.entity.LotteryPlayBonus;
import com.lottery.bean.entity.LotteryPlayModel;
import com.lottery.bean.entity.LotterySource;
import com.lottery.bean.entity.LotteryType;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.PlayAwardLevel;
import com.lottery.bean.entity.PlayModel;
import com.lottery.bean.entity.SourceLink;
import com.lottery.bean.entity.TaskConfig;
import com.lottery.bean.entity.vo.BonusGroupVO;
import com.lottery.bean.entity.vo.CustomerOrderVO;
import com.lottery.bean.entity.vo.DomainUrlVO;
import com.lottery.bean.entity.vo.LotteryAwardRecordVO;
import com.lottery.bean.entity.vo.LotteryJsonVO;
import com.lottery.bean.entity.vo.LotteryListVO;
import com.lottery.bean.entity.vo.LotteryPlayBonusListVO;
import com.lottery.bean.entity.vo.LotteryPlayBonusVO;
import com.lottery.bean.entity.vo.LotteryPlayModelListVO;
import com.lottery.bean.entity.vo.LotteryPlayModelVO;
import com.lottery.bean.entity.vo.LotterySourceVO;
import com.lottery.bean.entity.vo.LotteryTypeVO;
import com.lottery.bean.entity.vo.PlayAwardLevelVO;
import com.lottery.bean.entity.vo.PlayModelVO;
import com.lottery.bean.entity.vo.SourceLinkVO;
import com.lottery.service.IAdminParameterService;
import com.lottery.service.IBonusGroupService;
import com.lottery.service.ILotteryAwardRecordService;
import com.lottery.service.ILotteryPlayModelService;
import com.lottery.service.ILotterySourceService;
import com.lottery.service.ILotteryTypeService;
import com.lottery.service.IPlayModelService;
import com.lottery.service.ISourceLinkService;
import com.lottery.service.ITaskConfigService;
import com.lottery.service.ITaskLogService;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.BeanPropertiesCopy;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.JsonUtil;



@Controller
@RequestMapping("/lottery")
public class LotteryController extends BaseController {

	@Autowired
	private IPlayModelService playService;
	
	@Autowired
	private IBonusGroupService bonusService;
	
	@Autowired
	private ILotteryTypeService lotteryService;
	
	@Autowired
	private ITaskConfigService taskService;
	
	@Autowired
	private ITaskLogService logService;
	
	@Autowired
	private ILotterySourceService sourceService;
	
	@Autowired
	private ISourceLinkService linkService;
	
	@Autowired
	private ILotteryPlayModelService lpmService;
	
	@Autowired
	private ILotteryAwardRecordService awardService;
	
	@Autowired
	private IAdminParameterService parameterService;
	
	@RequestMapping("/showLotteryEngine")
	public ModelAndView showLotteryEngine(HttpServletRequest request,HttpServletResponse response,
			RedirectAttributes redirectAttributes){
		//先清空一次session中的旧attribute。
		request.getSession().removeAttribute("lotteryKey");
		request.getSession().removeAttribute("seriesListKey");
		request.getSession().removeAttribute("playListKey");
		request.getSession().removeAttribute("playBonusListKey");
		
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		
		LotteryTypeVO lotteryVo = new LotteryTypeVO();
		lotteryVo.setLotteryLevel(DataDictionaryUtil.COMMON_FLAG_1);
		param.put("lotteryKey", lotteryVo);
		List<LotteryType> lotteryList = null;
		try {
			lotteryList = lotteryService.queryLotteryEngineInfo(param);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e,model);
			return new ModelAndView("lottery/lottery_engine",model);
		}
		
		//构建vo，存放彩种组对应的彩种集合。
		Map<String,List<LotteryType>> groupMap = new HashMap<String,List<LotteryType>>();
		for(LotteryType lottery : lotteryList ){
			String group = lottery.getLotteryGroup();
			if(null==groupMap.get(group)){
				List<LotteryType> list = new ArrayList<LotteryType>();
				list.add(lottery);
				groupMap.put(group, list);
			}else{
				groupMap.get(group).add(lottery);
			}
		}
		
		List<LotteryListVO>  voList = new ArrayList<LotteryListVO>();
		for(String groupKey : groupMap.keySet()){
			LotteryListVO listVO = new LotteryListVO();
			listVO.setLotteryGroup(groupKey);
			listVO.setLotteryGroupName(CommonUtil.lotteryGroupMap.get(groupKey));
			listVO.setLotteryList(groupMap.get(groupKey));
			voList.add(listVO);
		}
		
		model.put("groupList", voList);
		this.setNowTime(model);
		return new ModelAndView("lottery/lottery_engine",model);
	}
	
	/**
	 * 当前时间显示
	 * @param model
	 */
	private void setNowTime(Map<String,Object> model){
		Date now = new Date();
		int hour = now.getHours();
		int minutes = now.getMinutes();
		int seconds = now.getSeconds();
		if(hour<10){
			model.put("hour","0" + hour);
		}else{
			model.put("hour", hour);
		}
		if(hour<10){
			model.put("minutes","0" + minutes);
		}else{
			model.put("minutes", minutes);
		}
		if(seconds<10){
			model.put("hour","0" + seconds);
		}else{
			model.put("seconds", seconds);
		}
	}
	
	@RequestMapping("/sourceMonitor")
	public ModelAndView sourceMonitor(HttpServletRequest request,HttpServletResponse response,
			RedirectAttributes redirectAttributes){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String,?> map = RequestContextUtils.getInputFlashMap(request);
		try {
			List<LotteryType> lotterys = lotteryService.queryLotteryTypeAll(param);
			model.put("lotterys", lotterys);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e,model);
		}
		if(map!=null){
			model.putAll(map);
		}
		
		return new ModelAndView("lottery/source_monitor",model);
	}
	
	@RequestMapping("/lotteryDetail")
	public ModelAndView lotteryDetail(LotteryTypeVO lotteryVo,HttpServletRequest request,HttpServletResponse response,
			RedirectAttributes redirectAttributes){
		
		Map<String,Object> map = (Map<String, Object>) RequestContextUtils.getInputFlashMap(request);
		if(null==map){
			map=new HashMap<String, Object>();
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		
		if(StringUtils.isEmpty(lotteryVo.getLotteryCode())){
			lotteryVo = (LotteryTypeVO) request.getSession().getAttribute("lotKey");
		}
		param.put("lotteryKey", lotteryVo);
		Map<String,?> infoMap = null;
		try {
			infoMap = lotteryService.queryLotteryAllInfo(param);
			
			LotteryType lottery = ((List<LotteryType>)infoMap.get("lotteryList")).get(0);
			lottery.setLotteryGroupName(CommonUtil.lotteryGroupMap.get(lottery.getLotteryGroup()));
			
			String key = "lotRestDays"+lottery.getLotteryCode();
			String[] keys = new String[]{key};
			param.put("parameterName", "lotConfig");
			param.put("parameterKeys", keys);
			Map<String,String> returnMap = parameterService.getParameterList(param);
			
			map.putAll(infoMap);
			map.put("restDays", returnMap==null?"0":returnMap.get(key));
			map.put("lottery", lottery);
			
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e,model);
			return new ModelAndView("lottery/lottery_detail",model);
		}
		
		return new ModelAndView("lottery/lottery_detail",map);
	}
	
	@RequestMapping("/initCreateLottery")
	public ModelAndView initCreateLottery(LotteryTypeVO lotteryVo,HttpServletRequest request,HttpServletResponse response,
			RedirectAttributes redirectAttributes){
		Map<String, Object> model = new HashMap<String, Object>();
		LotteryTypeVO sessionVo = (LotteryTypeVO) request.getSession().getAttribute("lotteryKey");
		if(null!=sessionVo){
			model=new HashMap<String, Object>();
			model.put("lotteryVo", sessionVo);
		}else{
			List<SourceLinkVO> links = new ArrayList<SourceLinkVO>();
			SourceLinkVO link = new SourceLinkVO();
			links.add(link);
			lotteryVo.setLinks(links);
			model.put("lotteryVo", lotteryVo);
		}
		
		//彩种组list
		List<LotteryTypeVO> groupNameList = new ArrayList<LotteryTypeVO>(); 
		Map<String,String> gm = CommonUtil.lotteryGroupMap;
		for(String key : gm.keySet()){
			LotteryTypeVO temp = new LotteryTypeVO();
			temp.setLotteryGroup(key);
			temp.setLotteryGroupName(gm.get(key));
			groupNameList.add(temp);
		}
		model.put("groupNameList", groupNameList);
		return new ModelAndView("lottery/create_lottery/lottery_source",model);
	}
	
	@RequestMapping("/createLotteryInfo")
	public ModelAndView createLotteryInfo(LotteryTypeVO lotteryVo,
			HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
		
		//放到session中，下个页面号获取到该信息。
		request.getSession().removeAttribute("lotteryKey");
		request.getSession().removeAttribute("seriesListKey");
		request.getSession().setAttribute("lotteryKey", lotteryVo);
		if(StringUtils.isEmpty(lotteryVo.getLotteryCode())){
			redirectAttributes.addFlashAttribute("errorMsg", "彩种代码不能为空!");
			return new ModelAndView("redirect:initCreateLottery.do");
		}
		if(StringUtils.isEmpty(lotteryVo.getLotteryName())){
			redirectAttributes.addFlashAttribute("errorMsg", "彩种名称不能为空!");
			return new ModelAndView("redirect:initCreateLottery.do");
		}
		if(StringUtils.isEmpty(lotteryVo.getSeriesRule())){
			redirectAttributes.addFlashAttribute("errorMsg", "奖期规则不能为空!");
			return new ModelAndView("redirect:initCreateLottery.do");
		}
		
		for(SourceLinkVO linkVo : lotteryVo.getLinks()){
			if(StringUtils.isEmpty(linkVo.getSourceName())){
				redirectAttributes.addFlashAttribute("errorMsg", "号源名称不能为空!");
				return new ModelAndView("redirect:initCreateLottery.do");
			}
			if(StringUtils.isEmpty(linkVo.getSourceLink())){
				redirectAttributes.addFlashAttribute("errorMsg", "号源网址不能为空!");
				return new ModelAndView("redirect:initCreateLottery.do");
			}
		}
		
		return new ModelAndView("redirect:initLotteryIssue.do");
	}
	
	@RequestMapping("/initLotteryIssue")
	public ModelAndView initLotteryIssue(HttpServletRequest request,HttpServletResponse response,
			RedirectAttributes redirectAttributes){
		Map<String, Object> model = new HashMap<String, Object>();
		LotteryListVO listVo = (LotteryListVO) request.getSession().getAttribute("seriesListKey");
		
		if(null!=listVo){
			model=new HashMap<String, Object>();
			model.put("lotteryList", listVo.getLotterys());
			model.put("timeConfig", listVo.getTimeConfig());
		}else{
			List<LotteryTypeVO> lots = new ArrayList<LotteryTypeVO>();
			LotteryTypeVO lot = new LotteryTypeVO();
			lots.add(lot);
			model.put("lotteryList", lots);
		}
		return new ModelAndView("lottery/create_lottery/issues",model);
	}
	
	@RequestMapping("/saveLotteryTask")
	public ModelAndView saveLotteryTask(LotteryListVO listVo,
			HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
		redirectAttributes.addFlashAttribute("lotteryList", listVo);
		
		if(StringUtils.isEmpty(listVo.getTimeConfig())){
			redirectAttributes.addFlashAttribute("errorMsg", "开奖周期至少要勾选一个!");
			return new ModelAndView("redirect:initLotteryIssue.do");
		}
		for(LotteryTypeVO typeVo : listVo.getLotterys()){
			if(StringUtils.isEmpty(typeVo.getStartTime())){
				redirectAttributes.addFlashAttribute("errorMsg", "开始时间不能为空!");
				return new ModelAndView("redirect:initLotteryIssue.do");
			}
			if(StringUtils.isEmpty(typeVo.getEndTime())){
				redirectAttributes.addFlashAttribute("errorMsg", "截止时间不能为空!");
				return new ModelAndView("redirect:initCreateLottery.do");
			}
			if(StringUtils.isEmpty(typeVo.getSpanTime())){
				redirectAttributes.addFlashAttribute("errorMsg", "奖期间隔不能为空!");
				return new ModelAndView("redirect:initLotteryIssue.do");
			}
			if(StringUtils.isEmpty(typeVo.getTotalTimes())){
				redirectAttributes.addFlashAttribute("errorMsg", "总奖期数不能为空!");
				return new ModelAndView("redirect:initLotteryIssue.do");
			}
			if(StringUtils.isEmpty(typeVo.getEndTime())){
				redirectAttributes.addFlashAttribute("errorMsg", "截止投注时间不能为空!");
				return new ModelAndView("redirect:initLotteryIssue.do");
			}
		}
		
		//放到session中，下个页面号获取到该信息。
		request.getSession().removeAttribute("playListKey");
		request.getSession().setAttribute("seriesListKey", listVo);
		return new ModelAndView("redirect:initLotteryPlayModel.do");
	}
	
	@RequestMapping("/initLotteryPlayModel")
	public ModelAndView initLotteryPlayModel(HttpServletRequest request,HttpServletResponse response,
			RedirectAttributes redirectAttributes){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		LotteryTypeVO lotteryVo = (LotteryTypeVO) request.getSession().getAttribute("lotteryKey");
		param.put("groupKey", lotteryVo.getLotteryGroup());
		List<PlayModel> playList=null;
		try {
			playList = playService.queryPlayModel(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, redirectAttributes);
			return new ModelAndView("redirect:initLotteryIssue.do", model);
		}
		List<PlayModel> selectedList = (List<PlayModel>) request.getSession().getAttribute("playListKey");
		if(selectedList!=null){
			for(PlayModel apm : playList){
				for(PlayModel pm : selectedList){
					if(pm.getModelCode().equals(apm.getModelCode())){
						apm.setSelectedModel(true);
						apm.setLevelList(pm.getLevelList());
						break;
					}
				}
			}
		}
		
		model.put("playList", playList);
		return new ModelAndView("lottery/create_lottery/game_info",model);
	}
	
	@RequestMapping("/savePlayModel")
	public ModelAndView savePlayModel(LotteryPlayModelVO playVo,
			HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
		Map<String, Object> param = new HashMap<String, Object>();
		if(StringUtils.isEmpty(playVo.getPlayList().size()==0)){
			redirectAttributes.addFlashAttribute("errorMsg", "彩种玩法至少要勾选一个!");
			return new ModelAndView("redirect:initLotteryPlayModel.do");
		}else{
			List<Long> idList = new ArrayList<Long>();
			for(int i=0;i<playVo.getPlayList().size();i++){
				idList.add(playVo.getPlayList().get(i).getId());
			}
			LotteryTypeVO lotteryVo = (LotteryTypeVO) request.getSession().getAttribute("lotteryKey");
			param.put("groupKey", lotteryVo.getLotteryGroup());
			param.put("idListKey", idList);
			List<PlayModel> playList = playService.queryPlayModel(param);
			if(!lotteryVo.getLotteryGroup().equals("SYXW")){
				for(PlayModel pm : playList){
					for(PlayModelVO plvo : playVo.getPlayList()){
						if(pm.getModelCode().equals(plvo.getModelCode())){
							pm.setLevelList(plvo.getLevelList());
							break;
						}
					}
				}
			}
			
			redirectAttributes.addFlashAttribute("playList", playList);
			//放到session中，下个页面号获取到该信息。
			request.getSession().removeAttribute("playBonusListKey");
			request.getSession().setAttribute("playListKey", playList);
		}
		
		return new ModelAndView("redirect:initBonusGroup.do");
	}
	
	
	@RequestMapping("/initBonusGroup")
	public ModelAndView initBonusGroup(HttpServletRequest request,HttpServletResponse response,
			RedirectAttributes redirectAttributes){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		List<BonusGroup> bonusGroupList=null;
		try {
			bonusGroupList = bonusService.findBonusGroupAll(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, redirectAttributes);
			return new ModelAndView("redirect:initLotteryIssue.do", model);
		}
		
		//从session中获取到选中的玩法
		List<PlayModel> selectedList = (List<PlayModel>) request.getSession().getAttribute("playListKey");
		for(BonusGroup group : bonusGroupList){
			group.setPlayList(selectedList);
		}
		
		
		model.put("bonusGroupList", bonusGroupList);
		return new ModelAndView("lottery/create_lottery/money_group",model);
	}
	
	@RequestMapping("/savePlayBonusGroup")
	public ModelAndView savePlayBonusGroup(LotteryPlayBonusListVO playBonusListVo,
			HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
		for(LotteryPlayBonusVO pv : playBonusListVo.getPlayBonusList()){
			if(null==pv.getMargin()){
				redirectAttributes.addFlashAttribute("errorMsg", "利润率不能为空!");
				return new ModelAndView("redirect:initBonusGroup.do");
			}
			if(null==pv.getRebates()){
				redirectAttributes.addFlashAttribute("errorMsg", "返点不能为空!");
				return new ModelAndView("redirect:initBonusGroup.do");
			}
			if(null==pv.getPayoutRatio()){
				redirectAttributes.addFlashAttribute("errorMsg", "返奖率不能为空!");
				return new ModelAndView("redirect:initBonusGroup.do");
			}
		}
		
		request.getSession().setAttribute("playBonusListKey", playBonusListVo);
		return new ModelAndView("redirect:initLimitAmount.do");
	}
	@RequestMapping("/initLimitAmount")
	public ModelAndView initLimitAmount(LotteryPlayModelVO playVo,
			HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
		Map<String, Object> model = new HashMap<String, Object>();
		List<PlayModel> selectedList = (List<PlayModel>) request.getSession().getAttribute("playListKey");
		model.put("playModelList", selectedList);
		model.put("playSize", selectedList.size());
		return new ModelAndView("lottery/create_lottery/limit_amount",model);
	}
	
	@RequestMapping("/saveAllLotteryInfo")
	public ModelAndView saveAllLotteryInfo(LotteryPlayModelListVO lpmListVo,
			HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
		Map<String, Object> param = new HashMap<String, Object>();
		if(null==lpmListVo.getTotalLimitAmount()){
			redirectAttributes.addFlashAttribute("errorMsg", "总限金额不能为空!");
			return new ModelAndView("redirect:initLimitAmount.do");
		}
		//放到session中，下个页面号获取到该信息。
		
		LotteryTypeVO lotteryVo = (LotteryTypeVO) request.getSession().getAttribute("lotteryKey");
		LotteryListVO seriesListVo = (LotteryListVO) request.getSession().getAttribute("seriesListKey");
		List<PlayModel> selectedList = (List<PlayModel>) request.getSession().getAttribute("playListKey");
		LotteryPlayBonusListVO playBonusListVo=(LotteryPlayBonusListVO) request.getSession().getAttribute("playBonusListKey");
		param.put("lotteryKey", lotteryVo);
		param.put("seriesListKey", seriesListVo);
		param.put("playListKey", selectedList);
		param.put("playBonusListKey", playBonusListVo);
		param.put("lpmListKey", lpmListVo);
		
		AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put(CommonUtil.USERKEY, user);
		
		try {
			lotteryService.saveAllLotteryInfo(param);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:initLimitAmount.do");
		}
		request.getSession().removeAttribute("lotteryKey");
		request.getSession().removeAttribute("seriesListKey");
		request.getSession().removeAttribute("playListKey");
		request.getSession().removeAttribute("playBonusListKey");
		return new ModelAndView("redirect:showLotteryEngine.do");
	}
	
	@RequestMapping("saveLotteryInfo")
	@ResponseBody
	public Map<String, ?> saveLotteryInfo(LotteryTypeVO lotteryVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lotteryKey", lotteryVo);
		LotteryType lottery = null;
		try {
			lottery = lotteryService.saveLotteryInfo(param);
			if(lottery.getLotteryGroup().equals(CommonUtil.LOTTERY_GROUP_3D)&&lotteryVo.getCatchTimes()>0){
				//修改3d的休市天数
				Map<String,String> keyValueMap = new HashMap<String, String>();
				keyValueMap.put("lotRestDays"+lottery.getLotteryCode(), lotteryVo.getCatchTimes()+"");
				param.put("keyValueMap", keyValueMap);
				param.put("parameterName", "lotConfig");
				param.put(CommonUtil.USERKEY, (AdminUser)request.getSession().getAttribute(CommonUtil.USERKEY));
				parameterService.saveParameterValue(param);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
			return model;
		}
		model.put("lottery", lottery);
		return model;
	}
	
	@RequestMapping("saveSource")
	@ResponseBody
	public Map<String, ?> saveSource(LotterySourceVO lsVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lotterySourceKey", lsVo);
		List<LotterySource> lsList=null;
		try {
			sourceService.saveSource(param);
			LotteryTypeVO vo = new LotteryTypeVO();
			vo.setLotteryCode(lsVo.getLotteryCode());
			vo.setLotteryGroup(lsVo.getLotteryGroup());
			param.put("lotteryKey", vo);
			lsList = sourceService.queryLinksByLotteryCode(param);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
			return model;
		}
		
		
		model.put("lsList", lsList);
		return model;
	}
	
	/**
	 * 保存奖期修改
	 * @param listVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("savePeriod")
	@ResponseBody
	public Map<String, ?> savePeriod(LotteryListVO listVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		if(StringUtils.isEmpty(listVo.getLotteryObjs())||listVo.getLotteryObjs().lastIndexOf("]")<0
				||(!StringUtils.isEmpty(listVo.getNewLotteryObjs())&&listVo.getNewLotteryObjs().lastIndexOf("]")<0)){
			LotteryExceptionLog.wirteLog(new LotteryException("奖期设置异常，无法解析的json字符串！"), model);
			return model;
		}
		String lotteryObjs = listVo.getLotteryObjs().replaceAll("'", "\"");
		LotteryJsonVO[] lotteryPeriodVos= (LotteryJsonVO[]) JsonUtil.jsonToObject(lotteryObjs,LotteryJsonVO[].class);
		param.put("periodsKey", lotteryPeriodVos);
		
		if(!StringUtils.isEmpty(listVo.getNewLotteryObjs())){
			String newLotteryObjs = listVo.getNewLotteryObjs().replaceAll("'", "\"");
			LotteryJsonVO[] newLotteryPeriodVos= (LotteryJsonVO[]) JsonUtil.jsonToObject(newLotteryObjs,LotteryJsonVO[].class);
			param.put("newPeriodsKey", newLotteryPeriodVos);
		}
		
		List<LotteryType> lsList=null;
		try {
			lsList = lotteryService.savePeriod(param);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
			return model;
		}
		model.put("lsList", lsList);
		return model;
	}
	
	/**
	 * 显示修改玩法的配置页面
	 * @param lpmListVo
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/showModifyModel")
	public ModelAndView showModifyModel(LotteryPlayModelVO lpmVo,
			HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		lpmVo.setStatus(DataDictionaryUtil.COMMON_FLAG_1);
		param.put("lpmKey", lpmVo);
		param.put("groupKey", lpmVo.getLotteryGroup());
		
		List<PlayModel> playList=null;
		try {
			playList = playService.queryPlayModel(param);
			List<LotteryPlayModel> lpmList = lpmService.queryPlayModelByLotteryCode(param);
			for(PlayModel pm :playList){
				for(LotteryPlayModel lpm : lpmList){
					if(lpm.getModelCode().equals(pm.getModelCode())){
						pm.setSelectedModel(true);
						List<PlayAwardLevelVO> voList = new ArrayList<PlayAwardLevelVO>();
						for(PlayAwardLevel level : lpm.getLevelList()){
							PlayAwardLevelVO vo = new PlayAwardLevelVO();
							BeanPropertiesCopy.copyProperties(level, vo);
							voList.add(vo);
						}
						pm.setLevelList(voList);
						break;
					}
				}
			}
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, redirectAttributes);
		}
		model.put("playList", playList);
		request.getSession().removeAttribute("lotKey");
		LotteryTypeVO lotVo = new LotteryTypeVO();
		lotVo.setLotteryCode(lpmVo.getLotteryCode());
		lotVo.setLotteryGroup(lpmVo.getLotteryGroup());
		request.getSession().setAttribute("lotKey", lotVo);
		return new ModelAndView("lottery/configLotPlayModel",model);
	}
	
	@RequestMapping("/saveModifyPlayModel")
	public ModelAndView saveModifyPlayModel(LotteryPlayModelVO playVo,
			HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
		Map<String, Object> param = new HashMap<String, Object>();
		if(StringUtils.isEmpty(playVo.getPlayList().size()==0)){
			redirectAttributes.addFlashAttribute("errorMsg", "彩种玩法至少要勾选一个!");
			return new ModelAndView("redirect:showModifyModel.do");
		}else{
			List<Long> idList = new ArrayList<Long>();
			for(int i=0;i<playVo.getPlayList().size();i++){
				idList.add(playVo.getPlayList().get(i).getId());
			}
			param.put("groupKey", playVo.getLotteryGroup());
			param.put("idListKey", idList);
			List<PlayModel> playList = playService.queryPlayModel(param);
			if(!playVo.getLotteryGroup().equals("SYXW")){
				for(PlayModel pm : playList){
					for(PlayModelVO plvo : playVo.getPlayList()){
						if(pm.getModelCode().equals(plvo.getModelCode())){
							pm.setLevelList(plvo.getLevelList());
							break;
						}
					}
				}
			}
			redirectAttributes.addFlashAttribute("playList", playList);
			//放到session中，下个页面号获取到该信息。
			request.getSession().setAttribute("playListKey", playList);
		}
		
		return new ModelAndView("redirect:showModifylpb.do");
	}
	
	@RequestMapping("/showModifylpb")
	public ModelAndView showModifylpb(LotteryPlayModelVO lpmVo,
			HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		List<BonusGroup> bonusGroupList=null;
		try {
			bonusGroupList = bonusService.findBonusGroupAll(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, redirectAttributes);
			return new ModelAndView("redirect:showModifyModel.do", model);
		}
		//从session中获取到选中的玩法
		List<PlayModel> selectedList = (List<PlayModel>) request.getSession().getAttribute("playListKey");
		for(BonusGroup group : bonusGroupList){
			group.setPlayList(selectedList);
		}
		model.put("lotteryVo", request.getSession().getAttribute("lotKey"));
		model.put("bonusGroupList", bonusGroupList);
		return new ModelAndView("lottery/configLotPlaybonus",model);
	}
	
	@RequestMapping("/saveModifylpb")
	public ModelAndView saveModifylpb(LotteryPlayBonusListVO playBonusListVo,
			HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
		for(LotteryPlayBonusVO pv : playBonusListVo.getPlayBonusList()){
			if(null==pv.getMargin()){
				redirectAttributes.addFlashAttribute("errorMsg", "利润率不能为空!");
				return new ModelAndView("redirect:configLotPlaybonus.do");
			}
			if(null==pv.getRebates()){
				redirectAttributes.addFlashAttribute("errorMsg", "返点不能为空!");
				return new ModelAndView("redirect:configLotPlaybonus.do");
			}
			if(null==pv.getPayoutRatio()){
				redirectAttributes.addFlashAttribute("errorMsg", "返奖率不能为空!");
				return new ModelAndView("redirect:configLotPlaybonus.do");
			}
		}
		request.getSession().setAttribute("playBonusListKey", playBonusListVo);
		return new ModelAndView("redirect:configLotLimitAmount.do");
	}
	
	@RequestMapping("/configLotLimitAmount")
	public ModelAndView configLotLimitAmount(LotteryPlayModelVO playVo,
			HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
		Map<String, Object> model = new HashMap<String, Object>();
		List<PlayModel> selectedList = (List<PlayModel>) request.getSession().getAttribute("playListKey");
		model.put("playModelList", selectedList);
		model.put("playSize", selectedList.size());
		return new ModelAndView("lottery/configLimitAmount",model);
	}
	
	/**
	 * 保存所有玩法相关的配置
	 * @param lpmListVo
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/savePlayRelated")
	public ModelAndView savePlayRelated(LotteryPlayModelListVO lpmListVo,
			HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
		Map<String, Object> param = new HashMap<String, Object>();
		if(null==lpmListVo.getTotalLimitAmount()){
			redirectAttributes.addFlashAttribute("errorMsg", "总限金额不能为空!");
			return new ModelAndView("redirect:configLotLimitAmount.do");
		}
		//放到session中，下个页面号获取到该信息。
		LotteryTypeVO lotVo =(LotteryTypeVO) request.getSession().getAttribute("lotKey");
		List<PlayModel> selectedList = (List<PlayModel>) request.getSession().getAttribute("playListKey");
		LotteryPlayBonusListVO playBonusListVo=(LotteryPlayBonusListVO) request.getSession().getAttribute("playBonusListKey");
		param.put("playListKey", selectedList);
		param.put("playBonusListKey", playBonusListVo);
		param.put("lpmListKey", lpmListVo);
		param.put("lotteryKey", lotVo);
		AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put(CommonUtil.USERKEY, user);
		try {
			lotteryService.savePlayRelated(param);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e);
			return new ModelAndView("redirect:configLotLimitAmount.do");
		}
		request.getSession().removeAttribute("playListKey");
		request.getSession().removeAttribute("playBonusListKey");
		return new ModelAndView("redirect:lotteryDetail.do");
	}
	
	
	/**
	 * 号源监控
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryFailedTask")
	@ResponseBody
	public Map<String,Object> queryFailedTask(LotteryAwardRecordVO vo ,HttpServletRequest request
			,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String, Object>(); 
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("awardKey", vo);
		Page<LotteryAwardRecordVO, LotteryAwardRecord> page=null;
		try {
			page = awardService.queryFailedTask(param);
			model.put("page", page);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e);
		}
		return model;
	}
	
	/**
	 * 号源监控
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryOneRecord")
	@ResponseBody
	public Map<String,Object> queryOneRecord(LotteryAwardRecordVO vo ,HttpServletRequest request
			,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String, Object>(); 
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("awardKey", vo);
		Page<LotteryAwardRecordVO, LotteryAwardRecord> page=null;
		try {
			page = awardService.queryOneRecord(param);
			model.put("page", page);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e);
		}
		return model;
	}
	/**
	 * 号源监控 停止自动开奖
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/stopAutoTask")
	@ResponseBody
	public Map<String,Object> stopAutoTask(LotteryAwardRecordVO vo ,HttpServletRequest request
			,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String, Object>(); 
		Map<String,Object> param = new HashMap<String, Object>();
		
		AdminUser user =  (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put("awardKey", vo);
		param.put(CommonUtil.USERKEY, user);
		LotteryAwardRecord record=null;
		try {
			record = awardService.updateAutoTask(param);
			model.put("record", record);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e);
		}
		return model;
	}
	
	/**
	 * 号源监控 手动开奖
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/handAward")
	@ResponseBody
	public Map<String,Object> handAward(LotteryAwardRecordVO vo ,HttpServletRequest request
			,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String, Object>(); 
		Map<String,Object> param = new HashMap<String, Object>();
		
		AdminUser user =  (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put("awardKey", vo);
		param.put(CommonUtil.USERKEY, user);
		try {
			awardService.updateHandAward(param);
			model.put("status", "success");
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e,model);
		}
		return model;
	}
	
	/**
	 * 撤销某期中奖
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/cancelAward")
	@ResponseBody
	public Map<String,Object> updateCancelAward(LotteryAwardRecordVO vo ,HttpServletRequest request
			,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String, Object>(); 
		Map<String,Object> param = new HashMap<String, Object>();
		
		AdminUser user =  (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put("awardKey", vo);
		param.put(CommonUtil.USERKEY, user);
		try {
			awardService.updateCancelAward(param);
			model.put("status", "success");
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e,model);
		}
		return model;
	}
}
