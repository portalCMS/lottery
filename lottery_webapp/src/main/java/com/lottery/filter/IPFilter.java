package com.lottery.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.GetProperty;
import com.xl.lottery.util.IPSeeker;

/**
 * Servlet Filter implementation class IPFilter1
 */
public class IPFilter implements Filter {

	private final String IPPROP = "ipname.properties";
    /**
     * Default constructor. 
     */
    public IPFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletResponse newresponse = (HttpServletResponse) response;
		HttpServletRequest newrequest = (HttpServletRequest) request;
		String isBlacknames = GetProperty.getPropertyByName2(IPPROP, "isBlacknames");
    	boolean flag = false;
    	String IP = newrequest.getRemoteAddr();
    	IP = IP.replaceAll(":", ".");
    	String coutry = IPSeeker.getInstance().getCountry(IP);
    	if(isBlacknames.equals("yes")){
    		String blacknames = GetProperty.getPropertyByName2(IPPROP, "blacknames");
    		String[] coutrys = blacknames.split(",");
    		for(String str:coutrys){
    			if(coutry.indexOf(str)>-1){
    				flag = true;
    				break;
    			}
    		}
    	}else{
    		String whitenames = GetProperty.getPropertyByName2(IPPROP, "whitenames");
    		String[] coutrys = whitenames.split(",");
    		for(String str:coutrys){
    			if(coutry.indexOf(str)>0){
    				flag = false;
    				break;
    			}
    		}
    	}
		if (flag) {  
			newresponse.sendRedirect("http://www.baidu.com");
        }
		newrequest.setCharacterEncoding("UTF-8");
		newresponse.setCharacterEncoding("UTF-8");
		newresponse.setContentType("text/html;charset=UTF-8");
		// session控制
		String[] noFilters = new String[] {"showLeftMenuBar.html","showloginmp.html", "loginmp.html","login.html", "home.html",
				"gohome.html", "createPic.html", "findpwd.html",
				"showpwd.html", "changepwd.html", "getPicCode.shtml" ,
				"reguser.html","addreguser.html","ycode.html","otherPayCallBack.html","getpeos.shtml",
				"linkreguser.html","linkOpenAccount.html","refreshOpenNumResult.shtml","refreshResult.shtml",
				"killUserByAdmin.shtml","showcheck.html","checkUrl.shtml","getUserIp.shtml","chome.html","chomeNew.html","otherybPayCallBack.html"};
		String uri = newrequest.getServletPath();
		boolean beFilter = true;
		for (String s : noFilters) {
			if (uri.indexOf(s) != -1) {
				beFilter = false;
				break;
			}
		}
		if (beFilter) {
			Object obj = newrequest.getSession().getAttribute(
					CommonUtil.CUSTOMERUSERKEY);
			if (null == obj) {
				newresponse.sendRedirect(newrequest.getContextPath() + "/gohome.html");
				return;
			}
		}
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
