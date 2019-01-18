package com.lottery.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.DataDictionary;
import com.lottery.dao.IDataDictionaryDao;
import com.lottery.service.IDataDictionaryService;

@Service
public class DataDictionaryServiceImpl implements IDataDictionaryService{


	@Autowired
	private IDataDictionaryDao dataDictionaryDao;
	
	@Override
	public List<DataDictionary> getDataDictionarysByFid(
			Map<String, Object> param)throws Exception {
		// TODO Auto-generated method stub
		long fid = (Long) param.get("fidkey");
		StringBuffer queryString = new StringBuffer("from DataDictionary t where t.fid = ? and t.sid != 0 and t.status = 10002 ");
		Query query = dataDictionaryDao.getSession().createQuery(queryString.toString());
		query.setParameter(0, fid);
		List<DataDictionary> dataDictionaries = query.list();
		if(dataDictionaries.size()>0)return dataDictionaries;
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getSnameBySid(long sid) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer queryString = new StringBuffer("from DataDictionary t where t.sid = ? and t.status = 10002 ");
		Query query = dataDictionaryDao.getSession().createQuery(queryString.toString());
		query.setParameter(0, sid);
		List<DataDictionary> dataDictionaries = query.list();
		if(dataDictionaries.size()>0)return dataDictionaries.get(0).getSname();
		return "";
	}

	@Override
	public Map<Long, String> queryAllDataDictionary() throws Exception {
		// TODO Auto-generated method stub
		Map<Long, String> dataMap = new HashMap<Long, String>();
		List<DataDictionary> datas = dataDictionaryDao.queryAll();
		for(DataDictionary data:datas){
			dataMap.put(data.getSid(), data.getSname());
		}
		return dataMap;
	}

	@Override
	public List<DataDictionary> getAllDataDictionary() throws Exception {
		// TODO Auto-generated method stub
		List<DataDictionary> datas = dataDictionaryDao.queryAll();
		return datas;
	}
	

}
