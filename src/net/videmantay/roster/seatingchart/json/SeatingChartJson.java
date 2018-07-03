package net.videmantay.roster.seatingchart.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

import net.videmantay.roster.json.GroupJson;
import net.videmantay.roster.json.ProcedureJson;
import net.videmantay.roster.json.RoutineConfigJson;
import net.videmantay.roster.json.StationsJson;


public class SeatingChartJson extends JavaScriptObject {

	protected SeatingChartJson(){}
	
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
	
	public final native JsArray<FurnitureJson> getFurniture()/*-{
			return this.furniture;
	}-*/; 
	
	public final native void setFurniture(JsArray<FurnitureJson> furniture)/*-{
			this.furniture = furniture;
	}-*/;
	
	public final native RoutineConfigJson getRoutine()/*-{
			return this.routine;
	}-*/;
	
	public final native void getRoutine(RoutineConfigJson routine)/*-{
	 		this.routine = routine;
}-*/;
	
	
	

	
}
