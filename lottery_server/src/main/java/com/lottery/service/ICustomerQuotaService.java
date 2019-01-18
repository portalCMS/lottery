package com.lottery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.CustomerQuota;

@Service
public interface ICustomerQuotaService{

	/**
	 * 查询用户配额
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CustomerQuota> findCustomerUser(final Map<String,?> param)throws Exception;
	
	/**
	 * 保存更新用户配额
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String saveOrUpdateCustomerQuota(final Map<String, ?> param)throws Exception;
}
