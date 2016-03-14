package net.videmantay.roster.seatingchart.json;

import com.google.gwt.core.client.JavaScriptObject;

public class StudentSeatJson extends JavaScriptObject {
	
	protected StudentSeatJson(){};
	
	public final native Integer getSeatNum()/*-{
		return this.seatNum;
	}-*/;
	
	public final native void setSeatNum(Integer num)/*-{
	 	this.seatNum = num;
	}-*/;
	
	public final native Long getRosterStudent()/*-{
		return this.rosterStudent;
	}-*/;
	
	public final native void setRosterStudent(Long student)/*-{
	 	this.rosterStudent = student;
	}-*/;
	
	public final native Long getStudentGroup()/*-{
		return this.studentGroup;
	}-*/;
	
	public final native void getStudentGroup(Long group)/*-{
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
		if(this.rosterStudent != null || this.rosterStudent != 0){
			this.isEmpty = true;}else{
				this.isEmpty = false;}
				return this.isEmpty;
	}-*/;
}
