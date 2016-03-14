package net.videmantay.roster.seatingchart.json;

import com.google.gwt.core.client.JavaScriptObject;

public class FurnitureJson extends JavaScriptObject {

	protected FurnitureJson(){}
	
	public final native String getTop()/*-{
			return this.top;
		}-*/;
	
	public final native void setTop(String top)/*-{
			this.top = top;
		}-*/;
	
	public final native String getLeft()/*-{
			return this.left;
		}-*/;
	
	public final native void setLeft(String left)/*-{
			this.left = left;
		}-*/;
	
	public final native Double getRotate()/*-{
			return this.rotate;
		}-*/;
	
	public final native void setRotate(Double rotate)/*-{
			this.rotate = rotate;
		}-*/;
	
	public final native String getType()/*-{
			return this.type;
		}-*/;
	
	public final native void setType(String type)/*-{
			this.type = type;
	}-*/;
	
	public final native Integer getZIndex()/*-{
			return this.zIndex;
		}-*/;
	
	public final native void setZIndex(Integer zIndex)/*-{
		this.zIndex = zIndex;
	}-*/;
	
	public final native String getIconUrl()/*-{
			return this.iconUrl;
		}-*/;
	public final native void setIconUrl(String icon)/*-{
			this.IconUrl = icon;
	}-*/;
	
	public final native String getBackgroundColor()/*-{
			return this.backgroundColor;
		}-*/;
	
	public final native void setBackgroundColor(String color)/*-{
			this.backgroundColor = color;
	}-*/;
	
	public final native String getKind()/*-{
			return this.kind;
	}-*/;
	
	public final native void setKind(String kind)/*-{
		this.kind = kind;
	}-*/;
}
