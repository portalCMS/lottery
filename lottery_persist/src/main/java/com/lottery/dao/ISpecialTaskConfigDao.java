package com.lottery.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.SpecialTaskConfig;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface ISpecialTaskConfigDao extends IGenericDao<SpecialTaskConfig>{

	public List<SpecialTaskConfig> queryByLotteryCode(String lotteryCode)throws Exception;


}
