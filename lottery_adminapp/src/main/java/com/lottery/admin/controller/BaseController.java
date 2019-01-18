package com.lottery.admin.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import com.lottery.bean.entity.AdminUser;
import com.lottery.dao.IOrderSequenceDao;
import com.lottery.service.IAdminUserService;
import com.lottery.service.IOrderSequenceService;
import com.xl.lottery.exception.LotteryExceptionDictionary;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.StringEscapeEditor;

public abstract class BaseController {

	@Autowired
	private IAdminUserService adminUserService;

	@Autowired
	protected IOrderSequenceService orderSequenceService;
	
	/**
	 * 权限密码及图片验证码检查
	 * 
	 * @param request
	 * @param model
	 * @param picKey
	 * @param piCode
	 * @param redirectUrl
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	protected ModelAndView checkRolePwdAndPiCode(HttpServletRequest request,
			Map<String, Object> model, String rolePwd,String picKey, String piCode,
			String redirectUrl) throws UnsupportedEncodingException {
		// 图片验证码
		String picCode = (String) request.getSession().getAttribute(picKey);
		if (picCode == null || !picCode.equalsIgnoreCase(piCode)) {
			model.put("errorMsg",LotteryExceptionDictionary.PICCODEERROR);
			return new ModelAndView(redirectUrl, model);
		}

		AdminUser adminUser = (AdminUser) request.getSession().getAttribute(
				CommonUtil.USERKEY);
		try {
			boolean isRight = adminUserService.checkRolePassword(
					adminUser.getUserName(), rolePwd);
			if (!isRight) {
				model.put("errorMsg","权限密码不正确，请重新输入！");
				return new ModelAndView(redirectUrl, model);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			model.put("errorMsg","查询权限密码系统异常！");
			LotteryExceptionLog.wirteLog(e1, model);
			return new ModelAndView(redirectUrl, model);
		}
		return null;
	}

	/**
	 * 网站sql注入，script注入等的防护。
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder,HttpServletRequest request) {
		binder.setAutoGrowCollectionLimit(5000);
		binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false, false));
	}
}
