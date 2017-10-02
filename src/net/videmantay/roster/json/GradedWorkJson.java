package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

import net.videmantay.shared.GradedWorkType;
import net.videmantay.shared.Language;
import net.videmantay.shared.json.EventJson;
import net.videmantay.student.json.RubricJson;

public class GradedWorkJson extends AssignmentJson {

	protected GradedWorkJson(){
	}
	
	public static GradedWorkJson create(){
		GradedWorkJson work = GradedWorkJson.createObject().cast();
		JsArrayString assignTo = JavaScriptObject.createArray().cast();
		assignTo.push("ALL");
		work.setAssignedTo(assignTo);
		return work;
	}
	
	public final native String getGradedWorkType()/*-{
		return this.type;
	}-*/;


	public final native GradedWorkJson setGradedWorkType(String string) /*-{
		this.type = gradedWorkType;
		return this;
	}-*/;
	
	public final native Double getPointsPossible()/*-{
		return this.pointsPossible;
	}-*/;
	
	public final native GradedWorkJson setPointsPossible(Double pointsPossible)/*-{
		this.pointsPossible = pointsPossible;
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

	public final native JsArrayString getAssignedTo() /*-{
		return this.assignedTo;
	}-*/;


	public final native void setAssignedTo(JsArrayString assignedTo) /*-{
		this.assignedTo = assignedTo;
	}-*/;
	
	public final native void setRubricId(Long rubricId) /*-{
	this.rubricId = rubricId;
}-*/;
	
	public final native Long getRubricId( ) /*-{
	return this.rubricId;
}-*/;
	

}
