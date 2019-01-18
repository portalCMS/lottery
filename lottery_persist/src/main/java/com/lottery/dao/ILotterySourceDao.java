package com.lottery.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.LotterySource;
import com.lottery.bean.entity.vo.LotteryTypeVO;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface ILotterySourceDao extends IGenericDao<LotterySource>{

	public List<LotterySource> querySourceByLotteryCode(LotteryTypeVO lotteryVo);

}
