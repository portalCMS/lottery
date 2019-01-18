package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_admin_role_permissions")
public class AdminRolePermissions extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1128987424448164074L;

	/**
	 * 角色ID
	 */
	@Column(name="role_id")
	private long roleId;
	
	/**
	 * 权限ID
	 */
	@Column(name="permissions_id")
	private long permissionsId;
	
	/**
	 * 状态
	 */
	@Column(name="status")
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

	@Override
	public String toString() {
		return "AdminRolePermissions [roleId=" + roleId + ", permissionsId="
				+ permissionsId + ", status=" + status + ", getId()=" + getId()
				+ ", getCreateTime()=" + getCreateTime() + ", getCreateUser()="
				+ getCreateUser() + ", getUpdateTime()=" + getUpdateTime()
				+ ", getUpdateUser()=" + getUpdateUser() + ", getVersion()="
				+ getVersion() + "]";
	}
	
	
}
