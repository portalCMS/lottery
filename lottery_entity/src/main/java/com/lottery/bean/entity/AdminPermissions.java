package com.lottery.bean.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户权限表
 * @author jeff
 *
 */
@Entity
@Table(name="t_admin_permissions")
public class AdminPermissions extends GenericEntity {

	private static final long serialVersionUID = 2649340127136104727L;

	/**
	 * 权限类型
	 */
	@Column(name="permissions_type")
	private int permissionsType;
	
	/**
	 * 权限URL或是开关
	 */
	@Column(name="permissions")
	private String permissions;
	
	/**
	 * 权限状态
	 */
	@Column(name="status")
	private int status;
	
	/**
	 * 层级
	 */
	@Column(name="levels")
	private String levels;
	
	/**
	 * 排列序号
	 */
	@Column(name="sequence")
	private int sequence;
	
	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	/**
	 * 权限名称
	 */
	@Column(name="permissions_name")
	private String permissionsName;
	
	
	public String getPermissionsName() {
		return permissionsName;
	}

	public void setPermissionsName(String permissionsName) {
		this.permissionsName = permissionsName;
	}

	public String getLevels() {
		return levels;
	}

	public void setLevels(String levels) {
		this.levels = levels;
	}

	public int getPermissionsType() {
		return permissionsType;
	}

	public void setPermissionsType(int permissionsType) {
		this.permissionsType = permissionsType;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AdminPermissions [permissionsType=" + permissionsType
				+ ", permissions=" + permissions + ", status=" + status
				+ ", getId()=" + getId() + ", getCreateTime()="
				+ getCreateTime() + ", getCreateUser()=" + getCreateUser()
				+ ", getUpdateTime()=" + getUpdateTime() + ", getUpdateUser()="
				+ getUpdateUser() + ", getVersion()=" + getVersion()
				 + ", getLevels()=" + getLevels()  + ", getPermissionsName()=" + getPermissionsName()
				 + ", getPermissionsName=" + getPermissionsName()
				+ "]";
	}
}
