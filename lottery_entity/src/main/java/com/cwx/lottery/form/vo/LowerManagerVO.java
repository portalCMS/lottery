package com.cwx.lottery.form.vo;

import java.io.Serializable;

/**
 * 下级管理VO
 * @author CW-HP7
 *
 */
public class LowerManagerVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8444512446180527097L;

	/**
	 * 目标用户
	 */
	private String toUserName;
	
	/**
	 * 充值金额
	 */
	private String toMoney;
	
	/**
	 * 资金密码
	 */
	private String moneryPwd;
	
	/**
	 * 返点调整
	 */
	private String toQuota;
	
	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getToMoney() {
		return toMoney;
	}

	public void setToMoney(String toMoney) {
		this.toMoney = toMoney;
	}

	public String getMoneryPwd() {
		return moneryPwd;
	}

	public void setMoneryPwd(String moneryPwd) {
		this.moneryPwd = moneryPwd;
	}

	public String getToQuota() {
		return toQuota;
	}

	public void setToQuota(String toQuota) {
		this.toQuota = toQuota;
	}

}
