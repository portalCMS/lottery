package com.lottery.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.SecurityQuestion;
import com.lottery.bean.entity.vo.SecurityQuestionVO;
import com.lottery.dao.ISecurityQuestionDao;
import com.lottery.service.ISecurityQuestionService;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;
import com.xl.lottery.util.MD5Util;

@Service
public class SecurityQuestionServiceImpl implements ISecurityQuestionService{

	@Autowired
	private ISecurityQuestionDao securityQuestionDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SecurityQuestion> findSecurityQuestionList(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		return securityQuestionDao.findSecurityQuestionList(user);
	}

	@Override
	public void updateQuestion(Map<String, Object> param) throws Exception {
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		SecurityQuestionVO questionVo = (SecurityQuestionVO)param.get("securityKey");
		List<SecurityQuestion> questionList = securityQuestionDao.findSecurityQuestionList(user);
		SecurityQuestion question = questionList.get(0);
		question.setQuestion(questionVo.getQuestion());
		question.setAnswer(questionVo.getAnswer());
		question.setUpdateTime(DateUtil.getNowDate());
		question.setUpdateUser(user.getCustomerName());
		securityQuestionDao.update(question);
	}

	@Override
	public void saveQuestion(Map<String, Object> param) throws Exception {
		CustomerUser user = (CustomerUser) param.get(CommonUtil.CUSTOMERUSERKEY);
		SecurityQuestionVO questionVo = (SecurityQuestionVO)param.get("securityKey");
		SecurityQuestion question = new SecurityQuestion();
		
		question.setQuestion(questionVo.getQuestion());
		question.setAnswer(MD5Util.makeMD5(questionVo.getAnswer()));
		question.setStatus(DataDictionaryUtil.STATUS_OPEN);
		question.setUserId(user.getId());
		
		question.setUpdateTime(DateUtil.getNowDate());
		question.setCreateTime(DateUtil.getNowDate());
		question.setCreateUser(user.getCustomerName());
		question.setUpdateUser(user.getCustomerName());
		
		securityQuestionDao.insert(question);
	}

}
