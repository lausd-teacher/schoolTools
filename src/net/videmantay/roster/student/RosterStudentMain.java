package net.videmantay.roster.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;

import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLoader;
import net.videmantay.roster.RosterEvent;
import net.videmantay.roster.RosterUrl;
import net.videmantay.roster.json.RosterJson;
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
	RosterStudentCollection  studentCollection;
	
	@UiField
	StudentPage studentPage;
	
	private final RosterJson roster = window.getPropertyJSO("roster").cast();
	
	private final JsArray<RosterStudentJson> students = roster.getRosterStudents();
	
	private boolean studentState = false;
	

	private Function studentListUpdated = new Function(){
		@Override
		public void f(){
			studentCollection.drawList();
		}
	};
	
	ClickHandler clickHandler = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			stuForm.show();
		}};
		
	
	
	public RosterStudentMain() {
		initWidget(uiBinder.createAndBindUi(this));
		fab.addClickHandler(clickHandler);
		$(body).on(RosterEvent.STUDENT_LIST_UPDATED, studentListUpdated);
		studentPage.setVisible(false);	
		studentCollection.drawList();
	}
	
	public RosterStudentMain setStudent(Long id){
		
		studentPage.setStudent(id);
		studentPage.setVisible(true);
		studentCollection.setVisible(false);
		
		return this;
	}
	
	
	}
