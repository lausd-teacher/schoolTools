package net.videmantay.shared.json;

import com.google.gwt.core.client.JavaScriptObject;

public class ExtProp extends JavaScriptObject {

	protected ExtProp(){}
	
	public final native String get(String key)/*-{
		return this['private'][key];
	
	}-*/;
	
	public final native ExtProp set(String key, String value)/*-{
		this['private'][key] = value;
	}-*/;
}
