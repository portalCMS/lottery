package com.lottery.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.bean.entity.AdminPermissions;
import com.lottery.bean.entity.AdminRole;
import com.lottery.bean.entity.vo.AdminPermissionsVO;
import com.lottery.bean.entity.vo.AdminRoleVO;
import com.lottery.dao.IAdminPermissionsDao;
import com.lottery.dao.IAdminRoleDao;
import com.lottery.dao.IAdminRolePermissionsDao;
import com.lottery.service.IAdminRoleInitService;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DozermapperUtil;

public class AdminRoleInitServiceImpl implements IAdminRoleInitService {


	@Autowired
	private IAdminRoleDao adminRoleDao;

	@Autowired
	private IAdminRolePermissionsDao adminRolePermissionsDao;

	@Autowired
	private IAdminPermissionsDao adminPermissionsDao;

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public Map<String, Object> findAdminRoleList() {
		// TODO Auto-generated method stub
		Map<String, Object> roleMap = new HashMap<String, Object>();
		List<AdminRoleVO> adminRoleVo = new ArrayList<AdminRoleVO>();
		List<AdminRole> adminRolelist = adminRoleDao
				.getSession()
				.createQuery(
						"from AdminRole t where t.status="
								+ DataDictionaryUtil.STATUS_OPEN).list();
		for (AdminRole ar : adminRolelist) {
			AdminRoleVO adminRoleVO = new AdminRoleVO();
			DozermapperUtil.getInstance().map(ar,adminRoleVO);
			StringBuffer sqlquery = new StringBuffer("SELECT t1 from AdminRolePermissions t,AdminPermissions t1 where t.permissionsId = t1.id and t1.status = ");
			sqlquery.append(DataDictionaryUtil.STATUS_OPEN).append(" and t.roleId=").append(ar.getId());
			List<AdminPermissions> AdminRolePermissionslist = adminRolePermissionsDao
					.getSession()
					.createQuery(sqlquery.toString()).list();
			List<AdminPermissionsVO> adminPermissionsVOList = new ArrayList<AdminPermissionsVO>();
			for(AdminPermissions permissions:AdminRolePermissionslist){
				AdminPermissionsVO adminPermissionsVO = new AdminPermissionsVO();
				DozermapperUtil.getInstance().map(permissions, adminPermissionsVO);
				adminPermissionsVOList.add(adminPermissionsVO);
			}
			adminRoleVO.setPermissionsList(adminPermissionsVOList);
			roleMap.put(adminRoleVO.getId()+"", adminRoleVO);
		}
		return roleMap;
	}
}
