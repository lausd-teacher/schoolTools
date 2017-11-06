package net.videmantay.student.json;

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.DateTimeFormat;


public class StudentIncidentJson extends JavaScriptObject {

	protected StudentIncidentJson(){}
	
	

	public final native Long getId() /*-{
		return this.id;
	}-*/;
	
	public final native StudentIncidentJson setId(Long id)/*-{
				this.id = id;
				return this;
	}-*/;



	public final native Long getIncidentId() /*-{
		return this.incidentId;
	}-*/;
	
	public final native StudentIncidentJson setIncidentId(Long incidentId) /*-{
		this.incidentId = incidentId;
		return this;
	}-*/;
	
	public final native String getStudentAcct() /*-{
		return this.studentAcct;
	}-*/;



	public final native StudentIncidentJson setStudentAcct(String studentAcct) /*-{
		this.studentAcct = studentAcct;
		return this;
	}-*/;
	
	public final native String getDate() /*-{
	return this.date;
}-*/;



public final native StudentIncidentJson setDate(String date) /*-{
	this.date = date;
	return this;
}-*/;



	public final native String getImageUrl() /*-{
		return this.imageUrl;
	}-*/;



	public final native StudentIncidentJson setImageUrl(String imageUrl) /*-{
		this.imageUrl = imageUrl;
		return this;
	}-*/;
	
	public final native String getName()/*-{
		return this.name;
	}-*/;
	
	public final native StudentIncidentJson setName(String name)/*-{
			this.name = name;
			return this;
	}-*/;
	
	public final native int getPoints()/*-{
				return this.points;
	}-*/;
	
	public final native StudentIncidentJson setPoints(int points)/*-{
			this.points = points;
			return this;
	}-*/;
	
	public static StudentIncidentJson create(String studentId, IncidentJson incident){
		StudentIncidentJson stuInc = StudentIncidentJson.createObject().cast();
		stuInc.setDate(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.ISO_8601).format(new Date()))
		.setImageUrl(incident.getImageUrl())
		.setName(incident.getName())
		.setPoints(incident.getPoints())
		.setStudentAcct(studentId);
		return stuInc;
		
	}
}
