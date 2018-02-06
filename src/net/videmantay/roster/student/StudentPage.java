package net.videmantay.roster.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.html.Span;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.student.json.RosterStudentJson;

import static com.google.gwt.query.client.GQuery.*;
public class StudentPage extends Composite {

	private static StudentPageUiBinder uiBinder = GWT.create(StudentPageUiBinder.class);

	interface StudentPageUiBinder extends UiBinder<Widget, StudentPage> {
	}

	@UiField
	MaterialLabel name;
		
	@UiField
	MaterialLabel DOB;
	
	@UiField
	Span summary;
	
	@UiField
	MaterialImage stuImage;
	
	@UiField
	HTMLPanel emptyAssignment;
	
	@UiField
	HTMLPanel emptyAttendance;
	
	@UiField
	HTMLPanel emptyIncident;
	
	@UiField
	HTMLPanel emptyJob;
	
	@UiField
	HTMLPanel emptyGoal;
	
	@UiField
	HTMLPanel emptyGroup;
	
	@UiField
	MaterialCollection assignmentCollection;
	
	@UiField
	MaterialCollection attendanceCollection;
	
	@UiField
	MaterialCollection incidentCollection;
	
	@UiField
	MaterialCollection jobCollection;
	
	@UiField
	MaterialCollection goalCollection;
	
	@UiField
	MaterialCollection groupCollection;
	
	
	
	private  RosterStudentJson student;
	
	public StudentPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	
	public void setStudent(RosterStudentJson stu){
		this.student = stu;
		name.setText(student.getFirstName()+" "+ student.getLastName());
		
		if(student.getUseThumbs()) {
		String url = student.getThumbnails() == null || student.getThumbnails().length() <= 0?"/../img/user.svg":student.getThumbnails().get(3).getUrl();
		stuImage.setUrl(url);
		}else {
			stuImage.setUrl(student.getPicUrl());
		}
		summary.setText(student.getCurrentSummary()!= null?student.getCurrentSummary():"");
		
		
		
	}
	
	@Override
	public void onUnload(){

	}

}
