package com.lottery.bean.entity;

import java.math.BigDecimal;
import java.text.Bidi;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 彩种对应玩法的中奖等级划分表（乐透型）
 * @author CW-HP9
 *
 */
@Entity
@Table(name="t_play_award_level")
public class PlayAwardLevel extends GenericEntity {

	private static final long serialVersionUID = 2004421406275876852L;
	//玩法代码
	@Column(name="play_code")
	private String playCode;
	//状态
	@Column(name="status")
	private Integer status;
	//状态
	@Column(name="award_level")
	private Integer awardLevel;
	//彩种
	@Column(name="lottery_code")
	private String lotteryCode;
	//理论中奖率
	@Column(name="wining_rate")
	private String winingRate;
	//理论中奖金额
	@Column(name="win_amount")
	private BigDecimal winAmount;
	//中奖等级名称
	@Column(name="level_name")
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
