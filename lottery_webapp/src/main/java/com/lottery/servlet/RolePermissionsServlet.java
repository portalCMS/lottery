package com.lottery.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lottery.bean.entity.DataDictionary;
import com.lottery.bean.entity.DomainUrl;
import com.lottery.bean.entity.LotteryPlaySelect;
import com.lottery.bean.entity.LotteryType;
import com.lottery.bean.entity.PlayModel;
import com.lottery.init.NumberCheck;
import com.lottery.service.IAdminParameterService;
import com.lottery.service.IDataDictionaryService;
import com.lottery.service.IDomainUrlService;
import com.lottery.service.ILotteryPlaySelectService;
import com.lottery.service.ILotteryTypeService;
import com.lottery.service.IPlayModelService;
import com.lottery.staticvalue.CommonStatic;
import com.xl.lottery.util.ApplicationContextUtil;

/**
 * Servlet implementation class RolePermissionsServlet
 */
@Component
public class RolePermissionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RolePermissionsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
		ApplicationContextUtil.setApplicationContext(ac);
		// 加载全局路径
		config.getServletContext().setAttribute("contextPath", config.getServletContext().getContextPath());
		IDataDictionaryService dataCodeService = (IDataDictionaryService) ac.getBean("dataDictionaryServiceImpl");
		ILotteryTypeService typeService = (ILotteryTypeService) ac.getBean("lotteryTypeServiceImpl");
		IPlayModelService playModelService = (IPlayModelService) ac.getBean("playModelServiceImpl");
		ILotteryPlaySelectService lotteryPlaySelectService = (ILotteryPlaySelectService) ac
				.getBean("lotteryPlaySelectServiceImpl");
		IDomainUrlService urlService = (IDomainUrlService) ac.getBean("domainUrlServiceImpl");
		IAdminParameterService parameterService = (IAdminParameterService) ac.getBean("adminParameterServiceImpl");
		try {
			List<DataDictionary> data = dataCodeService.getAllDataDictionary();
			Map<String, Object> codeMap = CommonStatic.getCodeMap();
			for (DataDictionary obj : data) {
				codeMap.put(CommonStatic.DATADICTIONARY_HEAD + Long.toString(obj.getSid()), obj.getSname());
			}
			List<LotteryType> types = typeService.getAllType();
			for (LotteryType type : types) {
				if (type.getLotteryLevel() == 2)
					continue;
				codeMap.put(CommonStatic.LOTTERYTYPE_HEAD + type.getLotteryCode(), type.getLotteryName());
			}
			List<PlayModel> playModels = playModelService.getAllPlayModel();
			for (PlayModel model : playModels) {
				codeMap.put(CommonStatic.PLAYMODEL_HEAD + model.getModelCode(), model.getModelName());
				CommonStatic.getCodeObjectMap().put(CommonStatic.PLAYMODEL_HEAD + model.getModelCode(), model);
			}
			List<LotteryPlaySelect> selectCodes = lotteryPlaySelectService.getAllSelectCode();
			for (LotteryPlaySelect selCode : selectCodes) {
				codeMap.put(CommonStatic.LOTTERYPLAYSELECT_HEAD + selCode.getSelectCode(), selCode.getSelectName());
			}
			List<DomainUrl> urls = urlService.queryAll();
			CommonStatic.urlObject.addAll(urls);
			this.getWsAddress(parameterService, config);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("数据加载失败");
			System.exit(1);
		}
		NumberCheck.init();
		System.err.println("数据加载完毕");
	}

	private void getWsAddress(IAdminParameterService parameterService, ServletConfig config) throws Exception {
		String[] keys = new String[] { "minPoint", "stepPoint" };
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("parameterName", "userConfig");
		param.put("parameterKeys", keys);
		Map<String, String> returnMap = parameterService.getParameterList(param);
		config.getServletContext().setAttribute("cminPoint", returnMap.get("minPoint"));
		config.getServletContext().setAttribute("cstepPoint", returnMap.get("stepPoint"));
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		// TODO Auto-generated method stub
	}

}
