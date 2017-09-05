package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

public class AttendanceJson extends JavaScriptObject {
	protected AttendanceJson(){}
	
	public final native String getId()/*-{}-*/;

	public final native Boolean getCompleted()/*-{}-*/;

	public final native JsArrayString getStudentPresent()/*-{}-*/;
	public final native AttendanceJson setStudentPresent(JsArrayString students)/*-{
			this.studentPresent = students;
	}-*/;
	public final native JsArrayString  getStudentAbsent()/*-{}-*/;
	public final native AttendanceJson setStudentAbsent(JsArrayString students)/*-{
		this.studentAbsent = students;
	}-*/;
	//tardy student will still fall under present category
	public final native JsArrayString  getAllStudents()/*-{}-*/;
	public final native JsArrayString  getStudentTardy()/*-{}-*/;
	public final native AttendanceJson setStudentTardy(JsArrayString students)/*-{
		this.studentTardy = students;
	}-*/;
	public final native JsArrayString getStudentAttendanceKeys()/*-{}-*/;
}
