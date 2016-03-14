package net.videmantay.roster.seatingchart.json;

import com.google.gwt.core.client.JavaScriptObject;


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
	
	public final native FurnitureJson[] getFurniture()/*-{
			return this.furniture;
	}-*/; 
	
	public final native void setFurniture(FurnitureJson[] furniture)/*-{
			this.furniture = furniture;
	}-*/;
}
