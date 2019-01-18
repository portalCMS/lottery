package com.lottery.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lottery.bean.entity.DataDictionary;
import com.lottery.bean.entity.DomainUrl;
import com.lottery.bean.entity.LotteryPlaySelect;
import com.lottery.bean.entity.LotteryType;
import com.lottery.bean.entity.PlayModel;
import com.lottery.service.IAdminParameterService;
import com.lottery.service.IDataDictionaryService;
import com.lottery.service.IDomainUrlService;
import com.lottery.service.ILotteryPlaySelectService;
import com.lottery.service.ILotteryTypeService;
import com.lottery.service.IPlayModelService;
import com.lottery.staticvalue.CommonStatic;
import com.xl.lottery.exception.LotteryExceptionLog;

@RequestMapping("Refresh")
@Controller
public class RefreshController extends BaseController {

	@Autowired
	private IDataDictionaryService dataCodeService;

	@Autowired
	private ILotteryTypeService typeService;

	@Autowired
	private IPlayModelService playModelService;

	@Autowired
	private ILotteryPlaySelectService lotteryPlaySelectService;

	@Autowired
	private IDomainUrlService urlService;

	@Autowired
	private IAdminParameterService parameterService;

	@RequestMapping("refreshMemCache")
	public void refreshMemCache(HttpServletRequest request, HttpServletResponse response) {
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
			this.getWsAddress(parameterService, request.getSession().getServletContext());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, "数据加载失败");
		}
	}

	private void getWsAddress(IAdminParameterService parameterService, ServletContext config) throws Exception {
		String[] keys = new String[] { "minPoint", "stepPoint" };
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("parameterName", "userConfig");
		param.put("parameterKeys", keys);
		Map<String, String> returnMap = parameterService.getParameterList(param);
		config.setAttribute("cminPoint", returnMap.get("minPoint"));
		config.setAttribute("cstepPoint", returnMap.get("stepPoint"));
	}

}
