package com.lottery.bean.entity.vo;


import java.util.List;

import javax.persistence.Column;

import com.lottery.bean.entity.GenericEntity;

/**
 * 用户权限表
 * @author jeff
 *
 */
public class AdminPermissionsVO extends GenericEntityVO {

	private static final long serialVersionUID = 2649340127136104727L;

	/**
	 * 权限类型
	 */
	private long permissionsType;
	
	/**
	 * 权限URL或是开关
	 */
	private String permissions;
	
	/**
	 * 权限状态
	 */
	private int status;
	
	/**
	 * 权限名称
	 */
	private String permissionsName;
	
	/**
	 * 层级
	 */
	private String levels;
	
	/**
	 * 下级菜单
	 */
	private List<AdminPermissionsVO> listLevelsPermissions;
	
	
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

	public List<AdminPermissionsVO> getListLevelsPermissions() {
		return listLevelsPermissions;
	}

	public void setListLevelsPermissions(
			List<AdminPermissionsVO> listLevelsPermissions) {
		this.listLevelsPermissions = listLevelsPermissions;
	}

	public long getPermissionsType() {
		return permissionsType;
	}

	public void setPermissionsType(long permissionsType) {
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
}
