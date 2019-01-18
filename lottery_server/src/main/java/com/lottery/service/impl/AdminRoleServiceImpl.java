package com.lottery.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.AdminRole;
import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.AdminRoleVO;
import com.lottery.dao.IAdminRoleDao;
import com.lottery.dao.IAdminUserDao;
import com.lottery.service.IAdminRoleService;
import com.lottery.service.IAdminUserService;

@Service
public class AdminRoleServiceImpl implements IAdminRoleService{

	@Autowired
	IAdminRoleDao adminRoleDao;
	
	@Autowired
	IAdminUserDao adminUserDao;
	
	@Override
	public List<AdminRole> queryListAll()throws Exception {
		return adminRoleDao.queryAll();
	}
	
	@Override
	public AdminUser findAdminUserByID(Long id)throws Exception {
		// TODO Auto-generated method stub
		return adminUserDao.queryById(id);
	}
	
	@Override
	public Page<AdminRoleVO, AdminRole> queryAdminRolePage(AdminRoleVO vo)
			throws Exception {
		// TODO Auto-generated method stub
		return adminRoleDao.queryAdminRolePage(vo);
	}
	
	@Override
	public void deleteToAdminRole(long roleId)throws Exception {
		// TODO Auto-generated method stub
			 AdminRole adminRole=adminRoleDao.queryById(roleId);
			 adminRoleDao.delete(adminRole);
	}
	
	@Override
	public void insertToAdminRole(AdminUser user,AdminRole adminrole) {
		// TODO Auto-generated method stub
		adminrole.setCreateUser(user.getUserName());
		adminrole.setCreateTime(new Date());
		adminrole.setUpdateUser(user.getUserName());
		adminrole.setUpdateTime(new Date());
		adminRoleDao.insert(adminrole);
	}
	
	@Override
	public void updateToAdminRole(AdminUser user, AdminRole adminrole)
			throws Exception {
		// TODO Auto-generated method stub
		AdminRole adminRolePo=adminRoleDao.queryById(adminrole.getId());
		adminRolePo.setRoleName(adminrole.getRoleName());
		adminRolePo.setRoleEnName(adminrole.getRoleEnName());
		adminRolePo.setRoleLevel(adminrole.getRoleLevel());
		adminRolePo.setUpdateUser(user.getUserName());
		adminRolePo.setUpdateTime(new Date());
		adminRoleDao.update(adminRolePo);
	}
	
	public AdminRole findAdminRoleById(long id)throws Exception{
		return adminRoleDao.queryById(id);
	}
}
