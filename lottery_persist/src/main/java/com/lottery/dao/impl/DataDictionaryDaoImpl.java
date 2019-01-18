package com.lottery.dao.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.DataDictionary;
import com.lottery.dao.IDataDictionaryDao;
import com.lottery.dao.IOperationAdminLogDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.CommonUtil;

@Repository
public class DataDictionaryDaoImpl extends GenericDAO<DataDictionary> implements
		IDataDictionaryDao {

	private static Logger logger = LoggerFactory.getLogger(DataDictionaryDaoImpl.class);
	
	@Autowired
	private AdminWriteLog adminWriteLog;
	
	public DataDictionaryDaoImpl() {
		super(DataDictionary.class);
	}

	@Override
	public String findFnameByFid(Map<String,?> param)throws Exception{
		// TODO Auto-generated method stub
		int fid = (Integer) param.get("fid");
		if(fid==0){
			throw new LotteryException("参数fid为空");
		}
		StringBuffer queryString = new StringBuffer("from DataDictionary t where t.fid=?");
		DataDictionary dataDictionary = queryForTopObject(queryString.toString(),new Object[]{fid});
		return dataDictionary.getFname();
	}

	@Override
	public String findSnameBySid(Map<String,?> param)throws Exception{
		String sid = (String) param.get("sid");
		if(StringUtils.isEmpty(sid)){
			throw new LotteryException("参数sid为空");
		}
		StringBuffer queryString = new StringBuffer("from DataDictionary t where t.sid=?");
		DataDictionary dataDictionary = queryForTopObject(queryString.toString(),new Object[]{sid});
		return dataDictionary.getSname();
	}

}
