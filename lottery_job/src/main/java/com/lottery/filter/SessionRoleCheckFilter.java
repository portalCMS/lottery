package com.lottery.filter;


import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.HtmlUtils;

import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.GetProperty;


/**
 * Servlet Filter implementation class SessionRoleCheckFilter
 */
@Repository
public class SessionRoleCheckFilter extends HandlerInterceptorAdapter  {
	
		private final String IPPROP = "ipname.properties";

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
		                    // 添加日志  
		                   // String operateContent = Constants.operateContent(uri);  
		                }  
		            }  
		          
		  
		        Map paramsMap = request.getParameterMap();  
		        
		        for (Iterator<Map.Entry> it = paramsMap.entrySet().iterator(); it  
		                .hasNext();) {  
		            Map.Entry entry = it.next();  
		            Object[] values = (Object[]) entry.getValue();  
		            for (Object obj : values) {  
//		            	if(obj instanceof String) {
//		            		obj = HtmlUtils.htmlEscape(obj.toString());
//						}
//		                if (!DataUtil.isValueSuccessed(obj)) {  
//		                    throw new RuntimeException("有非法字符：" + obj);  
//		                }  
		            }  
		        }  

	  
	        return super.preHandle(request, response, handler);  
	    }

}
