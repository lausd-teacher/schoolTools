package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import net.videmantay.shared.GradeLevel;
import net.videmantay.shared.SubjectType;

import com.google.appengine.api.datastore.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Lesson implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6200640680813126874L;
	
	@Id
	private Long id;
	private Date createOn;
	private String createdBy;
	private Date lastUpdate;
	private Long versionNum;
	

	@Index
	private String title;
	private List<Long> standards;
	private GradeLevel gradeLevel;
	@Index
	private SubjectType subject;
	private List<String> keyWords;
	private String mediaUrl;
	private List<EducationalLink> links;
	private Quiz quiz;
	private Set<Long> vocabList;
	public Long getId() {
		return id;
	}
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Long> getStandards() {
		return standards;
	}
	public void setStandards(List<Long> standards) {
		this.standards = standards;
	}
	public GradeLevel getGradeLevel() {
		return gradeLevel;
	}
	public void setGradeLevel(GradeLevel gradeLevel) {
		this.gradeLevel = gradeLevel;
	}
	public SubjectType getSubject() {
		return subject;
	}
	public void setSubject(SubjectType subject) {
		this.subject = subject;
	}
	public List<String> getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(List<String> keyWords) {
		this.keyWords = keyWords;
	}
	public String getMediaUrl() {
		return mediaUrl;
	}
	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}
	public List<EducationalLink> getLinks() {
		return links;
	}
	public void setLinks(List<EducationalLink> links) {
		this.links = links;
	}
	public Quiz getQuiz() {
		return quiz;
	}
	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}
	public Set<Long> getVocabList() {
		return vocabList;
	}
	public void setVocabList(Set<Long> vocabList) {
		this.vocabList = vocabList;
	}
	
}
