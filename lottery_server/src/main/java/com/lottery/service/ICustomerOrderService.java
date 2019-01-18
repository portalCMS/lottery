package com.lottery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.CustomerOrder;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerOrderStaVo;
import com.lottery.bean.entity.vo.CustomerOrderVO;

@Service
public interface ICustomerOrderService {

	/**
	 * 保存单点充值订单
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public boolean saveSignleRechargeOrDarwing(final Map<String, Object> param) throws Exception;

	/**
	 * 保存提款订单
	 * 
	 * @param param
	 * @throws Exception
	 */
	public void saveDrawingRqeuest(final Map<String, Object> param) throws Exception;

	/**
	 * 获取当天剩余的提款次数
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int countDrawingTimesToday(final Map<String, Object> param) throws Exception;

	/**
	 * 获取今日充值时间
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int countRechargeTimesToday(final Map<String, Object> param) throws Exception;

	/**
	 * 保存用户充值订单
	 * 
	 * @param param
	 * @throws Exception
	 */
	public void saveUserRecharge(final Map<String, Object> param) throws Exception;

	/**
	 * 查询充值订单 翻页
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<CustomerOrderVO, CustomerOrder> updateQueryRechargeOrderByPage(final Map<String, Object> param)
			throws Exception;

	/**
	 * 查询提款订单 翻页
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<CustomerOrderVO, CustomerOrder> queryDrawingOrderByPage(final Map<String, Object> param)
			throws Exception;

	/**
	 * 通过审核充值订单 通过参数配置生成手续费
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CustomerOrder saveApproveRechargeOrder(final Map<String, Object> param) throws Exception;

	/**
	 * 驳回充值
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CustomerOrder saveRejectRechargeOrder(final Map<String, Object> param) throws Exception;

	/**
	 * 显示提款订单明细
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> showDrawingOrderInfo(final Map<String, Object> param) throws Exception;

	/**
	 * 显示充值订单明细
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CustomerOrder showRechargeOrderInfo(final Map<String, Object> param) throws Exception;

	/**
	 * 过期订单重启（订单状态设置为处理中，过期时间调整为当前时间加上配置的过期时间）
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CustomerOrder updateRestartOrder(final Map<String, Object> param) throws Exception;

	/**
	 * 审核驳回提款订单（修改订单状态为失败，userCash的冻结金额减少）
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CustomerOrder saveRejectDrawingOrder(final Map<String, Object> param) throws Exception;

	/**
	 * 审核通过提款订单（修改订单状态为成功，userCash的冻结金额减少，实际金额也减少）
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CustomerOrder saveApproveDrawingOrder(final Map<String, Object> param) throws Exception;

	/**
	 * 统计订单明细数量
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int countDisposingOrder(Map<String, Object> param) throws Exception;

	/**
	 * 获取所有订单数量
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public long getAllOrderCount(Map<String, Object> param) throws Exception;

	/**
	 * 获取所有订单集合 翻页
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<CustomerOrderVO, CustomerOrder> queryAllOrderByPage(final Map<String, Object> param) throws Exception;

	/**
	 * 根据类型获取订单信息
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CustomerOrderVO queryOrderInfoByType(final Map<String, Object> param) throws Exception;

	/**
	 * 前台订单分页查询
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<CustomerOrderVO, CustomerOrder> queryTeamOrdersByPage(final Map<String, Object> param) throws Exception;

	/**
	 * 根据ID查询订单信息
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CustomerOrder queryOrderById(final Map<String, Object> param) throws Exception;
	
	/**
	 * 根据ID查询订单备注信息
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CustomerOrder queryResultOrderById(final Map<String, Object> param) throws Exception;

	/**
	 * 根据ID查询提款订单信息
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CustomerOrder queryDrawingOrderById(final Map<String, Object> param) throws Exception;

	/**
	 * 查询我的订单信息集合 翻页
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<CustomerOrderVO, CustomerOrder> queryMyOrdersByPage(final Map<String, Object> param) throws Exception;

	/**
	 * 查询追期订单信息集合 翻页
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<CustomerOrderVO, CustomerOrder> queryTraceOrders(final Map<String, Object> param) throws Exception;

	/**
	 * 查询下级收入信息集合 翻页
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<CustomerOrderVO, CustomerOrder> queryRevenueLower(final Map<String, Object> param) throws Exception;

	/**
	 * 查询最新中奖纪录
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CustomerOrderVO> queryNewWinningOrder(final Map<String, Object> param) throws Exception;

	/**
	 * 以map形式获取充值次数
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> chargeDrawCount(final Map<String, Object> param) throws Exception;

	/**
	 * 创建第三方支付订单
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CustomerOrder saveOtherPayOrder(final Map<String, Object> param) throws Exception;

	/**
	 * 查询活动领取记录
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<CustomerOrderVO, CustomerOrder> queryActiveOrder(final Map<String, Object> param) throws Exception;

	/**
	 * 通过审核活动领取订单
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CustomerOrder saveApproveActivityOrder(final Map<String, Object> param) throws Exception;

	/**
	 * 驳回活动领取订单申请
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CustomerOrder saveRejectActivityOrder(final Map<String, Object> param) throws Exception;

	/**
	 * 第三方充值成功后修改订单状态
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String updateOtherPayOrderSuccess(final Map<String, Object> param) throws Exception;

	/**
	 * 检查用户是否安全
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public boolean checkCustomerIsSafe(final Map<String, Object> param) throws Exception;

	/**
	 * 查询用户第三方充值次数
	 * 
	 * @param param
	 * @return
	 */
	public int countOtherPlayTimes(final Map<String, Object> param) throws Exception;

	/**
	 * 查询个人盈亏记录。
	 * 
	 * @param param
	 * @return 返回符合条件的个人盈亏记录。
	 * @throws Exception
	 */
	List<CustomerOrderStaVo> queryDayYkRecords(Map<String, Object> param) throws Exception;
	
	/**
	 * 查询个人盈亏记录。
	 * 
	 * @param param
	 * @return 返回符合条件的个人盈亏记录。
	 * @throws Exception
	 */
	List<CustomerOrderStaVo> queryHistoryYkRecords(Map<String, Object> param) throws Exception;
	
	/**
	 * 查询个人盈亏记录(图像报表)。
	 * 
	 * @param param
	 * @return 返回符合条件的个人盈亏记录。
	 * @throws Exception
	 */
	List<CustomerOrderStaVo> queryHistoryYkExport(Map<String, Object> param) throws Exception;
}
