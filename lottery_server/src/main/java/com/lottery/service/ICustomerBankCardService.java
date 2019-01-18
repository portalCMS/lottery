package com.lottery.service;

import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.BankManage;
import com.lottery.bean.entity.CustomerBankCard;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.VCustomerBindCard;
import com.lottery.bean.entity.vo.BankManageVO;
import com.lottery.bean.entity.vo.CustomerBankCardVO;

@Service
public interface ICustomerBankCardService {

	/**
	 * 统计银卡数量
	 * @return
	 * @throws Exception
	 */
	public int countBankCard() throws Exception;

	/**
	 * 保存银行卡
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String saveBankCard(final Map<String, Object> param)
			throws Exception;

	/**
	 * 获取银卡集合
	 * @return
	 * @throws Exception
	 */
	public List<CustomerBankCard> queryBankCardList() throws Exception;

	/**
	 * 获取银卡集合翻页
	 * @return
	 * @throws Exception
	 */
	public Page<CustomerBankCardVO, CustomerBankCard> findBankCardListByPage(
			final Map<String, ?> param) throws Exception;

	/**
	 * 根据id查询银卡信息
	 * @param parseLong
	 * @return
	 * @throws Exception
	 */
	public CustomerBankCard queryBankCardById(long parseLong) throws Exception;

	/**
	 * 更新银卡信息
	 * @param parseLong
	 * @return
	 * @throws Exception
	 */
	public void updateBankCard(Map<String, Object> param) throws Exception;

	/**
	 * 禁用银行卡
	 * @param param
	 * @throws Exception
	 */
	public void updateDisableBankCard(Map<String, Object> param)
			throws Exception;

	/**
	 * 删除银行卡
	 * @param param
	 * @throws Exception
	 */
	public void deleteBankCard(Map<String, Object> param) throws Exception;

	/**
	 * 开启银行卡
	 * @param param
	 * @throws Exception
	 */
	public void updateEnableBankCard(Map<String, Object> param)
			throws Exception;

	/**
	 * 开设总代查找银行卡
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public List<CustomerBankCard> getBankCard(final Map<String, Object> param)
			throws Exception;
	
	/**
	 * 新开设总代查找银行卡
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CustomerBankCardVO> getBankCardVO(final Map<String, Object> param) throws Exception;
	
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

	/**
	 * 根据卡库获取银行卡信息集合
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CustomerBankCard> queryCardListByInv(final Map<String, Object> param)throws Exception;
}
