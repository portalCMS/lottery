package com.lottery.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lottery.annotation.Token;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.SecurityQuestion;
import com.lottery.bean.entity.vo.CustomerUserVO;
import com.lottery.bean.entity.vo.SecurityQuestionVO;
import com.lottery.service.ICustomerUserService;
import com.lottery.service.ISecurityQuestionService;
import com.xl.lottery.exception.LotteryExceptionDictionary;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.MD5Util;
import com.xl.lottery.util.RandDomUtil;

@Controller
@RequestMapping("security")
public class SecurityController extends BaseController{

	@Autowired
	private ISecurityQuestionService secirotuQuestion;
	
	@Autowired
	private ICustomerUserService customerUserService;
	
	@RequestMapping("changepwd")
	public ModelAndView showFindUserPwd(HttpServletRequest request,HttpServletResponse reponse){
//		Map<String, Object> param = new HashMap<String, Object>();
//		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("forget_pwd/check_account");
	}
	
	@RequestMapping("findpwd")
	public ModelAndView showFindPassWord(CustomerUserVO vo,HttpServletRequest request,HttpServletResponse reponse,RedirectAttributes reAttributes){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		String picCode = (String) request.getSession().getAttribute(PictureCheckCodeController.RANDOMCODEKEY);
		if(picCode==null||!picCode.equalsIgnoreCase(vo.getCode())){
			reAttributes.addFlashAttribute("errorMsg", LotteryExceptionDictionary.PICCODEERROR);
			return new ModelAndView("redirect:changepwd.html"); 
		}
		param.put(CommonUtil.CUSTOMERUSERKEY, vo);
		CustomerUser user = null;
		try {
			user = customerUserService.findCustomerUserByName(param);
			if(user == null){
				reAttributes.addFlashAttribute("errorMsg","用户名错误");
				return new ModelAndView("redirect:changepwd.html"); 
			}
			param.put(CommonUtil.CUSTOMERUSERKEY, user);
			List<SecurityQuestion> list;
			list = secirotuQuestion.findSecurityQuestionList(param);
			model.put("sqlist", list);
			request.getSession().setAttribute("sqlist", list);
			request.getSession().setAttribute("temporary", user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("forget_pwd/check_qa",model);
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping("showpwd")
	public ModelAndView changeUserPassWord(SecurityQuestionVO vo,HttpServletRequest request,HttpServletResponse reponse,RedirectAttributes reAttributes){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		List<SecurityQuestion> list = (List<SecurityQuestion>) request.getSession().getAttribute("sqlist");
		if(list==null)return new ModelAndView("redirect:../home.html"); 
		for(SecurityQuestion sq : list){
			if(sq.getId()==vo.getId()){
				if(sq.getAnswer().equals(MD5Util.makeMD5(vo.getAnswer()))){
					String randomPwd = RandDomUtil.generateWord(8, false);
					CustomerUser user = (CustomerUser) request.getSession().getAttribute("temporary");
					user.setCustomerPwd(MD5Util.makeMD5(randomPwd));
					param.put(CommonUtil.CUSTOMERUSERKEY, user);
					try {
						boolean flag = customerUserService.updateCusomerUserPwd(param);
						model.put("randomPwd", randomPwd);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						LotteryExceptionLog.wirteLog(e, model);
					}
					break;
				}else{
					request.setAttribute("errorMsg","答案错误");
					return new ModelAndView("forget_pwd/check_qa"); 
				}
			}
		}
		request.getSession().removeAttribute("temporary");
		request.getSession().removeAttribute("sqlist");
		return new ModelAndView("forget_pwd/done",model);
	}
}
