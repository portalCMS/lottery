package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 用户角色
 * @author jeff
 *
 */
@Entity
@Table(name="t_admin_role")
public class AdminRole extends GenericEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 917670130866014880L;

	/**
	 * 角色名称
	 */
	@Column(name="role_name")
	private String roleName;
	
	/**
	 * 角色英文名称
	 */
	@Column(name="role_en_name")
	private String roleEnName;
	
	/**
	 * 角色级别
	 */
	@Column(name="role_level")
	private int roleLevel;
	
	/**
	 * 角色密码授权开关
	 */
	@Column(name="role_pwd_switch")
	private int rolePwdSwitch;
	
	/**
	 * 角色状态
	 */
	@Column(name="status")
	private int status;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleEnName() {
		return roleEnName;
	}

	public void setRoleEnName(String roleEnName) {
		this.roleEnName = roleEnName;
	}

	public int getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(int roleLevel) {
		this.roleLevel = roleLevel;
	}

	public int getRolePwdSwitch() {
		return rolePwdSwitch;
	}

	public void setRolePwdSwitch(int rolePwdSwitch) {
		this.rolePwdSwitch = rolePwdSwitch;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AdminRole [roleName=" + roleName + ", roleEnName=" + roleEnName
				+ ", roleLevel=" + roleLevel + ", rolePwdSwitch="
				+ rolePwdSwitch + ", status=" + status + ", getId()=" + getId()
				+ ", getCreateTime()=" + getCreateTime() + ", getCreateUser()="
				+ getCreateUser() + ", getUpdateTime()=" + getUpdateTime()
				+ ", getUpdateUser()=" + getUpdateUser() + ", getVersion()="
				+ getVersion() + "]";
	}
	
}
