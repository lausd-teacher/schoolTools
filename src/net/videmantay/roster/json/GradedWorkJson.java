package net.videmantay.roster.json;

import java.util.Set;

import net.videmantay.shared.GradedWorkType;
import net.videmantay.shared.Language;
import net.videmantay.student.json.EventJson;

public class GradedWorkJson extends AssignmentJson {

	protected GradedWorkJson(){}
	
	public final native String  getEventId()/*-{
		return this.eventId;
	}-*/;


	public final native void setEventId(String googleCalEventId)/*-{
		this.eventId = googleCalEventId;
	}-*/;


	public final native GradedWorkType getGradedWorkType()/*-{
		return this.gradedWorkType;
	}-*/;


	public final native void setGradedWorkType(GradedWorkType gradedWorkType) /*-{
		this.gradedWorkType = gradedWorkType;
	}-*/;
	
	public final native Double getPointsPossible()/*-{
		return this.pointsPossible;
	}-*/;
	
	public final native void setPointsPossible(Double pointsPossible)/*-{
		this.pointsPossible = pointsPossible;
	}-*/;


	public final native Language getLang() /*-{
		return this.lang;
	}-*/;


	public final native void setLang(Language lang) /*-{
		this.lang = lang;
	}-*/;


	public final native String getAssignedDate() /*-{
		return this.assignedDate;
	}-*/;


	public final native void setAssignedDate(String assignedDate) /*-{
		this.assignedDate = assignedDate;
	}-*/;
	
	public final native Boolean isFinishedGrading()/*-{
		return this.finishedGrading;
	}-*/;
	
	public final native void setFinishedGrading(Boolean status)/*-{
		this.finishedGrading = status;
	}-*/;


	public final native EventJson getEvent() /*-{
		return this.event;
	}-*/;


	public final native void setEvent(EventJson event) /*-{
		this.event = event;
	}-*/;


	public final native Set<String> getAssignedTo() /*-{
		return this.assignedTo;
	}-*/;


	public final native void setAssignedTo(Set<String> assignedTo) /*-{
		this.assignedTo = assignedTo;
	}-*/;
}
