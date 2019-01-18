package com.xl.lottery.util;

import java.math.BigDecimal;

/**
 * 四舍六入五成双
 * @author CW-HP7
 *
 */
public class AlgorithmCore {

	private static int n = 4;//保留小数位
	
	public static void main(String[] args) {
		System.out.println(AlgorithmCore.core(new BigDecimal(12.000021215155 / 12121.0054515154)));

	}
	
	public static BigDecimal core(BigDecimal num){
		double tempdouble = num.doubleValue();
		num=num.setScale(n+2,BigDecimal.ROUND_DOWN);
		num=num.movePointRight(n);
		String temp = num.toString();
		String[] strs = temp.split("\\.");
		if(Integer.parseInt(strs[1])==0){
			num=num.movePointLeft(n);
			return num.setScale(n,BigDecimal.ROUND_DOWN);
		}
		int integer = Integer.parseInt(strs[1])/10;
		int decimal = Integer.parseInt(strs[1])%10;
		/**
		 * 被修约的数字等于或大于6时，则进位；
		 */
		if(integer>5){
			num = num.add(new BigDecimal(1));
			num=num.movePointLeft(n);
			return num.setScale(n,BigDecimal.ROUND_DOWN);
		}
		/**
		 * 被修约的数字等于或小于4时，该数字舍去；
		 */
		if(integer<5){
			num=num.movePointLeft(n);
			return num.setScale(n,BigDecimal.ROUND_DOWN);
		}
		/**
		 * 若5的后面还有不为“0”的任何数，则此时无论5的前面是奇数还是偶数，均应进位
		 */
		if(integer==5&&(tempdouble-(int)tempdouble)>0.5){
			num = num.add(new BigDecimal(1));
			num=num.movePointLeft(n);
			return num.setScale(n,BigDecimal.ROUND_DOWN);
		}
		/**
		 * 被修约的数字等于5时，要看5前面的数字，若是奇数则进位，若是偶数则将5舍掉，即修约后末尾数字都成为偶数；
		 */
		if(integer==5&&decimal==0){
			if(Integer.parseInt(strs[0])%2==0){
				num=num.movePointLeft(n);
				return num.setScale(n,BigDecimal.ROUND_DOWN);
			}else{
				num = num.add(new BigDecimal(1));
				num=num.movePointLeft(n);
				return num.setScale(n,BigDecimal.ROUND_DOWN);
			}
		}
		return num;
		
	}
}
