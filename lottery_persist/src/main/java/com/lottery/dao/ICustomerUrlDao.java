package com.lottery.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerUrl;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerUrlVO;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface ICustomerUrlDao extends IGenericDao<CustomerUrl>{

	/**
	 * 查询页数
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<CustomerUrlVO, CustomerUrl> findCustomerUrlList(final Map<String,?> param)throws Exception;
	
	/**
	 * 根据域名查询
	 */
	public CustomerUrl findCustomerUrlList(String urlName)throws Exception;
	
	/**
	 * 查询有效的URL
	 * @return
	 * @throws Exception
	 */
	public List<CustomerUrl> findCustomerUrllist(final Map<String,?> param)throws Exception;
}
