package com.lottery.admin.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lottery.bean.entity.AdminPermissions;
import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.LotteryType;
import com.lottery.bean.entity.vo.AdminPermissionsVO;
import com.lottery.bean.entity.vo.AdminUserVO;
import com.lottery.bean.entity.vo.TempMapVO;
import com.lottery.service.IAdminUserService;
import com.lottery.service.IBetRecordService;
import com.lottery.service.ILotteryTypeService;
import com.xl.lottery.exception.LotteryExceptionDictionary;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.LotWebUtil;




/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 */
@Controller
public class LoginController extends BaseController{

	@Autowired
	private IAdminUserService adminUserService;
	
	@Autowired
	private IBetRecordService recordService;
	
	@Autowired
	private ILotteryTypeService lotteryService;
	
	@RequestMapping(value="/login")
	public ModelAndView showIndex(AdminUserVO admin,HttpServletRequest request, HttpServletResponse response
			,RedirectAttributes attrs){
		 ModelMap model = new ModelMap();
		HttpSession session = LotWebUtil.getSessionMap().get(admin.getAdminName());
		HttpSession sessionnew = request.getSession();
		if(session!=null&&(session!=sessionnew)){
			try {
				session.invalidate();
			} catch (Exception e) {
				// TODO: handle exception
				LotWebUtil.getSessionMap().remove(admin.getAdminName());
			}
		}
		//图片验证码
		String picCode = (String) sessionnew.getAttribute(PictureCheckCodeController.RANDOMCODEKEY);
		if(picCode==null||!picCode.equalsIgnoreCase(admin.getPicCode())){
			request.setAttribute("errorMsg", LotteryExceptionDictionary.PICCODEERROR);
			return new ModelAndView("login"); 
		}
		sessionnew.removeAttribute(PictureCheckCodeController.RANDOMCODEKEY);
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("username", admin.getAdminName());
		param.put("userpwd", admin.getAdminPwd());
		try {
			AdminUser user = adminUserService.checkAdminUser(param);
			if(user==null||user.getUserName()==null){
				request.setAttribute("errorMsg", LotteryExceptionDictionary.LOGINERROR);
				return new ModelAndView("login"); 
			}
			
			if(user!=null&&user.getUserError()>=3){
				request.setAttribute("errorMsg", LotteryExceptionDictionary.LOGINERRORCOUNT);
				return new ModelAndView("login"); 
			}
			
			sessionnew.setAttribute(CommonUtil.USERKEY, user);
			user.setIp(request.getRemoteAddr());
			LotWebUtil.getSessionMap().put(user.getUserName(), sessionnew);
			attrs.addFlashAttribute("userName", user.getUserName());
			List<AdminPermissionsVO> listAdminPermissionsVO=adminUserService.finaUserOrRoleAdminPermissions(user,"0");
			request.getSession().setAttribute("listAdminPermissionsVO", listAdminPermissionsVO); //
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
			return new ModelAndView("login"); 
		}
		
		return new ModelAndView("redirect:index.do");
	}
	
	@RequestMapping("/index")
	public ModelAndView showIndex(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String,Object> model=new HashMap<String, Object>();
		AdminUser admin  = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
	    List<AdminPermissions> list=adminUserService.getRoleUserPermissions(admin, "/querySalesData.do");
		if(list!=null && list.size()>0){
			model.put("isscue", "true");
		}else{
			model.put("isscue", "false");
		}
		return new ModelAndView("index",model);
	}
	
	/**
	 * 查询彩种销售数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/querySalesData")
	@ResponseBody
	public Map<String,Object> querySalesData(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		Map<String,BigDecimal> moneyMap = null;
		try {
			List<LotteryType> lotterys = lotteryService.queryLotteryTypeAll(param);
			param.put("lotteryListKey", lotterys);
			moneyMap = recordService.lotterySalesInfo(param);
			Map<String,String> lotPCMap = new HashMap<String, String>();
			Map<String,String> groupPCMap = new HashMap<String, String>();
			Map<String,String> lotGroupMap = new HashMap<String, String>();
			//BigDecimal allAmount = moneyMap.get("allLotsAmount");
			for(LotteryType type : lotterys){
				BigDecimal lotMoney = moneyMap.get(type.getLotteryCode());
				BigDecimal groupMoney = moneyMap.get(type.getLotteryGroup());
				lotPCMap.put(type.getLotteryName(), lotMoney.toString());
				if(null==groupPCMap.get(type.getLotteryGroup())){
					String lotGroupName = CommonUtil.lotteryGroupMap.get(type.getLotteryGroup());
					groupPCMap.put(lotGroupName, groupMoney.toString());
				}
				String lotGroupName = CommonUtil.lotteryGroupMap.get(type.getLotteryGroup());
				if(null==lotGroupMap.get(lotGroupName)){
					lotGroupMap.put(lotGroupName, type.getLotteryName());
				}else{
					String names = lotGroupMap.get(lotGroupName);
					lotGroupMap.remove(lotGroupName);
					lotGroupMap.put(lotGroupName, names+","+type.getLotteryName());
				}
			}
			List<TempMapVO> lotPcMaps = new ArrayList<TempMapVO>();
			List<TempMapVO> lotGroupMaps = new ArrayList<TempMapVO>();
			List<TempMapVO> groupMaps = new ArrayList<TempMapVO>();
			for(String key : lotPCMap.keySet()){
				TempMapVO vo = new TempMapVO();
				vo.setKey(key);
				vo.setValue(lotPCMap.get(key));
				lotPcMaps.add(vo);
			}
			
			for(String key : groupPCMap.keySet()){
				TempMapVO vo = new TempMapVO();
				vo.setKey(key);
				vo.setValue(groupPCMap.get(key));
				lotGroupMaps.add(vo);
			}
			
			for(String key : lotGroupMap.keySet()){
				TempMapVO vo = new TempMapVO();
				vo.setKey(key);
				vo.setValue(lotGroupMap.get(key));
				groupMaps.add(vo);
			}
			model.put("lotMaps",lotPcMaps);
			model.put("lotGroupMaps",lotGroupMaps);
			model.put("groupMaps",groupMaps);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("gohome")
	public ModelAndView showlogin(String error,HttpServletRequest request, HttpServletResponse response) throws Exception{
		if(error!=null&&error.equals("true")){
			request.getSession().invalidate();
			request.setAttribute("errorMsg", "注销成功");
		}else{
			request.setAttribute("errorMsg", "Session超时");
		}
		return new ModelAndView("login");
	}
	
	@RequestMapping("/loginhome")
	public ModelAndView loginhome(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("login");
	}
	
	@RequestMapping("/loginout")
	public ModelAndView loginout(HttpServletRequest request, HttpServletResponse response,RedirectAttributes reAttributes){
		HttpSession session = request.getSession();
		session.invalidate();
		return new ModelAndView("redirect:loginhome.do");
	}


	/**
	 * 获取当前用户的账户余额
	 * @param lotteryVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getUserName")
	@ResponseBody
	public Map<String,Object> getUserName(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String, Object>(); 
		AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		model.put("userName", user.getUserName());
		return model;
	}
}
