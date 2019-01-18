package com.lottery.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.UserCardInventory;
import com.lottery.dao.IUserCardInventoryDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.util.DataDictionaryUtil;

@Repository
public class UserCardInventoryDaoImpl extends GenericDAO<UserCardInventory> implements IUserCardInventoryDao{

	public UserCardInventoryDaoImpl() {
		super(UserCardInventory.class);
	}

	@Override
	public List<UserCardInventory> queryInventorys(Map<String, Object> param)
			throws Exception {
		String hql = "from UserCardInventory where status = ? ";
		List<UserCardInventory> list =getSession().createQuery(hql).
				setParameter(0, DataDictionaryUtil.STATUS_OPEN).list();
		return list;
	}

}
