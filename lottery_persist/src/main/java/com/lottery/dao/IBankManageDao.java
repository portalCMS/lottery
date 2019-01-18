package com.lottery.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.BankManage;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.BankManageVO;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface IBankManageDao  extends IGenericDao<BankManage>{

	/**
	 * 查询页数
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<BankManageVO, BankManage> findBankeManageList(final Map<String,?> param)throws Exception;
	
	public int countBankManage()throws Exception;
}
