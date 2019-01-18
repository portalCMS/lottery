package com.lottery.core.play;

import java.lang.reflect.Method;
import java.util.Arrays;

import com.xl.lottery.core.algorithm.Arrange;
import com.xl.lottery.core.algorithm.Combination;

public class ElevenSelectedFivePlay extends GenericLottery {
	private static final String UNAWARD="1,0";
	/**
	 * 前一直选
	 * 
	 * @param afew
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String positioningOne(String betNum, String lotteryNum) {
		int awardcount = 0;
		String[] betTemp = betNum.trim().split("\\|")[0].split(",");
		String lottery = lotteryNum.split(",")[0];
		for (String bet : betTemp) {
			if (bet.trim().equals(lottery.trim())) {
				awardcount++;
			}
		}
		return 1+","+awardcount;
	}

	/**
	 * 前一直选注数
	 * 
	 * @param betNum
	 * @return
	 */
	public static int positioningOneNote(String betNum) {
		return betNum.split("\\|")[0].split(",").length;
	}

	/**
	 * 前二直选
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String positioningTwo(String betNum, String lotteryNum) {
		int awardcount = 0;
		String[] temp = betNum.trim().split("\\|");
		String[] group1 = temp[0].split(",");
		String[] group2 = temp[1].split(",");
		String lottery = lotteryNum.split(",")[0].trim().concat(",")
				.concat(lotteryNum.split(",")[1].trim());
		for (int i = 0; i < group1.length; i++) {
			for (int j = 0; j < group2.length; j++) {
				if (group1[i].trim().equals(group2[j].trim())) {
					continue;
				}
				String betTemp = group1[i].trim().concat(",")
						.concat(group2[j].trim());
				if (betTemp.equals(lottery))
					awardcount++;
			}
		}
		return 1+","+awardcount;
	}

	/**
	 * 前二直选 注数
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public static int positioningTwoNote(String betNum) {
		int few = 0;
		String[] temp = betNum.split("\\|");
		String[] group1 = temp[0].split(",");
		String[] group2 = temp[1].split(",");
		for (int i = 0; i < group1.length; i++) {
			for (int j = 0; j < group2.length; j++) {
				if (group1[i].equals(group2[j])) {
					continue;
				}
				few++;
			}
		}
		return few;
	}

	/**
	 * 前二组选 普通投注
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupOfSelectedOrdinaryTwo(String betNum, String lotteryNum) {
		int awardcount = 0;
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
		return 1+","+awardcount;
	}

	/**
	 * 前二组选 普通投注 注数
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public static int groupOfSelectedOrdinaryTwoNote(String betNum) {
		return PlayUtil.getCombinationNote(betNum.split(",").length, 2);
	}

	/**
	 * 前二组选 胆拖投注
	 * 
	 * @param Bile
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupOfSelectedBileDragTwo(String bile, String betNum,
			String lotteryNum) {
		int awardcount = 0;
		String[] betTemp = betNum.trim().split(",");
		String lottery = lotteryNum.split(",")[0].trim().concat(",")
				.concat(lotteryNum.split(",")[1].trim());
		for (String str : betTemp) {
			if (str.equals(bile))
				continue;
			str = bile.concat(",").concat(str);
			String[] betArranage = str.split(",");
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
		return 1+","+awardcount;
	}

	/**
	 * 前二组选 胆拖投注 组数
	 * 
	 * @param Bile
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public static int groupOfSelectedBileDragTwoNote(String bile, String betNum) {
		int few = 0;
		String[] betTemp = betNum.trim().split(",");
		for (String str : betTemp) {
			if (str.equals(bile))
				continue;
			few++;
		}
		return few;
	}

	/**
	 * 前三直选
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String positioningThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		String[] temp = betNum.trim().split("\\|");
		String[] group1 = temp[0].split(",");
		String[] group2 = temp[1].split(",");
		String[] group3 = temp[2].split(",");
		String lottery = lotteryNum.split(",")[0].trim().concat(",")
				.concat(lotteryNum.split(",")[1].trim()).concat(",")
				.concat(lotteryNum.split(",")[2].trim());
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
					if (betTemp.equals(lottery))
						awardcount++;
				}
			}
		}
		return 1+","+awardcount;
	}

	/**
	 * 前三直选 注数
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public static int positioningThreeNote(String betNum) {
		int few = 0;
		String[] temp = betNum.trim().split("\\|");
		String[] group1 = temp[0].split(",");
		String[] group2 = temp[1].split(",");
		String[] group3 = temp[3].split(",");
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
					few++;
				}
			}
		}
		return few;
	}

	/**
	 * 前三组选 普通投注
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupOfSelectedOrdinaryThree(String betNum, String lotteryNum) {
		int awardcount = 0;
		String[] betTemp = betNum.trim().split(",");
		String lottery = lotteryNum.split(",")[0].trim().concat(",")
				.concat(lotteryNum.split(",")[1].trim()).concat(",")
				.concat(lotteryNum.split(",")[2].trim());
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
		return 1+","+awardcount;
	}

	/**
	 * 前三组选 普通投注 注数
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public static int groupOfSelectedOrdinaryThreeNote(String betNum) {
		return PlayUtil.getCombinationNote(betNum.split(",").length, 3);
	}

	/**
	 * 前三组选 胆拖投注
	 * 
	 * @param Bile
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String groupOfSelectedBileDragThree(String bile, String betNum,
			String lotteryNum) {
		int awardcount = 0;
		String[] betTemp = betNum.trim().split(",");
		String lottery = lotteryNum.split(",")[0].trim().concat(",")
				.concat(lotteryNum.split(",")[1].trim()).concat(",")
				.concat(lotteryNum.split(",")[2].trim());
		;
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
		return 1+","+awardcount;
	}

	/**
	 * 前三组选 胆拖投注 组数
	 * 
	 * @param Bile
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public static int groupOfSelectedBileDragThreeNote(String bile,
			String betNum) {
		int few = 0;
		if (bile.split(",").length == 2) {
			few = PlayUtil.getCombinationNote(betNum.trim().split(",").length, 1);
		} else {
			few = PlayUtil.getCombinationNote(betNum.trim().split(",").length, 2);
		}
		return few;
	}

	public static void main(String[] args) throws Exception {
		//PlayUtil esf = new PlayUtil();
		//System.out.println(esf.anyChooseBileDragX2(4,"09,03,05","01,04,02","01,09,03,05,04"));
		ElevenSelectedFivePlay efp = new ElevenSelectedFivePlay();
		System.out.println(efp.anyChooseFiveUpload("0102030405,0102030405,0103021011,0809101106,0504020103", "01,02,03,04,05"));
		
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	private Object invoke(String className, String methodName, Object[] args)
			throws Exception {
		Class clz = Class.forName(className);
		Method[] methods = clz.getMethods();
		Object obj = null;
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				obj = method.invoke(clz, args);
				break;
			}
		}
		return obj;
	}
	/**
	 * 前三直选单式
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String positioningThreeUpload(String betNum, String lotteryNum) {
		int awardcount = 0;
		//单式号码拆开
		String[] temp = betNum.trim().split("\\,");
		//前三位开奖号码
		String lottery = lotteryNum.trim().substring(0,8);
		for(int i=0;i<temp.length;i++){
			String bn = temp[i].trim();
			String[] nums = bn.split(" ");
			String bn3 = nums[0].concat(",").concat(nums[1])
					.concat(",").concat(nums[2]);
			if(bn3.trim().equals(lottery)){
				awardcount++;
			}
		}
		
		return 1+","+awardcount;
	}
	/**
	 * 任选五单式
	 * 
	 * @param betNum
	 * @param lotteryNum
	 * @return
	 */
	public String anyChooseFiveUpload(String betNum, String lotteryNum) {
		int awardcount = 0;
		//单式号码拆开
		String[] temp = betNum.trim().split("\\,");
		//开奖号码
		lotteryNum =this.sort(lotteryNum.trim());
		for(int i=0;i<temp.length;i++){
			String[] nums = temp[i].split(" ");
			String bn =nums[0].concat(",").concat(nums[1]).concat(",").concat(nums[2])
							.concat(",").concat(nums[3]).concat(",").concat(nums[4]);
			bn = this.sort(bn);
			if(bn.equals(lotteryNum)){
				awardcount++;
			}
		}
		
		return 1+","+awardcount;
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
	
	@Override
	public String invoke(int type, String bile, String betNum, String lotteryNum) {
		// TODO Auto-generated method stub
		return null;
	}

}
