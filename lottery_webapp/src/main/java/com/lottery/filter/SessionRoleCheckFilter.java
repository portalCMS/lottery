package com.lottery.filter;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xl.lottery.util.CommonUtil;

/**
 * Servlet Filter implementation class SessionRoleCheckFilter
 */
@Repository
public class SessionRoleCheckFilter extends HandlerInterceptorAdapter {

	// private SystemLoggerService systemLoggerService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		// 后台session控制
		String[] noFilters = new String[] { "login.html", "home.html",
				"gohome.html", "createPic.html", "findpwd.html",
				"showpwd.html", "changepwd.html", "getPicCode.shtml" };
		String uri = request.getServletPath();
		boolean beFilter = true;
		for (String s : noFilters) {
			if (uri.indexOf(s) != -1) {
				beFilter = false;
				break;
			}
		}
		if (beFilter) {
			Object obj = request.getSession().getAttribute(
					CommonUtil.CUSTOMERUSERKEY);
			if (null == obj) {
				response.sendRedirect(request.getContextPath() + "/gohome.html");
				return false;
			} else {
				// 添加日志
				// String operateContent = Constants.operateContent(uri);
			}
		}

		return super.preHandle(request, response, handler);
	}

}
