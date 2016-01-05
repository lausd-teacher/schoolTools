package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class BehaviorReport extends DBObj implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8576611663190914147L;
	
	@Id
	private Long id;

	private Date createOn;
	private String createdBy;
	@Index
	private Long rosterId;
	@Index
	private Long studentId;
	
	
	private String eventId;
	private String mediaUrl;
	private String summary;
	private Boolean parentsContacted;
	private Integer positivePoints;
	private Integer negativepoints;
	@Index
	private BehaviorType type;
	
	
	//LOOK INTO BETTER QUALIFICATIONS
	enum BehaviorType{SEVER, MODERATE, INCIDENTAL, GOOD, BETTER , BEST}


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

	public Long getRosterId() {
		return rosterId;
	}


	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
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


	public void setIncidentDate(String event) {
		this.eventId = event;
	}


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


	public Integer getPositivePoints() {
		return positivePoints;
	}


	public void setPositivePoints(Integer positivePoints) {
		this.positivePoints = positivePoints;
	}


	public Integer getNegativepoints() {
		return negativepoints;
	}


	public void setNegativepoints(Integer negativepoints) {
		this.negativepoints = negativepoints;
	};
}
