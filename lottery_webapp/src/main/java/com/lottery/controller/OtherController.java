package com.lottery.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lottery.bean.entity.AdvertisingList;
import com.lottery.bean.entity.ArticleManage;
import com.lottery.bean.entity.ClassSort;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.ArticleManageVO;
import com.lottery.bean.entity.vo.CustomerFeedbackVO;
import com.lottery.bean.entity.vo.CustomerIntegralVO;
import com.lottery.bean.entity.vo.CustomerOrderVO;
import com.lottery.service.IAdvertisingListService;
import com.lottery.service.IArticleManageService;
import com.lottery.service.IClassSortService;
import com.lottery.service.ICustomerFeedbackService;
import com.lottery.service.ICustomerIntegralService;
import com.lottery.service.ICustomerMessageService;
import com.lottery.service.ICustomerOrderService;
import com.lottery.service.ICustomerUserService;
import com.xl.lottery.desutil.AesUtil;
import com.xl.lottery.desutil.Md5Manage;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.exception.LotteryExceptionDictionary;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.ApplicationContextUtil;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.LotWebUtil;
import com.xl.lottery.util.SimpleMailService;
import com.xl.lottery.util.Util;

@Controller
public class OtherController extends BaseController {

	@Autowired
	private IArticleManageService aService;

	@Autowired
	private IClassSortService classSortService;

	@Autowired
	private ICustomerOrderService orderService;

	@Autowired
	private ICustomerFeedbackService feedBackService;

	@Autowired
	private IAdvertisingListService advertsService;

	@Autowired
	private ICustomerIntegralService integralService;

	@Autowired
	private ICustomerUserService userService;

	@Autowired
	private ICustomerMessageService msgService;

	@RequestMapping("shownotice")
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
			List<ArticleManageVO> list = aService.findNewNoticeArrticle(param);
			List<CustomerOrderVO> ordervos = orderService
					.queryNewWinningOrder(param);
			model.put("entity", am);
			model.put("ams", list);
			model.put("ordervos", ordervos);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("notice/noticeDetail", model);
	}

	@RequestMapping("showNotices")
	public ModelAndView showNotices(ArticleManageVO vo,
			HttpServletRequest request, HttpServletResponse reponse) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<CustomerOrderVO> ordervos = orderService
					.queryNewWinningOrder(param);
			param.put("regionCode", CommonUtil.Notice_CODE);
			List<AdvertisingList> adverts = advertsService
					.getAdvertisingLists(param);
			model.put("adverts", adverts);
			model.put("ordervos", ordervos);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}

		return new ModelAndView("notice/noticeList", model);
	}

	@RequestMapping("getNotices")
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

	@RequestMapping("showhcenter")
	public ModelAndView showHelpCenter(ArticleManageVO vo,
			HttpServletRequest request, HttpServletResponse reponse) {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("helper/helpCenter", model);
	}

	@RequestMapping("showHelps")
	public ModelAndView showHelps(ArticleManageVO vo,
			HttpServletRequest request, HttpServletResponse reponse) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("vo", vo);
		return new ModelAndView("helper/helpDetail", model);
	}

	@RequestMapping("getHelps")
	@ResponseBody
	public Map<String, Object> getHelps(ArticleManageVO vo,
			HttpServletRequest request, HttpServletResponse reponse) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			param.put("type", CommonUtil.HELP_CENTER);
			List<ClassSort> classSorts = classSortService
					.findClassSortsbyWebApp(param);
			List<ArticleManageVO> amvos = aService
					.queryWebAppHelpCenterArticle(param);
			model.put("classSorts", classSorts);
			model.put("amvos", amvos);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}

	@RequestMapping("updateHelpArticle")
	@ResponseBody
	public Map<String, Object> updateHelpArticle(ArticleManageVO vo,
			HttpServletRequest request, HttpServletResponse reponse) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("amvo", vo);
		param.put(CommonUtil.CUSTOMERUSERKEY, request.getSession()
				.getAttribute(CommonUtil.CUSTOMERUSERKEY));
		try {
			aService.updateHelpArticle(param);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}

	@RequestMapping("showfeedback")
	public ModelAndView showFeedBack(CustomerFeedbackVO vo,
			HttpServletRequest request, HttpServletResponse reponse) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("feedback", model);
	}
	

	@RequestMapping("downClient")
	public ModelAndView downClient(CustomerFeedbackVO vo,
			HttpServletRequest request, HttpServletResponse reponse) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("downClient", model);
	}

	@RequestMapping("saveFeedBack")
	public ModelAndView saveFeedBack(CustomerFeedbackVO vo,
			HttpServletRequest request, HttpServletResponse reponse,
			RedirectAttributes reAttributes) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("cfvo", vo);
		param.put(CommonUtil.CUSTOMERUSERKEY, request.getSession()
				.getAttribute(CommonUtil.CUSTOMERUSERKEY));
		// 图片验证码
		String picCode = (String) request.getSession().getAttribute(
				PictureCheckCodeController.RANDOMCODEKEY);
		if (picCode == null || !picCode.equalsIgnoreCase(vo.getPicCode())) {
			reAttributes.addFlashAttribute("errorMsg",
					LotteryExceptionDictionary.PICCODEERROR);
			reAttributes.addFlashAttribute("vo", vo);
			return new ModelAndView("redirect:showfeedback.html");
		}
		try {
			feedBackService.saveCustomerFeedback(param);
			reAttributes.addFlashAttribute("success", "感谢亲的宝贵建议");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, reAttributes);
		}
		return new ModelAndView("redirect:showfeedback.html", model);
	}

	@RequestMapping("registration")
	@ResponseBody
	public Map<String, Object> registration(HttpServletRequest request,
			HttpServletResponse reponse) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomerUser self = (CustomerUser) request.getSession().getAttribute(
				CommonUtil.CUSTOMERUSERKEY);
		try {
			CustomerIntegralVO integralVO = integralService
					.updateRegistration(self);
			model.put("integral", integralVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}

	@RequestMapping("connIm")
	public ModelAndView connectionIM(HttpServletRequest request,
			HttpServletResponse reponse) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomerUser user = (CustomerUser) request.getSession().getAttribute(
				CommonUtil.CUSTOMERUSERKEY);
		String cookstr = "{\"userId\":"+user.getId()+",\"userName\":\""+user.getCustomerAlias()+"\",\"Rose\":1}";
		Cookie loginCookie = new Cookie("user", new org.apache.catalina.util.URLEncoder().encode("j:"+cookstr));  
		loginCookie.setDomain("joyousphper.com");
		loginCookie.setPath("/");
		reponse.addCookie(loginCookie);
		return new ModelAndView("im", model);
	}
	
	@RequestMapping("sendEMail")
	public ModelAndView sendEMail(HttpServletRequest request,
			HttpServletResponse reponse){
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("default", model);
	}
	
	@RequestMapping("opencs")
	public ModelAndView opencs(HttpServletRequest request,
			HttpServletResponse reponse){
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("include/opencs", model);
	}
	
	/**
     * 这里这里用的是MultipartFile[] myfiles参数,所以前台就要用<input type="file" name="myfiles"/>
     * 上传文件完毕后返回给前台[0`filepath],0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
     */
    @RequestMapping(value="/fileUpload")
    @ResponseBody
    public Map<String, Object> fileUpload(MultipartFile myfiles,HttpServletRequest request, HttpServletResponse response) throws IOException{
    		Map<String,Object> resMap = new HashMap<String,Object>();
    		if (myfiles != null) {
    			//获取保存的路径，
    			String realPath = request.getSession().getServletContext().getRealPath("/upload/apk");
    			String originFileName = myfiles.getOriginalFilename();
    			if (myfiles.isEmpty()) {
    				// 未选择文件
    				resMap.put("status", "failed");
    				resMap.put("errorMsg", "亲，您未选择任何文件");
    				
    			} else if(originFileName.indexOf(".txt")==-1&&originFileName.indexOf(".text")==-1){
    				// 只能载入txt文件
    				resMap.put("status", "failed");
    				resMap.put("errorMsg", "亲，请导入.txt或.text文件");
    			}else{
    				// 文件原名称
    				originFileName = System.currentTimeMillis()+originFileName;
    				try {
    					//这里使用Apache的FileUtils方法来进行保存
    					FileUtils.copyInputStreamToFile(myfiles.getInputStream(),
    							new File(realPath, originFileName));
    					String fileInfo = Util.BufferedReaderFile(realPath+"/"+originFileName);
    					/*正则表达式将空格，换行符，制表符，等替换为逗号
    					fileInfo= Util.getStringNoBlank(fileInfo);
    					if(fileInfo.lastIndexOf(",")==fileInfo.length()){
    						fileInfo = fileInfo.substring(0, fileInfo.length()-1);
    					}
    					*/
    					resMap.put("fileInfo",fileInfo.trim());
    					resMap.put("status","success");
    				} catch (IOException e) {
    					resMap.put("status", "failed");
    					LotteryExceptionLog.wirteLog(e);
    				} finally {
    					 //上传完成后删除
    					File file = new File(realPath+"/"+originFileName);
    		            file.delete();
    			   }
    			}

    		}
    		return resMap;
    }

	public static void main(String[] args) {
		String cookstr = "j:{\"userId\":1,\"userName\":\"测试19\",\"Rose\":1}";
		System.out.println(new org.apache.catalina.util.URLEncoder().encode("j:"+cookstr));
	}
	
	
	@RequestMapping("showFirstNotice")
	@ResponseBody
	public Map<String, Object> showFirstNotice(ArticleManageVO vo,
			HttpServletRequest request, HttpServletResponse reponse) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("amvo", vo);
		try {
			ArticleManageVO am = aService.getNoticeArticleById(param);
			am.setContent(StringEscapeUtils.unescapeHtml3(am.getContent()));
			model.put("entity", am);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("getpeos")
	@ResponseBody
	public Map<String,String> getOnlinePeoples(HttpServletRequest request, HttpServletResponse reponse){
		Map<String,String> peos = new HashMap<String, String>();
		try {
			Map<String, HttpSession> map = LotWebUtil.getSessionMap();
			Set<String> keys = map.keySet();
			for(String key:keys){
				if(map.get(key)==null){
					map.remove(key);
				}
			}
			StringBuffer uns = new StringBuffer("");
			for(String un : map.keySet()){
				if(uns.length()==0){
					uns.append(un);
				}else{
					uns.append(","+un);
				}
			}
			String pesStr = AesUtil.encrypt(uns.toString(), Md5Manage.getInstance().getMd5());
			peos.put("peos", pesStr);
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e);
		}
		return peos;
	}
}
