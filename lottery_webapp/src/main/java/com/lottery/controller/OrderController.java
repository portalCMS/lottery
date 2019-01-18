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
import com.lottery.bean.entity.CustomerOrder;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.LotteryType;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.PlayModel;
import com.lottery.bean.entity.vo.CustomerOrderVO;
import com.lottery.bean.entity.vo.TempMapVO;
import com.lottery.service.IBetRecordService;
import com.lottery.service.ICustomerOrderService;
import com.lottery.service.IDataDictionaryService;
import com.lottery.service.ILotteryTypeService;
import com.lottery.service.IPlayModelService;
import com.lottery.staticvalue.CommonStatic;
import com.xl.lottery.desutil.AesUtil;
import com.xl.lottery.desutil.Md5Manage;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;


@Controller
@RequestMapping("order")
public class OrderController extends BaseController{
	
	@Autowired
	private ICustomerOrderService orderService;
	
	@Autowired
	private IDataDictionaryService dataService;
	
	@Autowired
	private IBetRecordService betRecordService;

	@Autowired
	private ILotteryTypeService lotteryTypeService;
	
	@Autowired
	private IPlayModelService playModelService;
	
	/**
	 * 展示订单查询页面
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("showQueryOrder")
	public ModelAndView showQueryOrder(HttpServletRequest request,HttpServletResponse reponse){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		
		try {
			List<LotteryType> types = lotteryTypeService.queryLotteryTypeAll(param);
			model.put("lotteryTypes", types);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("team/team_orders",model);
	}
	
	/**
	 * 展示盈收统计页面
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("showRevenue")
	public ModelAndView showRevenue(HttpServletRequest request,HttpServletResponse reponse){
		return new ModelAndView("team/revenue_statistics");
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
		if(StringUtils.isEmpty(vo.getRsvst4())){
			param.put("revenueKey", true);
		}else{
			param.put("revenueKey", false);
		}
		
		CustomerUser curUser = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		param.put(CommonUtil.CUSTOMERUSERKEY, curUser);
		
		Page<CustomerOrderVO, CustomerOrder> orders = null;
		try {
			orders = orderService.queryTeamOrdersByPage(param);
			model.put("orders", orders);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	/**
	 * 中奖返款订单明细
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("winningRebateDetail")
	public ModelAndView winningRebateDetail(CustomerOrderVO vo,RedirectAttributes attrs,
			HttpServletRequest request,HttpServletResponse reponse){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("ordIdKey", vo.getId());
		CustomerOrder order = null;
		String fromUrl = request.getHeader("Referer");
		model.put("fromUrl", fromUrl);
		// 用于页面标记选中左侧菜单。
		model.put("fromType", vo.getFromType());
		if(fromUrl.indexOf("finance")!=-1){
			model.put("menu", "profile");
		}else{
			model.put("menu", "team");
		}
		
		try {
			order = orderService.queryOrderById(param);
			param.put("orderNo", order.getRsvst1());
			this.queryTraceDetail(order, request, reponse, model, param);
			model.put("order", order);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, attrs);
			return new ModelAndView("redirect:showQueryOrder.html");
		}
		return new ModelAndView("orders/zjfk_order_detail",model);
	}
	
	private Map<String, Object> queryTraceDetail(CustomerOrder order, HttpServletRequest request,
			HttpServletResponse reponse, Map<String, Object> model, Map<String, Object> param) {
		try {
			List<BetRecord> betRecord = betRecordService.queryBetRecord(param);

			List<TempMapVO> tempvos = new ArrayList<TempMapVO>();
			param.put("lotteryCodeKey", betRecord.get(0).getLotteryCode());
			List<PlayModel> playModel = playModelService.queryPlayModelByLotteryCode(param);
			String temp = betRecord.get(0).getIssueNo();
			Map<String, BetRecord> temps = new HashMap<String, BetRecord>();
			List<BetRecord> brs = new ArrayList<BetRecord>();
			for (BetRecord br : betRecord) {
				if (br.getIssueNo().equals(temp)) {
					TempMapVO tempVo = new TempMapVO();
					for (PlayModel pm : playModel) {
						if (pm.getModelCode().equals(br.getPlayCode())) {
							tempVo.setKey(pm.getModelName());
							break;
						}
					}
					if (br.getBileNum().equals("")) {
						tempVo.setValue(AesUtil.decrypt(br.getBetNum(),
								Md5Manage.getInstance().getMd5() + br.getTempString()));
					} else {
						tempVo.setValue("胆码:"
								+ AesUtil.decrypt(br.getBileNum(),
										Md5Manage.getInstance().getMd5() + br.getTempString())
								+ "托码:"
								+ AesUtil.decrypt(br.getBetNum(), Md5Manage.getInstance().getMd5() + br.getTempString()));
					}

					if (br.getBetModel().doubleValue() == 1.0000) {
						tempVo.setValue2("元模式");
					} else if (br.getBetModel().doubleValue() == 0.1000) {
						tempVo.setValue2("角模式");
					} else {
						tempVo.setValue2("分模式");
					}

					tempvos.add(tempVo);
				}
				if (temps.get(br.getIssueNo()) == null) {
					temps.put(br.getIssueNo(), br);
					brs.add(br);
				} else {
					// 追期明细需要判断该期是否有中奖记录，有的话更新br的status。
					if (br.getBetStatus() == DataDictionaryUtil.BET_ORDER_TYPE_WIN) {
						brs.get(brs.size() - 1).setBetStatus(br.getBetStatus());
					}
				}
			}
			Map<String, Object> codeMap = CommonStatic.getCodeMap();
			String lotteryName = (String) codeMap
					.get(CommonStatic.LOTTERYTYPE_HEAD + betRecord.get(0).getLotteryCode());
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
	 * 投注返款(下级投注上级盈收)订单明细
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("betRebateDetail")
	public ModelAndView betRebateDetail(CustomerOrderVO vo,RedirectAttributes attrs,
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
			return new ModelAndView("redirect:showQueryOrder.html");
		}
		return new ModelAndView("orders/tzys_order_detail",model);
	}
	
	/**
	 * 投注返款(下级投注上级盈收)订单明细
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("cancelOrder")
	public ModelAndView cancelOrder(CustomerOrderVO vo,RedirectAttributes attrs,
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
			model.put("fromType", vo.getFromType());
			model.put("order", order);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, attrs);
			return new ModelAndView("redirect:showQueryOrder.html");
		}
		return new ModelAndView("orders/cancel_order_detail",model);
	}
	
	/**
	 * 自己的投注返款订单明细
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("myBetRebateDetail")
	public ModelAndView myBetRebateDetail(CustomerOrderVO vo,RedirectAttributes attrs,
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
			model.put("fromType", vo.getFromType());
			param.put("orderNo", order.getRsvst1());
			this.queryTraceDetail(order, request, reponse, model, param);
			model.put("order", order);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, attrs);
			return new ModelAndView("redirect:showQueryOrder.html");
		}
		return new ModelAndView("orders/tzfd_order_detail",model);
	}
	
	/**
	 * 现金提款订单明细
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("cashDrawingDetail")
	public ModelAndView cashDrawingDetail(CustomerOrderVO vo,RedirectAttributes attrs,
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
			order = orderService.queryDrawingOrderById(param);
			model.put("order", order);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, attrs);
			return new ModelAndView("redirect:showQueryOrder.html");
		}
		return new ModelAndView("orders/tk_order_detail",model);
	}
	
	/**
	 * 普通投注订单明细
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("betOrderDetail")
	public ModelAndView betOrderDetail(CustomerOrderVO vo,RedirectAttributes attrs,
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
			//彩种名称
			param.put("orderNo", order.getOrderNumber());
			List<BetRecord> betRecords = betRecordService.queryBetRecord(param);
			order.setRsvst2(CommonStatic.getCodeMap().get(CommonStatic.LOTTERYTYPE_HEAD+betRecords.get(0).getLotteryCode()).toString());
			order.setRsvst3(betRecords.get(0).getIssueNo());
			model.put("order", order);
			model.put("betRecords",betRecords);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, attrs);
			return new ModelAndView("redirect:showQueryOrder.html");
		}
		return new ModelAndView("orders/pttz_order_detail",model);
	}
}
