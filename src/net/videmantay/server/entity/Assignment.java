package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.SafeHtml;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import net.videmantay.server.constant.GradeLevel;
import net.videmantay.server.constant.SubjectType;

public class Assignment implements Serializable {

	@Id
	public Long id;

	@SafeHtml
	public String title;

	@Index
	public Long rosterId;

	public Set<String> standards = new HashSet<String>();

	public Set<GradeLevel> gradeLevels;

	@SafeHtml
	public String mediaUrl;

	@SafeHtml
	public String description;

	public SubjectType subject;

	private Set<EducationalLink> links;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<String> getStandards() {
		return standards;
	}

	public void setStandards(Set<String> standards) {
		this.standards = standards;
	}

	public Set<GradeLevel> getGradeLevels() {
		return gradeLevels;
	}

	public void setGradeLevels(Set<GradeLevel> gradeLevels) {
		this.gradeLevels = gradeLevels;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SubjectType getSubject() {
		return subject;
	}

	public void setSubject(SubjectType subject) {
		this.subject = subject;
	}

	public Long getRosterId() {
		return rosterId;
	}

	public void setRosterId(Long id) {
		this.rosterId = id;
	}

	public Set<EducationalLink> getLinks() {
		return links;
	}

	public void setLinks(Set<EducationalLink> links) {
		this.links = links;
	}

}
