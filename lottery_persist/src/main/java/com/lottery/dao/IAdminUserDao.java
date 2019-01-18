package com.lottery.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.AdminRole;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.AdminPermissions;
import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.vo.AdminRoleVO;
import com.lottery.bean.entity.vo.AdminUserVO;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface IAdminUserDao extends IGenericDao<AdminUser>{

	/**
	 * 登录验证用户
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public AdminUser checkAdminUser(final Map<String,?> param) throws Exception;

	/**
	 * 验证权限密码是否正确
	 * @param userName
	 * @param userRolePwd
	 * @return
	 * @throws Exception
	 */
	public boolean findRolePassword(String userName, String userRolePwd) throws Exception;
	
	/**
	 * 检验后台账号是否已存在
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public boolean checkAdminUserIsExist(String userName)throws Exception;
	
	/**
	 * 修改操作
	 * @param adminuservo
	 * @return
	 * @throws Exception
	 */
	public void editAdminUser(AdminUser adminuser)throws Exception;
	
	
	/**
	 * 加载登录用户权限
	 * @return
	 */
	public List<AdminPermissions> finaUserOrRoleAdminPermissions(AdminUser adminuser,String levels)throws Exception;
	
	/**
	 * 权限管理粒度划分到点击按钮
	 */
	public List<AdminPermissions> getRoleUserPermissions(AdminUser adminuser,String permissions)throws Exception;
	
	
	/**
	 * 查找URL对应的权限
	 */
	public List<AdminPermissions> getToPermissions(String permissions)throws Exception;
	
}
