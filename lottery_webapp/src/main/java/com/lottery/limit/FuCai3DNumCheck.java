package com.lottery.limit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FuCai3DNumCheck {

	public List<String> fucai3dNum(String selectCode){
		List<String> list = null;
		FuCai3DLimit fc = new FuCai3DLimit();
		switch (Integer.parseInt(selectCode)) {
			case 50001:
			case 50002:
			case 50003:
			case 50004:
			case 50005:
				list = fc.positioningThree("0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9");
				break;
			case 50006:
			case 50008:
			case 50009:
				list = fc.groupthreeThree("0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9");
				break;
			case 50007:
				list = fc.groupthreePackageThree("0,1,2,3,4,5,6,7,8,9");
				break;
			case 50010:
			case 50011:
			case 50012:
			case 50013:
				list = fc.groupsixThree("0,1,2,3,4,5,6,7,8,9");
				break;
			case 50014:
			case 50015:
				list = fc.positioningFive("0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9");
				break;
			case 50016:
				list = fc.threeChooseOne("0,1,2,3,4,5,6,7,8,9");
				break;
			case 50017:
				list = fc.threeChooseTwo("0,1,2,3,4,5,6,7,8,9");
				break;
			case 50032:
			case 50040:
			case 50030:
			case 50041:
			case 50031:
			case 50039:
				list = fc.positioningTwo("*|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9");	
				break;
			case 50035:
			case 50042:
			case 50033:
			case 50045:
			case 50036:
			case 50043:	
			case 50034:
			case 50044:
				list = fc.groupOfSelectedOrdinaryTwoJX("0,1,2,3,4,5,6,7,8,9");
				break;
			case 50037:
				list = fc.anyChooseOne("0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9");
				break;
			case 50038:
				list = fc.anyChooseTwo("0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9");
				break;
			default:
				list = new ArrayList<String>();
				break;
			}
			return list;
	}
	
	public static void main(String[] args) {
	}
}
