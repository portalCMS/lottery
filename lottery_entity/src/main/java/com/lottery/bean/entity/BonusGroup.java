package com.lottery.bean.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 奖金组表
 * @author CW-HP9
 *
 */
@Entity
@Table(name="t_bonus_group")
public class BonusGroup extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2004421406275876852L;

	//返点率
	@Column(name="bonus_rebates")
	private BigDecimal rebates;
	//奖金组名称
	@Column(name="bonus_name")
	private String name;
	
	@Column(name="status")
	private int status;
	
	@Column(name="remark")
	private String remark;
	//返奖率
	@Column(name="payout_ratio")
	private BigDecimal payoutRatio;
	//利润率
	@Column(name="margin")
	private BigDecimal margin;
	
	@Transient
	private List<PlayModel> playList;
	@Transient
	private List<LotteryPlayBonus> bonusList;
	
	public BigDecimal getRebates() {
		return rebates;
	}

	public void setRebates(BigDecimal rebates) {
		this.rebates = rebates;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public List<PlayModel> getPlayList() {
		return playList;
	}

	public void setPlayList(List<PlayModel> playList) {
		this.playList = playList;
	}

	public List<LotteryPlayBonus> getBonusList() {
		return bonusList;
	}

	public void setBonusList(List<LotteryPlayBonus> bonusList) {
		this.bonusList = bonusList;
	}

	@Override
	public String toString() {
		return "BonusGroup [rebates=" + rebates + ", name=" + name
				+ ", status=" + status + ", remark=" + remark
				+ ", payoutRatio=" + payoutRatio + ", margin=" + margin
				+ ", playList=" + playList + ", bonusList=" + bonusList
				+ ", getId()=" + getId() + ", getCreateTime()="
				+ getCreateTime() + ", getCreateUser()=" + getCreateUser()
				+ ", getUpdateTime()=" + getUpdateTime() + ", getUpdateUser()="
				+ getUpdateUser() + ", getVersion()=" + getVersion()
				+ ", toString()=" + super.toString() + "]";
	}
	
}
