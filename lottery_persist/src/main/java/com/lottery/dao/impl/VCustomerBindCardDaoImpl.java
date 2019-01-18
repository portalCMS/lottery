package com.lottery.dao.impl;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.VCustomerBindCard;
import com.lottery.dao.VCustomerBindCardDao;
import com.lottery.persist.generice.GenericDAO;

@Repository
public class VCustomerBindCardDaoImpl extends GenericDAO<VCustomerBindCard> implements
VCustomerBindCardDao {

	public VCustomerBindCardDaoImpl() {
		super(VCustomerBindCard.class);
		// TODO Auto-generated constructor stub
	}

}
