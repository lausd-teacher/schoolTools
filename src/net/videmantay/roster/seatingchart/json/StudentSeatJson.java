package net.videmantay.roster.seatingchart.json;

import com.google.gwt.core.client.JavaScriptObject;

public class StudentSeatJson extends JavaScriptObject {
	
	protected StudentSeatJson(){};
	
	public final native int getSeatNum()/*-{
		return this.seatNum;
	}-*/;
	
	public final native void setSeatNum(int num)/*-{
	 	this.seatNum = num;
	}-*/;
	
	public final native String getRosterStudent()/*-{
		return this.rosterStudent;
	}-*/;
	
	public final native void setRosterStudent(String student)/*-{
	 	this.rosterStudent = student;
	 	this.isEmpty = false;
	}-*/;
	
	public final native String getStudentGroup()/*-{
		return this.studentGroup;
	}-*/;
	
	public final native void getStudentGroup(String group)/*-{
		 this.studentGroup = group;
	}-*/;
	
	public final native String getColor()/*-{
		return this.color;
	}-*/;
	public final native void setColor(String color)/*-{
		 this.color = color;
	}-*/;
	
	public final native String getUrl()/*-{
		return this.url;
	}-*/;
	
	public final native String setUrl(String url)/*-{
		 this.url = url;
	}-*/;
	
	
	public final native boolean isEmpty()/*-{
		if(this.rosterStudent == null || this.rosterStudent == ""){
			this.isEmpty = true;}else{
				this.isEmpty = false;}
				return this.isEmpty;
	}-*/;
}
