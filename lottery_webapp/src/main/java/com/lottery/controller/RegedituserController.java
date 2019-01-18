package com.lottery.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.vo.CustomerUserVO;
import com.lottery.service.ICustomerUserService;
import com.xl.lottery.exception.LotteryExceptionDictionary;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DozermapperUtil;

@Controller
@RequestMapping("reg")
public class RegedituserController extends BaseController{

	@Autowired
	private ICustomerUserService userService;
	
	@RequestMapping("reguser")
	public ModelAndView showRegPage(HttpServletRequest request,HttpServletResponse reponse){
		//		Map<String, Object> param = new HashMap<String, Object>();
		//		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("reg/reguser");
	}
	
	@RequestMapping("addreguser")
	public ModelAndView regUser(CustomerUserVO vo,HttpServletRequest request,HttpServletResponse reponse,RedirectAttributes rediAttributes){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		//图片验证码
		String picCode = (String) request.getSession().getAttribute("regkey");
		if(picCode==null||!picCode.equalsIgnoreCase(vo.getCode())){
			rediAttributes.addFlashAttribute("errorMsg", LotteryExceptionDictionary.PICCODEERROR);
			return new ModelAndView("redirect:../home.html",model);
		}
		try {
			CustomerUser regUser = userService.getRegProxyUser();
			regUser.setCustomerName(CommonUtil.PROXYUSER);;
			param.put(CommonUtil.CUSTOMERUSERKEY, regUser);
			param.put("customeruserkey", vo);
			vo.setCustomerType(DataDictionaryUtil.CUSTOMER_TYPE_MEMBER);
			vo.setMinRebates(new BigDecimal(0.0));
			userService.saveOpenLowerUser(param);
			rediAttributes.addFlashAttribute("user", vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, rediAttributes);
			return new ModelAndView("redirect:reguser.html",model);
		}
		return new ModelAndView("redirect:../reglogin.html",model);
	}
	
	@RequestMapping("linkreguser")
	public ModelAndView showlinkpage(HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String links = new String(Base64.decodeBase64(request.getParameter("links").toString()),"utf-8");
			String parentId = Base64.encodeBase64String(links.split(",")[0].getBytes());
			String quota = Base64.encodeBase64String(links.split(",")[1].getBytes());
			String memberType = Base64.encodeBase64String(links.split(",")[2].getBytes());
			String link = request.getParameter("links");
			model.put("parentId", parentId);
			model.put("quota", quota);
			model.put("memberType", memberType);
			model.put("link", link);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("reg/linkuser",model);
	}
	
	@RequestMapping("linkOpenAccount")
	public ModelAndView linkOpenAccount(CustomerUserVO vo,HttpServletRequest request,HttpServletResponse reponse,RedirectAttributes rediAttributes){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		//图片验证码
		String picCode = (String) request.getSession().getAttribute("regkey");
		String link = request.getParameter("link");
		if(picCode==null||!picCode.equalsIgnoreCase(vo.getCode())){
			rediAttributes.addFlashAttribute("errorMsg", LotteryExceptionDictionary.PICCODEERROR);
			model.put("uservo", vo);
			model.put("parentId", request.getParameter("parentId"));
			model.put("quota", request.getParameter("quota"));
			model.put("memberType", request.getParameter("idsTree"));
			return new ModelAndView("redirect:linkreguser.html?links="+link);
		}
		try {
			String parentId = new String(Base64.decodeBase64(request.getParameter("parentId").toString()),"utf-8");
			String quota = new String(Base64.decodeBase64(request.getParameter("quota").toString()),"utf-8");
			String customerTypeStr = new String(Base64.decodeBase64(request.getParameter("idsTree").toString()),"utf-8");
			int customerType = Integer.parseInt(customerTypeStr);
			if(new BigDecimal(quota).longValue()>1){
				vo.setQuestionId(Long.parseLong(quota));
			}else{
				vo.setQuestionId(0);
				vo.setMinRebates(new BigDecimal(quota));
			}
			CustomerUser regUser = userService.queryUserById(Long.parseLong(parentId));
			CustomerUser supermanUser = new CustomerUser();
			DozermapperUtil.getInstance().map(regUser, supermanUser);
			supermanUser.setCustomerName(CommonUtil.LINKUSER);
			//regUser.setCustomerName(CommonUtil.PROXYUSER);;
			param.put(CommonUtil.CUSTOMERUSERKEY, supermanUser);
			param.put("customeruserkey", vo);
			vo.setCustomerType(customerType);
			userService.saveOpenLowerUser(param);
			rediAttributes.addFlashAttribute("user", vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, rediAttributes);
			model.put("uservo", vo);
			model.put("parentId", request.getParameter("parentId"));
			model.put("quota", request.getParameter("quota"));
			model.put("memberType", request.getParameter("idsTree"));
			return new ModelAndView("redirect:linkreguser.html?links="+link);
		}
		return new ModelAndView("redirect:../reglogin.html",model);
	}
}
