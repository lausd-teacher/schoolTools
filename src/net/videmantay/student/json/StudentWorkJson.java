package net.videmantay.student.json;

import com.google.gwt.core.client.JavaScriptObject;
import net.videmantay.shared.GradedWorkType;
import net.videmantay.shared.StudentWorkStatus;
import net.videmantay.shared.SubjectType;

public class StudentWorkJson extends JavaScriptObject {
	
	protected StudentWorkJson(){}
	
	public final native Long getRosterStudentId() /*-{
		return this.rosterStudentId;
	}-*/;

	public final native void setRosterStudentId(Long rosterStudent) /*-{
		this.rosterStudentId = rosterStudent;
	}-*/;

	public final native String getDateTaken() /*-{
		return this.dateTaken;
	}-*/;

	public final native void setDateTaken(String dateTaken) /*-{
		this.dateTaken = dateTaken;
	}-*/;

	public final native SubjectType getSubject() /*-{
		return this.subject;
	}-*/;

	public final native void setSubject(SubjectType subject) /*-{
		this.subject = subject;
	}-*/;

	public final native void setId(Long id) /*-{
		this.id = id;
	}-*/;

	public final native Double getPercentage() /*-{
		return this.percentage;
	}-*/;

	public final native void setPercentage(Double percentage) /*-{
		this.percentage = percentage;
	}-*/;

	public final native String getLetterGrade() /*-{
		return this.letterGrade;
	}-*/;

	public final native void setLetterGrade(String letterGrade) /*-{
		this.letterGrade = letterGrade;
	}-*/;
	

	public final native GradedWorkType getType() /*-{
		return this.type;
	}-*/;

	public final native void setType(GradedWorkType type) /*-{
		this.type = type;
	}-*/;

	public final native String getMessage() /*-{
		return this.message;
	}-*/;

	public final native void setMessage(String message) /*-{
		this.message = message;
	}-*/;

	public final native String getMediaUrl() /*-{
		return this.mediaUrl;
	}-*/;

	public final native void setMediaUrl(String mediaUrl) /*-{
		this.mediaUrl = mediaUrl;
	}-*/;


	public final native Long getId() /*-{
		return this.id;
	}-*/;

	public final native Double getPointsEarned() /*-{
		return this.pointsEarned;
	}-*/;

	public final native void setPointsEarned(Double pointsEarned) /*-{
		this.pointsEarned = pointsEarned;
	}-*/;
	
	public final native StudentWorkStatus getStudentWorkStatus() /*-{
		return this.studentWorkStatus;
	}-*/;

	public final native void setStudentWorkStatus(StudentWorkStatus studentWorkStatus) /*-{
		this.studentWorkStatus = studentWorkStatus;
	}-*/;
	public final native void setStudentWorkStatus(String value)/*-{
		value.trim();
		this.studentWorkStatus = StudentWorkStatus.valueOf(value.toUpperCase());
	}-*/;

}
