package com.lottery.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.BonusGroup;
import com.lottery.dao.IBonusGroupDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.util.DataDictionaryUtil;

@Repository
public class BonusGroupDaoImpl extends GenericDAO<BonusGroup> implements IBonusGroupDao{

	public BonusGroupDaoImpl() {
		super(BonusGroup.class);
	}

	@Override
	public List<BonusGroup> findBonusGroupAll(Map<String, ?> param) {
		// TODO Auto-generated method stub
		StringBuffer query = new StringBuffer("from BonusGroup t where t.status = ?");
		List<BonusGroup> beanlist = queryForList(query.toString(), null,new Object[]{DataDictionaryUtil.STATUS_OPEN});
		return beanlist;
	}

}
