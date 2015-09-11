package net.videmantay.teacher.seatingChart;

import java.util.Arrays;

import com.google.gwt.core.client.JavaScriptObject;


public class SeatingChart extends JavaScriptObject{
	
	protected SeatingChart(){}
	
	public static final native SeatingChart create()/*-{
			return {
					desks: new Array(),
					title:'',
					description:'',
					createOn: new Date(),
					thumbNail:''
			};
		}-*/;
	
	public final native Desk[] getDesks()/*-{
		return this.desks;
	}-*/;
	
	public SeatingChart addDesk(Desk desk){
		Arrays.asList(this.getDesks()).add(desk);
		return this;
	}
	
	public SeatingChart removeDesk(Desk desk){
		Arrays.asList(this.getDesks()).remove(desk);
		return this;
	}
	
	public SeatingChart clearDesks(){
		Arrays.asList(this.getDesks()).clear();
		return this;
	}
	

}
