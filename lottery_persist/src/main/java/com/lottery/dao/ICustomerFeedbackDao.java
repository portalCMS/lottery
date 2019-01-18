package com.lottery.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerFeedback;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerFeedbackVO;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface ICustomerFeedbackDao extends IGenericDao<CustomerFeedback>{

	public Page<CustomerFeedbackVO, CustomerFeedback> queryCustomerFeedback(final Map<String, Object> param)throws Exception;

}
