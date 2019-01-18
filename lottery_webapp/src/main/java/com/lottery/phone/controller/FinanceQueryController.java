package com.lottery.phone.controller;

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

import com.lottery.bean.entity.CustomerCash;
import com.lottery.bean.entity.CustomerOrder;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerOrderVO;
import com.lottery.service.ICustomerCashService;
import com.lottery.service.ICustomerOrderService;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;

@Controller
@RequestMapping("")
public class FinanceQueryController {
	
	@Autowired
	private ICustomerOrderService orderService;
	
	@Autowired
	private ICustomerCashService cashService;
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
		List<Long> ids = new ArrayList<Long>(1);
		ids.add(curUser.getId());
		param.put("idsKey",ids);
		Page<CustomerOrderVO, CustomerOrder> orders = null;
		try {
			orders = orderService.queryMyOrdersByPage(param);
			model.put("orders", orders);
			model.put("totalCount", orders.getTotalCount());
			model.put("pageNum", orders.getPageNum());
			model.put("maxCount", orders.getMaxCount());
			model.put("pageCount", orders.getPageCount());
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	/**
	 * 财务明细
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/showFinanceDetail")
	public ModelAndView showFinanceDetail(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		param.put("userId",user.getId());
		CustomerCash cash = null;
		try {
			cash = cashService.findCustomerCashByUserId(param);
			model.put("cash", cash);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("jspmp/finance/financeDetail", model);
	}
	
}
