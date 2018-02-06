package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.core.client.JsArrayString;

public class ScheduleItemJson extends JavaScriptObject {

	protected ScheduleItemJson() {
	}

	public final native String getId()/*-{}-*/;

	public final native String getTitle()/*-{
		return this.title
	}-*/;

	public final native void setTitle(String title)/*-{
		this.title = title;
	}-*/;
	// verify date string

	public final native String getStart()/*-{
		return this.start;
	}-*/;

	public final native void setStart(String start)/*-{
		this.start = start;
	}-*/;

	// verify date string

	public final native String getEnd()/*-{
		return this.end;
	}-*/;

	public final native void setEnd(String end)/*-{
		this.end = end;
	}-*/;
	
	public final native JsArrayNumber getDOW()/*-{
			return this.dow;
	}-*/;
	
	public final native void setDOW(JsArrayNumber dow)/*-{
	this.dow = dow;
	}-*/;

	public final native String getImg()/*-{
		return this.img;
	}-*/;

	public final native void setImg(String img)/*-{
		this.img = img;
	}-*/;

	public final native String getConstraint()/*-{
		return this.constraint;
	}-*/;

	public final native void setConstraint(String constraint)/*-{
		this.constraint = constraint;
	}-*/;

	public final native JsArrayString getClassName()/*-{
		return this.constraint;
	}-*/;

	public final native void setClassName(JsArrayString className)/*-{
		this.className = className;
	}-*/;

	public final native String getColor()/*-{
		return this.color;
	}-*/;

	public final native void setColor(String color)/*-{
		this.color = color;
	}-*/;

	public final native String getTextColor()/*-{
		return this.textColor;
	}-*/;

	public final native void setTextColor(String color)/*-{
		this.textColor = color;
	}-*/;

	public final native String getBackgroundColor()/*-{
		return this.backgroundColor;
	}-*/;

	public final native void setBackgroundColor(String color)/*-{
		this.backgroundColor = color;
	}-*/;

	public final native String getBorderColor()/*-{
		return this.borderColor;
	}-*/;

	public final native void setBorderColor(String color)/*-{
		this.borderColor = color;
	}-*/;

}
