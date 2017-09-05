package net.videmantay.student.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

public class StudentJobJson extends JavaScriptObject {

	protected StudentJobJson(){}
	
	public final native String getIconUrl() /*-{
		return this.iconUrl;
	}-*/;
	public final native void setIconUrl(String iconUrl) /*-{
		this.iconUrl = iconUrl;
	}-*/;
	public final native String getTitle() /*-{
		return this.title;
	}-*/;
	public final native void setTitle(String title) /*-{
		this.title = title;
	}-*/;
	public final native String getJobDescription() /*-{
		return this.jobDescription;
	}-*/;
	public final native void setJobDescription(String jobDescription) /*-{
		this.jobDescription = jobDescription;
	}-*/;
	
	public final native String getCategory() /*-{
		return this.category;
	}-*/;
	public final native void setCategory(String category) /*-{
		this.category = category;
	}-*/;
	public final native Long getId() /*-{
		return this.id;
	}-*/;
	
	public final native void setId(Long id)/*-{
		this.id = id;
	}-*/;
	public final native JsArrayString getAssignedStudents() /*-{
		return this.assignedStudents;
	}-*/;
	public final native void setAssignedStudents(JsArrayString assignedStudents) /*-{
		this.assignedStudents = assignedStudents;
	}-*/;

}
