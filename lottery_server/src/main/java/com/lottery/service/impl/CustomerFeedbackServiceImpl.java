package com.lottery.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.CustomerFeedback;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerFeedbackVO;
import com.lottery.dao.ICustomerFeedbackDao;
import com.lottery.dao.impl.CustomerUserWriteLog;
import com.lottery.service.ICustomerFeedbackService;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DozermapperUtil;

@Service
public class CustomerFeedbackServiceImpl implements ICustomerFeedbackService{

	@Autowired
	private ICustomerFeedbackDao feedBackDao; 
	
	@Autowired
	private CustomerUserWriteLog userlog;
	
	@Override
	public String saveCustomerFeedback(Map<String, Object> param)throws Exception {
		CustomerFeedbackVO vo = (CustomerFeedbackVO) param.get("cfvo");
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		CustomerFeedback entity = new CustomerFeedback();
		DozermapperUtil.getInstance().map(vo, entity);
//		entity.setTypeName(vo.getTypeName());
//		entity.setPageName(vo.getPageName());
//		entity.setDsce(vo.getDsce());
		entity.setCustomerId(user.getId());
		entity.setStatus(DataDictionaryUtil.STATUS_OPEN);
		entity.addInit(user.getCustomerName());
		feedBackDao.save(entity);
		return null;
	}

	@Override
	public Page<CustomerFeedbackVO, CustomerFeedback> queryCustomerFeedback(
			Map<String, Object> param) throws Exception {
		return feedBackDao.queryCustomerFeedback(param);
	}

}
