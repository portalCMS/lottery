package com.lottery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.LotteryAwardRecord;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.LotteryAwardRecordVO;

@Service
public interface ILotteryAwardRecordService {

	/**
	 * 保存开奖记录
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public LotteryAwardRecord insertAwardRecord(final Map<String, Object> param) throws Exception;

	/**
	 * 更新开奖记录
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public LotteryAwardRecord updateAwardRecord(final Map<String, Object> param) throws Exception;

	/**
	 * 获取开奖记录集合
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<LotteryAwardRecord> queryRecentAwardRecord(final Map<String, Object> param) throws Exception;
	
	/**
	 * 根据奖期和彩种code查询开奖号码
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String queryOpenbetNumberbylotteryCodeAndissue(final Map<String, ?> param)throws Exception;

	/**
	 * 查询失败奖期任务
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<LotteryAwardRecordVO, LotteryAwardRecord> queryFailedTask(final Map<String, Object> param)throws Exception;

	/**
	 * 停止自动开奖
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public LotteryAwardRecord updateAutoTask(final Map<String, Object> param)throws Exception;

	/**
	 * 根据ID查
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public LotteryAwardRecord queryRecordById(final Map<String, Object> param)throws Exception;

	/**
	 * 手动开奖
	 * @param param
	 * @throws Exception
	 */
	public void updateHandAward(final Map<String, Object> param)throws Exception;

	/**
	 * 撤销某期中奖
	 * @param param
	 * @throws Exception
	 */
	public void updateCancelAward(final Map<String, Object> param)throws Exception;

	/**
	 * 查询冷热遗漏
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Map<String,Integer>> queryHotMissingRecord(final Map<String, Object> param)throws Exception;

	/**
	 * 最近一天开奖结果的号码冷热
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<LotteryAwardRecord> queryLimitAwardRecord2(final Map<String, Object> param)throws Exception;
	/**
	 * 分页查询开奖记录，参数彩种代码和奖期号。
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<LotteryAwardRecordVO, LotteryAwardRecord> queryOneRecord(Map<String, Object> param)throws Exception;
	
	public Page<LotteryAwardRecordVO, LotteryAwardRecord> queryCodesRecord(Map<String, Object> param) throws Exception;

}
