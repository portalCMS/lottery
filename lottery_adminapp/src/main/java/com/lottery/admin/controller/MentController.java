package com.lottery.admin.controller;

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

import com.lottery.bean.entity.AdminPermissions;
import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.AdminPermissionsVO;
import com.lottery.bean.entity.vo.AdminUserVO;
import com.lottery.service.IAdminUserService;
import com.lottery.service.MessageMentService;
import com.xl.lottery.exception.LotteryExceptionDictionary;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;

@Controller
@RequestMapping("ment")
public class MentController extends BaseController{

	@Autowired
	private MessageMentService messageMentService;
	
	@Autowired
	private IAdminUserService adminUserService;
	
	/**
	 * 权限列表
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("initAdminPermissions")
	public ModelAndView initAdminPermissions(HttpServletRequest req,HttpServletResponse res,AdminPermissionsVO vo){
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Page<AdminPermissionsVO,AdminPermissions> page=messageMentService.queryAdminPermissionsPage(vo);
			model.put("page", page);
			model.put("vo", vo);
		} catch (Exception e) {
			// TODO: handle exception
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("admin/adminPermissionsMent",model);
	}
	
	/**
	 * 权限新增页
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("showaddPermissions")
	public ModelAndView showAddAdminpermissions(HttpServletRequest req,HttpServletResponse res,long id){
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			 AdminPermissions po=new AdminPermissions();
			 po.setId(id);
			if(id!=0){
				 po=messageMentService.findAdminPermissionsById(id);
			}
			model.put("vo", po);
			List<AdminPermissions> listAdminPermissions=messageMentService.queryAdminPermissionsByLevels();
			model.put("listAdminPermissions", listAdminPermissions);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ModelAndView("admin/addAdminPermissions",model);
	}
	
	/**
	 * 确定保存
	 * @param request
	 * @param response
	 * @param adminvo
	 * @param vo
	 * @return
	 */
	@RequestMapping("insertAdminPer")
	public ModelAndView addAdminpermissions(HttpServletRequest request,HttpServletResponse response,AdminPermissions adminvo,AdminUserVO vo,RedirectAttributes redirAttributes){
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			HttpSession session = request.getSession();
			AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
			if(adminvo!=null && adminvo.getId()!=0){
				messageMentService.updateAdminPermissions(adminvo, user);
			}else{
				messageMentService.insertAdminPermissions(adminvo, user);
			}
			redirAttributes.addFlashAttribute("success", "保存成功!");
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("redirect:initAdminPermissions.do",model);
	}
	
	/**
	 * 删除权限
	 * @param request
	 * @param response
	 * @param id
	 * @param redirAttributes
	 * @return
	 */
	@RequestMapping("delAdminPer")
	public ModelAndView delAdminPermissions(HttpServletRequest request,HttpServletResponse response,long id,RedirectAttributes redirAttributes){
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			messageMentService.deleteAdminPermissions(id);
			redirAttributes.addFlashAttribute("success", "删除成功!");
		} catch (Exception e) {
			// TODO: handle exception
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("redirect:initAdminPermissions.do",model);
	}
	
}
