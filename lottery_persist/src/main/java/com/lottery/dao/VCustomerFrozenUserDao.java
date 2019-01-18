package com.lottery.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.VCustomerFrozenUser;
import com.lottery.bean.entity.vo.VCustomerFrozenUserVO;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface VCustomerFrozenUserDao extends IGenericDao<VCustomerFrozenUser> {

	/**
	 * 查询视图获取冻结用户列表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<VCustomerFrozenUserVO, VCustomerFrozenUser> findVCustomerFrozenUsers(
			final Map<String, ?> param) throws Exception;
}
