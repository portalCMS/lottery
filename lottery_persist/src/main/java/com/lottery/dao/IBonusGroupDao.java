package com.lottery.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.BonusGroup;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface IBonusGroupDao extends IGenericDao<BonusGroup>{

	public List<BonusGroup> findBonusGroupAll(final Map<String,?> param);
}
