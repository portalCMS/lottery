package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 用户权限关系表
 * @author jeff
 *
 */
@Entity
@Table(name="t_admin_user_role")
public class AdminUserRole extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9125558885190228262L;

	/**
	 * 后台用户ID
	 */
	@Column(name="admin_user_id")
	private long adminUserId;
	
	/**
	 * 权限ID
	 */
	@Column(name="role_id")
	private long roleId;
	
	/**
	 * 状态
	 */
	@Column(name="status")
	private int status;

	public long getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(long adminUserId) {
		this.adminUserId = adminUserId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AdminUserRole [adminUserId=" + adminUserId + ", roleId="
				+ roleId + ", status=" + status + ", getId()=" + getId()
				+ ", getCreateTime()=" + getCreateTime() + ", getCreateUser()="
				+ getCreateUser() + ", getUpdateTime()=" + getUpdateTime()
				+ ", getUpdateUser()=" + getUpdateUser() + ", getVersion()="
				+ getVersion() + "]";
	}
	
}
