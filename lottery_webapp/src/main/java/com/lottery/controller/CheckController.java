package com.lottery.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lottery.bean.entity.DomainUrl;
import com.lottery.staticvalue.CommonStatic;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.exception.LotteryExceptionLog;


@RequestMapping("check")
@Controller
public class CheckController extends BaseController{

	
	@RequestMapping("showcheck")
	public ModelAndView refreshMemCache(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("checkUrl",model);
	}
	
	@RequestMapping("checkUrl")
	@ResponseBody
	public Map<String, Object> checkUrl(String url,String code,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		String picCode = (String) request.getSession().getAttribute("cu");
		try {
			model.put("success", "域名错误");
			if (!code.equalsIgnoreCase(picCode)) {
				throw new LotteryException("验证码错误");
			}
			if(!url.startsWith("http://"))url = "http://"+url;
			for(Object obj : CommonStatic.urlObject){
				DomainUrl urlobj = (DomainUrl) obj;
				String uo = urlobj.getUrl();
				if(!uo.startsWith("http://"))uo = "http://"+uo;
				if(uo.equals(url)){
					model.put("success", "域名正确");
					break;
				}/*else{
					model.put("success", "域名错误");
				}*/
			}
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
}
