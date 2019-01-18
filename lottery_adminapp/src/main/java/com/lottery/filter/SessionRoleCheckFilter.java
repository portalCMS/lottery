package com.lottery.filter;


import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lottery.bean.entity.AdminPermissions;
import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.vo.AdminPermissionsVO;
import com.lottery.dao.IAdminUserRoleDao;
import com.lottery.service.IAdminUserService;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.GetProperty;
import com.xl.lottery.util.IPSeeker;


/**
 * Servlet Filter implementation class SessionRoleCheckFilter
 */
@Repository
public class SessionRoleCheckFilter extends HandlerInterceptorAdapter  {

	
		private final String IPPROP = "ipname.properties";
	
		@Autowired
		IAdminUserRoleDao adminUserRoleDao; //用户权限关系表
		
		@Autowired
		private IAdminUserService adminUserService;
	  
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
	  
	    	String IP = request.getRemoteAddr();
	    	IP = IP.replaceAll(":", ".");
			
			String ipwhites = GetProperty.getPropertyByName2(IPPROP, "ipwhites");
			if(ipwhites!= null && !ipwhites.equals("")){
				String[] ips = ipwhites.split(",");
				int iperrorcount = 0;
				for(String str : ips){
					if(!str.trim().equals(IP.trim()))iperrorcount++;
				}
				if(iperrorcount >=ips.length){
					request.getSession();
					response.sendRedirect("http://www.baidu.com");
				}
			}
			
	        request.setCharacterEncoding("UTF-8");  
	        response.setCharacterEncoding("UTF-8");  
	        response.setContentType("text/html;charset=UTF-8");  
	        request.getSession().setAttribute("isAction", "true");
	        // 后台session控制  
	        String[] noFilters = new String[] { "login.do",
	                "loginhome.do", "gohome.do","createPic.do","loginhome.do"};  
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
	                        CommonUtil.USERKEY);  
	                if (null == obj) {  
	                		response.sendRedirect(request.getContextPath()+"/gohome.do");
		                    return false;  
	                } else {  

	    	            /********* 权限管理粒度划分 george***********/
	    	            AdminUser adminUser =(AdminUser) request.getSession().getAttribute(  
	                            CommonUtil.USERKEY);
	    	            String[] uris=uri.split("/");
	    	            if(uris.length>3){
	    	            	   uri="/"+uris[1]+"/"+uris[2]+".do";	
	    	            }
	    	            String  urls  = request.getHeader("Referer");
	    	            List<AdminPermissions> listpermissions=adminUserService.getToPermissions(uri);
	    	            if(listpermissions==null || listpermissions.size()<1){
		    	            	request.getSession().removeAttribute("success");
	    	            }else{
	        	            List<AdminPermissions> list=adminUserService.getRoleUserPermissions(adminUser, uri);
		    	            if(list==null || list.size()<1){
		    	    	        	response.sendRedirect(request.getContextPath()+"/admin/error.do?urls="+urls);
		    	    	        	return false;
		    	            }
	    	            }
	    	            /*****************************************/
	                    // 添加日志  
	                   // String operateContent = Constants.operateContent(uri);  
	                }  
	            }  
	            
//	            List<AdminParameter> listAdminParameter=adminParameterDao.queryAll();
	  
//	        Map paramsMap = request.getParameterMap();  
//	        
//	        for (Iterator<Map.Entry> it = paramsMap.entrySet().iterator(); it  
//	                .hasNext();) {  
//	            Map.Entry entry = it.next();  
//	            Object[] values = (Object[]) entry.getValue();  
//	            for (Object obj : values) {  
////	            	if(obj instanceof String) {
////	            		obj = HtmlUtils.htmlEscape(obj.toString());
////					}
////	                if (!DataUtil.isValueSuccessed(obj)) {  
////	                    throw new RuntimeException("有非法字符：" + obj);  
////	                }  
//	            }  
//	        }  
	  
	        return super.preHandle(request, response, handler);  
	    }

}
