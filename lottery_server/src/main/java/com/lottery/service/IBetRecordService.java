package com.lottery.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.BetRecord;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.BetRecordVO;
import com.lottery.bean.entity.vo.CustomerOrderStaVo;

@Service
public interface IBetRecordService {
	/**
	 * 查询彩种当前期的投注记录并检验是否已经中奖，派奖。
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<BetRecord> openLotteryResult(final Map<String, Object> param)throws Exception;
	/**
	 * 保存投注记录
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String saveBetRecordOrder(final Map<String, ?> param)throws Exception;
	/**
	 * 派发对应彩种奖期的返点金额，并且给追期订单的完成期数加1
	 * @param param1
	 * @throws Exception
	 */
	public void updatePayBetRebates(final Map<String, Object> param1)throws Exception;
	
	/**
	 * 后台投注单查询
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<BetRecordVO, BetRecord> queryBetRecords(final Map<String,?> param)throws Exception;
	/**
	 * 通过id查询投注记录详细信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public BetRecordVO getBetRecordInfoById(final Map<String,?> param)throws Exception;
	
	/**
	 * 统计自己团队投注金额(不包括自己)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public BigDecimal getLowerLevelSumAmount(final Map<String, ?> param)throws Exception;
	
	/**
	 * 前台投注单查询
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<BetRecordVO,BetRecord> queryBetRecordsWebApp(final Map<String,Object> param)throws Exception;
	
	
	/**
	 * 前台自己投注单查询
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<BetRecordVO,BetRecord> queryBetRecordsWebAppBySelf(final Map<String,Object> param)throws Exception;
	
	
	/**
	 * 追号单集合
	 */
	public List<BetRecord> queryBetRecordsByOrderNo(final Map<String,Object> param)throws Exception;
	
	/**
	 * 追号单集合翻译
	 * @param param
	 * @throws Exception
	 */
	public List<BetRecordVO> queryBetRecordVOsByOrderNo(final Map<String,Object> param)throws Exception;
	/**
	 * 给中奖记录的备用字段加密，标识是程序执行的结果，而非人工修改。
	 * @param param
	 * @throws Exception
	 */
	public void updateAwardEncrypt(final Map<String, Object> param)throws Exception;
	
	/**
	 * 中奖终止投注处理
	 * @param param
	 * @throws Exception
	 */
	public void updateBetAwardStop(Map<String, Object> param)throws Exception;
	
	/**
	 * 取消投注单
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String cancelBetRecord(Map<String,Object> param)throws Exception;
	
	/**
	 * 撤销投注方案
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String cancelPlanInfo(Map<String,Object> param)throws Exception;
	
	/**
	 * 彩种销售金额统计
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, BigDecimal> lotterySalesInfo(Map<String,Object> param)throws Exception;
	/**
	 * 后台撤销投注单(系统撤销)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String updateCancelBetByAdmin(Map<String, Object> param) throws Exception;
	
	
	/**
	 * 根据订单号、期号查询投注信息。
	 * @param issueNo
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	List<BetRecord> queryBetRecord(Map<String, Object> param) throws Exception;
	
	/**
	 * 查询团队盈亏报表。
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<CustomerOrderStaVo> queryTeamYkReport(Map<String, Object> param)throws Exception; 
	
	/**
	 * 查询团队盈亏报表。
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<CustomerOrderStaVo> queryTeamYkRecords(Map<String, Object> param)throws Exception;
}
