package com.lottery.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.CustomerCash;

@Service
public interface ICustomerCashService{

	/**
	 * 更新用户余额
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String updateCustomerCash(final Map<String, ?> param)throws Exception;
	
	/**
	 * 向下充值
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String updateCustomerCashToLowerCustomerCash(final Map<String, ?> param)throws Exception;
	
	/**
	 * 根据用户ID获取余额
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CustomerCash findCustomerCashByUserId(final Map<String,?> param)throws Exception;
}
