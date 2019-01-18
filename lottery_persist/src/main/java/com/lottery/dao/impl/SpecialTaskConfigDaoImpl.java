package com.lottery.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.SpecialTaskConfig;
import com.lottery.dao.ISpecialTaskConfigDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;

@Repository
public class SpecialTaskConfigDaoImpl extends GenericDAO<SpecialTaskConfig> implements ISpecialTaskConfigDao{

	public SpecialTaskConfigDaoImpl() {
		super(SpecialTaskConfig.class);
	}

	@Override
	public List<SpecialTaskConfig> queryByLotteryCode(String lotteryCode)
			throws Exception {
		String hql ="from SpecialTaskConfig where lotteryCode = ? and status=?";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, lotteryCode);
		query.setParameter(1, DataDictionaryUtil.COMMON_FLAG_1);
		return query.list();
	}


	
}
