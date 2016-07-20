package net.videmantay.shared.json;

import com.google.gwt.core.client.JavaScriptObject;

public class Gadget extends JavaScriptObject {

	protected Gadget(){}
	
	public final native String getDisplay()/*-{
		return this.display;
	}-*/; 
	
	public final native Integer getHeight()/*-{
		return this.height;
	}-*/;
	
	public final native String getIconLink()/*-{
		return this.iconLink;
	}-*/;
	
	public final native String getLink()/*-{
		return this.link;
	}-*/;
	
	public final native String getTitle()/*-{
		return this.title;
	}-*/;
	
	public final native String getType()/*-{
		return this.type;
	}-*/;
	
	public final native Integer getWidth()/*-{
		return this.width;
	}-*/;
	
	public final native String getPref(String name)/*-{
	return this['preferences'][name];
	}-*/;
	
	
}
