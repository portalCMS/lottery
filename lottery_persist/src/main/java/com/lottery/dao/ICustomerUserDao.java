package com.lottery.dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerOrderVO;
import com.lottery.bean.entity.vo.CustomerUserVO;
import com.lottery.persist.generice.IGenericDao;
import com.xl.lottery.exception.LotteryException;

@Repository
public interface ICustomerUserDao extends IGenericDao<CustomerUser> {

	/**
	 * 查询总代用户
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> findMainCustomers(final Map<String, ?> param)throws Exception;

	public List<CustomerUser> queryUserByName(CustomerOrderVO vo)throws Exception;
	
	/**
	 * 根据名字查询
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public CustomerUser queryUserByName(String userName)throws Exception;
	
	
	/**
	 * 查找当前用户树
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> findCuserTree(final Map<String,?> param)throws Exception;

	public List<CustomerUser> quUserByTeam(final Map<String, Object> param)throws Exception;
	
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

	public List<CustomerUser> queryBindCardUser(final Map<String,?> param)throws Exception;

	public Map<String, Integer> queryUserStatistic()throws Exception;
	/**
	 * 查询当月每天的用户活跃度
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryMonthActiveUser(final Map<String,?> param) throws Exception;

	public Map<String, Integer> queryMonthAddUser(final Map<String,?> param) throws Exception;
	
	public Page<Object, Object> getSuperQueryData(final Map<String, ?> param)throws Exception;

	public Map<String, ?> queryProfileAmount(final Map<String, Object> param)throws Exception;
	
	public Map<String, ?> queryProfileAmountMap(final Map<String, Object> param)throws Exception;

	public Map<String, ?> queryProfileData(final Map<String, Object> param)throws Exception;

	public Map<String, ?> queryMarketStatistic(final Map<String, Object> param)throws Exception;

	public Map<String, ?> queryAvgPerStasData(final Map<String, Object> param)throws Exception;
	
	public Map<String, ?> queryAmountStasData(final Map<String, Object> param)throws Exception;
	
	/**
	 * 获取当前用户好友字符串 例如 111:aaa111(id编号:好友名称),222:aaa222
	 * 包含当前上级以及下级
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> queryUserFirends(CustomerUser user)throws Exception;
	
	/**
	 * 查询测试用户或非测试用户Id
	 * @return
	 * @throws Exception
	 */
	public List<Long> queryUserIdByType(String customer)throws Exception;
	
	/**
	 * 查询根据父级ID获取所有子孙级用户
	 * @return
	 * @throws Exception
	 */
	public List<CustomerUser> querySunUsersByParent(CustomerUser customerUser)throws Exception;
	/**
	 * 检查前台用户绑定的银行卡是否已经重复
	 * @param param
	 * @throws LotteryException 
	 */
	public void checkCardNoIsExist(Map<String, Object> param) throws LotteryException;

	public Page<CustomerUserVO, Object> queryTeamUserByKey(Map<String, Object> param)throws LotteryException;
	
	/**
	 * 查询个人团队成员（直接下级和全部成员）
	 * 
	 * @param userId
	 * @return
	 */

	List<Object> queryTeamNum(String userId) throws LotteryException;
	
	/**
	 * 获取直接下级列表。
	 * @param userId
	 * @return
	 * @throws LotteryException
	 */
	 List<CustomerUser> getDirectSubCustomer(Map<String, String> param) throws LotteryException,ParseException; 
	
	/**
	 * 获取直接下级列表。
	 * @param userId
	 * @return
	 * @throws LotteryException
	 */
	 List<CustomerUser> getAllTeamCustomer(Map<String, String> param) throws LotteryException,ParseException; 

	 public BigDecimal getCustomerUserMaxRebatesByParentId(long parentId);
}

