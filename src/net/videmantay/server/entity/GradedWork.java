package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import net.videmantay.shared.GradedWorkType;
import net.videmantay.shared.Language;

import com.google.api.services.calendar.model.Event;
import com.google.gdata.data.DateTime;

public class GradedWork extends Assignment implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2502828195108307440L;

	private String eventId = "";
	 			
	private GradedWorkType gradedWorkType = GradedWorkType.HOMEWORK;
	
	private Language lang = Language.ENGLISH;
	
	private Double pointsPossible = 0.0;
	
	private String assignedDate = DateTime.now().toString();
	
	private Boolean finishedGrading = false;
	
	private Event event = null;
	
	/*list of students the assignemt is assigned to */
	/* key word 'ALL' case insensitive for all */
	/*otherwise csv of students remove [] */
	/*consider moving to assignment */
	private Set<String> assignedTo =new HashSet<String>();
	public String getEventId() {
		return eventId;
	}


	public void setEventId(String googleCalEventId) {
		this.eventId = googleCalEventId;
	}


	public GradedWorkType getGradedWorkType() {
		return gradedWorkType;
	}


	public void setGradedWorkType(GradedWorkType gradedWorkType) {
		this.gradedWorkType = gradedWorkType;
	}
	
	public Double getPointsPossible(){
		return this.pointsPossible;
	}
	
	public void setPointsPossible(Double pointsPossible){
		this.pointsPossible = pointsPossible;
	}


	public Language getLang() {
		return lang;
	}


	public void setLang(Language lang) {
		this.lang = lang;
	}


	public String getAssignedDate() {
		return assignedDate;
	}


	public void setAssignedDate(String assignedDate) {
		this.assignedDate = assignedDate;
	}
	
	public Boolean isFinishedGrading(){
		return finishedGrading;
	}
	
	public void setFinishedGrading(Boolean status){
		this.finishedGrading = status;
	}


	public Event getEvent() {
		return event;
	}


	public void setEvent(Event event) {
		this.event = event;
	}


	public Set<String> getAssignedTo() {
		return assignedTo;
	}


	public void setAssignedTo(Set<String> assignedTo) {
		this.assignedTo = assignedTo;
	}
	
	public String getGradedbookCol(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.getTitle() + "\n" + this.getSubject() + "\n" + this.getId());
		return sb.toString();
	}
	
}
