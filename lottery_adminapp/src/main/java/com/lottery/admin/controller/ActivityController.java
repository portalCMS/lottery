package com.lottery.admin.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.CustomerActivity;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.ClassSortVO;
import com.lottery.bean.entity.vo.CustomerActivityVO;
import com.lottery.service.IClassSortService;
import com.lottery.service.ICustomerActivityService;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.JsonUtil;
import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.vo.CustomerActivityVO;
import com.lottery.dao.ICustomerActivityDao;
import com.lottery.service.ICustomerActivityService;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;


@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController {
	
	@Autowired
	private ICustomerActivityService activityService;
	
	@Autowired
	private IClassSortService sortService;
	
	@RequestMapping("/showRegTempl")
	public ModelAndView showActivityTempl(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("activity/registerTempl");
	}
	
	/**
	 * 修改页面
	 * george
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/updateRegTempl")
	public ModelAndView updateActivityTempl(HttpServletRequest request, HttpServletResponse response,CustomerActivityVO po){
		Map<String,Object> model =new HashMap<String, Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		try {
			CustomerActivityVO vo = activityService.queryCustomerActivityById(po.getId());
			if(po.getType().equals("NO")){
				vo.setType("NO");
			}
			Map<String,Object> mapstr=(Map<String,Object>) JSONObject.parse(vo.getRule());
			model.put("vo", vo);
			model.put("map", mapstr);
		} catch (Exception e) {
			// TODO: handle exception
			LotteryExceptionLog.wirteLog(e, model);
		}
		
		return new ModelAndView("activity/registerUpdateTempl",model);
	}
	
	
	/**
	 * 保存修改
	 * george
	 * @param actVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/addUpeRegTempl")
	@ResponseBody
	public Map<String,Object> addUpeRegTempl(CustomerActivityVO actVo,HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> param = new HashMap<String,Object>();
		Map<String,Object> model = new HashMap<String,Object>();
		AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put("activityKey", actVo);
		param.put(CommonUtil.USERKEY, user);
		//金额配置比例和金额配置区域只能二选一，不可以同时配置。
		try{
			if(StringUtils.isEmpty(actVo.getStarttime())||StringUtils.isEmpty(actVo.getEndtime())){
				throw new LotteryException("活动开始时间和截止时间都不能为空!");
			}
			activityService.updateRegActivity(param);
			model.put("success", "活动修改成功！");
		}catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		
		return model;
	}
	
	/**
	 * 首冲模版修改页面
	 * george
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/updatefrecTempl")
	public ModelAndView updatefrecTempl(HttpServletRequest request, HttpServletResponse response,CustomerActivityVO po){
		Map<String,Object> model =new HashMap<String, Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		try {
			CustomerActivityVO vo = activityService.queryCustomerActivityById(po.getId());
			Map<String,Object> mapstr=(Map<String,Object>) JSONObject.parse(vo.getRule());
			if(po.getType().equals("NO")){
				vo.setType("NO");
			}
			model.put("vo", vo);
			model.put("map", mapstr);
		} catch (Exception e) {
			// TODO: handle exception
			LotteryExceptionLog.wirteLog(e, model);
		}
		
		return new ModelAndView("activity/frcTemplUp",model);
	}
	
	
	/**
	 * 首冲模版保存修改
	 * george
	 * @param actVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/addUpefrecTempl")
	@ResponseBody
	public Map<String,Object> addUpefrecTempl(CustomerActivityVO actVo,HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> param = new HashMap<String,Object>();
		Map<String,Object> model = new HashMap<String,Object>();
		AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put("activityKey", actVo);
		param.put(CommonUtil.USERKEY, user);
		//金额配置比例和金额配置区域只能二选一，不可以同时配置。
		try{
			if(StringUtils.isEmpty(actVo.getStarttime())||StringUtils.isEmpty(actVo.getEndtime())){
				throw new LotteryException("活动开始时间和截止时间都不能为空!");
			}
			activityService.updateFrcActivity(param);
			model.put("success", "活动修改成功！");
		}catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		
		return model;
	}
	
	/**
	 * 游戏活动模版
	 * george
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/updateBetTempl")
	public ModelAndView showUpBetTempl(HttpServletRequest request, HttpServletResponse response,CustomerActivityVO po){
		Map<String,Object> model =new HashMap<String, Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		try {
			CustomerActivityVO vo = activityService.queryCustomerActivityById(po.getId());
			Map<String,Object> mapstr=(Map<String,Object>) JSONObject.parseObject(vo.getRule());
			if(po.getType().equals("NO")){
				vo.setType("NO");
			}
			model.put("vo", vo);
			model.put("map", mapstr);
		} catch (Exception e) {
			// TODO: handle exception
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("activity/betTemplUp",model);
	}
	
	/**
	 * 游戏模版保存
	 * george
	 * @param actVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/addBetTempl")
	@ResponseBody
	public Map<String,Object> addBetTempl(CustomerActivityVO actVo,HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> param = new HashMap<String,Object>();
		Map<String,Object> model = new HashMap<String,Object>();
		AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put("activityKey", actVo);
		param.put(CommonUtil.USERKEY, user);
		//金额配置比例和金额配置区域只能二选一，不可以同时配置。
		try{
			if(StringUtils.isEmpty(actVo.getStarttime())||StringUtils.isEmpty(actVo.getEndtime())){
				throw new LotteryException("活动开始时间和截止时间都不能为空!");
			}
			
			if((actVo.getBetTempl().getRateAmount()==null&&actVo.getBetTempl().getAmountConfig()==null)
					||(actVo.getBetTempl().getRateAmount()!=null&&actVo.getBetTempl().getAmountConfig()!=null)){
				throw new LotteryException("金额配置比例，或金额配置区域只能二选一!");
			}
			activityService.updatesBetActivity(param);
			model.put("success", "活动修改成功！");
		}catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		
		return model;
	}
	
/**
	 * 首充活动模板
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/frecActivity")
	public ModelAndView showFirstRechargeActivity(HttpServletRequest request, HttpServletResponse response){
		//Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("activity/frcTempl",model);
	}
	
	@RequestMapping("/showActivityList")
	public ModelAndView showActivityList(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("activity/activityList");
	}
	@RequestMapping("/showBetTempl")
	public ModelAndView showBetTempl(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("activity/betTempl");
	}
	@RequestMapping("/showLuckdrawTempl")
	public ModelAndView showLuckdrawTempl(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("activity/luckdrawTempl");
	}
	@RequestMapping("/showHelpTempl")
	public ModelAndView showHelpTempl(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("activity/helpTempl");
	}
	@RequestMapping("/showDeadTempl")
	public ModelAndView showDeadTempl(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("activity/deadTempl");
	}
	
	
	@RequestMapping("/createBetTempl")
	@ResponseBody
	public Map<String,Object> createBetTempl(CustomerActivityVO actVo,HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> param = new HashMap<String,Object>();
		Map<String,Object> model = new HashMap<String,Object>();
		AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put("activityKey", actVo);
		param.put(CommonUtil.USERKEY, user);
		//金额配置比例和金额配置区域只能二选一，不可以同时配置。
		try{
			if(StringUtils.isEmpty(actVo.getStarttime())||StringUtils.isEmpty(actVo.getEndtime())){
				throw new LotteryException("活动开始时间和截止时间都不能为空!");
			}
			
			if((actVo.getBetTempl().getRateAmount()==null&&actVo.getBetTempl().getAmountConfig()==null)
					||(actVo.getBetTempl().getRateAmount()!=null&&actVo.getBetTempl().getAmountConfig()!=null)){
				throw new LotteryException("金额配置比例，或金额配置区域只能二选一!");
			}
			activityService.saveBetActivity(param);
			model.put("success", "活动创建成功！");
		}catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		
		return model;
	}
	
	@RequestMapping("/createRegTempl")
	@ResponseBody
	public Map<String,Object> createRegTempl(CustomerActivityVO actVo,HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> param = new HashMap<String,Object>();
		Map<String,Object> model = new HashMap<String,Object>();
		AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put("activityKey", actVo);
		param.put(CommonUtil.USERKEY, user);
		//金额配置比例和金额配置区域只能二选一，不可以同时配置。
		try{
			if(StringUtils.isEmpty(actVo.getStarttime())||StringUtils.isEmpty(actVo.getEndtime())){
				throw new LotteryException("活动开始时间和截止时间都不能为空!");
			}
			activityService.saveRegActivity(param);
			model.put("success", "活动创建成功！");
		}catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		
		return model;
	}
	
	@RequestMapping("/createFrcTempl")
	@ResponseBody
	public Map<String,Object> createFrcTempl(CustomerActivityVO actVo,HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> param = new HashMap<String,Object>();
		Map<String,Object> model = new HashMap<String,Object>();
		AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put("activityKey", actVo);
		param.put(CommonUtil.USERKEY, user);
		//金额配置比例和金额配置区域只能二选一，不可以同时配置。
		try{
			if(StringUtils.isEmpty(actVo.getStarttime())||StringUtils.isEmpty(actVo.getEndtime())){
				throw new LotteryException("活动开始时间和截止时间都不能为空!");
			}
			activityService.saveFrcActivity(param);
			model.put("success", "活动创建成功！");
		}catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		
		return model;
	}
	
	@RequestMapping("/createLuckTempl")
	@ResponseBody
	public Map<String,Object> createLuckTempl(CustomerActivityVO actVo,HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> param = new HashMap<String,Object>();
		Map<String,Object> model = new HashMap<String,Object>();
		AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put("activityKey", actVo);
		param.put(CommonUtil.USERKEY, user);
		try{
			if(StringUtils.isEmpty(actVo.getStarttime())||StringUtils.isEmpty(actVo.getEndtime())){
				throw new LotteryException("活动开始时间和截止时间都不能为空!");
			}
			//预热时间为空默认为开始时间
			if(StringUtils.isEmpty(actVo.getPretime())){
				actVo.setPretime(actVo.getStarttime());
			}
			//关闭时间为空默认为结束时间
			if(StringUtils.isEmpty(actVo.getClosetime())){
				actVo.setClosetime(actVo.getEndtime());
			}
			
			
//			if((actVo.getBetTempl().getRateAmount()==null&&actVo.getBetTempl().getAmountConfig()==null)
//					||(actVo.getBetTempl().getRateAmount()!=null&&actVo.getBetTempl().getAmountConfig()!=null)){
//				throw new LotteryException("金额配置比例，或金额配置区域只能二选一!");
//			}
			activityService.saveLuckActivity(param);
			model.put("success", "活动创建成功！");
		}catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		
		return model;
	}
	
	
	@RequestMapping("/queryActivity")
	@ResponseBody
	public Map<String, Object> queryActivity(CustomerActivityVO actVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("activityVO", actVo);
		try {
			Page<CustomerActivityVO, CustomerActivity> page = activityService.queryActivitys(param);
			model.put("page", page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
}
