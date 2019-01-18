package com.xl.lottery.desutil;

import com.xl.lottery.util.MD5Util;



public class Md5Manage {

	private static Md5Manage instance;
	
	private String key = "";
	
	private Md5Manage(){
		String temp = "我是大胡子";
		this.key = MD5Util.makeMD5(temp);
	}
	
	private Md5Manage(int isCard){
		String temp = "cardpwd中奖!@#$%";
		this.key = MD5Util.makeMD5(temp);
	}
	
	public static synchronized Md5Manage getInstance() {  
        if (instance == null) {  
            instance = new Md5Manage();  
        }  
        return instance;  
    }
	
	public String getMd5(){
		return this.key;
	}
	
}
