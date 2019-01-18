package com.lottery.service.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.AdvertisingList;
import com.lottery.dao.IAdvertisingListDao;
import com.lottery.service.IAdvertisingListService;

@Service
public class AdvertisingListServiceImpl implements IAdvertisingListService{
	
	@Autowired
	private IAdvertisingListDao advertisingListDao;

	@Override
	public List<AdvertisingList> getAdvertisingLists(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		String code = (String) param.get("regionCode");
		StringBuffer queryString = new StringBuffer("from AdvertisingList t where t.regionCode like ? ");
		Query query = advertisingListDao.getSession().createQuery(queryString.toString());
		query.setParameter(0, code+"%");
		return query.list();
	}

}
