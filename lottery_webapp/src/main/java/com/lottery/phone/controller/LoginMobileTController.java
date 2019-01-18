package com.lottery.phone.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lottery.annotation.Token;
import com.lottery.bean.entity.CustomerCash;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.LotteryAwardRecord;
import com.lottery.bean.entity.LotteryType;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerUserVO;
import com.lottery.bean.entity.vo.LotteryAwardRecordVO;
import com.lottery.bean.entity.vo.LotteryListVOWebApp;
import com.lottery.bean.entity.vo.LotteryTypeForm;
import com.lottery.bean.entity.vo.LotteryTypeVO;
import com.lottery.filter.BetFilter;
import com.lottery.ip.util.Utils;
import com.lottery.service.IBetRecordService;
import com.lottery.service.ICustomerCashService;
import com.lottery.service.ICustomerOrderService;
import com.lottery.service.ICustomerUserService;
import com.lottery.service.ILotteryAwardRecordService;
import com.lottery.service.ILotteryPlayBonusService;
import com.lottery.service.ILotteryPlayModelService;
import com.lottery.service.ILotteryTypeService;
import com.lottery.service.IPlayModelService;
import com.lottery.service.ITaskConfigService;
import com.xl.lottery.exception.LotteryExceptionDictionary;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DozermapperUtil;
import com.xl.lottery.util.LotWebUtil;

@Controller
public class LoginMobileTController {


	@Autowired
	private ICustomerUserService customerUserService;
	
	@Autowired
	private ILotteryTypeService lotteryTypeService;
	
	@Autowired
	private IPlayModelService playModelService;

	@Autowired
	private ICustomerOrderService customerOrderService;
	
	@Autowired
	private ILotteryTypeService lotteryService;
	
	@Autowired
	private BetFilter betFilter;
	
	@Autowired
	private ILotteryAwardRecordService awardService;
	
	@Autowired
	private IBetRecordService betRecordService;
	
	@Autowired
	private ICustomerCashService cashService;
	
	@Autowired
	private ITaskConfigService taskService;
	
	@Autowired
	private ILotteryPlayModelService lotteryModelService;
	
	@Autowired
	private ILotteryPlayBonusService lpbService;
	/**
	 * 登录首页
	 * @param rq
	 * @param re
	 * @return
	 */
	@RequestMapping("/showloginmp")
	public ModelAndView gologin(HttpServletRequest rq,HttpServletResponse re){
		Map<String, Object> model =new HashMap<String, Object>();
		return new ModelAndView("jspmp/login", model);
	}
	
	/**
	 * 用户登录
	 * @param vo
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("/loginmp")
//	@Token(needRemoveToken=true)
	public ModelAndView showLogin(CustomerUserVO vo,HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
		ModelMap model = new ModelMap();
		HttpSession session = LotWebUtil.getSessionMap().get(vo.getCustomerName());
		HttpSession sessionnew = request.getSession();
		if(session!=null&&(session!=sessionnew)){
			try {
				session.invalidate();
			} catch (Exception e) {
				// TODO: handle exception
				LotWebUtil.getSessionMap().remove(vo.getCustomerName());
			}
		}
		
		redirectAttributes.addFlashAttribute("noShowError", true);
		Integer pwdError = (Integer) sessionnew.getAttribute("pwdError");
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("customeruserkey", vo);
		try {
			CustomerUser entity = customerUserService.loginCheckCustomerUser(param);
			if(entity==null||entity.getCustomerName()==null){
				redirectAttributes.addFlashAttribute("errorMsg", LotteryExceptionDictionary.LOGINERROR);
				if(pwdError == null){
					pwdError = 1;
				}else{
					pwdError += 1;
				} 
				sessionnew.setAttribute("pwdError", pwdError);
				return new ModelAndView("redirect:showloginmp.html",model);
			}
			if(entity.getCustomerStatus() == DataDictionaryUtil.STATUS_CLOSE){
				redirectAttributes.addFlashAttribute("errorMsg", "账号已被冻结,请联系客服");
				return new ModelAndView("redirect:showloginmp.html",model);
			}
			if(entity.getCustomerError()>=3){
				redirectAttributes.addFlashAttribute("errorMsg", LotteryExceptionDictionary.LOGINERRORCOUNT);
				return new ModelAndView("redirect:home.html",model);
			}
			entity.setIp(Utils.getIpAddr(request));
			CustomerUser sessionUser = new CustomerUser();
			DozermapperUtil.getInstance().map(entity, sessionUser);
			sessionnew.setAttribute(CommonUtil.CUSTOMERUSERKEY, sessionUser);
			LotWebUtil.getSessionMap().put(entity.getCustomerName(), sessionnew);
			if(entity.getCustomerOnlineStatus()==DataDictionaryUtil.STATUS_ONLINE_NO){   //判断是否已激活
				if(entity.getCreateUser().equals(CommonUtil.PROXYUSER)){
					return new ModelAndView("redirect:showloginmp.html",model);
				}else{
					return new ModelAndView("redirect:showloginmp.html",model);
				}
			}
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, redirectAttributes);
			if(pwdError == null){
				pwdError = 1;
			}else{
				pwdError += 1;
			} 
			sessionnew.setAttribute("pwdError", pwdError);
			return new ModelAndView("redirect:showloginmp.html",model);
		}
		sessionnew.removeAttribute("pwdError");
		return new ModelAndView("redirect:indexmp.html",model);
	}
	
	
	
	
	/**
	 * 加载侧边栏
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/showLeftMenuBar")
	public ModelAndView showLeftMenuBar(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>(); 
		Map<String,Object> param = new HashMap<String,Object>();
		
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		if(user!=null){
			param.put("userId",user.getId());
			
			CustomerCash cash = null;
			List<LotteryListVOWebApp> listLotteryWebApp=null;
			try {
				cash = cashService.findCustomerCashByUserId(param);
				model.put("cash", cash);
				listLotteryWebApp=this.getAllLottery(request, response);
				model.put("listLotteryWebApp", listLotteryWebApp);
			} catch (Exception e) {
				e.printStackTrace();
				LotteryExceptionLog.wirteLog(e, model);
			}
			return new ModelAndView("jspmp/sidebar.tpl", model);
		}else{
			return new ModelAndView("jspmp/sidebarold.tpl", model);
		}
		
	}
	

	/**
	 * 开奖历史记录
	 * @param betRecordVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/showrecord")
	@ResponseBody
	public Map<String, ?> getpageRecord(LotteryAwardRecordVO vo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("awardKey", vo);
		try {
			Page<LotteryAwardRecordVO, LotteryAwardRecord> page = awardService.queryCodesRecord(param);
			model.put("record", page);
			model.put("totalCount", page.getTotalCount());
			model.put("pageNum", page.getPageNum());
			model.put("maxCount", page.getMaxCount());
			model.put("pageCount", page.getPageCount());
		} catch (Exception e) {
			// TODO: handle exception
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	/**
	 * 获取当前的所有彩种
	 * @param lotteryVo
	 * @param request
	 * @param response
	 * @return
	 */

	public List<LotteryListVOWebApp> getAllLottery(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		param.put("userId",user.getId());
		LotteryTypeVO lotteryVo = new LotteryTypeVO();
		lotteryVo.setLotteryStatus(DataDictionaryUtil.STATUS_OPEN);
		lotteryVo.setLotteryLevel(DataDictionaryUtil.COMMON_FLAG_1);
		param.put("lotteryKey",lotteryVo);
		List<LotteryType> lotteryList = null;
		List<LotteryListVOWebApp>  voList=null;
		try {
			lotteryList = lotteryService.queryLotteryList(param);
			//构建vo，存放彩种组对应的彩种集合。
			Map<String,List<LotteryTypeForm>> groupMap = new HashMap<String,List<LotteryTypeForm>>();
			for(LotteryType lottery : lotteryList ){
				String group = lottery.getLotteryGroup();
				if(null==groupMap.get(group)){
					List<LotteryTypeForm> list = new ArrayList<LotteryTypeForm>();
					LotteryTypeForm ltf = new LotteryTypeForm();
					DozermapperUtil.getInstance().map(lottery, ltf);
					list.add(ltf);
					groupMap.put(group, list);
				}else{
					LotteryTypeForm ltf = new LotteryTypeForm();
					DozermapperUtil.getInstance().map(lottery, ltf);
					groupMap.get(group).add(ltf);
				}
			}
			
			voList = new ArrayList<LotteryListVOWebApp>();
			for(String groupKey : groupMap.keySet()){
				LotteryListVOWebApp listVO = new LotteryListVOWebApp();
				listVO.setLotteryGroup(groupKey);
				listVO.setLotteryGroupName(CommonUtil.lotteryGroupMap.get(groupKey));
				listVO.setLotteryList(groupMap.get(groupKey));
				listVO.setRsvst1(groupMap.get(groupKey).get(0).getRsvst1());
				voList.add(listVO);
			}
			
			//用彩种的rsvst1字段作为排序依据
			Compare2 cp2 = new Compare2();
			Collections.sort(voList, cp2);
			
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		return voList;
	}
	
	/**
	 * 彩种排序根据备用字段排序
	 * @author CW-HP9
	 *
	 */
	class Compare2 implements Comparator<LotteryListVOWebApp>{
			@Override
			public int compare(LotteryListVOWebApp o1, LotteryListVOWebApp o2) {
				if(StringUtils.isEmpty(o1.getRsvst1())){
					o1.setRsvst1("9999");
				}
				if(StringUtils.isEmpty(o2.getRsvst1())){
					o2.setRsvst1("9999");
				}
				int code1 = Integer.parseInt(o1.getRsvst1());
				int code2 = Integer.parseInt(o2.getRsvst1());
				if(code1>code2){
					return 1;
				}else if(code1<code2){
					return -1;
				}
				return 0;
			}
	}
	/**
	 * 帐号安全
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("showAccountSafes")
	@Token(needSaveToken = true)
	public ModelAndView showAccountSafe(HttpServletRequest request,
			HttpServletResponse reponse) {
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(
				CommonUtil.CUSTOMERUSERKEY);
		return new ModelAndView("jspmp/user/account_security", model);
	}
	
	/**
	 * 注销
	 * @param request
	 * @param response
	 * @param reAttributes
	 * @return
	 */
	@RequestMapping("/loginDropOut")
	public ModelAndView loginout(HttpServletRequest request, HttpServletResponse response,RedirectAttributes reAttributes){
		HttpSession session = request.getSession();
		session.invalidate();
		return new ModelAndView("redirect:showloginmp.html");
	}
}
