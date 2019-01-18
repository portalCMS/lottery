package com.lottery.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.AdminPermissions;
import com.lottery.bean.entity.AdminRolePermissions;
import com.lottery.bean.entity.AdminUserRole;
import com.lottery.bean.entity.ClassSort;
import com.lottery.dao.IAdminRolePermissionsDao;
import com.lottery.persist.generice.GenericDAO;

@Repository
public class AdminRolePermissionsDaoImpl extends GenericDAO<AdminRolePermissions> implements IAdminRolePermissionsDao{

	private static Logger logger = LoggerFactory.getLogger(AdminRolePermissionsDaoImpl.class);
	
	public AdminRolePermissionsDaoImpl() {
		super(AdminRolePermissions.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public List<AdminPermissions> getAdminPermissionsList(String levels) {
		StringBuffer queryString = new StringBuffer("from AdminPermissions t where t.levels = ?");
		Query query = getSession().createQuery(queryString.toString());
		query.setParameter(0, levels);
		return query.list();
	}
	
	public boolean deleteAdminPermissions(long roleId){
		StringBuffer queryString = new StringBuffer("delete AdminRolePermissions t where t.roleId = ?");
		Query query = getSession().createQuery(queryString.toString());
		query.setParameter(0, roleId);
		int istrue=query.executeUpdate();
		if(istrue>0){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public int findAdminRolePermissionsByID(long roleId,long permissionsId) {
		StringBuffer queryString = new StringBuffer("select count(*) from AdminRolePermissions t where t.roleId = ? and t.permissionsId=?");
		Query query = getSession().createQuery(queryString.toString());
		query.setParameter(0, roleId);
		query.setParameter(1, permissionsId);
		return Integer.parseInt(query.list().get(0).toString());
	}
}
