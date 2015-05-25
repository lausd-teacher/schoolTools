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
	
	private Date createOn;
	private String createdBy;
	private Date lastUpdate;
	private Long versionNum;
	@Id
	private Long id;
	private Long answerKey;
	private List<Key> standards;
	private String questionType;
	private String prompt;
	private Boolean isCorrect;
	private String exmplanation;
	
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

	public List<Key> getStandards() {
		return standards;
	}

	public void setStandards(List<Key> standards) {
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
		return exmplanation;
	}

	public void setExmplanation(String exmplanation) {
		this.exmplanation = exmplanation;
	}

	public Long getId() {
		return id;
	}

	public Long getAnswerKey() {
		return answerKey;
	}

	public void setAnswerKey(Long answerKey) {
		this.answerKey = answerKey;
	};
	
}
