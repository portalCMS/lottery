package com.lottery.service.impl;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.VCustomerFrozenUser;
import com.lottery.bean.entity.vo.VCustomerFrozenUserVO;
import com.lottery.dao.VCustomerFrozenUserDao;
import com.lottery.service.IVCustomerFrozenUserService;

@Service
public class VCustomerFrozenUserServiceImpl implements IVCustomerFrozenUserService{

	@Autowired
	private VCustomerFrozenUserDao vCustomerFrozenUserDao;

	@Override
	public Page<VCustomerFrozenUserVO, VCustomerFrozenUser> findVCustomerFrozenUsers(
			Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		return vCustomerFrozenUserDao.findVCustomerFrozenUsers(param);
	}
}
