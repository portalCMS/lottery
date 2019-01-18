package com.lottery.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lottery.service.IAdminRoleInitService;
import com.lottery.service.IAdminRoleService;
import com.xl.lottery.util.ApplicationContextUtil;
import com.xl.lottery.util.CommonUtil;

/**
 * Servlet implementation class RolePermissionsServlet
 */
@Component
public class RolePermissionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RolePermissionsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	@SuppressWarnings("resource")
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	   ApplicationContext ac = WebApplicationContextUtils
	                .getWebApplicationContext(config.getServletContext());
	   ApplicationContextUtil.setApplicationContext(ac);
		IAdminRoleInitService adminRoleService = (IAdminRoleInitService)ApplicationContextUtil.getBean("adminRoleInitService");
		HashMap<String, Object> roleMap = (HashMap<String, Object>) adminRoleService.findAdminRoleList();
		config.getServletContext().setAttribute(CommonUtil.ROLEKEY, roleMap);
		//加载全局路径
		config.getServletContext().setAttribute("contextPath", config.getServletContext().getContextPath());
		System.err.println("权限加载完毕");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
