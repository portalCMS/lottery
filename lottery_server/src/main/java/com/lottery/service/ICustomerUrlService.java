package com.lottery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.CustomerUrl;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerUrlVO;

@Service
public interface ICustomerUrlService{

	/**
	 * 查询页数
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<CustomerUrlVO, CustomerUrl> findCustomerUrlList(final Map<String,?> param)throws Exception;
	
	/**
	 * 保存URL
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String saveCustomerUrl(final Map<String,?> param)throws Exception;
	
	/**
	 * 修改URL
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String updateCustomerUrl(final Map<String,?> param)throws Exception;
	
	
	/**
	 * 删除
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String deleteCustomerUrl(final Map<String,?> param)throws Exception;
	
	/**
	 * 查询有效的URL
	 * @return
	 * @throws Exception
	 */
	public List<CustomerUrl> findCustomerUrllist(final Map<String,?> param)throws Exception;
	
}
