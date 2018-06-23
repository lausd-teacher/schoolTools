package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;

public class Course extends JavaScriptObject {
	protected Course() {};
	public final native String getCourse_title()/*-{
		return this.course_title;
	}-*/;
	public final native String getDescription()/*-{
		return this.description;
	}-*/;
}
