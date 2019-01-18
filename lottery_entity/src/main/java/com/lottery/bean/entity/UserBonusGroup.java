package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_user_bonus_group")
public class UserBonusGroup extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6712699435367829655L;

	@Column(name="bonus_id")
	private long bid;
	
	@Column(name="customer_id")
	private long cuid;
	
	@Column(name="status")
	private int status;

	public long getBid() {
		return bid;
	}

	public void setBid(long bid) {
		this.bid = bid;
	}

	public long getCuid() {
		return cuid;
	}

	public void setCuid(long cuid) {
		this.cuid = cuid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UserBonusGroup [bid=" + bid + ", cuid=" + cuid + ", status="
				+ status + ", getId()=" + getId() + ", getCreateTime()="
				+ getCreateTime() + ", getCreateUser()=" + getCreateUser()
				+ ", getUpdateTime()=" + getUpdateTime() + ", getUpdateUser()="
				+ getUpdateUser() + ", getVersion()=" + getVersion() + "]";
	}
	
}
