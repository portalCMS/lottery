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
public class OftenPlayLimit {
	/**
	 * 一星直选
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> positioningOne(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] betTemp = betNum.trim().split("\\|")[4].split(",");
		for (String bet : betTemp) {
			list.add(bet);
		}
		return list;
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
		String[] group1 = temp[3].split(",");
		String[] group2 = temp[4].split(",");
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
		List<String> twoNums = positioningTwo("*|*|*|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9");
		for (String bet : betNum.split(",")) {
			if(bet.trim().equals(""))continue;
			for(String num : twoNums){
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
	 * 江西 二星组选 组选包号
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
	 * 重庆 二星组选 组选包号
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
	 * 后三星直选
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> positioningThree(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] temp = betNum.split("\\|");
		String[] group1 = temp[2].split(",");
		String[] group2 = temp[3].split(",");
		String[] group3 = temp[4].split(",");
		list = PlayUtil.assembleArraysToList(group1, group2, group3);
		return list;
	}
	
	/**
	 * 前三星直选
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> positioningThree2(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] temp = betNum.split("\\|");
		String[] group1 = temp[0].split(",");
		String[] group2 = temp[1].split(",");
		String[] group3 = temp[2].split(",");
		list = PlayUtil.assembleArraysToList(group1, group2, group3);
		return list;
	}
	
	/**
	 * 中三星直选
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> positioningThree3(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] temp = betNum.split("\\|");
		String[] group1 = temp[1].split(",");
		String[] group2 = temp[2].split(",");
		String[] group3 = temp[3].split(",");
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
		List<String> bet3StartNums = this.positioningThree("*|*|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9");
		for (String bet : betGroup) {
			//list.add(bet.trim()+":"+positioningthree.get(Integer.parseInt(bet.trim())));
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
		List<String> bet3StartNums = this.positioningThree("*|*|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9");
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
	 * 后三星 组三标准
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> groupthreeThree(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] temp = betNum.split("\\|")[2].split(",");
		String[] lastTemp = betNum.split("\\|")[4].split(",");
		for (String strtemp : temp) {
			for (String str : lastTemp) {
				if (strtemp.trim().equals(str.trim()))
					continue;
				String bet = strtemp.trim() + "," + strtemp.trim() + "," + str.trim();
				bet = this.sort(bet.trim());
				list.add(bet.trim());
			}
		}
		return list;
	}
	
	/**
	 * 后三星 组六标准
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> groupSixThree(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] temp = betNum.split("\\|")[2].split(",");
		String[] temp2 = betNum.split("\\|")[3].split(",");
		String[] lastTemp = betNum.split("\\|")[4].split(",");
		for (String strtemp : temp) {
			for(String strtemp2 : temp2){
				if (strtemp.trim().equals(strtemp2.trim()))
					continue;
				for (String str : lastTemp) {
					if (strtemp.trim().equals(str.trim()))
						continue;
					if (str.trim().equals(strtemp2.trim()))
						continue;
					String bet = strtemp.trim() + "," + strtemp2.trim() + "," + str.trim();
					list.add(bet.trim());
				}
			}
			
		}
		return list;
	}

	/**
	 * 前三星 组三标准
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> groupthreeThree2(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] temp = betNum.split("\\|")[0].split(",");
		String[] lastTemp = betNum.split("\\|")[2].split(",");
		for (String strtemp : temp) {
			for (String str : lastTemp) {
				if (strtemp.trim().equals(str.trim()))
					continue;
				String bet = strtemp.trim() + "," + strtemp.trim() + "," + str.trim();
				bet = this.sort(bet.trim());
				list.add(bet.trim());
			}
		}
 		return list;
	}
	
	/**
	 * 中三星 组三标准
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> groupthreeThree3(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] temp = betNum.split("\\|")[1].split(",");
		String[] lastTemp = betNum.split("\\|")[3].split(",");
		for (String strtemp : temp) {
			for (String str : lastTemp) {
				if (strtemp.trim().equals(str.trim()))
					continue;
				String bet = strtemp.trim() + "," + strtemp.trim() + "," + str.trim();
				bet = this.sort(bet.trim());
				list.add(bet.trim());
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
		List<String> groupThreeNums = groupthreeThree("*|*|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9");
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
			bet = this.sort(bet);
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
	 * 三星 组六 胆拖投注
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
	 * 四星直选
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> positioningFour(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] betGroup = betNum.split("\\|");
		list = PlayUtil.assembleArraysToList(betGroup[1].split(","),betGroup[2].split(","),betGroup[3].split(","),betGroup[4].split(","));
		return list;
	}

	/**
	 * 四星直选 单式
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> positioningFourUpLoad(String betNum) {
		List<String> list = new ArrayList<String>();
		for(String str:betNum.split(",")){
			str = str.trim();
			String bet = str.substring(0, 1).concat("|")+str.substring(1, 2).concat("|")
							+str.substring(2, 3).concat("|")+str.substring(3, 4);
			String[] betGroup = bet.split("\\|");
			String bn = PlayUtil.assembleArraysToList(betGroup[0].split(","),betGroup[1].split(","),betGroup[2].split(","),betGroup[3].split(",")).toString();
			bn = bn.substring(1,bn.length()-1);
			list.add(bn);
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

	public List<String> generalFive(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] betGroup = betNum.split("\\|");
		list = PlayUtil.assembleArraysToList(this.sort(betGroup[0]).split(","),this.sort(betGroup[1]).split(","),
				this.sort(betGroup[2]).split(","),this.sort(betGroup[3]).split(","),this.sort(betGroup[4]).split(","));
		return list;
	}

	/**
	 * 大小单双
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> smallbig(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] betGroup = betNum.split("\\|");
		list = PlayUtil.assembleArraysToList(betGroup[0].split(","),betGroup[1].split(","));
		return list;
	}

	/**
	 * 任选一
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
		for (String str : betGroup[3].split(",")) {
			if(str.trim().equals("")||str.trim().equals("*"))continue;
			list.add("4;"+str.trim());
		}
		for (String str : betGroup[4].split(",")) {
			if(str.trim().equals("")||str.trim().equals("*"))continue;
			list.add("5;"+str.trim());
		}
		return list;
	}

	/**
	 * 任选二
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
		String[] group3 = betGroup[3].split(",");
		String[] group4 = betGroup[4].split(",");
		if(!group[0].trim().equals("*")&&!group1[0].trim().equals("*")){
			list.addAll(getAnyChooseTwoLimit(group,group1,"01"));
		}
		if(!group[0].trim().equals("*")&&!group2[0].trim().equals("*")){
			list.addAll(getAnyChooseTwoLimit(group,group2,"02"));
		}
//		if(!group[0].trim().equals("*")&&!group2[0].trim().equals("*")){
//			list.addAll(getAnyChooseTwoLimit(group,group2,"02"));
//		}
		if(!group[0].trim().equals("*")&&!group3[0].trim().equals("*")){
			list.addAll(getAnyChooseTwoLimit(group,group3,"03"));
		}
		if(!group[0].trim().equals("*")&&!group4[0].trim().equals("*")){
			list.addAll(getAnyChooseTwoLimit(group,group4,"04"));
		}
		if(!group1[0].trim().equals("*")&&!group2[0].trim().equals("*")){
			list.addAll(getAnyChooseTwoLimit(group1,group2,"12"));
		}
		if(!group1[0].trim().equals("*")&&!group3[0].trim().equals("*")){
			list.addAll(getAnyChooseTwoLimit(group1,group3,"13"));
		}
		if(!group1[0].trim().equals("*")&&!group4[0].trim().equals("*")){
			list.addAll(getAnyChooseTwoLimit(group1,group4,"14"));
		}
		if(!group2[0].trim().equals("*")&&!group3[0].trim().equals("*")){
			list.addAll(getAnyChooseTwoLimit(group2,group3,"23"));
		}
		if(!group2[0].trim().equals("*")&&!group4[0].trim().equals("*")){
			list.addAll(getAnyChooseTwoLimit(group2,group4,"24"));
		}
		if(!group3[0].trim().equals("*")&&!group4[0].trim().equals("*")){
			list.addAll(getAnyChooseTwoLimit(group3,group4,"34"));
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
	
	
	/**
	 * 任选三
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> anyChooseThree(String betNum) {
		List<String> list = new ArrayList<String>();
		betNum=betNum.replaceAll("", " ");
		String[] betGroup = betNum.split("\\|");
		String[] group = betGroup[0].split(",");
		String[] group1 = betGroup[1].split(",");
		String[] group2 = betGroup[2].split(",");
		String[] group3 = betGroup[3].split(",");
		String[] group4 = betGroup[4].split(",");
		if(!group[0].trim().equals("*")&&!group1[0].trim().equals("*")&&!group2[0].trim().equals("*")){
			list.addAll(getAnyChooseThreeLimit(group,group1,group2,"012"));
		}
		if(!group[0].trim().equals("*")&&!group2[0].trim().equals("*")&&!group3[0].trim().equals("*")){
			list.addAll(getAnyChooseThreeLimit(group,group2,group3,"023"));
		}
		if(!group[0].trim().equals("*")&&!group1[0].trim().equals("*")&&!group3[0].trim().equals("*")){
			list.addAll(getAnyChooseThreeLimit(group,group1,group3,"013"));
		}
		if(!group1[0].trim().equals("*")&&!group2[0].trim().equals("*")&&!group3[0].trim().equals("*")){
			list.addAll(getAnyChooseThreeLimit(group1,group2,group3,"123"));
		}
		if(!group1[0].trim().equals("*")&&!group2[0].trim().equals("*")&&!group4[0].trim().equals("*")){
			list.addAll(getAnyChooseThreeLimit(group1,group2,group4,"124"));
		}
		if(!group2[0].trim().equals("*")&&!group3[0].trim().equals("*")&&!group4[0].trim().equals("*")){
			list.addAll(getAnyChooseThreeLimit(group2,group3,group4,"234"));
		}
		if(!group1[0].trim().equals("*")&&!group3[0].trim().equals("*")&&!group4[0].trim().equals("*")){
			list.addAll(getAnyChooseThreeLimit(group1,group3,group4,"134"));
		}
		if(!group[0].trim().equals("*")&&!group3[0].trim().equals("*")&&!group4[0].trim().equals("*")){
			list.addAll(getAnyChooseThreeLimit(group,group3,group4,"034"));
		}
		if(!group[0].trim().equals("*")&&!group2[0].trim().equals("*")&&!group4[0].trim().equals("*")){
			list.addAll(getAnyChooseThreeLimit(group,group2,group4,"024"));
		}
		if(!group[0].trim().equals("*")&&!group1[0].trim().equals("*")&&!group4[0].trim().equals("*")){
			list.addAll(getAnyChooseThreeLimit(group,group1,group4,"014"));
		}
		return list;
	}
	
	private List<String> getAnyChooseThreeLimit(String[] group,String[] group1,String[] group2,String logo){
		List<String> list = new ArrayList<String>();
		if(group.length>=1&&group[0].trim().equals("")){
			return list;
		}
		if(group1.length>=1&&group1[0].trim().equals("")){
			return list;	
		}
		if(group2.length>=1&&group2[0].trim().equals("")){
			return list;	
		}
		for(String str:group){
			if(str.trim().equals(""))continue;
			for(String str1:group1){
				if(str1.trim().equals(""))continue;
				for(String str2:group2){
					if(str2.trim().equals(""))continue;
						list.add(logo.trim().concat(":").concat(str.trim()).concat(",").concat(str1.trim()).concat(",").concat(str2.trim()));
				}
			}
		}
		return list;
	}
	
	/**
	 * 任选四
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> anyChooseFour(String betNum) {
		List<String> list = new ArrayList<String>();
		betNum=betNum.replaceAll("", " ");
		String[] betGroup = betNum.split("\\|");
		String[] group = betGroup[0].split(",");
		String[] group1 = betGroup[1].split(",");
		String[] group2 = betGroup[2].split(",");
		String[] group3 = betGroup[3].split(",");
		String[] group4 = betGroup[4].split(",");
		if(!group[0].trim().equals("*")&&!group1[0].trim().equals("*")
				&&!group2[0].trim().equals("*")&&!group3[0].trim().equals("*")){
			list.addAll(getAnyChooseFourLimit(group,group1,group2,group3,"0123"));
		}
		if(!group[0].trim().equals("*")&&!group2[0].trim().equals("*")
				&&!group3[0].trim().equals("*")&&!group4[0].trim().equals("*")){
			list.addAll(getAnyChooseFourLimit(group,group2,group3,group4,"0234"));
		}
		if(!group[0].trim().equals("*")&&!group1[0].trim().equals("*")
				&&!group3[0].trim().equals("*")&&!group4[0].trim().equals("*")){
			list.addAll(getAnyChooseFourLimit(group,group1,group3,group4,"0134"));
		}
		if(!group1[0].trim().equals("*")&&!group2[0].trim().equals("*")
				&&!group3[0].trim().equals("*")&&!group4[0].trim().equals("*")){
			list.addAll(getAnyChooseFourLimit(group1,group2,group3,group4,"1234"));
		}
		if(!group[0].trim().equals("*")&&!group1[0].trim().equals("*")
				&&!group2[0].trim().equals("*")&&!group4[0].trim().equals("*")){
			list.addAll(getAnyChooseFourLimit(group,group1,group2,group4,"0124"));
		}
		
		return list;
	}
	
	private List<String> getAnyChooseFourLimit(String[] group,String[] group1,String[] group2,String[] group3,String logo){
		List<String> list = new ArrayList<String>();
		if(group.length>=1&&group[0].trim().equals("")){
			return list;
		}
		if(group1.length>=1&&group1[0].trim().equals("")){
			return list;	
		}
		if(group2.length>=1&&group2[0].trim().equals("")){
			return list;	
		}
		if(group3.length>=1&&group3[0].trim().equals("")){
			return list;	
		}
		for(String str:group){
			if(str.trim().equals(""))continue;
			for(String str1:group1){
				if(str1.trim().equals(""))continue;
				for(String str2:group2){
					if(str2.trim().equals(""))continue;
					for(String str3:group3){
						if(str3.trim().equals(""))continue;
							list.add(logo.trim().concat(":").concat(str.trim()).concat(",")
								.concat(str1.trim()).concat(",").concat(str2.trim()).concat(",").concat(str3.trim()));
					}
				}
			}
		}
		return list;
	}
	
	
	/**
	 * 组选120  24   4   包号
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> groupOfSelected120_24_4(String selectCode,String betNum) {
		List<String> list = new ArrayList<String>();
		String[] betTemp = betNum.trim().split(",");
		int numLen = 5;
		if(selectCode.trim().equals("40081")){
			numLen = 4;
		}else if(selectCode.trim().equals("40083")){
			numLen = 2;
		}
		Combination comb = new Combination(betTemp.length, numLen);
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
	 * 组选60 一对  20  10  5 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> groupOfSelected60(int dhc,String betNum) {
		List<String> list = new ArrayList<String>();
		//重号
		String doubleNum = betNum.trim().split("\\|")[0];
		String[] dNums = doubleNum.trim().split(",");
		//单号
		String oneNum = betNum.trim().split("\\|")[1];
		String[] oNums = oneNum.trim().split(",");
		
		for(String dnum : dNums){
			boolean isRepeat = false;
			for(String onum : oNums){
				if(onum.equals(dnum)){
					isRepeat = true;
					break;
				}
			}
			if(isRepeat){
				if(oNums.length<dhc+1){
					continue;
				}
				Combination comb = new Combination(oNums.length, dhc);
				while (comb.hasMore()) {
					int[] index = comb.getNext();
					String bet = "";
					for (int i = 0; i < oNums.length; i++) {
						if (index[i] != 0) {
							if (bet.equals("")) {
								bet = bet.concat(oNums[index[i] * i].trim());
							} else {
								bet = bet.concat(",").concat(
										oNums[index[i] * i].trim());
							}
						}
					}
					if (bet.equals(""))
						continue;
					if(bet.indexOf(dnum)!=-1)
						continue;
					if(bet.split(",").length==(dhc-1)){
						continue;
					}
					bet = dnum.trim()+","+bet;
					list.add(bet);
				}
			}else{
				Combination comb = new Combination(oNums.length, dhc);
				while (comb.hasMore()) {
					int[] index = comb.getNext();
					String bet = "";
					for (int i = 0; i < oNums.length; i++) {
						if (index[i] != 0) {
							if (bet.equals("")) {
								bet = bet.concat(oNums[index[i] * i].trim());
							} else {
								bet = bet.concat(",").concat(
										oNums[index[i] * i].trim());
							}
						}
					}
					if (bet.equals(""))
						continue;
					if(bet.indexOf(dnum)!=-1)
						continue;
					if(bet.split(",").length==(dhc-1)){
						continue;
					}
					bet = dnum.trim()+","+bet;
					list.add(bet);
				}
			}
			
		}
		
		return list;
	}
	
	
	/**
	 * 组选30 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> groupOfSelected30(String betNum) {
		List<String> list = new ArrayList<String>();
		//重号
		String doubleNum = betNum.trim().split("\\|")[0];
		String[] dNums = doubleNum.trim().split(",");
		//先拼两个二重号
		List<String> allDnums = new ArrayList<String>();
		for(int i=0;i<dNums.length;i++){
			String d1 = dNums[i];
			for(int j=i+1;j<dNums.length;j++){
				String d2 = dNums[j];
				if(allDnums.contains(d1+","+d2)){
					continue;
				}
				allDnums.add(d1+","+d2);
			}
		}
		
		//单号
		String oneNum = betNum.trim().split("\\|")[1];
		String[] oNums = oneNum.trim().split(",");
		
		for(int i=0;i<allDnums.size();i++){
			String dns = allDnums.get(i);
			for(int j=0;j<oNums.length;j++){
				String ons = oNums[j];
				if(dns.indexOf(ons)!=-1){
					continue;
				}else{
					list.add(dns+";"+ons);
				}
			}
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
	
	public List<String> invok(String selectCode,String bile,String betNum){
		List<String> list = null;
		switch (Integer.parseInt(selectCode)) {
		case 40001:
			list = this.positioningOne(betNum);
			break;
		case 40002:
			list = this.positioningTwo(betNum);	
			break;
		case 40065:
			list = this.positioningTwo2(betNum);	
			break;
		case 40003:
		case 40067:
			list = this.positioningTwoUpload(betNum);	
			break;
		case 40064:
		case 40066:
			list = this.positioningsumTwo(betNum);
			break;
		case 40004:
		case 40068:
			list = this.groupOfSelectedOrdinaryTwoJX(betNum);
			break;
		case 40005:
		case 40069:	
			list = this.groupsumTwo(betNum);
			break;
		case 40006:
		case 40070:
			list = this.groupOfSelectedBileDragTwo(bile, betNum);
			break;
		case 40007:
			list = this.positioningThree(betNum);
			break;
		case 40036:
			list = this.positioningThree2(betNum);
			break;
		case 40041:
			list = this.positioningThree3(betNum);
			break;
		case 40008:
		case 40037:
		case 40042:
			list = this.positioningsumThree(betNum);
			break;
		case 40009:
		case 40038:
		case 40043:
			list = this.positioningblieThree(bile, betNum);
			break;
		case 40010:
		case 40039:
		case 40044:
			list = this.spanThree(betNum);
			break;
		case 40011:
		case 40040:
		case 40045:
			list = this.positioningThreeUpLoad(betNum);
			break;
		case 40012:
			list = this.groupthreeThree(betNum);
			break;
		case 40046:
			list = this.groupthreeThree2(betNum);
			break;
		case 40055:
			list = this.groupthreeThree3(betNum);
			break;
		case 40013:
		case 40047:
		case 40056:
			list = this.groupthreePackageThree(betNum);
			break;
		case 40014:
		case 40048:
		case 40057:
			list = this.groupsumthreeThree(betNum);
			break;
		case 40015:
		case 40050:
		case 40058:
			list = this.groupUpLoadThree(betNum);
			break;
		case 40016:
		case 40051:
		case 40059:
			list = this.groupsixThree(betNum);
			break;
		case 40017:
		case 40052:
		case 40060:
			list = this.groupsixsumThree(betNum);
			break;
		case 40018:
		case 40053:
		case 40061:
			list = this.groupSixThree(bile, betNum);
			break;
		case 40019:
		case 40054:
		case 40062:
			list = this.groupUpLoadSix(betNum);
			break;
		case 40020:
		case 40072:
			list = this.positioningFour(betNum);
			break;
		case 40021:
			list = this.positioningFive(betNum);
			break;
		case 40022:
			list = this.positioningFiveUpload(betNum);
			break;
		case 40023:
			list = this.generalFive(betNum);
			break;
		case 40024:
			list = this.positioningFiveUpload(betNum);
			break;
		case 40025:
		case 40063:
			list = this.smallbig(betNum);
			break;
		case 40026:
			list = this.anyChooseOne(betNum);
			break;
		case 40027:
			list = this.anyChooseTwo(betNum);
			break;
		case 40085:
			list = this.anyChooseThree(betNum);
			break;
		case 40086:
			list = this.anyChooseFour(betNum);
			break;
		case 40028:
		case 40074:
			list = this.groupOfSelectedOrdinaryTwoCQ(betNum);
			break;
		case 40029:
			list = this.groupsumTwo(betNum);
			break;
		case 40030:
		case 40031:
		case 40032:
			list = this.threeChooseOne(betNum);
			break;
			
		case 40033:
		case 40034:
		case 40035:
			list = this.threeChooseTwo(betNum);
			break;
		case 40071:
		case 40073:
			list = this.positioningFourUpLoad(betNum);
			break;
		case 40075:
		case 40081:
		case 40083:
			list = this.groupOfSelected120_24_4(selectCode,betNum);
			break;
		case 40076:
			list = this.groupOfSelected60(3,betNum);
			break;	
		case 40078:
		case 40082:
			list = this.groupOfSelected60(2,betNum);
			break;
		case 40079:
		case 40080:
		case 40084:
			list = this.groupOfSelected60(1,betNum);
			break;
		case 40077:
			list = this.groupOfSelected30(betNum);
			break;
		default:
			list = this.anyChooseOne(betNum);
			break;
		}
		return list;
	}
	
	public static void  main(String[] args){
		OftenPlayLimit pl = new OftenPlayLimit();
		System.out.println(pl.groupOfSelected30("5,6,7,8,9|0,1,2,3,4,5,6,7,8,9"));
		System.out.println(pl.groupOfSelected30("5,6,7,8,9|0,1,2,3,4,5,6,7,8,9").size());
	}
}
