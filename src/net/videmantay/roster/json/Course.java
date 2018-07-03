package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class Course extends JavaScriptObject {
	protected Course(){};
	
	public final native JsArray<Section> section()/*-{
		return this.section;
	}-*/;
	
}
