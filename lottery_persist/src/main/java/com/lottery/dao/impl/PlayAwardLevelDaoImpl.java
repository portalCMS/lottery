package com.lottery.dao.impl;

import java.math.BigDecimal;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.PlayAwardLevel;
import com.lottery.dao.IPlayAwardLevelDao;
import com.lottery.persist.generice.GenericDAO;

@Repository
public class PlayAwardLevelDaoImpl extends GenericDAO<PlayAwardLevel> implements IPlayAwardLevelDao{

	public PlayAwardLevelDaoImpl() {
		super(PlayAwardLevel.class);
	}

	@Override
	public BigDecimal getPlayAwardLevel(String playCode, String lotteryCode)
			throws Exception {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer("from PlayAwardLevel t where t.playCode=? and t.lotteryCode=? and t.status=?");
		Query query = getSession().createQuery(hql.toString());
		query.setParameter(0, playCode);
		query.setParameter(1, lotteryCode);
		query.setParameter(2, 1);
		PlayAwardLevel pal = (PlayAwardLevel) query.list().get(0);
		return pal.getWinAmount();
	}

}
