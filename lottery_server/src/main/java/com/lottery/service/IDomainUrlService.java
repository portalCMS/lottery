package com.lottery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.DomainUrl;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.DomainUrlVO;

@Service
public interface IDomainUrlService {

	/**
	 * 查询页数
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<DomainUrlVO, DomainUrl> findDomainUrlList(final Map<String,?> param)throws Exception;
	
	/**
	 * 保存URL
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String saveDomainUrl(final Map<String,?> param)throws Exception;
	
	/**
	 * 修改URL
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String updateDomainUrl(final Map<String,?> param)throws Exception;
	
	
	/**
	 * 删除
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String deleteDomainUrl(final Map<String,?> param)throws Exception;
	
	/**
	 * 查询有效的URL
	 * @return
	 * @throws Exception
	 */
	public List<DomainUrl> findDomainUrllist(final Map<String,?> param)throws Exception;
	
	
	/**
	 * 获取所有URL
	 * @return
	 * @throws Exception
	 */
	public List<DomainUrl> queryAll()throws Exception;
}
