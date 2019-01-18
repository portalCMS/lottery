package com.lottery.bean.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *彩种玩法中间表
 * @author CW-HP9
 *
 */
@Entity
@Table(name="t_lottery_play_model")
public class LotteryPlayModel extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2004421406275876852L;

	//彩种代码
	@Column(name="lottery_code")
	private String lotteryCode;
	//玩法代码
	@Column(name="model_code")
	private String modelCode;
	//状态
	@Column(name="status")
	private int status;
	//彩种组
	@Column(name="lottery_group")
	private String lotteryGroup;
	//限额
	@Column(name="limit_amount")
	private BigDecimal limitAmount;
	
	@Transient
	private PlayModel playModel;
	@Transient
	public List<LotteryPlaySelect>  selectList;
	@Transient
	private LotteryType lottery;
	@Transient
	private LotteryPlayBonus bonus;
	@Transient
	private List<PlayAwardLevel> levelList;
	
	public LotteryPlayBonus getBonus() {
		return bonus;
	}
	public void setBonus(LotteryPlayBonus bonus) {
		this.bonus = bonus;
	}
	public String getLotteryCode() {
		return lotteryCode;
	}
	public void setLotteryCode(String lotteryCode) {
		this.lotteryCode = lotteryCode;
	}
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getLotteryGroup() {
		return lotteryGroup;
	}
	public void setLotteryGroup(String lotteryGroup) {
		this.lotteryGroup = lotteryGroup;
	}
	public BigDecimal getLimitAmount() {
		return limitAmount;
	}
	public void setLimitAmount(BigDecimal limitAmount) {
		this.limitAmount = limitAmount;
	}
	public PlayModel getPlayModel() {
		return playModel;
	}
	public void setPlayModel(PlayModel playModel) {
		this.playModel = playModel;
	}
	
	public List<LotteryPlaySelect> getSelectList() {
		return selectList;
	}
	public void setSelectList(List<LotteryPlaySelect> selectList) {
		this.selectList = selectList;
	}
	
	public LotteryType getLottery() {
		return lottery;
	}
	public void setLottery(LotteryType lottery) {
		this.lottery = lottery;
	}
	public List<PlayAwardLevel> getLevelList() {
		return levelList;
	}
	public void setLevelList(List<PlayAwardLevel> levelList) {
		this.levelList = levelList;
	}
	@Override
	public String toString() {
		return "LotteryPlayModel [lotteryCode=" + lotteryCode + ", modelCode="
				+ modelCode + ", status=" + status + ", lotteryGroup="
				+ lotteryGroup + ", limitAmount=" + limitAmount
				+ ", playModel=" + playModel + ", getId()=" + getId()
				+ ", getCreateTime()=" + getCreateTime() + ", getCreateUser()="
				+ getCreateUser() + ", getUpdateTime()=" + getUpdateTime()
				+ ", getUpdateUser()=" + getUpdateUser() + ", getVersion()="
				+ getVersion() + "]";
	}
	
	
}
