package com.lottery.filter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lottery.bean.entity.vo.TempMapVO;

public class WebAppStaticCollection {
	/**
	 * 存储每个彩种的最新开奖号码
	 */
	private static Map<String, TempMapVO> openNumMap = new HashMap<String, TempMapVO>();
	/**
	 * 存储奖期限号拦截
	 */
	private static Map<String, Map<String,BigDecimal>> limitMap = new HashMap<String, Map<String,BigDecimal>>();
	
	/**
	 * 奖期限号列表
	 */
	private static Map<String,Set<String>> limitNumberMap = new HashMap<>();
	
	/**
	 * 动态限号
	 */
	private static Map<String,BigDecimal> activityLimitNumberMap = new HashMap<String, BigDecimal>();
	
	public static Map<String, Map<String, BigDecimal>> getLimitMap() {
		return limitMap;
	}

	public static void setLimitMap(Map<String, Map<String, BigDecimal>> limitMap) {
		WebAppStaticCollection.limitMap = limitMap;
	}

	public static Map<String, Set<String>> getLimitNumberMap() {
		return limitNumberMap;
	}

	public static void setLimitNumberMap(Map<String, Set<String>> limitNumberMap) {
		WebAppStaticCollection.limitNumberMap = limitNumberMap;
	}

	public static Map<String, BigDecimal> getActivityLimitNumberMap() {
		return activityLimitNumberMap;
	}

	public static void setActivityLimitNumberMap(
			Map<String, BigDecimal> activityLimitNumberMap) {
		WebAppStaticCollection.activityLimitNumberMap = activityLimitNumberMap;
	}
	public static Map<String, TempMapVO> getOpenNumMap() {
		return openNumMap;
	}

	public static void setOpenNumMap(Map<String, TempMapVO> openNumMap) {
		WebAppStaticCollection.openNumMap = openNumMap;
	}

	
}
