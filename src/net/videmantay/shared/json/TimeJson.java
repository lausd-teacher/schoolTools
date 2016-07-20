package net.videmantay.shared.json;

import com.google.gwt.core.client.JavaScriptObject;

public class TimeJson extends JavaScriptObject{
	protected TimeJson(){}
	
	public final native String getDate()/*-{
		return this.date;
	}-*/;
	
	public final native TimeJson setDate(String date)/*-{
		this.date = date;
		return this;
	}-*/;
	
	public final native String getDateTime()/*-{
		return this.datetime;
	}-*/;
	
	public final native TimeJson setDatetime(String date)/*-{
	this.datetime = date;
	return this;
}-*/; 
	
	public final native String getTimeZone()/*-{
		return this.timeZone;
	}-*/;
	
	public final native TimeJson setTimeZone(String zone)/*-{
	this.timeZone = zone;
	return this;
}-*/;
}//end TimeJson
