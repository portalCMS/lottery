package com.lottery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.SecurityQuestion;

@Service
public interface ISecurityQuestionService {

	
	/**
	 * 获取安全问题列表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<SecurityQuestion> findSecurityQuestionList(final Map<String, ?> param)throws Exception;

	/**
	 * 更新安全问题
	 * @param param
	 * @throws Exception
	 */
	public void updateQuestion(final Map<String, Object> param)throws Exception;

	/**
	 * 保存安全问题
	 * @param param
	 * @throws Exception
	 */
	public void saveQuestion(final Map<String, Object> param)throws Exception;
}
