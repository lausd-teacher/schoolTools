package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;


@Entity
public abstract class QuizQuestion implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5659584459936842149L;
	
	public Date createOn;
	public String createdBy;
	public Date lastUpdate;
	public Long versionNum;
	@Id
	public Long id;
	public List<Long> standards;
	public String questionType;
	public String prompt;
	public Boolean isCorrect;
	public String explanation;
	public String correctAnswer;
	public String testerResponse;
	
	enum QuestionType{MULTIPLE_ANSWER, SINGLE_ANSWER, TRUE_FALSE, TEXT_ANSWER,MATCHING }

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Long getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Long versionNum) {
		this.versionNum = versionNum;
	}

	public List<Long> getStandards() {
		return standards;
	}

	public void setStandards(List<Long> standards) {
		this.standards = standards;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public Boolean getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	public String getExmplanation() {
		return explanation;
	}

	public void setExmplanation(String exmplanation) {
		this.explanation = exmplanation;
	}

	public Long getId() {
		return id;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setAnswerKey(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	};
	
}
