package com.lottery.bean.entity.vo;

import java.math.BigDecimal;
import java.text.Bidi;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author CW-HP9
 *
 */
public class PlayAwardLevelVO extends GenericEntityVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//玩法代码
	private String playCode;
	//状态
	private Integer status;
	//状态
	private Integer awardLevel;
	//彩种
	private String lotteryCode;
	//理论中奖率
	private String winingRate;
	//理论中奖金额
	private BigDecimal winAmount;
	//奖金组id
	private String levelName;
	
	public String getPlayCode() {
		return playCode;
	}
	public void setPlayCode(String playCode) {
		this.playCode = playCode;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getAwardLevel() {
		return awardLevel;
	}
	public void setAwardLevel(Integer awardLevel) {
		this.awardLevel = awardLevel;
	}
	public String getLotteryCode() {
		return lotteryCode;
	}
	public void setLotteryCode(String lotteryCode) {
		this.lotteryCode = lotteryCode;
	}
	public String getWiningRate() {
		return winingRate;
	}
	public void setWiningRate(String winingRate) {
		this.winingRate = winingRate;
	}
	public BigDecimal getWinAmount() {
		return winAmount;
	}
	public void setWinAmount(BigDecimal winAmount) {
		this.winAmount = winAmount;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
	
}
