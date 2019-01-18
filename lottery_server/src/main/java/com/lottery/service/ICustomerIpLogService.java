package com.lottery.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.CustomerIpLog;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerIpLogVO;

@Service
public interface ICustomerIpLogService {

	/**
	 * 保存用户登录日志
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String saveIpLog(final Map<String,Object> param)throws Exception;
	
	/**
	 * 查询登录日志
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<CustomerIpLogVO, CustomerIpLog> findIpLogs(final Map<String,Object> param)throws Exception;
	
	
	/**
	 * 根据用户名或是IP查询用户IP情况
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Object, Object> queryIplogs(final Map<String, Object> param)throws Exception;
}
