package com.lottery.bean.entity.vo;



public class CustomerIntegralLogVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7319080559765116786L;

	private Integer integral;
	
	private Integer level;
	
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
}
