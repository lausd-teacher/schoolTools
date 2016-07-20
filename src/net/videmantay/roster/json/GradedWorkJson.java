package net.videmantay.roster.json;

import java.util.Set;

import com.google.gwt.core.client.JsArrayString;

import net.videmantay.shared.GradedWorkType;
import net.videmantay.shared.Language;
import net.videmantay.shared.json.EventJson;

public class GradedWorkJson extends AssignmentJson {

	protected GradedWorkJson(){
		JsArrayString assignTo = JsArrayString.createArray().cast();
		assignTo.push("ALL");
		this.setAssignedTo(assignTo);
	}
	
	public final native Long getRosterId()/*-{
			return this.rosterId;
		}-*/;
	
	public final native GradedWorkJson setRosterId(Long rosterId)/*-{
			this.rosterId = rosterId;
			return this;
	}-*/;
	public final native String  getEventId()/*-{
		return this.eventId;
	}-*/;


	public final native GradedWorkJson setEventId(String googleCalEventId)/*-{
		this.eventId = googleCalEventId;
		return this;
	}-*/;


	public final native GradedWorkType getGradedWorkType()/*-{
		return this.gradedWorkType;
	}-*/;


	public final native GradedWorkJson setGradedWorkType(GradedWorkType gradedWorkType) /*-{
		this.gradedWorkType = gradedWorkType;
		return this;
	}-*/;
	
	public final native Double getPointsPossible()/*-{
		return this.pointsPossible;
	}-*/;
	
	public final native GradedWorkJson setPointsPossible(Double pointsPossible)/*-{
		this.pointsPossible = pointsPossible;
		return this;
	}-*/;


	public final native Language getLang() /*-{
		return this.lang;
	}-*/;


	public final native GradedWorkJson setLang(Language lang) /*-{
		this.lang = lang;
		return this;
	}-*/;


	public final native String getAssignedDate() /*-{
		return this.assignedDate;
	}-*/;


	public final native void setAssignedDate(String assignedDate) /*-{
		this.assignedDate = assignedDate;
	}-*/;
	
	public final native String getDueDate()/*-{
		return this.dueDate;
	}-*/;
	
	public final native void setDueDate(String date)/*-{
	         this.dueDate = date;
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


	public final native JsArrayString getAssignedTo() /*-{
		return this.assignedTo;
	}-*/;


	public final native void setAssignedTo(JsArrayString assignedTo) /*-{
		this.assignedTo = assignedTo;
	}-*/;
}
