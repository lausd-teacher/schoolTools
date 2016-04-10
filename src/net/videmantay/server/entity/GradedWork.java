package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import net.videmantay.shared.GradedWorkType;
import net.videmantay.shared.Language;

import com.google.api.services.calendar.model.Event;
import com.google.gdata.data.DateTime;

public class GradedWork extends Assignment implements Serializable{
	
	
	public String eventId = "";
	 			
	public GradedWorkType type = GradedWorkType.HOMEWORK;
	
	public Language lang = Language.ENGLISH;
	
	public Double pointsPossible = 0.0;
	
	public String assignedDate = DateTime.now().toString();
	
	public String dueDate = DateTime.now().toString();
	
	public Boolean finishedGrading = false;
	
	public Long rubric;
	
	private Event event = null;
	
	/*list of students the assignemt is assigned to */
	/* key word 'ALL' case insensitive for all */
	/*otherwise csv of students remove [] */
	/*consider moving to assignment */
	public Set<String> assignedTo =new HashSet<String>();
	
	
	public String getEventId() {
		return eventId;
	}


	public void setEventId(String googleCalEventId) {
		this.eventId = googleCalEventId;
	}


	public GradedWorkType getGradedWorkType() {
		return type;
	}


	public void setGradedWorkType(GradedWorkType gradedWorkType) {
		this.type = gradedWorkType;
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
	public String getDueDate(){
		return this.dueDate;
	}
	public void setDueDate (String date){
		this.dueDate = date;
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
	
	public Long getRubric(){
		return this.rubric;
	}
	
	public void setRubric(Long rubric){
		this.rubric = rubric;
	}
	
	public String getGradedbookCol(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.getTitle() + "\n" + this.getSubject() + "\n" + this.getId());
		return sb.toString();
	}
	
}
