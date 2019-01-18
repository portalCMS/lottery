package com.lottery.dao;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.UserBonusGroup;
import com.lottery.persist.generice.IGenericDao;

@Repository
public interface IUserBonusGroupDao extends IGenericDao<UserBonusGroup> {

}
