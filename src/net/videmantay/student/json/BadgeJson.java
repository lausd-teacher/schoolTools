package net.videmantay.student.json;

import com.google.gwt.core.client.JavaScriptObject;

import net.videmantay.shared.BadgeType;

public class BadgeJson extends JavaScriptObject {

	protected BadgeJson(){}
	
	public final native String getTitle() /*-{
		return title;
	}-*/;
	public final native void setTitle(String title) /*-{
		this.title = title;
	}-*/;
	public final native String getDescription() /*-{
		return description;
	}-*/;
	public final native void setDescription(String description) /*-{
		this.description = description;
	}-*/;
	public final native String getUrl() /*-{
		return url;
	}-*/;
	public final native void setUrl(String url) /*-{
		this.url = url;
	}-*/;
	public final native BadgeType getType() /*-{
		return type;
	}-*/;
	public final native void setType(BadgeType type) /*-{
		this.type = type;
	}-*/;
}
