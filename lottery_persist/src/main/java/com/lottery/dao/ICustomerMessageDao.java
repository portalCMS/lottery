package com.lottery.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerMessage;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerMessageVO;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface ICustomerMessageDao extends IGenericDao<CustomerMessage>{

	public Integer getMsgCount(Map<String, Object> param)throws Exception;

	public Page<CustomerMessageVO, CustomerMessage> queryMsgPage(Map<String, Object> param)throws Exception;
}
