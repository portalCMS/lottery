package com.lottery.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.SecurityQuestion;
import com.lottery.persist.generice.IGenericDao;
@Repository
public interface ISecurityQuestionDao extends IGenericDao<SecurityQuestion>{

	public List<SecurityQuestion> findSecurityQuestionList(CustomerUser user) throws Exception;

}
