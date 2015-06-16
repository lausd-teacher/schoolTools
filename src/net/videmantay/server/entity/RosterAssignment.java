package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.videmantay.shared.RosterAssignmentType;
import net.videmantay.shared.Language;
import net.videmantay.shared.SubjectType;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

@Entity
public class RosterAssignment extends Assignment implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2502828195108307440L;
	
	
	@Index
	private Long rosterId;
	

	
	 //link to google calendar event
	 //must index so that we can retrieve 
	 @Index
	private String googleCalEventId = "";
	 
	 @Index
	 
	 private String googleCalRepeatEventId ="";
	 
	 private String recurringEventId="";
				
	private RosterAssignmentType rosterAssignmentType = RosterAssignmentType.HOMEWORK;
	
	private Language lang = Language.ENGLISH;
	
	private Double pointsPossible = 0.0;
	
	private Date dueDate = new Date();
	
	private Date assignedDate = new Date();
	
	private HashMap<Long, Long> studentWorks = new HashMap<Long,Long>();
		
	public Long getRosterId() {
		return rosterId;
	}


	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
	}

	
	public String getGoogleCalEventId() {
		return googleCalEventId;
	}


	public void setGoogleCalEventId(String googleCalEventId) {
		this.googleCalEventId = googleCalEventId;
	}


	public String getRecurringEventId() {
		return recurringEventId;
	}


	public void setRecurringEventId(String recurringEventId) {
		this.recurringEventId = recurringEventId;
	}


	public RosterAssignmentType getRosterAssignmentType() {
		return rosterAssignmentType;
	}


	public void setRosterAssignmentType(RosterAssignmentType rosterAssignmentType) {
		this.rosterAssignmentType = rosterAssignmentType;
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


	public Date getDueDate() {
		return dueDate;
	}


	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}


	public Date getAssignedDate() {
		return assignedDate;
	}


	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}


	public HashMap<Long,Long> getStudentWorks() {
		return studentWorks;
	}


	public void setStudentWorks(HashMap<Long,Long> studentWorks) {
		this.studentWorks = studentWorks;
	}
	


}
