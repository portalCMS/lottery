package com.lottery.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lottery.bean.entity.AdminRole;
import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.AdminUserRole;
import com.lottery.bean.entity.vo.AdminRoleVO;
import com.lottery.service.IAdminRolePermissionsService;
import com.lottery.service.IAdminRoleService;
import com.lottery.service.IAdminUserRoleService;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;

@Controller
@RequestMapping("admin")
public class CharacterController extends BaseController{

	@Autowired
	private IAdminRoleService adminRoleService;
	
	@Autowired
	private IAdminUserRoleService adminUserRoleService;
	
	@Autowired
	private IAdminRolePermissionsService adminRolePermissionsService;
	/**
	 * 分配角色
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("addcharacter")
	public ModelAndView addCharacter(HttpServletRequest request,HttpServletResponse reponse,String id){
		Map<String,Object> model =new HashMap<String, Object>(); 
		Map<String,Object> param =new HashMap<String, Object>();
		try {
			List<AdminRole> listAdminRole=adminRoleService.queryListAll();
			List<AdminRoleVO> listAdminRoleVO =new ArrayList<AdminRoleVO>();
			for(int i=0;i<listAdminRole.size();i++){
				AdminRoleVO adminRoleVo=new AdminRoleVO();
				BeanUtils.copyProperties(adminRoleVo, listAdminRole.get(i));
				listAdminRoleVO.add(adminRoleVo);
			}
			AdminUser adminuser=adminRoleService.findAdminUserByID(Long.valueOf(id));
			param.put("adminUserId", Long.valueOf(id));
			List<AdminUserRole> listadminUserRole=adminUserRoleService.findAdminUserRoleByUserID(param);
			model.put("listAdminRoleVO", listAdminRole);
			model.put("adminuser", adminuser);
			model.put("listadminUserRole", listadminUserRole);
		} catch (Exception e) {
			// TODO: handle exception
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("admin/adminUserCompetence", model);
	}
	/**
	 *添加角色
	 * @param request
	 * @param reponse
	 * @param roleIds
	 * @param userId
	 */
	@RequestMapping("saveadminuserrole")
	public ModelAndView saveAdminUserRole(HttpServletRequest request,HttpServletResponse reponse,String roleIds,long userId,RedirectAttributes reAttributes){
		Map<String,Object> model=new HashMap<String, Object>();
		try {
			AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
			adminUserRoleService.saveUserRole(roleIds, userId,user);
			reAttributes.addFlashAttribute("success", "添加成功!");
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("redirect:showadmins.do",model);
	}
	
	/**
	 * 分配角色权限
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping("showaddcharacter")
	public ModelAndView showaddAddCharacter(HttpServletRequest request,HttpServletResponse reponse,String id){
		Map<String,Object> model =new HashMap<String, Object>(); 
		Map<String,Object> param =new HashMap<String, Object>();
		try {
			List<AdminRole> listAdminRole=adminRoleService.queryListAll();
			List<AdminRoleVO> listAdminRoleVO =new ArrayList<AdminRoleVO>();
			for(int i=0;i<listAdminRole.size();i++){
				AdminRoleVO adminRoleVo=new AdminRoleVO();
				BeanUtils.copyProperties(adminRoleVo, listAdminRole.get(i));
				listAdminRoleVO.add(adminRoleVo);
			}
			model.put("listAdminRoleVO", listAdminRole);
		} catch (Exception e) {
			// TODO: handle exception
			LotteryExceptionLog.wirteLog(e, model);
		}

		return new ModelAndView("admin/adminUserCharacter", model);
	}
	
	/**
	 * 加载权限列表
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping("showPermissions")
	public void showPermissions(HttpServletRequest request,HttpServletResponse reponse,String roleId){
		reponse.setContentType("text/HTML;charset=UTF-8");
		Map<String,Object> model =new HashMap<String, Object>();
		try {
			PrintWriter out = reponse.getWriter();
			StringBuffer adminRoleStr = new StringBuffer();
			adminRoleStr.append("<div class='panel panel-default'><div class='panel-body'><div class='form-inline clearfix'><div class='form-group '>");
			String strs=adminRolePermissionsService.getAdminPermissionsList("0",roleId);
			adminRoleStr.append(strs);
			adminRoleStr.append("</div></div></div></div>");
			out.print(adminRoleStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
	}
	/**
	 * 添加角色权限
	 * @param request
	 * @param reponse
	 * @param roleId
	 * @param permissionsIds
	 */
	@RequestMapping("addAdminRolePermissions")
	public ModelAndView saveAdminRolePermissions(HttpServletRequest request,HttpServletResponse reponse,RedirectAttributes reAttributes,String roleId,String permissionsIds){
		Map<String,Object> model =new HashMap<String, Object>();
		try {
			AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
			adminRolePermissionsService.insertRolepermissions(roleId, permissionsIds, user);
			reAttributes.addFlashAttribute("success", "添加成功!");
		} catch (Exception e) {
			// TODO: handle exception
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("redirect:showadmins.do",model);
	}
}
