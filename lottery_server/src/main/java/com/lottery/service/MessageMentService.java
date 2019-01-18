package com.lottery.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.AdminPermissions;
import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.AdminPermissionsVO;

@Service
public interface MessageMentService {

	/**
	 * 加载权限列表
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Page<AdminPermissionsVO,AdminPermissions> queryAdminPermissionsPage(AdminPermissionsVO vo)throws Exception;
	
	/**
	 * 删除权限
	 * @param id
	 * @throws Exception
	 */
	public void deleteAdminPermissions(long id)throws Exception;
	
	/**
	 * 修改权限
	 * @param id
	 * @throws Exception
	 */
	public void updateAdminPermissions(AdminPermissions adminPermissions,AdminUser user)throws Exception;
	
	/**
	 * 新增
	 * @param adminPermissions
	 */
	public void insertAdminPermissions(AdminPermissions adminPermissions,AdminUser user)throws Exception;
	
	/**
	 * 查询权限对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public AdminPermissions findAdminPermissionsById(long id)throws Exception;
	
	
	
	public List<AdminPermissions> queryAdminPermissionsByLevels()throws Exception;
}
