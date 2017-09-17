package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;

public class RoutineJson extends JavaScriptObject {
	protected RoutineJson(){}
	
	public final native Long getId()/*-{
			return this.id;
	}-*/;
	public final native String getTitle()/*-{
			return this.title;
	}-*/;
	public final native String getDescription()/*-{
			return this.description;
	}-*/;
	public final native String getColor()/*-{
				return this.color;
	}-*/;
	public final native Boolean getIsDefault()/*-{
			return this.isDefault;
	}-*/;
	
	
	public final native RoutineJson setTitle(String title)/*-{
		this.title = title;
		return this;
	}-*/;
	public final native RoutineJson setDescription(String description)/*-{
		this.description = description;
		return this;
	}-*/;
	public final native RoutineJson setColor(String color)/*-{
		this.color = color;
		return this;
	}-*/;
	public final native RoutineJson setIsDefault(Boolean isDefault)/*-{
		this.isDefault = isDefault;
		return this;
	}-*/;
}
