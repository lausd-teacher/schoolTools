package net.videmantay.roster.seatingchart.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

import net.videmantay.shared.DeskKind;

public class DeskJson extends FurnitureJson {

	protected DeskJson(){
		
	}
	
	
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
		this.seats = seats;
	}-*/;
	
	public final void setSeats(String kind){
		int numOfSeats = 0;
		switch(kind){
		case DeskKind.DOUBLE:numOfSeats = 2; break;
		case DeskKind.SINGLE: numOfSeats = 1;break;
		case DeskKind.KIDNEY: numOfSeats = 5;break;
		case DeskKind.CARPET_LG: numOfSeats = 24;break;
		case DeskKind.CARPET_SM: numOfSeats = 30;break;
		}
		for(int i = 1; i <= numOfSeats; i++){
			StudentSeatJson seat = JavaScriptObject.createObject().cast();
			seat.setSeatNum(i);
			this.getSeats().push(seat);
		}
	}
		
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
