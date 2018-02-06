package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class ScheduleJson extends JavaScriptObject {

	protected ScheduleJson(){};
	
	public final native JsArray<ScheduleItemJson> getItems()/*-{
		return this.items;
	}-*/;
	
	public final native ScheduleJson setItem(JsArray<ScheduleItemJson> items)/*-{
			this.items = items;
				return this;
	}-*/;
	
	public final native Long getId()/*-{
			return this.id;
	}-*/;
}
