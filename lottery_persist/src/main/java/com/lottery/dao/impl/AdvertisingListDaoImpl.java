package com.lottery.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.AdvertisingList;
import com.lottery.dao.IAdvertisingListDao;
import com.lottery.persist.generice.GenericDAO;

@Repository
public class AdvertisingListDaoImpl extends GenericDAO<AdvertisingList> implements IAdvertisingListDao{

	public AdvertisingListDaoImpl() {
		super(AdvertisingList.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdvertisingList> queryAdvertsByRegionCode(String code) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer queryString = new StringBuffer("from AdvertisingList t where regionCode = ? order by index desc ");
		Query query = getSession().createQuery(queryString.toString());
		query.setParameter(0, code);
		return query.list();
	}

}
