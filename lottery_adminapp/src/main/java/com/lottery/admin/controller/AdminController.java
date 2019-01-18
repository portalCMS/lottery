package com.lottery.admin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lottery.bean.entity.AdminRole;
import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.AdminRoleVO;
import com.lottery.bean.entity.vo.AdminUserVO;
import com.lottery.service.IAdminRoleService;
import com.lottery.service.IAdminUserService;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.exception.LotteryExceptionDictionary;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.MD5Util;

@Controller
@RequestMapping("admin")
public class AdminController extends BaseController{

	@Autowired
	private IAdminUserService adminUserService;
	
	@Autowired IAdminRoleService adminRoleService;
	
	@RequestMapping("showadmins")
	public ModelAndView showAdminList(HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			List<AdminUser> adminusers = adminUserService.queryAllAdminUser();
			model.put("admins", adminusers);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("admin/admins",model);
	}
	
	@RequestMapping("showaddadmins")
	public ModelAndView showAddAdmin(HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("admin/addAdmin",model);
	}
	
	@RequestMapping("addAdminUser")
	public ModelAndView addAdminUser(AdminUserVO vo,HttpServletRequest request,HttpServletResponse reponse,RedirectAttributes redirAttributes){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		model.put("auvo",vo);
		//图片验证码
		String picCode = (String) session.getAttribute(PictureCheckCodeController.RANDOMCODEKEY);
		if(picCode==null||!picCode.equalsIgnoreCase(vo.getPicCode())){
			request.setAttribute("errorMsg", LotteryExceptionDictionary.PICCODEERROR);
			return new ModelAndView("admin/addAdmin",model); 
		}
		session.removeAttribute(PictureCheckCodeController.RANDOMCODEKEY);
		AdminUser adminUser = (AdminUser) request.getSession().getAttribute(
				CommonUtil.USERKEY);
		try {
			boolean isRight = adminUserService.checkRolePassword(
					adminUser.getUserName(), vo.getAmPwd());
			if (!isRight) {
				model.put("errorMsg","权限密码不正确，请重新输入！");
				return new ModelAndView("admin/addAdmin", model);
			}
			param.put("amvo", vo);
			AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
			param.put(CommonUtil.USERKEY, user);
			adminUserService.saveAdminUser(param);
			model.put("success","保存成功");
			model.remove("auvo");
		} catch (Exception e1) {
			LotteryExceptionLog.wirteLog(e1, model);
		}
		return new ModelAndView("admin/addAdmin",model);
	}

	/**
	 * 更改用户基本信息
	 * @param request
	 * @param reponse
	 * @param id
	 * @return
	 */
	@RequestMapping("showupdateadmins")
	public ModelAndView showUpdateAdmin(HttpServletRequest request,HttpServletResponse reponse,int id){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id", id);
		try {
			AdminUser adminuser=adminUserService.findAdminUserById(param);
			model.put("auvo", adminuser);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
	
		return new ModelAndView("admin/editAdmin",model);
	}
	
	/**
	 * 确定保存修改
	 * @param request
	 * @param reponse
	 * @param adminuser
	 * @return
	 */
	@RequestMapping("editadminuser")
	public ModelAndView editAdminUser(HttpServletRequest request,HttpServletResponse reponse,AdminUserVO adminuser,RedirectAttributes reAttributes) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			HttpSession session = request.getSession();
			//图片验证码
			String picCode = (String) session.getAttribute(PictureCheckCodeController.RANDOMCODEKEY);
			if(picCode==null||!picCode.equalsIgnoreCase(adminuser.getPicCode())){
				request.setAttribute("errorMsg", LotteryExceptionDictionary.PICCODEERROR);
				model.put("auvo", adminuser);
				return new ModelAndView("admin/addAdmin",model); 
			}
			session.removeAttribute(PictureCheckCodeController.RANDOMCODEKEY);
			AdminUser adminUser = (AdminUser) request.getSession().getAttribute(
					CommonUtil.USERKEY);
			boolean isRight = adminUserService.checkRolePassword(
					adminUser.getUserName(), adminuser.getAmPwd());
			if (!isRight) {
				model.put("errorMsg","权限密码不正确，请重新输入！");
				model.put("auvo", adminuser);
				return new ModelAndView("admin/addAdmin", model);
			}
			AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
			adminuser.setAdminPwd(MD5Util.makeMD5(adminuser.getAdminPwd()));
			adminuser.setAdminRolePwd(MD5Util.makeMD5(adminuser.getAdminRolePwd()));
			adminuser.setUpdateUser(user.getUserName());
			adminuser.setUpdateTime(new Date());
			adminUserService.updateAdminUser(adminuser);
			reAttributes.addFlashAttribute("success", "保存成功!");
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("redirect:showadmins.do",model);
	}
	
	/**
	 * 角色分页查询
	 * @param vo
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("/initAdminRole")
	public ModelAndView initNotice(AdminRoleVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		Page<AdminRoleVO, AdminRole> page= null;
		try {
			page = adminRoleService.queryAdminRolePage(vo);
			model.put("page", page);
			model.put("adminRoleVo",vo);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		
		return new ModelAndView("admin/roleManageMent",model);
	}
	
	/**
	 * 角色新增页面
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("showAdminRole")
	public ModelAndView showAdminRole(HttpServletRequest request,HttpServletResponse reponse,long roleId){
		Map<String,Object> model = new HashMap<String,Object>();
		try {
			AdminRole rolevo=new AdminRole();
			rolevo.setId(roleId);
			if(roleId!=0){
				rolevo=adminRoleService.findAdminRoleById(roleId);
			}
			model.put("rolevo", rolevo);
		} catch (Exception e) {
			// TODO: handle exception
			LotteryExceptionLog.wirteLog(e, model);
		}

		return new ModelAndView("admin/addAdminRole", model);
	}
	
	/**
	 * 确定保存
	 * @param request
	 * @param reponse
	 * @param adminrole
	 * @param vo
	 * @param reAttributes
	 * @return
	 */
	@RequestMapping("insertAdminRole")
	public ModelAndView inserAdminRole(HttpServletRequest request,HttpServletResponse reponse,AdminRole adminrole,AdminUserVO vo,RedirectAttributes reAttributes){
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			HttpSession session = request.getSession();
			//图片验证码
			String picCode = (String) session.getAttribute(PictureCheckCodeController.RANDOMCODEKEY);
			if(picCode==null||!picCode.equalsIgnoreCase(vo.getPicCode())){
				request.setAttribute("errorMsg", LotteryExceptionDictionary.PICCODEERROR);
				model.put("rolevo", adminrole);
				return new ModelAndView("admin/addAdminRole",model); 
			}
			session.removeAttribute(PictureCheckCodeController.RANDOMCODEKEY);
			AdminUser adminUser = (AdminUser) request.getSession().getAttribute(
					CommonUtil.USERKEY);
			boolean isRight = adminUserService.checkRolePassword(
					adminUser.getUserName(), vo.getAmPwd());
			if (!isRight) {
				model.put("errorMsg","权限密码不正确，请重新输入！");
				model.put("rolevo", adminrole);
				return new ModelAndView("admin/addAdminRole", model);
			}
			AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
			if(adminrole!=null && adminrole.getId()!=0){
				adminRoleService.updateToAdminRole(user, adminrole);
			}else{
				adminRoleService.insertToAdminRole(user, adminrole);
			}
			
			reAttributes.addFlashAttribute("success", "保存成功!");
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("redirect:initAdminRole.do",model);
	}
	
	/**
	 * 删除角色
	 * @param request
	 * @param reponse
	 * @param vo
	 * @param reAttributes
	 * @return
	 */
	@RequestMapping("delAdminRole")
	public ModelAndView deleteToAdminRole(HttpServletRequest request,HttpServletResponse reponse,AdminUserVO vo,RedirectAttributes reAttributes){
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if(vo!=null && vo.getId()!=0){
				adminRoleService.deleteToAdminRole(vo.getId());
				reAttributes.addFlashAttribute("success", "删除成功!");
			}else{
				model.put("errorMsg", "删除失败,参数不对!");
			}
		} catch (Exception e) {
			// TODO: handle exception
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("redirect:initAdminRole.do",model);
	}
	
	/**
	 * 
	 * @param request
	 * @param reponse
	 * @param vo
	 * @param reAttributes
	 * @return
	 * @throws LotteryException 
	 */
	@RequestMapping("error")
	public ModelAndView error(HttpServletRequest request,HttpServletResponse reponse,RedirectAttributes reAttributes) throws LotteryException{
		Map<String, Object> model = new HashMap<String, Object>();
		String url=request.getParameter("urls");
		reAttributes.addFlashAttribute("success", "温馨提示:亲，你没有访问该功能的权限!");
		return new ModelAndView("redirect:"+url,model);
	}
}
