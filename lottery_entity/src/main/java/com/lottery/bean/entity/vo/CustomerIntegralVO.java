package com.lottery.bean.entity.vo;


public class CustomerIntegralVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4153257243640969422L;

	private Integer integral;
	
	private Integer level;

	private Long customerId;
	
	private Integer todyIntegral;
	
	private boolean isRegistration;
	
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

	public Integer getTodyIntegral() {
		return todyIntegral;
	}

	public void setTodyIntegral(Integer todyIntegral) {
		this.todyIntegral = todyIntegral;
	}

	public boolean isRegistration() {
		return isRegistration;
	}

	public void setRegistration(boolean isRegistration) {
		this.isRegistration = isRegistration;
	}

	public Integer getContinuou() {
		return continuou;
	}

	public void setContinuou(Integer continuou) {
		this.continuou = continuou;
	}
}
