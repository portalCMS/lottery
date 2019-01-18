package com.xl.lottery.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Hex;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class LotWebUtil {
	//记录所有登陆账号
	private static Map<String, HttpSession> sessionMap = new HashMap<String, HttpSession>();
	
	
	public static Map<String, HttpSession> getSessionMap(){
		return sessionMap;
	}


	public static String entryptPassword(String plainPassword) {
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
		    byte[] md5 = md.digest(plainPassword.getBytes());
		    String pass = new String(Hex.encodeHex(md5));
		    return pass;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	


}
