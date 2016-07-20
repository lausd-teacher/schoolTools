package net.videmantay.shared.json;

import com.google.gwt.core.client.JavaScriptObject;

public class Reminder extends JavaScriptObject {

	protected Reminder(){}
	
	public final native String getMethod()/*-{
		return this.method;
	}-*/;
	
	public final native Integer getMinutes()/*-{
		return this.minutes;
	}-*/;
	
}
