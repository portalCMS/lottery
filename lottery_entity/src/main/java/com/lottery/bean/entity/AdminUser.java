package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 后台用户表
 * @author JEFF
 *
 */
@Entity
@Table(name="t_admin_user")
public class AdminUser extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5704510826067220652L;

	/**
	 * 用户账号
	 */
	@Column(name="user_name")
	private String userName;
	
	/**
	 * 密码
	 */
	@Column(name="user_pwd")
	private String userPwd;
	
	/**
	 * 权限密码(可为空，当赋予了相应级别后必填)
	 */
	@Column(name="user_role_pwd")
	private String userRolePwd;
	
	/**
	 * 用户昵称
	 */
	@Column(name="user_alias")
	private String userAlias;
	
	/**
	 * 状态
	 */
	@Column(name="user_status")
	private int userStatus;
	
	/**
	 * 错误登录次数
	 */
	@Column(name="user_error")
	private int userError;
	
	/**
	 * 在线状态
	 */
	@Column(name="user_online_status")
	private String userOnlineStatus;
	
	/**
	 * 用户组
	 * 0：所有 1：财务 2：用户
	 */
	@Column(name="user_group")
	private int userGroup;

	@Transient
	private String ip;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserRolePwd() {
		return userRolePwd;
	}

	public void setUserRolePwd(String userRolePwd) {
		this.userRolePwd = userRolePwd;
	}

	public String getUserAlias() {
		return userAlias;
	}

	public void setUserAlias(String userAlias) {
		this.userAlias = userAlias;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public int getUserError() {
		return userError;
	}

	public void setUserError(int userError) {
		this.userError = userError;
	}

	public String getUserOnlineStatus() {
		return userOnlineStatus;
	}

	public void setUserOnlineStatus(String userOnlineStatus) {
		this.userOnlineStatus = userOnlineStatus;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(int userGroup) {
		this.userGroup = userGroup;
	}

	@Override
	public String toString() {
		return "AdminUser [userName=" + userName + ", userPwd=" + userPwd
				+ ", userRolePwd=" + userRolePwd + ", userAlias=" + userAlias
				+ ", userStatus=" + userStatus + ", userError=" + userError
				+ ", userOnlineStatus=" + userOnlineStatus + ", userGroup="
				+ userGroup + ", ip=" + ip + ", getId()=" + getId()
				+ ", getCreateTime()=" + getCreateTime() + ", getCreateUser()="
				+ getCreateUser() + ", getUpdateTime()=" + getUpdateTime()
				+ ", getUpdateUser()=" + getUpdateUser() + ", getVersion()="
				+ getVersion() + "]";
	}
	
}
