package com.lottery.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.SecurityQuestion;
import com.lottery.bean.entity.UserCard;
import com.lottery.persist.generice.IGenericDao;
@Repository
public interface IUserCardDao extends IGenericDao<UserCard>{

	public List<UserCard> queryUserCardByUserId(long userId)throws Exception;

}
