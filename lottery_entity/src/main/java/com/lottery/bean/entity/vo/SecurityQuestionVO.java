package com.lottery.bean.entity.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

public class SecurityQuestionVO extends GenericEntityVO{



	/**
	 * 
	 */
	private static final long serialVersionUID = 5433733060255347263L;

	private long userId;
	
	private String question;
	
	private String answer;
	
	private int status;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
