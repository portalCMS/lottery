package com.lottery.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.VCustomerFrozenUser;
import com.lottery.bean.entity.vo.VCustomerFrozenUserVO;

@Service
public interface IVCustomerFrozenUserService {

	/**
	 * 查询视图获取冻结用户列表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<VCustomerFrozenUserVO, VCustomerFrozenUser> findVCustomerFrozenUsers(
			final Map<String, ?> param) throws Exception;
}
