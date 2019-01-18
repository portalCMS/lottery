package com.lottery.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lottery.annotation.Token;
import com.lottery.bean.entity.AdvertisingList;
import com.lottery.bean.entity.CustomerActivity;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerActivityVO;
import com.lottery.bean.entity.vo.CustomerOrderVO;
import com.lottery.service.IAdvertisingListService;
import com.lottery.service.ICustomerActivityService;
import com.lottery.servlet.WebSocketMessageInboundPool;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;



@Controller
@RequestMapping("activity")
public class ActiveController extends BaseController{
	@Autowired
	private ICustomerActivityService activityService;
	
	@Autowired
	private IAdvertisingListService advertsService;
	
	@RequestMapping("/showActivityList")
	public ModelAndView showActivityList(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> param = new HashMap<String,Object>();
		Map<String,Object> model = new HashMap<String,Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		try {
			List<CustomerActivity> actList = activityService.queryActivityList(param);
			if(null==actList||actList.size()==0){
				model.put("haveAct", 0);
			}else{
				model.put("haveAct", 1);
			}
			model.put("actList", actList);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("activity/activityList",model);
	}
	
	/**
	 * 获取活动奖励
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/querMyActivityRecord")
	@ResponseBody
	public Map<String,Object> querMyActivityRecord(CustomerActivityVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> param = new HashMap<String,Object>();
		Map<String,Object> model = new HashMap<String,Object>();
		param.put("activityKey", vo);
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		try {
			Page<CustomerActivityVO, CustomerActivity> myActPage = activityService.querMyActivityRecord(param);
			model.put("page", myActPage);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("/activityDetail")
	public ModelAndView activityDetail(CustomerActivityVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> param = new HashMap<String,Object>();
		Map<String,Object> model = new HashMap<String,Object>();
		param.put("activityKey", vo);
		try {
			CustomerActivityVO activity = activityService.queryActivityDetail(param);
			activity.setSummary(StringEscapeUtils.unescapeHtml3(activity.getSummary()));
			param.put("regionCode", CommonUtil.Notice_CODE);
			List<AdvertisingList> adverts = advertsService
					.getAdvertisingLists(param);
			model.put("activity", activity);
			model.put("adverts", adverts);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("activity/activityDetail",model);
	}
	
	/**
	 * 获取活动奖励
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getActivityAward")
	@ResponseBody
	public Map<String,Object> getActivityAward(CustomerActivityVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> param = new HashMap<String,Object>();
		Map<String,Object> model = new HashMap<String,Object>();
		param.put("activityKey", vo);
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		try {
			String isClient = (String) request.getSession().getAttribute("isClient");
			String cpuId = (String) request.getSession().getAttribute("cpuid");
			String diskId = (String) request.getSession().getAttribute("diskid");
			param.put("isClient", isClient);
			param.put("cpuId", cpuId);
			param.put("diskId", diskId);
			Map<String,Object> returnMap = activityService.saveActivityAward(param);
			if(null!=returnMap.get("result")&&returnMap.get("result").equals("success")){
				//WebSocket 通知后台审核活动奖励订单。
				WebSocketMessageInboundPool.sendMessage("前台顾客领取活动奖励！");
			}
			model.put("returnMap", returnMap);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	/**
	 * 后台所有用户的财务订单处理结果刷新，避免一直响不停。
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/refreshResult")
	@ResponseBody
	public Map<String,Object> refreshResult(String msg,HttpServletRequest request,HttpServletResponse response){
		//WebSocket 通知后台审核活动奖励订单。
		WebSocketMessageInboundPool.sendMessage("前台顾客领取活动奖励！");
		return null;
	}
}
