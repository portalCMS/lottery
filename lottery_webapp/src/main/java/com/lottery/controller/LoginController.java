package com.lottery.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
 





import com.lottery.annotation.Token;
import com.lottery.bean.entity.AdvertisingList;
import com.lottery.bean.entity.CustomerCash;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.UserCard;
import com.lottery.bean.entity.vo.ArticleManageVO;
import com.lottery.bean.entity.vo.CustomerIntegralVO;
import com.lottery.bean.entity.vo.CustomerOrderVO;
import com.lottery.bean.entity.vo.CustomerUserVO;
import com.lottery.bean.entity.vo.LotteryTypeVO;
import com.lottery.bean.entity.vo.TempMapVO;
import com.lottery.ip.util.Utils;
import com.lottery.service.IAdvertisingListService;
import com.lottery.service.IArticleManageService;
import com.lottery.service.IClassSortService;
import com.lottery.service.ICustomerCashService;
import com.lottery.service.ICustomerIntegralService;
import com.lottery.service.ICustomerIpLogService;
import com.lottery.service.ICustomerOrderService;
import com.lottery.service.ICustomerUserService;
import com.lottery.service.ILotteryTypeService;
import com.lottery.service.IUserCardService;
import com.mysql.jdbc.StringUtils;
import com.xl.lottery.exception.LotteryExceptionDictionary;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DozermapperUtil;
import com.xl.lottery.util.JsonUtil;
import com.xl.lottery.util.LotWebUtil;
import com.xl.lottery.util.MD5Util;

@Controller
public class LoginController extends BaseController{

	@Autowired
	private ICustomerUserService customerUserService;
	
	@Autowired
	private ICustomerCashService cashService;
	
	@Autowired
	private ILotteryTypeService lotteryService;
	
	@Autowired
	private IArticleManageService aService;
	
	@Autowired
	private IClassSortService classSortService;
	
	@Autowired
	private ICustomerOrderService orderService;
	
	@Autowired
	private ICustomerIpLogService ipService;
	
	@Autowired
	private IAdvertisingListService advertsService;
	
	@Autowired
	private ICustomerIntegralService integralService;
	
	@Autowired
	private IUserCardService userCardService;
	
	@RequestMapping("/login")
	@Token(needRemoveToken=true)
	public ModelAndView showLogin(CustomerUserVO vo,HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
		ModelMap model = new ModelMap();
		HttpSession session = LotWebUtil.getSessionMap().get(vo.getCustomerName());
		HttpSession sessionnew = request.getSession();
		if(session!=null&&(session!=sessionnew)){
			try {
				session.invalidate();
			} catch (Exception e) {
				// TODO: handle exception
				LotWebUtil.getSessionMap().remove(vo.getCustomerName());
			}
		}
		
		redirectAttributes.addFlashAttribute("noShowError", true);
		Integer pwdError = (Integer) sessionnew.getAttribute("pwdError");
		if(pwdError != null && pwdError>=3){
			//图片验证码
			String picCode = (String) sessionnew.getAttribute(PictureCheckCodeController.RANDOMCODEKEY);
			if(picCode==null||!picCode.equalsIgnoreCase(vo.getCode())){
				redirectAttributes.addFlashAttribute("errorMsg", LotteryExceptionDictionary.PICCODEERROR);
				return new ModelAndView("redirect:home.html",model);
			}
			sessionnew.removeAttribute(PictureCheckCodeController.RANDOMCODEKEY);
		}
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("customeruserkey", vo);
		try {
			CustomerUser entity = customerUserService.loginCheckCustomerUser(param);
			if(entity==null||entity.getCustomerName()==null){
				redirectAttributes.addFlashAttribute("errorMsg", LotteryExceptionDictionary.LOGINERROR);
				if(pwdError == null){
					pwdError = 1;
				}else{
					pwdError += 1;
				} 
				sessionnew.setAttribute("pwdError", pwdError);
				return new ModelAndView("redirect:home.html",model);
			}
			if(entity.getCustomerStatus() == DataDictionaryUtil.STATUS_CLOSE){
				redirectAttributes.addFlashAttribute("errorMsg", "账号已被冻结,请联系客服");
				return new ModelAndView("redirect:home.html",model);
			}
//			if(entity.getCustomerError()>=3){
//				redirectAttributes.addFlashAttribute("errorMsg", LotteryExceptionDictionary.LOGINERRORCOUNT);
//				return new ModelAndView("redirect:home.html",model);
//			}
			entity.setIp(Utils.getIpAddr(request));
			CustomerUser sessionUser = new CustomerUser();
			DozermapperUtil.getInstance().map(entity, sessionUser);
			sessionnew.setAttribute(CommonUtil.CUSTOMERUSERKEY, sessionUser);
			LotWebUtil.getSessionMap().put(entity.getCustomerName(), sessionnew);
			if(entity.getCustomerOnlineStatus()==DataDictionaryUtil.STATUS_ONLINE_NO){
				if(entity.getCreateUser().equals(CommonUtil.PROXYUSER)){
					return new ModelAndView("redirect:user/showInitMoneyPwd.html",model);
				}else{
					return new ModelAndView("redirect:user/showInitLoginPwd.html",model);
				}
			}
			//判断客服端登录，且cpuid与diskid不同则更新
			if(sessionnew.getAttribute("isClient")!=null&&sessionnew.getAttribute("isClient").toString().equals("true")){
				String cpuid = (String) sessionnew.getAttribute("cpuid");
				String diskid = (String) sessionnew.getAttribute("diskid");
				String fns = (String) sessionnew.getAttribute("fns");
				
				String fnames = fns;
				String isNew = (String) sessionnew.getAttribute("isNew");
				if(sessionnew.getAttribute("isNew")==null ){
					fnames = new String(Base64.decodeBase64(fns),"utf-8");
				}
				//String fnames = new String(Base64.decodeBase64(fns),"utf-8");
				
				if(entity.getRsvst1()==null||entity.getRsvst1().equals("")){
					entity.setRsvst1(cpuid);
					entity.setRsvst2(diskid);
					entity.setRsvst3(fnames);
					customerUserService.update(entity);
				}else if(!entity.getRsvst1().equals(cpuid)||!entity.getRsvst2().equals(diskid)
							|| entity.getRsvst3()==null || !entity.getRsvst3().equals(fnames)){
					entity.setRsvst1(cpuid);
					entity.setRsvst2(diskid);
					entity.setRsvst3(fnames);
					customerUserService.update(entity);
				}
			}
			param.put(CommonUtil.CUSTOMERUSERKEY, entity);
			ipService.saveIpLog(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, redirectAttributes);
			if(pwdError == null){
				pwdError = 1;
			}else{
				pwdError += 1;
			} 
			sessionnew.setAttribute("pwdError", pwdError);
			return new ModelAndView("redirect:home.html",model);
		}
		//this.sendOnlineCount();
		sessionnew.removeAttribute("pwdError");
		return new ModelAndView("redirect:index.html",model);
	}
	
	@RequestMapping("/reglogin")
	public ModelAndView showReginLogin(CustomerUserVO vo,HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
		ModelMap model = new ModelMap();
		vo = (CustomerUserVO) RequestContextUtils.getInputFlashMap(request).get("user");
		HttpSession session = LotWebUtil.getSessionMap().get(vo.getCustomerName());
		HttpSession sessionnew = request.getSession();
		if(session!=null&&(session!=sessionnew)){
			try {
				session.invalidate();
			} catch (Exception e) {
				// TODO: handle exception
				LotWebUtil.getSessionMap().remove(vo.getCustomerName());
			}
		}
		redirectAttributes.addFlashAttribute("noShowError", true);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("customeruserkey", vo);
		try {
			CustomerUser entity = customerUserService.loginCheckCustomerUser(param);
			if(entity==null||entity.getCustomerName()==null){
				redirectAttributes.addFlashAttribute("errorMsg", LotteryExceptionDictionary.LOGINERROR);
				return new ModelAndView("redirect:home.html",model);
			}
			if(entity.getCustomerStatus() == DataDictionaryUtil.STATUS_CLOSE){
				redirectAttributes.addFlashAttribute("errorMsg", "账号已被冻结,请联系客服");
				return new ModelAndView("redirect:home.html",model);
			}
			if(entity.getCustomerError()>=3){
				redirectAttributes.addFlashAttribute("errorMsg", LotteryExceptionDictionary.LOGINERRORCOUNT);
				return new ModelAndView("redirect:home.html",model);
			}
			entity.setIp(Utils.getIpAddr(request));
			CustomerUser sessionUser = new CustomerUser();
			DozermapperUtil.getInstance().map(entity, sessionUser);
			sessionnew.setAttribute(CommonUtil.CUSTOMERUSERKEY, sessionUser);
			LotWebUtil.getSessionMap().put(entity.getCustomerName(), sessionnew);
			if(entity.getCustomerOnlineStatus()==DataDictionaryUtil.STATUS_ONLINE_NO){
				return new ModelAndView("redirect:user/showInitMoneyPwd.html",model);
			}
			param.put(CommonUtil.CUSTOMERUSERKEY, entity);
			ipService.saveIpLog(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, redirectAttributes);
			return new ModelAndView("redirect:home.html",model);
		}
		//this.sendOnlineCount();
		return new ModelAndView("redirect:index.html",model);
	}
	
	@RequestMapping("/index")
	@Token(needSaveToken=true)
	public ModelAndView showIndex(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>(); 
		CustomerUser self = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		try {
			param.put("userId", self.getId());
			param.put("operationType", CommonUtil.ORDER_DETAIL_USER_DRAWING);
			List<UserCard> cards = userCardService.queryUserCardByUserIdNC(param);
			if(cards.size()==0){
				request.getSession().setAttribute("sessioncards", 1);
			}else{
				request.getSession().removeAttribute("sessioncards");
			}
			param.put("type", CommonUtil.NOTICE);
			List<ArticleManageVO> list = aService.findDefaultNoticeArrticle(param);
			List<CustomerOrderVO> ordervos = orderService.queryNewWinningOrder(param);
			param.put("regionCode", CommonUtil.INDEX_CODE);
			List<AdvertisingList> adverts = advertsService.getAdvertisingLists(param);
			CustomerIntegralVO integralVO = integralService.getSelfIntegral(self.getId());
			model.put("adverts", adverts);
			model.put("ams", list);
			model.put("ordervos", ordervos);
			model.put("integral", integralVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("default",model);
	}
	
	@RequestMapping("gohome")
	@Token(needSaveToken=true)
	public ModelAndView showlogin(String error,HttpServletRequest request, HttpServletResponse response,RedirectAttributes reAttributes) throws Exception{
		String url = "login_2";
		if(request.getSession().getAttribute("form")!=null){
			reAttributes.addFlashAttribute("form",request.getSession().getAttribute("form").toString());
			reAttributes.addFlashAttribute("fns",request.getSession().getAttribute("fns").toString());
			url = "redirect:chome.html";
		}
		if(error!=null&&error.equals("true")){
			request.getSession().invalidate();
			request.setAttribute("errorMsg", "注销成功");
		}else{
			request.setAttribute("errorMsg", "登录超时");
		}
		return new ModelAndView(url);
	}
	
	@SuppressWarnings("unused")
	@RequestMapping("/home")
	@Token(needSaveToken=true)
	public ModelAndView loginhome(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		if(user!=null&&user.getCustomerOnlineStatus()==DataDictionaryUtil.STATUS_ONLINE_ON){
			return new ModelAndView("redirect:index.html");
		}else if(user!=null&&!user.getCreateUser().equals(CommonUtil.PROXYUSER)){
			return new ModelAndView("redirect:user/showInitLoginPwd.html",model);
		}else if(user!=null&&user.getCreateUser().equals(CommonUtil.PROXYUSER)){
			return new ModelAndView("redirect:user/showInitMoneyPwd.html",model);
		}
		return new ModelAndView("login_2");
	}
	
	@SuppressWarnings("unused")
	@RequestMapping("/chomeNew")
	@Token(needSaveToken=true)
	public ModelAndView clienthomeNew(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		String form = request.getParameter("cs");
		if(!StringUtils.isNullOrEmpty(form)){
			form=form.replace(" ","+");
		}
		
		Map<String, ?> parammap = RequestContextUtils.getInputFlashMap(request);
		if(form==null) form = (String) parammap.get("cs");
		request.getSession().setAttribute("form",form);
		
		if(form!=null){
			form = new String(Base64.decodeBase64(form),"utf-8");
			
			String[] forms = form.split("&");
			String cpuid = forms[0].substring(4,forms[0].length());
			String diskid = forms[1].substring(4,forms[1].length());
			String fns = forms[2].substring(4,forms[2].length());
			
			request.getSession().setAttribute("cpuid",cpuid);
			request.getSession().setAttribute("diskid",diskid);
			request.getSession().setAttribute("fns",fns);
			//set isclient 
			String crcResult = request.getParameter("crc");
			String crcTemp=MD5Util.makeMD5(Base64.encodeBase64String(cpuid.getBytes()) +"dj"+Base64.encodeBase64String(diskid.getBytes())+"yl"
			+Base64.encodeBase64String(fns.getBytes()) );
			String isClient="false";
			if(crcTemp.equals(crcResult)){
				isClient="true";
			}
			//MD5.digest(arg0)
			request.getSession().setAttribute("isClient",isClient);
			request.getSession().setAttribute("isNew","true");
		}
		
		
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		if(user!=null&&user.getCustomerOnlineStatus()==DataDictionaryUtil.STATUS_ONLINE_ON){
			return new ModelAndView("redirect:index.html");
		}else if(user!=null&&!user.getCreateUser().equals(CommonUtil.PROXYUSER)){
			return new ModelAndView("redirect:user/showInitLoginPwd.html",model);
		}else if(user!=null&&user.getCreateUser().equals(CommonUtil.PROXYUSER)){
			return new ModelAndView("redirect:user/showInitMoneyPwd.html",model);
		}
		return new ModelAndView("login_2");
	}
	

	@SuppressWarnings("unused")
	@RequestMapping("/chome")
	@Token(needSaveToken=true)
	public ModelAndView clienthome(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		String form = request.getParameter("form");
		Map<String, ?> parammap = RequestContextUtils.getInputFlashMap(request);
		if(form==null) form = (String) parammap.get("form");
		request.getSession().setAttribute("form",form);
		if(form!=null){
			String fns = request.getParameter("fns");
			if(fns == null)fns = (String) parammap.get("fns");
			request.getSession().setAttribute("fns",fns);
			
			form = new String(Base64.decodeBase64(form),"utf-8");
			String isClient = form.substring(0,2)+form.substring(form.length()-2,form.length());
			String[] forms = form.split(",");
			String cpuid = forms[0].substring(2,forms[0].length());
			String diskid = forms[1].substring(0,forms[1].length()-2);
			request.getSession().setAttribute("isClient",isClient);
			request.getSession().setAttribute("cpuid",cpuid);
			request.getSession().setAttribute("diskid",diskid);
		}
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		if(user!=null&&user.getCustomerOnlineStatus()==DataDictionaryUtil.STATUS_ONLINE_ON){
			return new ModelAndView("redirect:index.html");
		}else if(user!=null&&!user.getCreateUser().equals(CommonUtil.PROXYUSER)){
			return new ModelAndView("redirect:user/showInitLoginPwd.html",model);
		}else if(user!=null&&user.getCreateUser().equals(CommonUtil.PROXYUSER)){
			return new ModelAndView("redirect:user/showInitMoneyPwd.html",model);
		}
		return new ModelAndView("login_2");
	}
	
	
	
	@RequestMapping("/loginout")
	public ModelAndView loginout(HttpServletRequest request, HttpServletResponse response,RedirectAttributes reAttributes){
		HttpSession session = request.getSession();
		String url = "redirect:home.html";
		if(request.getSession().getAttribute("form")!=null){
			reAttributes.addFlashAttribute("form",request.getSession().getAttribute("form").toString());
			reAttributes.addFlashAttribute("fns",request.getSession().getAttribute("fns").toString());
			url = "redirect:chome.html";
		}
		session.invalidate();
		return new ModelAndView(url);
	}
	
	/**
	 * 获取当前用户的账户余额
	 * @param lotteryVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getCashAmount")
	@ResponseBody
	public Map<String,?> getCashAmount(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		param.put("userId",user.getId());
		CustomerCash cash = null;
		try {
			cash = cashService.findCustomerCashByUserId(param);
			model.put("cashAmount", cash.getCash());
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
			return model;
		}
		return model;
	}
	
	/**
	 * 获取当前的所有彩种
	 * @param lotteryVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getAllLottery")
	@ResponseBody
	public Map<String,?> getAllLottery(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		param.put("userId",user.getId());
		LotteryTypeVO lotteryVo = new LotteryTypeVO();
		lotteryVo.setLotteryStatus(DataDictionaryUtil.STATUS_OPEN);
		lotteryVo.setLotteryLevel(DataDictionaryUtil.COMMON_FLAG_1);
		param.put("lotteryKey",lotteryVo);
		try {
			model.put("groupList", lotteryService.getLotteryGroups(param));
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
			return model;
		}
		return model;
	}
	
	/**
	 * 向后台反推最新的在线人数统计
	 */
	private void sendOnlineCount(){
		Map<String, HttpSession> map = LotWebUtil.getSessionMap();
		Set<String> keys = map.keySet();
		for(String key:keys){
			if(map.get(key)==null){
				map.remove(key);
			}
		}
		int sessions = map.keySet().size();
		StringBuffer uns = new StringBuffer("");
		for(String un : map.keySet()){
			if(uns.length()==0){
				uns.append(un);
			}else{
				uns.append(","+un);
			}
		}
		//WebSocketMessageInboundPool.sendMessage("onLineUserCount:"+sessions+";onLineUserNames:"+uns);
	}
	
	/**
	 * 后台踢前台用户下线
	 */
	@RequestMapping("/killUserByAdmin")
	@ResponseBody
	public String killUserByAdmin(String msg,HttpServletRequest request,HttpServletResponse response){
		msg = new String(Base64.decodeBase64(msg));
		TempMapVO vo = JsonUtil.jsonToObject(msg, TempMapVO.class);
		if(null==vo){
			return "failed";
		}else{
			HttpSession session = LotWebUtil.getSessionMap().get(vo.getValue());
			try {
				session.invalidate();
			} catch (Exception e) {
				LotWebUtil.getSessionMap().remove(vo.getValue());
			}
			//踢下线后更新后台在线用户统计
			//this.sendOnlineCount();
			return "success";
		}
	}
	
	
	
	public static void main(String[] args){
		/*String aa="mac=c03fd5432a5fc8d71909685d&drv=1a0211f0,新加卷ecd6f46a,新加卷0,新加卷d6d26d13&fns=1.txt,111.jpg,2015-04-02.txt,Beyond Compare.lnk,Chrome 应用启动器.lnk,desktop.ini,qq.txt,UltraEdit-32.lnk,~$域名列表.xlsx,光速搜索.lnk,域名列表.xlsx,彩票,新建文本文档 (3).txt,新建文本文档.txt,算法.txt,组员指南.txt,组长指南.txt,返奖.xlsx";
		String bb=Base64.encodeBase64String(aa.getBytes());
		System.out.println(bb);*/
		//System.out.println(MD5("NmMxOThmOGEzOGUxZTAzZjQ5NDc2NWVidjZTIyY2YwZmIsSVJNX0NDU0FfWDZiNGZlNTMxNSzQwrzTvu0zODZjNTNkYizQwrzTvu0zODZmMDNjYyxHUk1DVUxYRlJFUmI0ZmU1MzE1ylMjM0NbCyyKvOwMq/LmxuaywyMzQ1yO28/rncvNIubG5rLGRlc2t0b3AuaW5pLGVjbGlwc2UubG5rLEVkaXRQbHVzLmxuayzSu7z8R0hPU1QubG5rLLLKxrG/zbuntssuZXhlLM6i0MUubG5rLL7JtcQgRmlyZWZveCDK/b7dLNPQtcDUxrHKvMcubG5rLNPQtcC0yrXkLmxuaw=="));
		System.out.println(MD5Util.makeMD5("NmMxOThmOGEzOGUxZTAzZjQ5NDc2NWVidjZTIyY2YwZmIsSVJNX0NDU0FfWDZiNGZlNTMxNSzQwrzTvu0zODZjNTNkYizQwrzTvu0zODZmMDNjYyxHUk1DVUxYRlJFUmI0ZmU1MzE1ylMjM0NbCyyKvOwMq/LmxuaywyMzQ1yO28/rncvNIubG5rLGRlc2t0b3AuaW5pLGVjbGlwc2UubG5rLEVkaXRQbHVzLmxuayzSu7z8R0hPU1QubG5rLLLKxrG/zbuntssuZXhlLM6i0MUubG5rLL7JtcQgRmlyZWZveCDK/b7dLNPQtcDUxrHKvMcubG5rLNPQtcC0yrXkLmxuaw=="));
		
	}
	
}
