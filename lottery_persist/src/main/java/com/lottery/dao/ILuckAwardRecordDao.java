package com.lottery.dao;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.LuckAwardRecord;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface ILuckAwardRecordDao extends IGenericDao<LuckAwardRecord>{

}
