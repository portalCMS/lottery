package com.lottery.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerHandsel;
import com.lottery.dao.ICustomerHandselDao;
import com.lottery.persist.generice.GenericDAO;

@Repository
public class CustomerHandselDaoImpl extends GenericDAO<CustomerHandsel>
		implements ICustomerHandselDao {

	private static Logger logger = LoggerFactory.getLogger(CustomerHandselDaoImpl.class);
	
	public CustomerHandselDaoImpl() {
		super(CustomerHandsel.class);
		// TODO Auto-generated constructor stub
	}

}
