package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.html.Span;
import net.videmantay.roster.json.Student;

public class RosterStudentPanel extends Composite {

	private static RosterStudentPanelUiBinder uiBinder = GWT.create(RosterStudentPanelUiBinder.class);

	interface RosterStudentPanelUiBinder extends UiBinder<Widget, RosterStudentPanel> {
	}
	
	@UiField
	HTMLPanel rosterStudentPanel;
	
	@UiField
	DivElement studentImg;
	
	@UiField
	Span firstName;
	
	@UiField
	Span lastName;
	
	private Student student;

	public RosterStudentPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	
	public void setData(Student student){
		this.student = student;
		//set the id of the panel to student id 
		//this is so we can query and hide it when necessary
		this.getElement().setId(student.getId() );
		//studentImg.setUrl(student.getThumbnails().get(0).getUrl());
		firstName.setText(student.getName_first());
		lastName.setText(student.getName_last());
		
		studentImg.getStyle().setBackgroundImage("url("+student.getPicture_url()+")");
		
	}
	
	public Student getData(){
		return this.student;
	}

}
