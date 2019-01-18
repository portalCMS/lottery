package com.lottery.dao.impl;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.AdvertisingRegion;
import com.lottery.dao.IAdvertisingRegionDao;
import com.lottery.persist.generice.GenericDAO;

@Repository
public class AdvertisingRegionDaoImpl extends GenericDAO<AdvertisingRegion> implements IAdvertisingRegionDao{

	public AdvertisingRegionDaoImpl() {
		super(AdvertisingRegion.class);
	}

}
