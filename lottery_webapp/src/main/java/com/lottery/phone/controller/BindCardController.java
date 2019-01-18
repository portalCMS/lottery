package com.lottery.phone.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lottery.annotation.Token;
import com.lottery.bean.entity.BankManage;
import com.lottery.bean.entity.CustomerCash;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.UserCard;
import com.lottery.bean.entity.vo.CustomerOrderVO;
import com.lottery.bean.entity.vo.UserCardVO;
import com.lottery.service.IAdminParameterService;
import com.lottery.service.IBankManageService;
import com.lottery.service.ICustomerCashService;
import com.lottery.service.ICustomerOrderService;
import com.lottery.service.ICustomerUserService;
import com.lottery.service.IUserCardService;
import com.lottery.servlet.WebSocketMessageInboundPool;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.MD5Util;

@Controller
public class BindCardController {

	@Autowired
	private IUserCardService userCardService;
	
	@Autowired
	private IBankManageService bankManageService;
	
	@Autowired
	private ICustomerUserService userService;
	
	@Autowired
	private ICustomerCashService cashService;
	
	@Autowired
	private ICustomerOrderService orderService;
	
	@Autowired
	private IAdminParameterService parameterService;
	
	/**
	 * 用户银行卡绑定
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/showBindCards")
	public ModelAndView showBindCard(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(
				CommonUtil.CUSTOMERUSERKEY);
		param.put("userId", user.getId());
		param.put("operationType", CommonUtil.ORDER_DETAIL_USER_DRAWING);

		List<UserCard> cards = null;
		try {
			cards = userCardService.queryUserCardByUserId(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model, "用户银行表查询异常");
		}
		model.put("cards", cards);
		CustomerOrderVO orderVo = new CustomerOrderVO();
		orderVo.setOrderType(DataDictionaryUtil.ORDER_TYPE_OUT);
		orderVo.setOrderDetailType(DataDictionaryUtil.ORDER_DETAIL_WITHDRAWALS);
		orderVo.setCustomerId(user.getId());
		param.put("orderKey", orderVo);
		return new ModelAndView("jspmp/card/bind_list", model);
	}
	/**
	 * 确认绑定页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/bindCard")
	public ModelAndView bindCard(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(
				CommonUtil.CUSTOMERUSERKEY);
		param.put("userId", user.getId());
		param.put("operationType", CommonUtil.ORDER_DETAIL_USER_DRAWING);
		List<BankManage> banks = null;
		try {
			banks = bankManageService.findCanBindBankeList(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model, "银行表查询异常");
		}
		model.put("banks", banks);
		return new ModelAndView("jspmp/card/bind_card",model);
	}
	/**
	 * 确认绑定银行卡
	 * @param cardVo
	 * @param request
	 * @param reponse
	 * @param attributes
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/saveBindsCard")
	@ResponseBody
	public Map<String, ?> saveUserCard(UserCardVO cardVo,
			HttpServletRequest request, HttpServletResponse reponse,
			RedirectAttributes attributes) throws UnsupportedEncodingException {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();

		CustomerUser user = (CustomerUser) request.getSession().getAttribute(
				CommonUtil.CUSTOMERUSERKEY);
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		cardVo.setCustomerId(user.getId());
		param.put("userCard", cardVo);

		String pwd = MD5Util.makeMD5(cardVo.getMoneyPwd());
		if (!pwd.equals(user.getMoneyPwd())) {
			model.put("success", "zjerror");
			//return new ModelAndView("jspmp/card/bind_card",model);
			return model;
		}

		try {
			userService.saveUserCard(param);
		} catch (LotteryException e) {
			LotteryExceptionLog.wirteLog(e, attributes, e.getMessage());
			model.put("success", "error");
			//return new ModelAndView("redirect:showBindCards.html",model);
			return model;
		}catch (Exception e) {
			model.put("success", "error");
			//return new ModelAndView("redirect:showBindCards.html",model);
			return model;
		}
		model.put("success", "success");
		//return new ModelAndView("redirect:showBindCards.html", model);
		return model;
	}
	
	/**
	 * 银行卡充值
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("/showUserDrawing")
	@Token(needSaveToken = true)
	public ModelAndView showDrawing(HttpServletRequest request,
			HttpServletResponse reponse) {
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(
				CommonUtil.CUSTOMERUSERKEY);
		param.put("userId", user.getId());
		CustomerCash cash=null;
		try {
			cash = cashService.findCustomerCashByUserId(param);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		model.put("cash", cash);
		param.put("operationType", CommonUtil.ORDER_DETAIL_USER_DRAWING);
		List<UserCard> cards = null;
		try {
			cards = userCardService.queryUserCardByUserId(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model, "用户银行表查询异常");
		}
		model.put("cards", cards);

		CustomerOrderVO orderVo = new CustomerOrderVO();
		orderVo.setOrderType(DataDictionaryUtil.ORDER_TYPE_OUT);
		orderVo.setOrderDetailType(DataDictionaryUtil.ORDER_DETAIL_WITHDRAWALS);
		orderVo.setCustomerId(user.getId());
		param.put("orderKey", orderVo);

		int reminDrawingTimes = 0;
		try {
			reminDrawingTimes = orderService.countDrawingTimesToday(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model, "统计当天提款次数查询异常");
		}
		
		String[] keys = new String[] { "drawingMinAmount", "drawingMaxAmount","drawingStartTime","drawingEndTime"};

		param.put("parameterName", "financeConfig");
		param.put("parameterKeys", keys);

		Map<String, String> returnMap = null;
		try {
			returnMap = parameterService.getParameterList(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
			return new ModelAndView("user/adduser", model);
		}
		if (null != returnMap) {
			model.putAll(returnMap);
		}
		
		model.put("drawingTimes", reminDrawingTimes);
		return new ModelAndView("jspmp/card/user_card", model);
	}
	
	/**
	 * 确定提交提款申请
	 * @param orderVo
	 * @param request
	 * @param reponse
	 * @param attributes
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/saveDrawing")
	@ResponseBody
	public Map<String,?> submitDrawingRqeuest(CustomerOrderVO orderVo,
			HttpServletRequest request, HttpServletResponse reponse,
			RedirectAttributes attributes) throws UnsupportedEncodingException {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(
				CommonUtil.CUSTOMERUSERKEY);
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		param.put("orderKey", orderVo);
		String pwd = MD5Util.makeMD5(orderVo.getAdminPwd());
		if (!pwd.equals(user.getMoneyPwd())) {
			model.put("msg", "资金密码不正确!");
			return model;
			//return new ModelAndView("redirect:showDrawing.html");
		}

		try {
			orderService.saveDrawingRqeuest(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, attributes);
			model.put("msg", e.getMessage());
			return model;
			//return new ModelAndView("redirect:showDrawing.html");
		}
		model.put("msg", "恭喜您，提款申请已提交成功!");
		WebSocketMessageInboundPool.sendMessage("前台顾客充提！");
		//return new ModelAndView("redirect:showDrawing.html");
		return model;
	}
}
