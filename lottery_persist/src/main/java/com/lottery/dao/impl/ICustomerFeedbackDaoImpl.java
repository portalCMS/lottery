package com.lottery.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.lottery.bean.entity.ArticleManage;
import com.lottery.bean.entity.CustomerFeedback;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.ArticleManageVO;
import com.lottery.bean.entity.vo.CustomerFeedbackVO;
import com.lottery.dao.ICustomerFeedbackDao;
import com.lottery.dao.ICustomerUserDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;

@Repository
public class ICustomerFeedbackDaoImpl extends GenericDAO<CustomerFeedback> implements ICustomerFeedbackDao{

	@Autowired
	private ICustomerUserDao userDao;
	
	public ICustomerFeedbackDaoImpl() {
		super(CustomerFeedback.class);
	}

	@Override
	public Page<CustomerFeedbackVO, CustomerFeedback> queryCustomerFeedback(
			Map<String, Object> param) throws Exception {
		CustomerFeedbackVO vo = (CustomerFeedbackVO) param.get("cfvo");
		
		List<String> limitKeys = new ArrayList<String>();
		List<String> formula = new ArrayList<String>();
		List<Object> limitVals = new ArrayList<Object>();
		
		formula.add("=");
		limitKeys.add("status");
		limitVals.add(DataDictionaryUtil.STATUS_OPEN);
		
		Page<CustomerFeedbackVO, CustomerFeedback> page = (Page<CustomerFeedbackVO, CustomerFeedback>)doPageQuery
				(vo, CustomerFeedback.class,formula, limitKeys, limitVals,true);
		
		for(CustomerFeedback fb : page.getEntitylist()){
			CustomerUser user = userDao.queryById(fb.getCustomerId());
			fb.setCustomerName(user.getCustomerName());
		}
		return page;
	}

}
