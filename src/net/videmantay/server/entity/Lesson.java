package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import net.videmantay.server.constant.GradeLevel;
import net.videmantay.server.constant.SubjectType;

@Entity
public class Lesson implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6200640680813126874L;

	@Id
	public Long id;

	@Index
	public String title;
	public List<Long> standards;
	public GradeLevel gradeLevel;
	@Index
	public SubjectType subject;
	public List<String> keyWords;
	public String mediaUrl;
	private List<EducationalLink> links;
	private Quiz quiz;
	public Set<Long> vocabList;

	public Long getId() {
		return id;
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
