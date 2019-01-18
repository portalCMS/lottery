package com.lottery.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.vo.AdminUserVO;
import com.lottery.service.IAdminUserService;
import com.xl.lottery.exception.LotteryExceptionDictionary;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.LotWebUtil;




/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 */
@Controller
public class LoginController extends BaseController{

	@Autowired
	private IAdminUserService adminUserService;
	
	@RequestMapping(value="/login")
	public ModelAndView showIndex(AdminUserVO admin,HttpServletRequest request, HttpServletResponse response
			,RedirectAttributes attrs){
		 ModelMap model = new ModelMap();
		HttpSession session = LotWebUtil.getSessionMap().get(admin.getAdminName());
		HttpSession sessionnew = request.getSession();
		if(session!=null&&(session!=sessionnew)){
			try {
				session.invalidate();
			} catch (Exception e) {
				// TODO: handle exception
				LotWebUtil.getSessionMap().remove(admin.getAdminName());
			}
		}
		//图片验证码
		String picCode = (String) sessionnew.getAttribute(PictureCheckCodeController.RANDOMCODEKEY);
		if(picCode==null||!picCode.equalsIgnoreCase(admin.getPicCode())){
			request.setAttribute("errorMsg", LotteryExceptionDictionary.PICCODEERROR);
			return new ModelAndView("login"); 
		}
		sessionnew.removeAttribute(PictureCheckCodeController.RANDOMCODEKEY);
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("username", admin.getAdminName());
		param.put("userpwd", admin.getAdminPwd());
		try {
			AdminUser user = adminUserService.checkAdminUser(param);
			if(user==null||user.getUserName()==null){
				request.setAttribute("errorMsg", LotteryExceptionDictionary.LOGINERROR);
				return new ModelAndView("login"); 
			}
			
			//特殊用户才能操作JOB
			if(user.getUserGroup() != 0){
				request.setAttribute("errorMsg", LotteryExceptionDictionary.LOGINERROR);
				return new ModelAndView("login"); 
			}
			
//			if(user!=null&&user.getUserError()>=3){
//				request.setAttribute("errorMsg", LotteryExceptionDictionary.LOGINERRORCOUNT);
//				return new ModelAndView("login"); 
//			}
			
			sessionnew.setAttribute(CommonUtil.USERKEY, user);
			user.setIp(request.getRemoteAddr());
			LotWebUtil.getSessionMap().put(user.getUserName(), sessionnew);
			
			attrs.addFlashAttribute("userName", user.getUserName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
			return new ModelAndView("login"); 
		}
		
		return new ModelAndView("redirect:initJobs.do");
	}
	
	
	@RequestMapping("gohome")
	public ModelAndView showlogin(String error,HttpServletRequest request, HttpServletResponse response) throws Exception{
		if(error!=null&&error.equals("true")){
			request.getSession().invalidate();
			request.setAttribute("errorMsg", "注销成功");
		}else{
			request.setAttribute("errorMsg", "Session超时");
		}
		return new ModelAndView("login");
	}
	
	@RequestMapping("/loginhome")
	public ModelAndView loginhome(HttpServletRequest request, HttpServletResponse response){
		
		return new ModelAndView("login");
	}
	
	@RequestMapping("/loginout")
	public ModelAndView loginout(HttpServletRequest request, HttpServletResponse response,RedirectAttributes reAttributes){
		HttpSession session = request.getSession();
		session.invalidate();
		return new ModelAndView("redirect:loginhome.do");
	}


	/**
	 * 获取当前用户的账户余额
	 * @param lotteryVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getUserName")
	@ResponseBody
	public Map<String,Object> getUserName(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String, Object>(); 
		AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		model.put("userName", user.getUserName());
		return model;
	}
}
