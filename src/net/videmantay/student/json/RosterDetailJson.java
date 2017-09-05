package net.videmantay.student.json;

import com.google.gwt.core.client.JavaScriptObject;

import net.videmantay.shared.GradeLevel;

public class RosterDetailJson extends JavaScriptObject{
	
	protected RosterDetailJson(){}
	
	public final native Long getId()/*-{
		return this.id;
	}-*/;

	public final native RosterDetailJson setId(Long rosterId)/*-{
		this.id = rosterId;
		return this;
	}-*/;

	public final native String getTitle()/*-{
		return this.title;
	}-*/;

	public final native RosterDetailJson setTitle(String title)/*-{
		this.title = title;
		return this;
	}-*/;

	public final native String getDescription()/*-{
		return this.description;
	}-*/;

	public final native RosterDetailJson setDescription(String description)/*-{
		this.description = description;
		return this;
	}-*/;

	public final  native TeacherInfoJson getTeacherInfo()/*-{
		return this.teacherInfo;
	}-*/;

	public final native RosterDetailJson setTeacherInfo(TeacherInfoJson teacherInfo)/*-{
		this.teacherInfo = teacherInfo;
		return this;
	}-*/;

	public final native GradeLevel getGradeLevel()/*-{
		return this.gradeLevel;
	}-*/;

	public final native RosterDetailJson setGradeLevel(GradeLevel gradeLevel)/*-{
		this.gradeLevel = gradeLevel;
		return this;
	}-*/;

	
}
