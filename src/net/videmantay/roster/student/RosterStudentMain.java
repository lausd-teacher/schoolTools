package net.videmantay.roster.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;

import com.google.gwt.query.client.Function;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSideNav;
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
	MaterialPanel studentCollection;
	
	@UiField
	StudentPage studentPage;
	
	@UiField
	DivElement emptyStudentPage;
	
	@UiField
	DivElement studentDashboard;
	
	private boolean firstNameSort = true;
	
	private  JsArray<RosterStudentJson> students;
	
	
	private Function studentListUpdated = new Function(){
		@Override
		public boolean f(Event e, Object...o){
			RosterStudentJson student = (RosterStudentJson)o[0];
			//create  another JsArray and sort alphabetically by last or first name?
			students.push(student);
			drawStudentList();
			studentPage.setStudent(student);
			return true;
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
		$(body).on("studentsaved", studentListUpdated);
		
	}
	
	public RosterStudentMain setStudent(RosterStudentJson student){
		
		studentPage.setStudent(student);
		studentPage.setVisible(true);
		
		return this;
	}
	
	private void drawStudentList(){
		console.log("RosterStudentMain-drawList: Here is students");
		console.log(this.students);
		if(firstNameSort){students = NameOrder.byFirstName(students);}
		else{students = NameOrder.byLastName(students);}
		
		
		for(int i= 0; i < students.length(); i++){
			MaterialRow row = new MaterialRow();
			row.add(new StudentLink(students.get(i)));
		studentCollection.add(row);
		}
	}
	
	@Override
	public void onLoad(){

		fab.addClickHandler(clickHandler);
		if(students.length() <= 0){
			studentDashboard.getStyle().setDisplay(Display.NONE);
			emptyStudentPage.getStyle().setDisplay(Display.BLOCK);
			}else{
			studentDashboard.getStyle().setDisplay(Display.BLOCK);
			emptyStudentPage.getStyle().setDisplay(Display.NONE);
			drawStudentList();
			studentPage.setStudent(students.get(0));
			}
	}
	
	
	
	}
