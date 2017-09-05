package net.videmantay.roster.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;

import com.google.gwt.query.client.Function;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollection;
import net.videmantay.student.json.RosterStudentJson;

public class RosterStudentMain extends Composite{

	private static RosterStudentMainUiBinder uiBinder = GWT.create(RosterStudentMainUiBinder.class);

	interface RosterStudentMainUiBinder extends UiBinder<Widget, RosterStudentMain> {
	}
	
	
	@UiField
	MaterialButton fab;
	
	@UiField
	CreateStudentForm stuForm;
	
	@UiField
	DivElement rosterStudentMain;
	
	@UiField
	MaterialCollection  studentCollection;
	
	@UiField
	StudentPage studentPage;
	
	@UiField
	DivElement emptyStudentPage;
	
	@UiField
	DivElement studentDashboard;
	
	private final JsArray<RosterStudentJson> students;
	
	
	private Function studentListUpdated = new Function(){
		@Override
		public void f(){
			drawStudentList();
		}
	};
	
	ClickHandler clickHandler = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			stuForm.show();
		}};
		
	
	
	public RosterStudentMain(JsArray<RosterStudentJson> stus) {
		this.students = stus;
		initWidget(uiBinder.createAndBindUi(this));
		fab.addClickHandler(clickHandler);
		$(body).on("studentsaved", studentListUpdated);
		if(students.length() <= 0){
		studentDashboard.getStyle().setDisplay(Display.NONE);
		emptyStudentPage.getStyle().setDisplay(Display.BLOCK);
		}else{
		studentDashboard.getStyle().setDisplay(Display.BLOCK);
		drawStudentList();
		}
	}
	
	public RosterStudentMain setStudent(Long id){
		
		studentPage.setStudent(id);
		studentPage.setVisible(true);
		studentCollection.setVisible(false);
		
		return this;
	}
	public void drawStudentList(){
		
	}
	
	
	}
