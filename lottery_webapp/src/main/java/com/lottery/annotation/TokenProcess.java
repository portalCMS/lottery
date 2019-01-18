package com.lottery.annotation;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xl.lottery.util.CommonUtil;

public class TokenProcess{
	private static Logger logger = LoggerFactory.getLogger(TokenProcess.class);
    static Map<String, String> springmvc_token = new HashMap<String, String>();
        //生成一个唯一值的token
    @SuppressWarnings("unchecked")
    public synchronized static String generateGUID(HttpSession session) {
        String token = "";
        try {
            Object obj =  session.getAttribute("SPRINGMVC.TOKEN");
            if(obj!=null){
            	 springmvc_token = (Map<String,String>)session.getAttribute("SPRINGMVC.TOKEN");
                 token = new BigInteger(165, new Random()).toString(36)
                         .toUpperCase();
                 springmvc_token.put(CommonUtil.DEFAULT_TOKEN_NAME + "." + token,token);
                 session.setAttribute("SPRINGMVC.TOKEN", springmvc_token);
            }else{
            	 token = new BigInteger(165, new Random()).toString(36)
                         .toUpperCase();
                 springmvc_token.put(CommonUtil.DEFAULT_TOKEN_NAME + "." + token,token);
                 session.setAttribute("SPRINGMVC.TOKEN", springmvc_token);
            }
               
            //Constants.TOKEN_VALUE = token;
 
        } catch (IllegalStateException e) {
            logger.error("generateGUID() mothod find bug,by token session...");
        }
        return token;
    }
    
    @SuppressWarnings("unchecked")
    public synchronized static String generateGUID() {
        String token = "";
        token = new BigInteger(165, new Random()).toString(36)
                .toUpperCase();
        return token;
    }
 }
