package com.lottery.limit;

import java.util.List;

import org.springframework.stereotype.Component;


@Component
public class ElevenSelectedFiveNumCheck {

	public List<String> anyChooseNum(String selectCode){
		//修改为playCode
		int few = -1;
		List<String> anyChooseList = null;
		ElevenSelectedFivePlayLimit e = new ElevenSelectedFivePlayLimit();
		switch (Integer.parseInt(selectCode)) {
		case 30001:
			anyChooseList = e.positioningOne("01,02,03,04,05,06,07,08,09,10,11|-|-|-|-");
			break;
		case 30002:
			anyChooseList = e.positioningTwo("01,02,03,04,05,06,07,08,09,10,11|01,02,03,04,05,06,07,08,09,10,11|-|-|-");
			break;
		case 30004:
		case 30003:
			anyChooseList = e.groupOfSelectedOrdinaryTwo("01,02,03,04,05,06,07,08,09,10,11");
			break;
		case 30005:
		case 30006:
			few = 2;
			break;
		case 30007:
		case 30008:
		case 30009:
		case 30010:
		case 30011:
		case 30012:
			few = 3;
			break;
		case 30013:
		case 30014:
			few = 4;
			break;
		case 30015:
		case 30016:
		case 30017:
			few = 5;
			break;
		case 30018:
		case 30019:
			few = 6;
			break;
		case 30020:
		case 30021:
			few = 7;
			break;
		case 30022:
		case 30023:
			few = 8;
			break;
		}
		if(anyChooseList == null ){
			anyChooseList = e.anyChooseX(few,"01,02,03,04,05,06,07,08,09,10,11");
		}
		
		return anyChooseList;
	
	}
	
	
	public static void main(String[] args) {
		ElevenSelectedFiveNumCheck numCheck = new ElevenSelectedFiveNumCheck();
		System.out.println(numCheck.anyChooseNum(""));
	}
}
