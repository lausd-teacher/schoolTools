package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import net.videmantay.shared.SubjectType;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;


@Entity
public class Vocab implements Serializable {
	
	@Id
	public Long id;
	public Date createOn;
	public String createdBy;
	public Date lastUpdate;
	public Long versionNum;
	@Index
	public String vocab;
	public String definition;
	public String exampleSentence;
	public String picUrl;
	@Index
	public SubjectType subject;
	public Set<String> antonyms;
	public Set<String> synonyms;
	@Index
	public String unit;
	@Index
	public String lesson;
	public String text;
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
