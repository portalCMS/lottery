package com.lottery.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerOrder;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerOrderStaVo;
import com.lottery.bean.entity.vo.CustomerOrderVO;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface ICustomerOrderDao extends IGenericDao<CustomerOrder> {

	public void saveSignleRecharge(CustomerOrder order) throws Exception;

	public int countDrawingTimesToday(CustomerOrderVO orderVo) throws Exception;

	public int countRechargeTimesToday(CustomerOrderVO orderVo) throws Exception;

	public Page<CustomerOrderVO, CustomerOrder> queryUserOrderByPage(CustomerOrderVO orderVo) throws Exception;

	public BigDecimal sumTotalDrawingAmount(CustomerOrderVO newVo) throws Exception;

	public Page<CustomerOrderVO, CustomerOrder> queryAllOrderByPage(final Map<String, Object> param) throws Exception;

	/**
	 * 此方法给后台用户使用 前台不能使用此方法
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CustomerOrder queryOrderInfoByType(final Map<String, Object> param) throws Exception;

	public Page<CustomerOrderVO, CustomerOrder> queryTeamOrdersByPage(Map<String, Object> param) throws Exception;

	public CustomerOrder queryOrderByNum(final String sourceOrderNumber) throws Exception;

	public CustomerOrder queryDrawingOrderById(Long orderId) throws Exception;

	public Page<CustomerOrderVO, CustomerOrder> queryMyLotteryOrdersByPage(Map<String, Object> param) throws Exception;

	public Page<CustomerOrderVO, CustomerOrder> queryTraceOrdersByPage(final Map<String, Object> param)
			throws Exception;

	public Page<CustomerOrderVO, CustomerOrder> queryRevenueLower(final Map<String, Object> param) throws Exception;

	public boolean checkIssueAward(final Map<String, Object> param) throws Exception;

	public boolean checkBetRebates(final Map<String, Object> param) throws Exception;

	/**
	 * 查询最新中奖纪录
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CustomerOrder> queryNewWinningOrder(final Map<String, Object> param) throws Exception;

	public Map<String, Object> chargeDrawCount(final Map<String, Object> param) throws Exception;

	/**
	 * 查询个人当天盈亏记录。
	 * 
	 * @param param
	 * @return 返回符合条件的个人盈亏记录。
	 * @throws Exception
	 */
	List<CustomerOrderStaVo> queryDayYkRecords(Map<String, Object> param) throws Exception;

	/**
	 * 查询个人历史盈亏记录。
	 * 
	 * @param param
	 * @return 返回符合条件的个人盈亏记录。
	 * @throws Exception
	 */
	List<CustomerOrderStaVo> queryHistoryYkRecords(Map<String, Object> param) throws Exception;
}
