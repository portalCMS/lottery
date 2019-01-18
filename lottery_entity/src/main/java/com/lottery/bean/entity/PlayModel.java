package com.lottery.bean.entity;

import java.math.BigDecimal;
import java.text.Bidi;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.lottery.bean.entity.vo.PlayAwardLevelVO;

/**
 * 玩法表
 * @author CW-HP9
 *
 */
@Entity
@Table(name="t_play_model")
public class PlayModel extends GenericEntity {

	private static final long serialVersionUID = 2004421406275876852L;
	//玩法代码
	@Column(name="model_code")
	private String modelCode;
	//状态
	@Column(name="status")
	private int status;
	//玩法名称
	@Column(name="model_name")
	private String modelName;
	//玩法描述
	@Column(name="model_desc")
	private String modelDesc;
	//彩种组
	@Column(name="lottery_group")
	private String lotteryGroup;
	//理论中奖率
	@Column(name="wining_rate")
	private String winingRate;
	//玩法对应的可投注号码总数
	@Column(name="total_bets")
	private int totalBets;
	
	@Column(name="group_name")
	private String groupName;
	//理论中奖金额
	@Column(name="win_amount")
	private BigDecimal winAmount;
	
	@Column(name="bal_count")
	private Integer balCount;
	//排序依据
	@Column(name="sort_index")
	private Integer sortIndex;
	//手机端玩法控制
	@Column(name="mobile_flag")
	private Integer mobileFlag;
	
	public Integer getMobileFlag() {
		return mobileFlag;
	}
	public void setMobileFlag(Integer mobileFlag) {
		this.mobileFlag = mobileFlag;
	}
	@Transient
	private boolean selectedModel=false;
	
	@Transient
	private BigDecimal winingAmount;
	
	@Transient
	private int limitBets;
	
	@Transient
	private BigDecimal limitAmount;
	
	@Transient
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
	public BigDecimal getWiningAmount() {
		return winingAmount;
	}
	public void setWiningAmount(BigDecimal winingAmount) {
		this.winingAmount = winingAmount;
	}
	public int getTotalBets() {
		return totalBets;
	}
	public void setTotalBets(int totalBets) {
		this.totalBets = totalBets;
	}
	public int getLimitBets() {
		return limitBets;
	}
	public void setLimitBets(int limitBets) {
		this.limitBets = limitBets;
	}
	public BigDecimal getLimitAmount() {
		return limitAmount;
	}
	public void setLimitAmount(BigDecimal limitAmount) {
		this.limitAmount = limitAmount;
	}
	public boolean isSelectedModel() {
		return selectedModel;
	}
	public void setSelectedModel(boolean selectedModel) {
		this.selectedModel = selectedModel;
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
	
	public Integer getBalCount() {
		return balCount;
	}
	public void setBalCount(Integer balCount) {
		this.balCount = balCount;
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
	
	public Integer getSortIndex() {
		return sortIndex;
	}
	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}
	@Override
	public String toString() {
		return "PlayModel [modelCode=" + modelCode + ", status=" + status
				+ ", modelName=" + modelName + ", modelDesc=" + modelDesc
				+ ", lotteryGroup=" + lotteryGroup + ", winingRate="
				+ winingRate + ", totalBets=" + totalBets + ", groupName="
				+ groupName + ", winAmount=" + winAmount + ", balCount="
				+ balCount + ", sortIndex=" + sortIndex + ", selectedModel="
				+ selectedModel + ", winingAmount=" + winingAmount
				+ ", limitBets=" + limitBets + ", limitAmount=" + limitAmount
				+ ", levelList=" + levelList + "]";
	}
	
}
