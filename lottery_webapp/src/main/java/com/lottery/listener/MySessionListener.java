package com.lottery.listener;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.servlet.WebSocketMessageInboundPool;
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
		CustomerUser user = (CustomerUser)session.getAttribute(CommonUtil.CUSTOMERUSERKEY);
		if (session != null) {
			if(map!=null&&map.get(user.getCustomerName())!=null&&map.get(user.getCustomerName())==session){
				map.remove(user.getCustomerName());
			}else{
				log.info("已有其他session填入sessionMap");
			}
		}
		
//		Set<String> keys = map.keySet();
//		for(String key:keys){
//			if(map.get(key)==null){
//				map.remove(key);
//			}
//		}
//		int sessions = map.keySet().size();
		StringBuffer uns = new StringBuffer("");
		for(String un : map.keySet()){
			if(uns.length()==0){
				uns.append(un);
			}else{
				uns.append(","+un);
			}
		}
		//WebSocketMessageInboundPool.sendMessage("onLineUserCount:"+sessions+";onLineUserNames:"+uns);
	}

}
