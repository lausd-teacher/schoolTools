package net.videmantay.student.json;


import com.floreysoft.gwt.picker.client.domain.result.Thumbnail;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayInteger;
import com.google.gwt.core.client.JsArrayString;

import net.videmantay.shared.json.RosterInfoJson;


public class RosterStudentJson extends JavaScriptObject{

	protected RosterStudentJson(){}
	
	public final native  String getId()/*-{
	return this.id;
}-*/;
	
	public final native  boolean getUseThumbs()/*-{
	return this.useThumbs;
}-*/;
	public final native RosterStudentJson setUseThumbs(boolean useThumbs)/*-{
		this.useThumbs = useThumbs;
		return this;
	}-*/;
	public final native  String getReadingLevel()/*-{
	return this.readingLevel;
}-*/;
	public final native  String getHomeLang()/*-{
	return this.homeLang;
}-*/;
	public final native RosterStudentJson setHomeLang(String lang)/*-{
		this.homeLang = lang;
		return this;
	}-*/;
	public final native  JsArrayString getModifications ()/*-{
	return this.modifications;
}-*/;
	public final native RosterStudentJson setModifications(JsArrayString mods)/*-{
		this.modifications = mods;
		return this;
	}-*/;
	public final native  Boolean getIEP()/*-{
	return this.IEP;
}-*/;
	
	public final native RosterStudentJson setIEP(Boolean iep)/*-{
	this.IEP = iep;
	return this;
	}-*/;
	public final native  String getCurrentSummary()/*-{
	return this.currentSummary;
}-*/;
	public final native RosterStudentJson setCurrentSummary(String summary)/*-{
		this.currentSummary = summary;
	}-*/;
	
	public final native RosterStudentJson setReadingLevel(String level)/*-{
		this.readingLevel = level;
		return this;
	}-*/;
	public final native  String driveFolder()/*-{
	return this.modifications;
}-*/;
	public final native RosterStudentJson setDriveFolder(String folder)/*-{
		this.driveFolder = folder;
	}-*/;
	
	public final native Long getRosterId() /*-{
		return this.rosterId;
	}-*/;
	
	public final native RosterStudentJson setRosterId(Long id)/*-{
		this.rosterId = id;
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
	public final native JsArray<StudentGroupJson> getGroups() /*-{
	return this.jobs;
}-*/;
public final native RosterStudentJson setGroups(JsArray<StudentGroupJson> groups) /*-{
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
	
	public final native String getPicUrl() /*-{
	return this.picUrl;
}-*/;
public final native RosterStudentJson setPicUrl(String url) /*-{
	this.picUrl = url;
	return this;
}-*/;
	public final native RosterInfoJson getRosterInfo()/*-{
		return this.rosterInfo;
	}-*/;
	
	public final native RosterStudentJson setRosterInfoJson(RosterInfoJson rosDe)/*-{
		this.rosterInfo = rosDe;
		return this;
	}-*/;
	
	
	public final native JsArrayInteger getPosPoints()/*-{
		return this.posPoints;
	}-*/;
	
	public final native RosterStudentJson setPosPoints(JsArrayInteger points)/*-{
		this.posPoints = points;
	return this;
}-*/;
	
	public final native JsArrayInteger getNegPoints()/*-{
	return this.negPoints;
}-*/;

public final native RosterStudentJson setNegPoints(JsArrayInteger points)/*-{
	this.negPoints =points;
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
