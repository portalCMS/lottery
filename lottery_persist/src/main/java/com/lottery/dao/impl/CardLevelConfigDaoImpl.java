package com.lottery.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CardLevelConfig;
import com.lottery.bean.entity.UserCardInventory;
import com.lottery.dao.ICardLevelConfigDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.util.DataDictionaryUtil;

@Repository
public class CardLevelConfigDaoImpl extends GenericDAO<CardLevelConfig> implements ICardLevelConfigDao{

	public CardLevelConfigDaoImpl() {
		super(CardLevelConfig.class);
	}

	@Override
	public List<CardLevelConfig> queryCardLevel(Map<String, Object> param)
			throws Exception {
		String hql="from CardLevelConfig where status = ? ";
		List<CardLevelConfig> list =getSession().createQuery(hql).
				setParameter(0, DataDictionaryUtil.STATUS_OPEN).list();
		return list;
	}

	@Override
	public void deleteCardLevel(Map<String, Object> param) throws Exception {
		String hql="update CardLevelConfig set status = ? where id > 0 ";
		Query query = getSession().createQuery(hql).setParameter(0, DataDictionaryUtil.STATUS_CLOSE);
		query.executeUpdate();
	}

}
