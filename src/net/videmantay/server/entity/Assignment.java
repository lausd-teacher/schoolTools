package net.videmantay.server.entity;

import java.util.Set;

import net.videmantay.shared.GradeLevel;
import net.videmantay.shared.SubjectType;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;


@Entity
public class Assignment {
	
	@Id
	private Long id;
	
	private String title;
	
	@Index
	private Set<CCStandard>standards;
	
	private Set<GradeLevel> gradeLevels;
	
	private String mediaUrl;
	
	private String description;
	
	@Index
	private SubjectType subject;
	
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

	public Set<CCStandard> getStandards() {
		return standards;
	}

	public void setStandards(Set<CCStandard> standards) {
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

	public Set<EducationalLink> getLinks() {
		return links;
	}

	public void setLinks(Set<EducationalLink> links) {
		this.links = links;
	}
	
	
}
