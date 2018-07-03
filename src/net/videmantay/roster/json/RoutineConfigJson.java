package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;


public class RoutineConfigJson extends JavaScriptObject {
	protected RoutineConfigJson(){}
	
	
	public final native JsArray<GroupJson> getGroups()/*-{
		return this.groups;
	}-*/;
	

	public final native  JsArray<ProcedureJson> getProcedures()/*-{
		return this.procedures;
	}-*/;
	

	public final native  StationsJson getStations()/*-{
		return this.stations;
	}-*/;
	

	//setters 
	
public final native void setGroups(JsArray<GroupJson> groups)/*-{
		this.groups = groups;
}-*/;
	

	public final native  void setProcedures(JsArray<ProcedureJson> proc)/*-{
		this.procedures = proc;
	}-*/;
	

	public final native void setStations( StationsJson sta)/*-{
		this.stations = sta;
	}-*/;
	
	
	public final native void removeProcedure(int index)/*-{
		this.procedures.splice(index,1);
	}-*/;
	
	public final native void removeStation(int index)/*-{
	this.stations.areas.splice(index,1);
}-*/;
}
