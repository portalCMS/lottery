package com.lottery.dao.impl;


import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.lottery.bean.entity.CustomerActivity;
import com.lottery.bean.entity.LuckAwardRecord;
import com.lottery.dao.ILuckAwardRecordDao;
import com.lottery.persist.generice.GenericDAO;


@Component
public class LuckAwardRecordDaoImpl extends GenericDAO<LuckAwardRecord> implements ILuckAwardRecordDao {

	public LuckAwardRecordDaoImpl() {
		super(LuckAwardRecord.class);
	}

	

}
