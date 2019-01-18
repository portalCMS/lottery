package com.xl.lottery.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ApplicationContextUtil {

	private static ApplicationContext factory = null;
	
	private static ApplicationContext getFactory(){
		if(factory == null){
			factory = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		}
		return factory;
	}
	
	public static Object getBean(String beanId){
		return ApplicationContextUtil.getFactory().getBean(beanId);
	}
	
	public static Object getBean(Class clz){
		return ApplicationContextUtil.getFactory().getBean(clz);
	}
	
	public static void setApplicationContext(ApplicationContext factory){
		ApplicationContextUtil.factory = factory;
	}
}
