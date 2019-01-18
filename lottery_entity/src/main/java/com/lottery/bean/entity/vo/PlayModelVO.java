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
public class PlayModelVO extends GenericEntityVO {

	private static final long serialVersionUID = 2004421406275876852L;
	//玩法代码
	private String modelCode;
	//状态
	private int status;
	//玩法名称
	private String modelName;
	//玩法描述
	private String modelDesc;
	//彩种组
	private String lotteryGroup;
	//理论中奖率
	private String winingRate;
	//玩法对应的可投注号码总数
	private int totalBets;
	
	private String groupName;
	//理论中奖金额
	private BigDecimal winAmount;
	
	private List<PlayAwardLevelVO> levelList;
	
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
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getModelDesc() {
		return modelDesc;
	}
	public void setModelDesc(String modelDesc) {
		this.modelDesc = modelDesc;
	}
	public String getLotteryGroup() {
		return lotteryGroup;
	}
	public void setLotteryGroup(String lotteryGroup) {
		this.lotteryGroup = lotteryGroup;
	}
	public int getTotalBets() {
		return totalBets;
	}
	public void setTotalBets(int totalBets) {
		this.totalBets = totalBets;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public BigDecimal getWinAmount() {
		return winAmount;
	}
	public void setWinAmount(BigDecimal winAmount) {
		this.winAmount = winAmount;
	}
	
	public String getWiningRate() {
		return winingRate;
	}
	public void setWiningRate(String winingRate) {
		this.winingRate = winingRate;
	}
	
	
	public List<PlayAwardLevelVO> getLevelList() {
		return levelList;
	}
	public void setLevelList(List<PlayAwardLevelVO> levelList) {
		this.levelList = levelList;
	}
	@Override
	public String toString() {
		return "PlayModel [modelCode=" + modelCode + ", status=" + status
				+ ", modelName=" + modelName + ", modelDesc=" + modelDesc
				+ ", lotteryGroup=" + lotteryGroup + ", winingRate="
				+ winingRate + ", totalBets=" + totalBets + ", winingAmount="
				+ ", getId()=" + getId() + ", getCreateTime()="
				+ getCreateTime() + ", getCreateUser()=" + getCreateUser()
				+ ", getUpdateTime()=" + getUpdateTime() + ", getUpdateUser()="
				+ getUpdateUser() + ", getVersion()=" + getVersion() + "]";
	}
	
	
}
