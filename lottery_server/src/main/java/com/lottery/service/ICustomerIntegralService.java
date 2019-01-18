package com.lottery.service;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.vo.CustomerIntegralVO;

@Service
public interface ICustomerIntegralService {

	/**
	 * 获取自己积分
	 * @param customerId
	 * @return
	 * @throws Exception
	 */
	public CustomerIntegralVO getSelfIntegral(long customerId)throws Exception;
	
	/**
	 * 更新今日积分
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public CustomerIntegralVO updateRegistration(CustomerUser user)throws Exception;
}
