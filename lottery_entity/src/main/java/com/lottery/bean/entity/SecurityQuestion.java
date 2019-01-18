package com.lottery.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_security_question")
public class SecurityQuestion extends GenericEntity{



	/**
	 * 
	 */
	private static final long serialVersionUID = 5433733060255347263L;

	@Column(name="customeruser_id")
	private long userId;
	
	@Column(name="question")
	private String question;
	
	@Column(name="answer")
	private String answer;

	@Column(name="status")
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

	@Override
	public String toString() {
		return "SecurityQuestion [userId=" + userId + ", question=" + question
				+ ", answer=" + answer + ", status=" + status + ", getId()="
				+ getId() + ", getCreateTime()=" + getCreateTime()
				+ ", getCreateUser()=" + getCreateUser() + ", getUpdateTime()="
				+ getUpdateTime() + ", getUpdateUser()=" + getUpdateUser()
				+ ", getVersion()=" + getVersion() + "]";
	}
	
	
}
