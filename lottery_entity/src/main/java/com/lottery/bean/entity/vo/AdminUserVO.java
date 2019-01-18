package com.lottery.bean.entity.vo;



/**
 * 后台用户表
 * @author JEFF
 *
 */
public class AdminUserVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5704510826067220652L;

	/**
	 * 用户账号
	 */
	private String adminName;
	
	/**
	 * 密码
	 */
	private String adminPwd;
	
	/**
	 * 权限密码(可为空，当赋予了相应级别后必填)
	 */
	private String adminRolePwd;
	
	/**
	 * 用户昵称
	 */
	private String adminAlias;
	
	/**
	 * 状态
	 */
	private String adminStatus;
	
	/**
	 * 错误登录次数
	 */
	private String adminError;
	
	/**
	 * 在线状态
	 */
	private String adminOnlineStatus;
	
	/**
	 * 用户组
	 * 0：所有 1：财务 2：用户
	 */
	private int userGroup;
	
	/**
	 * 图片验证码
	 */
	private String picCode;
	
	private String ip;
	
	private String amPwd;

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminPwd() {
		return adminPwd;
	}

	public void setAdminPwd(String adminPwd) {
		this.adminPwd = adminPwd;
	}

	public String getAdminRolePwd() {
		return adminRolePwd;
	}

	public void setAdminRolePwd(String adminRolePwd) {
		this.adminRolePwd = adminRolePwd;
	}

	public String getAdminAlias() {
		return adminAlias;
	}

	public void setAdminAlias(String adminAlias) {
		this.adminAlias = adminAlias;
	}

	public String getAdminStatus() {
		return adminStatus;
	}

	public void setAdminStatus(String adminStatus) {
		this.adminStatus = adminStatus;
	}

	public String getAdminError() {
		return adminError;
	}

	public void setAdminError(String adminError) {
		this.adminError = adminError;
	}

	public String getAdminOnlineStatus() {
		return adminOnlineStatus;
	}

	public void setAdminOnlineStatus(String adminOnlineStatus) {
		this.adminOnlineStatus = adminOnlineStatus;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPicCode() {
		return picCode;
	}

	public void setPicCode(String picCode) {
		this.picCode = picCode;
	}

	public String getAmPwd() {
		return amPwd;
	}

	public void setAmPwd(String amPwd) {
		this.amPwd = amPwd;
	}

	public int getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(int userGroup) {
		this.userGroup = userGroup;
	}

}
