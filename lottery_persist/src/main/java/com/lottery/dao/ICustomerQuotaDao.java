package com.lottery.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerQuota;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface ICustomerQuotaDao extends IGenericDao<CustomerQuota> {

	public List<CustomerQuota> getCustomerQuotaByCustomerId(long customer);
	
	public BigDecimal getCustomerQuotaMaxProportionByCustomerId(long customer);
}
