package com.lottery.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lottery.bean.entity.AdminPermissions;
import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.vo.AdminPermissionsVO;
import com.lottery.bean.entity.vo.AdminUserVO;
import com.lottery.dao.IAdminUserDao;
import com.lottery.dao.impl.AdminWriteLog;
import com.lottery.service.IAdminUserService;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.MD5Util;

@Service
public class AdminUserServiceImpl implements IAdminUserService{


	@Autowired
	private IAdminUserDao adminUserDao;
	@Autowired
	private AdminWriteLog adminLog;
	@Override
	public AdminUser findAdminUserById(Map<String, ?> param) {
		// TODO Auto-generated method stub
		int id = (Integer) param.get("id");
		return adminUserDao.queryById(id);
	}

	@Override
	public AdminUser checkAdminUser(Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		return adminUserDao.checkAdminUser(param);
	}
	@Override
	public boolean checkRolePassword(String userName,String userRolePwd) throws Exception {
		boolean isRight = adminUserDao.findRolePassword( userName, userRolePwd);
		return isRight;
	}

	@Override
	public String saveAdminUser(Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		AdminUserVO vo = (AdminUserVO) param.get("amvo");
		AdminUser user = (AdminUser) param.get(CommonUtil.USERKEY);
		boolean isExist = adminUserDao.checkAdminUserIsExist(vo.getAdminName());
		if(isExist)throw new LotteryException("账户已经存在");
		AdminUser entity = adminUserVoToAdminUser(vo);
		entity.addInit(user.getUserName());
		adminUserDao.save(entity);
		return "success";
	}

	private AdminUser adminUserVoToAdminUser(AdminUserVO vo) throws Exception{
		AdminUser entity = new AdminUser();
		entity.setUserName(vo.getAdminName());
		entity.setUserPwd( MD5Util.makeMD5(vo.getAdminPwd()));
		entity.setUserRolePwd( MD5Util.makeMD5(vo.getAdminRolePwd()));
		entity.setUserGroup(vo.getUserGroup());
		entity.setUserAlias(vo.getAdminName()+"管理员");
		entity.setUserStatus(DataDictionaryUtil.STATUS_OPEN);
		entity.setUserError(0);
		entity.setUserOnlineStatus(Integer.toString(DataDictionaryUtil.STATUS_ONLINE_NO));
		return entity;
	}

	@Override
	public List<AdminUser> queryAllAdminUser() throws Exception {
		// TODO Auto-generated method stub
		return adminUserDao.queryAll();
	}
	
	@Override
	public void updateAdminUser(AdminUserVO adminuservo) throws Exception {
		AdminUser adminuser=adminUserDao.queryById(adminuservo.getId());
		if(adminuser!=null){
			if(!StringUtils.isEmpty(adminuservo.getAdminName())){
				adminuser.setUserName(adminuservo.getAdminName());
			}
			if(!StringUtils.isEmpty(adminuservo.getAdminPwd())){
				adminuser.setUserPwd(adminuservo.getAdminPwd());
			}
			if(!StringUtils.isEmpty(adminuservo.getAdminRolePwd())){
				adminuser.setUserRolePwd(adminuservo.getAdminRolePwd());
			}
			if(!StringUtils.isEmpty(adminuservo.getUserGroup())){
				adminuser.setUserGroup(adminuservo.getUserGroup());
			}
			adminuser.setUpdateUser(adminuservo.getUpdateUser());
			adminuser.setUpdateTime(adminuservo.getUpdateTime());
		}
		adminUserDao.editAdminUser(adminuser);
	}
	
	/**
	 * 加载用户权限
	 * @throws LotteryException 
	 */
	@Override
	public List<AdminPermissionsVO> finaUserOrRoleAdminPermissions(
			AdminUser adminuser,String levels) throws Exception {
		// TODO Auto-generated method stub
		List<AdminPermissions> listAdminPermissions=adminUserDao.finaUserOrRoleAdminPermissions(adminuser,levels);
		List<AdminPermissionsVO> listAdminPermissionsVO=new ArrayList<AdminPermissionsVO>();
		for(int i=0;i<listAdminPermissions.size();i++){
			AdminPermissionsVO adminPermissionsVO=new AdminPermissionsVO();
		    for(int j=listAdminPermissions.size()-1;j>i;j--){
		         if(listAdminPermissions.get(i).getPermissionsName().equals(listAdminPermissions.get(j).getPermissionsName())){
		             listAdminPermissions.remove(j);
		         }
		    }
			PropertyUtils.copyProperties(adminPermissionsVO, listAdminPermissions.get(i));
			List<AdminPermissionsVO> listp=this.finaUserOrRoleAdminPermissions(adminuser, String.valueOf(adminPermissionsVO.getId()));
			adminPermissionsVO.setListLevelsPermissions(listp);
			listAdminPermissionsVO.add(adminPermissionsVO);
		}
		return listAdminPermissionsVO;
	}
	
	/**
	 * 权限管理粒度划分到点击按钮
	 */
	public List<AdminPermissions> getRoleUserPermissions(AdminUser adminuser,String permissions)throws Exception{
		return adminUserDao.getRoleUserPermissions(adminuser, permissions);
	}
	
	/**
	 * 查找URL对应的权限
	 */
	public List<AdminPermissions> getToPermissions(String permissions)throws Exception{
		
		return adminUserDao.getToPermissions(permissions);
	}
}
