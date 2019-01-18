package com.lottery.bean.entity.vo;

import javax.persistence.Column;

import com.lottery.bean.entity.GenericEntity;

public class AdminRolePermissionsVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1128987424448164074L;

	/**
	 * 角色ID
	 */
	private long roleId;
	
	/**
	 * 权限ID
	 */
	private long permissionsId;
	
	/**
	 * 状态
	 */
	private int status;

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public long getPermissionsId() {
		return permissionsId;
	}

	public void setPermissionsId(long permissionsId) {
		this.permissionsId = permissionsId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
