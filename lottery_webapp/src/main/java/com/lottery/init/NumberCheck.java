package com.lottery.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lottery.limit.ElevenSelectedFiveNumCheck;
import com.lottery.limit.FuCai3DNumCheck;
import com.lottery.limit.OfenPlayNumCheck;



public class NumberCheck {

	/**
	 * key selectCode
	 * value 号码集合
	 */
	//public static Map<String, String> fuCai3dMap;
	
	public static Map<String, Map<String, String>> sscMap;
	
	public static Map<String, Map<String, String>> x5Map;
	
	
	public static void init(){
//		if(fuCai3dMap == null){
//			fuCai3dMap = new HashMap<String, String>();
//		}
		if(sscMap == null){
			sscMap = new HashMap<String, Map<String, String>>();
		}
		if(x5Map == null){
			x5Map = new HashMap<String, Map<String, String>>();
		}
		
		OfenPlayNumCheck opnc = new OfenPlayNumCheck();
		for (int i = 40001; i < 40087; i++) {
			List<String> numbers= opnc.sscNum(i + "");
			Map<String, String> tempMap = new HashMap<String, String>();
			for(String number : numbers){
				tempMap.put(number.trim(), "1");
			}
			sscMap.put(i+"", tempMap);
		}
		
		FuCai3DNumCheck fc3 = new FuCai3DNumCheck();
		for (int i = 50001; i < 50046; i++) {
			Map<String, String> tempMap = new HashMap<String, String>();
			List<String> numbers= fc3.fucai3dNum(i + "");
			for(String number : numbers){
				tempMap.put(number.trim(), "1");
			}
			sscMap.put(i+"",tempMap);
		}
		
		ElevenSelectedFiveNumCheck esfn = new ElevenSelectedFiveNumCheck();
		for (int i = 30001; i < 30024; i++) {
			List<String> numbers= esfn.anyChooseNum(i + "");
			Map<String, String> tempMap = new HashMap<String, String>();
			for(String number : numbers){
				tempMap.put(number.trim(), "1");
			}
			sscMap.put(i+"",tempMap);
		}
	}
	
	public static void main(String[] args) {
		NumberCheck.init();
		System.out.println(NumberCheck.sscMap.keySet().toString());
		for(String key : NumberCheck.sscMap.keySet()){
			System.out.println(key);
		}
	}
}
