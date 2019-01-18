package com.lottery.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
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
import com.lottery.annotation.TokenProcess;
import com.lottery.bean.entity.BankManage;
import com.lottery.bean.entity.CustomerBankCard;
import com.lottery.bean.entity.CustomerBindCard;
import com.lottery.bean.entity.CustomerIpLog;
import com.lottery.bean.entity.CustomerOrder;
import com.lottery.bean.entity.CustomerQuota;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.SecurityQuestion;
import com.lottery.bean.entity.UserCard;
import com.lottery.bean.entity.vo.BankManageVO;
import com.lottery.bean.entity.vo.CustomerCashVO;
import com.lottery.bean.entity.vo.CustomerIpLogVO;
import com.lottery.bean.entity.vo.CustomerMessageVO;
import com.lottery.bean.entity.vo.CustomerOrderVO;
import com.lottery.bean.entity.vo.CustomerQuotaVO;
import com.lottery.bean.entity.vo.CustomerUserVO;
import com.lottery.bean.entity.vo.GenericEntityVO;
import com.lottery.bean.entity.vo.OrtherPayVO;
import com.lottery.bean.entity.vo.OrtherYBPayVO;
import com.lottery.bean.entity.vo.SecurityQuestionVO;
import com.lottery.bean.entity.vo.UserCardVO;
import com.lottery.ip.util.Utils;
import com.lottery.service.IAdminParameterService;
import com.lottery.service.IBankManageService;
import com.lottery.service.ICustomerBindCardService;
import com.lottery.service.ICustomerCashService;
import com.lottery.service.ICustomerIpLogService;
import com.lottery.service.ICustomerOrderService;
import com.lottery.service.ICustomerQuotaService;
import com.lottery.service.ICustomerUserService;
import com.lottery.service.ISecurityQuestionService;
import com.lottery.service.IUserCardService;
import com.lottery.servlet.WebSocketMessageInboundPool;
import com.xl.lottery.desutil.AesUtil;
import com.xl.lottery.desutil.Md5Manage;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.MD5Util;
import com.xl.lottery.util.RandDomUtil;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	private ICustomerUserService userService;

	@Autowired
	private ICustomerBindCardService bindCardService;

	@Autowired
	private IUserCardService userCardService;

	@Autowired
	private IBankManageService bankManageService;

	@Autowired
	private ICustomerOrderService orderService;

	@Autowired
	private ICustomerQuotaService customerQuotaService;

	@Autowired
	private ICustomerCashService customerCash;

	@Autowired
	private ISecurityQuestionService questionService;

	@Autowired
	private IAdminParameterService parameterService;
	
	@Autowired
	private ICustomerIpLogService ipService;

	@RequestMapping("/saveLoginPwd")
	@Token(needRemoveToken = true)
	public ModelAndView saveLoginPwd(CustomerUserVO customerVo,
			HttpServletRequest request, HttpServletResponse reponse,
			RedirectAttributes rAttributes) throws UnsupportedEncodingException {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		customerVo.setId(user.getId());
		param.put("customeruserkey", customerVo);
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		CustomerUser modifyUser = null;
		try {
			modifyUser = userService.saveCustomerUserPwd(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, rAttributes);
			return new ModelAndView("redirect:showInitLoginPwd.html"); 
		}
		modifyUser.setIp(Utils.getIpAddr(request));
		request.getSession().removeAttribute(CommonUtil.CUSTOMERUSERKEY);
		request.getSession().setAttribute(CommonUtil.CUSTOMERUSERKEY,modifyUser);
		return new ModelAndView("redirect:showInitMoneyPwd.html"); 
	}

	@RequestMapping("/showInitLoginPwd")
	@Token(needSaveToken=true)
	public ModelAndView showInitLoginPwd(HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("register/change_pwd",model); 
	}

	@RequestMapping("/saveMoneyPwd")
	@Token(needRemoveToken=true)
	public ModelAndView saveMoneyPwd(CustomerUserVO customerVo,
			HttpServletRequest request,HttpServletResponse reponse,RedirectAttributes rAttributes) 
					throws UnsupportedEncodingException{
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		if(user.getIp()==null)user.setIp(Utils.getIpAddr(request));
		customerVo.setId(user.getId());
		param.put("customeruserkey", customerVo);
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		CustomerUser modifyUser = null;
		try {
			modifyUser = userService.saveCustomerUserMoneyPwd(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, rAttributes);
			return new ModelAndView("redirect:showSaveMoneyPwd.html", model);
		}
		modifyUser.setIp(user.getIp());
		request.getSession().removeAttribute(CommonUtil.CUSTOMERUSERKEY);
		request.getSession().setAttribute(CommonUtil.CUSTOMERUSERKEY,
				modifyUser);
		return new ModelAndView("redirect:showPersonInfo.html", model);
	}

	@RequestMapping("/showInitMoneyPwd")
	@Token(needSaveToken=true)
	public ModelAndView showInitMoneyPwd(HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> model = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(
				CommonUtil.CUSTOMERUSERKEY);
		model.put("userId", user.getId());
		return new ModelAndView("register/account_security", model);
	}

	@RequestMapping("/savePersonInfo")
	@Token(needRemoveToken = true)
	public ModelAndView savePersonInfo(CustomerUserVO customerVo,
			HttpServletRequest request, HttpServletResponse reponse,
			RedirectAttributes rAttributes) throws UnsupportedEncodingException {
		Map<String, Object> param = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		customerVo.setId(user.getId());
		param.put("customeruserkey", customerVo);
		param.put("uservokey", customerVo);
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		CustomerUser modifyUser = null;
		try {
			String flag = userService.checkAlias(param);
			if(flag.equals("false")){
				throw new LotteryException("昵称已经被占用");
			}
			modifyUser = userService.savePersonInfo(param);
			modifyUser.setIp(Utils.getIpAddr(request));
			request.getSession().removeAttribute(CommonUtil.CUSTOMERUSERKEY);
			request.getSession().setAttribute(CommonUtil.CUSTOMERUSERKEY,modifyUser);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, rAttributes);
			return new ModelAndView("redirect:showPersonInfo.html");
		}

		return new ModelAndView("redirect:showFinishedPage.html");
	}

	@RequestMapping("/showPersonInfo")
	@Token(needSaveToken = true)
	public ModelAndView showPersonInfo(HttpServletRequest request,
			HttpServletResponse reponse) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userId", request.getParameter("userId"));
		return new ModelAndView("register/account_info", model);
	}

	@RequestMapping("/showFinishedPage")
//	@Token(needSaveToken = true)
	public ModelAndView showFinishedPage(HttpServletRequest request,
			HttpServletResponse reponse) {
		return new ModelAndView("register/done");
	}

	@RequestMapping("/showUserRecharge")
	@Token(needSaveToken = true)
	public ModelAndView showUserRecharge(HttpServletRequest request,
			HttpServletResponse reponse, RedirectAttributes rAttributes) {
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(
				CommonUtil.CUSTOMERUSERKEY);
		param.put("userId", user.getId());
		param.put("operationType", CommonUtil.ORDER_DETAIL_USER_RECHARGE);
		List<UserCard> bindCardList = null;
		try {
			bindCardList = userCardService.queryUserCardByUserId(param);
			model.put("userBindCards", bindCardList);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
			model.put("errorMessage", "查询用户绑定的银行卡异常");
		}

		CustomerOrderVO orderVo = new CustomerOrderVO();
		orderVo.setOrderType(DataDictionaryUtil.ORDER_TYPE_INCOME);
		orderVo.setOrderDetailType(DataDictionaryUtil.ORDER_DETAIL_CASH_ADD);
		orderVo.setCustomerId(user.getId());
		param.put("orderKey", orderVo);

		int reminRechargeTimes = 0;
		try {
			reminRechargeTimes = orderService.countRechargeTimesToday(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
			model.put("errorMsg", "统计当天提款次数查询异常");
		}

		String[] keys = new String[] { "rechargeMinAmount", "rechargeMaxAmount","rechargeStartTime","rechargeEndTime",
										"thirdPartyMinAmount","thirdPartyMaxAmount","thirdPartyTimes","defaultRecharge","isOpenHx"
										,"rechargeCount","rechargeMoney"};

		param.put("parameterName", "financeConfig");
		param.put("parameterKeys", keys);

		Map<String, String> returnMap = null;
		try {
			returnMap = parameterService.getParameterList(param);
			param.put(CommonUtil.CUSTOMERUSERKEY, user);
			param.put("rechargeCount", returnMap.get("rechargeCount"));
			param.put("rechargeMoney", returnMap.get("rechargeMoney"));
			boolean flag = orderService.checkCustomerIsSafe(param);
			if(!flag)model.put("nosafe", "nosafe");
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
			return new ModelAndView("user/adduser", model);
		}
		if (null != returnMap) {
			model.putAll(returnMap);
		}

		model.put("rechargeTimes", reminRechargeTimes);
		return new ModelAndView("profile/recharge", model);
	}

	/**
	 * 获取bank的帮助信息，下拉选中就触发这个ajax请求。
	 * 
	 * @param bankVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getBankDescripe")
	@ResponseBody
	public Map<String, String> getBankDescripe(UserCardVO cardVo,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, String> model = new HashMap<String, String>();

		BankManageVO bankVo = new BankManageVO();
		bankVo.setId(cardVo.getBankId());
		param.put("bankmanagekey", bankVo);
		BankManage bank = null;
		try {
			bank = bankManageService.findBankById(param);
			model.put("description", bank.getDescribe());
			return model;
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e);
		}
		return model;
	}

	@RequestMapping("saveUserRecharge")
	@Token(needRemoveToken = true)
	@ResponseBody
	public Map<String, ?> saveUserRecharge(CustomerOrderVO orderVo,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		if(null!=request.getAttribute("tokenError")){
			model.put("errorMsg", request.getAttribute("tokenError"));
			return model;
		}

		CustomerUser user = (CustomerUser) request.getSession().getAttribute(
				CommonUtil.CUSTOMERUSERKEY);
		orderVo.setId(user.getId());
		param.put("orderkey", orderVo);
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		// 重新生成token替换页面token
		request.getSession().setAttribute("token", TokenProcess.generateGUID());
		model.put("token", request.getSession().getAttribute("token"));
		try {
			orderService.saveUserRecharge(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e,model);
			return model;
		}
		model.put("saveSuccess", "success");
		model.put("success", "亲，您的充值申请已提交成功，请点击页面银行链接进行充值！");
		WebSocketMessageInboundPool.sendMessage("前台顾客充提！");
		return model;
	}

	@RequestMapping("/userRechargeConfirm")
	@Token(needSaveToken = true)
	public ModelAndView userRechargeConfirm(UserCardVO cardVo,
			HttpServletRequest request, HttpServletResponse reponse,
			RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();

		CustomerUser user = (CustomerUser) request.getSession().getAttribute(
				CommonUtil.CUSTOMERUSERKEY);
		param.put("userId", user.getId());
		param.put("cardVoKey", cardVo);
		List<CustomerBindCard> bindCards = null;
		try {
			//查询用户等级对应的n个系统转入卡，然后随机取得一张卡(返回的bindCards的size=1)。
			bindCards = bindCardService.queryBindCardById(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, redirectAttributes);
			return new ModelAndView("redirect:showUserRecharge.html");
		}
		if (bindCards.size() == 0) {
			redirectAttributes.addFlashAttribute("errorMsg",
					"用户没有分配到能够充值的系统给予的转入卡!");
			return new ModelAndView("redirect:showUserRecharge.html");
		}
		
		CustomerBankCard card =  bindCards.get(0).getBankCard();
		model.put("bindCard", bindCards.get(0));
		//来源卡信息从vo中获取
		model.put("cardId", cardVo.getId());
		model.put("bankName", cardVo.getBranchBankName());
		model.put("cardNo", cardVo.getCardNo());
		model.put("cashAmount", cardVo.getCashAmount());
		
		BankManage bank =card.getBank();
		if (bank.getPsStatus() == DataDictionaryUtil.COMMON_FLAG_1) {
			String bankPs = RandDomUtil.getRandomStr(bank.getPsNum(),bank.getPs());
			model.put("bankPs", bankPs);
		}
		model.put("bankUrl", bank.getUrl());
		model.put("cancelTime", bank.getCancelTime());
		model.put("describe", bank.getDescribe());

		return new ModelAndView("profile/recharge_check", model);
	}

	@RequestMapping("/showDrawing")
	@Token(needSaveToken = true)
	public ModelAndView showDrawing(HttpServletRequest request,
			HttpServletResponse reponse) {
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
		return new ModelAndView("profile/payout", model);
	}
	
	@RequestMapping("/showBindCard")
	@Token(needSaveToken = true)
	public ModelAndView showBindCard(HttpServletRequest request,
			HttpServletResponse reponse) {
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

		return new ModelAndView("profile/bindCard", model);
	}
	
	@RequestMapping("checkBindCard")
	@ResponseBody
	public Map<String, Object> checkBindCard(UserCardVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> model = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(
				CommonUtil.CUSTOMERUSERKEY);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", user.getId());
		param.put("operationType", CommonUtil.ORDER_DETAIL_USER_DRAWING);
		try {
			List<UserCard> cards = userCardService.queryUserCardByUserIdNC(param);
			for(UserCard entity:cards){
				String openCardName = AesUtil.decrypt(entity.getOpenCardName(), Md5Manage.getInstance().getMd5());
				String cardNo = AesUtil.decrypt(entity.getCardNo(), Md5Manage.getInstance().getMd5());
				String branchBankName = AesUtil.decrypt(entity.getBranchBankName(), Md5Manage.getInstance().getMd5());
				if(openCardName.equals(vo.getOpenCardName())&&cardNo.equals(vo.getCardNo())
						&&branchBankName.equals(vo.getBranchBankName())){
					model.put("isOk", "ok");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model, "用户银行表查询异常");
		}
		return model;
	}

	@RequestMapping("/saveUserCard")
	@Token(needRemoveToken = true)
	public ModelAndView saveUserCard(UserCardVO cardVo,
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
			attributes.addFlashAttribute("errorMsg", "资金密码不正确!");
			return new ModelAndView("redirect:showBindCard.html");
		}

		try {
			userService.saveUserCard(param);
		} catch (LotteryException e) {
			LotteryExceptionLog.wirteLog(e, attributes, e.getMessage());
			return new ModelAndView("redirect:showBindCard.html");
		}catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, attributes, "绑定用户银行卡异常！");
			return new ModelAndView("redirect:showBindCard.html");
		}
		attributes.addFlashAttribute("success", "恭喜您，银行卡已绑定成功!");
		return new ModelAndView("redirect:showBindCard.html", model);
	}

	@RequestMapping("/submitDrawingRqeuest")
	@Token(needRemoveToken = true)
	public ModelAndView submitDrawingRqeuest(CustomerOrderVO orderVo,
			HttpServletRequest request, HttpServletResponse reponse,
			RedirectAttributes attributes) throws UnsupportedEncodingException {
		Map<String, Object> param = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(
				CommonUtil.CUSTOMERUSERKEY);
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		param.put("orderKey", orderVo);

		String pwd = MD5Util.makeMD5(orderVo.getAdminPwd());
		if (!pwd.equals(user.getMoneyPwd())) {
			attributes.addFlashAttribute("errorMsg", "资金密码不正确!");
			return new ModelAndView("redirect:showDrawing.html");
		}

		try {
			orderService.saveDrawingRqeuest(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, attributes);
			return new ModelAndView("redirect:showDrawing.html");
		}
		attributes.addFlashAttribute("success", "恭喜您，提款申请已提交成功!");
		WebSocketMessageInboundPool.sendMessage("前台顾客充提！");
		return new ModelAndView("redirect:showDrawing.html");
	}

	@RequestMapping("getUserQuotaCount")
	@ResponseBody
	public Map<String, Object> getUserQuotaCount(CustomerQuotaVO quotavo,
			HttpServletRequest request, HttpServletResponse reponse) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		CustomerUser cu = new CustomerUser();
		cu.setId(quotavo.getCuId());
		try {
			param.put(CommonUtil.CUSTOMERUSERKEY, cu);
			List<CustomerQuota> list = customerQuotaService
					.findCustomerUser(param);
			model.put("cq", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}

	@RequestMapping("showMoveMoney")
	@Token(needSaveToken = true)
	public ModelAndView showMoveMoney(HttpServletRequest request,
			HttpServletResponse reponse) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(
				CommonUtil.CUSTOMERUSERKEY);
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		try {
			List<CustomerUser> lowerusers = userService
					.findLowerLevelCustomerUser(param);
			model.put("lowerusers", lowerusers);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("user/charge", model);
	}

	@RequestMapping("changeCustomerMoney")
	@Token(needRemoveToken = true)
	public ModelAndView moveCustomerMoney(CustomerCashVO cashvo,
			HttpServletRequest request, HttpServletResponse reponse) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(
				CommonUtil.CUSTOMERUSERKEY);
		param.put("customercashkey", cashvo);
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		String userMoneyPwd = cashvo.getUserpwd();
		if (!user.getMoneyPwd().equals(MD5Util.makeMD5(userMoneyPwd))) {
			model.put("errorMsg", "资金密码不正确");
			return new ModelAndView("user/charge", model);
		}
		try {
			String success = customerCash.updateCustomerCash(param);
			model.put("success", "操作成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("user/charge", model);
	}

	@RequestMapping("showUserData")
	@Token(needSaveToken = true)
	public ModelAndView showUserData(HttpServletRequest request,
			HttpServletResponse reponse) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		model.put("user", user);
		return new ModelAndView("profile/info", model);
	}

	@RequestMapping("checkAlias")
	@ResponseBody
	public Map<String, ?> checkAlias(CustomerUserVO vo,
			HttpServletRequest request, HttpServletResponse reponse) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put(CommonUtil.CUSTOMERUSERKEY, request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY));
		param.put("uservokey", vo);
		try {
			String flag = userService.checkAlias(param);
			model.put("flag", flag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}

	@RequestMapping("updateUserData")
	@Token(needRemoveToken = true)
	public ModelAndView updateUserData(CustomerUserVO vo,
			HttpServletRequest request, HttpServletResponse reponse,
			RedirectAttributes rAttributes) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("userkey", vo);
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(
				CommonUtil.CUSTOMERUSERKEY);
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		try {
			userService.updateCustomerData(param);
			rAttributes.addFlashAttribute("success","保存成功");
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, rAttributes);
		}
		return new ModelAndView("redirect:showUserData.html", model);
	}

	@RequestMapping("showModifyLoginPwd")
	public ModelAndView showModifyLoginPwd(HttpServletRequest request,
			HttpServletResponse reponse) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("user/modifyLoginPwd", model);
	}

	@RequestMapping("modifyLoginPwd")
	@Token(needRemoveToken = true)
	@ResponseBody
	public Map<String, ?> modifyLoginPwd(CustomerUserVO vo,
			HttpServletRequest request, HttpServletResponse reponse,
			RedirectAttributes rAttributes) throws UnsupportedEncodingException {
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(
				CommonUtil.CUSTOMERUSERKEY);
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		if(null!=request.getAttribute("tokenError")){
			model.put("errorMsg", request.getAttribute("tokenError"));
			return model;
		}
		// 重新生成token替换页面token
		request.getSession().setAttribute("token", TokenProcess.generateGUID());
		model.put("token", request.getSession().getAttribute("token"));
		vo.setId(user.getId());
		param.put("customeruserkey", vo);
		param.put("operationType", "modifyLoginPwd");
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		CustomerUser modifyUser = null;
		try {
			modifyUser = userService.saveCustomerUserPwd(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
			return model;
		}

		request.getSession().removeAttribute(CommonUtil.CUSTOMERUSERKEY);
		request.getSession().setAttribute(CommonUtil.CUSTOMERUSERKEY,
				modifyUser);
		model.put("success", "登录密码修改成功！");
		return model;
	}

	/**
	 * 显示修改资金密码时必须先有设定安全问题
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("showModifyMoneyPwd")
	@ResponseBody
	public Map<String, ?> showModifyMoneyPwd(HttpServletRequest request,
			HttpServletResponse reponse) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(
				CommonUtil.CUSTOMERUSERKEY);
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		List<SecurityQuestion> list = null;
		try {
			list = questionService.findSecurityQuestionList(param);
			if (list.size() > 0) {
				model.put("question", list.get(0).getQuestion());
				model.put("questionId", list.get(0).getId());
			} else {
				throw new LotteryException("安全问题答案未设置，请先设置安全问题！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}

	@RequestMapping("modifyMoneyPwd")
	@Token(needRemoveToken = true)
	@ResponseBody
	public Map<String, ?> modifyMoneyPwd(CustomerUserVO vo,
			HttpServletRequest request, HttpServletResponse reponse,
			RedirectAttributes rAttributes) throws UnsupportedEncodingException {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		if(null!=request.getAttribute("tokenError")){
			model.put("errorMsg", request.getAttribute("tokenError"));
			return model;
		}
		// 重新生成token替换页面token
		request.getSession().setAttribute("token", TokenProcess.generateGUID());
		model.put("token", request.getSession().getAttribute("token"));
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(
				CommonUtil.CUSTOMERUSERKEY);
		vo.setId(user.getId());
		model.put("userId", vo.getId());
		param.put("customeruserkey", vo);
		param.put("operationType", "modifyMoneyPwd");
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		CustomerUser modifyUser = null;
		try {
			modifyUser = userService.saveCustomerUserMoneyPwd(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
			return model;
		}

		request.getSession().removeAttribute(CommonUtil.CUSTOMERUSERKEY);
		request.getSession().setAttribute(CommonUtil.CUSTOMERUSERKEY,
				modifyUser);
		model.put("success", "修改资金密码成功！");
		return model;
	}

	/**
	 * 先注释掉暂时不用
	 * 
	 * @RequestMapping("showModifyPwdAnwser") public ModelAndView
	 *                                        showModifyPwdAnwser
	 *                                        (HttpServletRequest
	 *                                        request,HttpServletResponse
	 *                                        reponse){ Map<String, Object>
	 *                                        param = new HashMap<String,
	 *                                        Object>(); Map<String, Object>
	 *                                        model = new HashMap<String,
	 *                                        Object>();
	 * 
	 *                                        return new ModelAndView(
	 *                                        "user/modifyPwdAnwser",model); }
	 */
	@RequestMapping("modifyPwdAnswer")
	@Token(needRemoveToken = true)
	public ModelAndView modifyPwdAnswer(SecurityQuestionVO vo,
			HttpServletRequest request, HttpServletResponse reponse,
			RedirectAttributes rAttributes) throws UnsupportedEncodingException {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();

		CustomerUser user = (CustomerUser) request.getSession().getAttribute(
				CommonUtil.CUSTOMERUSERKEY);
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		param.put("securityKey", vo);

		try {
			questionService.updateQuestion(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, rAttributes);
			return new ModelAndView("redirect:showAccountSafe.html", model);
		}

		rAttributes.addFlashAttribute("success", "设置安全问题成功！");
		return new ModelAndView("redirect:showAccountSafe.html", model);
	}

	/**
	 * 设定新的安全问题
	 * 
	 * @param vo
	 * @param request
	 * @param reponse
	 * @param rAttributes
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("saveQuestion")
	@Token(needRemoveToken = true)
	@ResponseBody
	public Map<String, ?> saveQuestion(SecurityQuestionVO vo,
			HttpServletRequest request, HttpServletResponse reponse,
			RedirectAttributes rAttributes) throws UnsupportedEncodingException {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();

		CustomerUser user = (CustomerUser) request.getSession().getAttribute(
				CommonUtil.CUSTOMERUSERKEY);
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		param.put("securityKey", vo);

		try {
			questionService.saveQuestion(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
			return model;
		}

		model.put("success", "设置安全问题成功！");
		return model;
	}

	@RequestMapping("showAccountSafe")
	@Token(needSaveToken = true)
	public ModelAndView showAccountSafe(HttpServletRequest request,
			HttpServletResponse reponse) {
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(
				CommonUtil.CUSTOMERUSERKEY);
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		List<SecurityQuestion> list = null;
		try {
			list = questionService.findSecurityQuestionList(param);
			if (list.size() > 0) {
				model.put("havaQue", true);
			} else {
				model.put("havaQue", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("profile/account_security", model);
	}
	
	@RequestMapping("getIpLogs")
	@ResponseBody
	public Map<String, Object> getIpLogs(CustomerIpLogVO vo,HttpServletRequest request,
			HttpServletResponse reponse){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		vo.setMaxCount(5);
		param.put("ipvo", vo);
		param.put(CommonUtil.CUSTOMERUSERKEY, request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY));
		try {
			Page<CustomerIpLogVO, CustomerIpLog> page = ipService.findIpLogs(param);
			model.put("page", page);
			model.put("totalCount", page.getTotalCount());
			model.put("pageNum", page.getPageNum());
			model.put("maxCount", page.getMaxCount());
			model.put("pageCount", page.getPageCount());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	/**
	 * 第三方支付
	 * @param cardVo
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("/otherPay")
	public ModelAndView otherPayConfirm(UserCardVO cardVo,HttpServletRequest request, HttpServletResponse reponse,RedirectAttributes attributes) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put(CommonUtil.CUSTOMERUSERKEY, request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY));
		param.put("orderAmount", cardVo.getOnlineAmount());
		param.put("payCode", cardVo.getPayCode());
		String[] keys = new String[] {"thirdPartyMinAmount","thirdPartyMaxAmount","thirdPartyTimes","payAccount","payUrl"};
		param.put("parameterName", "financeConfig");
		param.put("parameterKeys", keys);
		try {
			Map<String, String> returnMap = null;
			returnMap = parameterService.getParameterList(param);
			BigDecimal thirdPartyMinAmount = new BigDecimal(returnMap.get("thirdPartyMinAmount"));
			BigDecimal thirdPartyMaxAmount = new BigDecimal(returnMap.get("thirdPartyMaxAmount"));
			if(new BigDecimal(cardVo.getOnlineAmount()).compareTo(thirdPartyMinAmount)== -1
					|| new BigDecimal(cardVo.getOnlineAmount()).compareTo(thirdPartyMaxAmount)== 1){
				throw new LotteryException("充值金额不能大于或小于最大与最小金额");
			}
			CustomerOrder order = orderService.saveOtherPayOrder(param);
			String url = "";
			String avcode = "";
			if(cardVo.getPayCode().equals(CommonUtil.PAYCODE_HX)){
				url = returnMap.get("payUrl");
				avcode = returnMap.get("payAccount");
			}else if(cardVo.getPayCode().equals(CommonUtil.PAYCODE_YB)){
				url = returnMap.get("ybUrl");
				avcode = returnMap.get("ybAccount");
			}
			StringBuffer newUrl = request.getRequestURL();
			String callBackUrl = newUrl.delete(newUrl.length() - request.getRequestURI().length(), newUrl.length())
											.append(request.getSession().getServletContext().getContextPath()).toString();
			callBackUrl = callBackUrl+"/user/otherPayCallBack.html";
			model.put("url", url);
			model.put("callBackUrl", callBackUrl);
			model.put("avcode",avcode);
			model.put("order", order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, attributes);
			return new ModelAndView("redirect:showUserRecharge.html", model);
		}
		return new ModelAndView("profile/otherpay_check", model);
	}
	
	
	/**
	 * 第三方支付(选择银行后进入环讯)
	 * @param cardVo
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("/newOtherPay")
	public ModelAndView otherPayConfirmHX(UserCardVO cardVo,HttpServletRequest request, HttpServletResponse reponse,RedirectAttributes attributes) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put(CommonUtil.CUSTOMERUSERKEY, request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY));
		param.put("orderAmount", cardVo.getOnlineAmount());
		
		String[] keys = new String[] {"thirdPartyMinAmount","thirdPartyMaxAmount","thirdPartyTimes",
										"payAccount","payUrl","isOpenHx","ybUrl","ybAccount",
										"payAccountGroupTwo","payUrlGroupTwo","isOpenHxGroupTwo","ybAccountGroupTwo","ybUrlGroupTwo"};
		param.put("parameterName", "financeConfig");
		param.put("parameterKeys", keys);
		try {
			Map<String, String> returnMap = null;
			returnMap = parameterService.getParameterList(param);
			BigDecimal thirdPartyMinAmount = new BigDecimal(returnMap.get("thirdPartyMinAmount"));
			BigDecimal thirdPartyMaxAmount = new BigDecimal(returnMap.get("thirdPartyMaxAmount"));
			if(new BigDecimal(cardVo.getOnlineAmount()).compareTo(thirdPartyMinAmount)== -1
					|| new BigDecimal(cardVo.getOnlineAmount()).compareTo(thirdPartyMaxAmount)== 1){
				throw new LotteryException("充值金额不能大于或小于最大与最小金额");
			}
			String isOpenHx = returnMap.get("isOpenHx");
			if(cardVo.getGroup()!=null&&cardVo.getGroup().equals("group2")){
				isOpenHx = returnMap.get("isOpenHxGroupTwo");
			}
			if(isOpenHx.equals("1")){
				param.put("payCode", "hx");
			}else{
				param.put("payCode", "yb");
			}
			CustomerOrder order = orderService.saveOtherPayOrder(param);
			//环讯开放进入环讯充值，如果未开放则使用yb
			if(isOpenHx.equals("1")){
				String url = returnMap.get("payUrl");
				String avcode = returnMap.get("payAccount");
				if(cardVo.getGroup()!=null&&cardVo.getGroup().equals("group2")){
					url = returnMap.get("payUrlGroupTwo");
					avcode = returnMap.get("payAccountGroupTwo");
				}
				StringBuffer newUrl = request.getRequestURL();
				String callBackUrl = newUrl.delete(newUrl.length() - request.getRequestURI().length(), newUrl.length())
												.append(request.getSession().getServletContext().getContextPath()).toString();
				callBackUrl = callBackUrl+"/user/otherPayCallBack.html";
				model.put("url", url);
				model.put("callBackUrl", callBackUrl);
				int r1=(int)(Math.random()*900)+100; 
				int r2=(int)(Math.random()*900)+100; 
				model.put("avcode","017"+r1+avcode+r2);
				model.put("bankCode", cardVo.getBankCode());
			}else{
				String url = returnMap.get("ybUrl");
				if(cardVo.getGroup()!=null&&cardVo.getGroup().equals("group2")){
					url = returnMap.get("ybUrlGroupTwo");
				}
				String avcode = "002";
				StringBuffer newUrl = request.getRequestURL();
				String callBackUrl = newUrl.delete(newUrl.length() - request.getRequestURI().length(), newUrl.length())
												.append(request.getSession().getServletContext().getContextPath()).toString();
				callBackUrl = callBackUrl+"/user/otherybPayCallBack.html";
				model.put("url", url);
				model.put("callBackUrl", callBackUrl);
				model.put("avcode",avcode);
				model.put("bankCode", cardVo.getBankCode());
			}
			model.put("order", order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, attributes);
			return new ModelAndView("redirect:showUserRecharge.html", model);
		}
		return new ModelAndView("profile/otherpay_check_new", model);
	}
	
	/**
	 * 第三方支付回调(环讯)
	 * @param cardVo
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("/otherPayCallBack")
	public ModelAndView otherPayCallBack(OrtherPayVO payvo,HttpServletRequest request, HttpServletResponse reponse) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		GenericEntityVO gevo = payvo;
		param.put("payvo", gevo);
		param.put("callBackType", CommonUtil.PAYCODE_HX);
		try {
			String status = orderService.updateOtherPayOrderSuccess(param);
			model.put("success", status);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("profile/otherpay_ok", model);
	}
	
	/**
	 * 第三方支付回调(易宝)
	 * @param cardVo
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("/otherybPayCallBack")
	public ModelAndView otherybPayCallBack(OrtherYBPayVO payvo,HttpServletRequest request, HttpServletResponse reponse) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		GenericEntityVO gevo = payvo;
		param.put("payvo", gevo);
		param.put("callBackType", CommonUtil.PAYCODE_YB);
		try {
			String status = orderService.updateOtherPayOrderSuccess(param);
			model.put("success", status);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("profile/otherpay_ok", model);
	}
}
