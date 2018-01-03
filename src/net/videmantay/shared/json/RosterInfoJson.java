package net.videmantay.shared.json;

import com.google.gwt.core.client.JavaScriptObject;
import net.videmantay.student.json.TeacherInfoJson;

public class RosterInfoJson extends JavaScriptObject {
	protected RosterInfoJson(){}
	
	public final native Long getId()/*-{
		return this.id;
	}-*/;
	
	public final native String getName()/*-{
			return this.name;
	}-*/;
	public final native RosterInfoJson setName(String name)/*-{
		this.name = name;
		return this;
	}-*/;
	public final native String getDescription()/*-{
		return this.description;
	}-*/;
	public final native RosterInfoJson setDescription(String descript)/*-{
		this.description = descript;
		return this;
	}-*/;
	public final native  String getStart()/*-{
		return this.start;
	}-*/;
	public final native RosterInfoJson setStart(String start)/*-{
		this.start = start;
		return this;
	}-*/;
	public final native String getEnd()/*-{
		return this.end;
	}-*/;
	public final native RosterInfoJson setEnd(String end)/*-{
		this.end = end;
		return this;
	}-*/;
	public final native String getRoomNum()/*-{
		return this.roomNum;
	}-*/;
	public final native RosterInfoJson setRoomNum(String roomNum)/*-{
			
			this.roomNum =roomNum;
			return this;
	}-*/;

	public final native TeacherInfoJson getTeacherInfo()/*-{
			return this.teacherInfo;
	}-*/;
	public final native RosterInfoJson setTeacherInfo(TeacherInfoJson teacherInfo)/*-{
		this.teacherInfo = teacherInfo;
		return this;
	}-*/;
	
	public final native String getColor()/*-{
		return this.color;
	}-*/;
	public final native RosterInfoJson setColor(String color)/*-{
		this.color = color;
		return this;
	}-*/;
	
}
