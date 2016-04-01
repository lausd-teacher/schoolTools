package net.videmantay.student.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayInteger;


public class RosterStudentJson extends JavaScriptObject {

	protected RosterStudentJson(){}
	
	public final native Long getRoster() /*-{
		return this.roster;
	}-*/;
	public final native RosterStudentJson setRoster(Long roster) /*-{
		this.roster = roster;
		return this;
	}-*/;
	public final native String getFirstName() /*-{
		return firstName;
	}-*/;
	public final native RosterStudentJson setFirstName(String firstName) /*-{
		this.firstName = firstName;
		return this;
	}-*/;
	
	public final native Boolean getGlasses() /*-{
		return glasses;
	}-*/;
	public final native RosterStudentJson setGlasses(Boolean glasses) /*-{
		this.glasses = glasses;
		return this;
	}-*/;
	public final native String getEldLevel() /*-{
		return eldLevel;
	}-*/;
	public final native RosterStudentJson setEldLevel(String eldLevel) /*-{
		this.eldLevel = eldLevel;
		return this;
	}-*/;
	public final native JsArray<StudentJobJson> getJobs() /*-{
		return jobs;
	}-*/;
	public final native RosterStudentJson setJobs(JsArray<StudentJobJson> jobs) /*-{
		this.jobs = jobs;
		return this;
	}-*/;
	public final native String getLastName() /*-{
		return lastName;
	}-*/;
	public final native RosterStudentJson setLastName(String lastName) /*-{
		this.lastName = lastName;
		return this;
	}-*/;
	public final native String getExtName()/*-{
		return this.extName;
	}-*/;
	public final native RosterStudentJson setExtName(String extName)/*-{
		this.extName = extName;
		return this;
	}-*/;
	public final native String getPicUrl() /*-{
		return picUrl;
	}-*/;
	public final native RosterStudentJson setPicUrl(String picUrl) /*-{
		this.picUrl = picUrl;
		return this;
	}-*/;
	public final native String getDOB() /*-{
		return DOB;
	}-*/;
	public final native RosterStudentJson setDOB(String dOB) /*-{
		DOB = dOB;
		return this;
	}-*/;
	public final native String getStudentGoogleId() /*-{
		return studentGoogleId;
	}-*/;
	public final native RosterStudentJson setStudentGoogleId(String studentGoogleId) /*-{
		this.studentGoogleId = studentGoogleId;
		return this;
	}-*/;

	public final native JsArrayInteger getPositviePoints() /*-{
		return positviePoints;
	}-*/;
	public final native RosterStudentJson setPositviePoints(JsArrayInteger positviePoints) /*-{
		this.positviePoints = positviePoints;
		return this;
	}-*/;
	public final native JsArrayInteger getNegativePoints() /*-{
		return NegativePoints;
	}-*/;
	public final native RosterStudentJson setNegativePoints(JsArrayInteger negativePoints) /*-{
		NegativePoints = negativePoints;
		return this;
	}-*/;
	public final native JsArray<BadgeJson> getBadges() /*-{
		return goals;
	}-*/;
	public final native RosterStudentJson setBadges(JsArray<BadgeJson> badges) /*-{
		this.badges = goals;
		return this;
	}-*/;
	public final native Long getId()/*-{
		return id;
	}-*/;
	public final native RosterDetailJson getRosterDetail()/*-{
		return this.rosterDetail;
	}-*/;
	
	public final native RosterStudentJson setRostserDetail(RosterDetailJson rosDe)/*-{
		this.rosterDetail = rosDe;
		return this;
	}-*/;
	public final native RosterStudentJson setId(Long id) /*-{
		this.id = id;
	}-*/;
	
	public final native String getStudentFolderId() /*-{
		return studentFolderId;
	}-*/;
	public final native RosterStudentJson setStudentFolderId(String studentFolderId) /*-{
		this.studentFolderId = studentFolderId;
		return this;
	}-*/;
	public final native String getStudentCalId() /*-{
		return studentCalId;
	}-*/;
	public final native RosterStudentJson setStudentCalId(String studentCalId) /*-{
		this.studentCalId = studentCalId;
		return this;
	}-*/;
	public final native String getStudentTasksId() /*-{
		return studentTasksId;
	}-*/;
	public final native RosterStudentJson setStudentTasksId(String studentTasksId) /*-{
		this.studentTasksId = studentTasksId;
		return this;
	}-*/;
	public final native Boolean getInactive() /*-{
		return inactive;
	}-*/;
	public final native RosterStudentJson setInactive(Boolean inactive) /*-{
		this.inactive = inactive;
		return this;
	}-*/;
}
