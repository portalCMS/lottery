package com.lottery.admin.controller;

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

import com.lottery.bean.entity.AdvertisingList;
import com.lottery.bean.entity.AdvertisingRegion;
import com.lottery.bean.entity.vo.AdvertisingRegionVO;
import com.lottery.service.IAdvertisingRegionService;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;



@Controller
@RequestMapping("/advert")
public class AdvertisingController extends BaseController{

	@Autowired
	private IAdvertisingRegionService advertRegionService;
	
	
	@RequestMapping("showAdvers")
	public ModelAndView showAdvers(HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			List<AdvertisingRegionVO> regions = advertRegionService.queryAdvertRegion(param);
			model.put("regions", regions);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("ad/ads",model);
	}
	
	@RequestMapping("showAdverInfo")
	public ModelAndView showAdverInfo(AdvertisingRegionVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		model.put("regionvo", vo);
		param.put("id", vo.getId());
		try {
			AdvertisingRegion entity= advertRegionService.queryAdvertRegionbyId(param);
			model.put("entity", entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("ad/setImg",model);
	}
	
	@RequestMapping("getAdvertisingList")
	@ResponseBody
	public Map<String, Object> getAdvertisingList(AdvertisingRegionVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("regionCode", vo.getRegionCode());
		try {
			List<AdvertisingList> imgs = advertRegionService.queryAdvertisingListByCode(param);
			model.put("imgs", imgs);
		} catch (Exception e) {
			// TODO: handle exception
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("saveAdvertisingList")
	public ModelAndView saveAdvertisingList(AdvertisingRegionVO vo,HttpServletRequest request,HttpServletResponse reponse,RedirectAttributes reAttributes){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("arvo", vo);
		param.put(CommonUtil.USERKEY, request.getSession().getAttribute(CommonUtil.USERKEY));
		try {
			advertRegionService.saveAdvertisingList(param);
			reAttributes.addFlashAttribute("success", "保存成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, reAttributes);
		}
		return new ModelAndView("redirect:showAdvers.do",model);
	}
	
}
