package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.html.Span;
import net.videmantay.student.json.RosterStudentJson;

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

	public RosterStudentPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}



	public RosterStudentPanel(RosterStudentJson student) {
		this();
		setData(student);
	}
	
	public void setData(RosterStudentJson student){
		//set the id of the panel to student id 
		//this is so we can query and hide it when necessary
		this.getElement().setId(student.getId() +"");
		//studentImg.setUrl(student.getThumbnails().get(0).getUrl());
		firstName.setText(student.getFirstName());
		lastName.setText(student.getLastName());
		String url= student.getThumbnails() == null||student.getThumbnails().get(1).getUrl() == null ||student.getThumbnails().get(1).getUrl() == ""? "../img/user.svg":student.getThumbnails().get(1).getUrl();
		studentImg.getStyle().setBackgroundImage("url('" + url +"')");
	}

}
