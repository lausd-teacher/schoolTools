package net.videmantay.student.json;

import com.google.gwt.core.client.JavaScriptObject;

public class IncidentJson extends JavaScriptObject {

	protected IncidentJson(){}
	
	public final native Long getId() /*-{
	return this.id;
}-*/;
public final native void setId(Long id) /*-{
	this.id = id;
}-*/;

public final native Long getRosterId()/*-{
	return this.rosterId;
}-*/;

public final native String getName() /*-{
	return this.name;
}-*/;
public final native void setName(String name) /*-{
	this.name = name;
}-*/;
public final native String getImageUrl() /*-{
	return this.imageUrl;
}-*/;
public final native void setImageUrl(String imageUrl) /*-{
	this.imageUrl = imageUrl;
}-*/;

public final native int getPoints() /*-{
   return this.points;
}-*/;
public final native void setPoints(int incidentPoints) /*-{
   this.points = incidentPoints;
}-*/;

public  static IncidentJson create(){
	IncidentJson incident =IncidentJson.createObject().cast();
	incident.setImageUrl("");
	incident.setName("");
	incident.setPoints(0);
	return incident;
}
	
}
