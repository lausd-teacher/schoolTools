package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;


public class StudentIncident implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8576611663190914147L;
	
	private Long id;
	
	private Long studentId;
	private String date;
	private String eventId;
	private String mediaUrl;
	private String summary;
	private Boolean parentsContacted;
	private Integer pointValue;
	@Index
	private BehaviorType type;
	
	
	//LOOK INTO BETTER QUALIFICATIONS
	@Index
	enum BehaviorType{SEVER, MODERATE, INCIDENTAL, GOOD, BETTER , BEST}


	public Long getId() {
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}

	public Long getStudentId() {
		return studentId;
	}


	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}


	public String getEventId() {
		return eventId;
	}

	public void setEventId(String id) {
		this.eventId = id;
	};

	
	public String getMediaUrl() {
		return mediaUrl;
	}


	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}


	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}


	public BehaviorType getType() {
		return type;
	}


	public void setType(BehaviorType type) {
		this.type = type;
	}


	public Boolean getParentsContacted() {
		return parentsContacted;
	}


	public void setParentsContacted(Boolean parentsContacted) {
		this.parentsContacted = parentsContacted;
	}


	public Integer getPointValue() {
		return pointValue;
	}


	public void setPointValue(Integer points) {
		this.pointValue = points;
	}
	
	public String getDate(){
		return this.date;
	}
	
	public void setDate(String date){
		this.date = date;
	}

	
}