package com.lottery.limit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfenPlayNumCheck {

	public List<String> sscNum(String selectCode){
		OftenPlayLimit opl = new OftenPlayLimit();
		List<String> list = null;
		switch (Integer.parseInt(selectCode)) {
			case 40001:
				list = opl.positioningOne("*|*|*|*|1,2,3,4,5,6,7,8,9,0");
				break;
			case 40002:
			case 40065:
			case 40003:
			case 40067:
			case 40064:
			case 40066:
				list = opl.positioningTwo("*|*|*|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9");	
				break;
			case 40004:
			case 40068:
			case 40005:
			case 40069:	
			case 40006:
			case 40070:
			case 40028:
			case 40074:
			case 40029:
				list = opl.groupOfSelectedOrdinaryTwoJX("0,1,2,3,4,5,6,7,8,9");
				break;
			case 40007:
			case 40036:
			case 40041:
			case 40008:
			case 40037:
			case 40042:
			case 40009:
			case 40038:
			case 40043:
			case 40010:
			case 40039:
			case 40044:
			case 40011:
			case 40040:
			case 40045:
				list = opl.positioningThree("*|*|1,2,3,4,5,6,7,8,9,0|1,2,3,4,5,6,7,8,9,0|1,2,3,4,5,6,7,8,9,0");
				break;
			case 40012:
			case 40046:
			case 40055:
			case 40015:
			case 40050:
			case 40058:
			case 40014:
			case 40057:
			case 40048:
				list = opl.groupthreeThree("*|*|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9");
				break;
			case 40013:
			case 40047:
			case 40056:
				list = opl.groupthreePackageThree("0,1,2,3,4,5,6,7,8,9");
				break;
			case 40016:
			case 40051:
			case 40059:
			case 40017:
			case 40052:
			case 40060:
			case 40018:
			case 40053:
			case 40061:
			case 40019:
			case 40054:
			case 40062:
				list = opl.groupsixThree("0,1,2,3,4,5,6,7,8,9");
				break;
			case 40020:
			case 40072:
			case 40071:
			case 40073:
				list = opl.positioningFour("*|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9");
				break;
			case 40023:
			case 40021:
			case 40022:
			case 40024:
				list = opl.positioningFive("0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9");
				break;
			case 40025:
			case 40063:
				list = opl.smallbig("大,小,单,双|大,小,单,双");
				break;
			case 40026:
				list = opl.anyChooseOne("0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9");
				break;
			case 40027:
				list = opl.anyChooseTwo("0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9");
				break;
			case 40030:
			case 40031:
			case 40032:
				list = opl.threeChooseOne("0,1,2,3,4,5,6,7,8,9");
				break;
			case 40033:
			case 40034:
			case 40035:
				list = opl.threeChooseTwo("0,1,2,3,4,5,6,7,8,9");
				break;
			case 40075:
			case 40081:
			case 40083:
				list = opl.groupOfSelected120_24_4(selectCode,"0,1,2,3,4,5,6,7,8,9");
				break;
			case 40076:
				list = opl.groupOfSelected60(3,"0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9");
				break;	
			case 40078:
			case 40082:
				list = opl.groupOfSelected60(2,"0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9");
				break;
			case 40079:
			case 40080:
			case 40084:
				list = opl.groupOfSelected60(1,"0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9");
				break;
			case 40077:
				list = opl.groupOfSelected30("0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9");
				break;
			case 40085:
				list = opl.anyChooseThree("0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9");
				break;
			case 40086:
				list = opl.anyChooseFour("0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9|0,1,2,3,4,5,6,7,8,9");
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
