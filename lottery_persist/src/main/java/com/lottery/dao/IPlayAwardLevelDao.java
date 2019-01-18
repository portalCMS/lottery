package com.lottery.dao;

import java.math.BigDecimal;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.PlayAwardLevel;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface IPlayAwardLevelDao extends IGenericDao<PlayAwardLevel>{

	public BigDecimal getPlayAwardLevel(String playCode,String lotteryCode)throws Exception;
}
