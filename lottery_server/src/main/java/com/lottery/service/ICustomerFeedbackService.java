package com.lottery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.CustomerFeedback;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerFeedbackVO;

@Service
public interface ICustomerFeedbackService {

	/**
	 * 保存客户反馈
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String saveCustomerFeedback(final Map<String,Object> param)throws Exception;
	
	/**
	 * 客户反馈信息翻页
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<CustomerFeedbackVO, CustomerFeedback> queryCustomerFeedback(final Map<String,Object> param)throws Exception;
}
