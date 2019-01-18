package com.lottery.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.BetRecord;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.BetRecordVO;
import com.lottery.bean.entity.vo.CustomerOrderStaVo;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface IBetRecordDao extends IGenericDao<BetRecord>{

	public String updateBetWinning(final Map<String,Object> param) throws Exception;

	public void updateAwardUser(final Map<String, Object> param)throws Exception;

	public void updatePayBetRebates(final Map<String, Object> param)throws Exception;
	
	/**
	 * 后台投注单查询
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<BetRecordVO, BetRecord> queryBetRecords(final Map<String,?> param)throws Exception;
	
	
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

	public void updateBetNoWinner(final Map<String, Object> param);
	
	/**
	 * 追号单集合
	 */
	public List<BetRecord> queryBetRecordsByOrderNo(final Map<String,Object> param)throws Exception;

	public void updateAwardEncrypt(final Map<String, Object> param)throws Exception;

	public void updateBetAwardStop(final Map<String, Object> param)throws Exception;

	public Map<String, BigDecimal> lotterySalesInfo(final Map<String, Object> param)throws Exception;
	
	public void addBetRecordsByTrack(final long orderid,final String issueNo)throws Exception;
	
	/**
	 * 前台用户获取盈亏报表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Object,Object> queryYkReport(Map<String, Object> param)throws Exception;
	
	/**
	 * 后台用户获取盈亏报表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Object,Object> queryYkReportAdmin(Map<String, Object> param)throws Exception;
	
	/**
	 * 根据订单号、期号查询投注信息
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
	
	/**
	 * 分页统计团队历史盈亏记录。
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Page<Object, Object> queryYkRecord(Map<String, Object> param) throws Exception;
}
