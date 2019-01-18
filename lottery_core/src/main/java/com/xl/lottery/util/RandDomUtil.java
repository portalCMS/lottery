package com.xl.lottery.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class RandDomUtil {

	private static String[] beforeShuffle = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z","a","b","c","d","e","f","g","h","i","j","k","l","m","n",
			"o","p","q","r","s","t","u","v","w","x","y","z"};
	
	private static String[] beforeShufflenum = new String[] { "1","2","3","4","5","6","A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V","7","8","9",
			"W", "X", "Y", "Z","a","b","c","d","e","f","g","h","i","j","k","l","m","n",
			"o","p","q","r","s","t","u","v","w","x","y","z"};
	
	/**
	 * 如果type=1返回纯数字，type=2返回存字母，type=3返回字母加数字
	 * @param num
	 * @param type
	 * @return
	 */
	public static String getRandomStr(int num,String type){
		if(type.equals("1")){
			return getRandomNum(num);
		}else if(type.equals("2")){
			return generateWord(num, true);
		}else{
			return generateWord(num, false);
		}
	}
	
	public static String getRandomNum(int num) {
		String[] digits = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
		Random rnum = new Random(new Date().getTime());
		for (int i = 0; i < digits.length; i++) {
			int index = Math.abs(rnum.nextInt()) % 10;
			String tmpDigit = digits[index];
			digits[index] = digits[i];
			digits[i] = tmpDigit;
		}

		String returnStr = digits[0];
		for (int i = 1; i < num; i++) {
			returnStr = digits[i] + returnStr;
		}
		return returnStr;
	}

	/**
	 * 
	 * @param flag true 生成4位纯英文随机字符 false数字和字母混合
	 * @return
	 */
	public static String generateWord(int num,boolean flag) {
	
		List list = null;
		if(flag){
			list = Arrays.asList(beforeShuffle);
		}else{
			list = Arrays.asList(beforeShufflenum);
		}
		Collections.shuffle(list);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}
		String afterShuffle = sb.toString();
		String result = afterShuffle.substring(5, 5+num);
		return result;
	}

	public static String getRandomBetNum(int num) {
		String[] digits = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "00","10","11" };
		Random rnum = new Random(Long.parseLong(Double.toString(Math.round(Math.random()*10000)).split("\\.")[0]));
		for (int i = 0; i < digits.length; i++) {
			int index = Math.abs(rnum.nextInt()) % 10;
			String tmpDigit = digits[index];
			digits[index] = digits[i];
			digits[i] = tmpDigit;
		}

		String returnStr = digits[0];
		for (int i = 1; i < num; i++) {
			returnStr = digits[i] +"," + returnStr;
		}
		return returnStr;
	}
	
	public static String get11X5RandomBetNum(int num,int min,int max) {
		StringBuffer lotNum=new StringBuffer("");
		while(true){
			String[] lots = lotNum.toString().split(",");
			if(lots.length==5){
				break;
			}
	        Random random = new Random();
	        Integer s = random.nextInt(max)%(max-min+1) + min;
	        if(lotNum.toString().equals("")){
	        	if(s<10){
	        		lotNum.append("0"+s);
	        	}else{
	        		lotNum.append(s);
	        	}
	        }else{
	        	boolean isContinue =false;
	        	for(int k=0;k<lots.length;k++){
	        		if(Integer.parseInt(lots[k])==s){
	        			isContinue = true;
	        			break;
	        		}
	        	}
	        	if(isContinue){
	        		continue;
	        	}
	        	if(s<10){
	        		lotNum.append(",0"+s);
	        	}else{
	        		lotNum.append(","+s);
	        	}
	        }
		}
		return lotNum.toString();
	}
	
	public static String getSSCRandomBetNum(int num,int min,int max) {
		StringBuffer lotNum=new StringBuffer("");
		while(true){
			String[] lots = lotNum.toString().split(",");
			if(lots.length==num){
				break;
			}
	        Random random = new Random();
	        Integer s = random.nextInt(max)%(max-min+1) + min;
	        if(lotNum.toString().equals("")){
	        		lotNum.append(s);
	        }else{
	        	lotNum.append(","+s);
	        }
		}
		return lotNum.toString();
	}
	
	public static String get3DRandomBetNum(int num,int min,int max) {
		StringBuffer lotNum=new StringBuffer("");
		while(true){
			String[] lots = lotNum.toString().split(",");
			if(lots.length==num){
				break;
			}
	        Random random = new Random();
	        Integer s = random.nextInt(max)%(max-min+1) + min;
	        if(lotNum.toString().equals("")){
	        		lotNum.append(s);
	        }else{
	        	lotNum.append(","+s);
	        }
		}
		return lotNum.toString();
	}
	
	public static void main(String[] args) {
		for(int i=0;i<10;i++){
			//System.out.println("11选5："+RandDomUtil.get11X5RandomBetNum(5,1,11));
			System.out.println("时时彩："+RandDomUtil.getSSCRandomBetNum(5,0,10));
		}
	}
}
