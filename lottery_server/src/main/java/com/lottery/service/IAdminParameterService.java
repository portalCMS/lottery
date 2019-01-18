package com.lottery.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface IAdminParameterService {
	/**
	 * 获取参数配置表的参数值
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> getParameterList(final Map<String, Object> param) throws Exception;
	/**
	 * 保存参数配置
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public boolean saveParameterValue(Map<String, Object> param) throws Exception;

}
