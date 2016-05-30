package net.videmantay.student.json;

import com.floreysoft.gwt.picker.client.domain.result.Thumbnail;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayInteger;


public class RosterStudentJson extends JavaScriptObject{

	protected RosterStudentJson(){}
	
	public final native Long getRoster() /*-{
		return this.roster;
	}-*/;
	public final native RosterStudentJson setRoster(Long roster) /*-{
		this.roster = roster;
		return this;
	}-*/;
	public final native String getFirstName() /*-{
		return this.firstName;
	}-*/;
	public final native RosterStudentJson setFirstName(String firstName) /*-{
		this.firstName = firstName;
		return this;
	}-*/;
	
	public final native Boolean getGlasses() /*-{
		return this.glasses;
	}-*/;
	public final native RosterStudentJson setGlasses(Boolean glasses) /*-{
		this.glasses = glasses;
		return this;
	}-*/;
	public final native String getEldLevel() /*-{
		return this.eldLevel;
	}-*/;
	public final native RosterStudentJson setEldLevel(String eldLevel) /*-{
		this.eldLevel = eldLevel;
		return this;
	}-*/;
	public final native JsArray<StudentJobJson> getJobs() /*-{
		return this.jobs;
	}-*/;
	public final native RosterStudentJson setJobs(JsArray<StudentJobJson> jobs) /*-{
		this.jobs = jobs;
		return this;
	}-*/;
	public final native String getLastName() /*-{
		return this.lastName;
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
	public final native JsArray<Thumbnail> getThumbnails() /*-{
		return this.thumbnails;
	}-*/;
	public final native RosterStudentJson setThumbnails(JsArray<Thumbnail> thumbnails) /*-{
		this.thumbnails = thumbnails;
		return this;
	}-*/;
	public final native String getDOB() /*-{
		return this.DOB;
	}-*/;
	public final native RosterStudentJson setDOB(String dOB) /*-{
		this.DOB = dOB;
		return this;
	}-*/;
	public final native String getAcctId() /*-{
		return this.acctId;
	}-*/;
	public final native RosterStudentJson setAcctId(String acctId) /*-{
		this.acctId = acctId;
		return this;
	}-*/;
	
	
	public final native JsArray<BadgeJson> getBadges() /*-{
		return this.badges;
	}-*/;
	public final native RosterStudentJson setBadges(JsArray<BadgeJson> badges) /*-{
		this.badges = badges;
		return this;
	}-*/;
	public final native Long getId()/*-{
		return this.id;
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
		return this.studentFolderId;
	}-*/;
	public final native RosterStudentJson setStudentFolderId(String studentFolderId) /*-{
		this.studentFolderId = studentFolderId;
		return this;
	}-*/;
	
	public final native JsArrayInteger getPoints()/*-{
		return this.points;
	}-*/;
	
	public final native RosterStudentJson setPoints(JsArrayInteger points)/*-{
		this.points = points;
	return this;
}-*/;
	
	public final native String getStudentCalId() /*-{
		return this.studentCalId;
	}-*/;
	public final native RosterStudentJson setStudentCalId(String studentCalId) /*-{
		this.studentCalId = studentCalId;
		return this;
	}-*/;
	public final native String getStudentTasksId() /*-{
		return this.studentTasksId;
	}-*/;
	public final native RosterStudentJson setStudentTasksId(String studentTasksId) /*-{
		this.studentTasksId = studentTasksId;
		return this;
	}-*/;
	public final native Boolean getInactive() /*-{
		return this.inactive;
	}-*/;
	public final native RosterStudentJson setInactive(Boolean inactive) /*-{
		this.inactive = inactive;
		return this;
	}-*/;
}
