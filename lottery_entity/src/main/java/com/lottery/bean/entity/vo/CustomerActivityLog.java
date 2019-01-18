package com.lottery.bean.entity.vo;


public class CustomerActivityLog extends GenericEntityVO{


	private static final long serialVersionUID = 1L;

	private long customerId;
	
	private long activityId;

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getActivityId() {
		return activityId;
	}

	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}
}
