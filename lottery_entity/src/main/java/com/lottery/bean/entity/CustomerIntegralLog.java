package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 每日签到记录表
 * @author CW-HP7
 *
 */
@Entity
@Table(name="t_customer_integral_log")
public class CustomerIntegralLog extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8211482073931937878L;

	@Column(name="integral")
	private Integer integral;
	
	@Column(name="integral_level")
	private Integer level;
	
	@Column(name="customer_id")
	private Long customerId;

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "CustomerIntegralLog [integral=" + integral + ", level=" + level
				+ ", customerId=" + customerId + ", getId()=" + getId()
				+ ", getCreateTime()=" + getCreateTime() + ", getCreateUser()="
				+ getCreateUser() + ", getUpdateTime()=" + getUpdateTime()
				+ ", getUpdateUser()=" + getUpdateUser() + ", getVersion()="
				+ getVersion() + "]";
	}
}
