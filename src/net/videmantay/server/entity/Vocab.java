package net.videmantay.server.entity;

import java.util.Date;
import java.util.Set;

import net.videmantay.shared.SubjectType;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;


@Entity
public class Vocab {
	
	@Id
	private Long id;
	private Date createOn;
	private String createdBy;
	private Date lastUpdate;
	private Long versionNum;
	@Index
	private String vocab;
	private String definition;
	private String exampleSentence;
	private String picUrl;
	@Index
	private SubjectType subject;
	private Set<String> antonyms;
	private Set<String> synonyms;
	@Index
	private String unit;
	@Index
	private String lesson;
	private String text;
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
	public String getVocab() {
		return vocab;
	}
	public void setVocab(String vocab) {
		this.vocab = vocab;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	public String getExampleSentence() {
		return exampleSentence;
	}
	public void setExampleSentence(String exampleSentence) {
		this.exampleSentence = exampleSentence;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public SubjectType getSubject() {
		return subject;
	}
	public void setSubject(SubjectType subject) {
		this.subject = subject;
	}
	public Set<String> getAntonyms() {
		return antonyms;
	}
	public void setAntonyms(Set<String> antonyms) {
		this.antonyms = antonyms;
	}
	public Set<String> getSynonyms() {
		return synonyms;
	}
	public void setSynonyms(Set<String> synonyms) {
		this.synonyms = synonyms;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getLesson() {
		return lesson;
	}
	public void setLesson(String lesson) {
		this.lesson = lesson;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Long getId() {
		return id;
	}

}
