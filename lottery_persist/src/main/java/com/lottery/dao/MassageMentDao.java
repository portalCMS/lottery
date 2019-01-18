package com.lottery.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.AdminPermissions;
import com.lottery.bean.entity.vo.AdminPermissionsVO;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface MassageMentDao extends IGenericDao<AdminPermissions>{

	/**
	 * 加载权限列表
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Page<AdminPermissionsVO,AdminPermissions> queryAdminPermissionsPage(AdminPermissionsVO vo)throws Exception;
	
	
	public List<AdminPermissions> queryAdminPermissionsByLevels()throws Exception;
	
}
