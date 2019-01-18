package com.lottery.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.LotterySource;
import com.lottery.bean.entity.PlayModel;
import com.lottery.bean.entity.vo.LotteryTypeVO;
import com.lottery.dao.ILotterySourceDao;
import com.lottery.dao.IPlayModelDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.util.DataDictionaryUtil;

@Repository
public class LotterySourceDaoImpl extends GenericDAO<LotterySource> implements ILotterySourceDao{

	public LotterySourceDaoImpl() {
		super(LotterySource.class);
	}

	@Override
	public List<LotterySource> querySourceByLotteryCode(LotteryTypeVO lotteryVo) {
		String hql ="from LotterySource where lotteryCode = ? and lotteryGroup = ? ";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, lotteryVo.getLotteryCode());
		query.setParameter(1, lotteryVo.getLotteryGroup());
		return query.list();
	}


}
