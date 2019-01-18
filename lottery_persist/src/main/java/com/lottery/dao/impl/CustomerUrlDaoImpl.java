package com.lottery.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerUrl;
import com.lottery.bean.entity.DomainUrl;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerUrlVO;
import com.lottery.bean.entity.vo.DomainUrlVO;
import com.lottery.dao.ICustomerUrlDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;

@Repository
public class CustomerUrlDaoImpl extends GenericDAO<CustomerUrl> implements
		ICustomerUrlDao {

	private static Logger logger = LoggerFactory.getLogger(CustomerUrlDaoImpl.class);
	
	public CustomerUrlDaoImpl() {
		super(CustomerUrl.class);
	}

	@Override
	public Page<CustomerUrlVO, CustomerUrl> findCustomerUrlList(
			Map<String, ?> param) throws Exception {
		
		CustomerUrlVO customerUrlVo = (CustomerUrlVO) param.get("customerurlkey");
		CustomerUrl entity = new CustomerUrl();
		List<String> keys = new ArrayList<String>();
		keys.add("status");
		List<Object> vals = new ArrayList<Object>();
		vals.add(DataDictionaryUtil.STATUS_OPEN);
		List<String> formula = new ArrayList<String>();
		formula.add("=");
		@SuppressWarnings("unchecked")
		Page<CustomerUrlVO, CustomerUrl> page =(Page<CustomerUrlVO, CustomerUrl>) doPageQuery(customerUrlVo,entity,formula,keys,vals);
		return page;
	}
	
	protected int findCustomerUrlCount() {
		int count = ((BigInteger) getSession().createSQLQuery("select count(1) from t_customer_url t where t.url_status="+DataDictionaryUtil.STATUS_OPEN).list().get(0)).intValue();
		return count;
	}

	@Override
	public CustomerUrl findCustomerUrlList(String urlName) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sqlquery = new StringBuffer("from CustomerUrl t where t.url = ? and t.urlStatus=?");
		CustomerUrl customerUrl = queryForObject(sqlquery.toString(),new Object[]{urlName,DataDictionaryUtil.STATUS_OPEN});
		return customerUrl;
	}

	@Override
	public List<CustomerUrl> findCustomerUrllist(Map<String,?> param) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sqlquery = new StringBuffer("from CustomerUrl t where t.urlStatus=?");
		List<CustomerUrl> urllist = queryForList(sqlquery.toString(),null,new Object[]{DataDictionaryUtil.STATUS_OPEN});
		return urllist;
	}

}
