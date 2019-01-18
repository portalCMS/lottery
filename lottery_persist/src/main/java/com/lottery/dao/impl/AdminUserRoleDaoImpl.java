package com.lottery.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.AdminRolePermissions;
import com.lottery.bean.entity.AdminUserRole;
import com.lottery.bean.entity.BonusGroup;
import com.lottery.dao.IAdminUserRoleDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.util.DataDictionaryUtil;

@Repository
public class AdminUserRoleDaoImpl extends GenericDAO<AdminUserRole> implements IAdminUserRoleDao{

	private static Logger logger = LoggerFactory.getLogger(AdminUserDaoImpl.class);
	
	public AdminUserRoleDaoImpl() {
		super(AdminUserRole.class);
	}
	/****
	 * 取出用户权限信息 george
	 */
	@Override
	public List<AdminUserRole> findAdminUserRoleByUserID(Map<String, ?> param) {
		// TODO Auto-generated method stub
		StringBuffer queryString = new StringBuffer("from AdminUserRole t where t.adminUserId = ?");
		Query query = getSession().createQuery(queryString.toString());
		query.setParameter(0, param.get("adminUserId"));
		return query.list();
	}
	
	@Override
	public List<AdminUserRole> finaAdminUserRoleByRoleID(long roleId) {
		// TODO Auto-generated method stub
		StringBuffer queryString  = new StringBuffer("from AdminUserRole t where t.roleId = ?");
		Query query = getSession().createQuery(queryString.toString());
		query.setParameter(0, roleId);
		List<AdminUserRole> list = query.list(); 
		if(list.size()==0)
		return null;
		return list;
	}
}
