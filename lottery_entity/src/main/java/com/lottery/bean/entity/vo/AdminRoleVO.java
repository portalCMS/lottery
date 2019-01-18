package com.lottery.bean.entity.vo;

import java.util.List;

import com.lottery.bean.entity.GenericEntity;


/**
 * 用户角色
 * @author jeff
 *
 */
public class AdminRoleVO extends GenericEntityVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 917670130866014880L;

	/**
	 * 角色名称
	 */
	private String roleName;
	
	/**
	 * 角色英文名称
	 */
	private String roleEnName;
	
	/**
	 * 角色级别
	 */
	private int roleLevel;
	
	/**
	 * 角色密码授权开关
	 */
	private int rolePwdSwitch;
	
	/**
	 * 角色状态
	 */
	private int status;
	
	private List<AdminPermissionsVO> permissionsList;

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

	public List<AdminPermissionsVO> getPermissionsList() {
		return permissionsList;
	}

	public void setPermissionsList(List<AdminPermissionsVO> permissionsList) {
		this.permissionsList = permissionsList;
	}
	
}
