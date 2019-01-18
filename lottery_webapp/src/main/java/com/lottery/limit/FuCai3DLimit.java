package com.lottery.limit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.lottery.core.play.PlayUtil;
import com.xl.lottery.core.algorithm.Arrange;
import com.xl.lottery.core.algorithm.Combination;

@Component
public class FuCai3DLimit {
	/**
	 * 二星直选和值
	 */
	private static Map<Integer, Integer> positionsumtwo = new HashMap<Integer, Integer>();
	/**
	 * 二星组选和值
	 */
	private static Map<Integer, Integer> groupsumtwo = new HashMap<Integer, Integer>();
	/**
	 * 三星组选 和值
	 */
	private static Map<Integer, Integer> groupsumthree = new HashMap<Integer, Integer>();

	/**
	 * 三星直选和值
	 */
	private static Map<Integer, Integer> positioningthree = new HashMap<Integer, Integer>();

	/**
	 * 三星组6选和值
	 */
	private static Map<Integer, Integer> grousixpsumthree = new HashMap<Integer, Integer>();

	/**
	 * 三星跨度
	 */
	private static Map<Integer, Integer> spanthree = new HashMap<Integer, Integer>();

	static {
		// 二星直选和值 注数初始化
		positionsumtwo.put(0, 1);
		positionsumtwo.put(1, 2);
		positionsumtwo.put(2, 3);
		positionsumtwo.put(3, 4);
		positionsumtwo.put(4, 5);
		positionsumtwo.put(5, 6);
		positionsumtwo.put(6, 7);
		positionsumtwo.put(7, 8);
		positionsumtwo.put(8, 9);
		positionsumtwo.put(9, 10);
		positionsumtwo.put(10, 9);
		positionsumtwo.put(11, 8);
		positionsumtwo.put(12, 7);
		positionsumtwo.put(13, 6);
		positionsumtwo.put(14, 5);
		positionsumtwo.put(15, 4);
		positionsumtwo.put(16, 3);
		positionsumtwo.put(17, 2);
		positionsumtwo.put(18, 1);		
		// 二星组选和值 注数初始化
		groupsumtwo.put(0, 1);
		groupsumtwo.put(1, 1);
		groupsumtwo.put(2, 2);
		groupsumtwo.put(3, 2);
		groupsumtwo.put(4, 3);
		groupsumtwo.put(5, 3);
		groupsumtwo.put(6, 4);
		groupsumtwo.put(7, 4);
		groupsumtwo.put(8, 5);
		groupsumtwo.put(9, 5);
		groupsumtwo.put(10, 5);
		groupsumtwo.put(11, 4);
		groupsumtwo.put(12, 4);
		groupsumtwo.put(13, 3);
		groupsumtwo.put(14, 3);
		groupsumtwo.put(15, 2);
		groupsumtwo.put(16, 2);
		groupsumtwo.put(17, 1);
		groupsumtwo.put(18, 1);

		// //////三星直选 和值 注数初始化
		positioningthree.put(0, 1);
		positioningthree.put(1, 3);
		positioningthree.put(2, 6);
		positioningthree.put(3, 10);
		positioningthree.put(4, 15);
		positioningthree.put(5, 21);
		positioningthree.put(6, 28);
		positioningthree.put(7, 36);
		positioningthree.put(8, 45);
		positioningthree.put(9, 55);
		positioningthree.put(10, 63);
		positioningthree.put(11, 69);
		positioningthree.put(12, 73);
		positioningthree.put(13, 75);
		positioningthree.put(14, 75);
		positioningthree.put(15, 73);
		positioningthree.put(16, 69);
		positioningthree.put(17, 63);
		positioningthree.put(18, 55);
		positioningthree.put(19, 45);
		positioningthree.put(20, 36);
		positioningthree.put(21, 28);
		positioningthree.put(22, 21);
		positioningthree.put(23, 15);
		positioningthree.put(24, 10);
		positioningthree.put(25, 6);
		positioningthree.put(26, 3);
		positioningthree.put(27, 1);

		// 初始化三星组选和值
		groupsumthree.put(1, 1);
		groupsumthree.put(2, 2);
		groupsumthree.put(3, 1);
		groupsumthree.put(4, 3);
		groupsumthree.put(5, 3);
		groupsumthree.put(6, 3);
		groupsumthree.put(7, 4);
		groupsumthree.put(8, 5);
		groupsumthree.put(9, 4);
		groupsumthree.put(10, 5);
		groupsumthree.put(11, 5);
		groupsumthree.put(12, 4);
		groupsumthree.put(13, 5);
		groupsumthree.put(14, 5);
		groupsumthree.put(15, 4);
		groupsumthree.put(16, 5);
		groupsumthree.put(17, 5);
		groupsumthree.put(18, 4);
		groupsumthree.put(19, 5);
		groupsumthree.put(20, 4);
		groupsumthree.put(21, 3);
		groupsumthree.put(22, 3);
		groupsumthree.put(23, 3);
		groupsumthree.put(24, 1);
		groupsumthree.put(25, 2);
		groupsumthree.put(26, 1);

		// 初始化三星组6选和值
		grousixpsumthree.put(3, 1);
		grousixpsumthree.put(4, 1);
		grousixpsumthree.put(5, 2);
		grousixpsumthree.put(6, 3);
		grousixpsumthree.put(7, 4);
		grousixpsumthree.put(8, 5);
		grousixpsumthree.put(9, 7);
		grousixpsumthree.put(10, 8);
		grousixpsumthree.put(11, 9);
		grousixpsumthree.put(12, 10);
		grousixpsumthree.put(13, 10);
		grousixpsumthree.put(14, 10);
		grousixpsumthree.put(15, 10);
		grousixpsumthree.put(16, 9);
		grousixpsumthree.put(17, 8);
		grousixpsumthree.put(18, 7);
		grousixpsumthree.put(19, 5);
		grousixpsumthree.put(20, 4);
		grousixpsumthree.put(21, 3);
		grousixpsumthree.put(22, 2);
		grousixpsumthree.put(23, 1);
		grousixpsumthree.put(24, 1);

		// 三星跨度组数初始化
		spanthree.put(0, 10);
		spanthree.put(1, 54);
		spanthree.put(2, 96);
		spanthree.put(3, 126);
		spanthree.put(4, 144);
		spanthree.put(5, 150);
		spanthree.put(6, 144);
		spanthree.put(7, 126);
		spanthree.put(8, 96);
		spanthree.put(9, 54);
	}
	
	
	/**
	 * 三星直选
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> positioningThree(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] temp = betNum.split("\\|");
		String[] group1 = temp[0].split(",");
		String[] group2 = temp[1].split(",");
		String[] group3 = temp[2].split(",");
		list = PlayUtil.assembleArraysToList(group1, group2, group3);
		return list;
	}
	
	/**
	 * 三星直选手工录入
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> positioningThreeUpLoad(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] betCods = betNum.trim().split(",");
		for(String code : betCods){
			String newCode = code.substring(0,1).concat(",").concat(code.substring(1,2)).concat(",").concat(code.substring(2,3));
			if(code.trim().length()==3)list.add(this.sort(newCode));
		}
		return list;
	}

	/**
	 * 三星直选 和值
	 * 
	 * @return
	 */
	public List<String> positioningsumThree(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] betGroup = betNum.split(",");
		List<String> bet3StartNums = this.positioningThree("0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9");
		for (String bet : betGroup) {
			for(String num : bet3StartNums){
				String[] nums = num.split(",");
				int sum=0;
				for(int i=0;i<nums.length;i++){
					sum = sum + Integer.parseInt(nums[i]);
				}
				if(Integer.parseInt(bet.trim())==sum){
					list.add(num);
				}
			}
		}
		return list;
	}
	/**
	 * 三星直选胆拖
	 * 
	 * @param bile
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> positioningblieThree(String bile, String betNum) {
		List<String> list = new ArrayList<String>();
		String[] betTemp = betNum.split(",");
		Combination comb = new Combination(betTemp.length,
				3 - bile.split(",").length);
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
			bet = bile.concat(",").concat(bet);
			if(bet.split(",").length<3)continue;
			Arrange strArrange = new Arrange(bet.split(",").length);
			while (strArrange.hasMore()) {
				int[] indexArr = strArrange.getNext();
				String betArr = "";
				for (int i = 0; i < indexArr.length; i++) {
					if (betArr.equals("")) {
						betArr = betArr.concat(bet.split(",")[indexArr[i]].trim());
					} else {
						betArr = betArr.concat(",").concat(
								bet.split(",")[indexArr[i]].trim());
					}
				}
				list.add(betArr);
			}
			
		}
		return list;
	}

	/**
	 * 三星直选 跨度
	 * 
	 * @return
	 */
	public List<String> spanThree(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] betGroup = betNum.split(",");
		List<String> bet3StartNums = this.positioningThree("0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9");
		for (String bet : betGroup) {
			//list.add(bet.trim()+":"+spanthree.get(Integer.parseInt(bet.trim())));
			for(String num : bet3StartNums){
				String[] nums = num.split(",");
				int min=99;
				int max=0;
				for(int i=0;i<nums.length;i++){
					if(min>Integer.parseInt(nums[i])){
						min = Integer.parseInt(nums[i]);
					}
					if(max<Integer.parseInt(nums[i])){
						max = Integer.parseInt(nums[i]);
					}
				}
				if(Integer.parseInt(bet.trim())==(max-min)){
					list.add(num);
				}
			}
		}
		return list;
	}

	/**
	 * 三星 组三标准
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> groupthreeThree(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] temp = betNum.split("\\|")[0].split(",");
		String[] lastTemp = betNum.split("\\|")[2].split(",");
		for (String strtemp : temp) {
			for (String str : lastTemp) {
				if (strtemp.trim().equals(str.trim()))
					continue;
				String bet = strtemp.trim() + "," + strtemp.trim() + "," + str.trim();
				bet = this.sort(bet);
				list.add(bet);
			}
		}
		return list;
	}

	/**
	 * 三星 组三包号
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> groupthreePackageThree(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] bets = betNum.split(",");
		Combination comb = new Combination(bets.length, 2);
		while (comb.hasMore()) {
			int[] index = comb.getNext();
			String bet = "";
			for (int i = 0; i < bets.length; i++) {
				if (index[i] != 0) {
					if (bet.equals("")) {
						bet = bet.concat(bets[index[i] * i].trim());
					} else {
						bet = bet.concat(",").concat(bets[index[i] * i].trim());
					}
				}
			}
			if(bet.trim().equals(""))continue;
			list.add(bet.split(",")[0]+","+bet.split(",")[0]+","+bet.split(",")[1]);
			list.add(bet.split(",")[0]+","+bet.split(",")[1]+","+bet.split(",")[0]);
		}
		return list;
	}

	/**
	 * 三星 组三 和值
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> groupsumthreeThree(String betNum) {
		List<String> list = new ArrayList<String>();
		List<String> groupThreeNums = groupthreeThree("0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9");
		for (String bet : betNum.split(",")) {
			if(bet.trim().equals(""))continue;
			//list.add(bet.trim()+":"+groupsumthree.get(Integer.parseInt(bet.trim())));
			for(String num : groupThreeNums){
				String[] nums = num.split(",");
				int sum=0;
				for(int i=0;i<nums.length;i++){
					sum = sum + Integer.parseInt(nums[i]);
				}
				if(Integer.parseInt(bet.trim())==sum){
					list.add(num);
				}
			}
		}
		return list;
	}

	/**
	 * 三星 组三手工录入
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> groupUpLoadThree(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] betCods = betNum.trim().split(",");
		for(String code : betCods){
			String newCode = code.substring(0,1).concat(",").concat(code.substring(1,2)).concat(",").concat(code.substring(2,3));
			if(code.trim().length()==3)list.add(this.sort(newCode));
		}
		return list;
	}

	/**
	 * 三星 组六 包号
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> groupsixThree(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] bets = betNum.split(",");
		Combination comb = new Combination(bets.length, 3);
		while (comb.hasMore()) {
			int[] index = comb.getNext();
			String bet = "";
			for (int i = 0; i < bets.length; i++) {
				if (index[i] != 0) {
					if (bet.equals("")) {
						bet = bet.concat(bets[index[i] * i].trim());
					} else {
						bet = bet.concat(",").concat(bets[index[i] * i].trim());
					}
				}
			}
			if(bet.equals(""))continue;
			list.add(bet);
		}
		return list;
	}

	/**
	 * 三星 组六 和值
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> groupsixsumThree(String betNum) {
		List<String> list = new ArrayList<String>();
		List<String> groupSixNums = groupsixThree("0,1,2,3,4,5,6,7,8,9");
		for (String bet : betNum.split(",")) {
			for(String num : groupSixNums){
				String[] nums = num.split(",");
				int sum=0;
				for(int i=0;i<nums.length;i++){
					sum = sum + Integer.parseInt(nums[i]);
				}
				if(Integer.parseInt(bet.trim())==sum){
					list.add(num);
				}
			}
		}
		return list;
	}


	/**
	 * 前三组六 胆拖投注
	 * 
	 * @param Bile
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> groupSixThree(String bile, String betNum) {
		List<String> list = new ArrayList<String>();
		String[] betTemp = betNum.split(",");
		Combination comb = new Combination(betTemp.length,
				3 - bile.split(",").length);
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
			bet = bile.concat(",").concat(bet);
			bet = this.sort(bet.trim());
			if(bet.split(",").length>=3)list.add(bet);
		}
		return list;
	}

	/**
	 * 三星 组六手工录入
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> groupUpLoadSix(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] betCods = betNum.trim().split(",");
		for(String code : betCods){
			String newCode = code.substring(0,1).concat(",").concat(code.substring(1,2)).concat(",").concat(code.substring(2,3));
			if(code.trim().length()==3)list.add(this.sort(newCode));
		}
		return list;
	}
	/**
	 * 五星直选
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> positioningFive(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] betGroup = betNum.split("\\|");
		list = PlayUtil.assembleArraysToList(betGroup[0].split(","),betGroup[1].split(","),
							betGroup[2].split(","),betGroup[3].split(","),betGroup[4].split(","));
		return list;
	}
	
	/**
	 * 五星直选手工上传
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> positioningFiveUpload(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] betCods = betNum.trim().split(",");
		for(String code : betCods){
			String newCode = code.substring(0,1).concat(",").concat(code.substring(1,2)).concat(",").concat(code.substring(2,3))
					.concat(",").concat(code.substring(3,4)).concat(",").concat(code.substring(4,5));
			if(code.trim().length()>=5)list.add(this.sort(newCode));
		}
		return list;
	}
	/**
	 * 三星一码不定位
	 * @param betNum
	 * @return
	 */
	public List<String> threeChooseOne(String betNum){
		List<String> list = new ArrayList<String>();
		String[] bets = betNum.split(",");
		for(String num : bets){
			list.add(num);
		}
		return list;
	}
	/**
	 * 三星二码不定位
	 * @param betNum
	 * @return
	 */
	public List<String> threeChooseTwo(String betNum){
		List<String> list = new ArrayList<String>();
		String[] betTemp = betNum.trim().split(",");
		Combination comb = new Combination(betTemp.length, 2);
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
			if (bet.equals(""))
				continue;
			list.add(bet);
		}
		return list;
	}
	
	/**
	 * 
	 * @param betNumber
	 * @return
	 */
	public String sort(String betNumber){
		String[] temp = betNumber.split(",");
		Arrays.sort(temp);
		StringBuffer br = new StringBuffer("");
		for(String str:temp){
			if(br.toString().equals("")){
				br.append(str);
			}else{
				br.append(",").append(str);
			}
		}
		return br.toString();
	}
	
	/**
	 * 后二星直选
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> positioningTwo(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] temp = betNum.trim().split("\\|");
		String[] group1 = temp[1].split(",");
		String[] group2 = temp[2].split(",");
		list = PlayUtil.assembleArraysToList(group1, group2);
		return list;
	}
	/**
	 * 前二星直选
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> positioningTwo2(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] temp = betNum.trim().split("\\|");
		String[] group1 = temp[0].split(",");
		String[] group2 = temp[1].split(",");
		list = PlayUtil.assembleArraysToList(group1, group2);
		return list;
	}
	/**
	 * 二星直选和值
	 * 
	 * @return
	 */
	public List<String> positioningsumTwo(String betNum) {
		List<String> list = new ArrayList<String>();
		List<String> bet3StartNums = this.positioningTwo("*|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9");
		for (String str : betNum.split(",")) {
			//list.add(str+":"+positionsumtwo.get(Integer.parseInt(str.trim())));
			for(String num : bet3StartNums){
				String[] nums = num.split(",");
				int sum=0;
				for(int i=0;i<nums.length;i++){
					sum = sum + Integer.parseInt(nums[i]);
				}
				if(Integer.parseInt(str.trim())==sum){
					list.add(num);
				}
			}
		}
		return list;
	}
	
	/**
	 * 二星直选 单式录入
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> positioningTwoUpload(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] temp = betNum.trim().split(",");
		for(String code : temp){
			String newCode = code.substring(0,1).concat(",").concat(code.substring(1,2));
			if(code.trim().length()==2)list.add(this.sort(newCode));
		}
		return list;
	}
	
	/**
	 * 江西 二星组选 组选包号(含对子)
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> groupOfSelectedOrdinaryTwoJX(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] betTemp = betNum.trim().split(",");
		Combination comb = new Combination(betTemp.length, 2);
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
			if (bet.equals(""))
				continue;
			list.add(bet);
		}
		if(betTemp.length>2){
			for(String str:betTemp){
				list.add(str.trim()+","+str.trim());
			}
		}
		return list;
	}

	/**
	 * 重庆 二星组选 组选包号(不含对子)
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> groupOfSelectedOrdinaryTwoCQ(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] betTemp = betNum.trim().split(",");
		Combination comb = new Combination(betTemp.length, 2);
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
			if (bet.equals(""))
				continue;
			list.add(bet);
		}
		return list;
	}
	/**
	 * 二星组选和值
	 * 
	 * @return
	 */
	public List<String> groupsumTwo(String betNum) {
		List<String> list = new ArrayList<String>();
		List<String> twoNums = groupOfSelectedOrdinaryTwoJX("0,1,2,3,4,5,6,7,8,9");
		for (String str : betNum.split(",")) {
			//list.add(str+":"+groupsumtwo.get(Integer.parseInt(str.trim())));
			for(String num : twoNums){
				String[] nums = num.split(",");
				int sum=0;
				for(int i=0;i<nums.length;i++){
					sum = sum + Integer.parseInt(nums[i]);
				}
				if(Integer.parseInt(str.trim())==sum){
					list.add(num);
				}
			}
		}
		return list;
	}
	/**
	 * 江西二星组选胆拖
	 */
	public List<String> groupOfSelectedBileDragTwo(String bile, String betNum) {
		List<String> list = new ArrayList<String>();
		String[] betTemp = betNum.trim().split(",");
		for (String str : betTemp) {
			if (str.equals(bile))
				continue;
			str = bile.concat(",").concat(str);
			str = this.sort(str.trim());
			list.add(str);
		}
		return list;
	}
	
	/**
	 * 3D 任选一
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> anyChooseOne(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] betGroup = betNum.split("\\|");
		for (String str : betGroup[0].split(",")) {
			if(str.trim().equals("")||str.trim().equals("*"))continue;
			list.add("1;"+str.trim());
		}
		for (String str : betGroup[1].split(",")) {
			if(str.trim().equals("")||str.trim().equals("*"))continue;
			list.add("2;"+str.trim());
		}
		for (String str : betGroup[2].split(",")) {
			if(str.trim().equals("")||str.trim().equals("*"))continue;
			list.add("3;"+str.trim());
		}
		return list;
	}

	/**
	 * 3D任选二
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> anyChooseTwo(String betNum) {
		List<String> list = new ArrayList<String>();
		betNum=betNum.replaceAll("", " ");
		String[] betGroup = betNum.split("\\|");
		String[] group = betGroup[0].split(",");
		String[] group1 = betGroup[1].split(",");
		String[] group2 = betGroup[2].split(",");
		if(!group[0].trim().equals("*")&&!group1[0].trim().equals("*")){
			list.addAll(getAnyChooseTwoLimit(group,group1,"01"));
		}
		if(!group[0].trim().equals("*")&&!group2[0].trim().equals("*")){
			list.addAll(getAnyChooseTwoLimit(group,group2,"02"));
		}
//		if(!group[0].trim().equals("*")&&!group2[0].trim().equals("*")){
//			list.addAll(getAnyChooseTwoLimit(group,group2,"02"));
//		}
		if(!group1[0].trim().equals("*")&&!group2[0].trim().equals("*")){
			list.addAll(getAnyChooseTwoLimit(group1,group2,"12"));
		}
		return list;
	}
	private List<String> getAnyChooseTwoLimit(String[] group,String[] group1,String logo){
		List<String> list = new ArrayList<String>();
		if(group.length>=1&&group[0].trim().equals("")){
			return list;
		}
		if(group1.length>=1&&group[0].trim().equals("")){
			return list;	
		}
		for(String str:group){
			if(str.trim().equals(""))continue;
			for(String str1:group1){
				if(str1.trim().equals(""))continue;
				list.add(logo.trim().concat(":").concat(str.trim()).concat(",").concat(str1.trim()));
			}
		}
		return list;
	}
	
	/*************************************************/
	public List<String> invok(String selectCode,String bile,String betNum){
		List<String> list = null;
		switch (Integer.parseInt(selectCode)) {
		case 50001:
				list = this.positioningThree(betNum);
				break;
		case 50002:
				list = this.positioningsumThree(betNum);
				break;
		case 50003:
				list = this.positioningblieThree(bile, betNum);
				break;
		case 50004:
				list = this.spanThree(betNum);
				break;
		case 50005:
				list = this.positioningThreeUpLoad(betNum);
				break;
		case 50006:
				list = this.groupthreeThree(betNum);
				break;
		case 50007:
				list = this.groupthreePackageThree(betNum);
				break;
		case 50008:
				list = this.groupsumthreeThree(betNum);
				break;
		case 50009:
				list = this.groupUpLoadThree(betNum);
				break;
		case 50010:
				list = this.groupsixThree(betNum);
				break;
		case 50011:
				list = this.groupsixsumThree(betNum);
				break;
		case 50012:
				list = this.groupSixThree(bile, betNum);
				break;
		case 50013:
				list = this.groupUpLoadSix(betNum);
				break;
		case 50014:
			list = this.positioningFive(betNum);
			break;
		case 50015:
			list = this.positioningFiveUpload(betNum);
			break;
		case 50016:
			list = this.threeChooseOne(betNum);
			break;
		case 50017:
			list = this.threeChooseTwo(betNum);
			break;
		case 50032:
			list = this.positioningTwo(betNum);	
			break;
		case 50040:
			list = this.positioningTwo2(betNum);	
			break;	
		case 50030:
		case 50041:
			list = this.positioningsumTwo(betNum);	
			break;
		case 50031:
		case 50039:
			list = this.positioningTwoUpload(betNum);
			break;
		case 50035:
		case 50042:
			list = this.groupOfSelectedOrdinaryTwoJX(betNum);
			break;
		case 50033:
		case 50045:
			list = this.groupOfSelectedOrdinaryTwoCQ(betNum);
			break;
		case 50036:
		case 50043:	
			list = this.groupsumTwo(betNum);
			break;
		case 50034:
		case 50044:
			list = this.groupOfSelectedBileDragTwo(bile, betNum);
			break;
		case 50037:
			list = this.anyChooseOne(betNum);
			break;
		case 50038:
			list = this.anyChooseTwo(betNum);
			break;
		default:
			list = this.positioningFiveUpload(betNum);
			break;
		}
		return list;
	}
	
	public static void main(String[] args) {
		String str = "123";
		System.out.println(str.substring(0,1).concat(",").concat(str.substring(1,2)).concat(",").concat(str.substring(2,3)));
	}
}
