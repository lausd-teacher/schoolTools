package net.videmantay.roster.seatingchart.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;


import net.videmantay.student.json.StudentGroupJson;

public class SeatingChartDetailJson extends JavaScriptObject {

	protected SeatingChartDetailJson(){}
	
	//title
			//description of proceedures
			//list of groups and  sections
			// a one to one with a seatingChart
			//durations in secs or minutes how long it should be on that time???
			//timer's good for section rotation
	public final native JsArray<StudentGroupJson> getGroups()/*-{
				return this.groups;
	}-*/;
	
	public final native JsArray<SectionJson> getSections()/*-{}-*/;
	
	// this is the same as seatingChart details
	public final native SeatingChartJson getSeatingChart()/*-{}-*/;
	
	public final native Long getId()/*-{
	return this.id;
}-*/;

public final native void setId(Long id)/*-{
	 this.id = id;
}-*/;
public final native String getTitle()/*-{
	return this.title;
}-*/;

public final native void setTitle(String title)/*-{
	 this.title = title;
}-*/;

public final native String getDescript()/*-{
		return this.descript;
}-*/;

public final native void setDescript(String descript)/*-{
	 this.descript = descript;
}-*/;
		
}
