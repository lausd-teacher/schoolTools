package net.videmantay.roster.seatingchart.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

import net.videmantay.shared.DeskKind;

public class FurnitureJson extends JavaScriptObject {

	protected FurnitureJson(){}
	
	public final native String getTop()/*-{
			return this.top;
		}-*/;
	
	public final native void setTop(String top)/*-{
			this.top = top;
		}-*/;
	
	public final native String getLeft()/*-{
			return this.left;
		}-*/;
	
	public final native void setLeft(String left)/*-{
			this.left = left;
		}-*/;
	
	public final native String getTransform()/*-{
			return this.transform;
	}-*/;
	
	public final native void setTransform(String transform)/*-{
		this.transform = transform;
	}-*/;
	
	public final native double getRotate()/*-{
		if(this.rotate == null){
			this.rotate = 0.0;
		}
			return this.rotate;
		}-*/;
	
	public final native void setRotate(double rotate)/*-{
			this.rotate = rotate;
		}-*/;
	
	public final native String getType()/*-{
		//furniture type - desk, teacherdesk, bookshelf, etc
			return this.type;
		}-*/;
	
	public final native void setType(String type)/*-{
		//furniture type - desk, teacherdesk, bookshelf, etc
			this.type = type;
	}-*/;
	
	public final native void setKind(String kind)/*-{
		this.kind = kind;
	}-*/;
	
	public final native String getKind()/*-{
		return this.kind;
	}-*/;
	
	public final native Integer getZIndex()/*-{
			return this.zIndex;
		}-*/;
	
	public final native void setZIndex(Integer zIndex)/*-{
		this.zIndex = zIndex;
	}-*/;
	
	public final native String getIconUrl()/*-{
			return this.iconUrl;
		}-*/;
	public final native void setIconUrl(String icon)/*-{
			this.IconUrl = icon;
	}-*/;
	
	public final native String getBackgroundColor()/*-{
			return this.backgroundColor;
		}-*/;
	
	public final native void setBackgroundColor(String color)/*-{
			this.backgroundColor = color;
	}-*/;
	
	public final native String getId()/*-{
	
	return this.id;
}-*/;

public final native void setId(String id)/*-{
	this.id = id;
	return this;
}-*/;
public final native JsArray<StudentSeatJson> getSeats()/*-{
return this.seats;
}-*/;

public final native void setSeats(JsArray<StudentSeatJson> seats)/*-{
this.seats = seats;
this.type="desk";
}-*/;

public final void setSeats(String kind){
JsArray<StudentSeatJson> seats = JavaScriptObject.createArray().cast();
this.setSeats(seats);
int numOfSeats = 0;
switch(kind){
case DeskKind.DOUBLE:numOfSeats = 2;this.setKind("double"); break;
case DeskKind.SINGLE: numOfSeats = 1; this.setKind("single");break;
case DeskKind.KIDNEY: numOfSeats = 5; this.setKind("kidney");break;
case DeskKind.CARPET_LG: numOfSeats = 24;this.setKind("carpetLg");break;
case DeskKind.CARPET_SM: numOfSeats = 30; this.setKind("carpetSm");break;
}
for(int i = 1; i <= numOfSeats; i++){
	StudentSeatJson seat = JavaScriptObject.createObject().cast();
	seat.setSeatNum(i);
	this.getSeats().push(seat);
}
}

public final native StudentSeatJson getSeatByNum(int i)/*-{

for(var s in this.seats){
	if( s.getSeatNum() == i){
		return s;
		}//end if
	}//end for
return null;
}-*/;

public final static native FurnitureJson create()/*-{
	return {rotate:0.0, 
			kind:"double",
			type:"desk",
			top:"0px",
			left:"0px",
			seats:[]};
	
}-*/;

public final  native void nullify()/*-{
	var $this  = this;
	$this = null;
	}-*/;
	
}
