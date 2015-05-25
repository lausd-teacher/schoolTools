package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import net.videmantay.shared.SubjectType;

import com.google.appengine.api.datastore.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Quiz implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3218458268220288678L;
	
	
	private Date createOn;
	private String createdBy;
	private Date lastUpdate;
	private Long versionNum;
	
	@Id
	private Long id;
	
	private Double percentage;
	private Integer correctAnswers;
	private Integer incorrectAnswers;
	private String descript;
	@Index
	private String title;
	private SubjectType subjectType;
	private Set<Long> standardsCovered;
	
	
	private List<Long> questions;


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


	public Double getPercentage() {
		return percentage;
	}


	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}


	public Integer getCorrectAnswers() {
		return correctAnswers;
	}


	public void setCorrectAnswers(Integer correctAnswers) {
		this.correctAnswers = correctAnswers;
	}


	public Integer getIncorrectAnswers() {
		return incorrectAnswers;
	}


	public void setIncorrectAnswers(Integer incorrectAnswers) {
		this.incorrectAnswers = incorrectAnswers;
	}


	public String getDescript() {
		return descript;
	}


	public void setDescript(String descript) {
		this.descript = descript;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public SubjectType getSubjectType() {
		return subjectType;
	}


	public void setSubjectType(SubjectType subjectType) {
		this.subjectType = subjectType;
	}


	public Set<Long> getStandardsCovered() {
		return standardsCovered;
	}


	public void setStandardsCovered(Set<Long> standardsCovered) {
		this.standardsCovered = standardsCovered;
	}


	public List<Long> getQuestions() {
		return questions;
	}


	public void setQuestions(List<Long> questions) {
		this.questions = questions;
	}


	public Long getId() {
		return id;
	}
}
