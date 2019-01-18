package com.lottery.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户配额表
 * @author jeff
 */
@Entity
@Table(name="t_customer_quota")
public class CustomerQuota extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1866434264784183097L;

	/**
	 * 用户ID
	 */
	@Column(name="customer_id")
	private long customerId;
	
	/**
	 * 配额类型
	 */
	@Column(name="proportion")
	private BigDecimal proportion;
	
	/**
	 * 配额数
	 */
	@Column(name="quota_count")
	private int quota_count;
	
	/**
	 * 状态
	 */
	@Column(name="status")
	private int status;

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getProportion() {
		return proportion;
	}

	public void setProportion(BigDecimal proportion) {
		this.proportion = proportion;
	}

	public int getQuota_count() {
		return quota_count;
	}

	public void setQuota_count(int quota_count) {
		this.quota_count = quota_count;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CustomerQuota [customerId=" + customerId + ", proportion="
				+ proportion + ", quota_count=" + quota_count + ", status="
				+ status + ", getId()=" + getId() + ", getCreateTime()="
				+ getCreateTime() + ", getCreateUser()=" + getCreateUser()
				+ ", getUpdateTime()=" + getUpdateTime() + ", getUpdateUser()="
				+ getUpdateUser() + ", getVersion()=" + getVersion() + "]";
	}
}
