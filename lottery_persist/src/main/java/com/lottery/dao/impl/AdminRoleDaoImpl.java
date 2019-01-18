package com.lottery.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.AdminRole;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.AdminRoleVO;
import com.lottery.dao.IAdminRoleDao;
import com.lottery.persist.generice.GenericDAO;

@Repository
public class AdminRoleDaoImpl extends GenericDAO<AdminRole> implements IAdminRoleDao{

	private static Logger logger = LoggerFactory.getLogger(AdminRoleDaoImpl.class);
	
	public AdminRoleDaoImpl() {
		super(AdminRole.class);
	}
	
	/**
	 * 角色列表
	 */
	@Override
	public Page<AdminRoleVO, AdminRole> queryAdminRolePage(
			AdminRoleVO vo)throws Exception {
		// TODO Auto-generated method stub
		List<String> formula = new ArrayList<String>();
		List<String> limitKeys = new ArrayList<String>();
		List<Object> limitVals = new ArrayList<Object>();
		if(vo!=null&& vo.getRoleName()!=null&&!"".equals(vo.getRoleName())){
			formula.add("=");
			limitKeys.add("roleName");
			limitVals.add(vo.getRoleName());
		}
		if(vo!=null&&vo.getRoleEnName()!=null&&!"".equals(vo.getRoleEnName())){
			formula.add("=");
			limitKeys.add("roleEnName");
			limitVals.add(vo.getRoleEnName());
		}
		Page<AdminRoleVO, AdminRole> page = (Page<AdminRoleVO, AdminRole>) doPageQuery(vo, AdminRole.class, formula, limitKeys, limitVals, true);
		return page;
	}
}
