package com.lottery.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.AdminRole;
import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.AdminRoleVO;

@Service
public interface IAdminRoleService {
	
	public List<AdminRole> queryListAll() throws Exception;
	
	public AdminUser findAdminUserByID(Long id)throws Exception;
	
	public Page<AdminRoleVO, AdminRole> queryAdminRolePage(AdminRoleVO vo)throws Exception;
	
	public void insertToAdminRole(AdminUser user,AdminRole adminrole)throws Exception;
	
	public void deleteToAdminRole(long roleId)throws Exception;
	
	public void updateToAdminRole(AdminUser user,AdminRole adminrole)throws Exception;
	
	public AdminRole findAdminRoleById(long id)throws Exception;
}
