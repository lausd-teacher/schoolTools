package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

public class AttendanceJson extends JavaScriptObject {
	protected AttendanceJson(){}

	public final native String getDate() /*-{
		return this.date;
	}-*/;
	public final native void setDate(String date)/*-{
		this.date = date;
	}-*/;

	public final native JsArrayString getStudentPresent()/*-{}-*/;
	public final native AttendanceJson setStudentPresent(JsArrayString students)/*-{
			this.studentPresent = students;
	}-*/;
	public final native JsArrayString  getStudentAbsent()/*-{}-*/;
	public final native AttendanceJson setStudentAbsent(JsArrayString students)/*-{
		this.studentAbsent = students;
	}-*/;

}
