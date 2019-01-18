package com.lottery.dao.impl;



import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.AdminPermissions;
import com.lottery.dao.IAdminPermissionsDao;
import com.lottery.persist.generice.GenericDAO;

@Repository
public class AdminPermissionsDaoImpl extends GenericDAO<AdminPermissions> implements IAdminPermissionsDao{

	private static Logger logger = LoggerFactory.getLogger(AdminPermissionsDaoImpl.class);
	
	public AdminPermissionsDaoImpl() {
		super(AdminPermissions.class);
	}

}
