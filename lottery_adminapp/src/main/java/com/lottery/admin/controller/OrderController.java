package com.lottery.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.BetRecord;
import com.lottery.bean.entity.CustomerOrder;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.DataDictionary;
import com.lottery.bean.entity.LotteryType;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.PlayModel;
import com.lottery.bean.entity.vo.BetRecordVO;
import com.lottery.bean.entity.vo.CustomerOrderVO;
import com.lottery.bean.entity.vo.CustomerUserVO;
import com.lottery.bean.entity.vo.LotteryGroupVO;
import com.lottery.service.IBetRecordService;
import com.lottery.service.ICustomerOrderService;
import com.lottery.service.ICustomerUserService;
import com.lottery.service.IDataDictionaryService;
import com.lottery.service.ILotteryPlayModelService;
import com.lottery.service.ILotteryTypeService;
import com.lottery.service.IPlayModelService;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;

@Controller
public class OrderController extends BaseController{

	@Autowired
	private ICustomerOrderService customerOrderService;
	
	@Autowired
	private IDataDictionaryService dataDictionaryService;
	
	@Autowired
	private IBetRecordService betRecordService;
	
	@Autowired
	private ILotteryPlayModelService lotteryPlayModelService;
	
	@Autowired
	private ILotteryTypeService lotteryTypeService;
	
	@Autowired
	private IPlayModelService playModelService;
	
	@Autowired
	private ICustomerUserService userService;
	
	@RequestMapping("showOrders")
	public ModelAndView showOrdersView(CustomerOrderVO orderVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			param.put("fidkey", 17000L);
			List<DataDictionary> orderStatus  = dataDictionaryService.getDataDictionarysByFid(param);
			param.put("fidkey", 18000L);
			List<DataDictionary> orderDetailTypes = dataDictionaryService
					.getDataDictionarysByFid(param);
			Long orderCount = customerOrderService.getAllOrderCount(param);
			model.put("orderStatus", orderStatus);
			model.put("orderDetailTypes", orderDetailTypes);
			model.put("orderCount", orderCount);
		} catch (Exception e) {
			// TODO: handle exception
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("order/orders",model);
	}
	
	@RequestMapping("showRecords")
	public ModelAndView showRecordsView(BetRecordVO betRecordVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<LotteryGroupVO> groups = new ArrayList<LotteryGroupVO>();
			Map<String, String> lotteryGroupMap = CommonUtil.lotteryGroupMap;
			Set<String> keyString = lotteryGroupMap.keySet();
			String groupCodes = "";
			for (String key : keyString) {
				if(groupCodes.equals("")){
					groupCodes = groupCodes.concat(key);
				}else{
					groupCodes = groupCodes.concat(",").concat(key);
				}
				LotteryGroupVO group = new LotteryGroupVO();
				group.setLotteryGroupCode(key);
				group.setLotteryGroupName(lotteryGroupMap.get(key));
				groups.add(group);
			}
			param.put("groupCodeKey", groupCodes);
			List<LotteryType> types = lotteryTypeService.queryLotteryTypeByGroupCode(param);
			List<PlayModel> playModels = playModelService.queryPlayModelByGroupCode(param);
			model.put("groups", groups);
			model.put("types", types);
			model.put("playModels", playModels);
		} catch (Exception e) {
			// TODO: handle exception
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("records/records",model);
	}
	
	@RequestMapping("getBetRecordData")
	@ResponseBody
	public Map<String, ?> getBetRecordData(BetRecordVO betRecordVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String groupCodes = "";
			String types = "";
			String playModels = "";
			Set<String> keyString = CommonUtil.lotteryGroupMap.keySet();
			for (String key : keyString) {
				if (groupCodes.equals("")) {
					groupCodes = groupCodes.concat(key);
				} else {
					groupCodes = groupCodes.concat(",").concat(key);
				}
			}
			if (betRecordVo.getGroupCode().equals("")){
				betRecordVo.setGroupCode(groupCodes);
			}
			param.put("groupCodeKey", groupCodes);
			if(!betRecordVo.getGroupCode().equals("")&&betRecordVo.getLotteryCode().equals("0")){
				List<LotteryType> typelist = lotteryTypeService.queryLotteryTypeByGroupCode(param);
				for(LotteryType type:typelist){
					if(!type.getLotteryGroup().equals(betRecordVo.getGroupCode())&&betRecordVo.getGroupCode().indexOf(",")==-1)continue;
					if (types.equals("")) {
						types = types.concat(type.getLotteryCode());
					} else {
						types = types.concat(",").concat(type.getLotteryCode());
					}
				}
				betRecordVo.setLotteryCode(types);
			}
			if(!betRecordVo.getGroupCode().equals("")&&betRecordVo.getPlayCode().equals("0")){
				List<PlayModel> playModellist = playModelService.queryPlayModelByGroupCode(param);
				for(PlayModel playModel:playModellist){
					if (playModels.equals("")) {
						playModels = playModels.concat(playModel.getModelCode());
					} else {
						playModels = playModels.concat(",").concat(playModel.getModelCode());
					}
				}
				betRecordVo.setPlayCode(playModels);
			}
			if(!betRecordVo.getuName().equals("")){
				CustomerUserVO uservo = new CustomerUserVO();
				uservo.setCustomerName(betRecordVo.getuName());
				param.put(CommonUtil.CUSTOMERUSERKEY, uservo);
				CustomerUser user = userService.findCustomerUserByName(param);
				long userId = -1;
				if(user!=null)userId = user.getId();
				betRecordVo.setCustomerId(userId);
			}
			param.put("betRecordkeyVo", betRecordVo);
			Page<BetRecordVO, BetRecord> page = betRecordService.queryBetRecords(param);
			model.put("betRecordVO", page);
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
	
	@RequestMapping("getTypeAndModelPlay")
	@ResponseBody
	public Map<String,?> getTypeAndPlayModel(String groupCode,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> value = new HashMap<String, Object>();
		param.put("groupCodeKey", groupCode);
		try {
			List<LotteryType> types = lotteryTypeService
					.queryLotteryTypeByGroupCode(param);
			List<PlayModel> playModels = playModelService
					.queryPlayModelByGroupCode(param);
			value.put("types", types);
			value.put("playModels", playModels);
		} catch (Exception e) {
			// TODO: handle exception
			LotteryExceptionLog.wirteLog(e, value);
		}
		return value;
	}
	
	@RequestMapping("getBetRecordInfo")
	@ResponseBody
	public Map<String,?> getBetRecordInfo(BetRecordVO betRecordVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> value = new HashMap<String, Object>();
		try {
			param.put("betRecordkeyVo", betRecordVo);
			BetRecordVO vo = betRecordService.getBetRecordInfoById(param);
			value.put("vo", vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, value);
		}
		return value;
	}
	
	@RequestMapping("detailsInfo")
	public ModelAndView getBetRecordDetails(BetRecordVO betRecordVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			param.put("betRecordkeyVo", betRecordVo);
			BetRecordVO vo = betRecordService.getBetRecordInfoById(param);
			model.put("vo", vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("records/record_detail",model);
	}
	
	@RequestMapping("queryOrders")
	@ResponseBody
	public Map<String, ?> queryOrders(CustomerOrderVO orderVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> objValue = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("customerOrderKey", orderVo);
		Page<CustomerOrderVO, CustomerOrder> orderMaps=null;
		try {
			orderMaps = customerOrderService.queryAllOrderByPage(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e,objValue);
			objValue.put("totalCount", 0);
			objValue.put("pageNum", 0);
			objValue.put("maxCount", 0);
			objValue.put("pageCount", 0);
			return objValue;
		}
		objValue.put("userOrderMaps", orderMaps);
		objValue.put("totalCount", orderMaps.getTotalCount());
		objValue.put("pageNum", orderMaps.getPageNum());
		objValue.put("maxCount", orderMaps.getMaxCount());
		objValue.put("pageCount", orderMaps.getPageCount());
		return objValue;
	}
	
	/**
	 * 撤单 18004 18006
	 * @param orderVo
	 * @param request
	 * @param response
	 * @returnS
	 */
	@RequestMapping(value="showQueryInfocd")
	public ModelAndView queryOrderInfocd(CustomerOrderVO orderVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ordervokey", orderVo);
		try {
			CustomerOrderVO vo = customerOrderService.queryOrderInfoByType(param);
			model.put("ordervo", vo);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("order/details/cd_detail",model);
	}
	
	/**
	 * 充值 18009
	 * @param orderVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="showQueryInfocz")
	public ModelAndView queryOrderInfocz(CustomerOrderVO orderVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ordervokey", orderVo);
		try {
			CustomerOrderVO vo = customerOrderService.queryOrderInfoByType(param);
			model.put("ordervo", vo);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("order/details/cz_detail",model);
	}
	
	/***
	 * 充值返款 18010
	 * @param orderVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="showQueryInfoczfk")
	public ModelAndView queryOrderInfoCZFK(CustomerOrderVO orderVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ordervokey", orderVo);
		try {
			CustomerOrderVO vo = customerOrderService.queryOrderInfoByType(param);
			model.put("ordervo", vo);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("order/details/czfk_detail",model);
	}
	
	/**
	 * 活动派发 18012
	 * @param orderVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="showQueryInfohdpf")
	public ModelAndView queryOrderInfohdpf(CustomerOrderVO orderVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ordervokey", orderVo);
		try {
			CustomerOrderVO vo = customerOrderService.queryOrderInfoByType(param);
			model.put("ordervo", vo);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("order/details/hdpf_detail",model);
	}
	
	/**
	 * 后台充值 18011
	 * @param orderVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="showQueryInfohtcz")
	public ModelAndView queryOrderInfohtcz(CustomerOrderVO orderVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ordervokey", orderVo);
		try {
			CustomerOrderVO vo = customerOrderService.queryOrderInfoByType(param);
			model.put("ordervo", vo);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("order/details/htcz_detail",model);
	}
	
	/**
	 * 后台扣款 18008
	 * @param orderVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="showQueryInfohtkk")
	public ModelAndView queryOrderInfohtkk(CustomerOrderVO orderVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ordervokey", orderVo);
		try {
			CustomerOrderVO vo = customerOrderService.queryOrderInfoByType(param);
			model.put("ordervo", vo);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("order/details/htkk_detail",model);
	}
	
	/**
	 * 提款订单 18007
	 * @param orderVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="showQueryInfotk")
	public ModelAndView queryOrderInfotk(CustomerOrderVO orderVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ordervokey", orderVo);
		try {
			CustomerOrderVO vo = customerOrderService.queryOrderInfoByType(param);
			model.put("ordervo", vo);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("order/details/tk_detail",model);
	}
	
	/**
	 * 投注订单  18001
	 * @param orderVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="showQueryInfotz")
	public ModelAndView queryOrderInfotz(CustomerOrderVO orderVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ordervokey", orderVo);
		try {
			CustomerOrderVO vo = customerOrderService.queryOrderInfoByType(param);
			param.put("orderNo",vo.getOrderNumber());
			List<BetRecordVO> vos = betRecordService.queryBetRecordVOsByOrderNo(param);
			model.put("ordervo", vo);
			model.put("brs", vos);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("order/details/tz_detail",model);
	}
	
	
	/**
	 * 投注返点 18005
	 * @param orderVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="showQueryInfotzfd")
	public ModelAndView queryOrderInfotzfd(CustomerOrderVO orderVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ordervokey", orderVo);
		try {
			CustomerOrderVO vo = customerOrderService.queryOrderInfoByType(param);
			model.put("ordervo", vo);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("order/details/tzfd_detail",model);
	}
	
	/**
	 * 投注盈利 18015
	 * @param orderVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="showQueryInfotzyl")
	public ModelAndView queryOrderInfotzyl(CustomerOrderVO orderVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ordervokey", orderVo);
		try {
			CustomerOrderVO vo = customerOrderService.queryOrderInfoByType(param);
			//CustomerUser user = userService.queryUserById(vo.getCustomerId());
			///////来源订单信息
			CustomerOrderVO ref = new CustomerOrderVO();
			ref.setOrderNumber(vo.getRsvst1());
			param.put("ordervokey", ref);
			CustomerOrderVO refvo = customerOrderService.queryOrderInfoByType(param);
			CustomerUser refUser = userService.queryUserById(refvo.getCustomerId());
			model.put("ordervo", vo);
			model.put("refid", refUser.getId());
			model.put("refname", refUser.getCustomerName());
			model.put("point", vo.getRsvdc4());
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("order/details/tzyl_detail",model);
	}
	
	/**
	 * 系统分红 18016
	 * @param orderVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="showQueryInfoxtfh")
	public ModelAndView queryOrderInfoxtfh(CustomerOrderVO orderVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ordervokey", orderVo);
		try {
			CustomerOrderVO vo = customerOrderService.queryOrderInfoByType(param);
			model.put("ordervo", vo);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("order/details/xtfh_detail",model);
	}
	
	/**
	 * 中奖返款 18003
	 * @param orderVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="showQueryInfozjfk")
	public ModelAndView queryOrderInfozjfk(CustomerOrderVO orderVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ordervokey", orderVo);
		try {
			CustomerOrderVO vo = customerOrderService.queryOrderInfoByType(param);
			model.put("ordervo", vo);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("order/details/zjfk_detail",model);
	}
	
	
	/**
	 * 资金转出 18013
	 * @param orderVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="showQueryInfozjzc")
	public ModelAndView queryOrderInfozjzc(CustomerOrderVO orderVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ordervokey", orderVo);
		try {
			CustomerOrderVO vo = customerOrderService.queryOrderInfoByType(param);
			model.put("ordervo", vo);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("order/details/zjzc_detail",model);
	}
	
	/**
	 * 资金转入 18014
	 * @param orderVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="showQueryInfozjzr")
	public ModelAndView queryOrderInfozjzr(CustomerOrderVO orderVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ordervokey", orderVo);
		try {
			CustomerOrderVO vo = customerOrderService.queryOrderInfoByType(param);
			model.put("ordervo", vo);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("order/details/zjzr_detail",model);
	}
	
	/**
	 * 后台系统撤单
	 * @param betRecordVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("cancelBetByAdmin")
	@ResponseBody
	public Map<String,?> cancelBetByAdmin(BetRecordVO betRecordVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		AdminUser adminUser = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		try {
			param.put(CommonUtil.USERKEY, adminUser);
			param.put("betRecordkeyVo", betRecordVo);
			String result = betRecordService.updateCancelBetByAdmin(param);
			model.put("info", result);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
}
