package com.lottery.bean.entity.vo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author CW-HP9
 *
 */
public class LotteryPlayBonusVO extends GenericEntityVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2004421406275876852L;

	//彩种代码
	private String lotteryCode;
	//玩法代码
	private String modelCode;
	//玩法名称
	private String modelName;
	//状态
	private int status;
	//理论奖金
	private String bonusAmount;
	//返点率
	private BigDecimal rebates;
	//返奖率
	private BigDecimal payoutRatio;
	//利润率
	private BigDecimal margin;
	//理论中奖率
	private String winningRate;
	//奖金组id
	private long bonusGroupId;
	//用户id
	private long userId;
	
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
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
	public long getBonusGroupId() {
		return bonusGroupId;
	}
	public void setBonusGroupId(long bonusGroupId) {
		this.bonusGroupId = bonusGroupId;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getWinningRate() {
		return winningRate;
	}
	public void setWinningRate(String winningRate) {
		this.winningRate = winningRate;
	}
	
	
}
