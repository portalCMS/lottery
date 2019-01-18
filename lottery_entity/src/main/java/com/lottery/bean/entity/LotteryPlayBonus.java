package com.lottery.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 玩法奖金组表
 * @author CW-HP9
 *
 */
@Entity
@Table(name="t_lottery_play_bonus")
public class LotteryPlayBonus extends GenericEntity {

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
	//理论奖金
	@Column(name="bonus_amount")
	private String bonusAmount;
	//返点率
	@Column(name="bonus_rebates")
	private BigDecimal rebates;
	//返奖率
	@Column(name="payout_ratio")
	private BigDecimal payoutRatio;
	//利润率
	@Column(name="margin")
	private BigDecimal margin;
	//理论中奖率
	@Column(name="winning_rate")
	private String winningRate;
	//奖金组id
	@Column(name="bonus_group_id")
	private long bonusGroupId;
	
	@Transient
	private PlayModel model;
	
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
	public String getBonusAmount() {
		return bonusAmount;
	}
	public void setBonusAmount(String bonusAmount) {
		this.bonusAmount = bonusAmount;
	}
	public BigDecimal getRebates() {
		return rebates;
	}
	public void setRebates(BigDecimal rebates) {
		this.rebates = rebates;
	}
	public BigDecimal getPayoutRatio() {
		return payoutRatio;
	}
	public void setPayoutRatio(BigDecimal payoutRatio) {
		this.payoutRatio = payoutRatio;
	}
	public BigDecimal getMargin() {
		return margin;
	}
	public void setMargin(BigDecimal margin) {
		this.margin = margin;
	}
	public String getWinningRate() {
		return winningRate;
	}
	public void setWinningRate(String winningRate) {
		this.winningRate = winningRate;
	}
	public long getBonusGroupId() {
		return bonusGroupId;
	}
	public void setBonusGroupId(long bonusGroupId) {
		this.bonusGroupId = bonusGroupId;
	}
	public PlayModel getModel() {
		return model;
	}
	public void setModel(PlayModel model) {
		this.model = model;
	}
	@Override
	public String toString() {
		return "LotteryPlayBonus [lotteryCode=" + lotteryCode + ", modelCode="
				+ modelCode + ", status=" + status + ", bonusAmount="
				+ bonusAmount + ", rebates=" + rebates + ", payoutRatio="
				+ payoutRatio + ", margin=" + margin + ", winningRate="
				+ winningRate + ", bonusGroupId=" + bonusGroupId + ", model="
				+ model + ", getId()=" + getId() + ", getCreateTime()="
				+ getCreateTime() + ", getCreateUser()=" + getCreateUser()
				+ ", getUpdateTime()=" + getUpdateTime() + ", getUpdateUser()="
				+ getUpdateUser() + ", getVersion()=" + getVersion() + "]";
	}
	
	
}
