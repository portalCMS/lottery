package com.lottery.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.AdminUser;
import com.lottery.dao.IAdminParameterDao;
import com.lottery.dao.impl.AdminWriteLog;
import com.lottery.service.IAdminParameterService;
import com.xl.lottery.util.CommonUtil;

@Service
public class AdminParameterServiceImpl implements IAdminParameterService{

	@Autowired
	private IAdminParameterDao parameterDao;
	
	@Autowired
	private AdminWriteLog adminWriteLog;
	
	@Override
	public Map<String,String> getParameterList(Map<String, Object> param) throws Exception {
		String parameterName = (String) param.get("parameterName");
		String[] parameterKeys = (String[]) param.get("parameterKeys");
		
		return parameterDao.queryParameterList(parameterName,parameterKeys);
	}
	@Override
	public boolean saveParameterValue(Map<String, Object> param) throws Exception {
		String parameterName = (String) param.get("parameterName");
		Map<String,String> keyValueMap = (Map<String,String>) param.get("keyValueMap");
		AdminUser admin = (AdminUser) param.get(CommonUtil.USERKEY);
		String userName= admin.getUserName();
		parameterDao.setParameterValue(parameterName,keyValueMap,userName);
		adminWriteLog.saveWriteLog(admin, CommonUtil.UPDATE, "setParameterValue",
				keyValueMap.toString());
		return true;
	}

}
