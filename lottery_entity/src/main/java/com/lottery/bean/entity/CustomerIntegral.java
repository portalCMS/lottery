package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户积分表
 * @author CW-HP7
 *
 */
@Entity
@Table(name="t_customer_integral")
public class CustomerIntegral extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7845460301035279583L;

	@Column(name="integral")
	private Integer integral;
	
	@Column(name="integral_level")
	private Integer level;
	
	@Column(name="customer_id")
	private Long customerId;
	
	@Column(name="Continuou")
	private Integer continuou = 0;

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
		return "CustomerIntegral [integral=" + integral + ", level=" + level
				+ ", customerId=" + customerId + ", getId()=" + getId()
				+ ", getCreateTime()=" + getCreateTime() + ", getCreateUser()="
				+ getCreateUser() + ", getUpdateTime()=" + getUpdateTime()
				+ ", getUpdateUser()=" + getUpdateUser() + ", getVersion()="
				+ getVersion() + "]";
	}

	public Integer getContinuou() {
		return continuou;
	}

	public void setContinuou(Integer continuou) {
		this.continuou = continuou;
	}
}
