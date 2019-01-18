package com.lottery.bean.entity.vo;

import java.util.List;

import com.lottery.bean.entity.LotteryPlayBonus;

public class LotteryPlayBonusListVO extends GenericEntityVO {

	private static final long serialVersionUID = 2004421406275876852L;

	private List<LotteryPlayBonusVO> playBonusList;

	public List<LotteryPlayBonusVO> getPlayBonusList() {
		return playBonusList;
	}

	public void setPlayBonusList(List<LotteryPlayBonusVO> playBonusList) {
		this.playBonusList = playBonusList;
	}

	
	
	
}
