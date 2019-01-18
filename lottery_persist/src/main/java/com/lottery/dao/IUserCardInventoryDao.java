package com.lottery.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.UserCardInventory;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface IUserCardInventoryDao extends IGenericDao<UserCardInventory>{

	public List<UserCardInventory> queryInventorys(final Map<String, Object> param)throws Exception;

}
