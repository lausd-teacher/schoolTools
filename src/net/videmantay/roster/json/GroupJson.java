package net.videmantay.roster.json;

import java.util.HashSet;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;


public class GroupJson extends JavaScriptObject{

	protected GroupJson(){}
	
	public final native  Integer getNum()/*-{
		return this.num;
	}-*/;
	public final native  String getTitle()/*-{
			return this.title;
	}-*/;
	
	public final native  String getObjective()/*-{
			return this.objective;
	}-*/;
	
	public final native  String getType()/*-{
			return this.type;
	}-*/;
	
	public final native  String getBackGroundColor()/*-{
			return this.backGroundColor;
	}-*/;
	
	public final native  String getTextColor()/*-{
			return this.textColor;
	}-*/;
	
	public final native  String getBorderColor()/*-{
	
			return this.borderColor;
	}-*/;
	
	public final native GroupJson getStudents()/*-{
		return this.students;
	}-*/;
	
	//setters
	public final native  GroupJson setNum(Integer num)/*-{
		this.num = num;
	return this;
}-*/;
public final native  GroupJson setTitle(String title)/*-{
	this.title = title;
		return this;
}-*/;

public final native  GroupJson setObjective(String obj)/*-{
	this.objective = obj;
		return this;
}-*/;

public final native  GroupJson setType(String type)/*-{
	this.type = type;
		return this;
}-*/;

public final native  GroupJson setBackGroundColor(String bg)/*-{
	this.backgroundColor = bg;
		return this;
}-*/;

public final native  GroupJson setTextColor(String tc)/*-{
	this.textColor = tc;
		return this;
}-*/;

public final native  GroupJson setBorderColor(String bc)/*-{
	this.borderColor = bc;
		return this;
}-*/;

public final native GroupJson setStudents(JsArrayString stu)/*-{
	this.students = stu;
	return this;
}-*/;
}
