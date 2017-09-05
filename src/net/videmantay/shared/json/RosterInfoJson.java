package net.videmantay.shared.json;

import com.google.gwt.core.client.JavaScriptObject;
import net.videmantay.student.json.TeacherInfoJson;

public class RosterInfoJson extends JavaScriptObject {
	protected RosterInfoJson(){}
	
	public final native Long getId()/*-{}-*/;
	public final native String getName()/*-{}-*/;
	public final native RosterInfoJson setName(String name)/*-{}-*/;
	public final native String getDescription()/*-{}-*/;
	public final native RosterInfoJson setDescription(String descript)/*-{}-*/;
	public final native  String getStart()/*-{}-*/;
	public final native RosterInfoJson setStart(String start)/*-{}-*/;
	public final native String getEnd()/*-{}-*/;
	public final native RosterInfoJson setEnd(String end)/*-{}-*/;
	public final native String getRoomNum()/*-{}-*/;
	public final native RosterInfoJson setRoomNum(String roomNum)/*-{
			
			this.roomNum =roomNum;
			return this;
	}-*/;

	public final native TeacherInfoJson getTeacherInfo()/*-{}-*/;
	public final native RosterInfoJson setTeacherInfo(TeacherInfoJson teacherInfo)/*-{
		this.teacherInfo = teacherInfo;
		return this;
	}-*/;
	
}
