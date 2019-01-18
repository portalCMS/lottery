package com.lottery.service.impl;


import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.AdminPermissions;
import com.lottery.bean.entity.AdminRolePermissions;
import com.lottery.bean.entity.AdminUser;
import com.lottery.dao.IAdminPermissionsDao;
import com.lottery.dao.IAdminRolePermissionsDao;
import com.lottery.service.IAdminRolePermissionsService;

@Service
public class AdminRolePermissionServiceImpl implements IAdminRolePermissionsService{

	@Autowired
	private IAdminRolePermissionsDao adminRolePermissionsDao;
	
	@Override
	public AdminRolePermissions finaAdminRolePermissionsByID(int roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAdminPermissionsList(String levels,String roleId)throws Exception {
		 List<AdminPermissions> listAdminPermissions=adminRolePermissionsDao.getAdminPermissionsList(levels);
	//	adminRoleStr.append("<label class='control-label'>权限列表：</label> ");
		 StringBuffer adminRoleStr=new StringBuffer();
		 int rtnum=0;
		for(int i=0;i<listAdminPermissions.size();i++){
			AdminPermissions adminPermissions=listAdminPermissions.get(i);
			
			 rtnum=adminRolePermissionsDao.findAdminRolePermissionsByID(Long.valueOf(roleId),adminPermissions.getId());
			
			adminRoleStr.append("<div>");
			adminRoleStr.append("<label class='checkbox-inline'>");
			adminRoleStr.append("<input type='checkbox' id='"+adminPermissions.getId()+"'  name='adminuserrolecheck' ");
			if(rtnum>0){
				adminRoleStr.append("checked");
			}
			adminRoleStr.append(" value='"+adminPermissions.getId()+","+adminPermissions.getId()+"' onclick='onbiatclick("+adminPermissions.getId()+")'/>"+adminPermissions.getPermissionsName()+"");
			adminRoleStr.append("</label>");
			List<AdminPermissions> listAdminPermissionsLevle=adminRolePermissionsDao.getAdminPermissionsList(String.valueOf(adminPermissions.getId()));
			for(int j=0;j<listAdminPermissionsLevle.size();j++){
				AdminPermissions adminPermissionspo=listAdminPermissionsLevle.get(j);
				rtnum=adminRolePermissionsDao.findAdminRolePermissionsByID(Long.valueOf(roleId),adminPermissionspo.getId());
				adminRoleStr.append("---->");
				adminRoleStr.append("<label class='checkbox-inline'>");
				adminRoleStr.append("<input type='checkbox' id='"+adminPermissionspo.getId()+"'  name='adminuserrolecheck'"); //&nbsp;&nbsp;
				if(rtnum>0){
					adminRoleStr.append("checked");
				}
				adminRoleStr.append(" value='"+adminPermissionspo.getId()+","+adminPermissions.getId()+"' onclick='oncheckclick("+adminPermissionspo.getId()+")'/>"+adminPermissionspo.getPermissionsName()+"");
				adminRoleStr.append("</label>");
				
			}
			adminRoleStr.append("</div>");
			adminRoleStr.append("</br>");
		}
	
		return adminRoleStr.toString();
	}
	public boolean deleteRolePermissions(long roleId)throws Exception{
		return adminRolePermissionsDao.deleteAdminPermissions(roleId);
	}
	public void insertRolepermissions(String roleId,String permissionsIds,AdminUser user) throws Exception{
			boolean isScue=this.deleteRolePermissions(Long.valueOf(roleId));
			String[] ids=permissionsIds.split("#");
			for(int i=0;i<ids.length;i++){
				AdminRolePermissions ad=new AdminRolePermissions();
				ad.setRoleId(Long.valueOf(roleId));
				String[] pid = ids[i].split(",");
				ad.setPermissionsId(Long.valueOf(pid[0].toString()));
				ad.setStatus(10002);
				ad.setCreateUser(user.getUserName());
				ad.setCreateTime(new Date());
				ad.setUpdateUser(user.getUserName());
				ad.setUpdateTime(new Date());
				ad.setVersion(0);
				adminRolePermissionsDao.insert(ad);
			}
	}
}
