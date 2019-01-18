package com.lottery.core.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.xl.lottery.core.algorithm.Arrange;
import com.xl.lottery.core.algorithm.Combination;
import com.xl.lottery.util.Util;

/**
 * 江西时时彩
 * 
 * @author CW-HP7
 * 
 */
public class OftenPlay extends GenericLottery {
	
	private static final String UNAWARD="1,0";

	/**
	 * 一星直选
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String positioningOne(String betNum, String lotteryNum) {
		int awardcount = 0;
		String[] betTemp = betNum.trim().split("\\|")[4].split(",");
		String lottery = lotteryNum.split(",")[4];
		for (String bet : betTemp) {
			if (bet.trim().equals(lottery.trim())) {
				awardcount++;
			}
		}
		return 1 + "," + awardcount;
	}

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
		String[] group1 = temp[3].split(",");
		String[] group2 = temp[4].split(",");
		String lottery = lotteryNum.split(",")[3].trim().concat(",")
				.concat(lotteryNum.split(",")[4].trim());
		for (int i = 0; i < group1.length; i++) {
			for (int j = 0; j < group2.length; j++) {
				String betTemp = group1[i].trim().concat(",")
						.concat(group2[j].trim());
				if (betTemp.equals(lottery))
					awardcount++;
			}
		}
		return 1 + "," + awardcount;
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
		String lottery = lotteryNum.split(",")[3].trim().concat(",")
				.concat(lotteryNum.split(",")[4].trim());
		String[] betTemp = betNum.split(",");
		for (String str : betTemp) {
			String temp = str.trim().substring(0, 1).concat(",")
					.concat(str.trim().substring(1, 2));
			if (lottery.equals(temp)) {
				awardcount++;
			}
		}
		return 1 + "," + awardcount;
	}

	/**
	 * 江西 二星组选 组选包号(不含对子)
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupOfSelectedOrdinaryTwoJX(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] betTemp = betNum.trim().split(",");
		String lottery = lotteryNum.split(",")[3].trim().concat(",")
				.concat(lotteryNum.split(",")[4].trim());
		// 判断1等奖或是2等奖
		if (!lotteryNum.split(",")[3].trim().equals(
				lotteryNum.split(",")[4].trim()))
			level = 2;
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
			for (String str : betArranage) {
				if (lottery.indexOf(str.trim()) > -1) {
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
		return level + "," + awardcount;
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
		String lottery = lotteryNum.split(",")[3].trim().concat(",")
				.concat(lotteryNum.split(",")[4].trim());
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
			for (String str : betArranage) {
				if (lottery.indexOf(str.trim()) > -1) {
					temp++;
				}
			}
			if (temp >= lottery.split(",").length)
				awardcount++;
		}
		return level + "," + awardcount;
	}

	/**
	 * 二星和值
	 * 
	 * @return
	 */
	public String groupsumTwo(String betNum, String lotteryNum) {
		int level = 1;
		// 判断1等奖或是2等奖
		if (!lotteryNum.split(",")[3].trim().equals(
				lotteryNum.split(",")[4].trim()))
			level = 2;
		for (String str : betNum.split(",")) {
			int bet = Integer.parseInt(str);
			String[] lotteryTemp = lotteryNum.split(",");
			int temp = 0;
			temp = Integer.parseInt(lotteryTemp[3])
					+ Integer.parseInt(lotteryTemp[4]);
			if (temp == bet)
				return level + "," + 1;
		}
		return level + "," + 0;
	}

	/**
	 * 后二星和值
	 * 
	 * @return
	 */
	public String positioningsumTwo(String betNum, String lotteryNum) {
		int level = 1;
		for (String str : betNum.split(",")) {
			int bet = Integer.parseInt(str);
			String[] lotteryTemp = lotteryNum.split(",");
			int temp = 0;
			temp = Integer.parseInt(lotteryTemp[3])
					+ Integer.parseInt(lotteryTemp[4]);
			if (temp == bet)
				return level + "," + 1;
		}
		return level + "," + 0;
	}

	/**
	 * 江西二星组选胆拖
	 */
	public String groupOfSelectedBileDragTwo(String bile, String betNum,
			String lotteryNum) {
		int level = 1;
		// 判断1等奖或是2等奖
		if (!lotteryNum.split(",")[3].trim().equals(
				lotteryNum.split(",")[4].trim()))
			level = 2;
		int awardcount = 0;
		String[] betTemp = betNum.trim().split(",");
		String lottery = lotteryNum.split(",")[3].trim().concat(",")
				.concat(lotteryNum.split(",")[4].trim());
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
		return level + "," + awardcount;
	}

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
		String[] group1 = temp[2].split(",");
		String[] group2 = temp[3].split(",");
		String[] group3 = temp[4].split(",");
		String lottery = lotteryNum.split(",")[2].trim().concat(",")
				.concat(lotteryNum.split(",")[3].trim()).concat(",")
				.concat(lotteryNum.split(",")[4].trim());
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
		return level + "," + awardcount;
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
		String lottery = lotteryNum.split(",")[2].trim().concat(",")
				.concat(lotteryNum.split(",")[3].trim()).concat(",")
				.concat(lotteryNum.split(",")[4].trim());
		String[] betTemp = betNum.split(",");
		for (String str : betTemp) {
			String temp = str.trim().substring(0, 1).concat(",")
					.concat(str.trim().substring(1, 2)).concat(",")
					.concat(str.trim().substring(2, 3));
			if (lottery.equals(temp))
				awardcount++;
		}
		return level + "," + awardcount;
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
		int temp = Integer.parseInt(lotteryTemp[2])
				+ Integer.parseInt(lotteryTemp[3])
				+ Integer.parseInt(lotteryTemp[4]);
		for (String bet : betGroup) {
			if (temp == Integer.parseInt(bet))
				awardcount++;
		}
		return level + "," + awardcount;
	}

	/**
	 * 三星直选胆拖
	 * 
	 * @param bile
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String positioningblieThree(String bile, String betNum,
			String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] lotteryTemp = lotteryNum.split(",");
		String[] betTemp = betNum.split(",");
		String lottery = lotteryTemp[2].trim().concat(",")
				+ lotteryTemp[3].trim().concat(",") + lotteryTemp[4].trim();
		// 组三不中
		if (!lotteryTemp[2].trim().equals(lotteryTemp[3].trim())
				&& !lotteryTemp[2].trim().equals(lotteryTemp[4].trim())
				&& !lotteryTemp[3].trim().equals(lotteryTemp[4].trim())) {
			if (lotteryTemp[2].trim().equals(lotteryTemp[3].trim())
					&& lotteryTemp[2].trim().equals(lotteryTemp[4].trim())) {
				return level + "," + awardcount;
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
				for (String num : bet.split(",")) {
					if (lottery.indexOf(num) > -1) {
						tempCount++;
					}
				}
				if (tempCount >= 3) {
					awardcount++;
				}
			}
		} else {
			return level + "," + awardcount;
		}

		return level + "," + awardcount;
	}

	/**
	 * 三星直选 跨度
	 * 
	 * @return
	 */
	public String spanThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String lottery = lotteryNum.split(",")[2].trim().concat(",")
				.concat(lotteryNum.split(",")[3].trim()).concat(",")
				.concat(lotteryNum.split(",")[4].trim());
		int max = PlayUtil.maxInt(lottery.split(","));
		int mini = PlayUtil.miniInit(lottery.split(","));
		String[] betGroup = betNum.split(",");
		for (String bet : betGroup) {
			if (Integer.parseInt(bet) == (max - mini))
				awardcount++;
		}
		return level + "," + awardcount;
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
		String[] temp = betNum.split("\\|")[2].split(",");
		String[] lastTemp = betNum.split("\\|")[4].split(",");
		// 豹子不中
		if (lotteryNum.split(",")[2].trim().equals(
				lotteryNum.split(",")[3].trim())
			&& lotteryNum.split(",")[2].trim().equals(
				lotteryNum.split(",")[4].trim())) {
				return level + "," + awardcount;
		}
		// 开奖号码不符合三星组三要求
		if (!lotteryNum.split(",")[2].trim().equals(
				lotteryNum.split(",")[3].trim())
				&& !lotteryNum.split(",")[3].trim().equals(
						lotteryNum.split(",")[4].trim())
				&& !lotteryNum.split(",")[2].trim().equals(
						lotteryNum.split(",")[4].trim())) {
			return level + "," + awardcount;
		}
		String lottery = lotteryNum.split(",")[2].trim().concat(",")
				.concat(lotteryNum.split(",")[3].trim()).concat(",")
				.concat(lotteryNum.split(",")[4].trim());
		for (String strtemp : temp) {
			for (String str : lastTemp) {
				if (strtemp.equals(str))
					continue;
				int count = 0;
				if (lotteryNum.split(",")[0].trim().equals(strtemp)) {
					count++;
				}
				if (lotteryNum.split(",")[1].trim().equals(strtemp)) {
					count++;
				}
				if (lotteryNum.split(",")[2].trim().equals(strtemp)) {
					count++;
				}
				int count1 = 0;
				if (lottery.indexOf(str) > -1) {
					count1++;
				}
				if (count == 2 && count1 > 0) {
					awardcount++;
				}
			}
		}
		return level + "," + awardcount;
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
		String lottery = lotteryNum.split(",")[2].trim().concat(",")
				.concat(lotteryNum.split(",")[3].trim()).concat(",")
				.concat(lotteryNum.split(",")[4].trim());
		// 豹子不中
		if (lotteryNum.split(",")[2].trim().equals(lotteryNum.split(",")[3].trim())
			&& lotteryNum.split(",")[2].trim().equals(lotteryNum.split(",")[4].trim())) {
			return level + "," + awardcount;
		}
		// 非组三开奖号码不中
		if (!lotteryNum.split(",")[2].trim().equals(
				lotteryNum.split(",")[3].trim())
				&& !lotteryNum.split(",")[3].trim().equals(
						lotteryNum.split(",")[4].trim())
				&& !lotteryNum.split(",")[2].trim().equals(
						lotteryNum.split(",")[4].trim())) {
			return level + "," + awardcount;
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
		return level + "," + awardcount;
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
		// 豹子不中
		if (lotteryNum.split(",")[2].trim().equals(lotteryNum.split(",")[3].trim())
			&& lotteryNum.split(",")[2].trim().equals(lotteryNum.split(",")[4].trim())) {
			return level + "," + awardcount;
		}
		// 非组三开奖号码不中
		if (!lotteryNum.split(",")[2].trim().equals(
				lotteryNum.split(",")[3].trim())
				&& !lotteryNum.split(",")[3].trim().equals(
						lotteryNum.split(",")[4].trim())
				&& !lotteryNum.split(",")[2].trim().equals(
						lotteryNum.split(",")[4].trim())) {
			return level + "," + awardcount;
		}
		int temp = 0;
		temp = Integer.parseInt(lotteryTemp[2])
				+ Integer.parseInt(lotteryTemp[3])
				+ Integer.parseInt(lotteryTemp[4]);
		for (String bet : betNum.split(",")) {
			if (temp == Integer.parseInt(bet))
				awardcount++;
		}
		return level + "," + awardcount;
	}

	/**
	 * 三星 组三手工录入
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupUpLoadThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] lotteryTemp = lotteryNum.split(",");
		String[] temp1 = betNum.split(",");
		// 豹子不中
		if (lotteryTemp[2].trim().equals(lotteryTemp[3])
				&& lotteryTemp[2].equals(lotteryTemp[4])) {
			return level + "," + awardcount;
		}
		// 非组三开奖号码不中
		if (!lotteryNum.split(",")[2].trim().equals(
				lotteryNum.split(",")[3].trim())
				&& !lotteryNum.split(",")[3].trim().equals(
						lotteryNum.split(",")[4].trim())
				&& !lotteryNum.split(",")[2].trim().equals(
						lotteryNum.split(",")[4].trim())) {
			return level + "," + awardcount;
		}
		String lottery = lotteryNum.split(",")[2].trim().concat(",")
				.concat(lotteryNum.split(",")[3].trim()).concat(",")
				.concat(lotteryNum.split(",")[4].trim());
		for (String betStr : temp1) {
			String[] temp = new String[3];
			temp[0] = betStr.trim().substring(0, 1);
			temp[1] = betStr.trim().substring(1, 2);
			temp[2] = betStr.trim().substring(2, 3);
			String tempStr = temp[0]+","+temp[1]+","+temp[2];
			tempStr = this.sort(tempStr.trim());
			lottery = this.sort(lottery.trim());
			if (tempStr.equals(lottery))
				awardcount++;
		}
		return level + "," + awardcount;
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
		String lottery = lotteryNum.split(",")[2].trim().concat(",")
				.concat(lotteryNum.split(",")[3].trim()).concat(",")
				.concat(lotteryNum.split(",")[4].trim());
		// 豹子不中
		if (lotteryNum.split(",")[2].trim().equals(lotteryNum.split(",")[3].trim())
			&& lotteryNum.split(",")[2].trim().equals(lotteryNum.split(",")[4].trim())) {
			return level + "," + awardcount;
		}
		// 非组六开奖号码不中
		String[] tempstr = lotteryNum.split(",");
		if (tempstr[2].equals(tempstr[3]) || tempstr[2].equals(tempstr[4])
				|| tempstr[3].equals(tempstr[4])) {
			return level + "," + awardcount;
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
			for (String str : bettemp) {
				if (lottery.indexOf(str) > -1)
					few++;
			}
			if (few >= 3)
				awardcount++;
		}
		return level + "," + awardcount;
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
		// 豹子不中
		if (lotteryNum.split(",")[2].trim().equals(lotteryNum.split(",")[3].trim())
			&& lotteryNum.split(",")[2].trim().equals(lotteryNum.split(",")[4].trim())) {
			return level + "," + awardcount;
		}
		// 非组六开奖号码不中
		String[] tempstr = lotteryNum.split(",");
		if (tempstr[2].equals(tempstr[3]) || tempstr[2].equals(tempstr[4])
				|| tempstr[3].equals(tempstr[4])) {
			return level + "," + awardcount;
		}
		String[] lotteryTemp = lotteryNum.split(",");
		int temp = Integer.parseInt(lotteryTemp[2])
				+ Integer.parseInt(lotteryTemp[3])
				+ Integer.parseInt(lotteryTemp[4]);
		for (String bet : betNum.split(",")) {
			if (temp == Integer.parseInt(bet))
				awardcount++;
		}
		return level + "," + awardcount;
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
		// 豹子不中
		if (lotteryNum.split(",")[2].trim().equals(lotteryNum.split(",")[3].trim())
			&& lotteryNum.split(",")[2].trim().equals(lotteryNum.split(",")[4].trim())) {
			return level + "," + awardcount;
		}
		// 非组六开奖号码不中
		String[] tempstr = lotteryNum.split(",");
		if (tempstr[2].equals(tempstr[3]) || tempstr[2].equals(tempstr[4])
				|| tempstr[3].equals(tempstr[4])) {
			return level + "," + awardcount;
		}
		String[] betTemp = betNum.split(",");
		String lottery = lotteryNum.split(",")[2].trim().concat(",")
				.concat(lotteryNum.split(",")[3].trim()).concat(",")
				.concat(lotteryNum.split(",")[4].trim());
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
		return level + "," + awardcount;
	}

	/**
	 * 三星 组六手工录入
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupUpLoadSix(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		// 豹子不中
		if (lotteryNum.split(",")[2].trim().equals(lotteryNum.split(",")[3].trim())
			&& lotteryNum.split(",")[2].trim().equals(lotteryNum.split(",")[4].trim())) {
			return level + "," + awardcount;
		}
		// 非组六开奖号码不中
		String[] tempstr = lotteryNum.split(",");
		if (tempstr[2].equals(tempstr[3]) || tempstr[2].equals(tempstr[4])
				|| tempstr[3].equals(tempstr[4])) {
			return level + "," + awardcount;
		}
		String lottery = lotteryNum.split(",")[2].trim().concat(",")
				.concat(lotteryNum.split(",")[3].trim()).concat(",")
				.concat(lotteryNum.split(",")[4].trim());
		String[] temp1 = betNum.split(",");
		for (String betStr : temp1) {
			String[] temp = new String[3];
			temp[0] = betStr.trim().substring(0, 1);
			temp[1] = betStr.trim().substring(1, 2);
			temp[2] = betStr.trim().substring(2, 3);
			int count = 0;
			for (String str : temp) {
				if (lottery.indexOf(str) > -1) {
					count++;
				}
			}
			if (count >= 3)
				awardcount++;
		}
		return level + "," + awardcount;
	}

	/**
	 * 四星直选
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String positioningFour(String betNum, String lotteryNum) {
		return 1 + "," + positioningFourOne(betNum, lotteryNum);
	}

	/**
	 * 四星直选 单式
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String positioningFourUpLoad(String betNum, String lotteryNum) {
		int level = 1;
		int awardcount = 0;
		for (String str : betNum.split(",")) {
			String bet = "*|" + str.substring(0, 1).concat("|")
					+ str.substring(1, 2).concat("|")
					+ str.substring(2, 3).concat("|") + str.substring(3, 4);
			awardcount += positioningFourOne(bet, lotteryNum);
		}
		return level + "," + awardcount;
	}

	/**
	 * 四星通选 单式
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String generalFourUpLoad(String betNum, String lotteryNum) {
		int awardcount = 0;
		int awardcount1 = 0;
		for (String str : betNum.split(",")) {
			String bet = "*|" + str.substring(0, 1).concat("|")
					+ str.substring(1, 2).concat("|")
					+ str.substring(2, 3).concat("|") + str.substring(3, 4);
			awardcount += positioningFourOne(bet, lotteryNum);
			awardcount1 += positioningFourTwo(bet, lotteryNum);
		}
		return 1 + "," + awardcount + ";" + 2 + "," + awardcount1;
	}

	/**
	 * 四星通选
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String generalFour(String betNum, String lotteryNum) {
		return 1 + "," + positioningFourOne(betNum, lotteryNum) + ";" + 2 + ","
				+ positioningFourTwo(betNum, lotteryNum);
	}

	/**
	 * 直选4 一等奖中奖注数
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	private int positioningFourOne(String betNum, String lotteryNum) {
		int awardcount = 0;
		String[] betGroup = betNum.split("\\|");
		String[] lottery = lotteryNum.split(",");
		int few = 0;
		for (String str : betGroup[1].split(",")) {
			if (str.trim().equals(lottery[1].trim()))
				few++;
		}
		for (String str : betGroup[2].split(",")) {
			if (str.trim().equals(lottery[2].trim()))
				few++;
		}
		for (String str : betGroup[3].split(",")) {
			if (str.trim().equals(lottery[3].trim()))
				few++;
		}
		for (String str : betGroup[4].split(",")) {
			if (str.trim().equals(lottery[4].trim()))
				few++;
		}
		if (few == 4) {
			awardcount++;
		}
		return awardcount;
	}

	/**
	 * 直选4 二等奖等奖中奖注数
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	private int positioningFourTwo(String betNum, String lotteryNum) {
		int awardcount = 0;
		String[] betGroup = betNum.split("\\|");
		String[] lottery = lotteryNum.split(",");
		int few = 0;
		// 中2、3、4位
		for (String str : betGroup[1].split(",")) {
			if (str.trim().equals(lottery[1].trim()))
				few++;
		}
		for (String str : betGroup[2].split(",")) {
			if (str.trim().equals(lottery[2].trim()))
				few++;
		}
		for (String str : betGroup[3].split(",")) {
			if (str.trim().equals(lottery[3].trim()))
				few++;
		}
		if (few >= 3) {
			awardcount += PlayUtil.getCombinationNote(
					betGroup[3].split(",").length, 1);
		}
		few = 0;
		// 中3、4、5位
		for (String str : betGroup[2].split(",")) {
			if (str.trim().equals(lottery[2].trim()))
				few++;
		}
		for (String str : betGroup[3].split(",")) {
			if (str.trim().equals(lottery[3].trim()))
				few++;
		}
		for (String str : betGroup[4].split(",")) {
			if (str.trim().equals(lottery[4].trim()))
				few++;
		}
		if (few >= 3) {
			awardcount += PlayUtil.getCombinationNote(
					betGroup[1].split(",").length, 1);
		}
		return awardcount;
	}

	/**
	 * 五星直选
	 * 
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
		for (String str : betGroup[0].split(",")) {
			if (str.trim().equals(lottery[0].trim()))
				few++;
		}
		for (String str : betGroup[1].split(",")) {
			if (str.trim().equals(lottery[1].trim()))
				few++;
		}
		for (String str : betGroup[2].split(",")) {
			if (str.trim().equals(lottery[2].trim()))
				few++;
		}
		for (String str : betGroup[3].split(",")) {
			if (str.trim().equals(lottery[3].trim()))
				few++;
		}
		for (String str : betGroup[4].split(",")) {
			if (str.trim().equals(lottery[4].trim()))
				few++;
		}
		if (few == 5)
			awardcount++;
		return level + "," + awardcount;
	}

	/**
	 * 五星直选手工录入
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String positioningUpLoadFive(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] betTemp = betNum.split(",");
		for (String str : betTemp) {
			String temp = str.trim().substring(0, 1).concat(",")
					.concat(str.trim().substring(1, 2)).concat(",")
					.concat(str.trim().substring(2, 3)).concat(",")
					.concat(str.trim().substring(3, 4)).concat(",")
					.concat(str.trim().substring(4, 5));
			if (lotteryNum.equals(temp))
				awardcount++;
		}
		return level + "," + awardcount;
	}

	/**
	 * 五星通选 三种中奖状态 1：五位全中 2：前三位或是后三位 3：前两位或是后两位
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String generalFive(String betNum, String lotteryNum) {
		String levelStr = 1 + ","
				+ getGeneralFiveOneAwardcount(betNum, lotteryNum);
		levelStr += ";" + 2 + ","
				+ getGeneralFiveTwoAwardcount(betNum, lotteryNum);
		levelStr += ";" + 3 + ","
				+ getGeneralFiveThreeAwardcount(betNum, lotteryNum);
		return levelStr;
	}

	/**
	 * 通选五 一等奖中奖计算
	 * 
	 * @return
	 */
	private int getGeneralFiveOneAwardcount(String betNum, String lotteryNum) {
		int awardcount = 0;
		String[] betGroup = betNum.split("\\|");
		String[] lottery = lotteryNum.split(",");
		int temp = 0;
		for (String str : betGroup[0].split(",")) {
			if (str.trim().equals(lottery[0].trim())) {
				temp++;
			}
		}
		for (String str : betGroup[1].split(",")) {
			if (str.trim().equals(lottery[1].trim())) {
				temp++;
			}
		}
		for (String str : betGroup[2].split(",")) {
			if (str.trim().equals(lottery[2].trim())) {
				temp++;
			}
		}
		for (String str : betGroup[3].split(",")) {
			if (str.trim().equals(lottery[3].trim())) {
				temp++;
			}
		}
		for (String str : betGroup[4].split(",")) {
			if (str.trim().equals(lottery[4].trim())) {
				temp++;
			}
		}
		if (temp >= 5)
			awardcount++;
		return awardcount;
	}

	/**
	 * 通选五 二等奖中奖计算
	 * 
	 * @return
	 */
	private int getGeneralFiveTwoAwardcount(String betNum, String lotteryNum) {
		int awardcount = 0;
		String[] betGroup = betNum.split("\\|");
		String[] lottery = lotteryNum.split(",");
		int temp = 0;
		// 判断前三位中奖
		for (String str : betGroup[0].split(",")) {
			if (str.trim().equals(lottery[0].trim())) {
				temp++;
			}
		}
		for (String str : betGroup[1].split(",")) {
			if (str.trim().equals(lottery[1].trim())) {
				temp++;
			}
		}
		for (String str : betGroup[2].split(",")) {
			if (str.trim().equals(lottery[2].trim())) {
				temp++;
			}
		}
		if (temp >= 3) {
			awardcount += PlayUtil.getCombinationNote(
					betGroup[3].split(",").length, 1)
					* PlayUtil.getCombinationNote(
							betGroup[4].split(",").length, 1);
		}
		// 判断后三位中奖
		temp = 0;
		for (String str : betGroup[2].split(",")) {
			if (str.trim().equals(lottery[2].trim())) {
				temp++;
			}
		}
		for (String str : betGroup[3].split(",")) {
			if (str.trim().equals(lottery[3].trim())) {
				temp++;
			}
		}
		for (String str : betGroup[4].split(",")) {
			if (str.trim().equals(lottery[4].trim())) {
				temp++;
			}
		}
		if (temp >= 3) {
			awardcount += PlayUtil.getCombinationNote(
					betGroup[0].split(",").length, 1)
					* PlayUtil.getCombinationNote(
							betGroup[1].split(",").length, 1);
		}
		return awardcount;
	}

	/**
	 * 通选五 三等级等奖中奖计算
	 * 
	 * @return
	 */
	private int getGeneralFiveThreeAwardcount(String betNum, String lotteryNum) {
		int awardcount = 0;
		String[] betGroup = betNum.split("\\|");
		String[] lottery = lotteryNum.split(",");
		int temp = 0;
		// 判断前2位中奖
		for (String str : betGroup[0].split(",")) {
			if (str.trim().equals(lottery[0].trim())) {
				temp++;
			}
		}
		for (String str : betGroup[1].split(",")) {
			if (str.trim().equals(lottery[1].trim())) {
				temp++;
			}
		}
		if (temp >= 2) {
			awardcount += PlayUtil.getCombinationNote(
					betGroup[2].split(",").length, 1)
					* PlayUtil.getCombinationNote(
							betGroup[3].split(",").length, 1)
					* PlayUtil.getCombinationNote(
							betGroup[4].split(",").length, 1);
		}
		// 判断后2位中奖
		temp = 0;
		for (String str : betGroup[3].split(",")) {
			if (str.trim().equals(lottery[3].trim())) {
				temp++;
			}
		}
		for (String str : betGroup[4].split(",")) {
			if (str.trim().equals(lottery[4].trim())) {
				temp++;
			}
		}
		if (temp >= 2) {
			awardcount += PlayUtil.getCombinationNote(
					betGroup[0].split(",").length, 1)
					* PlayUtil.getCombinationNote(
							betGroup[1].split(",").length, 1)
					* PlayUtil.getCombinationNote(
							betGroup[2].split(",").length, 1);
		}
		return awardcount;
	}

	/**
	 * 五星通选 三种中奖状态 1：五位全中 2：前三位或是后三位 3：前两位或是后两位
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String generalUpLoadFive(String betNum, String lotteryNum) {
		String levelStr = 1 + ","
				+ getGeneralUploadFiveOneAwardcount(betNum, lotteryNum);
		levelStr += ";" + 2 + ","
				+ getGeneralUploadFiveTwoAwardcount(betNum, lotteryNum);
		levelStr += ";" + 3 + ","
				+ getGeneralUploadFiveThreeAwardcount(betNum, lotteryNum);
		return levelStr;
	}

	/**
	 * 通选五 一等奖中奖计算
	 * 
	 * @return
	 */
	private int getGeneralUploadFiveOneAwardcount(String betNum,
			String lotteryNum) {
		int awardcount = 0;
		String[] betTemp = betNum.split(",");
		for (String str : betTemp) {
			String temp = str.trim().substring(0, 1).concat(",")
					.concat(str.trim().substring(1, 2)).concat(",")
					.concat(str.trim().substring(2, 3)).concat(",")
					.concat(str.trim().substring(3, 4)).concat(",")
					.concat(str.trim().substring(4, 5));
			if (lotteryNum.equals(temp))
				awardcount++;
		}
		return awardcount;
	}

	/**
	 * 通选五 二等奖中奖计算
	 * 
	 * @return
	 */
	private int getGeneralUploadFiveTwoAwardcount(String betNum,
			String lotteryNum) {
		int awardcount = 0;
		String[] betGroup = betNum.split(",");
		String[] lottery = lotteryNum.split(",");
		for (String str : betGroup) {
			int temp = 0;
			// 判断前三位中奖
			if (str.trim().substring(0, 1).equals(lottery[0].trim())) {
				temp++;
			}
			if (str.trim().substring(1, 2).equals(lottery[1].trim())) {
				temp++;
			}
			if (str.trim().substring(2, 3).equals(lottery[2].trim())) {
				temp++;
			}
			if (temp >= 3) {
				awardcount++;
			}
			// 判断后三位中奖
			temp = 0;
			if (str.trim().substring(2, 3).equals(lottery[2].trim())) {
				temp++;
			}
			if (str.trim().substring(3, 4).equals(lottery[3].trim())) {
				temp++;
			}
			if (str.trim().substring(4, 5).equals(lottery[4].trim())) {
				temp++;
			}
			if (temp >= 3) {
				awardcount++;
			}
		}
		return awardcount;
	}

	/**
	 * 通选五 三等级等奖中奖计算
	 * 
	 * @return
	 */
	private int getGeneralUploadFiveThreeAwardcount(String betNum,
			String lotteryNum) {
		int awardcount = 0;
		String[] betGroup = betNum.split(",");
		String[] lottery = lotteryNum.split(",");
		for (String str : betGroup) {
			int temp = 0;
			// 判断前2位中奖
			if (str.trim().substring(0, 1).trim().equals(lottery[0].trim())) {
				temp++;
			}
			if (str.trim().substring(1, 2).equals(lottery[1].trim())) {
				temp++;
			}
			if (temp >= 2) {
				awardcount++;
			}
			// 判断后2位中奖
			temp = 0;
			if (str.trim().substring(3, 4).equals(lottery[3].trim())) {
				temp++;
			}
			if (str.trim().substring(4, 5).equals(lottery[4].trim())) {
				temp++;
			}
			if (temp >= 2) {
				awardcount++;
			}
		}
		return awardcount;
	}

	/**
	 * 大小单双
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String smallbig(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] betGroup = betNum.split("\\|");
		String[] lottery = lotteryNum.split(",");
		for (String str : betGroup[0].split(",")) {
			int few = 0;
			if (str.trim().equals("小") && Integer.parseInt(lottery[3]) >= 0
					&& Integer.parseInt(lottery[3]) <= 4)
				few++;
			if (str.trim().equals("大") && Integer.parseInt(lottery[3]) >= 5
					&& Integer.parseInt(lottery[3]) <= 9)
				few++;
			if (str.trim().equals("单") && Integer.parseInt(lottery[3]) % 2 != 0)
				few++;
			if (str.trim().equals("双") && Integer.parseInt(lottery[3]) % 2 == 0)
				few++;
			for (String str1 : betGroup[1].split(",")) {
				int few1 = 0;
				if (str1.trim().equals("小")
						&& Integer.parseInt(lottery[4]) >= 0
						&& Integer.parseInt(lottery[4]) <= 4)
					few1++;
				if (few > 0 && few1 > 0) {
					awardcount++;
					few1 = 0;
				}
				if (str1.trim().equals("大")
						&& Integer.parseInt(lottery[4]) >= 5
						&& Integer.parseInt(lottery[4]) <= 9)
					few1++;
				if (few > 0 && few1 > 0) {
					awardcount++;
					few1 = 0;
				}
				if (str1.trim().equals("单")
						&& Integer.parseInt(lottery[4]) % 2 != 0)
					few1++;
				if (few > 0 && few1 > 0) {
					awardcount++;
					few1 = 0;
				}
				if (str1.trim().equals("双")
						&& Integer.parseInt(lottery[4]) % 2 == 0)
					few1++;
				if (few > 0 && few1 > 0) {
					awardcount++;
					few1 = 0;
				}
			}
		}
		return level + "," + awardcount;
	}

	/**
	 * 任选一
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String anyChooseOne(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		betNum = betNum.replaceAll("", " ");
		String[] betGroup = betNum.split("\\|");
		String[] lottery = lotteryNum.split(",");
		for (String str : betGroup[0].split(",")) {
			if (str.trim().equals(lottery[0].trim()))
				awardcount++;
		}
		for (String str : betGroup[1].split(",")) {
			if (str.trim().equals(lottery[1].trim()))
				awardcount++;
		}
		for (String str : betGroup[2].split(",")) {
			if (str.trim().equals(lottery[2].trim()))
				awardcount++;
		}
		for (String str : betGroup[3].split(",")) {
			if (str.trim().equals(lottery[3].trim()))
				awardcount++;
		}
		for (String str : betGroup[4].split(",")) {
			if (str.trim().equals(lottery[4].trim()))
				awardcount++;
		}
		return level + "," + awardcount;
	}

	/**
	 * 任选二
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String anyChooseTwo(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		betNum = betNum.replaceAll("", " ");
		String[] betGroup = betNum.split("\\|");
		String[] lottery = lotteryNum.split(",");
		int few = 0;
		for (String str : betGroup[0].split(",")) {
			if (str.trim().equals(lottery[0].trim()))
				few++;
		}
		for (String str : betGroup[1].split(",")) {
			if (str.trim().equals(lottery[1].trim()))
				few++;
		}
		for (String str : betGroup[2].split(",")) {
			if (str.trim().equals(lottery[2].trim()))
				few++;
		}
		for (String str : betGroup[3].split(",")) {
			if (str.trim().equals(lottery[3].trim()))
				few++;
		}
		for (String str : betGroup[4].split(",")) {
			if (str.trim().equals(lottery[4].trim()))
				few++;
		}
		if (few >= 2) {
			awardcount = PlayUtil.getCombinationNote(few, 2);
		}
		return level + "," + awardcount;
	}
	
	/**
	 * 任选三
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String anyChooseThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		betNum = betNum.replaceAll("", " ");
		String[] betGroup = betNum.split("\\|");
		String[] lottery = lotteryNum.split(",");
		int few = 0;
		for (String str : betGroup[0].split(",")) {
			if (str.trim().equals(lottery[0].trim()))
				few++;
		}
		for (String str : betGroup[1].split(",")) {
			if (str.trim().equals(lottery[1].trim()))
				few++;
		}
		for (String str : betGroup[2].split(",")) {
			if (str.trim().equals(lottery[2].trim()))
				few++;
		}
		for (String str : betGroup[3].split(",")) {
			if (str.trim().equals(lottery[3].trim()))
				few++;
		}
		for (String str : betGroup[4].split(",")) {
			if (str.trim().equals(lottery[4].trim()))
				few++;
		}
		if (few >= 3) {
			awardcount = PlayUtil.getCombinationNote(few, 3);
		}
		return level + "," + awardcount;
	}
	
	/**
	 * 任选四
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String anyChooseFour(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		betNum = betNum.replaceAll("", " ");
		String[] betGroup = betNum.split("\\|");
		String[] lottery = lotteryNum.split(",");
		int few = 0;
		for (String str : betGroup[0].split(",")) {
			if (str.trim().equals(lottery[0].trim()))
				few++;
		}
		for (String str : betGroup[1].split(",")) {
			if (str.trim().equals(lottery[1].trim()))
				few++;
		}
		for (String str : betGroup[2].split(",")) {
			if (str.trim().equals(lottery[2].trim()))
				few++;
		}
		for (String str : betGroup[3].split(",")) {
			if (str.trim().equals(lottery[3].trim()))
				few++;
		}
		for (String str : betGroup[4].split(",")) {
			if (str.trim().equals(lottery[4].trim()))
				few++;
		}
		if (few >= 4) {
			awardcount = PlayUtil.getCombinationNote(few, 4);
		}
		return level + "," + awardcount;
	}

	/*********************************************************** 中三 *********************************************************************/
	/**
	 * 中三星直选
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String amongPositioningThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] temp = betNum.split("\\|");
		String[] group1 = temp[1].split(",");
		String[] group2 = temp[2].split(",");
		String[] group3 = temp[3].split(",");
		String lottery = lotteryNum.split(",")[1].trim().concat(",")
				.concat(lotteryNum.split(",")[2].trim()).concat(",")
				.concat(lotteryNum.split(",")[3].trim());
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
		return level + "," + awardcount;
	}

	/**
	 * 中三星直选手工录入
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String amongPositioningUpLoadThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String lottery = lotteryNum.split(",")[1].trim().concat(",")
				.concat(lotteryNum.split(",")[2].trim()).concat(",")
				.concat(lotteryNum.split(",")[3].trim());
		String[] betTemp = betNum.split(",");
		for (String str : betTemp) {
			String temp = str.trim().substring(0, 1).concat(",")
					.concat(str.trim().substring(1, 2)).concat(",")
					.concat(str.trim().substring(2, 3));
			if (lottery.equals(temp))
				awardcount++;
		}
		return level + "," + awardcount;
	}

	/**
	 * 中三星直选 和值
	 * 
	 * @return
	 */
	public String amongPositioningsumThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] betGroup = betNum.split(",");
		String[] lotteryTemp = lotteryNum.split(",");
		int temp = Integer.parseInt(lotteryTemp[1])
				+ Integer.parseInt(lotteryTemp[2])
				+ Integer.parseInt(lotteryTemp[3]);
		for (String bet : betGroup) {
			if (temp == Integer.parseInt(bet))
				awardcount++;
		}
		return level + "," + awardcount;
	}

	/**
	 * 中三星直选胆拖
	 * 
	 * @param bile
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String amongPositioningblieThree(String bile, String betNum,
			String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] lotteryTemp = lotteryNum.split(",");
		String[] betTemp = betNum.split(",");
		String lottery = lotteryTemp[1].trim().concat(",")
				+ lotteryTemp[2].trim().concat(",") + lotteryTemp[3].trim();
		// 组三不中
		if (!lotteryTemp[1].trim().equals(lotteryTemp[2].trim())
				&& !lotteryTemp[1].trim().equals(lotteryTemp[3].trim())
				&& !lotteryTemp[2].trim().equals(lotteryTemp[3].trim())) {
			if (lotteryTemp[1].trim().equals(lotteryTemp[2].trim())
					&& lotteryTemp[1].trim().equals(lotteryTemp[3].trim())) {
				return level + "," + awardcount;
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
				for (String num : bet.split(",")) {
					if (lottery.indexOf(num) > -1) {
						tempCount++;
					}
				}
				if (tempCount >= 3) {
					awardcount++;
				}
			}
		} else {
			return level + "," + awardcount;
		}

		return level + "," + awardcount;
	}

	/**
	 * 中三星直选 跨度
	 * 
	 * @return
	 */
	public String amongSpanThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String lottery = lotteryNum.split(",")[1].trim().concat(",")
				.concat(lotteryNum.split(",")[2].trim()).concat(",")
				.concat(lotteryNum.split(",")[3].trim());
		int max = PlayUtil.maxInt(lottery.split(","));
		int mini = PlayUtil.miniInit(lottery.split(","));
		String[] betGroup = betNum.split(",");
		for (String bet : betGroup) {
			if (Integer.parseInt(bet) == (max - mini))
				awardcount++;
		}
		return level + "," + awardcount;
	}

	/**
	 * 中三星 组三标准
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String amongGroupthreeThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] temp = betNum.split("\\|")[1].split(",");
		String[] lastTemp = betNum.split("\\|")[3].split(",");
		// 开奖号码不符合三星组三要求
		if (!lotteryNum.split(",")[1].trim().equals(
				lotteryNum.split(",")[2].trim())
				&& !lotteryNum.split(",")[2].trim().equals(
						lotteryNum.split(",")[3].trim())
				&& !lotteryNum.split(",")[1].trim().equals(
						lotteryNum.split(",")[3].trim())) {
			return level + "," + awardcount;
		}
		// 豹子不中
		if (lotteryNum.split(",")[1].trim().equals(
				lotteryNum.split(",")[2].trim())
				&& lotteryNum.split(",")[1].trim().equals(
						lotteryNum.split(",")[3].trim())) {
			return level + "," + awardcount;
		}
		String lottery = lotteryNum.split(",")[1].trim().concat(",")
				.concat(lotteryNum.split(",")[2].trim()).concat(",")
				.concat(lotteryNum.split(",")[3].trim());
		for (String strtemp : temp) {
			for (String str : lastTemp) {
				if (strtemp.equals(str))
					continue;
				int count = 0;
				if (lotteryNum.split(",")[0].trim().equals(strtemp)) {
					count++;
				}
				if (lotteryNum.split(",")[1].trim().equals(strtemp)) {
					count++;
				}
				if (lotteryNum.split(",")[2].trim().equals(strtemp)) {
					count++;
				}
				int count1 = 0;
				if (lottery.indexOf(str) > -1) {
					count1++;
				}
				if (count == 2 && count1 > 0) {
					awardcount++;
				}
			}
		}
		return level + "," + awardcount;
	}

	/**
	 * 中三星 组三包号
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String amongGroupthreePackageThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] bets = betNum.split(",");
		String lottery = lotteryNum.split(",")[1].trim().concat(",")
				.concat(lotteryNum.split(",")[2].trim()).concat(",")
				.concat(lotteryNum.split(",")[3].trim());
		// 非组三开奖号码不中
		if (!lotteryNum.split(",")[1].trim().equals(
				lotteryNum.split(",")[2].trim())
				&& !lotteryNum.split(",")[2].trim().equals(
						lotteryNum.split(",")[3].trim())
				&& !lotteryNum.split(",")[1].trim().equals(
						lotteryNum.split(",")[3].trim())) {
			return level + "," + awardcount;
		}
		// 豹子不中
		if (lotteryNum.split(",")[1].trim().equals(
				lotteryNum.split(",")[2].trim())
				&& lotteryNum.split(",")[1].trim().equals(
						lotteryNum.split(",")[3].trim())) {
			return level + "," + awardcount;
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
		return level + "," + awardcount;
	}

	/**
	 * 中三星 组三 和值
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String amongGroupsumthreeThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] lotteryTemp = lotteryNum.split(",");
		// 非组三开奖号码不中
		if (!lotteryNum.split(",")[1].trim().equals(
				lotteryNum.split(",")[2].trim())
				&& !lotteryNum.split(",")[2].trim().equals(
						lotteryNum.split(",")[3].trim())
				&& !lotteryNum.split(",")[1].trim().equals(
						lotteryNum.split(",")[3].trim())) {
			return level + "," + awardcount;
		}
		// 豹子不中
		if (lotteryNum.split(",")[1].trim().equals(
				lotteryNum.split(",")[2].trim())
				&& lotteryNum.split(",")[1].trim().equals(
						lotteryNum.split(",")[3].trim())) {
			return level + "," + awardcount;
		}
		int temp = 0;
		temp = Integer.parseInt(lotteryTemp[1])
				+ Integer.parseInt(lotteryTemp[2])
				+ Integer.parseInt(lotteryTemp[3]);
		for (String bet : betNum.split(",")) {
			if (temp == Integer.parseInt(bet))
				awardcount++;
		}
		return level + "," + awardcount;
	}

	/**
	 * 中三星 组三手工录入
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String amongGroupUpLoadThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] lotteryTemp = lotteryNum.split(",");
		String[] temp1 = betNum.split(",");
		// 豹子不中
		if (lotteryTemp[3].trim().equals(lotteryTemp[1])
				&& lotteryTemp[2].equals(lotteryTemp[3])) {
			return level + "," + awardcount;
		}
		// 非组三开奖号码不中
		if (!lotteryNum.split(",")[1].trim().equals(
				lotteryNum.split(",")[2].trim())
				&& !lotteryNum.split(",")[2].trim().equals(
						lotteryNum.split(",")[3].trim())
				&& !lotteryNum.split(",")[1].trim().equals(
						lotteryNum.split(",")[3].trim())) {
			return level + "," + awardcount;
		}
		String lottery = lotteryNum.split(",")[1].trim().concat(",")
				.concat(lotteryNum.split(",")[2].trim()).concat(",")
				.concat(lotteryNum.split(",")[3].trim());
		for (String betStr : temp1) {
			String[] temp = new String[3];
			temp[0] = betStr.trim().substring(0, 1);
			temp[1] = betStr.trim().substring(1, 2);
			temp[2] = betStr.trim().substring(2, 3);
			String tempStr = temp[0]+","+temp[1]+","+temp[2];
			tempStr = this.sort(tempStr.trim());
			lottery = this.sort(lottery.trim());
			if (tempStr.equals(lottery))
				awardcount++;
		}
		return level + "," + awardcount;
	}
	
	/**
	 * 大小单双
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String agoSmallbig(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] betGroup = betNum.split("\\|");
		String[] lottery = lotteryNum.split(",");
		for (String str : betGroup[0].split(",")) {
			int few = 0;
			if (str.trim().equals("小") && Integer.parseInt(lottery[0]) >= 0
					&& Integer.parseInt(lottery[0]) <= 4)
				few++;
			if (str.trim().equals("大") && Integer.parseInt(lottery[0]) >= 5
					&& Integer.parseInt(lottery[0]) <= 9)
				few++;
			if (str.trim().equals("单") && Integer.parseInt(lottery[0]) % 2 != 0)
				few++;
			if (str.trim().equals("双") && Integer.parseInt(lottery[0]) % 2 == 0)
				few++;
			for (String str1 : betGroup[1].split(",")) {
				int few1 = 0;
				if (str1.trim().equals("小")
						&& Integer.parseInt(lottery[1]) >= 0
						&& Integer.parseInt(lottery[1]) <= 4)
					few1++;
				if (few > 0 && few1 > 0) {
					awardcount++;
					few1 = 0;
				}
				if (str1.trim().equals("大")
						&& Integer.parseInt(lottery[1]) >= 5
						&& Integer.parseInt(lottery[1]) <= 9)
					few1++;
				if (few > 0 && few1 > 0) {
					awardcount++;
					few1 = 0;
				}
				if (str1.trim().equals("单")
						&& Integer.parseInt(lottery[1]) % 2 != 0)
					few1++;
				if (few > 0 && few1 > 0) {
					awardcount++;
					few1 = 0;
				}
				if (str1.trim().equals("双")
						&& Integer.parseInt(lottery[1]) % 2 == 0)
					few1++;
				if (few > 0 && few1 > 0) {
					awardcount++;
					few1 = 0;
				}
			}
		}
		return level + "," + awardcount;
	}

	/**
	 * 中三星 组六 包号
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String amongGroupsixThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] bets = betNum.split(",");
		String lottery = lotteryNum.split(",")[1].trim().concat(",")
				.concat(lotteryNum.split(",")[2].trim()).concat(",")
				.concat(lotteryNum.split(",")[3].trim());
		// 豹子不中
		if (lotteryNum.split(",")[1].trim().equals(
				lotteryNum.split(",")[2].trim())
				&& lotteryNum.split(",")[1].trim().equals(
						lotteryNum.split(",")[3].trim())) {
			return level + "," + awardcount;
		}
		// 非组六开奖号码不中
		String[] tempstr = lotteryNum.split(",");
		if (tempstr[1].equals(tempstr[2]) || tempstr[1].equals(tempstr[3])
				|| tempstr[2].equals(tempstr[3])) {
			return level + "," + awardcount;
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
			for (String str : bettemp) {
				if (lottery.indexOf(str) > -1)
					few++;
			}
			if (few >= 3)
				awardcount++;
		}
		return level + "," + awardcount;
	}

	/**
	 * 中三星 组六 和值
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String amongGroupsixsumThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		// 豹子不中
		if (lotteryNum.split(",")[1].trim().equals(
				lotteryNum.split(",")[2].trim())
				&& lotteryNum.split(",")[1].trim().equals(
						lotteryNum.split(",")[3].trim())) {
			return level + "," + awardcount;
		}
		// 非组六开奖号码不中
		String[] tempstr = lotteryNum.split(",");
		if (tempstr[1].equals(tempstr[2]) || tempstr[1].equals(tempstr[3])
				|| tempstr[2].equals(tempstr[3])) {
			return level + "," + awardcount;
		}
		String[] lotteryTemp = lotteryNum.split(",");
		int temp = Integer.parseInt(lotteryTemp[1])
				+ Integer.parseInt(lotteryTemp[2])
				+ Integer.parseInt(lotteryTemp[3]);
		for (String bet : betNum.split(",")) {
			if (temp == Integer.parseInt(bet))
				awardcount++;
		}
		return level + "," + awardcount;
	}

	/**
	 * 中三组六 胆拖投注
	 * 
	 * @param Bile
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String amongGroupSixThree(String bile, String betNum,
			String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		// 豹子不中
		if (lotteryNum.split(",")[1].trim().equals(
				lotteryNum.split(",")[2].trim())
				&& lotteryNum.split(",")[1].trim().equals(
						lotteryNum.split(",")[3].trim())) {
			return level + "," + awardcount;
		}
		// 非组六开奖号码不中
		String[] tempstr = lotteryNum.split(",");
		if (tempstr[1].equals(tempstr[2]) || tempstr[1].equals(tempstr[3])
				|| tempstr[2].equals(tempstr[3])) {
			return level + "," + awardcount;
		}
		String[] betTemp = betNum.split(",");
		String lottery = lotteryNum.split(",")[1].trim().concat(",")
				.concat(lotteryNum.split(",")[2].trim()).concat(",")
				.concat(lotteryNum.split(",")[3].trim());
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
		return level + "," + awardcount;
	}

	/**
	 * 中三星 组六手工录入
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String amongGroupUpLoadSix(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		// 豹子不中
		if (lotteryNum.split(",")[1].trim().equals(
				lotteryNum.split(",")[2].trim())
				&& lotteryNum.split(",")[1].trim().equals(
						lotteryNum.split(",")[3].trim())) {
			return level + "," + awardcount;
		}
		// 非组六开奖号码不中
		String[] tempstr = lotteryNum.split(",");
		if (tempstr[1].equals(tempstr[2]) || tempstr[1].equals(tempstr[3])
				|| tempstr[2].equals(tempstr[3])) {
			return level + "," + awardcount;
		}
		String lottery = lotteryNum.split(",")[1].trim().concat(",")
				.concat(lotteryNum.split(",")[2].trim()).concat(",")
				.concat(lotteryNum.split(",")[3].trim());
		String[] temp1 = betNum.split(",");
		for (String betStr : temp1) {
			String[] temp = new String[3];
			temp[0] = betStr.trim().substring(0, 1);
			temp[1] = betStr.trim().substring(1, 2);
			temp[2] = betStr.trim().substring(2, 3);
			int count = 0;
			for (String str : temp) {
				if (lottery.indexOf(str) > -1) {
					count++;
				}
			}
			if (count >= 3)
				awardcount++;
		}
		return level + "," + awardcount;
	}

	/*********************************************************** 中三END *********************************************************************/

	/*********************************************************** 前三 *********************************************************************/
	/**
	 * 前三星直选
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String agoPositioningThree(String betNum, String lotteryNum) {
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
		return level + "," + awardcount;
	}

	/**
	 * 前三星直选手工录入
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String agoPositioningUpLoadThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String lottery = lotteryNum.split(",")[0].trim().concat(",")
				.concat(lotteryNum.split(",")[1].trim()).concat(",")
				.concat(lotteryNum.split(",")[2].trim());
		String[] betTemp = betNum.split(",");
		for (String str : betTemp) {
			String temp = str.trim().substring(0, 1).concat(",")
					.concat(str.trim().substring(1, 2)).concat(",")
					.concat(str.trim().substring(2, 3));
			if (lottery.equals(temp))
				awardcount++;
		}
		return level + "," + awardcount;
	}

	/**
	 * 前三星直选 和值
	 * 
	 * @return
	 */
	public String agoPositioningsumThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] betGroup = betNum.split(",");
		String[] lotteryTemp = lotteryNum.split(",");
		int temp = Integer.parseInt(lotteryTemp[0])
				+ Integer.parseInt(lotteryTemp[1])
				+ Integer.parseInt(lotteryTemp[2]);
		for (String bet : betGroup) {
			if (temp == Integer.parseInt(bet))
				awardcount++;
		}
		return level + "," + awardcount;
	}

	/**
	 * 前三星直选胆拖
	 * 
	 * @param bile
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String agoPositioningblieThree(String bile, String betNum,
			String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] lotteryTemp = lotteryNum.split(",");
		String[] betTemp = betNum.split(",");
		String lottery = lotteryTemp[0].trim().concat(",")
				+ lotteryTemp[1].trim().concat(",") + lotteryTemp[2].trim();
		// 组三不中
		if (!lotteryTemp[0].trim().equals(lotteryTemp[1].trim())
				&& !lotteryTemp[0].trim().equals(lotteryTemp[2].trim())
				&& !lotteryTemp[1].trim().equals(lotteryTemp[2].trim())) {
			if (lotteryTemp[0].trim().equals(lotteryTemp[1].trim())
					&& lotteryTemp[0].trim().equals(lotteryTemp[2].trim())) {
				return level + "," + awardcount;
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
				for (String num : bet.split(",")) {
					if (lottery.indexOf(num) > -1) {
						tempCount++;
					}
				}
				if (tempCount >= 3) {
					awardcount++;
				}
			}
		} else {
			return level + "," + awardcount;
		}

		return level + "," + awardcount;
	}

	/**
	 * 前三星直选 跨度
	 * 
	 * @return
	 */
	public String agoSpanThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String lottery = lotteryNum.split(",")[0].trim().concat(",")
				.concat(lotteryNum.split(",")[1].trim()).concat(",")
				.concat(lotteryNum.split(",")[2].trim());
		int max = PlayUtil.maxInt(lottery.split(","));
		int mini = PlayUtil.miniInit(lottery.split(","));
		String[] betGroup = betNum.split(",");
		for (String bet : betGroup) {
			if (Integer.parseInt(bet) == (max - mini))
				awardcount++;
		}
		return level + "," + awardcount;
	}

	/**
	 * 前三星 组三标准
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String agoGroupthreeThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] temp = betNum.split("\\|")[0].split(",");
		String[] lastTemp = betNum.split("\\|")[1].split(",");
		// 豹子不中
		if (lotteryNum.split(",")[0].trim().equals(
				lotteryNum.split(",")[1].trim())
				&& lotteryNum.split(",")[0].trim().equals(
						lotteryNum.split(",")[2].trim())) {
			return level + "," + awardcount;
		}
		// 开奖号码不符合三星组三要求
		if (!lotteryNum.split(",")[0].trim().equals(
				lotteryNum.split(",")[1].trim())
				&& !lotteryNum.split(",")[1].trim().equals(
						lotteryNum.split(",")[2].trim())
				&& !lotteryNum.split(",")[0].trim().equals(
						lotteryNum.split(",")[2].trim())) {
			return level + "," + awardcount;
		}
		String lottery = lotteryNum.split(",")[0].trim().concat(",")
				.concat(lotteryNum.split(",")[1].trim()).concat(",")
				.concat(lotteryNum.split(",")[2].trim());
		for (String strtemp : temp) {
			for (String str : lastTemp) {
				if (strtemp.equals(str))
					continue;
				int count = 0;
				if (lotteryNum.split(",")[0].trim().equals(strtemp)) {
					count++;
				}
				if (lotteryNum.split(",")[1].trim().equals(strtemp)) {
					count++;
				}
				if (lotteryNum.split(",")[2].trim().equals(strtemp)) {
					count++;
				}
				int count1 = 0;
				if (lottery.indexOf(str) > -1) {
					count1++;
				}
				if (count == 2 && count1 > 0) {
					awardcount++;
				}
			}
		}
		return level + "," + awardcount;
	}

	/**
	 * 前三星 组三包号
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String agoGroupthreePackageThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] bets = betNum.split(",");
		String lottery = lotteryNum.split(",")[0].trim().concat(",")
				.concat(lotteryNum.split(",")[1].trim()).concat(",")
				.concat(lotteryNum.split(",")[2].trim());
		// 豹子不中
		if (lotteryNum.split(",")[0].trim().equals(
				lotteryNum.split(",")[1].trim())
				&& lotteryNum.split(",")[0].trim().equals(
						lotteryNum.split(",")[2].trim())) {
			return level + "," + awardcount;
		}
		// 非组三开奖号码不中
		if (!lotteryNum.split(",")[0].trim().equals(
				lotteryNum.split(",")[1].trim())
				&& !lotteryNum.split(",")[1].trim().equals(
						lotteryNum.split(",")[2].trim())
				&& !lotteryNum.split(",")[0].trim().equals(
						lotteryNum.split(",")[2].trim())) {
			return level + "," + awardcount;
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
		return level + "," + awardcount;
	}

	/**
	 * 前三星 组三 和值
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String agoGroupsumthreeThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] lotteryTemp = lotteryNum.split(",");
		// 豹子不中
		if (lotteryNum.split(",")[0].trim().equals(
				lotteryNum.split(",")[1].trim())
				&& lotteryNum.split(",")[0].trim().equals(
						lotteryNum.split(",")[2].trim())) {
			return level + "," + awardcount;
		}
		// 非组三开奖号码不中
		if (!lotteryNum.split(",")[0].trim().equals(
				lotteryNum.split(",")[1].trim())
				&& !lotteryNum.split(",")[1].trim().equals(
						lotteryNum.split(",")[2].trim())
				&& !lotteryNum.split(",")[0].trim().equals(
						lotteryNum.split(",")[2].trim())) {
			return level + "," + awardcount;
		}
		int temp = 0;
		temp = Integer.parseInt(lotteryTemp[0])
				+ Integer.parseInt(lotteryTemp[1])
				+ Integer.parseInt(lotteryTemp[2]);
		for (String bet : betNum.split(",")) {
			if (temp == Integer.parseInt(bet))
				awardcount++;
		}
		return level + "," + awardcount;
	}

	/**
	 * 前三星 组三手工录入
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String agoGroupUpLoadThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] lotteryTemp = lotteryNum.split(",");
		String[] temp1 = betNum.split(",");
		// 豹子不中
		if (lotteryTemp[2].trim().equals(lotteryTemp[0])
				&& lotteryTemp[1].equals(lotteryTemp[2])) {
			return level + "," + awardcount;
		}
		// 非组三开奖号码不中
		if (!lotteryNum.split(",")[0].trim().equals(
				lotteryNum.split(",")[1].trim())
				&& !lotteryNum.split(",")[1].trim().equals(
						lotteryNum.split(",")[2].trim())
				&& !lotteryNum.split(",")[0].trim().equals(
						lotteryNum.split(",")[2].trim())) {
			return level + "," + awardcount;
		}
		String lottery = lotteryNum.split(",")[0].trim().concat(",")
				.concat(lotteryNum.split(",")[1].trim()).concat(",")
				.concat(lotteryNum.split(",")[2].trim());
		for (String betStr : temp1) {
			String[] temp = new String[3];
			temp[0] = betStr.trim().substring(0, 1);
			temp[1] = betStr.trim().substring(1, 2);
			temp[2] = betStr.trim().substring(2, 3);
			String tempStr = temp[0]+","+temp[1]+","+temp[2];
			tempStr = this.sort(tempStr.trim());
			lottery = this.sort(lottery.trim());
			if (tempStr.equals(lottery))
				awardcount++;
		}
		return level + "," + awardcount;
	}

	/**
	 * 前三星 组六 包号
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String agoGroupsixThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] bets = betNum.split(",");
		String lottery = lotteryNum.split(",")[0].trim().concat(",")
				.concat(lotteryNum.split(",")[1].trim()).concat(",")
				.concat(lotteryNum.split(",")[2].trim());
		// 豹子不中
		if (lotteryNum.split(",")[0].trim().equals(
				lotteryNum.split(",")[1].trim())
				&& lotteryNum.split(",")[0].trim().equals(
						lotteryNum.split(",")[2].trim())) {
			return level + "," + awardcount;
		}
		// 非组六开奖号码不中
		String[] tempstr = lotteryNum.split(",");
		if (tempstr[0].equals(tempstr[1]) || tempstr[0].equals(tempstr[2])
				|| tempstr[1].equals(tempstr[2])) {
			return level + "," + awardcount;
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
			for (String str : bettemp) {
				if (lottery.indexOf(str) > -1)
					few++;
			}
			if (few >= 3)
				awardcount++;
		}
		return level + "," + awardcount;
	}

	/**
	 * 前三星 组六 和值
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String agoGroupsixsumThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		// 豹子不中
		if (lotteryNum.split(",")[0].trim().equals(
				lotteryNum.split(",")[1].trim())
				&& lotteryNum.split(",")[0].trim().equals(
						lotteryNum.split(",")[2].trim())) {
			return level + "," + awardcount;
		}
		// 非组六开奖号码不中
		String[] tempstr = lotteryNum.split(",");
		if (tempstr[0].equals(tempstr[1]) || tempstr[0].equals(tempstr[2])
				|| tempstr[1].equals(tempstr[2])) {
			return level + "," + awardcount;
		}
		String[] lotteryTemp = lotteryNum.split(",");
		int temp = Integer.parseInt(lotteryTemp[0])
				+ Integer.parseInt(lotteryTemp[1])
				+ Integer.parseInt(lotteryTemp[2]);
		for (String bet : betNum.split(",")) {
			if (temp == Integer.parseInt(bet))
				awardcount++;
		}
		return level + "," + awardcount;
	}

	/**
	 * 前三组六 胆拖投注
	 * 
	 * @param Bile
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String agoGroupSixThree(String bile, String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		// 豹子不中
		if (lotteryNum.split(",")[0].trim().equals(
				lotteryNum.split(",")[1].trim())
				&& lotteryNum.split(",")[0].trim().equals(
						lotteryNum.split(",")[2].trim())) {
			return level + "," + awardcount;
		}
		// 非组六开奖号码不中
		String[] tempstr = lotteryNum.split(",");
		if (tempstr[0].equals(tempstr[1]) || tempstr[0].equals(tempstr[2])
				|| tempstr[1].equals(tempstr[2])) {
			return level + "," + awardcount;
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
		return level + "," + awardcount;
	}

	/**
	 * 前三星 组六手工录入
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String agoGroupUpLoadSix(String betNum, String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		// 豹子不中
		if (lotteryNum.split(",")[0].trim().equals(
				lotteryNum.split(",")[1].trim())
				&& lotteryNum.split(",")[0].trim().equals(
						lotteryNum.split(",")[2].trim())) {
			return level + "," + awardcount;
		}
		// 非组六开奖号码不中
		String[] tempstr = lotteryNum.split(",");
		if (tempstr[0].equals(tempstr[1]) || tempstr[0].equals(tempstr[2])
				|| tempstr[1].equals(tempstr[2])) {
			return level + "," + awardcount;
		}
		String lottery = lotteryNum.split(",")[0].trim().concat(",")
				.concat(lotteryNum.split(",")[1].trim()).concat(",")
				.concat(lotteryNum.split(",")[2].trim());
		String[] temp1 = betNum.split(",");
		for (String betStr : temp1) {
			String[] temp = new String[3];
			temp[0] = betStr.trim().substring(0, 1);
			temp[1] = betStr.trim().substring(1, 2);
			temp[2] = betStr.trim().substring(2, 3);
			int count = 0;
			for (String str : temp) {
				if (lottery.indexOf(str) > -1) {
					count++;
				}
			}
			if (count >= 3)
				awardcount++;
		}
		return level + "," + awardcount;
	}

	/*********************************************************** 前三END *********************************************************************/

	/*********************************************************** 前二 ************************************************************************/
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
		return 1 + "," + awardcount;
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
		for (String str : betTemp) {
			String temp = str.trim().substring(0, 1).concat(",")
					.concat(str.trim().substring(1, 2));
			if (lottery.equals(temp)) {
				awardcount++;
			}
		}
		return 1 + "," + awardcount;
	}

	/**
	 * 江西 前二星组选 组选包号(不含对子)
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String agoGroupOfSelectedOrdinaryTwoJX(String betNum,
			String lotteryNum) {
		int awardcount = 0;
		int level = 1;
		String[] betTemp = betNum.trim().split(",");
		String lottery = lotteryNum.split(",")[0].trim().concat(",")
				.concat(lotteryNum.split(",")[1].trim());
		// 判断1等奖或是2等奖
//		if (!lotteryNum.split(",")[0].trim().equals(
//				lotteryNum.split(",")[1].trim()))
//			level = 2;
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
			for (String str : betArranage) {
				if (lottery.indexOf(str.trim()) > -1) {
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
		return level + "," + awardcount;
	}

	/**
	 * 重庆 前二星组选 组选包号
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String agoGroupOfSelectedOrdinaryTwoCQ(String betNum,
			String lotteryNum) {
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
			for (String str : betArranage) {
				if (lottery.indexOf(str.trim()) > -1) {
					temp++;
				}
			}
			if (temp >= lottery.split(",").length)
				awardcount++;
		}
		return level + "," + awardcount;
	}

	/**
	 * 前二星和值
	 * 
	 * @return
	 */
	public String agoPositioningsumTwo(String betNum, String lotteryNum) {
		int level = 1;
		for (String str : betNum.split(",")) {
			int bet = Integer.parseInt(str);
			String[] lotteryTemp = lotteryNum.split(",");
			int temp = 0;
			temp = Integer.parseInt(lotteryTemp[0])
					+ Integer.parseInt(lotteryTemp[1]);
			if (temp == bet)
				return level + "," + 1;
		}
		return level + "," + 0;
	}

	/**
	 * 前二星和值
	 * 
	 * @return
	 */
	public String agoGroupsumTwo(String betNum, String lotteryNum) {
		int level = 1;
		// 判断1等奖或是2等奖
		if (!lotteryNum.split(",")[0].trim().equals(
				lotteryNum.split(",")[1].trim()))
			level = 2;
		for (String str : betNum.split(",")) {
			int bet = Integer.parseInt(str);
			String[] lotteryTemp = lotteryNum.split(",");
			int temp = 0;
			temp = Integer.parseInt(lotteryTemp[0])
					+ Integer.parseInt(lotteryTemp[1]);
			if (temp == bet)
				return level + "," + 1;
		}
		return level + "," + 0;
	}

	/**
	 * 江西前二星组选胆拖
	 */
	public String agoGroupOfSelectedBileDragTwo(String bile, String betNum,
			String lotteryNum) {
		int level = 1;
		// 判断1等奖或是2等奖
		if (!lotteryNum.split(",")[0].trim().equals(
				lotteryNum.split(",")[1].trim()))
			level = 2;
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
		return level + "," + awardcount;
	}

	/*********************************************************** 前二END *********************************************************************/

	/*********************************************************** 不定位 **********************************************************************/

	/**
	 * 前三一码不定位
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String agoNotPositioningOne(String betNum, String lotteryNum) {
		int level = 1;
		int awardcount = 0;
		String[] lotteryNumTemp = lotteryNum.split(",");
		for (String str : betNum.split(",")) {
			if (str.trim().equals(lotteryNumTemp[0].trim())) {
				awardcount++;
			}
			if (!str.trim().equals(lotteryNumTemp[0].trim())
					&& str.trim().equals(lotteryNumTemp[1].trim())) {
				awardcount++;
			}
			if (!str.trim().equals(lotteryNumTemp[0].trim())
					&& !str.trim().equals(lotteryNumTemp[1].trim())
					&& str.trim().equals(lotteryNumTemp[2].trim())) {
				awardcount++;
			}
		}
		return level + "," + awardcount;
	}

	/**
	 * 中三一码不定位
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String amongNotPositioningOne(String betNum, String lotteryNum) {
		int level = 1;
		int awardcount = 0;
		String[] lotteryNumTemp = lotteryNum.split(",");
		for (String str : betNum.split(",")) {
			if (str.trim().equals(lotteryNumTemp[1].trim())) {
				awardcount++;
			}
			if (!str.trim().equals(lotteryNumTemp[1].trim())
					&& str.trim().equals(lotteryNumTemp[2].trim())) {
				awardcount++;
			}
			if (!str.trim().equals(lotteryNumTemp[1].trim())
					&& !str.trim().equals(lotteryNumTemp[2].trim())
					&& str.trim().equals(lotteryNumTemp[3].trim())) {
				awardcount++;
			}
		}
		return level + "," + awardcount;
	}

	/**
	 * 后三一码不定位
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String notPositioningOne(String betNum, String lotteryNum) {
		int level = 1;
		int awardcount = 0;
		String[] lotteryNumTemp = lotteryNum.split(",");
		for (String str : betNum.split(",")) {
			if (str.trim().equals(lotteryNumTemp[2].trim())) {
				awardcount++;
			}
			if (!str.trim().equals(lotteryNumTemp[2].trim())
					&& str.trim().equals(lotteryNumTemp[3].trim())) {
				awardcount++;
			}
			if (!str.trim().equals(lotteryNumTemp[2].trim())
					&& !str.trim().equals(lotteryNumTemp[3].trim())
					&& str.trim().equals(lotteryNumTemp[4].trim())) {
				awardcount++;
			}
		}
		return level + "," + awardcount;
	}

	/**
	 * 前三二码不定位
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String agoNotPositioningTwo(String betNum, String lotteryNum) {
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
			if (StringUtils.isEmpty(bet)) {
				continue;
			}
			if (bet.split(",")[0].equals(bet.split(",")[1])){
				continue;
			}
			String[] betArranage = bet.split(",");
			for (int i = 0; i < 3; i++) {
				if (lotteryNumTemp[i].trim().equals(betArranage[0].trim())) {
					// 必须要两个号码相同才算中奖
					for (int j = 0; j < 3; j++) {
						if (lotteryNumTemp[j].trim().equals(
								betArranage[1].trim())) {
							awardcount++;
						}
					}
				}
			}
		}
		// 只要开奖号码有对子，则只能算中1注。
		if (lotteryNumTemp[0].equals(lotteryNumTemp[1])
				|| lotteryNumTemp[1].equals(lotteryNumTemp[2])
				|| lotteryNumTemp[0].equals(lotteryNumTemp[2])) {
			if (awardcount > 1) {
				awardcount = 1;
			}
		}
		return level + "," + awardcount;
	}

	/**
	 * 中三二码不定位
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String amongNotPositioningTwo(String betNum, String lotteryNum) {
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
			if (StringUtils.isEmpty(bet)) {
				continue;
			}
			if (bet.split(",")[0].equals(bet.split(",")[1])){
				continue;
			}
			String[] betArranage = bet.split(",");
			
			for (int i = 1; i < 4; i++) {
				if (lotteryNumTemp[i].trim().equals(betArranage[0].trim())) {
					// 必须要两个号码相同才算中奖
					for (int j = 1; j < 4; j++) {
						if (lotteryNumTemp[j].trim().equals(
								betArranage[1].trim())) {
							awardcount++;
						}
					}
				}
			}
		}
		// 只要开奖号码有对子，则只能算中1注。
		if (lotteryNumTemp[1].equals(lotteryNumTemp[2])
				|| lotteryNumTemp[2].equals(lotteryNumTemp[3])
				|| lotteryNumTemp[1].equals(lotteryNumTemp[3])) {
			if (awardcount > 1) {
				awardcount = 1;
			}
		}
		return level + "," + awardcount;
	}

	/**
	 * 后三二码不定位
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String notPositioningTwo(String betNum, String lotteryNum) {
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
			if (StringUtils.isEmpty(bet)) {
				continue;
			}
			if (bet.split(",")[0].equals(bet.split(",")[1])){
				continue;
			}
			String[] betArranage = bet.split(",");
			for (int i = 2; i < 5; i++) {
				if (lotteryNumTemp[i].trim().equals(betArranage[0].trim())) {
					// 必须要两个号码相同才算中奖
					for (int j = 2; j < 5; j++) {
						if (lotteryNumTemp[j].trim().equals(
								betArranage[1].trim())) {
							awardcount++;
						}
					}
				}
			}
		}
		// 只要开奖号码有对子，则只能算中1注。
		if (lotteryNumTemp[2].equals(lotteryNumTemp[3])
				|| lotteryNumTemp[2].equals(lotteryNumTemp[4])
				|| lotteryNumTemp[3].equals(lotteryNumTemp[4])) {
			if (awardcount > 1) {
				awardcount = 1;
			}
		}
		return level + "," + awardcount;
	}

	/*********************************************************** 不定位END **********************************************************************/
	/**
	 * 组选120
	  * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupOfSelected120(String betNum, String lotteryNum) {
		//如果不是5个单号，则不中奖
		String[] lotNums = lotteryNum.trim().split(",");
		Map<String,String> dNumMap = new HashMap<String,String>(5);
		for(int i=0;i<lotNums.length;i++){
			for(int j=0;j<lotNums.length;j++){
				if(i!=j&&lotNums[i].equals(lotNums[j])){
					if(dNumMap.get(lotNums[i])==null){
						dNumMap.put(lotNums[i], lotNums[i]);
					}
					break;
				}
			}
		}
		
		if(dNumMap.keySet().size()!=0){
			return OftenPlay.UNAWARD;
		}
		//给投注号码和开奖号码排序一下，然后看是否投注号码包含开奖号码，如果是则中奖。
		betNum = this.sort(betNum);
		lotteryNum = this.sort(lotteryNum);
		
		lotNums = lotteryNum.split(",");
		String[] betNums = betNum.split(",");
		
		int sameCount=0;
		for(String lotNum : lotNums){
			for(String num : betNums){
				if(lotNum.equals(num)){
					sameCount++;
					break;
				}
			}
		}
		
		if(sameCount==lotNums.length){
			return "1,1";
		}
		
		return OftenPlay.UNAWARD;
	}
	
	
	
	/**
	 * 组选24
	  * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupOfSelect24(String betNum, String lotteryNum) {
		//如果不是4个单号，则不中奖
		String[] lotNums = lotteryNum.trim().split(",");
		Map<String,String> dNumMap = new HashMap<String,String>(4);
		for(int i=0;i<lotNums.length;i++){
			for(int j=0;j<lotNums.length;j++){
				if(i!=j&&lotNums[i].equals(lotNums[j])){
					if(dNumMap.get(lotNums[i])==null){
						dNumMap.put(lotNums[i], lotNums[i]);
					}
					break;
				}
			}
		}
		
		if(dNumMap.keySet().size()!=0){
			return OftenPlay.UNAWARD;
		}
		//给投注号码和开奖号码排序一下，然后看是否投注号码包含开奖号码，如果是则中奖。
		betNum = this.sort(betNum);
		lotteryNum = this.sort(lotteryNum);
		
		lotNums = lotteryNum.split(",");
		String[] betNums = betNum.split(",");
		
		int sameCount=0;
		for(String lotNum : lotNums){
			for(String num : betNums){
				if(lotNum.equals(num)){
					sameCount++;
					break;
				}
			}
		}
		
		if(sameCount==lotNums.length){
			return "1,1";
		}
		
		return OftenPlay.UNAWARD;
	}
	/**
	 * 组选24
	  * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupOfSelected24(String betNum, String lotteryNum) {
		//四星组选24 传入的开奖号码为后四
		lotteryNum = lotteryNum.trim().substring(2);
		return this.groupOfSelect24(betNum, lotteryNum);
	}
	/**
	 * 组选6
	  * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupOfSelected6(String betNum, String lotteryNum) {
		//四星组选6 传入的开奖号码为后四
		lotteryNum = lotteryNum.trim().substring(2);
		String[] lotNums = lotteryNum.split(",");
		Map<String,Integer> dNumMap = new HashMap<String,Integer>(5);
		for(String lotNum : lotNums){
			if(dNumMap.get(lotNum.trim())==null){
				dNumMap.put(lotNum.trim(), 1);
			}else{
				int count = dNumMap.get(lotNum);
				count++;
				dNumMap.remove(lotNum);
				dNumMap.put(lotNum, count);
			}
		}
		
		if(dNumMap.keySet().size()!=2){
			return OftenPlay.UNAWARD;
		}
		
		String twoNum1 = "";
		String twoNum2 = "";
		for(String key : dNumMap.keySet()){
			if(dNumMap.get(key)==2){
				if(twoNum1.length()!=0){
					twoNum2 = key;
				}else{
					twoNum1 = key;
				}
			}
		}
		//不是双对，则返回未中奖
		if(twoNum1.length()==0||twoNum2.length()==0){
			return OftenPlay.UNAWARD;
		}
		//是双对则当做开奖号码只有两个，看买的号码是否包含
		lotteryNum = twoNum1+","+twoNum2;
		return this.groupOfSelected120(betNum, lotteryNum);
	}
	/**
	 * 组选60 一对 三单
	  * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupOfSelected60(String betNum, String lotteryNum) {
		//如果不是5个单号，则不中奖
		String[] lotNums = lotteryNum.trim().split(",");
		int sameCount = 0;
		String lotDnum ="";
		for(int i=0;i<lotNums.length;i++){
			for(int j=0;j<lotNums.length;j++){
				if(i!=j&&lotNums[i].equals(lotNums[j])){
					sameCount++;
					lotDnum = lotNums[i];
					break;
				}
			}
		}
		//因为循环的是同一个字符串数组，所以sameCount=2表示，5个号码里有2个号码是相同的即为只有一个二重号的开奖结果
		if(sameCount!=2){
			return OftenPlay.UNAWARD;
		}
		//给投注号码和开奖号码排序一下，然后看是否投注号码包含开奖号码，如果是则中奖。
		String dNums = betNum.split("\\|")[0];
		String oNums = betNum.split("\\|")[1];
		dNums = this.sort(dNums);
		oNums = this.sort(oNums);
		lotteryNum = this.sort(lotteryNum);
		StringBuffer oneLotNums = new StringBuffer("");
		for(String lotNum : lotNums){
			if(!lotNum.equals(lotDnum)){
				if(StringUtils.isEmpty(oneLotNums)){
					oneLotNums.append(lotNum);
				}else{
					oneLotNums.append(","+lotNum);
				}
			}
		}
		//先判断重号是否相同
		int oneSameCount=0;
		if(dNums.trim().indexOf(lotDnum.trim())!=-1){
			//再判断单号是否被包含在所买号码中
			String[] lotOnums = oneLotNums.toString().split(",");
			String[] betOneNums = oNums.split(",");
			for(String lotNum : lotOnums){
				for(String betOneNum : betOneNums){
					if(betOneNum.equals(lotNum)){
						oneSameCount++;
						break;
					}
				}
			}
		}
		
		if(oneSameCount==lotNums.length-2){
			return "1,1";
		}
		return OftenPlay.UNAWARD;
	}
	
	/**
	 * 组选12
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupOfSelected12(String betNum, String lotteryNum) {
		//四星，所以传入后四位开奖号码
		lotteryNum = lotteryNum.trim().substring(2);
		return this.groupOfSelected60(betNum, lotteryNum);
	}
	
	/**
	 * 组选30 两对 一单
	  * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupOfSelected30(String betNum, String lotteryNum) {
		//如果不是5个单号，则不中奖
		String[] lotNums = lotteryNum.trim().split(",");
		Map<String,String> dNumMap = new HashMap<String,String>(5);
		for(int i=0;i<lotNums.length;i++){
			for(int j=0;j<lotNums.length;j++){
				if(i!=j&&lotNums[i].equals(lotNums[j])){
					if(dNumMap.get(lotNums[i])==null){
						dNumMap.put(lotNums[i], lotNums[i]);
					}
					break;
				}
			}
		}
		//因为循环的是同一个字符串数组，所以map!=2表示，5个号码里两个二重号的开奖结果
		if(dNumMap.keySet().size()!=2){
			return OftenPlay.UNAWARD;
		}
		//给投注号码和开奖号码排序一下，然后看是否投注号码包含开奖号码，如果是则中奖。
		String dNums = betNum.split("\\|")[0];
		String oNums = betNum.split("\\|")[1];
		dNums = this.sort(dNums);
		oNums = this.sort(oNums);
		lotteryNum = this.sort(lotteryNum);
		StringBuffer oneLotNums = new StringBuffer("");
		for(String lotNum : lotNums){
			if(dNumMap.get(lotNum)==null){
				if(StringUtils.isEmpty(oneLotNums)){
					oneLotNums.append(lotNum);
				}else{
					oneLotNums.append(","+lotNum);
				}
			}
		}
		//先判断重号是否相同
		int dSameCount=0;
		String[] dNumStrs = dNums.split(",");
		for(String key : dNumMap.keySet()){
			for(String dNum : dNumStrs){
				if(key.equals(dNum)){
					dSameCount++;
					break;
				}
			}
		}
		if(dSameCount!=2||oneLotNums.length()==0){
			return OftenPlay.UNAWARD;
		}
		
		//再判断单号是否被包含在所买号码中
		int oneSameCount=0;
		String[] lotOnums = oneLotNums.toString().split(",");
		String[] betOneNums = oNums.split(",");
		for(String lotNum : lotOnums){
			for(String betOneNum : betOneNums){
				if(betOneNum.equals(lotNum)){
					oneSameCount++;
					break;
				}
			}
		}
		
		if(oneSameCount==1){
			return "1,1";
		}
		return OftenPlay.UNAWARD;
	}
	
	/**
	 * 组选20 三条 二单
	  * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupOfSelected20(String betNum, String lotteryNum) {
		//如果不是5个单号，则不中奖
		String[] lotNums = lotteryNum.trim().split(",");
		Map<String,Integer> threeNumMap = new HashMap<String,Integer>(5);
		for(int i=0;i<lotNums.length;i++){
			String key = lotNums[i].trim();
			if(threeNumMap.get(key)==null){
				threeNumMap.put(key, 1);
			}else{
				int count = threeNumMap.get(key);
				count++;
				threeNumMap.remove(key);
				threeNumMap.put(key, count);
			}
		}
		//因为循环的是同一个字符串数组，所以map!=3表示，5个号码里不是三条的开奖结果
		if(threeNumMap.keySet().size()!=lotNums.length-2){
			return OftenPlay.UNAWARD;
		}
		
		String threeNum = "";
		for(String key : threeNumMap.keySet()){
			if(threeNumMap.get(key)==3){
				threeNum = key;
			}else if(threeNumMap.get(key)!=1){
				//如果剩余号码不为单号，则直接返回未中奖
				return OftenPlay.UNAWARD;
			}
		}
		//给投注号码和开奖号码排序一下，然后看是否投注号码包含开奖号码，如果是则中奖。
		String tNums = betNum.split("\\|")[0];
		String oNums = betNum.split("\\|")[1];
		tNums = this.sort(tNums);
		oNums = this.sort(oNums);
		lotteryNum = this.sort(lotteryNum);
		StringBuffer oneLotNums = new StringBuffer("");
		for(String lotNum : lotNums){
			if(!lotNum.equals(threeNum)){
				if(StringUtils.isEmpty(oneLotNums)){
					oneLotNums.append(lotNum);
				}else{
					oneLotNums.append(","+lotNum);
				}
			}
		}
		//先判断重号是否相同
		if(tNums.indexOf(threeNum)==-1){
			return OftenPlay.UNAWARD;
		}
		
		//再判断单号是否被包含在所买号码中
		int oneSameCount=0;
		String[] lotOnums = oneLotNums.toString().split(",");
		String[] betOneNums = oNums.split(",");
		for(String lotNum : lotOnums){
			for(String betOneNum : betOneNums){
				if(betOneNum.equals(lotNum)){
					oneSameCount++;
					break;
				}
			}
		}
		
		if(oneSameCount==lotNums.length-3){
			return "1,1";
		}
		return OftenPlay.UNAWARD;
	}
	/**
	 * 四星组选4
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupOfSelected4(String betNum, String lotteryNum) {
		//四星，所以传入后四位开奖号码
		lotteryNum = lotteryNum.trim().substring(2);
		return this.groupOfSelected20(betNum, lotteryNum);
	}
	/**
	 * 组选10 三条 一对
	  * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupOfSelected10(String betNum, String lotteryNum) {
		//如果不是5个单号，则不中奖
		String[] lotNums = lotteryNum.trim().split(",");
		Map<String,Integer> threeNumMap = new HashMap<String,Integer>(5);
		for(int i=0;i<lotNums.length;i++){
			String key = lotNums[i].trim();
			if(threeNumMap.get(key)==null){
				threeNumMap.put(key, 1);
			}else{
				int count = threeNumMap.get(key);
				count++;
				threeNumMap.remove(key);
				threeNumMap.put(key, count);
			}
		}
		//因为循环的是同一个字符串数组，所以map!=3表示，5个号码里不是三条的开奖结果
		if(threeNumMap.keySet().size()!=2){
			return OftenPlay.UNAWARD;
		}
		
		String threeNum = "";
		String twoNum = "";
		for(String key : threeNumMap.keySet()){
			if(threeNumMap.get(key)==3){
				threeNum = key;
			}else if(threeNumMap.get(key)==2){
				twoNum = key;
			}
		}
		//给投注号码和开奖号码排序一下，然后看是否投注号码包含开奖号码，如果是则中奖。
		String tNums = betNum.split("\\|")[0];
		String dNums = betNum.split("\\|")[1];
		tNums = this.sort(tNums);
		dNums = this.sort(dNums);
		
		//先判断重号是否相同
		if(twoNum.length()==0||tNums.indexOf(threeNum)==-1||dNums.indexOf(twoNum)==-1){
			return OftenPlay.UNAWARD;
		}
		if(tNums.indexOf(threeNum)!=-1&&dNums.indexOf(twoNum)!=-1){
			return "1,1";
		}
		
		return OftenPlay.UNAWARD;
	}
	
	/**
	 * 组选5 四条 一单
	  * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupOfSelected5(String betNum, String lotteryNum) {
		//如果不是5个单号，则不中奖
		String[] lotNums = lotteryNum.trim().split(",");
		Map<String,Integer> fourNumMap = new HashMap<String,Integer>(5);
		for(int i=0;i<lotNums.length;i++){
			String key = lotNums[i].trim();
			if(fourNumMap.get(key)==null){
				fourNumMap.put(key, 1);
			}else{
				int count = fourNumMap.get(key);
				count++;
				fourNumMap.remove(key);
				fourNumMap.put(key, count);
			}
		}
		//因为循环的是同一个字符串数组，所以map!=3表示，5个号码里不是三条的开奖结果
		if(fourNumMap.keySet().size()!=2){
			return OftenPlay.UNAWARD;
		}
		
		String fourNum = "";
		String oneNum = "";
		for(String key : fourNumMap.keySet()){
			if(fourNumMap.get(key)==4){
				fourNum = key;
			}else if(fourNumMap.get(key)==1){
				oneNum = key;
			}
		}
		//给投注号码和开奖号码排序一下，然后看是否投注号码包含开奖号码，如果是则中奖。
		String fNums = betNum.split("\\|")[0];
		String oNums = betNum.split("\\|")[1];
		fNums = this.sort(fNums);
		oNums = this.sort(oNums);
		
		//先判断重号是否相同
		if(oneNum.length()==0||fNums.indexOf(fourNum)==-1||oNums.indexOf(oneNum)==-1){
			return OftenPlay.UNAWARD;
		}
		if(fNums.indexOf(fourNum)!=-1&&oNums.indexOf(oneNum)!=-1){
			return "1,1";
		}
		
		return OftenPlay.UNAWARD;
	}
	
	/***********************************组选 end**************************************/
	
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
	
	public String invoke(int type, String bile, String betNum, String lotteryNum) {

		return null;
	}

	public static void main(String[] args) throws IOException {
		OftenPlay of = new OftenPlay();
		//String betNum = Util.BufferedReaderFile("D:\\t.txt");
		System.out.println(of.agoGroupOfSelectedOrdinaryTwoJX("0,1,2,3,4", "3,1,7"));
	//	System.out.println(of.groupUpLoadThree("001,002,003,004,005,006,007,008,009,010,011,020,022,030,033,040,044,050,055,060,066,070,077,080,088,090,099,100,101,110,112,113,114,115,116,117,118,119,121,122,131,133,141,144,151,155,161,166,171,177,181,188,191,199,200,202,211,212,220,221,223,224,225,226,227,228,229,232,233,242,244,252,255,262,266,272,277,282,288,292,299,300,303,311,313,322,323,330,331,332,334,335,336,337,338,339,343,344,353,355,363,366,373,377,383,388,393,399,400,404,411,414,422,424,433,434,440,441,442,443,445,446,447,448,449,454,455,464,466,474,477,484,488,494,499,500,505,511,515,522,525,533,535,544,545,550,551,552,553,554,556,557,558,559,565,566,575,577,585,588,595,599,600,606,611,616,622,626,633,636,644,646,655,656,660,661,662,663,664,665,667,668,669,676,677,686,688,696,699,700,707,711,717,722,727,733,737,744,747,755,757,766,767,770,771,772,773,774,775,776,778,779,787,788,797,799,800,808,811,818,822,828,833,838,844,848,855,858,866,868,877,878,880,881,882,883,884,885,886,887,889,898,899,900,909,911,919,922,929,933,939,944,949,955,959,966,969,977,979,988,989,990,991,992,993,994,995,996,997,998", "3,8,0,3,0"));
	//	System.out.println(of.agoGroupUpLoadThree("001,002,003,004,005,006,007,008,009,010,011,020,022,030,033,040,044,050,055,060,066,070,077,080,088,090,099,100,101,110,112,113,114,115,116,117,118,119,121,122,131,133,141,144,151,155,161,166,171,177,181,188,191,199,200,202,211,212,220,221,223,224,225,226,227,228,229,232,233,242,244,252,255,262,266,272,277,282,288,292,299,300,303,311,313,322,323,330,331,332,334,335,336,337,338,339,343,344,353,355,363,366,373,377,383,388,393,399,400,404,411,414,422,424,433,434,440,441,442,443,445,446,447,448,449,454,455,464,466,474,477,484,488,494,499,500,505,511,515,522,525,533,535,544,545,550,551,552,553,554,556,557,558,559,565,566,575,577,585,588,595,599,600,606,611,616,622,626,633,636,644,646,655,656,660,661,662,663,664,665,667,668,669,676,677,686,688,696,699,700,707,711,717,722,727,733,737,744,747,755,757,766,767,770,771,772,773,774,775,776,778,779,787,788,797,799,800,808,811,818,822,828,833,838,844,848,855,858,866,868,877,878,880,881,882,883,884,885,886,887,889,898,899,900,909,911,919,922,929,933,939,944,949,955,959,966,969,977,979,988,989,990,991,992,993,994,995,996,997,998", "0,3,0,3,8"));
	}

}
