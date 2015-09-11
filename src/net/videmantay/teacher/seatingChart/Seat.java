package net.videmantay.teacher.seatingChart;

import com.google.gwt.core.client.JavaScriptObject;

public class Seat extends JavaScriptObject{
	protected Seat(){};
	public final static native Seat create()/*-{
	return {};}-*/;
	
	public final native Seat setStudentId(Long id)/*-{
		this.studentId = id;
		return this;
	}-*/;
	
	public final native Long getStudentId()/*-{
		return this.studentId;
	}-*/;
	
	public final native Seat setPosition(int pos)/*-{
		this.position = pos;
		return this;
	}-*/;
	
	public final native int getPosition()/*-{
		return this.position;
	}-*/;
}//end Seat Class
