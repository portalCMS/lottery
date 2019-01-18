package com.lottery.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.AdminPermissions;
import com.lottery.bean.entity.AdminRolePermissions;
import com.lottery.bean.entity.AdminUser;

@Service
public interface IAdminRolePermissionsService {

	public AdminRolePermissions finaAdminRolePermissionsByID(int roleId) throws Exception;
	
	public String getAdminPermissionsList(String levels,String roleId)throws Exception;
	
	public void insertRolepermissions(String roleId,String permissionsIds,AdminUser user) throws Exception;
	
}
