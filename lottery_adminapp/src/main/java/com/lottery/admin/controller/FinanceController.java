package com.lottery.admin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.CustomerActivity;
import com.lottery.bean.entity.CustomerCash;
import com.lottery.bean.entity.CustomerMessage;
import com.lottery.bean.entity.CustomerOrder;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.AdminUserVO;
import com.lottery.bean.entity.vo.CustomerActivityVO;
import com.lottery.bean.entity.vo.CustomerIpLogVO;
import com.lottery.bean.entity.vo.CustomerMessageVO;
import com.lottery.bean.entity.vo.CustomerOrderVO;
import com.lottery.bean.entity.vo.CustomerUserVO;
import com.lottery.bean.entity.vo.TempMapVO;
import com.lottery.service.IAdminParameterService;
import com.lottery.service.ICustomerActivityService;
import com.lottery.service.ICustomerCashService;
import com.lottery.service.ICustomerMessageService;
import com.lottery.service.ICustomerOrderService;
import com.lottery.service.ICustomerUserService;
import com.xl.lottery.GrabNo.ClientFactory;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.JsonUtil;
import com.xl.lottery.util.MD5Util;
import com.xl.lottery.util.Util;

@Controller
public class FinanceController extends BaseController{
	@Autowired
	private ICustomerMessageService msgService;
	
	@Autowired
	private ICustomerUserService customerUserService;
	
	@Autowired
	private ICustomerOrderService customerOrderService;
	
	@Autowired
	private IAdminParameterService parameterService;
	
	@Autowired
	private ICustomerCashService customerCashService;

	@Autowired
	private ICustomerActivityService activityService;
	/**
	 * 显示财务管理页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/showFinanceOrders")
	public ModelAndView showFinanceOrders(HttpServletRequest request,
			HttpServletResponse response){
		return new ModelAndView("finance/finance_orders");
	}
	

	/**
	 * 显示财务报表页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/showStatistics")
	public ModelAndView showStatistics(HttpServletRequest request,
			HttpServletResponse response){
		return new ModelAndView("finance/statistics");
	}
	
	/**
	 * 财务配置初始化
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/initFinanceConfig")
	public ModelAndView initFinanceConfig(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();

	
		String[] keys = new String[]{"thirdPartyMinAmount","thirdPartyMaxAmount","thirdPartyTimes",
							"rechargeMinAmount","rechargeMaxAmount","rechargeTimes","rechargeStartTime","rechargeEndTime",
								"drawingMinAmount","drawingMaxAmount","drawingTimes","drawingStartTime","drawingEndTime",
								"payAccount","payUrl","ybAccount","ybUrl","defaultRecharge","rechargeCount","rechargeMoney","isOpenHx",
								"payAccountGroupTwo","payUrlGroupTwo","isOpenHxGroupTwo","ybAccountGroupTwo","ybUrlGroupTwo"};
		
		param.put("parameterName", "financeConfig");
		param.put("parameterKeys", keys);
		
		Map<String,String> returnMap=null;
		try {
			returnMap = parameterService.getParameterList(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e,model);
			return new ModelAndView("finance/config", model);
		}
		if(returnMap!=null){
			model.putAll(returnMap);
		}
		
		
		return new ModelAndView("finance/config", model);
	}

	/**
	 * 保存财务配置
	 * @param admin
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/saveFinanceConfig")
	public ModelAndView saveFinanceConfig(AdminUserVO admin,HttpServletRequest request,
			HttpServletResponse response,RedirectAttributes rAttributes) throws UnsupportedEncodingException {
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		
		ModelAndView returnView = this.checkRolePwdAndPiCode(request, model,request.getParameter("rolePwd"),
				"financeConfigkey", request.getParameter("picCode"),"redirect:initFinanceConfig.do");
		
		if(null!=returnView){
			rAttributes.addFlashAttribute("errorMsg", model.get("errorMsg"));
			returnView.getModel().remove("errorMsg");
			return returnView;
		}
		
		
		String[] keys = new String[]{"thirdPartyMinAmount","thirdPartyMaxAmount","thirdPartyTimes",
				"rechargeMinAmount","rechargeMaxAmount","rechargeTimes","rechargeStartTime","rechargeEndTime",
					"drawingMinAmount","drawingMaxAmount","drawingTimes","drawingStartTime","drawingEndTime",
					"payAccount","payUrl","ybAccount","ybUrl","defaultRecharge","rechargeCount","rechargeMoney","isOpenHx",
					"payAccountGroupTwo","payUrlGroupTwo","isOpenHxGroupTwo","ybAccountGroupTwo","ybUrlGroupTwo"};
		Map<String,String> keyValueMap = new HashMap<String, String>();
		for(String key : keys){
			keyValueMap.put(key, request.getParameter(key));
		}
		param.put("keyValueMap", keyValueMap);
		param.put("parameterName", "financeConfig");
		param.put(CommonUtil.USERKEY, (AdminUser)request.getSession().getAttribute(CommonUtil.USERKEY));
		try {
			parameterService.saveParameterValue(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, rAttributes);
			return new ModelAndView("redirect:users", model);
		}
			
		rAttributes.addFlashAttribute("success", "提交成功！");
		return new ModelAndView("redirect:initFinanceConfig.do", model);
	}
	
	@RequestMapping("showFinanceChare")
	public ModelAndView showFinanceChare(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("finance/charge");
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping("addSingleOrder")
	public ModelAndView addSingleOrder(CustomerOrderVO vo,HttpServletRequest request,HttpServletResponse response,RedirectAttributes rAttributes) throws UnsupportedEncodingException{
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put("customerOrder", vo);
		param.put(CommonUtil.USERKEY, user);
		CustomerUser entity = null;
		String userMoneyPwd = vo.getAdminPwd();
		if(!user.getUserRolePwd().equals(MD5Util.makeMD5(userMoneyPwd))){
			rAttributes.addFlashAttribute("errorMsg","资金密码不正确");
			return new ModelAndView("redirect:showFinanceChare.do",model); 
		}
		try {
			entity = customerUserService.checkCustomerUser(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, rAttributes);
		}
		if(entity==null){
			rAttributes.addFlashAttribute("errorMsg", "用户不存在，请确认用户名是否正确！");
			return new ModelAndView("redirect:showFinanceChare.do", model);
		}else{
			vo.setCustomerId(entity.getId());
		}
		try {
			boolean falg = customerOrderService.saveSignleRechargeOrDarwing(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, rAttributes);
			return new ModelAndView("redirect:showFinanceChare.do", model);
		}
		if(vo.getOrderDetailType()==DataDictionaryUtil.ORDER_DETAIL_BACKGROUND_ADD){
			rAttributes.addFlashAttribute("success", "单点后台充值操作成功！");
		}else if(vo.getOrderDetailType()==DataDictionaryUtil.ORDER_DETAIL_BACKGROUND_DEDUCTIONS){
			rAttributes.addFlashAttribute("success", "单点后台提款操作成功！");
		}
		
		
		return new ModelAndView("redirect:showFinanceChare.do", model);
	}
	
	@RequestMapping("checkCustomer")
	@ResponseBody
	public Map<String, ?> showCustomerUser(CustomerOrderVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		param.put("customerOrder", vo);
		CustomerUser entity = null;
		try {
			entity = customerUserService.checkCustomerUser(param);
			if(entity==null){
				result.put("entity", "");
			}else{
				param.put("userId", entity.getId());
				CustomerCash cash = customerCashService.findCustomerCashByUserId(param);
				result.put("entity", entity);
				result.put("cash", cash);
			}
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, result);
			result.put("entity", "");
			return result;
		}
		return result;
	}
	
	@RequestMapping("checkLowerCustomers")
	@ResponseBody
	public Map<String,?> checkLowerCustomers(CustomerUserVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			param.put("customeruservokey", vo);
			CustomerUser entity = customerUserService.checkLowerCustomers(param);
			param.put("userId", entity.getId());
			CustomerCash cash = customerCashService.findCustomerCashByUserId(param);
			model.put("cash", cash);
			model.put("lowerCustomer", entity);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e);
		}
		return model;
	}
	
	@RequestMapping("moveMoneyTOLowerCustomer")
	public ModelAndView moveMoneyTOLowerCustomer(CustomerUserVO vo,HttpServletRequest request,HttpServletResponse response,RedirectAttributes reAttributes) throws UnsupportedEncodingException{
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("customeruservokey", vo);
		AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put(CommonUtil.USERKEY, user);
		String userMoneyPwd = vo.getAdminPwd();
		if(!user.getUserRolePwd().equals(MD5Util.makeMD5(userMoneyPwd))){
			reAttributes.addFlashAttribute("errorMsg", "资金密码不正确");
			return new ModelAndView("finance/charge",model); 
		}
		try {
			String success = customerCashService.updateCustomerCashToLowerCustomerCash(param);
			reAttributes.addFlashAttribute(success, "操作成功");
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, reAttributes);
		}
		return new ModelAndView("redirect:showFinanceChare.do", model);
	}
	
	/**
	 * 查询用户充值记录
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("queryRechargeOrder")
	@ResponseBody
	public Map<String, ?> queryRechargeOrder(CustomerOrderVO vo,
				HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();	
		param.put("customerOrderKey", vo);
		
		Page<CustomerOrderVO, CustomerOrder> orderMaps=null;
		try {
			orderMaps = customerOrderService.updateQueryRechargeOrderByPage(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e,model);
			model.put("totalCount", 0);
			model.put("pageNum", 0);
			model.put("maxCount", 0);
			model.put("pageCount", 0);
			return model;
		}
		model.put("userOrderMaps", orderMaps);
		model.put("totalCount", orderMaps.getTotalCount());
		model.put("pageNum", orderMaps.getPageNum());
		model.put("maxCount", orderMaps.getMaxCount());
		model.put("pageCount", orderMaps.getPageCount());
		return model;
		
	}
	
	/**
	 * 查询用户提款记录
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("queryDrawingOrder")
	@ResponseBody
	public Map<String, ?> queryDrawingOrder(CustomerOrderVO vo,
				HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();	
		param.put("customerOrderKey", vo);
		
		Page<CustomerOrderVO, CustomerOrder> orderMaps=null;
		try {
			orderMaps = customerOrderService.updateQueryRechargeOrderByPage(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e,model);
			model.put("totalCount", 0);
			model.put("pageNum", 0);
			model.put("maxCount", 0);
			model.put("pageCount", 0);
			return model;
		}
		model.put("userOrderMaps", orderMaps);
		model.put("totalCount", orderMaps.getTotalCount());
		model.put("pageNum", orderMaps.getPageNum());
		model.put("maxCount", orderMaps.getMaxCount());
		model.put("pageCount", orderMaps.getPageCount());
		return model;
		
	}
	
	/**
	 * 查询活动领取记录
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("queryActivityOrder")
	@ResponseBody
	public Map<String, ?> queryActivityOrder(CustomerOrderVO vo,
				HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();	
		param.put("customerOrderKey", vo);
		Page<CustomerOrderVO, CustomerOrder> orderMaps=null;
		try {
			orderMaps = customerOrderService.queryActiveOrder(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e,model);
			model.put("totalCount", 0);
			model.put("pageNum", 0);
			model.put("maxCount", 0);
			model.put("pageCount", 0);
			return model;
		}
		model.put("userOrderMaps", orderMaps);
		model.put("totalCount", orderMaps.getTotalCount());
		model.put("pageNum", orderMaps.getPageNum());
		model.put("maxCount", orderMaps.getMaxCount());
		model.put("pageCount", orderMaps.getPageCount());
		return model;
		
	}
	
	/**
	 * 查看充值订单详情
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("showRechargeOrderInfo")
	public ModelAndView showRechargeOrderInfo(CustomerOrderVO vo,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderKey", vo);
		CustomerOrder order =null;
		try {
			order = customerOrderService.showRechargeOrderInfo(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
			return new ModelAndView("finance/finance_orders", model);
		}
		model.put("order", order);
		return new ModelAndView("finance/rechargeOrderInfo", model);
	}
	
	/**
	 * 通过审核充值订单，默认手续费看数据库的参数表配置（默认0.1）
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("approveRechargeOrder")
	@ResponseBody
	public Map<String, ?> approveRechargeOrder(CustomerOrderVO vo,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		
		AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put(CommonUtil.USERKEY, user);
		param.put("orderKey", vo);
		CustomerOrder order = null;
		try {
			order = customerOrderService.saveApproveRechargeOrder(param);
			//站内信
			CustomerMessageVO msgVo = new CustomerMessageVO();
			msgVo.setToUserId(order.getCustomerId());
			msgVo.setTitle("充值成功");
			String msg = "亲，您的充值申请["+order.getOrderNumber()+"]已经通过审核，请查看账户余额是否已经增加 "
					 + order.getReceiveAmount()+" 元。 \n如有任何问题，可咨询客服，祝您游戏愉快！";
			msgVo.setMessage(msg);
			msgVo.setMsgType("0");
			param.put("msgVO", msgVo);
			msgService.saveMsg(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		model.put("order", order);
		return  model;
	}
	
	/**
	 * 订单明细页面通过审核充值订单，默认手续费看数据库的参数表配置（默认0.1）
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("approveRechargeOrder2")
	public ModelAndView approveRechargeOrder2(CustomerOrderVO vo,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		Map<String, Object> model = (Map<String, Object>) this.approveRechargeOrder(vo, request, response);
		CustomerOrder order = (CustomerOrder) model.get("order");
		model.put("id", order.getId());
		return  new ModelAndView("redirect:showRechargeOrderInfo.do",model);
	}
	/**
	 * 驳回审核充值订单
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("rejectRechargeOrder")
	@ResponseBody
	public Map<String, ?>  rejectRechargeOrder(CustomerOrderVO vo,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put(CommonUtil.USERKEY, user);
		param.put("orderKey", vo);
		CustomerOrder order = null;
		try {
			order = customerOrderService.saveRejectRechargeOrder(param);
			//站内信
			CustomerMessageVO msgVo = new CustomerMessageVO();
			msgVo.setToUserId(order.getCustomerId());
			msgVo.setTitle("充值失败");
			String msg = "亲，您的充值申请["+order.getOrderNumber()+"]未通过审核，原因是:\""+order.getRsvst4()+"\"。"
					+ "\n如有任何问题，可咨询客服，祝您游戏愉快！";
			msgVo.setMessage(msg);
			msgVo.setMsgType("0");
			param.put("msgVO", msgVo);
			msgService.saveMsg(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		model.put("order", order);
		return  model;
	}
	
	/**
	 * 订单明细页面驳回审核充值订单
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("rejectRechargeOrder2")
	public ModelAndView  rejectRechargeOrder2(CustomerOrderVO vo,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		Map<String,Object> model = (Map<String, Object>) this.rejectRechargeOrder(vo, request, response);
		CustomerOrder order =(CustomerOrder) model.get("order");
		model.put("id", order.getId());
		return  new ModelAndView("redirect:showRechargeOrderInfo.do",model);
	}
	/**
	 * 过期订单重启（订单状态设置为处理中，过期时间调整为当前时间加上配置的过期时间）
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("restartOrder")
	public ModelAndView  restartOrder(CustomerOrderVO vo,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put(CommonUtil.USERKEY, user);
		param.put("orderKey", vo);
		CustomerOrder order=null;
		try {
			order = customerOrderService.updateRestartOrder(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		model.put("id", order.getId());
		return  new ModelAndView("redirect:showRechargeOrderInfo.do",model);
	}
	/**
	 * 查看提款订单详情
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("showDrawingOrderInfo")
	public ModelAndView showDrawingOrderInfo(CustomerOrderVO vo,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderKey", vo);
		Map<String,Object> returnMap = null;
		try {
			returnMap = customerOrderService.showDrawingOrderInfo(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
			return new ModelAndView("finance/finance_orders", model);
		}
		
		for(Entry entry : returnMap.entrySet()){
			model.put((String) entry.getKey(),entry.getValue());
		}
		
		return new ModelAndView("finance/drawing_order", model);
	}
	
	/**
	 * 通过审核提款订单
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("approveDrawingOrder")
	public ModelAndView approveDrawingOrder(CustomerOrderVO vo,HttpServletRequest request,HttpServletResponse response,RedirectAttributes rAttributes){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		
		AdminUser admin  = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		try {
//			//验证财务密码和验证码是否正确
//			ModelAndView returnView = this.checkRolePwdAndPiCode(request, model, vo.getAdminPwd(),"drawingOrderkey", 
//					vo.getPicCode(),"redirect:showDrawingOrderInfo.do?id="+vo.getId());
//			if(null!=returnView){
//				rAttributes.addFlashAttribute("errorMsg", model.get("errorMsg"));
//				returnView.getModel().remove("errorMsg");
//				return returnView;
//			}
			
			param.put(CommonUtil.USERKEY, admin);
			param.put("orderKey", vo);
			CustomerOrder order = null;
			order = customerOrderService.saveApproveDrawingOrder(param);
			model.put("id", order.getId());
			
			//站内信
			CustomerMessageVO msgVo = new CustomerMessageVO();
			msgVo.setToUserId(order.getCustomerId());
			msgVo.setTitle("提款成功");
			String msg = "亲，您的提款申请["+order.getOrderNumber()+"]已经通过审核，请查看您的银行账户是否已经收到款项  "
					 + order.getReceiveAmount()+" 元。 \n如有任何问题，可咨询客服，祝您游戏愉快！";
			msgVo.setMessage(msg);
			msgVo.setMsgType("0");
			param.put("msgVO", msgVo);
			msgService.saveMsg(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, rAttributes);
		}
		return new ModelAndView("redirect:showDrawingOrderInfo.do", model);
	}
	
	/**
	 * 驳回审核充值订单
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("rejectDrawingOrder")
	public ModelAndView  rejectDrawingOrder(CustomerOrderVO vo,HttpServletRequest request,HttpServletResponse response,RedirectAttributes rAttributes){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			AdminUser admin  = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
//			//验证财务密码和验证码是否正确
//			ModelAndView returnView = this.checkRolePwdAndPiCode(request, model,vo.getAdminPwd(), "drawingOrderkey", 
//					vo.getPicCode(),"redirect:showDrawingOrderInfo.do?id="+vo.getId());
//			if(null!=returnView){
//				rAttributes.addFlashAttribute("errorMsg", model.get("errorMsg"));
//				returnView.getModel().remove("errorMsg");
//				return returnView;
//			}
			
			param.put(CommonUtil.USERKEY, admin);
			param.put("orderKey", vo);
			CustomerOrder order = null;
			order = customerOrderService.saveRejectDrawingOrder(param);
			model.put("id", order.getId());
			
			//站内信
			CustomerMessageVO msgVo = new CustomerMessageVO();
			msgVo.setToUserId(order.getCustomerId());
			msgVo.setTitle("提款失败");
			String msg = "亲，您的提款申请["+order.getOrderNumber()+"]未通过审核，原因是：\""+order.getRsvst4()+"\"。"
					+ "\n如有任何问题，可咨询客服，祝您游戏愉快！";
			msgVo.setMessage(msg);
			msgVo.setMsgType("0");
			param.put("msgVO", msgVo);
			msgService.saveMsg(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, rAttributes);
		}
		
		return new ModelAndView("redirect:showDrawingOrderInfo.do", model);
	}
	
	
	/**
	 * 统计处理中的充值/提款订单数量
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("countDisposingOrder")
	@ResponseBody
	public Map<String, ?>  countDisposingOrder(CustomerOrderVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put(CommonUtil.USERKEY, user);
		param.put("orderKey", vo);
		int count = 0;
		try {
			count = customerOrderService.countDisposingOrder(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		model.put("count", count);
		return  model;
	}
	
	/**
	 * 统计处理中的充值/提款订单数量
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("chargeDrawCount")
	@ResponseBody
	public Map<String, ?>  chargeDrawCount(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put(CommonUtil.USERKEY, user);
		try {
			model = customerOrderService.chargeDrawCount(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return  model;
	}
	
	
	/**
	 * 通过审核活动领取订单
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("approveActivityOrder")
	@ResponseBody
	public Map<String, ?> approveActivityOrder(CustomerOrderVO vo,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		
		AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put(CommonUtil.USERKEY, user);
		param.put("orderKey", vo);
		CustomerOrder order = null;
		try {
			order = customerOrderService.saveApproveActivityOrder(param);
			
			//站内信
			CustomerMessageVO msgVo = new CustomerMessageVO();
			msgVo.setToUserId(order.getCustomerId());
			msgVo.setTitle("活动奖励领取成功");
			String msg = "亲，您的活动领取奖励申请["+order.getOrderNumber()+"]已经通过审核，请查看账户余额是否已经增加 "
					 + order.getReceiveAmount()+" 元。 \n如有任何问题，可咨询客服，祝您游戏愉快！";
			msgVo.setMessage(msg);
			msgVo.setMsgType("0");
			param.put("msgVO", msgVo);
			msgService.saveMsg(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		model.put("order", order);
		return  model;
	}
	/**
	 * 驳回审核活动领取订单
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("rejectActivityOrder")
	@ResponseBody
	public Map<String, ?> rejectActivityOrder(CustomerOrderVO vo,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		
		AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put(CommonUtil.USERKEY, user);
		param.put("orderKey", vo);
		CustomerOrder order = null;
		try {
			order = customerOrderService.saveRejectActivityOrder(param);
			
			//站内信
			CustomerMessageVO msgVo = new CustomerMessageVO();
			msgVo.setToUserId(order.getCustomerId());
			msgVo.setTitle("活动奖励领取失败");
			String msg = "亲，您的活动领取奖励申请["+order.getOrderNumber()+"]被驳回，原因是： "+vo.getRsvst5()+". \n如有任何问题，可咨询客服，祝您游戏愉快！";
			msgVo.setMessage(msg);
			msgVo.setMsgType("0");
			param.put("msgVO", msgVo);
			msgService.saveMsg(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		model.put("order", order);
		return  model;
	}
	
	/**
	 * 驳回审核活动领取订单
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("showActivityOrder")
	public ModelAndView showActivityOrder(CustomerActivityVO vo,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		
		AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put(CommonUtil.USERKEY, user);
		param.put("activityKey", vo);
		
		CustomerActivity activity = null;
		try {
			activity = activityService.showActivityOrder(param);
			model.put("activity", activity);
			
			//ordIdKey
			param = new HashMap<String, Object>();
			param.put("ordIdKey", vo.getCustomerId());
			CustomerOrder resultTemp= customerOrderService.queryResultOrderById(param);
			model.put("customerOrder", resultTemp);
			
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		
		return  new ModelAndView("finance/activityOrderInfo",model);
	}
	
	@RequestMapping("refreshResult")
	@ResponseBody
	public Map<String, Object> refreshResult(CustomerIpLogVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		
		try {
			String links = vo.getIpAddress()+"/activity/refreshResult.shtml";
			links = "http"+links.substring(links.indexOf(":"));
			TempMapVO mapVo = new TempMapVO();
			mapVo.setKey("userName");
			mapVo.setValue(vo.getUname());
			String params = JsonUtil.objToJson(mapVo);
			params = org.apache.commons.codec.binary.Base64.encodeBase64String(params.getBytes());
			//将最新的开奖结果发送到前台的map中
			String jsonStr="";  
			for(int i=0;i<5;i++){
				try {
					jsonStr = ClientFactory.getUrl(links,"msg="+params);
				} catch (Exception e) {
					continue;
				}
				
				break;
			}
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
}
