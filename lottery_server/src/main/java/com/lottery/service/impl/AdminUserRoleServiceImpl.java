package com.lottery.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.AdminUserRole;
import com.lottery.dao.IAdminUserRoleDao;
import com.lottery.dao.impl.AdminUserRoleDaoImpl;
import com.lottery.service.IAdminUserRoleService;

@Service
public class AdminUserRoleServiceImpl implements IAdminUserRoleService{

	@Autowired
	private IAdminUserRoleDao adminUserRoleDao;
	
	@Override
	public List<AdminUserRole> finaAdminUserRoleByRoleID(long roleId) {
		// TODO Auto-generated method stub
		return adminUserRoleDao.finaAdminUserRoleByRoleID(roleId);
	}
	
	public void saveUserRole(String roleIds,long userId,AdminUser user) throws Exception{
		String[] strs=roleIds.split(",");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("adminUserId", userId);
		List<AdminUserRole> listuserRole=adminUserRoleDao.findAdminUserRoleByUserID(param);
		for(int i=0;i<listuserRole.size();i++){
			adminUserRoleDao.delete(listuserRole.get(i));
		}
		for(int j=0;j<strs.length;j++){
			AdminUserRole t =new AdminUserRole();
			t.setRoleId(Long.valueOf(strs[j]));
			t.setAdminUserId(Long.valueOf(userId));
			t.setStatus(10002);
			t.setVersion(0);
			t.setCreateUser(user.getUserName());
			t.setCreateTime(new Date());
			t.setUpdateUser(user.getUserName());
			t.setUpdateTime(new Date());
			adminUserRoleDao.insert(t);
		}
	}
	
	@Override
	public List<AdminUserRole> findAdminUserRoleByUserID(Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		return adminUserRoleDao.findAdminUserRoleByUserID(param);
	}
}
