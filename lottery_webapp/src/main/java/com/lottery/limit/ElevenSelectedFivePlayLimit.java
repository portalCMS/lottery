package com.lottery.limit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.xl.lottery.core.algorithm.Combination;
import com.xl.lottery.util.JsonUtil;

@Component
public class ElevenSelectedFivePlayLimit {

	
	
	/**
	 * 前一直选
	 * 
	 * @param afew
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> positioningOne(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] betTemp = betNum.split("\\|")[0].split(",");
		for (String bet : betTemp) {
			if(bet.equals(""))continue;
			list.add(bet);
		}
		return list;
	}


	/**
	 * 前二直选
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> positioningTwo(String betNum) {
		betNum = betNum.trim();
		List<String> list = new ArrayList<String>();
		String[] temp = betNum.split("\\|");
		String[] group1 = temp[0].split(",");
		String[] group2 = temp[1].split(",");
		for (int i = 0; i < group1.length; i++) {
			for (int j = 0; j < group2.length; j++) {
				if (group1[i].equals(group2[j])) {
					continue;
				}
				String betTemp = group1[i].trim().concat(",")
						.concat(group2[j].trim());
				if(betTemp.equals(""))continue;
				list.add(betTemp);
			}
		}
		return list;
	}


	/**
	 * 前二组选 普通投注
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> groupOfSelectedOrdinaryTwo(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] betTemp = betNum.split(",");
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
			if(bet.equals(""))continue;
			bet = this.sort(bet);
			list.add(bet);
		}
		return list;
	}


	/**
	 * 前二组选 胆拖投注
	 * 
	 * @param Bile
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> groupOfSelectedBileDragTwo(String bile, String betNum) {
		List<String> list = new ArrayList<String>();
		String[] betTemp = betNum.split(",");
		for (String str : betTemp) {
			if (str.equals(bile))
				continue;
			str = bile.concat(",").concat(str);
			if(str.equals(""))continue;
			str = this.sort(str);
			list.add(str);
		}
		return list;
	}

	/**
	 * 前三直选
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
		for (int i = 0; i < group1.length; i++) {
			for (int j = 0; j < group2.length; j++) {
				if (group1[i].trim().equals(group2[j].trim())) {
					continue;
				}
				for (int y = 0; y < group3.length; y++) {
					if (group3[y].trim().equals(group2[j].trim())) {
						continue;
					}
					if (group3[y].trim().equals(group1[i].trim())) {
						continue;
					}
					String betTemp = group1[i].trim().concat(",")
							.concat(group2[j].trim()).concat(",")
							.concat(group3[y].trim());
					if(betTemp.equals(""))continue;
					betTemp = this.sort(betTemp.trim());
					list.add(betTemp);
				}
			}
		}
		return list;
	}
	
	/**
	 * 前三手工录入
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> positioningThreeUpLoad(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] betCods = betNum.trim().split(",");
		for(String code : betCods){
			String[] nums = code.split(" ");
			String newCode = nums[0].concat(",").concat(nums[1]).concat(",").concat(nums[2]);
			if(code.trim().length()==8)list.add(this.sort(newCode));
		}
		return list;
	}

	/**
	 * 任选五手工录入
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> positioningFiveUpLoad(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] betCods = betNum.trim().split(",");
		for(String code : betCods){
			String[] nums = code.split(" ");
			String newCode = nums[0].concat(",").concat(nums[1]).concat(",").concat(nums[2])
					.concat(",").concat(nums[3]).concat(",").concat(nums[4]);
			if(code.trim().length()==14)list.add(this.sort(newCode));
		}
		return list;
	}


	/**
	 * 前三组选 普通投注
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> groupOfSelectedOrdinaryThree(String betNum) {
		List<String> list = new ArrayList<String>();
		String[] betTemp = betNum.split(",");
		Combination comb = new Combination(betTemp.length, 3);
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
			if(bet.equals(""))continue;
			bet = this.sort(bet);
			list.add(bet);
		}
		return list;
	}

	/**
	 * 前三组选 胆拖投注
	 * 
	 * @param Bile
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> groupOfSelectedBileDragThree(String bile, String betNum) {
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
			if(bet.equals(""))continue;
			bet = bile.concat(",").concat(bet);
			bet = this.sort(bet);
			if(bet.equals(""))continue;
			list.add(bet);
		}
		return list;
	}


	/**
	 * 任选中奖判断 普通 11X5
	 * @param few
	 * @param bet
	 * @return
	 */
	public List<String> anyChooseX(int few,String betNum){
		List<String> list = new ArrayList<String>();
		String[] betTemp = betNum.split(",");
		Combination comb = new Combination(betTemp.length,few);
		while(comb.hasMore()){
			int[] index=comb.getNext();
			String bet = "";
			for(int i=0;i<betTemp.length;i++){
				if(index[i]!=0){
					if(bet.equals("")){
						bet=bet.concat(betTemp[index[i]*i].trim());
					}else{
						bet=bet.concat(",").concat(betTemp[index[i]*i].trim());
					}
				}				
			}
			bet = this.sort(bet);
			if(bet.equals(""))continue;
			list.add(bet);
		}
		return list;
	}
	
	/**
	 * 任选胆拖是否中奖
	 * @param bile
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public List<String> anyChooseBileDragX(int few,String bile,String betNum){
		List<String> list = new ArrayList<String>();
		int betfew = 0;
		Combination comb = new Combination(betNum.split(",").length,few-bile.split(",").length);
		while(comb.hasMore()){
			int[] index=comb.getNext();
			String bet = "";
			for(int i=0;i<betNum.split(",").length;i++){
				if(index[i]!=0){
					if(bet.equals("")){
						bet=bet.concat(betNum.split(",")[index[i]*i].trim());
					}else{
						bet=bet.concat(",").concat(betNum.split(",")[index[i]*i].trim());
					}
				}				
			}
			bet = bet.concat(",").concat(bile);
			bet = this.sort(bet);
			if(bet.equals("")||bet.split(",").length<few)continue;
			list.add(bet);
		}
		return list;
	}


	/**
	 * 内部list排序类，根据modelCode排序。
	 * @author CW-HP9
	 *
	 */
	class Compare implements Comparator<String>{
			@Override
			public int compare(String o1, String o2) {
				int code1 = Integer.parseInt(o1);
				int code2 = Integer.parseInt(o2);
				if(code1>code2){
					return 1;
				}else if(code1<code2){
					return -1;
				}
				return 0;
			}
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
		case 30001:
			list = this.positioningOne(betNum);
			break;
		case 30002:
			list = this.positioningTwo(betNum);	
			break;
		case 30003:
			list = this.groupOfSelectedOrdinaryTwo(betNum);
			break;
		case 30004:
			list = this.groupOfSelectedBileDragTwo(bile, betNum);
			break;
		case 30005:
			list = this.anyChooseX(2, betNum);
			break;
		case 30006:
			list = this.anyChooseBileDragX(2, bile, betNum);
			break;
		case 30007:
			list = this.positioningThree(betNum);
			break;
		case 30008:
			list = this.positioningThreeUpLoad(betNum);
			break;
		case 30009:
			list = this.groupOfSelectedOrdinaryThree(betNum);
			break;
		case 30010:
			list = this.groupOfSelectedBileDragThree(bile, betNum);
			break;
		case 30011:
			list = this.anyChooseX(3, betNum);
			break;
		case 30012:
			list = this.anyChooseBileDragX(3, bile, betNum);
			break;
		case 30013:
			list = this.anyChooseX(4, betNum);
			break;
		case 30014:
			list = this.anyChooseBileDragX(4, bile, betNum);
			break;
		case 30015:
			list = this.anyChooseX(5, betNum);
			break;
		case 30016:
			list = this.anyChooseBileDragX(5, bile, betNum);
			break;
		case 30017:
			list = this.positioningFiveUpLoad(betNum);
			break;
		case 30018:
			list = this.anyChooseX(6, betNum);
			break;
		case 30019:
			list = this.anyChooseBileDragX(6, bile, betNum);
			break;
		case 30020:
			list = this.anyChooseX(7, betNum);
			break;
		case 30021:
			list = this.anyChooseBileDragX(7, bile, betNum);
			break;
		case 30022:
			list = this.anyChooseX(8, betNum);
			break;
		default:
			list = this.anyChooseBileDragX(8, bile, betNum);
			break;
		}
		return list;
	}
	public static void main(String[] args) {
		ElevenSelectedFivePlayLimit e = new ElevenSelectedFivePlayLimit();
		List<String> posoneList = e.anyChooseX(1,"01,02,03,04,05,06,07,08,09,10,11");
		StringBuffer sb = new StringBuffer();
		for(String num : posoneList){
			sb.append(";"+num);
		}
		System.out.println(posoneList.size()+":"+sb.toString());
	}
}
