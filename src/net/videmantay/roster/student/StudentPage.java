package net.videmantay.roster.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialImage;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.student.json.RosterStudentJson;

import static com.google.gwt.query.client.GQuery.*;
public class StudentPage extends Composite {

	private static StudentPageUiBinder uiBinder = GWT.create(StudentPageUiBinder.class);

	interface StudentPageUiBinder extends UiBinder<Widget, StudentPage> {
	}

	@UiField
	SpanElement firstName;
	
	@UiField
	SpanElement lastName;
	
	@UiField
	SpanElement extName;
	
	@UiField
	SpanElement DOB;
	
	@UiField
	MaterialImage stuImage;
	
	
	private  RosterStudentJson student;
	
	public StudentPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public StudentPage(Long id){
		this();
	}
	
	public void setStudent(Long id){
		
	}
	
	@Override
	public void onUnload(){

	}

}
