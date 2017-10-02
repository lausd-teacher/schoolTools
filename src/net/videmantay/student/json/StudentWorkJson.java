package net.videmantay.student.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;


public class StudentWorkJson extends JavaScriptObject {
	
	protected StudentWorkJson(){}
	
	public final native String getRosterStudentId() /*-{
		return this.studentId;
	}-*/;

	public final native void setStudentId(String rosterStudent) /*-{
		this.studentId = rosterStudent;
	}-*/;
	
	public final native Long getGradedWorkId()/*-{
		return this.gradedWorkId;
	}-*/;
	
	public final native void setGradedWorkId(Long id)/*-{
			this.gradedWorkId = id;
	}-*/;

	public final native String getDateTaken() /*-{
		return this.dateTaken;
	}-*/;

	public final native void setDateTaken(String dateTaken) /*-{
		this.dateTaken = dateTaken;
	}-*/;

	public final native String getSubject() /*-{
		return this.subject;
	}-*/;

	public final native void setSubject(String subject) /*-{
		this.subject = subject;
	}-*/;

	public final native void setId(Long id) /*-{
		this.id = id;
	}-*/;
	

	public final native Long getId() /*-{
		return this.id;
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
	

	public final native String getType() /*-{
		return this.type;
	}-*/;

	public final native void setType(String type) /*-{
		this.type = type;
	}-*/;

	public final native String getMessage() /*-{
		return this.message;
	}-*/;

	public final native void setMessage(String message) /*-{
		this.message = message;
	}-*/;

	public final native JsArrayString getAttachment() /*-{
		return this.attachments;
	}-*/;

	public final native void setMediaUrl(JsArrayString attachments) /*-{
		this.attachments = attachments;
	}-*/;


	public final native Double getPointsEarned() /*-{
		return this.pointsEarned;
	}-*/;

	public final native void setPointsEarned(Double pointsEarned) /*-{
		this.pointsEarned = pointsEarned;
	}-*/;
	
	public final native String getStudentWorkStatus() /*-{
		return this.status;
	}-*/;

	public final native void setStudentWorkStatus(String studentWorkStatus) /*-{
		this.status = studentWorkStatus;
	}-*/;


}
