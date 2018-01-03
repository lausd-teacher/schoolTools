package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

import net.videmantay.student.json.IncidentJson;
import net.videmantay.student.json.RosterStudentJson;
import net.videmantay.student.json.StudentJobJson;

public class RosterJson extends JavaScriptObject {

	protected RosterJson(){}

	

	public final native Long getId()/*-{
		return this.id;
	}-*/;

	public final native String getSid()/*-{
		return this.sid;
	}-*/;
	
	public final native String getTaskListId()/*-{
	return this.taskListId;
}-*/;
	
	public final native String getCalendarId()/*-{
	return this.calendarId;
}-*/;

		
	public final native String getRosterFolderId()/*-{
		return this.teacherFolderId;
	}-*/;

	public final native RosterJson setRosterFolderId(String rosterFolderId)/*-{
		this.teacherFolderId = rosterFolderId;
		return this;
	}-*/;
	
	public final native JsArray<RosterStudentJson> getStudents()/*-{
		return this.students;
	}-*/;

	public final native RosterJson setStudents(JsArray<RosterStudentJson> rosterStudents)/*-{
		this.students = rosterStudents;
		return this;
	}-*/;

	public final native RoutineConfigJson getRoutineConfig()/*-{
			return this.defaultRoutine;
	}-*/;
	
	public final native RosterJson setRoutineConfig(RoutineConfigJson config)/*-{
	
			this.defaultRoutine = config;
			return this;
	}-*/;

	public final native JsArray<RoutineJson> getRoutines()/*-{
			return this.routines;
	}-*/;
	
	public final native RosterJson setRoutines(JsArray<RoutineJson> classTimes)/*-{
						this.routines = classTimes;
	}-*/;
	
	public final native JsArray<IncidentJson> getIncidents()/*-{
		return this.incidents;
	}-*/;
	
	public final native RosterJson setIncidents(JsArray<IncidentJson> incidents)/*-{
		this.incidents = incidents;
		return this;
	}-*/;
	
	
	public final native AttendanceJson getAttendance()/*-{
	return this.attendance;
}-*/;
	
	public final native RosterJson setAttendance(AttendanceJson attendance)/*-{
		this.attendance = attendance;
		return this;
	}-*/;
	
	public final native ScheduleJson getSchedule()/*-{
			return this.schedule;
	}-*/;
	
	public final native RosterJson setSchedule(ScheduleJson schedule)/*-{
			this.schedule = schedule;
			return this;
	}-*/;
	
	

}
