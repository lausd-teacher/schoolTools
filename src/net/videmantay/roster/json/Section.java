package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;

public class Section extends JavaScriptObject {	
	protected Section() {}
	
	public final native String getCourse_title()/*-{
	return this.course_title;
}-*/;
public final native String getDescription()/*-{
	return this.description;
}-*/;

public final native String getProfileURL()/*-{
		return this.profile_url;
}-*/;

}
