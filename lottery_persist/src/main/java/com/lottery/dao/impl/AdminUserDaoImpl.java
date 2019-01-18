package com.lottery.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.AdminPermissions;
import com.lottery.bean.entity.AdminUser;
import com.lottery.dao.IAdminUserDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.MD5Util;

@Repository
public class AdminUserDaoImpl extends GenericDAO<AdminUser> implements IAdminUserDao{

	private static Logger logger = LoggerFactory.getLogger(AdminUserDaoImpl.class);
	
	public AdminUserDaoImpl() {
		super(AdminUser.class);
	}

	@Override
	public AdminUser checkAdminUser(Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		String username = (String)param.get("username");
		String userpwd = MD5Util.makeMD5((String)param.get("userpwd"));
		StringBuffer sql = new StringBuffer("from AdminUser t where t.userName=? and t.userPwd=? and t.userStatus=? and t.userError<=3");
		AdminUser admin = queryForObject(sql.toString(), new Object[]{username,userpwd,DataDictionaryUtil.STATUS_OPEN});
		return admin;
	}

	@Override
	public boolean findRolePassword(String userName,String userRolePwd) {
		StringBuffer sqlquery = new StringBuffer("from AdminUser t where t.userName = ? and t.userRolePwd=?");
		userRolePwd = MD5Util.makeMD5(userRolePwd);
		AdminUser adminUser = queryForObject(sqlquery.toString(),new Object[]{userName,userRolePwd});
		if(null!=adminUser){
			return true;
		}
		return false;
	}

	@Override
	public boolean checkAdminUserIsExist(String userName) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer queryString = new StringBuffer(" from AdminUser t where t.userName = ? and t.userStatus = 10002 ");
		Query query = getSession().createQuery(queryString.toString());
		query.setParameter(0, userName);
		if(query.list().size()>0)return true;
		return false;
	}
	
	@Override
	public void editAdminUser(AdminUser adminuser) throws Exception {
		// TODO Auto-generated method stub
		this.update(adminuser);
	}
	
	/**
	 * 加载用户权限
	 */
	@Override
	public List<AdminPermissions> finaUserOrRoleAdminPermissions(
			AdminUser adminuser,String levels)throws Exception {
		StringBuffer queryString = new StringBuffer("select s.* from t_admin_user_role e "
				+ " inner join t_admin_role_permissions a on e.role_id=a.role_id "
				+ " inner join t_admin_permissions s on a.permissions_id=s.id "
				+ " where e.admin_user_id =:userid and s.levels =:levels and s.status=10002 order by s.sequence asc");
		SQLQuery sqlQuery = getSession().createSQLQuery(queryString.toString());
		sqlQuery.setParameter("levels", levels);
		sqlQuery.setParameter("userid", adminuser.getId());
		sqlQuery.addEntity(AdminPermissions.class);
		return sqlQuery.list();
	}
	
	/**
	 * 查找URL对应的权限
	 */
	@Override
	public List<AdminPermissions> getToPermissions(String permissions)throws Exception {
		StringBuffer queryString = new StringBuffer("select s.* from t_admin_permissions s "
				+ " where s.permissions =:permissions order by s.sequence asc");
		SQLQuery sqlQuery = getSession().createSQLQuery(queryString.toString());
		sqlQuery.setParameter("permissions", permissions);
		sqlQuery.addEntity(AdminPermissions.class);
		return sqlQuery.list();
	}
	
	/**
	 * 权限管理粒度划分到点击按钮
	 */
	@Override
	public List<AdminPermissions> getRoleUserPermissions(
			AdminUser adminuser,String permissions)throws Exception {
		StringBuffer queryString = new StringBuffer("select s.* from t_admin_user_role e "
				+ " inner join t_admin_role_permissions a on e.role_id=a.role_id "
				+ " inner join t_admin_permissions s on a.permissions_id=s.id "
				+ " where e.admin_user_id =:userid and s.permissions =:permissions order by s.sequence asc");
		SQLQuery sqlQuery = getSession().createSQLQuery(queryString.toString());
		sqlQuery.setParameter("permissions", permissions);
		sqlQuery.setParameter("userid", adminuser.getId());
		sqlQuery.addEntity(AdminPermissions.class);
		return sqlQuery.list();
	}
}
