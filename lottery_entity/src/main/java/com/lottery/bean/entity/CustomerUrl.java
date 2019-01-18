package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户可访问URL
 * @author JEFF
 *
 */
@Entity
@Table(name="t_customer_url")
public class CustomerUrl extends GenericEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8232857598273808833L;

	/**
	 * 用户ID
	 */
	@Column(name="customer_id")
	private long customerId;
	
	/**
	 * url
	 */
	@Column(name="url_id")
	private long urlid;
	
	/**
	 * 状态
	 * 0:不可以用   1:可以用
	 */
	@Column(name="url_status")
	private int urlStatus;
	
	/**
	 * 备注
	 */
	@Column(name="remark")
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getUrlid() {
		return urlid;
	}

	public void setUrlid(long urlid) {
		this.urlid = urlid;
	}

	public int getUrlStatus() {
		return urlStatus;
	}

	public void setUrlStatus(int urlStatus) {
		this.urlStatus = urlStatus;
	}

	@Override
	public String toString() {
		return "CustomerUrl [customerId=" + customerId + ", urlid=" + urlid
				+ ", urlStatus=" + urlStatus + ", remark=" + remark
				+ ", getId()=" + getId() + ", getCreateTime()="
				+ getCreateTime() + ", getCreateUser()=" + getCreateUser()
				+ ", getUpdateTime()=" + getUpdateTime() + ", getUpdateUser()="
				+ getUpdateUser() + ", getVersion()=" + getVersion() + "]";
	}
	
}
