package net.videmantay.roster.json;

import java.util.Set;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import net.videmantay.shared.GradeLevel;
import net.videmantay.shared.SubjectType;

public class AssignmentJson extends JavaScriptObject {

	protected AssignmentJson(){}
	
	public final native Long getId()/*-{
		return this.id;
	}-*/;

	public final native void setId(Long id)/*-{
		this.id = id;
	}-*/;

	public final native String getTitle()/*-{
		return this.title;
	}-*/;

	public final native void setTitle(String title)/*-{
		this.title = title;
	}-*/;

	public final native JsArrayString getStandards()/*-{
		return this.standards;
	}-*/;

	public final native void setStandards(JsArrayString standards)/*-{
		this.standards = standards;
	}-*/;

	public final native Set<GradeLevel> getGradeLevels()/*-{
		return this.gradeLevels;
	}-*/;

	public final native void setGradeLevels(Set<GradeLevel> gradeLevels)/*-{
		this.gradeLevels = gradeLevels;
	}-*/;

	public final native String getMediaUrl()/*-{
		return this.mediaUrl;
	}-*/;

	public final native void setMediaUrl(String mediaUrl)/*-{
		this.mediaUrl = mediaUrl;
	}-*/;

	public final native String getDescription()/*-{
		return this.description;
	}-*/;

	public final native void setDescription(String description)/*-{
		this.description = description;
	}-*/;

	public final native SubjectType getSubject()/*-{
		return this.subject;
	}-*/;

	public final native void setSubject(SubjectType subject)/*-{
		this.subject = subject;
	}-*/;

//	public final native Set<EducationalLink> getLinks()/*-{
//		return this.links;
//	}-*/; 
//
//	public final native void setLinks(Set<EducationalLink> links)/*-{
//		this.links = links;
//	}-*/; 
}
