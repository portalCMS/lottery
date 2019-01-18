package com.lottery.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerIpLog;
import com.lottery.bean.entity.CustomerMessage;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerIpLogVO;
import com.lottery.bean.entity.vo.CustomerMessageVO;
import com.lottery.dao.ICustomerMessageDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;

@Repository
public class CustomerMessageDaoImpl extends GenericDAO<CustomerMessage> implements ICustomerMessageDao{

	public CustomerMessageDaoImpl() {
		super(CustomerMessage.class);
	}

	@Override
	public Integer getMsgCount(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		StringBuffer queryString = new StringBuffer("select count(id) from CustomerMessage t ");
		queryString.append(" where to_customer_id = ? and status = ? ");
		Query query = getSession().createQuery(queryString.toString());
		query.setParameter(0, user.getId());
		query.setParameter(1, CommonUtil.MSG_STATUS_UNREAD);
		Long count = (Long) query.list().get(0);
		return count.intValue();
	}

	@Override
	public Page<CustomerMessageVO, CustomerMessage> queryMsgPage(
			Map<String, Object> param) throws Exception {
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		CustomerMessageVO vo = (CustomerMessageVO) param.get("messageKey");
		StringBuffer queryString = new StringBuffer("from CustomerMessage t where t.toUserId = ? order by t.createTime desc");
		StringBuffer hqlQueryCount = new StringBuffer("select count(t.id) from CustomerMessage t where t.toUserId = ? ");
		List<String> limitKeys = new ArrayList<String>();
		List<Object> limitVals = new ArrayList<Object>();
		limitVals.add(user.getId());
		Page<CustomerMessageVO, CustomerMessage> page = (Page<CustomerMessageVO, CustomerMessage>)
				pageQuery(vo, queryString,hqlQueryCount,limitKeys, limitVals);
		
		return page;
	}

}
