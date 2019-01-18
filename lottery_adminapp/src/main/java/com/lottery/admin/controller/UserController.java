package com.lottery.admin.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
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

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.BankManage;
import com.lottery.bean.entity.BonusGroup;
import com.lottery.bean.entity.CardLevelConfig;
import com.lottery.bean.entity.CustomerBankCard;
import com.lottery.bean.entity.CustomerCash;
import com.lottery.bean.entity.CustomerQuota;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.DomainUrl;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.UserCard;
import com.lottery.bean.entity.UserCardInventory;
import com.lottery.bean.entity.VCustomerBindCard;
import com.lottery.bean.entity.VCustomerFrozenUser;
import com.lottery.bean.entity.vo.AdminUserVO;
import com.lottery.bean.entity.vo.CardLevelConfigVO;
import com.lottery.bean.entity.vo.CustomerBankCardVO;
import com.lottery.bean.entity.vo.CustomerIpLogVO;
import com.lottery.bean.entity.vo.CustomerUserVO;
import com.lottery.bean.entity.vo.DomainUrlVO;
import com.lottery.bean.entity.vo.TempMapVO;
import com.lottery.bean.entity.vo.VCustomerFrozenUserVO;
import com.lottery.service.IAdminParameterService;
import com.lottery.service.IAdminUserService;
import com.lottery.service.IBankManageService;
import com.lottery.service.IBonusGroupService;
import com.lottery.service.ICardLevelConfigService;
import com.lottery.service.ICustomerBankCardService;
import com.lottery.service.ICustomerBindCardService;
import com.lottery.service.ICustomerCashService;
import com.lottery.service.ICustomerIpLogService;
import com.lottery.service.ICustomerQuotaService;
import com.lottery.service.ICustomerUrlService;
import com.lottery.service.ICustomerUserService;
import com.lottery.service.IDomainUrlService;
import com.lottery.service.IUserCardInventoryService;
import com.lottery.service.IUserCardService;
import com.lottery.service.IVCustomerFrozenUserService;
import com.xl.lottery.GrabNo.ClientFactory;
import com.xl.lottery.desutil.AesUtil;
import com.xl.lottery.desutil.Md5Manage;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.exception.LotteryExceptionDictionary;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.JsonUtil;
import com.xl.lottery.util.MD5Util;
import com.xl.lottery.util.RandDomUtil;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	@Autowired
	private IUserCardInventoryService cardInventoryService;
	@Autowired
	private ICustomerUrlService customerUrlService;
	
	@Autowired
	private IDomainUrlService domainUrlService;
	
	@Autowired
	private IAdminUserService adminUserService;
	
	@Autowired
	private IBonusGroupService bonusGroupService;
	
	@Autowired
	private IBankManageService bankManageService;
	
	@Autowired
	private ICustomerUserService customerUserService;
	
	@Autowired
	private IAdminParameterService parameterService;
	
	@Autowired
	private ICustomerBankCardService customerBankCardService;
	
	@Autowired
	private IUserCardService userCardService;
	
	@Autowired
	private ICustomerCashService customerCashService;
	
	@Autowired
	private ICustomerQuotaService customerQuotaService;
	
	@Autowired
	private ICustomerBindCardService customerBindCardService;

	@Autowired
	private IVCustomerFrozenUserService vCustomerFrozenUserService;
	
	@Autowired
	private ICardLevelConfigService cardLevelService;
	
	@Autowired
	private ICustomerIpLogService ipService;
	
	@RequestMapping("/showcustomerlist")
	public ModelAndView showCustomerList(DomainUrlVO domainUrlVO,
			HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("domainUrlkey", domainUrlVO);
		try {
			Page<DomainUrlVO, DomainUrl> page = domainUrlService
					.findDomainUrlList(param);
			model.put("pagelist", page);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, redirectAttributes);
			return new ModelAndView("redirect:domain", model);
		}

		return new ModelAndView("user/domain", model);
	}

	@RequestMapping("showUserManage")
	public ModelAndView showUserManage(CustomerUserVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("customeruservokey", vo);
		try {
			List<Object[]> users = customerUserService.findMainCustomers(param);
			List<Object[]> objs = customerUserService.queryUserCount();
			model.put("mainusers", users);
			model.put("voparams", vo);
			Object[] obj = objs.get(0);
			model.put("usercount", obj[0]);
			model.put("proxycount", obj[1]);
			model.put("membercount", obj[2]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("user/users",model);
	}
	
	/**
	 * 根据用户名查询用户信息
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("queryUser")
	@ResponseBody
	public Map<String, Object> queryUser(CustomerUserVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("customeruservokey", vo);
		try {
			CustomerUser user = customerUserService.queryUserByName(vo.getCustomerName());
			if(user==null){
				throw new LotteryException("无该用户名对应的用户记录！");
			}
			model.put("user", user);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	/**
	 * 重置前台用户登录密码
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("restPwd")
	@ResponseBody
	public Map<String, Object> restPwd(CustomerUserVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("customeruservokey", vo);
		try {
			CustomerUser user = customerUserService.saveRestPwdByName(vo.getCustomerName());
			model.put("info", "重置用户["+user.getCustomerName()+"]的登录密码为[a123456]成功!");
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	@RequestMapping("/addCustomerUrl")
	public ModelAndView addCustomerUrl(DomainUrl url,HttpServletRequest request, 
			HttpServletResponse response,RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("domainUrlkey", url);
		param.put(CommonUtil.USERKEY,
				request.getSession().getAttribute(CommonUtil.USERKEY));
		try {
			String addstatus = domainUrlService.saveDomainUrl(param);
			if (addstatus.equals("success")) {
				redirectAttributes.addFlashAttribute("success", LotteryExceptionDictionary.OPERATIONSUCCESS);
			} else {
				redirectAttributes.addFlashAttribute("errorMsg",LotteryExceptionDictionary.OPERRATIONFAILE);
			}
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, redirectAttributes);
			return new ModelAndView("redirect:showcustomerlist.do", model);
		}
		return new ModelAndView("redirect:showcustomerlist.do", model);
	}

	@RequestMapping("deleteCustomerUrl")
	public ModelAndView deleteCustomerUrl(DomainUrl url,HttpServletRequest request, 
			HttpServletResponse response,RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("domainUrlkey", url);
		param.put(CommonUtil.USERKEY,
				request.getSession().getAttribute(CommonUtil.USERKEY));
		try {
			String delStatus = domainUrlService.deleteDomainUrl(param);
			if (delStatus.equals("success")) {
				redirectAttributes.addFlashAttribute("success", LotteryExceptionDictionary.OPERATIONSUCCESS);
			} else {
				redirectAttributes.addFlashAttribute("errorMsg",LotteryExceptionDictionary.OPERRATIONFAILE);
			}
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, redirectAttributes);
			return new ModelAndView("redirect:showcustomerlist.do", model);
		}
		return new ModelAndView("redirect:showcustomerlist.do", model);
	}

	@RequestMapping("/initUserBaseConfig")
	public ModelAndView initUserBaseConfig(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();

		String[] keys = new String[]{"minPoint","stepPoint","webws","jobws"};

		param.put("parameterName", "userConfig");
		param.put("parameterKeys", keys);
		List<UserCardInventory> inventorys = null;
		Map<String,String> returnMap=null;
		try {
			returnMap = parameterService.getParameterList(param);
			inventorys = cardInventoryService.queryInventorys(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
			return new ModelAndView("user/config", model);
		}
		
		if(null!=returnMap){
			model.putAll(returnMap);
		}
		model.put("inventorys", inventorys);
		return new ModelAndView("user/config", model);
	}
	
	@RequestMapping("queryCardLevelConfig")
	@ResponseBody
	public Map<String, Object> queryCardLevelConfig(CustomerBankCardVO bankCardVO,HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		List<CardLevelConfig> levels = null;	
		try {
			levels = cardLevelService.queryCardLevel(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		model.put("levels", levels);
		return model;
	}

	@RequestMapping("/saveUserBaseConfig")
	public ModelAndView saveUserBaseConfig(CardLevelConfigVO vo,HttpServletRequest request,
			HttpServletResponse response,RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		
		ModelAndView returnModel = this.checkRolePwdAndPiCode(request, model,
				vo.getRsvst1(),"configkey", vo.getRsvst2(),"redirect:initUserBaseConfig.do");
		if(null!=returnModel){
			return returnModel;
		}
		Map<String,String> keyValueMap = new HashMap<String, String>();
		keyValueMap.put("minPoint", vo.getRsvdc1().toString());
		keyValueMap.put("stepPoint", vo.getRsvdc2().toString());
		keyValueMap.put("webws", vo.getWebws());
		keyValueMap.put("jobws", vo.getJobws());
		param.put("keyValueMap", keyValueMap);
		param.put("levelVoKey", vo);
		param.put("parameterName", "userConfig");
		param.put(CommonUtil.USERKEY, (AdminUser)request.getSession().getAttribute(CommonUtil.USERKEY));
		try {
			parameterService.saveParameterValue(param);
			cardLevelService.saveCardLevel(param);
			request.getSession().getServletContext().setAttribute("webws", vo.getWebws());
			request.getSession().getServletContext().setAttribute("jobws", vo.getJobws());
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, redirectAttributes);
			return new ModelAndView("user/config", model);
		}
		redirectAttributes.addFlashAttribute("success", "提交成功！");
		return new ModelAndView("redirect:initUserBaseConfig.do", model);
	}
	
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping("showAddMainUser")
	public ModelAndView showAddMainUser(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		//String randomPwd = RandDomUtil.generateWord(8, false);
		model.put("randomPwd", "a123456");
		List<BonusGroup> bglist;
		try {
			bglist = bonusGroupService.findBonusGroupAll(param);
			model.put("bglist", bglist);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e1, model);
		}
		String minPoint = ""; //最小配额
		String stepPoint ="";  //最小点差
		
		
		String[] keys = new String[]{"minPoint","stepPoint"};

		param.put("parameterName", "userConfig");
		param.put("parameterKeys", keys);
		
		Map<String,String> returnMap=null;
		try {
			returnMap = parameterService.getParameterList(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
			return new ModelAndView("user/adduser", model);
		}
		if(null!=returnMap){
			model.putAll(returnMap);
		}
		
		
		try {
			List<DomainUrl> urlList = domainUrlService.findDomainUrllist(param);
			model.put("urlList", urlList);
			List<BankManage> banks = bankManageService.findBankeManageList();
			model.put("banks", banks);
			model.put("proxyuserstatus", customerUserService.checkRegUserStatus());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("user/adduser",model);
	}
	
	@RequestMapping("addmainuser")
	public ModelAndView addCustomerMainUser(CustomerUserVO userVo,HttpServletRequest request,
			HttpServletResponse response,RedirectAttributes redirectAttributes) throws UnsupportedEncodingException{
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		AdminUser admin  = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		if(!MD5Util.makeMD5(userVo.getAdminPwd()).equals(admin.getUserRolePwd())){
			redirectAttributes.addFlashAttribute("errorMsg", "权限密码错误!");
			return new ModelAndView("redirect:showAddMainUser.do",model);
		}
		if(!request.getSession().getAttribute(userVo.getPickey()).toString().equalsIgnoreCase(userVo.getCode())){
			redirectAttributes.addFlashAttribute("errorMsg", "验证码错误!");
			return new ModelAndView("redirect:showAddMainUser.do",model);
		}
		param.put("customeruserkey", userVo);
		param.put(CommonUtil.USERKEY, admin);
		try {
			String success = customerUserService.saveMainCustomer(param);
			if(success.equals("success")){
				redirectAttributes.addFlashAttribute("success", "操作成功!");
			}else{
				redirectAttributes.addFlashAttribute("faile", "操作失败!");
			}
			
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, redirectAttributes);
		}
		return new ModelAndView("redirect:showAddMainUser.do",model);
	}
	
	/**
	 * 开始注册总代
	 * @param userVo
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("addRegUser")
	public ModelAndView addCustomerRegUser(CustomerUserVO userVo,HttpServletRequest request,
			HttpServletResponse response,RedirectAttributes redirectAttributes) throws UnsupportedEncodingException{
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		AdminUser admin  = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		if(!MD5Util.makeMD5(userVo.getAdminPwd()).equals(admin.getUserRolePwd())){
			redirectAttributes.addFlashAttribute("errorMsg", "权限密码错误!");
			return new ModelAndView("redirect:showAddMainUser.do",model);
		}
		if(!request.getSession().getAttribute(userVo.getPickey()).toString().equalsIgnoreCase(userVo.getCode())){
			redirectAttributes.addFlashAttribute("errorMsg", "验证码错误!");
			return new ModelAndView("redirect:showAddMainUser.do",model);
		}
		userVo.setCreateUser(CommonUtil.PROXYUSER);
		param.put("customeruserkey", userVo);
		param.put(CommonUtil.USERKEY, admin);
		try {
			if(customerUserService.checkRegUserStatus()){
				redirectAttributes.addFlashAttribute("errorMsg", "已有注册总代,无须添加");
				return new ModelAndView("redirect:showAddMainUser.do",model);
			}
			String success = customerUserService.saveMainCustomer(param);
			if(success.equals("success")){
				redirectAttributes.addFlashAttribute("success", "操作成功!");
			}else{
				redirectAttributes.addFlashAttribute("faile", "操作失败!");
			}
			
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, redirectAttributes);
		}
		return new ModelAndView("redirect:showAddMainUser.do",model);
	}
	
	
	/**
	 * 添加测试总代
	 * @param userVo
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("addTestUser")
	public ModelAndView addCustomerTestUser(CustomerUserVO userVo,HttpServletRequest request,
			HttpServletResponse response,RedirectAttributes redirectAttributes) throws UnsupportedEncodingException{
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		AdminUser admin  = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		if(!MD5Util.makeMD5(userVo.getAdminPwd()).equals(admin.getUserRolePwd())){
			redirectAttributes.addFlashAttribute("errorMsg", "权限密码错误!");
			return new ModelAndView("redirect:showAddMainUser.do",model);
		}
		if(!request.getSession().getAttribute(userVo.getPickey()).toString().equalsIgnoreCase(userVo.getCode())){
			redirectAttributes.addFlashAttribute("errorMsg", "验证码错误!");
			return new ModelAndView("redirect:showAddMainUser.do",model);
		}
		userVo.setCustomerType(DataDictionaryUtil.CUSTOMER_TYPE_TEST);
		param.put("customeruserkey", userVo);
		param.put(CommonUtil.USERKEY, admin);
		try {
			String success = customerUserService.saveMainCustomer(param);
			if(success.equals("success")){
				redirectAttributes.addFlashAttribute("success", "操作成功!");
			}else{
				redirectAttributes.addFlashAttribute("faile", "操作失败!");
			}
			
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, redirectAttributes);
		}
		return new ModelAndView("redirect:showAddMainUser.do",model);
	}
	
	@SuppressWarnings("unused")
	@RequestMapping("showUserInfo/{id}")
	public ModelAndView showUserInfo(CustomerUserVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("uservokey", vo);
		try {
			List<Object[]> userTree = customerUserService.findCuserTree(param);
			CustomerUser tempEntity = new CustomerUser();
			tempEntity.setId(vo.getId());
			param.put(CommonUtil.CUSTOMERUSERKEY, tempEntity);
			List<Object[]> lowerUser = customerUserService.findLowerLevelCustomerUserObjects(param);
			List<VCustomerBindCard> bankCards = customerBankCardService.findCardsByUserId(param);
			param.put("userId", vo.getId());
			List<Object[]> userCard = userCardService.queryUserCardBycustomerId(param);
			for(Object[] obj: userCard){
				UserCard uc = (UserCard) obj[1];
				uc.setOpenCardName(AesUtil.decrypt(uc.getOpenCardName(), Md5Manage.getInstance().getMd5()));
				uc.setCardNo(AesUtil.decrypt(uc.getCardNo(), Md5Manage.getInstance().getMd5()));
				uc.setAddress(AesUtil.decrypt(uc.getAddress(), Md5Manage.getInstance().getMd5()));
				uc.setBranchBankName(AesUtil.decrypt(uc.getBranchBankName(), Md5Manage.getInstance().getMd5()));
			}
			CustomerCash cash = customerCashService.findCustomerCashByUserId(param);
			List<CustomerQuota> quotas = customerQuotaService.findCustomerUser(param);
			model.put("userTree", userTree);
			model.put("lowerUser", lowerUser);
			model.put("bankCards", bankCards);
			model.put("userCard", userCard);
			model.put("cash", cash);
			model.put("user", userTree.get(userTree.size()-1));
			model.put("quotas", quotas);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("user/user",model);
		
	}
	
	@RequestMapping("findBankCards")
	@ResponseBody
	public Map<String,?> findBankCards(CustomerUserVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			List<BankManage> banks = bankManageService.findBanks(param);
			if(vo.getBankId() == 0){
				param.put("bankId", banks.get(0).getId());
			}else{
				param.put("bankId", vo.getBankId());
			}
			param.put("userId", vo.getId());
			List<CustomerBankCard> bankCards = customerBankCardService.findBankCardsNotByUserId(param);
			model.put("banks", banks);
			model.put("bankCards", bankCards);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("addBindCard")
	public ModelAndView addBindCard(long id,long bankCardId,HttpServletRequest request,
			HttpServletResponse response,RedirectAttributes redirectAttributes){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", id);
		param.put("cardId", bankCardId);
		AdminUser admin = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put(CommonUtil.USERKEY, admin);
		try {
			String success = customerBindCardService.saveBindCardByUser(param);
			redirectAttributes.addFlashAttribute(success, "添加银行卡成功");
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, redirectAttributes);
		}
		return new ModelAndView("redirect:showUserInfo/"+id+".do",model);
	}
	
	@RequestMapping("updateUserInfo")
	public ModelAndView updateUserInfo(long userId,CustomerUserVO vo,HttpServletRequest request,
			HttpServletResponse response,RedirectAttributes redirectAttributes){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		vo.setId(userId);
		AdminUser admin = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put("uservokey", vo);
		param.put(CommonUtil.USERKEY, admin);
		try {
			if(!MD5Util.makeMD5(vo.getAdminPwd()).equals(admin.getUserRolePwd())){
				redirectAttributes.addFlashAttribute("errorMsg", "权限密码错误!");
				return new ModelAndView("redirect:showUserInfo/"+userId+".do",model);
			}
			if(!request.getSession().getAttribute(vo.getPickey()).toString().equalsIgnoreCase(vo.getCode())){
				redirectAttributes.addFlashAttribute("errorMsg", "验证码错误!");
				return new ModelAndView("redirect:showUserInfo/"+userId+".do",model);
			}
			String success = customerUserService.updateUserInfo(param);
			redirectAttributes.addFlashAttribute(success, "用户信息修改成功!");
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, redirectAttributes);
		}
		return new ModelAndView("redirect:showUserInfo/"+userId+".do",model);
	}
	
	@RequestMapping("showFrozenUsers")
	public ModelAndView showFrozenUsers(VCustomerFrozenUserVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("frozenvokey", vo);
		try {
			Page<VCustomerFrozenUserVO, VCustomerFrozenUser> page = vCustomerFrozenUserService.findVCustomerFrozenUsers(param);
			model.put("page", page);
			model.put("vo", vo);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("user/lock",model);
	}
	
	@RequestMapping("showuserip")
	public ModelAndView showuserip(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("user/userip",model);
	}
	
	@RequestMapping("queryips")
	@ResponseBody
	public Map<String, Object> queryIpdatas(CustomerIpLogVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ipvo", vo);
		try {
			Page<Object, Object> page = ipService.queryIplogs(param);
			model.put("page", page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("killUser")
	@ResponseBody
	public Map<String, Object> killUser(CustomerIpLogVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		
		try {
			String links = vo.getIpAddress()+"/killUserByAdmin.shtml";
			links = "http"+links.substring(links.indexOf(":"));
			TempMapVO mapVo = new TempMapVO();
			mapVo.setKey("userName");
			mapVo.setValue(vo.getUname());
			String params = JsonUtil.objToJson(mapVo);
			params = org.apache.commons.codec.binary.Base64.encodeBase64String(params.getBytes());
			//将最新的开奖结果发送到前台的map中
			String jsonStr;  
			for(int i=0;i<3;i++){
				try {
					jsonStr = ClientFactory.getUrl(links,"msg="+params);
					if(!StringUtils.isEmpty(jsonStr)&&jsonStr.equals("success")){
						model.put("info", "用户["+vo.getUname()+"]成功被踢出！");
						break;
					}
				} catch (Exception e) {
					continue;
				}
			}
			if(model.get("info")==null){
				model.put("info", "踢出用户操作失败！");
			}
			
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("getpeos")
	@ResponseBody
	public Map<String, Object> getOnlinePeoples(String webUrl,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if(webUrl.indexOf("ws://")>-1){
				webUrl = webUrl.split("ws://")[1];
			}
			String links = "http://"+webUrl+"/getpeos.shtml";
			links = "http"+links.substring(links.indexOf(":"));
			//将最新的开奖结果发送到前台的map中
			String jsonStr;  
			try {
				jsonStr = ClientFactory.getUrl(links,"from=1");
				jsonStr = jsonStr.split(":")[1].split("}")[0].trim().split("\"")[1];
				jsonStr = AesUtil.decrypt(jsonStr, Md5Manage.getInstance().getMd5());
				model.put("peos", jsonStr);
			} catch (Exception e) {
				LotteryExceptionLog.wirteLog(e, model);
			}
			if(model.get("info")==null){
				model.put("info", "踢出用户操作失败！");
			}
			
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	

	@RequestMapping("findMaxChildRebates")
	@ResponseBody
	public Map<String,?> findMaxChildRebates(CustomerUserVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			long customerLong=vo.getId();
			BigDecimal resultMaxRebates=customerUserService.getCustomerUserMaxRebatesByParentId(customerLong);
			model.put("resultMaxRebates", resultMaxRebates);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
}
