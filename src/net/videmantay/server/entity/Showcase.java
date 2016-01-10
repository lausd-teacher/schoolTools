package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

import net.videmantay.server.user.Roster;

@Entity
public class Showcase implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6633384734480960306L;

	@Id
	private Long id;
	
	@Parent
	Key<Roster> rosterKey;
	
	@Index
	private String studentId;
	
	private String url;
	
	private String description;
	
	private String iconUrl;
	
	@Index
	private Date createdOn;
	
	@Index
	private String showcaseGroup;
	
	private String title;

	public Key<Roster> getRosterKey() {
		return rosterKey;
	}

	public void setRosterKey(Key<Roster> rosterKey) {
		this.rosterKey = rosterKey;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getShowcaseGroup() {
		return showcaseGroup;
	}

	public void setShowcaseGroup(String showcaseGroup) {
		this.showcaseGroup = showcaseGroup;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getId() {
		return id;
	}
	
}
