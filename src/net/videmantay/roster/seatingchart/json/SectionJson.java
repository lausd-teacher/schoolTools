package net.videmantay.roster.seatingchart.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;

import net.videmantay.student.json.RosterStudentJson;

public class SectionJson extends JavaScriptObject {
	protected SectionJson(){}

	public final native String getColor()/*-{
		return this.color;
	}-*/;
	
	public final native String getName()/*-{
		return this.name;
	}-*/;
	
	public final native JavaScriptObject getOffset()/*-{
		return this.offset;
	}-*/;
	
	public final native String getType()/*-{
		return this.type;
	}-*/;
	
	public final native JavaScriptObject getBoundingBox()/*-{
		return this.boundingBox;
	}-*/;
	
	public final native String getZIndex()/*-{
		return $wnd.$(this).css('zIndex');
	}-*/;
	
	public final native JsArray<DeskJson> getDesks()/*-{
			return this.desks;
	}-*/;
	
	public final native JsArray<RosterStudentJson> getStudents()/*-{
			return this.students;
	}-*/;
	
	public final native int getSectionNum()/*-{
				return this.sectionNum;
	}-*/;
	
	public final native JsArrayString getStudentRotation()/*-{}-*/;
	
	
	/////////SETTERS///////////////////////////////////////////////////
	public final native SectionJson setColor(String color)/*-{
			this.color = color;
			return this;
	}-*/;
	
	public final native SectionJson setName(String name)/*-{
			this.name = name;
			return this;
	}-*/;
	
	public final native SectionJson setOffset(JavaScriptObject offset)/*-{
			this.offset = offset;
			return this;
	}-*/;
	
	public final native SectionJson setType(String type)/*-{
			this.type= type;
			return this;
	}-*/;
	

	public final native SectionJson setDesks(JsArray<DeskJson> desks)/*-{
		this.desks  = desks;
		return this;
	}-*/;
	
	public final native SectionJson setStudents(JsArray<RosterStudentJson> students)/*-{
				this.students = students;
				return this;
	}-*/;
	
	public final native SectionJson setSectionNum(int sectionNum)/*-{
				this.sectionNum = sectionNum;
				return this;
	}-*/;
	
	
	
}
