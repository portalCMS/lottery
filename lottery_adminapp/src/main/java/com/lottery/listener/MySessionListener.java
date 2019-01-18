package com.lottery.listener;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lottery.bean.entity.AdminUser;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.LotWebUtil;

public class MySessionListener implements HttpSessionListener {

	private Logger log = LoggerFactory.getLogger(MySessionListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent se) {
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		Map<String, HttpSession> map = LotWebUtil.getSessionMap();
		HttpSession session = se.getSession();
		AdminUser user = (AdminUser)session.getAttribute(CommonUtil.USERKEY);
		if (session != null) {
			if(map.get(user.getUserName())==session){
				map.remove(user.getUserName());
			}else{
				log.info("已有其他session填入sessionMap");
			}
		}
	}

}
