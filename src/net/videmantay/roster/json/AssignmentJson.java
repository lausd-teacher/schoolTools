package net.videmantay.roster.json;

import java.util.Set;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import net.videmantay.shared.GradeLevel;
import net.videmantay.shared.SubjectType;
import net.videmantay.student.json.RubricJson;

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

	public final native String getDescription()/*-{
		return this.description;
	}-*/;

	public final native void setDescription(String description)/*-{
		this.description = description;
	}-*/;

	public final native String getSubject()/*-{
		return this.subject;
	}-*/;

	public final native void setSubject(String string)/*-{
		this.subject = string;
	}-*/;

	public final native RubricJson getRubric()/*-{
		return this.rubric;
	}-*/;
	
	public final native void SetRubric(RubricJson rubric)/*-{
		this.rubric = rubric;
	}-*/;
//	public final native Set<EducationalLink> getLinks()/*-{
//		return this.links;
//	}-*/; 
//
//	public final native void setLinks(Set<EducationalLink> links)/*-{
//		this.links = links;
//	}-*/; 
}
