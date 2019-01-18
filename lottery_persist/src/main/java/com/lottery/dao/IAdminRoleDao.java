package com.lottery.dao;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.AdminRole;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.AdminRoleVO;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface IAdminRoleDao extends IGenericDao<AdminRole>{

	/**
	 * 角色列表
	 * @return
	 */
	public Page<AdminRoleVO,AdminRole> queryAdminRolePage(AdminRoleVO adminrolevo)throws Exception;
	
}
