package com.lottery.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.AdminUserRole;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface IAdminUserRoleDao extends IGenericDao<AdminUserRole> {
	public List<AdminUserRole> findAdminUserRoleByUserID(Map<String, ?> param);

	public List<AdminUserRole> finaAdminUserRoleByRoleID(long roleId);
}
