package com.lottery.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerUserVO;
import com.xl.lottery.exception.LotteryException;

@Service
public interface ICustomerUserService {
	
	/**
	 * 开设总代用户
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String saveMainCustomer(final Map<String,?> param)throws Exception;
	
	
	/**
	 * 检查用户名是否存在
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CustomerUser checkCustomerUser(final Map<String,?> param)throws Exception;
	
	
	/**
	 * 根据用户名查找用户
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CustomerUser findCustomerUserByName(final Map<String,?>param)throws Exception;
	
	/**
	 * 登录验证
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CustomerUser loginCheckCustomerUser(final Map<String,?> param)throws Exception;
	
	/**
	 * 保存用户密码
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CustomerUser saveCustomerUserPwd(final Map<String,?> param)throws Exception;

	/**
	 * 保存用户资金密码
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CustomerUser saveCustomerUserMoneyPwd(Map<String, Object> param)throws Exception;


	/**
	 * 保存用户信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CustomerUser savePersonInfo(Map<String, Object> param)throws Exception;
	
	/**
	 * 找回密码修改
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public boolean updateCusomerUserPwd(final Map<String,?> param)throws Exception;


	/**
	 * 保存用户绑卡信息
	 * @param param
	 * @throws Exception
	 */
	public void saveUserCard(Map<String, Object> param)throws Exception;
	
	/**
	 * 开设下级
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String saveOpenLowerUser(final Map<String, ?> param)throws Exception;
	
	/**
	 * 查询下级信息集合
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> findLowerLevelCustomerUserObjects(final Map<String,?> param)throws Exception;
	
	/***
	 * 统计下级信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CustomerUser> findLowerLevelCustomerUser(final Map<String,?> param)throws Exception;
	
	/**
	 * 更新用户信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CustomerUser updateCustomerData(final Map<String,?> param)throws Exception;
	
	/**
	 * 检查下级信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CustomerUser checkLowerCustomers(final Map<String,?> param)throws Exception;
	
	
	/**
	 * 查询总代用户
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> findMainCustomers(final Map<String, ?> param)throws Exception;
	
	
	/**
	 * 查找当前用户树
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> findCuserTree(final Map<String,?> param)throws Exception;
	
	
	/**
	 * 修改用户信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String updateUserInfo(final Map<String,?> param)throws Exception;
	
	/**
	 * 检查别名是否重复
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String checkAlias(final Map<String,?> param)throws Exception; 
	
	
	/**
	 * 前台查询团队树形结构投注金额统计
	 * @return
	 * @throws Exception
	 */
	public List<Object> queryTeamMoneyInfo(final Map<String,?> param)throws Exception;
	
	
	/**
	 * 前台查询下级团队树形结构翻页
	 * @return
	 * @throws Exception
	 */
	public Page<CustomerUserVO, Object> queryLowerLevels(final Map<String,?> param)throws Exception;
	
	/**
	 * 统计用户数量
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> queryUserCount()throws Exception;
	
	/**
	 * 检查是否已经开设前台注册用总代
	 * @return
	 * @throws Exception
	 */
	public boolean checkRegUserStatus()throws Exception;
	
	/**
	 * 获取前端注册用户总代ID
	 * @return
	 * @throws Exception
	 */
	public CustomerUser getRegProxyUser()throws Exception;
	/**
	 * 统计报表功能查询常规用户数据
	 * @return
	 * @throws Exception
	 */
	public Map<String, Integer> queryUserStatistic()throws Exception;
	/**
	 * 月用户活跃度统计
	 * @param param 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryMonthActiveUser(final Map<String, Object> param)throws Exception;
	/**
	 * 当月新增用户统计
	 * @param param 
	 * @return
	 * @throws Exception
	 */
	public Map<String, Integer> queryMonthAddUser(final Map<String, Object> param)throws Exception;
	/**
	 * 查询大户的所有类型订单金额数据
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, ?> queryProfileAmount(final Map<String, Object> param)throws Exception;
	/**
	 * 查询大户的所有类型订单金额数据分页信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, ?> queryProfileAmountMap(final Map<String, Object> param)throws Exception;
	/**
	 * 查询个人投注信息汇总
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, ?> queryProfileData(final Map<String, Object> param)throws Exception;
	/**
	 * 查询运营数据
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, ?> queryMarketStatistic(final Map<String, Object> param)throws Exception;
	/**
	 * 查询每日人均值及盈亏值数据
	 * @param param
	 * @return
	 */
	public Map<String, ?> queryAvgPerStasData(final Map<String, Object> param)throws Exception;
	/**
	 * 查询投注，充值，返奖，返点，网站余额
	 * @param param
	 * @return
	 */
	public Map<String, ?> queryAmountStasData(final Map<String, Object> param)throws Exception;
	

	/**
	 * 根据用户ID查询用户信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public CustomerUser queryUserById(long userId)throws Exception;
	
	/**
	 * 获取当前用户好友字符串 例如 111:aaa111(id编号:好友名称),222:aaa222
	 * 包含当前上级以及下级
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String queryUserFirends(CustomerUser user)throws Exception;
	
	
	/**
	 * 根据用户名查询用户
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public CustomerUser queryUserByName(String userName)throws Exception;
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public CustomerUser update(CustomerUser user)throws Exception;
	
	/**
	 * 更新下级返点
	 * @return
	 * @throws Exception
	 */
	public String updateLowerQuota(final Map<String, Object> param)throws Exception;
	/**
	 * 根据条件查询团队成员信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<CustomerUserVO, Object> queryTeamUserByKey(final Map<String, Object> param)throws Exception;
	/**
	 * 根据用户名重置前台用户密码
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public CustomerUser saveRestPwdByName(String userName)throws Exception;
	
	/**
	 * 查询个人团队成员（直接下级和全部成员）
	 * @param userId
	 * @return
	 */
	
	List<Object> queryTeamNum(String userId) throws LotteryException;
	
	
	/**
	 * 按日、周、月统计直接下级。
	 * @param userId
	 * @return
	 * @throws LotteryException
	 */
	Map<String, Integer> groupDirectSubCustomer(String userId) throws LotteryException,ParseException; 
	
	/**
	 * 按日、周、月统计全部成员。
	 * @param userId
	 * @return
	 * @throws LotteryException
	 */
	Map<String, Integer> groupAllTeamCustomer(String userId) throws LotteryException,ParseException; 

	/**
	 * 根据父级ID查询下级最大返点率			
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public BigDecimal getCustomerUserMaxRebatesByParentId(long parentId) throws Exception;
}

