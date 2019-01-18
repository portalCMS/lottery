package com.lottery.core.play;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.xl.lottery.util.DataDictionaryUtil;

public abstract class GenericLottery {

	private static Map<String,String> lotteyMap = new HashMap<String, String>();
	
	static{
		lotteyMap.put("jxssc", "com.lottery.core.play.OftenPlay");
		lotteyMap.put("cqssc", "com.lottery.core.play.OftenPlay");
		lotteyMap.put(DataDictionaryUtil.SYXW_GD_LOTTERY_CODE, "com.lottery.core.play.ElevenSelectedFivePlay");
	}
	
	public static boolean checkLotteryStatus(String lotteryCode,String type,String bile,String betNum,String lotteryNum){
		try {
			GenericLottery g = (GenericLottery)Class.forName(lotteyMap.get(lotteryCode)).newInstance();
			String status = g.invoke(Integer.parseInt(type), bile, betNum, lotteryNum);
			if(status.equals("success"))return true;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean checkLotteryStatus(String code,String type,String betNum,String lotteryNum){
		try {
			GenericLottery g = (GenericLottery)Class.forName(lotteyMap.get(code)).newInstance();
			String status = g.invoke(Integer.parseInt(type), "", betNum, lotteryNum);
			if(status.equals("success"))return true;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public abstract String invoke(int type,String bile,String betNum,String lotteryNum);
}
