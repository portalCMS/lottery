package com.lottery.core.play;


import java.util.Arrays;

import org.springframework.util.StringUtils;

import com.xl.lottery.core.algorithm.Arrange;
import com.xl.lottery.core.algorithm.Combination;

public class FuCai3D {

	/**
	 * 三星直选
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String positioningThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] temp = betNum.split("\\|");
		String[] group1 = temp[0].split(",");
		String[] group2 = temp[1].split(",");
		String[] group3 = temp[2].split(",");
		String lottery = lotteryNum.split(",")[0].trim().concat(",")
				.concat(lotteryNum.split(",")[1].trim()).concat(",")
				.concat(lotteryNum.split(",")[2].trim());
		for (int i = 0; i < group1.length; i++) {
			for (int j = 0; j < group2.length; j++) {
				for (int y = 0; y < group3.length; y++) {
					String betTemp = group1[i].trim().concat(",")
							.concat(group2[j].trim()).concat(",")
							.concat(group3[y].trim());
					if (betTemp.equals(lottery))
						awardcount++;
				}
			}
		}
		return level+","+awardcount;
	}
	
	/**
	 * 三星直选手工录入
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String positioningUpLoadThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] betTemp = betNum.split(",");
		for(String str:betTemp){
			String temp = str.trim().substring(0,1).concat(",").concat(str.trim().substring(1,2)).concat(",").concat(str.trim().substring(2,3));
			if(lotteryNum.equals(temp))awardcount++;
		}
		return level+","+awardcount;
	}

	/**
	 * 三星直选 和值
	 * 
	 * @return
	 */
	public String positioningsumThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] betGroup = betNum.split(",");				
		String[] lotteryTemp = lotteryNum.split(",");
		int temp = Integer.parseInt(lotteryTemp[0])
				+ Integer.parseInt(lotteryTemp[1])
				+ Integer.parseInt(lotteryTemp[2]);
		for(String bet : betGroup){
			if (temp == Integer.parseInt(bet))
				awardcount++;
		}
		return level+","+awardcount;
	}
	
	/**
	 * 三星直选胆拖
	 * @param bile
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String positioningblieThree(String bile, String betNum,String lotteryNum){
		int awardcount = 0;
		int level = 1;
		String[] lotteryTemp = lotteryNum.split(",");
		String[] betTemp = betNum.split(",");
		String lottery = lotteryTemp[0].trim().concat(",")+lotteryTemp[1].trim().concat(",")+lotteryTemp[2].trim();
		//组三不中
		if(!lotteryTemp[0].trim().equals(lotteryTemp[1].trim())
			&&!lotteryTemp[0].trim().equals(lotteryTemp[1].trim())
				&&!lotteryTemp[1].trim().equals(lotteryTemp[2].trim())){
			if(lotteryTemp[0].trim().equals(lotteryTemp[1].trim())&&lotteryTemp[0].trim().equals(lotteryTemp[2].trim())){
				return level+","+awardcount;
			}
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
				int tempCount = 0;
				for(String num : bet.split(",")){
					if(lottery.indexOf(num)>-1){
						tempCount++;
					}
				}
				if(tempCount>=3){
					awardcount++;
				}
			}	
		}else{
			return level+","+awardcount;
		}
		
		return level+","+awardcount;
	}
	
	/**
	 * 三星直选 跨度
	 * 
	 * @return
	 */
	public String spanThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String lottery = lotteryNum.split(",")[0].trim().concat(",")
				.concat(lotteryNum.split(",")[1].trim()).concat(",")
				.concat(lotteryNum.split(",")[2].trim());
		int max = PlayUtil.maxInt(lottery.split(","));
		int mini = PlayUtil.miniInit(lottery.split(","));
		String[] betGroup = betNum.split(",");
		for(String bet:betGroup){
			if (Integer.parseInt(bet) == (max - mini))
				awardcount++;
		}
		return level+","+awardcount;
	}


	/**
	 * 三星 组三标准
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupthreeThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] temp = betNum.split("\\|")[0].split(",");
		String[] lastTemp = betNum.split("\\|")[2].split(",");
		//开奖号码不符合三星组三要求
		if(!lotteryNum.split(",")[0].trim().equals(lotteryNum.split(",")[1].trim())
			&&!lotteryNum.split(",")[1].trim().equals(lotteryNum.split(",")[2].trim())
				&&!lotteryNum.split(",")[0].trim().equals(lotteryNum.split(",")[2].trim())){
			return level+","+awardcount;
		}
		//豹子不中
		if(lotteryNum.split(",")[0].trim().equals(lotteryNum.split(",")[1].trim())
				&& lotteryNum.split(",")[1].trim().equals(lotteryNum.split(",")[2].trim())){
			return level+","+awardcount;
		}
		String lottery = lotteryNum.split(",")[0].trim().concat(",")
				.concat(lotteryNum.split(",")[1].trim()).concat(",")
				.concat(lotteryNum.split(",")[2].trim());
		for(String strtemp : temp){
			for (String str : lastTemp) {
				if(strtemp.equals(str))continue;
				int count = 0;
				if(lotteryNum.split(",")[0].trim().equals(strtemp)){
					count++;
				}
				if(lotteryNum.split(",")[1].trim().equals(strtemp)){
					count++;
				}
				if(lotteryNum.split(",")[2].trim().equals(strtemp)){
					count++;
				}
				int count1 = 0;
				if(lottery.indexOf(str)>-1){
					count1++;
				}
				if(count == 2 && count1>0){
					awardcount++;
				}
			}
		}
		return level+","+awardcount;
	}

	/**
	 * 三星 组三包号
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupthreePackageThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] bets = betNum.split(",");
		String[] tempstr = lotteryNum.split(",");
		String lottery = lotteryNum.split(",")[0].trim().concat(",")
				.concat(lotteryNum.split(",")[1].trim()).concat(",")
				.concat(lotteryNum.split(",")[2].trim());
		//开奖号码不符合三星组三要求
		if(!lotteryNum.split(",")[0].trim().equals(lotteryNum.split(",")[1].trim())
			&&!lotteryNum.split(",")[1].trim().equals(lotteryNum.split(",")[2].trim())
				&&!lotteryNum.split(",")[0].trim().equals(lotteryNum.split(",")[2].trim())){
				return level+","+awardcount;
		}
		//开奖号码不符合三星组三要求
		if(lotteryNum.split(",")[0].trim().equals(lotteryNum.split(",")[1].trim())
				&& lotteryNum.split(",")[1].trim().equals(lotteryNum.split(",")[2].trim())){
				return level+","+awardcount;
		}
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
			String[] betTemp = bet.split(",");
			String[] lotteryTemp = lottery.split(",");
			int few = 0;
			for (int i = 0; i < betTemp.length; i++) {
				for (int j = 0; j < lotteryTemp.length; j++) {
					if (betTemp[i].trim().equals(lotteryTemp[j].trim()))
						few++;
				}
			}
			if (few >= 3)
				awardcount++;
		}
		return level+","+awardcount;
	}

	/**
	 * 三星 组三 和值
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupsumthreeThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] lotteryTemp = lotteryNum.split(",");
		String[] tempstr = lotteryNum.split(",");
		//开奖号码不符合三星组三要求
		if(!lotteryNum.split(",")[0].trim().equals(lotteryNum.split(",")[1].trim())
			&&!lotteryNum.split(",")[1].trim().equals(lotteryNum.split(",")[2].trim())
				&&!lotteryNum.split(",")[0].trim().equals(lotteryNum.split(",")[2].trim())){
					return level+","+awardcount;
		}
		//开奖号码不符合三星组三要求
		if(lotteryNum.split(",")[0].trim().equals(lotteryNum.split(",")[1].trim())
				&& lotteryNum.split(",")[1].trim().equals(lotteryNum.split(",")[2].trim())){
				return level+","+awardcount;
		}
		int temp = 0;
		temp = Integer.parseInt(lotteryTemp[0])
				+ Integer.parseInt(lotteryTemp[1])
				+ Integer.parseInt(lotteryTemp[2]);
		for(String bet:betNum.split(",")){
			if (temp == Integer.parseInt(bet))
				awardcount++;
		}
		return level+","+awardcount;
	}

	/**
	 * 三星 组三手工录入
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupUpLoadThree(String betNum, String lotteryNum){
		int awardcount = 0;
		int level = 1;
		//豹子不中
		String[] lotteryTemp = lotteryNum.split(",");
		if(lotteryTemp[0].trim().equals(lotteryTemp[1])&&lotteryTemp[0].equals(lotteryTemp[2])){
				return level+","+awardcount;
		}
		//非组三开奖号码不中
		if(!lotteryTemp[0].trim().equals(lotteryTemp[1].trim())
			&&!lotteryTemp[1].trim().equals(lotteryTemp[2].trim())
				&&!lotteryTemp[0].trim().equals(lotteryTemp[2].trim())){
				return level+","+awardcount;
		}
		String[] betNums = betNum.split(",");
		for(String betStr : betNums){
			String[] temp = new String[3];
			temp[0] = betStr.trim().substring(0,1);
			temp[1] = betStr.trim().substring(1,2);
			temp[2] = betStr.trim().substring(2,3);
			String lottery = lotteryTemp[0].concat(","+lotteryTemp[1]).concat(","+lotteryTemp[2]);
			String tempStr = temp[0]+","+temp[1]+","+temp[2];
			tempStr = this.sort(tempStr.trim());
			lottery = this.sort(lottery.trim());
			if (tempStr.equals(lottery))
				awardcount++;
		}
		return level+","+awardcount;
	}
	
	/**
	 * 号码排序
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
	 * 三星 组六 包号
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupsixThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] bets = betNum.split(",");
		String lottery = lotteryNum.split(",")[0].trim().concat(",")
				.concat(lotteryNum.split(",")[1].trim()).concat(",")
				.concat(lotteryNum.split(",")[2].trim());
		//豹子不中
		String[] lotteryTemp = lotteryNum.split(",");
		if(lotteryTemp[0].trim().equals(lotteryTemp[1])&&lotteryTemp[0].equals(lotteryTemp[2])){
			return level+","+awardcount;
		}
		//非组六开奖号码不中
		String[] tempstr = lotteryNum.split(",");
		if(tempstr[0].equals(tempstr[1])||tempstr[0].equals(tempstr[2])||tempstr[1].equals(tempstr[2])){
			return level+","+awardcount;
		}	
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
			String[] bettemp = bet.split(",");
			int few = 0;
			for(String str:bettemp){
				if(lottery.indexOf(str)>-1)few++;
			}
			if(few>=3)awardcount++;
		}
		return level+","+awardcount;
	}

	/**
	 * 三星 组六 和值
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupsixsumThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		//非组六开奖号码不中
		String[] tempstr = lotteryNum.split(",");
		if(tempstr[0].equals(tempstr[1])||tempstr[0].equals(tempstr[2])||tempstr[1].equals(tempstr[2])){
			return level+","+awardcount;
		}
		String[] lotteryTemp = lotteryNum.split(",");
		int temp = Integer.parseInt(lotteryTemp[0])
				+ Integer.parseInt(lotteryTemp[1])
				+ Integer.parseInt(lotteryTemp[2]);
		for(String bet : betNum.split(",")){
			if (temp == Integer.parseInt(bet))
				awardcount++;
		}
		return level+","+awardcount;
	}

	/**
	 * 前三组六 胆拖投注
	 * 
	 * @param Bile
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupSixThree(String bile, String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		//非组六开奖号码不中
		String[] tempstr = lotteryNum.split(",");
		if(tempstr[0].equals(tempstr[1])||tempstr[0].equals(tempstr[2])||tempstr[1].equals(tempstr[2])){
			return level+","+awardcount;
		}
		//豹子不中
		String[] lotteryTemp = lotteryNum.split(",");
		if(lotteryTemp[0].trim().equals(lotteryTemp[1])&&lotteryTemp[0].equals(lotteryTemp[2])){
			return level+","+awardcount;
		}
		String[] betTemp = betNum.split(",");
		String lottery = lotteryNum.split(",")[0].trim().concat(",")
				.concat(lotteryNum.split(",")[1].trim()).concat(",")
				.concat(lotteryNum.split(",")[2].trim());
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
			String[] betArranage = bet.split(",");
			Arrange strArrange = new Arrange(betArranage.length);
			while (strArrange.hasMore()) {
				int[] indexArr = strArrange.getNext();
				String betArr = "";
				for (int i = 0; i < indexArr.length; i++) {
					if (betArr.equals("")) {
						betArr = betArr.concat(betArranage[indexArr[i]].trim());
					} else {
						betArr = betArr.concat(",").concat(
								betArranage[indexArr[i]].trim());
					}
				}
				if (betArr.equals(lottery))
					awardcount++;
			}
		}
		return level+","+awardcount;
	}


	/**
	 * 三星 组六手工录入
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupUpLoadSix(String betNum, String lotteryNum){
		int awardcount = 0;
		int level = 1;
		//非组六开奖号码不中
		String[] tempstr = lotteryNum.split(",");
		if(tempstr[0].equals(tempstr[1])||tempstr[0].equals(tempstr[2])||tempstr[1].equals(tempstr[2])){
			return level+","+awardcount;
		}
		//豹子不中
		String[] lotteryTemp = lotteryNum.split(",");
		if(lotteryTemp[0].trim().equals(lotteryTemp[1])&&lotteryTemp[0].equals(lotteryTemp[2])){
			return level+","+awardcount;
		}
		String[] betNums = betNum.split(",");
		for(String betStr : betNums){
			String[] temp = new String[3];
			temp[0] = betStr.trim().substring(0,1);
			temp[1] = betStr.trim().substring(1,2);
			temp[2] = betStr.trim().substring(2,3);
			
			String lottery = tempstr[0].trim().concat(",")
					.concat(tempstr[1].trim()).concat(",")
					.concat(tempstr[2].trim());
			int count = 0;
			for(String str : temp){
				if(str.equals(""))continue;
				if(lottery.indexOf(str)>-1){
					count++;
				}
			}
			if(count>=3)awardcount++;
		}
		return level+","+awardcount;
	}
	
	/**
	 * 五星直选
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String positioningFive(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] betGroup = betNum.split("\\|");
		String[] lottery = lotteryNum.split(",");
		int few = 0;
		for(String str:betGroup[0].split(",")){
			if(str.trim().equals(lottery[0].trim()))few++;
		}
		for(String str:betGroup[1].split(",")){
			if(str.trim().equals(lottery[1].trim()))few++;		
		}
		for(String str:betGroup[2].split(",")){
			if(str.trim().equals(lottery[2].trim()))few++;
		}
		for(String str:betGroup[3].split(",")){
			if(str.trim().equals(lottery[3].trim()))few++;
		}
		for(String str:betGroup[4].split(",")){
			if(str.trim().equals(lottery[4].trim()))few++;
		}
		if(few==5)awardcount++;
		return level+","+awardcount;
	}
	
	/**
	 * 五星直选手工录入
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String positioningUpLoadFive(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] betTemp = betNum.split(",");
		for(String str:betTemp){
			String temp = str.trim().substring(0,1).concat(",").concat(str.trim().substring(1,2)).concat(",").concat(str.trim().substring(2,3))
						.concat(",").concat(str.trim().substring(3,4)).concat(",").concat(str.trim().substring(4,5));
			if(lotteryNum.equals(temp))awardcount++;
		}
		return level+","+awardcount;
	}
	
	
/***********************************************************不定位**********************************************************************/
	
	/**
	 * 三星一码不定位
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String agoNotPositioningOne(String betNum, String lotteryNum){
		int level = 1;
		int awardcount = 0;
		String[] lotteryNumTemp = lotteryNum.split(",");
		for(String str:betNum.split(",")){
			if(str.trim().equals(lotteryNumTemp[0].trim())){
				awardcount++;
			}
			if(!str.trim().equals(lotteryNumTemp[0].trim())&&str.trim().equals(lotteryNumTemp[1].trim())){
				awardcount++;			
			}
			if(!str.trim().equals(lotteryNumTemp[0].trim())&&!str.trim().equals(lotteryNumTemp[1].trim())&&str.trim().equals(lotteryNumTemp[2].trim())){
				awardcount++;
			}
		}
		return level+","+awardcount;
	}
	
	
	/**
	 * 三星二码不定位
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String agoNotPositioningTwo(String betNum, String lotteryNum){
		int level = 1;
		int awardcount = 0;
		String[] betTemp = betNum.split(",");
		String[] lotteryNumTemp = lotteryNum.split(",");
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
			if(StringUtils.isEmpty(bet)){
				continue;
			}
			String[] betArranage = bet.split(",");
			for(int i=0;i<3;i++){
				if(lotteryNumTemp[i].trim().equals(betArranage[0].trim())){
					//必须要两个号码相同才算中奖
					for(int j=0;j<3;j++){
						if(lotteryNumTemp[j].trim().equals(betArranage[1].trim())){
							awardcount++;
						}
					}
				}
			}
		}
		//只要开奖号码有对子，则只能算中1注。
		if(lotteryNumTemp[0].equals(lotteryNumTemp[1])
				||lotteryNumTemp[1].equals(lotteryNumTemp[2])
						||lotteryNumTemp[0].equals(lotteryNumTemp[2])){
			if(awardcount>1){
				awardcount = 1;
			}
		}
		return level+","+awardcount;
	}
	
	/**************前二开始*********************************/
	/**
	 * 前二星直选
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String agoPositioningTwo(String betNum, String lotteryNum) {
		int awardcount = 0;
		String[] temp = betNum.trim().split("\\|");
		String[] group1 = temp[0].split(",");
		String[] group2 = temp[1].split(",");
		String lottery = lotteryNum.split(",")[0].trim().concat(",")
				.concat(lotteryNum.split(",")[1].trim());
		for (int i = 0; i < group1.length; i++) {
			for (int j = 0; j < group2.length; j++) {
				String betTemp = group1[i].trim().concat(",")
						.concat(group2[j].trim());
				if (betTemp.equals(lottery))
					awardcount++;
			}
		}
		return 1+","+awardcount;
	}
	
	/**
	 * 前二星直选手工录入
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String agoPositioningUpLoadTwo(String betNum, String lotteryNum) {
		int awardcount = 0;
		String lottery = lotteryNum.split(",")[0].trim().concat(",")
				.concat(lotteryNum.split(",")[1].trim());
		String[] betTemp = betNum.split(",");
		for(String str:betTemp){
			String temp = str.trim().substring(0,1).concat(",").concat(str.trim().substring(1,2));
			if(lottery.equals(temp)){
				awardcount++;
			}
		}
		return 1+","+awardcount;
	}


	/**
	 * 江西 前二星组选 组选包号(不含对子)
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String agoGroupOfSelectedOrdinaryTwoJX(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] betTemp = betNum.trim().split(",");
		String lottery = lotteryNum.split(",")[0].trim().concat(",")
				.concat(lotteryNum.split(",")[1].trim());
		//判断1等奖或是2等奖
//		if(!lotteryNum.split(",")[0].trim().equals(lotteryNum.split(",")[1].trim()))level=2;
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
			String[] betArranage = bet.split(",");
			int temp = 0;
			for(String str : betArranage){
				if(lottery.indexOf(str.trim())>-1){
					temp++;
				}
			}
			//若只选两个号，开奖号后两位为对子时不中奖
			if(level==1&&betTemp.length<=2&&temp>lottery.split(",").length)continue;
			if(temp>=lottery.split(",").length)awardcount++;
		}
		return level+","+awardcount;
	}
	
	/**
	 * 重庆 前二星组选 组选包号
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String agoGroupOfSelectedOrdinaryTwoCQ(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 2;
		String[] betTemp = betNum.trim().split(",");
		String lottery = lotteryNum.split(",")[0].trim().concat(",")
				.concat(lotteryNum.split(",")[1].trim());
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
			String[] betArranage = bet.split(",");
			int temp = 0;
			for(String str : betArranage){
				if(lottery.indexOf(str.trim())>-1){
					temp++;
				}
			}
			if(temp>=lottery.split(",").length)awardcount++;
		}
		return level+","+awardcount;
	}
	
	/**
	 * 前二星和值
	 * 
	 * @return
	 */
	public String agoPositioningsumTwo(String betNum, String lotteryNum) {
		int level = 1;
		for(String str:betNum.split(",")){
			int bet = Integer.parseInt(str);
			String[] lotteryTemp = lotteryNum.split(",");
			int temp = 0;
			temp = Integer.parseInt(lotteryTemp[0])
					+ Integer.parseInt(lotteryTemp[1]);
			if (temp == bet)
				return level+","+1;
		}
		return level+","+0;
	}

	/**
	 * 前二星和值
	 * 
	 * @return
	 */
	public String agoGroupsumTwo(String betNum, String lotteryNum) {
		int level = 1;
		//判断1等奖或是2等奖
		if(!lotteryNum.split(",")[0].trim().equals(lotteryNum.split(",")[1].trim()))level=2;
		for(String str:betNum.split(",")){
			int bet = Integer.parseInt(str);
			String[] lotteryTemp = lotteryNum.split(",");
			int temp = 0;
			temp = Integer.parseInt(lotteryTemp[0])
					+ Integer.parseInt(lotteryTemp[1]);
			if (temp == bet)
				return level+","+1;
		}
		return level+","+0;
	}
	/**
	 * 江西二星组选胆拖
	 */
	public String groupOfSelectedBileDragTwo(String bile, String betNum,String lotteryNum){
		int level = 1;
		//判断1等奖或是2等奖
		if(!lotteryNum.split(",")[1].trim().equals(lotteryNum.split(",")[2].trim()))level=2;
		int awardcount = 0;
		String[] betTemp = betNum.trim().split(",");
		String lottery = lotteryNum.split(",")[1].trim().concat(",")
				.concat(lotteryNum.split(",")[2].trim());
		for (String str : betTemp) {
			if (str.equals(bile))
				continue;
			str = bile.concat(",").concat(str);
			String[] betArranage = str.split(",");
			int temp = 0;
			for (String strtemp : betArranage) {
				if (lottery.indexOf(strtemp.trim()) > -1) {
					temp++;
				}
			}
			// 若只选两个号，开奖号后两位为对子时不中奖
			if (level == 1 && betTemp.length <= 2
					&& temp > lottery.split(",").length)
				continue;
			if (temp >= lottery.split(",").length)
				awardcount++;
		}
		return level+","+awardcount;
	}
	
	/**
	 * 江西前二星组选胆拖
	 */
	public String agoGroupOfSelectedBileDragTwo(String bile, String betNum,String lotteryNum){
		int level = 1;
		//判断1等奖或是2等奖
		if(!lotteryNum.split(",")[0].trim().equals(lotteryNum.split(",")[1].trim()))level=2;
		int awardcount = 0;
		String[] betTemp = betNum.trim().split(",");
		String lottery = lotteryNum.split(",")[0].trim().concat(",")
				.concat(lotteryNum.split(",")[1].trim());
		for (String str : betTemp) {
			if (str.equals(bile))
				continue;
			str = bile.concat(",").concat(str);
			String[] betArranage = str.split(",");
			int temp = 0;
			for (String strtemp : betArranage) {
				if (lottery.indexOf(strtemp.trim()) > -1) {
					temp++;
				}
			}
			// 若只选两个号，开奖号后两位为对子时不中奖
			if (level == 1 && betTemp.length <= 2
					&& temp > lottery.split(",").length)
				continue;
			if (temp >= lottery.split(",").length)
				awardcount++;
		}
		return level+","+awardcount;
	}
	/** 前二结束************************************/
	/**
	 * 后二星直选
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String positioningTwo(String betNum, String lotteryNum) {
		int awardcount = 0;
		String[] temp = betNum.trim().split("\\|");
		String[] group1 = temp[1].split(",");
		String[] group2 = temp[2].split(",");
		String lottery = lotteryNum.split(",")[1].trim().concat(",")
				.concat(lotteryNum.split(",")[2].trim());
		for (int i = 0; i < group1.length; i++) {
			for (int j = 0; j < group2.length; j++) {
				String betTemp = group1[i].trim().concat(",")
						.concat(group2[j].trim());
				if (betTemp.equals(lottery))
					awardcount++;
			}
		}
		return 1+","+awardcount;
	}
	
	/**
	 * 后二星直选手工录入
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String positioningUpLoadTwo(String betNum, String lotteryNum) {
		int awardcount = 0;
		String lottery = lotteryNum.split(",")[1].trim().concat(",")
				.concat(lotteryNum.split(",")[2].trim());
		String[] betTemp = betNum.split(",");
		for(String str:betTemp){
			String temp = str.trim().substring(0,1).concat(",").concat(str.trim().substring(1,2));
			if(lottery.equals(temp)){
				awardcount++;
			}
		}
		return 1+","+awardcount;
	}
	
	/**
	 * 江西 二星组选 组选包号
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupOfSelectedOrdinaryTwoJX(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] betTemp = betNum.trim().split(",");
		String lottery = lotteryNum.split(",")[1].trim().concat(",")
				.concat(lotteryNum.split(",")[2].trim());
		//判断1等奖或是2等奖
		if(!lotteryNum.split(",")[1].trim().equals(lotteryNum.split(",")[2].trim()))level=2;
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
			String[] betArranage = bet.split(",");
			int temp = 0;
			for(String str : betArranage){
				if(lottery.indexOf(str.trim())>-1){
					temp++;
				}
			}
			//若只选两个号，开奖号后两位为对子时不中奖
			if(level==1&&betTemp.length<=2&&temp>lottery.split(",").length)continue;
			if(temp>=lottery.split(",").length)awardcount++;
		}
		return level+","+awardcount;
	}
	
	/**
	 * 重庆 二星组选 组选包号
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupOfSelectedOrdinaryTwoCQ(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 2;
		String[] betTemp = betNum.trim().split(",");
		String lottery = lotteryNum.split(",")[1].trim().concat(",")
				.concat(lotteryNum.split(",")[2].trim());
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
			String[] betArranage = bet.split(",");
			int temp = 0;
			for(String str : betArranage){
				if(lottery.indexOf(str.trim())>-1){
					temp++;
				}
			}
			if(temp>=lottery.split(",").length)awardcount++;
		}
		return level+","+awardcount;
	}
	/**
	 * 二星和值
	 * 
	 * @return
	 */
	public String groupsumTwo(String betNum, String lotteryNum) {
		int level = 1;
		//判断1等奖或是2等奖
		if(!lotteryNum.split(",")[1].trim().equals(lotteryNum.split(",")[2].trim()))level=2;
		for(String str:betNum.split(",")){
			int bet = Integer.parseInt(str);
			String[] lotteryTemp = lotteryNum.split(",");
			int temp = 0;
			temp = Integer.parseInt(lotteryTemp[1])
					+ Integer.parseInt(lotteryTemp[2]);
			if (temp == bet)
				return level+","+1;
		}
		return level+","+0;
	}

	/**
	 * 任选一
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String anyChooseOne(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		betNum=betNum.replaceAll("", " ");
		String[] betGroup = betNum.split("\\|");
		String[] lottery = lotteryNum.split(",");
		for(String str:betGroup[0].split(",")){
			if(str.trim().equals(lottery[0].trim()))awardcount++;
		}
		for(String str:betGroup[1].split(",")){
			if(str.trim().equals(lottery[1].trim()))awardcount++;
		}
		for(String str:betGroup[2].split(",")){
			if(str.trim().equals(lottery[2].trim()))awardcount++;
		}
		return level+","+awardcount;
	}
	
	/**
	 * 任选二
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String anyChooseTwo(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		betNum=betNum.replaceAll("", " ");
		String[] betGroup = betNum.split("\\|");
		String[] lottery = lotteryNum.split(",");
		int few = 0;
		for(String str:betGroup[0].split(",")){
			if(str.trim().equals(lottery[0].trim()))few++;
		}
		for(String str:betGroup[1].split(",")){
			if(str.trim().equals(lottery[1].trim()))few++;		
		}
		for(String str:betGroup[2].split(",")){
			if(str.trim().equals(lottery[2].trim()))few++;
		}
		if(few>=2){
			awardcount = PlayUtil.getCombinationNote(few, 2);
		}
		return level+","+awardcount;
	}
	
	/**
	 * 后二星和值
	 * 
	 * @return
	 */
	public String positioningsumTwo(String betNum, String lotteryNum) {
		int level = 1;
		for(String str:betNum.split(",")){
			int bet = Integer.parseInt(str);
			String[] lotteryTemp = lotteryNum.split(",");
			int temp = 0;
			temp = Integer.parseInt(lotteryTemp[1])
					+ Integer.parseInt(lotteryTemp[2]);
			if (temp == bet)
				return level+","+1;
		}
		return level+","+0;
	}
	/***********************************************************不定位END**********************************************************************/
	
	public static void main(String[] args) {
		FuCai3D fc = new FuCai3D();
		System.out.println(fc.agoGroupOfSelectedOrdinaryTwoJX("0,1,2,3,4","3,3,7"));
	}
}
