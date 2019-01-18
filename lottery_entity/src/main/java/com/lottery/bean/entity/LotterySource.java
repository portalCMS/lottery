package com.lottery.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 彩种号源中间表
 * @author CW-HP9
 *
 */
@Entity
@Table(name="t_lottery_source")
public class LotterySource extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2004421406275876852L;
	//彩种组代码
	@Column(name="lottery_group")
	private String lotteryGroup;
	//彩种代码
	@Column(name="lottery_code")
	private String lotteryCode;
	//状态
	@Column(name="status")
	private int status;
	//理论奖金
	@Column(name="source_link_id")
	private long sourceLinkId;
	
	@Transient
	private SourceLink link;
	
	public String getLotteryGroup() {
		return lotteryGroup;
	}
	public void setLotteryGroup(String lotteryGroup) {
		this.lotteryGroup = lotteryGroup;
	}
	public String getLotteryCode() {
		return lotteryCode;
	}
	public void setLotteryCode(String lotteryCode) {
		this.lotteryCode = lotteryCode;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getSourceLinkId() {
		return sourceLinkId;
	}
	public void setSourceLinkId(long sourceLinkId) {
		this.sourceLinkId = sourceLinkId;
	}
	public SourceLink getLink() {
		return link;
	}
	public void setLink(SourceLink link) {
		this.link = link;
	}
	
	@Override
	public String toString() {
		return "LotterySource [lotteryGroup=" + lotteryGroup + ", lotteryCode="
				+ lotteryCode + ", status=" + status + ", sourceLinkId="
				+ sourceLinkId + ", link=" + link + ", getId()=" + getId()
				+ ", getCreateTime()=" + getCreateTime() + ", getCreateUser()="
				+ getCreateUser() + ", getUpdateTime()=" + getUpdateTime()
				+ ", getUpdateUser()=" + getUpdateUser() + ", getVersion()="
				+ getVersion() + "]";
	}
	
	
}
