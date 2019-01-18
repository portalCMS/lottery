package com.lottery.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cwx.lottery.form.vo.LowerManagerVO;
import com.lottery.annotation.Token;
import com.lottery.bean.entity.BetRecord;
import com.lottery.bean.entity.CustomerQuota;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.LotteryType;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.PlayModel;
import com.lottery.bean.entity.vo.BetRecordVO;
import com.lottery.bean.entity.vo.CustomerCashVO;
import com.lottery.bean.entity.vo.CustomerOrderStaVo;
import com.lottery.bean.entity.vo.CustomerOrderVO;
import com.lottery.bean.entity.vo.CustomerQuotaVO;
import com.lottery.bean.entity.vo.CustomerUserVO;
import com.lottery.bean.entity.vo.ReportVO;
import com.lottery.service.IBetRecordService;
import com.lottery.service.ICustomerCashService;
import com.lottery.service.ICustomerQuotaService;
import com.lottery.service.ICustomerUserService;
import com.lottery.service.ILotteryAwardRecordService;
import com.lottery.service.ILotteryTypeService;
import com.lottery.service.IPlayModelService;
import com.lottery.service.IReportService;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.MD5Util;

@Controller
@RequestMapping("team")
public class TeamController extends BaseController{
	
	@Autowired
	private ICustomerUserService userService;
	
	@Autowired
	private ICustomerQuotaService customerQuotaService;
	
	@Autowired
	private ILotteryTypeService lotteryTypeService;
	
	@Autowired
	private IPlayModelService playModelService;
	
	@Autowired
	private IBetRecordService betRecordService;
	
	@Autowired
	private ILotteryAwardRecordService arservice;
	
	@Autowired
	private ICustomerCashService cashService;
	
	@Autowired
	private IReportService reportService;
	
	@RequestMapping("show")
	public ModelAndView showManager(CustomerUserVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("team/team_list",model);
	}
	
	@RequestMapping("showTeamInfo")
	@ResponseBody
	public Map<String,Object> queryTeamInfo(CustomerUserVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
			String[] ids = vo.getIdsTree().split(",");
			param.put("uservokey", vo);
			if(ids.length == 0 || ids[0].equals("")){
				param.put("userId", user.getId());
				vo.setId(user.getId());
//				CustomerCash cash = cashService.findCustomerCashByUserId(param);
				ids[0] = Long.toString(user.getId());
				param.put("ids", ids);
				List<Object> breads = userService.queryTeamMoneyInfo(param);
				Page<CustomerUserVO, Object> page = userService.queryLowerLevels(param);
//				model.put("cash", cash);
				model.put("breads", breads);
				model.put("page", page);
			}else{
				if(vo.getId()!=0){
					//ids[ids.length] = Long.toString(vo.getId());
					String[] idsnew = new String[ids.length+1];
					for(int i=0;i<idsnew.length;i++){
						if(i+1==idsnew.length){
							idsnew[i] =  Long.toString(vo.getId());
							break;
						}
						idsnew[i] = ids[i];
					}
					param.put("userId", vo.getId());
					param.put("ids", idsnew);
				}else{
					param.put("userId", Long.parseLong(ids[ids.length-1]));
					param.put("ids", ids);
				}
				//CustomerCash cash = cashService.findCustomerCashByUserId(param);
				List<Object> breads = userService.queryTeamMoneyInfo(param);
				Page<CustomerUserVO, Object> page = userService.queryLowerLevels(param);
				model.put("breads", breads);
				model.put("page", page);
			}
			model.put("team", userService.queryTeamNum(String.valueOf(user.getId())));
			model.put("user", user);
			
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	/**
	 * 根据查询条件查询团队成员信息
	 * @param vo
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("queryTeamUserByKey")
	@ResponseBody
	public Map<String,Object> queryTeamUserByKey(CustomerUserVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
			param.put("uservokey", vo);
			param.put(CommonUtil.CUSTOMERUSERKEY, user);
			Page<CustomerUserVO, Object> page = userService.queryTeamUserByKey(param);
			model.put("page", page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("showqm")
	@Token(needSaveToken=true)
	public ModelAndView showQuotaManage(HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		try {
			List<CustomerUser> lowerlist = userService.findLowerLevelCustomerUser(param);
			List<CustomerQuota> quotalist = customerQuotaService.findCustomerUser(param);
			model.put("lowerlist", lowerlist);
			model.put("quotalist", quotalist);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("team/quota_manager",model); 
	}
	
	@SuppressWarnings("unused")
	@RequestMapping("changeUserQuota")
	@Token(needRemoveToken=true)
	public ModelAndView changeUserQuota(CustomerQuotaVO quotavo,HttpServletRequest request,HttpServletResponse reponse,RedirectAttributes rAttributes){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		param.put("customerquotakey", quotavo);
		try {
			String success = customerQuotaService.saveOrUpdateCustomerQuota(param);
			rAttributes.addFlashAttribute("success", "操作成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, rAttributes);
		}
		return new ModelAndView("redirect:showqm.html",model); 
	}
	
	@RequestMapping("getUserQuotaCount")
	@ResponseBody
	public Map<String, Object> getUserQuotaCount(CustomerQuotaVO quotavo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		CustomerUser cu = new CustomerUser();
		cu.setId(quotavo.getCuId());
		try {
			param.put(CommonUtil.CUSTOMERUSERKEY, cu);
			List<CustomerQuota> list = customerQuotaService.findCustomerUser(param);
			model.put("cq", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("showcreate")
	@Token(needSaveToken=true)
	public ModelAndView showOpenLowerCustomerUser(HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		try {
			List<CustomerQuota> quotalist = customerQuotaService.findCustomerUser(param);
			String tcstepPoint = (String) request.getSession().getServletContext().getAttribute("cstepPoint");
			Double cstepPoint = Double.parseDouble(tcstepPoint);
			String tcminPoint = (String) request.getSession().getServletContext().getAttribute("cminPoint");
			Double cminPoint = Double.parseDouble(tcminPoint);
			if(cminPoint>=user.getRebates().doubleValue()*100){
				cminPoint = user.getRebates().doubleValue();
				cminPoint = cminPoint*1000;
			}else{
				cminPoint = cminPoint*10;
			}
			List<Integer> qtQuota = new ArrayList<Integer>();
			cstepPoint = cstepPoint*10;
			for(int i=cminPoint.intValue()-cstepPoint.intValue();i>=0;){
				qtQuota.add(i);
				i = i - cstepPoint.intValue();
			}
			model.put("quotalist", quotalist);
			model.put("qtQuota", qtQuota);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("team/create_account",model); 
	}
	
	@RequestMapping("getSelfQuotas")
	@ResponseBody
	public Map<String, Object> getQuotas(HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		try {
			List<CustomerQuota> quotalist = customerQuotaService.findCustomerUser(param);
			String tcstepPoint = (String) request.getSession().getServletContext().getAttribute("cstepPoint");
			Double cstepPoint = Double.parseDouble(tcstepPoint);
			String tcminPoint = (String) request.getSession().getServletContext().getAttribute("cminPoint");
			Double cminPoint = Double.parseDouble(tcminPoint);
			List<Integer> qtQuota = new ArrayList<Integer>();
			if(cminPoint>=user.getRebates().doubleValue()*100){
				cminPoint = user.getRebates().doubleValue();
				cminPoint = cminPoint*1000;
			}else{
				cminPoint = cminPoint*10;
			}
			cstepPoint = cstepPoint*10;
			for(int i=cminPoint.intValue()-cstepPoint.intValue();i>=0;){
				qtQuota.add(i);
				i = i - cstepPoint.intValue();
			}
			model.put("quotalist", quotalist);
			model.put("qtQuota", qtQuota);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping("savecreate")
	@Token(needRemoveToken=true)
	@ResponseBody
	public Map<String, ?> openLowerCustomerUser(CustomerUserVO vo,HttpServletRequest request,HttpServletResponse reponse,RedirectAttributes attributes){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
//		if(null!=request.getAttribute("tokenError")){
//			model.put("errorMsg", request.getAttribute("tokenError"));
//			return model;
//		}
		String randPassword = "a123456";
		vo.setCustomerPwd(randPassword);  //设置随机密码
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		param.put("customeruserkey", vo);
		try {
			String succes = userService.saveOpenLowerUser(param);
			model.put("success", "操作成功");
			model.put("pwd", randPassword);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		model.put("token", request.getSession().getAttribute("token"));
		return model; 
	}
	
	@RequestMapping("showbrs")
	public ModelAndView showBetRecords(HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
			param.put("customerId", user.getId());
			List<LotteryType> types = lotteryTypeService.queryLotteryTypeAll(param);
			BigDecimal amount = betRecordService.getLowerLevelSumAmount(param);
			model.put("types", types);
			model.put("amount", amount);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("team/team_lottery",model);
	}
	
	@RequestMapping("getPlayModels")
	@ResponseBody
	public Map<String, ?> getPlayModelsByLotteryCode(String lotteryCode,HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			param.put("lotteryCodeKey",lotteryCode);
			List<PlayModel> playModels = playModelService.queryPlayModelByLotteryCode(param);
			model.put("playModels", playModels);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("showbrdata")
	@ResponseBody
	public Map<String,?> showBetRecordData(BetRecordVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			param.put("berRecordKey", vo);
			param.put("revenueKey", false);
			CustomerOrderVO orderVO = new CustomerOrderVO();
			orderVO.setRsvst3(vo.getuName());
			orderVO.setRsvst2(vo.getLevel());
			orderVO.setRsvst4(vo.getCheckLower());
			param.put("ordersKey", orderVO);
			param.put(CommonUtil.CUSTOMERUSERKEY, request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY));
			Page<BetRecordVO,BetRecord> page = betRecordService.queryBetRecordsWebApp(param);
			model.put("page", page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("showbrdetail")
	public ModelAndView showBetRecordDetailInfo(BetRecordVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String fromUrl = request.getHeader("Referer");
			if(vo.getFromUrl()!=null&&!vo.getFromUrl().equals(""))fromUrl = vo.getFromUrl();
			param.put("betRecordkeyVo",vo);
			if(vo.getId() == 0){
				model.put("error", "记录不存在");
				if(fromUrl==null)fromUrl = PERSONALCENTERURL;
				return new ModelAndView(fromUrl,model);
			}
			model.put("fromUrlName", vo.getUrlName());
			model.put("fromUrl", fromUrl);
			model.put("fromType", vo.getFromType());
			if(fromUrl.indexOf("finance")!=-1){
				model.put("menu", "profile");
			}else{
				model.put("menu", "team");
			}
			BetRecordVO brvo = betRecordService.getBetRecordInfoById(param);
			param.put("lotteryCode", brvo.getLotteryCodeCopy());
			param.put("issue", brvo.getIssueNo());
			String openBetNumber = arservice.queryOpenbetNumberbylotteryCodeAndissue(param);
			brvo.setOpernBetNumber(openBetNumber);
			model.put("brvo", brvo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("orders/lottery_order_detail",model);
	}
	
	@RequestMapping("showlm")
	public ModelAndView showLowerManage(HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("team/lower_manager",model);
	}
	
	@RequestMapping("checklowern")
	@ResponseBody
	public Map<String,Object> checkLowerName(String userName,HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		try {
			param.put(CommonUtil.CUSTOMERUSERKEY, user);
			List<CustomerQuota> quotalist = customerQuotaService.findCustomerUser(param);
			CustomerUser lowerUser = userService.queryUserByName(userName);
			if(lowerUser==null||lowerUser.getCustomerSuperior() != user.getId()){
				model.put("emsg", "该用户本非您的下级");
			}
			model.put("superuser", user);
			model.put("sunuser", lowerUser);
			model.put("cstepPoint", request.getSession().getServletContext().getAttribute("cstepPoint"));
			model.put("cminPoint", request.getSession().getServletContext().getAttribute("cminPoint"));
			model.put("quotalist", quotalist);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("getlowerm")
	@ResponseBody
	public Map<String,Object> giveLowerMonery(LowerManagerVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(
				CommonUtil.CUSTOMERUSERKEY);
		String userMoneyPwd = vo.getMoneryPwd();
		try {
			if (!user.getMoneyPwd().equals(MD5Util.makeMD5(userMoneyPwd))) {
				throw new LotteryException("资金密码错误");
			}
			CustomerCashVO cashvo = new CustomerCashVO();
			param.put("customercashkey", cashvo);
			param.put(CommonUtil.CUSTOMERUSERKEY, user);
			CustomerUser toUser =  userService.queryUserByName(vo.getToUserName());
			cashvo.setCash(new BigDecimal(vo.getToMoney()));
			cashvo.setCuId(toUser.getId());
			cashService.updateCustomerCash(param);
			model.put("success", "操作成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("changelowerq")
	@ResponseBody
	public Map<String, Object> changeLowerQuota(LowerManagerVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(
				CommonUtil.CUSTOMERUSERKEY);
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		param.put("lmvo", vo);
		try {
			userService.updateLowerQuota(param);
			model.put("success", "操作成功");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("queryYKReport")
	public ModelAndView queryYKReport(HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("team/ykreport",model);
	}
	
	@RequestMapping("showYkReport")
	@ResponseBody
	public Map<String, Object> showYkReport(ReportVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		vo.setUid(user.getId());
		param.put(CommonUtil.CUSTOMERUSERKEY, user);
		param.put("reportVO", vo);
		vo.setStartTime(vo.getStartTime()+" 04:00:00");
		vo.setEndTime(vo.getEndTime()+" 04:00:00");
		Page<Object, Object> page =null;
		try {
			if(vo.getRsvst1().equals("1")){
				List<CustomerOrderStaVo> result = betRecordService.queryTeamYkRecords(param);
				model.put("results", result);
			}else if(vo.getRsvst1().equals("2")){
				page = reportService.queryYkReport(param);
				model.put("page", page);
			}else{
				page = reportService.queryYkReport(param);
				model.put("page", page);
			}
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("staTeamInfo")
	@ResponseBody
	public Map<String,Object> staTeamInfo(HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> model = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		try {
			if(request.getParameter("type").equals("1")){
				model.put("DirectAgent",userService.groupDirectSubCustomer(String.valueOf(user.getId())));
			}else{
				model.put("allTeam",userService.groupAllTeamCustomer(String.valueOf(user.getId())));
			}
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
}
