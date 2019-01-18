package com.lottery.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerBankCard;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.VCustomerBindCard;
import com.lottery.bean.entity.vo.CustomerBankCardVO;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface ICustomerBankCardDao extends IGenericDao<CustomerBankCard> {

	
	public List<CustomerBankCard> getBankCard(final Map<String,Object> param) throws Exception;
	
	public List<Object[]> getBackCardCountAndMoneyAndLevelAndInventoryName(final long cardId) throws Exception;
	
	public int countBankCard();
	
	public Page<CustomerBankCardVO, CustomerBankCard> findBankCardListByPage(
			Map<String, ?> param);
	
	/**
	 * 根据用户ID查找银行卡
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<VCustomerBindCard> findCardsByUserId(final Map<String,?> param)throws Exception;
	
	/**
	 * 排除用户已绑定的银行卡
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CustomerBankCard> findBankCardsNotByUserId(final Map<String, ?> param)throws Exception;

	public List<CustomerBankCard> queryCardListByInv(final Map<String, Object> param)throws Exception;
}
