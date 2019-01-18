package com.lottery.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.SecurityQuestion;
import com.lottery.dao.ISecurityQuestionDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.util.DataDictionaryUtil;

@Repository
public class SecurityQuestionDaoImpl extends GenericDAO<SecurityQuestion> implements ISecurityQuestionDao{

	public SecurityQuestionDaoImpl() {
		super(SecurityQuestion.class);
	}

	@Override
	public List<SecurityQuestion> findSecurityQuestionList(CustomerUser user) throws Exception {
		StringBuffer sql = new StringBuffer("from SecurityQuestion t where t.userId = ? and t.status=? order by createTime desc");
		Query query = getSession().createQuery(sql.toString());
		query.setParameter(0, user.getId());
		query.setParameter(1, DataDictionaryUtil.STATUS_OPEN);
		return query.list();
	}

}
