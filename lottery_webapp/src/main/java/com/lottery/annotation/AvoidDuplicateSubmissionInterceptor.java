package com.lottery.annotation;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lottery.bean.entity.CustomerUser;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.CommonUtil;

/**
 * <p>
 * 防止重复提交过滤器
 * </p>
 *
 */
public class AvoidDuplicateSubmissionInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(AvoidDuplicateSubmissionInterceptor.class);
 
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
 
        CustomerUser user =(CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
        if (user != null) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
 
            //有token注解即要进行token值得检查
            Token annotation = method.getAnnotation(Token.class);
            if (annotation != null) {
            	//生成token随机值
                boolean needSaveSession = annotation.needSaveToken();
                if (needSaveSession) {
                    request.getSession().setAttribute("token", TokenProcess.generateGUID());
                }
                //获取页面token值，与session中存储的token值进行对比。
                boolean needRemoveSession = annotation.needRemoveToken();
                if (needRemoveSession) {
                    if (isRepeatSubmit(request)) {
                        LOG.error("please don't repeat submit,[user:" + user.getCustomerName() + ",url:"
                                + request.getServletPath() + "]");
                        request.getSession().setAttribute("token", TokenProcess.generateGUID());
                        String url = request.getHeader("Referer"); //获得前一页的URL
                        if(request.getServletPath().indexOf(".shtml")!=-1){
                        	request.setAttribute("tokenError", "亲，请不要重复提交数据，请刷新页面重试！");
                        	 return true;
                        }
                        if(url.indexOf("?")==-1){
                        	response.sendRedirect(url+"?tokenError=true");
                        }else{
                        	if(url.indexOf("tokenError")>-1){
                          		 response.sendRedirect(url);
                          	 }else{
                          		 response.sendRedirect(url+"&tokenError=true");
                          	 }
                         }
                        
                        return false;
                    }
                    request.getSession().removeAttribute("token");
                    request.getSession().setAttribute("token", TokenProcess.generateGUID());
                }
            }
        }
        return true;
    }
 
    /**
     * 判断token值是否一致
     * @param request
     * @return
     */
    private boolean isRepeatSubmit(HttpServletRequest request) {
        String serverToken = (String) request.getSession(false).getAttribute("token");
        if (serverToken == null) {
            return true;
        }
        String clinetToken = request.getParameter("token");
        if (clinetToken == null) {
            return true;
        }
        if (!serverToken.equals(clinetToken)) {
            return true;
        }
        return false;
    }
 
}
