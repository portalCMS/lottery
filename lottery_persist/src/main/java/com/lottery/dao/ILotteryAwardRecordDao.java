package com.lottery.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.LotteryAwardRecord;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.LotteryAwardRecordVO;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface ILotteryAwardRecordDao  extends IGenericDao<LotteryAwardRecord>{

	public List<LotteryAwardRecord> queryRecentAwardRecord(final Map<String, Object> param) throws Exception;

	public Page<LotteryAwardRecordVO, LotteryAwardRecord> queryFailedTask(final Map<String, Object> param) throws Exception;

	public void cancelAward(final Map<String, Object> param) throws Exception;

	public List<LotteryAwardRecord> queryLimitAwardRecord(final Map<String, Object> param) throws Exception;

	public List<LotteryAwardRecord> queryLimitAwardRecord2(final Map<String, Object> param) throws Exception;

	public Page<LotteryAwardRecordVO, LotteryAwardRecord> queryOneRecord(Map<String, Object> param)throws Exception;
	
	public Page<LotteryAwardRecordVO, LotteryAwardRecord> queryCodesRecord(Map<String, Object> param) throws Exception;

}
