package com.lottery.bean.entity.vo;


public class UserBonusGroupVO extends GenericEntityVO{

	private static final long serialVersionUID = -6712699435367829655L;

	private long bid;
	
	private long cuid;
	
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
	
}
