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
import com.lottery.bean.entity.LotteryPlaySelect;
import com.lottery.bean.entity.LotteryType;
import com.lottery.bean.entity.PlayModel;
import com.lottery.service.IAdminParameterService;
import com.lottery.service.IAdminRoleInitService;
import com.lottery.service.IDataDictionaryService;
import com.lottery.service.ILotteryPlaySelectService;
import com.lottery.service.ILotteryTypeService;
import com.lottery.service.IPlayModelService;
import com.lottery.staticvalue.CommonStatic;
import com.xl.lottery.util.ApplicationContextUtil;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;

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
	@Override
	@SuppressWarnings("resource")
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		ApplicationContext ac = WebApplicationContextUtils
                .getWebApplicationContext(config.getServletContext());
		ApplicationContextUtil.setApplicationContext(ac);
		IAdminRoleInitService adminRoleService = (IAdminRoleInitService)ApplicationContextUtil.getBean("adminRoleInitService");
		IDataDictionaryService dataDictionaryService = (IDataDictionaryService)ApplicationContextUtil.getBean("dataDictionaryServiceImpl");
		try {
			DataDictionaryUtil.getDataString().putAll(dataDictionaryService.queryAllDataDictionary());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<String, Object> roleMap = (HashMap<String, Object>) adminRoleService.findAdminRoleList();
		config.getServletContext().setAttribute(CommonUtil.ROLEKEY, roleMap);
		//加载全局路径
		config.getServletContext().setAttribute("contextPath", config.getServletContext().getContextPath());
		IDataDictionaryService dataCodeService = (IDataDictionaryService) ac.getBean("dataDictionaryServiceImpl");
		ILotteryTypeService typeService = (ILotteryTypeService) ac.getBean("lotteryTypeServiceImpl");
		IPlayModelService playModelService = (IPlayModelService) ac.getBean("playModelServiceImpl");
		ILotteryPlaySelectService lotteryPlaySelectService = (ILotteryPlaySelectService) ac.getBean("lotteryPlaySelectServiceImpl");
		IAdminParameterService parameterService = (IAdminParameterService) ac.getBean("adminParameterServiceImpl");
		try {
			List<DataDictionary> data = dataCodeService.getAllDataDictionary();
			for(DataDictionary obj : data){
				CommonStatic.getCodeMap().put(CommonStatic.DATADICTIONARY_HEAD+Long.toString(obj.getSid()), obj.getSname());
			}
			List<LotteryType> types = typeService.getAllType();
			for(LotteryType type : types){
				if(type.getLotteryLevel() == 2)continue;
				CommonStatic.getCodeMap().put(CommonStatic.LOTTERYTYPE_HEAD+type.getLotteryCode(), type.getLotteryName());
			}
			List<PlayModel> playModels = playModelService.getAllPlayModel();
			for(PlayModel model : playModels){
				CommonStatic.getCodeMap().put(CommonStatic.PLAYMODEL_HEAD+model.getModelCode(), model.getModelName());
				CommonStatic.getCodeObjectMap().put(CommonStatic.PLAYMODEL_HEAD+model.getModelCode(), model);
			}
			List<LotteryPlaySelect> selectCodes = lotteryPlaySelectService.getAllSelectCode();
			for(LotteryPlaySelect selCode : selectCodes){
				CommonStatic.getCodeMap().put(CommonStatic.LOTTERYPLAYSELECT_HEAD+selCode.getSelectCode(), selCode.getSelectName());
			}
			this.getWsAddress(parameterService,config);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("数据加载失败");
			System.exit(1);
		}
		System.err.println("权限加载完毕");
	}

	private void getWsAddress(IAdminParameterService parameterService,ServletConfig config) throws Exception{
		String[] keys = new String[]{"webws","jobws"};
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("parameterName", "userConfig");
		param.put("parameterKeys", keys);
		Map<String,String> returnMap = parameterService.getParameterList(param);
		if(returnMap!=null){
			config.getServletContext().setAttribute("webws", returnMap.get("webws"));
			config.getServletContext().setAttribute("jobws", returnMap.get("jobws"));
		}
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
