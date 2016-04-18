package net.videmantay.roster.seatingchart.json;

import com.google.gwt.core.client.JsArray;

public class DeskJson extends FurnitureJson {

	protected DeskJson(){}
	
	
	public final native String getId()/*-{
	
			return this.id;
	}-*/;
	
	public final native DeskJson setId(String id)/*-{
			this.id = id;
			return this;
	}-*/;
	public final native JsArray<StudentSeatJson> getSeats()/*-{
		return this.seats;
	}-*/;
	
	public final native void setSeats(JsArray<StudentSeatJson> seats)/*-{
		
	}-*/;
	
	public final native StudentSeatJson getSeatByNum(int i)/*-{
		var seat = null;
		for(var s in this.seats){
			if( s.getSeatNum() == i){
				seat = s;
				break;
				}//end if
			}//end for
		return seat;
	}-*/;
	
}
