package com.lottery.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerBindCard;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface ICustomerBindCardDao extends IGenericDao<CustomerBindCard> {

	public List<CustomerBindCard> queryBindCardByUserAgentId(long userId)throws Exception;

}
