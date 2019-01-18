package com.lottery.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.LotteryPlaySelect;
import com.lottery.bean.entity.vo.LotteryPlaySelectVO;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface ILotteryPlaySelectDao extends IGenericDao<LotteryPlaySelect>{

	public List<LotteryPlaySelect> querySelectByModel(LotteryPlaySelectVO selectVo) throws Exception;
	
	public LotteryPlaySelect queryPlaySelectByCode(final String selectCode)throws Exception;

}
