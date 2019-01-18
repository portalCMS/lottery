package com.lottery.controller;

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

import com.lottery.annotation.Token;
import com.lottery.bean.entity.CustomerMessage;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.LotteryType;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.TaskConfig;
import com.lottery.bean.entity.vo.CustomerMessageVO;
import com.lottery.bean.entity.vo.LotteryTypeVO;
import com.lottery.service.ICustomerMessageService;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;

@Controller
public class MsgController extends BaseController{

	@Autowired
	private ICustomerMessageService msgService;
	
	/**
	 * 时时彩页面玩法初始化
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/showWebMsg")
	@Token(needSaveToken=true)
	public ModelAndView showWebMsg(LotteryTypeVO lotteryVo,HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("profile/webMsg");
	}
	
	@RequestMapping("queryMsgPage")
	@ResponseBody
	public Map<String,Object> queryMsgPage(CustomerMessageVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String, Object>();
		CustomerUser self = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		param.put(CommonUtil.CUSTOMERUSERKEY, self);
		param.put("messageKey", vo);
		Page<CustomerMessageVO,CustomerMessage> page = null;
		try {
			page = msgService.queryMsgPage(param);
			model.put("page", page);
			model.put("totalCount", page.getTotalCount());
			model.put("pageNum", page.getPageNum());
			model.put("maxCount", page.getMaxCount());
			model.put("pageCount", page.getPageCount());
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("getmsgcount")
	@ResponseBody
	public Map<String,Object> getMsgcount(HttpServletRequest request,HttpServletResponse reponse){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String, Object>();
		CustomerUser self = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		param.put(CommonUtil.CUSTOMERUSERKEY, self);
		Integer count = 0;
		try {
			count = msgService.getMsgCount(param);
			model.put("msgCount", count);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("readMsg")
	@ResponseBody
	public Map<String, Object> readMsg(CustomerMessageVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		try {
			msgService.updateReadMsg(vo.getId(), user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		} 
		return model;
	}
	
	@RequestMapping("delMsg")
	@ResponseBody
	public Map<String, Object> delMsg(CustomerMessageVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		try {
			msgService.deleteMsg(vo.getId(), user);
			model.put("success", "删除成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		} 
		return model;
	}
	
	
	@RequestMapping("sendMsg")
	@ResponseBody
	public Map<String, Object> sendMsg(CustomerMessageVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY);
		try {
			param.put(CommonUtil.CUSTOMERUSERKEY, user);
			param.put("msgVO", vo);
			msgService.saveWebMsg(param);
			model.put("success", "发送成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		} 
		return model;
	}
}
