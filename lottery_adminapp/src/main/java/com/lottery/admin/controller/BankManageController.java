package com.lottery.admin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.BankManage;
import com.lottery.bean.entity.CustomerBankCard;
import com.lottery.bean.entity.CustomerBindCard;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.UserCardInventory;
import com.lottery.bean.entity.vo.AdminUserVO;
import com.lottery.bean.entity.vo.BankManageVO;
import com.lottery.bean.entity.vo.CustomerBankCardVO;
import com.lottery.bean.entity.vo.CustomerBindCardVO;
import com.lottery.bean.entity.vo.UserCardInventoryVO;
import com.lottery.service.IAdminUserService;
import com.lottery.service.IBankManageService;
import com.lottery.service.ICustomerBankCardService;
import com.lottery.service.ICustomerBindCardService;
import com.lottery.service.IUserCardInventoryService;
import com.xl.lottery.exception.LotteryExceptionDictionary;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.MD5Util;

@Controller
public class BankManageController extends BaseController{

	@Autowired
	private IBankManageService bankManageService;
	
	@Autowired
	private ICustomerBindCardService customerBindCardService;
	
	@Autowired
	private IAdminUserService adminUserService;
	
	@Autowired
	private ICustomerBankCardService customerBankCardService; 
	
	@Autowired
	private IUserCardInventoryService cardInventoryService;
	
	@RequestMapping("showBankManage")
	public ModelAndView showBankManage(CustomerBankCardVO cardVo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("bankCardKey", cardVo);
		int countCard=0;
		int countBank=0;
		try {
			 countCard = customerBankCardService.countBankCard();
			 countBank = bankManageService.countBankManage();
			 Page<CustomerBankCardVO, CustomerBankCard> page = customerBankCardService.findBankCardListByPage(param);
			 model.put("countCard", countCard);
			 model.put("countBank", countBank);
			 model.put("pagelist", page);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model,"查询数据库异常!");
			return new ModelAndView("finance/cards",model);
		}
		
		return new ModelAndView("finance/cards",model);
	}
	
	@RequestMapping("showBanks")
	public ModelAndView showBanks(BankManageVO bankManageVO,HttpServletRequest request, HttpServletResponse response,RedirectAttributes reAttributes){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("bankmanagekey", bankManageVO);
		try {
			Page<BankManageVO, BankManage> page = bankManageService.findBankeManageList(param);
			model.put("pagelist", page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, reAttributes);
			return new ModelAndView("redirect:domain",model);
		}
		return new ModelAndView("finance/banks",model);
	}
	
	@RequestMapping("showAddBank")
	public ModelAndView showAddBank(HttpServletRequest request, HttpServletResponse response){
		
		return new ModelAndView("finance/binding_bank");
	}
	
	@RequestMapping("addBank")
	public ModelAndView addBank(BankManageVO bankManage,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		AdminUser admin  = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		if(!MD5Util.makeMD5(bankManage.getPwd()).equals(admin.getUserRolePwd())){
			model.put("errorMsg","权限密码错误");
			return new ModelAndView("finance/binding_bank",model);
		}
		if(!request.getSession().getAttribute(bankManage.getPickey()).toString().equalsIgnoreCase(bankManage.getCode())){
			model.put("errorMsg","验证码错误");
			return new ModelAndView("finance/binding_bank",model);
		}
		param.put("bankmanagekey", bankManage);
		param.put(CommonUtil.USERKEY, admin);
		try {
			String success = bankManageService.saveBank(param);
			if(success.equals("success")){
				model.put("success", "操作成功");
			}else{
				model.put("errorMsg", "操作失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
			return new ModelAndView("finance/binding_bank",model);
		}
		return new ModelAndView("finance/binding_bank",model);
	}
	
	@RequestMapping("showUpdateBank")
	public ModelAndView showUpdateBank(BankManageVO bankManageVO,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("bankmanagekey", bankManageVO);
		try {
			BankManage bank = bankManageService.findBankById(param);
			model.put("entity", bank);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
			return new ModelAndView("finance/bank",model);
		}
		return new ModelAndView("finance/bank",model);
	}
	
	@RequestMapping("updateBank")
	public ModelAndView updateBank(BankManageVO bankManage,HttpServletRequest request, HttpServletResponse response,RedirectAttributes attributes) throws UnsupportedEncodingException{
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		AdminUser admin  = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		if(!MD5Util.makeMD5(bankManage.getPwd()).equals(admin.getUserRolePwd())){
			attributes.addFlashAttribute("errorMsg","权限密码错误");
			return new ModelAndView("redirect:showUpdateBank.do?id="+bankManage.getId(),model);
		}
		if(!request.getSession().getAttribute(bankManage.getPickey()).toString().equalsIgnoreCase(bankManage.getCode())){
			attributes.addFlashAttribute("errorMsg","验证码错误");
			return new ModelAndView("redirect:showUpdateBank.do?id="+bankManage.getId(),model);
		}
		param.put("bankmanagekey", bankManage);
		param.put(CommonUtil.USERKEY, admin);
		try{
			String success = bankManageService.updateBank(param);
			if(success.equals("success")){
				attributes.addFlashAttribute("success","操作成功");
			}else{
				attributes.addFlashAttribute("errorMsg", "操作失败");
				return new ModelAndView("redirect:showUpdateBank.do?id="+bankManage.getId(),model);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, attributes);
			return new ModelAndView("redirect:showUpdateBank.do?id="+bankManage.getId(),model);
		}
		return new ModelAndView("redirect:showUpdateBank.do?id="+bankManage.getId(),model);
	}
	
	
	@RequestMapping("initBindCard")
	public ModelAndView initBindCard(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		List<BankManage> banks =null;
		List<UserCardInventory> inventorys = null;
		try {
			banks = bankManageService.findBankeManageList();
			inventorys = cardInventoryService.queryInventorys(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model, "银行表查询异常");
			return new ModelAndView("finance/binding_card",model);
		}
		model.put("banks", banks);
		model.put("inventorys", inventorys);
		return new ModelAndView("finance/binding_card",model);
	}
	
	@RequestMapping("/saveBindCard")
	public ModelAndView saveBindCard(CustomerBankCardVO bankCardVO,HttpServletRequest request,
			HttpServletResponse response,RedirectAttributes rAttributes) throws UnsupportedEncodingException {
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		AdminUser admin  = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		
		ModelAndView returnView = this.checkRolePwdAndPiCode(request, model,request.getParameter("rolePwd"),"bindcardkey", request.getParameter("picCode"),"redirect:initBindCard.do");
		if(null!=returnView){
			return returnView;
		}
		param.put("bankCardKey", bankCardVO);
		param.put(CommonUtil.USERKEY, admin);
		try {
			String success = customerBankCardService.saveBankCard(param);
			if(!success.equals("success")){
				rAttributes.addFlashAttribute("errorMsg", "操作失败");
			}
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, rAttributes);
			return new ModelAndView("redirect:initBindCard.do",model);
		}
		
		rAttributes.addFlashAttribute("success", "绑定新银行卡成功！");
		return new ModelAndView("redirect:initBindCard.do", model);
	}
	
	@RequestMapping("showCardInfo")
	public ModelAndView showCardInfo(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		model.put("cardId",request.getParameter("id"));
		model.put("cardStatus",request.getParameter("status"));
		long parseLong = Long.parseLong(request.getParameter("id"));
		try {
			CustomerBankCard bankCard = customerBankCardService.queryBankCardById(parseLong);
			BankManageVO bankVO = new BankManageVO();
			bankVO.setId(bankCard.getBankId());
			param.put("bankmanagekey", bankVO);
			BankManage bankManage = bankManageService.findBankById(param);
			model.put("bankCard", bankCard);
			model.put("bankManage", bankManage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("finance/card", model);
	}
	
	@RequestMapping("getCardBindUserInfo")
	@ResponseBody
	public Map<String,Object> getCardBindUserInfo(CustomerBindCardVO vo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cbcvo",vo);
		try {
			Map<String,Object> cardUserInfos = customerBindCardService.queryCardUserInfo(param);
			model.put("userInfo", cardUserInfos);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("initUpdateCard")
	public ModelAndView initUpdateCard(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		
		String cardId = request.getParameter("cardId");
		CustomerBankCard bankCard=null;
		List<UserCardInventory> inventorys = null;
		try {
			bankCard = customerBankCardService.queryBankCardById(Long.parseLong(cardId));
			inventorys = cardInventoryService.queryInventorys(param);
		} catch (NumberFormatException e1) {
			LotteryExceptionLog.wirteLog(e1, model,"银行卡对应Id不能为null");
			return new ModelAndView("finance/card",model);
		} catch (Exception e1) {
			LotteryExceptionLog.wirteLog(e1, model, "查询银行卡异常");
			return new ModelAndView("finance/card",model);
		}
		model.put("bankCard", bankCard);
		model.put("inventorys", inventorys);
		List<BankManage> banks =null;
		try {
			banks = bankManageService.findBankeManageList();
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model, "银行表查询异常");
			return new ModelAndView("finance/card",model);
		}
		model.put("banks", banks);
		return new ModelAndView("finance/edit_card", model);
	}
	
	@RequestMapping("updateBankCard")
	public ModelAndView updateBankCard(CustomerBankCardVO cardVo,HttpServletRequest request, HttpServletResponse response,RedirectAttributes reAttributes) throws UnsupportedEncodingException{
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		AdminUser admin  = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put("bankCardKey", cardVo);
		param.put(CommonUtil.USERKEY, admin);
		
		ModelAndView returnView = this.checkRolePwdAndPiCode(request, model,request.getParameter("rolePwd"), "bindcardkey", request.getParameter("picCode"), "redirect:initUpdateCard.do?cardId="+cardVo.getId());
		if(null!=returnView){
			return returnView;
		}
		try {
			customerBankCardService.updateBankCard(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, reAttributes, "更新银行卡异常！");
			return new ModelAndView("redirect:initUpdateCard.do?cardId="+cardVo.getId(),model);
		}
		reAttributes.addFlashAttribute("success", "修改银行卡信息成功！");
		return new ModelAndView("redirect:initUpdateCard.do?cardId="+cardVo.getId(),model);
	}
	
	@RequestMapping("disableBankCard")
	public ModelAndView disableBankCard(CustomerBankCardVO cardVo,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("cardId",request.getParameter("cardId"));
		cardVo.setId(Long.parseLong(request.getParameter("cardId")));
		
		Map<String, Object> param = new HashMap<String, Object>();
		AdminUser admin  = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put("bankCardKey", cardVo);
		param.put(CommonUtil.USERKEY, admin);
		
		try {
			customerBankCardService.updateDisableBankCard(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model,"停用银行卡异常！");
			return new ModelAndView("finance/card",model);
		}
		model.put("cardStatus",DataDictionaryUtil.STATUS_CLOSE);
		model.put("success", "停用银行卡成功！");
		return new ModelAndView("finance/card",model);
	}
	
	@RequestMapping("enableBankCard")
	public ModelAndView enableBankCard(CustomerBankCardVO cardVo,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("cardId",request.getParameter("cardId"));
		cardVo.setId(Long.parseLong(request.getParameter("cardId")));
		
		Map<String, Object> param = new HashMap<String, Object>();
		AdminUser admin  = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put("bankCardKey", cardVo);
		param.put(CommonUtil.USERKEY, admin);
		try {
			customerBankCardService.updateEnableBankCard(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model, "启用银行卡异常！");
			return new ModelAndView("finance/card",model);
		}
		model.put("cardStatus",DataDictionaryUtil.STATUS_OPEN);
		model.put("success", "启用银行卡成功！");
		return new ModelAndView("finance/card",model);
	}
	
	@RequestMapping("deleteBankCard")
	public ModelAndView deleteBankCard(CustomerBankCardVO cardVo,HttpServletRequest request, HttpServletResponse response,RedirectAttributes rAttributes) throws UnsupportedEncodingException{
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("cardId",request.getParameter("cardId"));
		cardVo.setId(Long.parseLong(request.getParameter("cardId")));
		
		Map<String, Object> param = new HashMap<String, Object>();
		AdminUser admin  = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put("bankCardKey", cardVo);
		param.put(CommonUtil.USERKEY, admin);
		try {
			customerBankCardService.deleteBankCard(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, rAttributes,"删除银行卡异常！");
			return new ModelAndView("redirect:showBankManage.do",model);
		}
		rAttributes.addFlashAttribute("success","删除银行卡成功！");
		return new ModelAndView("redirect:showBankManage.do",model);
	}
	
	
	@RequestMapping("getBankCards")
	@ResponseBody
	public Map<String,Object> getBankCardList(CustomerBankCardVO bankCardVO,HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("customerbankcardkey", bankCardVO);
		try {
			model.put("bankCards",customerBankCardService.getBankCardVO(param));
			return model;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e);
		}
		return model;
	}
	@RequestMapping("showCardInventory")
	public ModelAndView showCardInventory(HttpServletRequest request, HttpServletResponse response,RedirectAttributes rAttributes) throws UnsupportedEncodingException{
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		List<UserCardInventory> inventorys = null;
		try {
			inventorys = cardInventoryService.queryInventorys(param);
			model.put("inventorys", inventorys);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e);
		}
		return new ModelAndView("finance/cardInvConfig",model);
	}
	
	@RequestMapping("addCardInv")
	public ModelAndView insertCardInv(UserCardInventoryVO cardInvVo,HttpServletRequest request, 
			HttpServletResponse response,RedirectAttributes rAttributes) throws UnsupportedEncodingException{
		Map<String, Object> param = new HashMap<String, Object>();
		AdminUser admin  = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put(CommonUtil.USERKEY, admin);
		param.put("cardInvVoKey", cardInvVo);
		try {
			cardInventoryService.insertCardInv(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e,rAttributes);
		}
		return new ModelAndView("redirect:showCardInventory.do");
	}
	
	@RequestMapping("updateCardInv")
	public ModelAndView updateCardInv(UserCardInventoryVO cardInvVo,HttpServletRequest request, 
			HttpServletResponse response,RedirectAttributes rAttributes) throws UnsupportedEncodingException{
		Map<String, Object> param = new HashMap<String, Object>();
		AdminUser admin  = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		param.put(CommonUtil.USERKEY, admin);
		param.put("cardInvVoKey", cardInvVo);
		try {
			cardInventoryService.updateCardInv(param);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e,rAttributes);
		}
		return new ModelAndView("redirect:showCardInventory.do");
	}
	
	@RequestMapping("queryInventoryCards")
	@ResponseBody
	public Map<String, Object> queryInventoryCards(CustomerBankCardVO bankCardVO,HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		List<CustomerBankCard> cards = null ;
		param.put("cardVokey", bankCardVO);
		try {
			cards = customerBankCardService.queryCardListByInv(param);
			model.put("cards", cards);
			model.put("invId", bankCardVO.getCardInventoryId());
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e);
		}
		return model;
	}
	@RequestMapping("showCardConfig")
	public ModelAndView showCardConfig(HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("finance/cardConfig",model);
	}
}
