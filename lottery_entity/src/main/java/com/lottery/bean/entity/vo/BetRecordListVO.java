package com.lottery.bean.entity.vo;

import java.math.BigDecimal;
import java.util.List;

public class BetRecordListVO {

	private BigDecimal orderAmount;
	
	private List<BetRecordVO> volist;
	
	private String awardStop;
	
	private int trackNo;
	
	private String isTrack;
	
	private String payForType;
	
	private String issueNos;
	
	public List<BetRecordVO> getVolist() {
		return volist;
	}

	public void setVolist(List<BetRecordVO> volist) {
		this.volist = volist;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getAwardStop() {
		return awardStop;
	}

	public void setAwardStop(String awardStop) {
		this.awardStop = awardStop;
	}

	public int getTrackNo() {
		return trackNo;
	}

	public void setTrackNo(int trackNo) {
		this.trackNo = trackNo;
	}

	public String getPayForType() {
		return payForType;
	}

	public void setPayForType(String payForType) {
		this.payForType = payForType;
	}

	public String getIsTrack() {
		return isTrack;
	}

	public void setIsTrack(String isTrack) {
		this.isTrack = isTrack;
	}

	public String getIssueNos() {
		return issueNos;
	}

	public void setIssueNos(String issueNos) {
		this.issueNos = issueNos;
	}

}
