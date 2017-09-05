package net.videmantay.student.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayNumber;
import net.videmantay.shared.GroupingType;

public class StudentGroupJson extends JavaScriptObject {

	protected StudentGroupJson(){}
	
	public final native Long getId() /*-{
		return this.id;
	}-*/;

	public final native void setId(Long id) /*-{
		this.id = id;
	}-*/;

	public final native String getTitle() /*-{
		return this.title;
	}-*/;

	public final native void setTitle(String title) /*-{
		this.title = title;
	}-*/;

	public final native String getObjective() /*-{
		return this.objective;
	}-*/;

	public final native void setObjective(String objective) /*-{
		this.objective = objective;
	}-*/;

	public final native GroupingType getType() /*-{
		return this.type;
	}-*/;

	public final native void setType(GroupingType type) /*-{
		this.type = type;
	}-*/;

	public final native String getBackGroundColor() /*-{
		return this.backGroundColor;
	}-*/;

	public final native void setBackGroundColor(String backGroundColor) /*-{
		this.backGroundColor = backGroundColor;
	}-*/;

	public final native String getTextColor() /*-{
		return this.textColor;
	}-*/;

	public final native void setTextColor(String textColor) /*-{
		this.textColor = textColor;
	}-*/;

	public final native String getBorderColor() /*-{
		return this.borderColor;
	}-*/;

	public final native void setBorderColor(String borderColor) /*-{
		this.borderColor = borderColor;
	}-*/;

	public final native JsArrayNumber getStudents() /*-{
		return this.students;
	}-*/;

	public final native void setStudents(JsArrayNumber students) /*-{
		this.students = students;
	}-*/;
}
