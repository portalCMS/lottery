package com.lottery.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lottery.bean.entity.ArticleManage;
import com.lottery.bean.entity.ClassSort;
import com.lottery.bean.entity.CustomerFeedback;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.ArticleManageVO;
import com.lottery.bean.entity.vo.ClassSortVO;
import com.lottery.bean.entity.vo.CustomerFeedbackVO;
import com.lottery.bean.entity.vo.CustomerMessageVO;
import com.lottery.service.IArticleManageService;
import com.lottery.service.IClassSortService;
import com.lottery.service.ICustomerFeedbackService;
import com.lottery.service.ICustomerMessageService;
import com.lottery.service.ICustomerUserService;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;


@Controller
@RequestMapping("/article")
public class ArticleController extends BaseController{

	@Autowired
	private IArticleManageService articleService;
	
	@Autowired
	private IClassSortService sortService;
	
	@Autowired
	private ICustomerFeedbackService feedBackService;
	
	@Autowired
	private ICustomerUserService userService;
	
	@Autowired
	private ICustomerMessageService msgService;
	
	@RequestMapping("/initNotice")
	public ModelAndView initNotice(ArticleManageVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		Page<ArticleManageVO, ArticleManage> page= null;
		try {
			param.put("code", CommonUtil.NOTICE);
			List<ClassSortVO> list = sortService.findClassSorts(param);
			model.put("sorts", list);
			
			param.put("articleKey", vo);
			page = articleService.queryNoticeArticle(param);
			model.put("page", page);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		
		return new ModelAndView("announcement/items",model);
	}
	
	
	@RequestMapping("/queryNotice")
	@ResponseBody
	public Map<String,Object> queryNotice(ArticleManageVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		Page<ArticleManageVO, ArticleManage> page= null;
		try {
			param.put("articleKey", vo);
			page = articleService.queryNoticeArticle(param);
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
	
	@RequestMapping("/initCreateNotice")
	public ModelAndView initCreateNotice(ArticleManageVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("code", CommonUtil.NOTICE);
		try {
			List<ClassSortVO> list = sortService.findClassSorts(param);
			model.put("sorts", list);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("announcement/create",model);
	}
	
	@RequestMapping("/saveNoticeArticle")
	public ModelAndView saveNoticeArticle(ArticleManageVO vo,HttpServletRequest request,HttpServletResponse reponse,RedirectAttributes rAttributes){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		try {
			param.put("articleKey", vo);
			param.put(CommonUtil.USERKEY,request.getSession().getAttribute(CommonUtil.USERKEY));
			articleService.saveNoticeArticle(param);
			rAttributes.addFlashAttribute("success", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("redirect:initNotice.do",model);
	}
	@RequestMapping("showHelp")
	public ModelAndView showHelpCenter(ArticleManageVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("code", CommonUtil.HELP_CENTER);
		param.put("articleManageVO", vo);
		try {
			Page<ArticleManageVO,ArticleManage> list = articleService.showHelpCenter(param);
			param.put("code", CommonUtil.HELP_CENTER);
			List<ClassSortVO> cslist = sortService.findClassSorts(param);
			model.put("helpcenters", list);
			model.put("cslist", cslist);
			model.put("vo", vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("helper/items",model);
	}
	
	@RequestMapping("showNoticeSort")
	public ModelAndView showNoticeSort(ClassSortVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("code", CommonUtil.NOTICE);
		try {
			List<ClassSortVO> list = sortService.findClassSorts(param);
			model.put("sorts", list);
			model.put("code", CommonUtil.NOTICE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("helper/sortbyhelp",model);
	}
	
	@RequestMapping("showHelpCenterSort")
	public ModelAndView showHelpSort(ClassSortVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("code", CommonUtil.HELP_CENTER);
		try {
			List<ClassSortVO> list = sortService.findClassSorts(param);
			model.put("sorts", list);
			model.put("code", CommonUtil.HELP_CENTER);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("helper/sortbyhelp",model);
	}
	
	@RequestMapping("saveHelpSort")
	@ResponseBody
	public Map<String, Object> saveHelpSort(ClassSortVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("amvo", vo);
		param.put(CommonUtil.USERKEY, request.getSession().getAttribute(CommonUtil.USERKEY));
		try {
			ClassSort obj = sortService.saveHelpCenterSort(param);
			model.put("success", "分类添加成功");
			model.put("obj", obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("updateHelpSort")
	@ResponseBody
	public Map<String, Object> updateHelpSort(ClassSortVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("amvo", vo);
		param.put(CommonUtil.USERKEY, request.getSession().getAttribute(CommonUtil.USERKEY));
		try {
			ClassSort obj = sortService.updateHelpCenterSort(param);
			model.put("success", "分类修改成功");
			model.put("obj", obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("getClassSort")
	@ResponseBody
	public Map<String, Object> getClassSort(ClassSortVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("amvo", vo);
		try {
			List<ClassSort> objs = sortService.findClassSortsbyother(param);
			model.put("objs", objs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("deleteHelpSort")
	@ResponseBody
	public Map<String, Object> deleteHelpSort(ClassSortVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("amvo", vo);
		param.put(CommonUtil.USERKEY, request.getSession().getAttribute(CommonUtil.USERKEY));
		try {
			sortService.deleteHelpCenterSort(param);
			model.put("success", "分类删除成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("/initCreateHelpCenter")
	public ModelAndView initCreateHelpCenter(ArticleManageVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("code", CommonUtil.HELP_CENTER);
		try {
			List<ClassSortVO> list = sortService.findClassSorts(param);
			model.put("sorts", list);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("helper/create",model);
	}
	
	@RequestMapping("/saveHelpCenter")
	public ModelAndView saveHelpCenter(ArticleManageVO vo,HttpServletRequest request,HttpServletResponse reponse,RedirectAttributes rAttributes){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		
		try {
			param.put("articleKey", vo);
			param.put(CommonUtil.USERKEY,request.getSession().getAttribute(CommonUtil.USERKEY));
			articleService.saveNoticeArticle(param);
			rAttributes.addFlashAttribute("success", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("redirect:showHelp.do",model);
	}
	
	@RequestMapping("initUpdateHelpCenter")
	public ModelAndView initUpdateHelpCenter(ArticleManageVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		try {
			param.put("amvo", vo);
			ArticleManage entity = articleService.showUpdateArticleMange(param);
			param.put("code", CommonUtil.HELP_CENTER);
			List<ClassSortVO> list = sortService.findClassSorts(param);
			model.put("entity", entity);
			model.put("sorts", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("helper/update",model);
	}
	
	@RequestMapping("initUpdateNotice")
	public ModelAndView initUpdateNotice(ArticleManageVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		try {
			param.put("amvo", vo);
			ArticleManage entity = articleService.showUpdateArticleMange(param);
			param.put("code", CommonUtil.NOTICE);
			List<ClassSortVO> list = sortService.findClassSorts(param);
			model.put("entity", entity);
			model.put("sorts", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("announcement/update",model);
	}
	
	@RequestMapping("updateHelpCenter")
	public ModelAndView updateHelpCenter(ArticleManageVO vo,HttpServletRequest request,HttpServletResponse reponse,RedirectAttributes rAttributes){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		try {
			param.put("amvo", vo);
			param.put(CommonUtil.USERKEY, request.getSession().getAttribute(CommonUtil.USERKEY));
			articleService.updateArticleManage(param);
			rAttributes.addFlashAttribute("success", "修改成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, rAttributes);
		}
		return new ModelAndView("redirect:showHelp.do",model);
	}
	
	@RequestMapping("updateHelpCenterStatus")
	@ResponseBody
	public Map<String,Object> updateHelpCenterStatus(ArticleManageVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		try {
			param.put("amvo", vo);
			param.put(CommonUtil.USERKEY, request.getSession().getAttribute(CommonUtil.USERKEY));
			articleService.updateArticleManage(param);
			model.put("success", "修改成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("updateNotice")
	public ModelAndView updateNotice(ArticleManageVO vo,HttpServletRequest request,HttpServletResponse reponse,RedirectAttributes rAttributes){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		try {
			param.put("amvo", vo);
			param.put(CommonUtil.USERKEY, request.getSession().getAttribute(CommonUtil.USERKEY));
			articleService.updateArticleManage(param);
			rAttributes.addFlashAttribute("success", "修改成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, rAttributes);
		}
		return new ModelAndView("redirect:initNotice.do",model);
	}
	
	@RequestMapping("updateNoticeStatus")
	@ResponseBody
	public Map<String,Object> updateNoticeStatus(ArticleManageVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		try {
			param.put("amvo", vo);
			param.put(CommonUtil.USERKEY, request.getSession().getAttribute(CommonUtil.USERKEY));
			articleService.updateArticleManage(param);
			model.put("success", "修改成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("deleteArticleMange")
	@ResponseBody
	public Map<String,Object> deleteArticleMange(ArticleManageVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		try {
			param.put("amvo", vo);
			param.put(CommonUtil.USERKEY, request.getSession().getAttribute(CommonUtil.USERKEY));
			articleService.deleteArticleManage(param);
			model.put("success", "删除成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	/**
	 * 查询用户反馈记录
	 * @param vo
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("queryFeedbacks")
	@ResponseBody
	public Map<String,Object> queryFeedbacks(CustomerFeedbackVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String,Object> param = new HashMap<String,Object>();
		Map<String,Object> model = new HashMap<String,Object>();
		param.put("cfvo", vo);
		param.put(CommonUtil.CUSTOMERUSERKEY, request.getSession().getAttribute(CommonUtil.CUSTOMERUSERKEY));
		Page<CustomerFeedbackVO, CustomerFeedback> page= null;
		try {
			page = feedBackService.queryCustomerFeedback(param);
			model.put("page", page);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	/**
	 * show站内信
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("showWebMsg")
	public ModelAndView showWebMsg(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("announcement/webMsg");
	}
	
	@RequestMapping("checkMsgUserName")
	@ResponseBody
	public Map<String, Object> checkMsgUserName(String toUserName,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		 try {
			CustomerUser user = userService.queryUserByName(toUserName);
			if(user == null)model.put("checkMsg", "该用户不存在");
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
		param.put("msgVO", vo);
		param.put(CommonUtil.USERKEY, request.getSession().getAttribute(CommonUtil.USERKEY));
		try {
			msgService.saveMsg(param);
			model.put("success", "发送成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		} 
		return model;
	}
}
