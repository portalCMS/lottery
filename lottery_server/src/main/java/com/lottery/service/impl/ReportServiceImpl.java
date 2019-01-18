package com.lottery.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.Page;
import com.lottery.dao.IBetRecordDao;
import com.lottery.service.IReportService;

@Service
public class ReportServiceImpl implements IReportService{

	@Autowired
	private IBetRecordDao betRecordDao;
	
	@Override
	public Page<Object, Object> queryYkReport(Map<String, Object> param)
			throws Exception {
		Page<Object, Object> page = betRecordDao.queryYkReport(param);
		return page;
	}

	@Override
	public Page<Object, Object> queryYkReportAdmin(Map<String, Object> param)
			throws Exception {
		return betRecordDao.queryYkReportAdmin(param);
	}

	@Override
	public Page<Object, Object> queryYkRecord(Map<String, Object> param) throws Exception {
		return betRecordDao.queryYkRecord(param);
	}

}
