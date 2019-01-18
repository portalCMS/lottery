package com.lottery.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.AdminPermissions;
import com.lottery.bean.entity.AdminRolePermissions;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface IAdminRolePermissionsDao extends IGenericDao<AdminRolePermissions>{
	
	public List<AdminPermissions> getAdminPermissionsList(String levels);
	
	public boolean deleteAdminPermissions(long roleId);
	
	public int findAdminRolePermissionsByID(long roleId,long permissionsId); //查询角色权限
}
