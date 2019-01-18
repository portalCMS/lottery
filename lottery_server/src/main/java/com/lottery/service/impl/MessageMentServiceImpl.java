package com.lottery.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.AdminPermissions;
import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.AdminPermissionsVO;
import com.lottery.dao.MassageMentDao;
import com.lottery.service.MessageMentService;

@Service
public class MessageMentServiceImpl implements MessageMentService{
	
	@Autowired
	private MassageMentDao messageMentDao;
	
	/**
	 * 权限列表
	 */
	@Override
	public Page<AdminPermissionsVO, AdminPermissions> queryAdminPermissionsPage(
			AdminPermissionsVO vo) throws Exception {
		// TODO Auto-generated method stub
		return messageMentDao.queryAdminPermissionsPage(vo);
	}
	
	/**
	 * 删除
	 */
	@Override
	public void deleteAdminPermissions(long id) throws Exception {
		// TODO Auto-generated method stub
		AdminPermissions adminPermissions=messageMentDao.queryById(id);
		if(adminPermissions!=null){
			messageMentDao.delete(adminPermissions);
		}
	}
	
	/**
	 * 修改
	 */
	@Override
	public void updateAdminPermissions(AdminPermissions po,AdminUser user) throws Exception {
		// TODO Auto-generated method stub
		AdminPermissions adminPermissions=messageMentDao.queryById(po.getId());
		if(adminPermissions!=null){
			adminPermissions.setPermissionsName(po.getPermissionsName());
			adminPermissions.setPermissions(po.getPermissions());
			adminPermissions.setVersion(po.getVersion());
			adminPermissions.setUpdateUser(user.getUserName());
			adminPermissions.setUpdateTime(new Date());
			adminPermissions.setLevels(po.getLevels());
			adminPermissions.setStatus(po.getStatus());
			messageMentDao.update(adminPermissions);
		}
	}
	
	/**
	 * 新增
	 */
	@Override
	public void insertAdminPermissions(AdminPermissions adminPermissions,AdminUser user)
			throws Exception {
		// TODO Auto-generated method stub
		adminPermissions.setPermissionsType(19002);
		adminPermissions.setCreateUser(user.getUserName());
		adminPermissions.setCreateTime(new Date());
		adminPermissions.setUpdateUser(user.getUserName());
		adminPermissions.setUpdateTime(new Date());
		adminPermissions.setVersion(0);
		messageMentDao.insert(adminPermissions);
	}
	
	@Override
	public AdminPermissions findAdminPermissionsById(long id) throws Exception {
		// TODO Auto-generated method stub
		return messageMentDao.queryById(id);
	}
	
	
	@Override
	public List<AdminPermissions> queryAdminPermissionsByLevels()
			throws Exception {
		// TODO Auto-generated method stub
		return messageMentDao.queryAdminPermissionsByLevels();
	}
}
