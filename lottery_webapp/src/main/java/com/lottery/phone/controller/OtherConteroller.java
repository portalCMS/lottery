package com.lottery.phone.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lottery.bean.entity.AdvertisingList;
import com.lottery.bean.entity.ArticleManage;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.ArticleManageVO;
import com.lottery.bean.entity.vo.CustomerOrderVO;
import com.lottery.service.IAdvertisingListService;
import com.lottery.service.IArticleManageService;
import com.lottery.service.ICustomerFeedbackService;
import com.lottery.service.ICustomerOrderService;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;

@Controller
public class OtherConteroller {
	@Autowired
	private ICustomerOrderService orderService;

	@Autowired
	private ICustomerFeedbackService feedBackService;

	@Autowired
	private IAdvertisingListService advertsService;
	
	@Autowired
	private IArticleManageService aService;
	
	/**
	 * 网站公告页
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("showLoadNotices")
	public ModelAndView showLoadNotices(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("jspmp/article/article_manage", model);
	}
	
	/**
	 * 网站公告列表查询执行方法
	 * @param vo
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("queryNotices")
	@ResponseBody
	public Map<String, Object> getNotices(ArticleManageVO vo,
			HttpServletRequest request, HttpServletResponse reponse) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("articleKey", vo);
		try {
			Page<ArticleManageVO, ArticleManage> page = aService
					.queryWebAppNoticeArticle(param);
			model.put("page", page);
			model.put("totalCount", page.getTotalCount());
			model.put("pageNum", page.getPageNum());
			model.put("maxCount", page.getMaxCount());
			model.put("pageCount", page.getPageCount());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	/**
	 * 公告详情页
	 * @param vo
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("showDetail")
	public ModelAndView showNoticeArticle(ArticleManageVO vo,
			HttpServletRequest request, HttpServletResponse reponse) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("amvo", vo);
		try {
			ArticleManageVO am = aService.getNoticeArticleById(param);
			am.setContent(StringEscapeUtils.unescapeHtml3(am.getContent()));
			param.put("type", CommonUtil.NOTICE);
			param.put("max", 5);
			//List<ArticleManageVO> list = aService.findNewNoticeArrticle(param);
			//List<CustomerOrderVO> ordervos = orderService
			///		.queryNewWinningOrder(param);
			model.put("entity", am);
			//model.put("ams", list);
			//model.put("ordervos", ordervos);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("jspmp/article/article_Detail", model);
	}
	
	/**
	 * 网站公告列表查询执行方法
	 * @param vo
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("queryNewWinningOrder")
	@ResponseBody
	public Map<String, Object> queryNewWinningOrder(CustomerOrderVO vo,
			HttpServletRequest request, HttpServletResponse reponse) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("minAmountKey", vo.getOrderAmount());
		try {
			List<CustomerOrderVO> ordervos = orderService.queryNewWinningOrder(param);
			model.put("newWingOrders", ordervos);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
}
