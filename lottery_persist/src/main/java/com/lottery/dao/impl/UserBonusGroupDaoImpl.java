package com.lottery.dao.impl;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.UserBonusGroup;
import com.lottery.dao.IUserBonusGroupDao;
import com.lottery.persist.generice.GenericDAO;

@Repository
public class UserBonusGroupDaoImpl extends GenericDAO<UserBonusGroup>implements IUserBonusGroupDao{

	public UserBonusGroupDaoImpl() {
		super(UserBonusGroup.class);
	}

}
