package com.lottery.servlet;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WebSocketMessageInboundPool {

	//保存连接的MAP容器
	public static final Map<String,WebSocketMessageInbound > connections = new HashMap<String,WebSocketMessageInbound>();
	
	//向连接池中添加连接
	public static void addMessageInbound(WebSocketMessageInbound inbound){
		//添加连接
		connections.put(inbound.getUser(), inbound);
	}
	
	//获取所有的在线用户
	public static Set<String> getOnlineUser(){
		return connections.keySet();
	}
	
	public static void removeMessageInbound(WebSocketMessageInbound inbound){
		//移除连接
		connections.remove(inbound.getUser());
	}
	
	public static void sendMessageToUser(String user,String message){
		try {
			//向特定的用户发送数据
			WebSocketMessageInbound inbound = connections.get(user);
			if(inbound != null){
				inbound.getWsOutbound().writeTextMessage(CharBuffer.wrap(message));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//向所有的用户发送消息
	public static void sendMessage(String message){
		try {
			Set<String> keySet = connections.keySet();
			for (String key : keySet) {
				WebSocketMessageInbound inbound = connections.get(key);
				if(inbound != null){
					inbound.getWsOutbound().writeTextMessage(CharBuffer.wrap(message));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
