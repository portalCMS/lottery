package com.lottery.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerUrl;
import com.lottery.bean.entity.DomainUrl;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.DomainUrlVO;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface IDomainUrlDao  extends IGenericDao<DomainUrl>{

	/**
	 * 查询页数
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<DomainUrlVO, DomainUrl> findDomainUrlList(final Map<String,?> param)throws Exception;
	
	/**
	 * 根据域名查询
	 */
	public DomainUrl findDomainUrlList(String urlName)throws Exception;
	
	/**
	 * 查询有效的URL
	 * @return
	 * @throws Exception
	 */
	public List<DomainUrl> findDomainUrllist(final Map<String,?> param)throws Exception;
}
