package com.lottery.core.play;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xl.lottery.core.algorithm.Combination;
import com.xl.lottery.util.CombineUtil;
import com.xl.lottery.util.RandDomUtil;

public class PlayUtil {

	/**
	 * Cm/n=m!/(n!*(m-n)!)
	 * 
	 * @param args
	 */
	public static int getCombinationNote(int m, int n) {
		int c = 0;
		int mfactorial = factorial(m);
		int nfactorial = factorial(n);
		int mnfactorial = factorial(m - n);
		c = mfactorial / (nfactorial * mnfactorial);
		return c;
	}

	private static int factorial(int x) {
		int factorial = 1;
		for (int i = x; i >= 1; i--) {
			factorial *= i;
		}
		return factorial;
	}

	/**
	 * 任选中奖判断 普通 11X5
	 * 
	 * @param few
	 * @param bet
	 * @return
	 */
	public static String anyChooseX(int few, String betNum, String lotteryNum) {
		int awardcount = 0;
		String[] betTemp = betNum.split(",");
		int n = lotteryNum.split(",").length;
		Combination comb = new Combination(betTemp.length, few);
		while (comb.hasMore()) {
			int[] index = comb.getNext();
			String bet = "";
			for (int i = 0; i < betTemp.length; i++) {
				if (index[i] != 0) {
					if (bet.equals("")) {
						bet = bet.concat(betTemp[index[i] * i].trim());
					} else {
						bet = bet.concat(",").concat(
								betTemp[index[i] * i].trim());
					}
				}
			}
			String[] betArranage = bet.split(",");
			int betfew = 0;
			if (few < n) {
				String[] strtemp = lotteryNum.trim().split(",");
				for (int i = 0; i < strtemp.length; i++) {
					if (bet.indexOf(strtemp[i]) > -1)
						betfew++;
				}
				if (betfew >= few)
					awardcount++;
			} else {
				for (String betStr : betArranage) {
					if (lotteryNum.trim().indexOf(betStr.trim()) > -1)
						betfew++;
				}
				if (betfew >= n)
					awardcount++;
			}

		}
		return 1 + "," + awardcount;
	}

	// /**
	// * 验证任选普通是否中奖
	// * @param few
	// * @param betNum
	// * @param lotteryNum
	// * @return
	// */
	// public static boolean anyChooseX(String betNum,String lotteryNum){
	// String[] betTemp = betNum.split(",");
	// int betfew = 0;
	// for(String betStr:betTemp){
	// if(lotteryNum.trim().indexOf(betStr.trim())>-1)betfew++;
	// }
	// if(betfew>=lotteryNum.split(",").length)return true;
	// return false;
	// }

	/**
	 * 验证任选普通是否中奖注数
	 * 
	 * @param few
	 * @param groupfew
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public static int anyChooseLotteryNoteX(int few, String betNum,
			String lotteryNum) {
		int lotteryNote = 0;
		String[] betTemp = betNum.split(",");
		Combination comb = new Combination(betTemp.length, few);
		while (comb.hasMore()) {
			int[] index = comb.getNext();
			String bet = "";
			for (int i = 0; i < betTemp.length; i++) {
				if (index[i] != 0) {
					if (bet.equals("")) {
						bet = bet.concat(betTemp[index[i] * i].trim());
					} else {
						bet = bet.concat(",").concat(
								betTemp[index[i] * i].trim());
					}
				}
			}
			String[] betArranage = bet.split(",");
			int betfew = 0;
			for (String betStr : betArranage) {
				if (lotteryNum.trim().indexOf(betStr.trim()) > -1)
					betfew++;
			}
			if (betfew >= lotteryNum.split(",").length)
				lotteryNote++;
		}
		return lotteryNote;
	}

	/**
	 * 任选胆拖是否中奖
	 * 
	 * @param bile
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public static String anyChooseBileDragX(int few, String bile,
			String betNum, String lotteryNum) {
		int awardcount = 0;
		//String[] betTemp = lotteryNum.split(",");
		//胆码不中则直接退出
		String[] biles = bile.trim().split(",");
		int right = 0;
		for(String b:biles){
			if(b.trim().equals(""))continue;
			if(lotteryNum.indexOf(b)>-1)right++;
		}
		if(right==0){
			return 1 + "," + 0;
		}
		//胆码不中则直接退出 end
		int betfew = 0;
		int n = few - bile.split(",").length;
		Combination comb = new Combination(betNum.split(",").length, n);
		while (comb.hasMore()) {
			int[] index = comb.getNext();
			String bet = "";
			for (int i = 0; i < betNum.split(",").length; i++) {
				if (index[i] != 0) {
					if (bet.equals("")) {
						bet = bet
								.concat(betNum.split(",")[index[i] * i].trim());
					} else {
						bet = bet.concat(",").concat(
								betNum.split(",")[index[i] * i].trim());
					}
				}
			}
			bet = bet.concat(",").concat(bile);
			String[] betArranage = bet.split(",");
			if (few < 5) {
				String[] strtemp = lotteryNum.trim().split(",");
				for (int i = 0; i < strtemp.length; i++) {
					if (bet.indexOf(strtemp[i]) > -1)
						betfew++;
				}
				if (betfew >= few)
					awardcount++;
			} else {
				for (String betStr : betArranage) {
					if(betStr.equals(""))break;
					if (lotteryNum.trim().indexOf(betStr.trim()) > -1)
						betfew++;
				}
				if (betfew == lotteryNum.split(",").length)
					awardcount++;
			}
			betfew = 0;
		}
		return 1 + "," + awardcount;
	}

	/**
	 * 验证任选胆拖是否中奖注数
	 * 
	 * @param few
	 * @param groupfew
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public static int anyChooseLotteryBileDragNoteX(int few, String bile,
			String betNum, String lotteryNum) {
		int lotteryNote = 0;
		String[] betTemp = betNum.split(",");
		int n = betTemp.length - bile.split(",").length;
		if (few < betTemp.length)
			n = few - bile.split(",").length;
		Combination comb = new Combination(betNum.split(",").length, n);
		while (comb.hasMore()) {
			int[] index = comb.getNext();
			String bet = "";
			for (int i = 0; i < betTemp.length; i++) {
				if (index[i] != 0) {
					if (bet.equals("")) {
						bet = bet.concat(betTemp[index[i] * i].trim());
					} else {
						bet = bet.concat(",").concat(
								betTemp[index[i] * i].trim());
					}
				}
			}
			bet = bet.concat(",").concat(bile);
			String[] betArranage = bet.split(",");
			int betfew = 0;
			for (String betStr : betArranage) {
				if (lotteryNum.trim().indexOf(betStr.trim()) > -1)
					betfew++;
			}
			if (few < 5) {
				if (betfew >= few)
					lotteryNote++;
			} else {
				if (betfew == lotteryNum.split(",").length)
					lotteryNote++;
			}
		}
		return lotteryNote;
	}

	public static int maxInt(String[] nums) {
		int max = 0;
		for (int i = 0; i < nums.length; i++) {
			if (i == 0) {
				max = Integer.parseInt(nums[i]);
			} else {
				if (Integer.parseInt(nums[i]) > max)
					max = Integer.parseInt(nums[i]);
			}
		}
		return max;
	}

	public static int miniInit(String[] nums) {
		int mini = 0;
		for (int i = 0; i < nums.length; i++) {
			if (i == 0) {
				mini = Integer.parseInt(nums[i]);
			} else {
				if (Integer.parseInt(nums[i]) < mini)
					mini = Integer.parseInt(nums[i]);
			}
		}
		return mini;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(PlayUtil.anyChooseBileDragX(8, "09,10,11", "03,06,04,05,07,08", "08,10,11,04,05"));
	}

	// 方法一：使用可变参数方法返回数组类型的List
	public static List<String> assembleArraysToList(Object[]... objects) {
		List<Object> arrayList = new ArrayList<>();
		// 遍历方法的参数
		for (int i = 0; i < objects.length; i++) {
			if (i == 0) {
				// 对于第一个数组参数,先将其转变成List类型,以便能使用辅助方法进行处理
				arrayList = Arrays.asList(objects[i]);
			} else {
				// 对从第二个参数开始的数组与前面组合过的列表进行组合
				arrayList = assembleArrayToList(arrayList, objects[i]);
			}
		}
		List<String> newlist = new ArrayList<>();
		for(Object obj : arrayList)
			newlist.add(obj.toString());
		return newlist;
	}

	// 方法一的辅助方法：将一个数组类型或对象类型的List与数组组合，并返回List
		public static List<Object> assembleArrayToList(List<Object> aList, Object[] array) {
			List<Object> arrList = new ArrayList<Object>();
			// 遍历aList,将array与aList进行组合
			for (int i = 0; i < aList.size(); i++) {
				Object obj = aList.get(i);
				// 检查aList的元素是否是数组类型的，如果不是，则直接产生组合列表
				if (obj instanceof String && ((String) obj).indexOf(",")>-1) {
					String listArr = (String) obj;
					// 对数组类型的aList元素与array进行组合
					for (int k = 0; k < array.length; k++) {
						String str = listArr;
						String temp = (String) array[k];
						if(temp.trim().equals(""))continue;
						str = str.concat(",").concat(temp);
						arrList.add(str);
					}
				} else {
					// 对非数组类型的aList元素与array进行组合
					for (int j = 0; j < array.length; j++) {
						String arrObj = aList.get(i).toString().concat(",").concat((String) array[j]);
						arrList.add(arrObj);
					}
				}
			}
			return arrList;
		}
		
}
