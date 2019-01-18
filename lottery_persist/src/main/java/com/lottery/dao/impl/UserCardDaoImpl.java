package com.lottery.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.SecurityQuestion;
import com.lottery.bean.entity.UserCard;
import com.lottery.dao.ISecurityQuestionDao;
import com.lottery.dao.IUserCardDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.util.DataDictionaryUtil;

@Repository
public class UserCardDaoImpl extends GenericDAO<UserCard> implements IUserCardDao{

	public UserCardDaoImpl() {
		super(UserCard.class);
	}

	@Override
	public List<UserCard> queryUserCardByUserId(long userId) throws Exception {
		String hql ="from UserCard t where t.customerId = ? and status=?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, userId);
		query.setParameter(1, DataDictionaryUtil.STATUS_OPEN);
		return query.list();
	}

}
