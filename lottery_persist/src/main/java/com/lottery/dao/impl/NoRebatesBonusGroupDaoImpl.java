package com.lottery.dao.impl;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.NoRebatesBonusGroup;
import com.lottery.dao.INoRebatesBonusGroupDao;
import com.lottery.persist.generice.GenericDAO;

@Repository
public class NoRebatesBonusGroupDaoImpl extends GenericDAO<NoRebatesBonusGroup>implements INoRebatesBonusGroupDao{

	public NoRebatesBonusGroupDaoImpl() {
		super(NoRebatesBonusGroup.class);
	}

}
