package com.lottery.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.AdminPermissions;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.AdminPermissionsVO;
import com.lottery.dao.MassageMentDao;
import com.lottery.persist.generice.GenericDAO;

@Repository
public class MessageMentDaoImpl extends GenericDAO<AdminPermissions> implements MassageMentDao{

	private static Logger logger = LoggerFactory.getLogger(MessageMentDaoImpl.class);
	
	public MessageMentDaoImpl() {
		super(AdminPermissions.class);
	}
	
	
	@Override
	public Page<AdminPermissionsVO, AdminPermissions> queryAdminPermissionsPage(
			AdminPermissionsVO vo) throws Exception {
		// TODO Auto-generated method stub
		List<String> formula = new ArrayList<String>();
		List<String> limitKeys = new ArrayList<String>();
		List<Object> limitVals = new ArrayList<Object>();
		if(vo!=null&& vo.getPermissionsName()!=null&&!"".equals(vo.getPermissionsName())){
			formula.add("=");
			limitKeys.add("permissionsName");
			limitVals.add(vo.getPermissionsName());
		}
		Page<AdminPermissionsVO, AdminPermissions> page = (Page<AdminPermissionsVO, AdminPermissions>) doPageQuery(vo, AdminPermissions.class, formula, limitKeys, limitVals, true);
		return page;
	}
	
	
	@Override
	public List<AdminPermissions> queryAdminPermissionsByLevels()
			throws Exception {
		StringBuffer strbuff=new StringBuffer("from AdminPermissions a where a.levels=0  and a.permissionsName!='管理员'");
		Query query=this.getSession().createQuery(strbuff.toString());
		return query.list();
	}
}
