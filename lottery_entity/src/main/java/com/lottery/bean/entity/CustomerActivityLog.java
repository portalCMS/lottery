package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_customer_activity_log")
public class CustomerActivityLog extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6010304724817188974L;

	@Column(name="customer_id")
	private long customerId;
	
	@Column(name="activity_id")
	private long activityId;
	
	@Column(name="order_id")
	private long orderId;
	
	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

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
