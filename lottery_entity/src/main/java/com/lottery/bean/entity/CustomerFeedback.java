package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="t_customer_feedback")
public class CustomerFeedback extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7277190564678953338L;

	/**
	 * 类型名称
	 */
	@Column(name="typeName")
	private String typeName;
	
	/**
	 * 页面名称
	 */
	@Column(name="pageName")
	private String pageName;
	
	/**
	 * 描述
	 */
	@Column(name="dsce")
	private String dsce;
	
	/**
	 * 处理状态
	 */
	@Column(name="status")
	private int status;
	
	/**
	 * 提交人
	 */
	@Column(name="customer_id")
	private long customerId;

	@Transient
	private String customerName;
	
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getDsce() {
		return dsce;
	}

	public void setDsce(String dsce) {
		this.dsce = dsce;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
}
