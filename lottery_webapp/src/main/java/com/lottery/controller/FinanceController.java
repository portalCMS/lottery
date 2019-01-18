package com.lottery.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lottery.bean.entity.BetRecord;
import com.lottery.bean.entity.CustomerCash;
import com.lottery.bean.entity.CustomerOrder;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.LotteryType;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.PlayModel;
import com.lottery.bean.entity.TaskConfig;
import com.lottery.bean.entity.UserCard;
import com.lottery.bean.entity.vo.BetRecordVO;
import com.lottery.bean.entity.vo.CustomerOrderVO;
import com.lottery.bean.entity.vo.LotteryTypeVO;
import com.lottery.bean.entity.vo.PlayModelVO;
import com.lottery.bean.entity.vo.TempMapVO;
import com.lottery.service.IBetRecordService;
import com.lottery.service.ICustomerCashService;
import com.lottery.service.ICustomerOrderService;
import com.lottery.service.ICustomerUserService;
import com.lottery.service.ILotteryAwardRecordService;
import com.lottery.service.ILotteryTypeService;
import com.lottery.service.IPlayModelService;
import com.lottery.service.ITaskConfigService;
import com.lottery.service.IUserCardService;
import com.lottery.staticvalue.CommonStatic;
import com.xl.lottery.desutil.AesUtil;
import com.xl.lottery.desutil.Md5Manage;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;


@Controller
@RequestMapping("finance")
public class FinanceController extends BaseController{
	@Autowired
	private ITaskConfigService taskService;
	
	@Autowired
	private ICustomerCashService cashService;
	
	@Autowired
	private ICustomerOrderService orderService;
	
	@Autowired
	private ILotteryTypeService lotteryTypeService;
	
	@Autowired
	private IPlayModelService playModelService;
	
	@Autowired
	private IBetRecordService betRecordService;
	
	@Autowired
	private ILotteryAwardRecordService arservice;
	
	@Autowired
	private IUserCardService userCardService;
	
	@Autowired
	private ICustomerUserService userService;
	
	/**
	 * 展示财务明细页面
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("showFinanceDetail")
	public ModelAndView showFinanceDetail(HttpServletRequest request,HttpServletResponse reponse){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		param.put("userId",user.getId());
		CustomerCash cash = null;
		try {
			cash = cashService.findCustomerCashByUserId(param);
			// 菜种集合。
			model.put("lotteryTypes", lotteryTypeService.queryLotteryTypeAll(param));
			model.put("cash", cash);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("profile/financial_detail",model);
	}
	
	/**
	 * 展示我的追号记录页面
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("showMyBetRecord")
	public ModelAndView showMyBetRecord(HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<LotteryType> types = lotteryTypeService.queryLotteryTypeAll(param);
			model.put("types", types);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("profile/my_bet",model);
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
			param.put(CommonUtil.CUSTOMERUSERKEY, request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY));
			Page<BetRecordVO,BetRecord> page = betRecordService.queryBetRecordsWebAppBySelf(param);
			model.put("page", page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("getPlayModelsAndIssueNo")
	@ResponseBody
	public Map<String, ?> getPlayModelsAndIssueNoByLotteryCode(String lotteryCode, HttpServletRequest request,
			HttpServletResponse reponse) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			param.put("lotteryCodeKey", lotteryCode);
			List<PlayModelVO> playModels = playModelService.queryPlayModelVoByLotteryCode(param);
			LotteryTypeVO lotteryTypeVO = new LotteryTypeVO();
			lotteryTypeVO.setLotteryCode(lotteryCode);
			param.put("lotteryKey", lotteryTypeVO);
			TaskConfig config = taskService.queryCurrentTask(param);
			if (config != null) {
				lotteryTypeVO.setCurrentIssueNo(config.getLotterySeries());
			} else {
				lotteryTypeVO.setCurrentIssueNo("");
			}
			lotteryTypeVO.setPlayModelVOs(playModels);
			model.put("lotteryType", lotteryTypeVO);
		} catch (Exception e) {
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
	
	@RequestMapping("showAwardStopDetail")
	public ModelAndView showAwardStopDetail(BetRecordVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		String fromUrl = request.getHeader("Referer");
		if(vo.getId()==0){
			model.put("error", "记录不存在");
			if(fromUrl==null)fromUrl = PERSONALCENTERURL;
			return new ModelAndView(fromUrl,model);
		}
		model.put("fromUrl", fromUrl);
		if(fromUrl.indexOf("finance")!=-1){
			model.put("menu", "profile");
		}else{
			model.put("menu", "team");
		}
		this.queryTraceDetail(vo, request, reponse,model,param);
		
		return new ModelAndView("orders/award_stop_order_detail",model);
	}
	/**
	 * 展示追号订单页面
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("showMyTraceBetRecord")
	public ModelAndView showMyTraceBetRecord(HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<LotteryType> types = lotteryTypeService.queryLotteryTypeAll(param);
			model.put("types", types);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("profile/my_trace_bet",model);
	}
	
	@RequestMapping("queryTraceOrders")
	@ResponseBody
	public Map<String,?> queryTraceOrders(CustomerOrderVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			param.put("ordersKey", vo);
			param.put(CommonUtil.CUSTOMERUSERKEY, request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY));
			Page<CustomerOrderVO, CustomerOrder> page = orderService.queryTraceOrders(param);
			model.put("orders", page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	/**
	 * 查询追号详情
	 * @param vo
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("queryTraceInfo")
	public ModelAndView queryTraceOrdersDetail(BetRecordVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		String fromUrl = request.getHeader("Referer");
		if(vo.getId()==0){
			model.put("error", "记录不存在");
			if(fromUrl==null)fromUrl = PERSONALCENTERURL;
			return new ModelAndView(fromUrl,model);
		}
		model.put("fromUrl", fromUrl);
		if(fromUrl.indexOf("finance")!=-1){
			model.put("menu", "profile");
		}else{
			model.put("menu", "team");
		}
		this.queryTraceDetail(vo, request, reponse,model,param);
		//标识是从财务明细过来的查询追号详情，还是追号记录
		model.put("fromType", vo.getFromType());
		return new ModelAndView("orders/lottery_trace_order_detail",model);
	}
	
	private Map<String, Object> queryTraceDetail(BetRecordVO vo,HttpServletRequest request,HttpServletResponse reponse
					,Map<String, Object> model,Map<String, Object> param){
		try {
			param.put("ordIdKey", vo.getId());
			CustomerOrder order = orderService.queryOrderById(param);
			if(order.getOrderDetailType()==DataDictionaryUtil.ORDER_DETAIL_CHASE_AFTER_REBATES){
				param.put("orderNo", order.getRsvst1());
				param.put("betStatusKey", DataDictionaryUtil.BET_ORDER_TYPE_CANCEL);
			}else{
				param.put("orderNo", order.getOrderNumber());
			}
			param.put("orderKey", order);
			List<BetRecord> betRecord = betRecordService.queryBetRecordsByOrderNo(param);
			
			
			List<TempMapVO> tempvos = new ArrayList<TempMapVO>();
			param.put("lotteryCodeKey",betRecord.get(0).getLotteryCode());
			List<PlayModel> playModel = playModelService.queryPlayModelByLotteryCode(param);
			String temp = betRecord.get(0).getIssueNo();
			Map<String,BetRecord> temps = new HashMap<String,BetRecord>();
			List<BetRecord> brs = new ArrayList<BetRecord>();
			for(BetRecord br : betRecord){
				if(br.getIssueNo().equals(temp)){
					TempMapVO tempVo = new TempMapVO();
					for(PlayModel pm : playModel){
						if(pm.getModelCode().equals(br.getPlayCode())){
							tempVo.setKey(pm.getModelName());
							break;
						}
					}
					if(br.getBileNum().equals("")||StringUtils.isBlank(br.getBileNum())){
						tempVo.setValue(AesUtil.decrypt(br.getBetNum(), Md5Manage.getInstance().getMd5()+br.getTempString()));
					}else{
						tempVo.setValue("胆码:"+AesUtil.decrypt(br.getBileNum(), Md5Manage.getInstance().getMd5()+br.getTempString())+"托码:"+AesUtil.decrypt(br.getBetNum(), Md5Manage.getInstance().getMd5()+br.getTempString()));
					}
					
					if(br.getBetModel().doubleValue()==1.0000){
						tempVo.setValue2("元模式");
					}else if(br.getBetModel().doubleValue()==0.1000){
						tempVo.setValue2("角模式");
					}else{
						tempVo.setValue2("分模式");
					}
					
					tempvos.add(tempVo);
				}
				if(temps.get(br.getIssueNo())==null){
					temps.put(br.getIssueNo(),br);
					brs.add(br);
				}else{
					//追期明细需要判断该期是否有中奖记录，有的话更新br的status。
					BetRecord oldBr = temps.get(br.getIssueNo());
					if(br.getBetStatus()==DataDictionaryUtil.BET_ORDER_TYPE_WIN){
						brs.get(brs.size()-1).setBetStatus(br.getBetStatus());
					}
				}
			}
			Map<String,Object> codeMap = CommonStatic.getCodeMap();
			String lotteryName = (String) codeMap.get(CommonStatic.LOTTERYTYPE_HEAD+betRecord.get(0).getLotteryCode());
			model.put("order", order);
			model.put("tempvos", tempvos);
			model.put("lotteryName", lotteryName);
			model.put("brs", brs);
			model.put("lotCode", brs.get(0).getLotteryCode());
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	/**
	 * 充值订单明细
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("rcOrderDetail")
	public ModelAndView rcOrderDetail(CustomerOrderVO vo,RedirectAttributes attrs,
			HttpServletRequest request,HttpServletResponse reponse){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("ordIdKey", vo.getId());
		String fromUrl = request.getHeader("Referer");
		model.put("fromUrl", fromUrl);
		if(fromUrl.indexOf("finance")!=-1){
			model.put("menu", "profile");
		}else{
			model.put("menu", "team");
		}
		try {
			CustomerOrder order = orderService.queryOrderById(param);
			if(order.getOrderDetailType()!=DataDictionaryUtil.ORDER_DETAIL_DISTRIBUTED_ACTIVITIES){
				param.put("cardIdKey", order.getSourceId());
				Long cid = (Long) param.get("cardIdKey");
				if(order.getOrderDetailType() != DataDictionaryUtil.ORDER_DETAIL_OTHER_PAY && cid!=null && cid != 0){
					UserCard card = userCardService.queryUserCardById(param);
					card.setCardNo(card.getCardNo().substring(0,4)+" **** **** **** "
								+card.getCardNo().substring(card.getCardNo().length()-3,card.getCardNo().length()));
					model.put("card", card);
				}
			}
			model.put("order", order);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, attrs);
			return new ModelAndView("redirect:showFinanceDetail.html");
		}
		return new ModelAndView("orders/cz_order_detail",model);
	}
	
	/**
	 * 资金转入转出订单明细
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("zjOrderDetail")
	public ModelAndView zjOrderDetail(CustomerOrderVO vo,RedirectAttributes attrs,
			HttpServletRequest request,HttpServletResponse reponse){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("ordIdKey", vo.getId());
		String fromUrl = request.getHeader("Referer");
		model.put("fromUrl", fromUrl);
		if(fromUrl.indexOf("finance")!=-1){
			model.put("menu", "profile");
		}else{
			model.put("menu", "team");
		}
		try {
			CustomerOrder order = orderService.queryOrderById(param);
			//查询用户信息
			CustomerUser getUser = userService.queryUserById(order.getCustomerId());
			CustomerUser fromUser = userService.queryUserById(order.getFromCustomerId());
			order.setRsvst1(getUser.getCustomerName());
			order.setRsvst2(fromUser.getCustomerName());
			model.put("order", order);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, attrs);
			return new ModelAndView("redirect:showFinanceDetail.html");
		}
		String pageUrl = "orders/zjzr_order_detail";
		if(!StringUtils.isEmpty(vo.getFromType())&&vo.getFromType().equals("zjzc")){
			pageUrl = "orders/zjzc_order_detail";
		}
		return new ModelAndView(pageUrl,model);
	}
	
	/**
	 * 方案详情
	 * @param vo
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("queryPlanInfo")
	public ModelAndView queryPlanInfo(BetRecordVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			param.put("orderNo", vo.getOrderNo());
			List<BetRecord> betRecord = betRecordService.queryBetRecordsByOrderNo(param);
			param.put("lotteryCodeKey",betRecord.get(0).getLotteryCode());
			List<PlayModel> playModel = playModelService.queryPlayModelByLotteryCode(param);
			List<BetRecord> brs = new ArrayList<BetRecord>();
			String temp = vo.getIssueNo();
			for(BetRecord br : betRecord){
				if(br.getIssueNo().equals(temp)){
					if(br.getBileNum().equals("")){
						br.setBetNum(AesUtil.decrypt(br.getBetNum(), Md5Manage.getInstance().getMd5()+br.getTempString()));
					}else{
						br.setBileNum(AesUtil.decrypt(br.getBileNum(), Md5Manage.getInstance().getMd5()+br.getTempString()));
						br.setBetNum(AesUtil.decrypt(br.getBetNum(), Md5Manage.getInstance().getMd5()+br.getTempString()));
					}
					for(PlayModel pm : playModel){
						if(pm.getModelCode().equals(br.getPlayCode())){
							br.setPlayCode(pm.getModelName());
						}
					}
					brs.add(br);
				}
			}
			param.put("lotteryCode",brs.get(0).getLotteryCode());
			param.put("issue",vo.getIssueNo());
			String lotteryName = lotteryTypeService.queryLotteryTypeNameByCode(betRecord.get(0).getLotteryCode());
			String openNumber = arservice.queryOpenbetNumberbylotteryCodeAndissue(param);
			model.put("orderNo", brs.get(0).getOrderNo());
			model.put("brs", brs);
			model.put("issueNo", brs.get(0).getIssueNo());
			model.put("openNumber", openNumber);
			model.put("lotteryName", lotteryName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("orders/lottery_plan_detail",model);
	}
	
	/**
	 * 方案详情
	 * @param vo
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("queryIssueBets")
	@ResponseBody
	public Map<String, Object> queryIssueBets(BetRecordVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			param.put("orderNo", vo.getOrderNo());
			List<BetRecord> betRecord = betRecordService.queryBetRecordsByOrderNo(param);
			param.put("lotteryCodeKey",betRecord.get(0).getLotteryCode());
			List<BetRecord> brs = new ArrayList<BetRecord>();
			String temp = vo.getIssueNo();
			Map<String,Object> codeMap = CommonStatic.getCodeMap();
			for(BetRecord br : betRecord){
				if(br.getIssueNo().equals(temp)){
					if(br.getBileNum().equals("")){
						br.setBetNum(AesUtil.decrypt(br.getBetNum(), Md5Manage.getInstance().getMd5()+br.getTempString()));
					}else{
						br.setBileNum(AesUtil.decrypt(br.getBileNum(), Md5Manage.getInstance().getMd5()+br.getTempString()));
						br.setBetNum(AesUtil.decrypt(br.getBetNum(), Md5Manage.getInstance().getMd5()+br.getTempString()));
					}
					br.setPlayCode((String)codeMap.get(CommonStatic.PLAYMODEL_HEAD+br.getPlayCode()));
					br.setSelectCode((String) codeMap.get(CommonStatic.LOTTERYPLAYSELECT_HEAD+br.getSelectCode()));
					brs.add(br);
				}
			}
			param.put("lotteryCode",brs.get(0).getLotteryCode());
			param.put("issue",vo.getIssueNo());
			String lotteryName = (String)codeMap.get(CommonStatic.LOTTERYTYPE_HEAD+betRecord.get(0).getLotteryCode());
			String openNumber = arservice.queryOpenbetNumberbylotteryCodeAndissue(param);
			model.put("orderNo", brs.get(0).getOrderNo());
			model.put("brs", brs);
			model.put("issueNo", brs.get(0).getIssueNo());
			model.put("openNumber", openNumber);
			model.put("lotteryName", lotteryName);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	/**
	 * 查询对应的订单记录
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryOrder")
	@ResponseBody
	public Map<String,?> queryOrder(CustomerOrderVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("ordersKey", vo);
		
		CustomerUser curUser = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		param.put(CommonUtil.CUSTOMERUSERKEY, curUser);
		Page<CustomerOrderVO, CustomerOrder> orders = null;
		try {
			orders = orderService.queryMyOrdersByPage(param);
			model.put("orders", orders);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}

	/**
	 * 展示个人盈亏页面
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("showPersonalFinancialPage")
	public ModelAndView showPersonalFinancePage(HttpServletRequest request, HttpServletResponse reponse) {
		return new ModelAndView("profile/personal_financial_list", new HashMap<String, Object>());
	}

	/**
	 * 查询个人盈亏记录列表。
	 * 
	 * @param customerOrder
	 *            个人订单VO
	 * @param request
	 * @param response
	 * @return 返回符合条件的个人盈亏记录列表。
	 */	
	@RequestMapping("/queryPersonalFinancialList")
	@ResponseBody
	public Map<String, ?> queryPersonalFinancialList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		// 获取个人信息
		CustomerUser curUser = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		param.put("customerUser", curUser);
		param.put("sdate", request.getParameter("sdate"));
		param.put("edate", request.getParameter("edate"));
		try {
			if(request.getParameter("queryType").equals("1")){
				model.put("orders", orderService.queryHistoryYkRecords(param));
			}else if(request.getParameter("queryType").equals("0")){
				model.put("orders", orderService.queryDayYkRecords(param));
			}else{
				model.put("orders", orderService.queryHistoryYkExport(param));
			}
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}

	/**
	 * 查询用户从下级获得的盈收统计
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryRevenueLower")
	@ResponseBody
	public Map<String,?> queryRevenueLower(CustomerOrderVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("ordersKey", vo);
		
		CustomerUser curUser = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		param.put("userIdKey",curUser.getId());
		param.put(CommonUtil.CUSTOMERUSERKEY, curUser);
		Page<CustomerOrderVO, CustomerOrder> orders = null;
		try {
			orders = orderService.queryRevenueLower(param);
			model.put("orders", orders);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("cancelPlan")
	@ResponseBody
	public Map<String, ?> cancelPlanInfo(BetRecordVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("brvo", vo);
		param.put(CommonUtil.CUSTOMERUSERKEY, request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY));
		try {
			betRecordService.cancelPlanInfo(param);
			model.put("success", "方案撤销成功");
			model.put("betId", vo.getId());
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("showrcbrdata")
	@ResponseBody
	public Map<String,?> showRecentBetRecordData(BetRecordVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			param.put("berRecordKey", vo);
			param.put(CommonUtil.CUSTOMERUSERKEY, request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY));
			Page<BetRecordVO,BetRecord> page = betRecordService.queryBetRecordsWebAppBySelf(param);
			for(BetRecordVO tempVo : page.getPagelist()){
				param.put("lotteryCode",tempVo.getLotteryCodeCopy());
				param.put("issue",tempVo.getIssueNo());
				String openNumber = arservice.queryOpenbetNumberbylotteryCodeAndissue(param);
				tempVo.setOpernBetNumber(openNumber);
			}
			
			model.put("page", page);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("queryCurIssue")
	@ResponseBody
	public Map<String, ?> queryCurIssue(LotteryTypeVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("lotteryKey", vo);
		TaskConfig task =null;
		try {
			task = taskService.queryCurrentTask(param);
			model.put("issueNo", task.getLotterySeries());
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	
	/**
	 * 自己的投注返款订单明细
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("myFeeRefundDetail")
	public ModelAndView myFeeRefundDetail(CustomerOrderVO vo,RedirectAttributes attrs,
			HttpServletRequest request,HttpServletResponse reponse){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("ordIdKey", vo.getId());
		CustomerOrder order = null;
		String fromUrl = request.getHeader("Referer");
		model.put("fromUrl", fromUrl);
		if(fromUrl.indexOf("finance")!=-1){
			model.put("menu", "profile");
		}else{
			model.put("menu", "team");
		}
		try {
			order = orderService.queryOrderById(param);
			model.put("order", order);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, attrs);
			return new ModelAndView("redirect:showFinanceDetail.html");
		}
		return new ModelAndView("orders/czfk_order_detail",model);
	}
	@RequestMapping("showLotteryTypeDetail")
	public ModelAndView myFeeRefundDetail(RedirectAttributes attrs, HttpServletRequest request,
			HttpServletResponse reponse) {
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		param.put("userId",user.getId());
		LotteryTypeVO lotteryVo = new LotteryTypeVO();
		lotteryVo.setLotteryStatus(DataDictionaryUtil.STATUS_OPEN);
		lotteryVo.setLotteryLevel(DataDictionaryUtil.COMMON_FLAG_1);
		param.put("lotteryKey",lotteryVo);
		try {
			model.put("lotteryGroups", lotteryTypeService.getLotteryGroups(param));
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e,model);
		}
		return new ModelAndView("profile/lottery_type_info", model);
	}
}
