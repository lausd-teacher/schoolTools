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
	
	private  StudentPage $this;
	
	private  RosterStudentJson student;
	
	public StudentPage() {
		initWidget(uiBinder.createAndBindUi(this));
		$this = this;
	}
	
	public StudentPage(Long id){
		this();
		this.setStudent(id);
	}
	
	public void setStudent(Long id){
		JsArray<RosterStudentJson> stu = ((RosterJson)window.getPropertyJSO("roster")).getRosterStudents();
		for(int i =0; i< stu.length(); i++){
			if(stu.get(i).getId() == id){
				student = stu.get(i);
				break;
			}
		}
	}
	
	@Override
	public void onUnload(){
		$this = null;
	}

}
