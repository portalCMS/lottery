package com.lottery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.AdminPermissions;
import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.vo.AdminPermissionsVO;
import com.lottery.bean.entity.vo.AdminUserVO;

@Service
public interface IAdminUserService {

	/**
	 * 根据用户ID获取用户对象
	 * @param param
	 * @return
	 */
	public AdminUser findAdminUserById(final Map<String,?> param) throws Exception;
	
	
	/**
	 * 登录验证用户
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public AdminUser checkAdminUser(final Map<String,?> param) throws Exception;
	
	/**
	 * 验证管理员密码是否正确
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public boolean checkRolePassword(String userName,String userRolePwd)throws Exception;
	
	/**
	 * 新建保存后台管理员
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String saveAdminUser(final Map<String,?> param) throws Exception;
	
	/**
	 * 获取所有后台管理员
	 * @return
	 * @throws Exception
	 */
	public List<AdminUser> queryAllAdminUser()throws Exception;
	
	/**
	 * 更改用户
	 * @param adminuser
	 * @return
	 * @throws Exception
	 */
	public void updateAdminUser(AdminUserVO adminuser) throws Exception;
	
	/**
	 * 加载登录用户权限
	 * @return
	 */
	public List<AdminPermissionsVO> finaUserOrRoleAdminPermissions(AdminUser adminuser,String levels) throws Exception;
	
	/**
	 * 权限管理粒度划分到点击按钮
	 */
	public List<AdminPermissions> getRoleUserPermissions(AdminUser adminuser,String permissions)throws Exception;
	
	/**
	 * 查找URL对应的权限
	 */
	public List<AdminPermissions> getToPermissions(String permissions)throws Exception;
}
