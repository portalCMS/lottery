package com.lottery.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerIpLog;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerIpLogVO;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface ICustomerIpLogDao extends IGenericDao<CustomerIpLog>{

	public Page<CustomerIpLogVO, CustomerIpLog> findIpLogs(final Map<String,Object> param)throws Exception;
	
	
	public void saveIPlog(CustomerUser user,String log)throws Exception;
	
	/**
	 * 根据用户名或是IP查询用户IP情况
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<Object, Object> queryIplogs(final Map<String, Object> param)throws Exception;
}
