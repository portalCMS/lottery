package com.lottery.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.LotteryPlayBonus;
import com.lottery.bean.entity.vo.LotteryPlayBonusVO;
import com.lottery.persist.generice.IGenericDao;
import com.xl.lottery.exception.LotteryException;

@Repository
public interface ILotteryPlayBonusDao extends IGenericDao<LotteryPlayBonus>{

	public List<LotteryPlayBonus> queryPlayBonusBylotteryAndPlay(LotteryPlayBonusVO lotteryVo);

	public LotteryPlayBonus queryPlayBonusBylpm(LotteryPlayBonusVO lpbVo) throws LotteryException;
	
	public List<LotteryPlayBonus>  queryPlayBonusByUserId(LotteryPlayBonusVO lpbVo) throws LotteryException;

}
