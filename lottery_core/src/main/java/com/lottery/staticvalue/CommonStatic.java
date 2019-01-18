package com.lottery.staticvalue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonStatic {

	private CommonStatic(){
		
	}
	
	private static Map<String,Object> codeMap = new HashMap<String, Object>();
	private static Map<String,Object> codeObjectMap = new HashMap<String, Object>();
	
	/**
	 * url List
	 */
	public static List<Object> urlObject = new ArrayList<Object>();
	
	public static final String DATADICTIONARY_HEAD = "D_";
	public static final String LOTTERYTYPE_HEAD = "L_";
	public static final String PLAYMODEL_HEAD = "P_";
	public static final String LOTTERYPLAYSELECT_HEAD = "S_";
	
	public static String webws = "";
	public static String jobws = "";
	
	public static Map<String,Object> getCodeMap(){
		return codeMap;
	}
	
	public static Map<String,Object> getCodeObjectMap(){
		return codeObjectMap;
	}
}
