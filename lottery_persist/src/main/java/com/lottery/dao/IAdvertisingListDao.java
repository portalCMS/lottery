package com.lottery.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.AdvertisingList;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface IAdvertisingListDao extends IGenericDao<AdvertisingList>{

	public List<AdvertisingList> queryAdvertsByRegionCode(String code)throws Exception;
}
