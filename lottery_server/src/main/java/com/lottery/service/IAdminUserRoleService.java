package com.lottery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.AdminUserRole;

@Service
public interface IAdminUserRoleService {
	
	public List<AdminUserRole> finaAdminUserRoleByRoleID(long roleId)throws Exception;
	
	public void saveUserRole(String roleIds,long userId,AdminUser user)throws Exception;
	
	public List<AdminUserRole> findAdminUserRoleByUserID(Map<String, ?> param)throws Exception;
}
