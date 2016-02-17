package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;

public class GoogleServiceJson extends JavaScriptObject {

	protected GoogleServiceJson(){}
	
	public final native String getTitle() /*-{
		return this.title;
	}-*/;
	public final native void setTitle(String title) /*-{
		this.title = title;
	}-*/;
	public final native String getId() /*-{
		return this.id;
	}-*/;
	public final native void setId(String id) /*-{
		this.id = id;
	}-*/;
	public final native String getDescription() /*-{
		return this.description;
	}-*/;
	public final native void setDescription(String description) /*-{
		this.description = description;
	}-*/;
	public final native String getType() /*-{
		return this.type;
	}-*/;
	public final native void setType(String type) /*-{
		this.type = type;
	}-*/;
}
