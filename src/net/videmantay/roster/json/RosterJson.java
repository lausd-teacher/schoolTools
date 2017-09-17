package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

import net.videmantay.student.json.RosterStudentJson;
import net.videmantay.student.json.StudentJobJson;

public class RosterJson extends JavaScriptObject {

	protected RosterJson(){}

	
	public final  native StudentJobJson[] getStudentJobs()/*-{
		return this.studentJobs;
	}-*/;

	public final native RosterJson setStudentJobs(StudentJobJson[] studentJobs)/*-{
		this.studentJobs = studentJobs;
		return this;
	}-*/;

	public final native Long getId()/*-{
		return this.id;
	}-*/;

	public final native String getOwnerId()/*-{
		return this.ownerId;
	}-*/;

	public final native RosterJson setOwnerId(String ownerId)/*-{
		this.ownerId = ownerId;
		return this;
	}-*/;

	
	public final native JsArray<GoogleServiceJson> getGoogleCalendars()/*-{
		return this.googleCalendars;
	}-*/;
	
	public final native RosterJson setGoogleCalendars(JsArray<GoogleServiceJson> googleCals)/*-{
		this.googleCalendars = googleCals;
		return this;
	}-*/;
	
	public final native JsArray<GoogleServiceJson> getGoogleTasks()/*-{
		return this.googleTasks;
	}-*/;
	
	public final native RosterJson setGoogleTasks(JsArray<GoogleServiceJson> googleTasks)/*-{
		this.googleTasks = googleTasks;
		return this;
	}-*/;
	
	public final native String getRosterFolderId()/*-{
		return this.rosterFolderId;
	}-*/;

	public final native RosterJson setRosterFolderId(String rosterFolderId)/*-{
		this.rosterFolderId = rosterFolderId;
		return this;
	}-*/;
	
	public final native JsArray<RosterStudentJson> getRosterStudents()/*-{
		return this.students;
	}-*/;

	public final native RosterJson setRosterStudents(JsArray<RosterStudentJson> rosterStudents)/*-{
		this.students = rosterStudents;
		return this;
	}-*/;

	public final native RoutineConfigJson getRoutineConfig()/*-{}-*/;
	
	public final native RosterJson setRosterConfig(RoutineConfigJson config)/*-{}-*/;

	public final native JsArray<RoutineJson> getRoutines()/*-{
			return this.routines;
	}-*/;
	
	public final native RosterJson setRoutines(JsArray<RoutineJson> classTimes)/*-{
						this.classTimes = classTimes;
	}-*/;
	
	
	
	
}
