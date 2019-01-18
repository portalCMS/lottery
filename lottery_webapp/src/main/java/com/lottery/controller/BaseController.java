package com.lottery.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.xl.lottery.util.StringEscapeEditor;

public abstract class BaseController {

	public final String PERSONALCENTERURL = "";
	
	/**
	 * 网站sql注入，script注入等的防护。
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder,HttpServletRequest request) {
		binder.setAutoGrowCollectionLimit(5000);
		binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false, false));
	}
}
