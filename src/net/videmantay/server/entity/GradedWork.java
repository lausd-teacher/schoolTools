package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import net.videmantay.shared.GradedWorkType;
import net.videmantay.shared.Language;
import net.videmantay.shared.SubjectType;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

@Entity
public class GradedWork extends Assignment implements Serializable{
	
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
				
	private GradedWorkType gradedWorkType = GradedWorkType.HOMEWORK;
	
	private Language lang = Language.ENGLISH;
	
	private Double pointsPossible = 0.0;
	
	private Date dueDate = new Date();
	
	private Date assignedDate = new Date();
	
	private Set<StudentWork> studentWorks = new HashSet<StudentWork>();
	
	private Set<Ref<StudentWork>> studentWorkRefs;
	
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


	public Set<StudentWork> getStudentWorks() {
		return studentWorks;
	}


	public void setStudentWorks(Set<StudentWork> studentWorks) {
		this.studentWorks = studentWorks;
	}

}
