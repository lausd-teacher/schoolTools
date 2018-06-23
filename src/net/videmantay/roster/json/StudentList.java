package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;

public class StudentList extends JavaScriptObject {
	protected StudentList() {};
	public final native Student[] getEnrollment()/*-{
		return this.enrollment;
	}-*/;
	
	public final native String getTotal()/*-{
		return this.total;
	}-*/;
		
}
