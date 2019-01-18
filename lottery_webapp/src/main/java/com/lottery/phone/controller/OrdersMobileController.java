package com.lottery.phone.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lottery.bean.entity.BetRecord;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.BetRecordVO;
import com.lottery.service.IBetRecordService;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;

@Controller
@RequestMapping("")
public class OrdersMobileController {
	
	@Autowired
	private IBetRecordService betRecordService;
	/**
	 * 投注记录页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/showPhoneOrders")
	public ModelAndView showPhoneOrders(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>(); 
		return new ModelAndView("jspmp/order/orders", model);
	}
	
	
	/**
	 * 投注记录查询执行方法
	 * @param betRecordVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/showRecordData")
	@ResponseBody
	public Map<String, ?> getBetRecordData(BetRecordVO betRecordVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			param.put(CommonUtil.CUSTOMERUSERKEY, request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY));
			param.put("berRecordKey", betRecordVo);
			Page<BetRecordVO, BetRecord> page = betRecordService.queryBetRecordsWebAppBySelf(param);
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
	
}
