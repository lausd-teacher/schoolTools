package net.videmantay.roster.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;

import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.query.client.plugins.widgets.WidgetsUtils;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModalContent;
import net.videmantay.roster.RosterEvent;
import net.videmantay.roster.RosterUrl;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.student.json.RosterStudentJson;

public class RosterStudentMain extends Composite {

	private static RosterStudentMainUiBinder uiBinder = GWT.create(RosterStudentMainUiBinder.class);

	interface RosterStudentMainUiBinder extends UiBinder<Widget, RosterStudentMain> {
	}
	
	@UiField
	MaterialCollection studentCollection;
	@UiField
	MaterialColumn studentContent;
	@UiField
	MaterialButton fab;
	
	CreateStudentForm stuForm = new CreateStudentForm();
	
	private EmptyStudentList empty = new EmptyStudentList();
	
	private Function studentListUpdated = new Function(){
		@Override
		public void f(){
			drawList();
		}
	};
	ClickHandler clickHandler = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			stuForm.show();
		}};
		
	
	
	public RosterStudentMain() {
		initWidget(uiBinder.createAndBindUi(this));
		
		drawList();
		fab.addClickHandler(clickHandler);
		$(body).on(RosterEvent.STUDENT_LIST_UPDATED, studentListUpdated);
		
	}
	
	public void showEmpty(){
		studentContent.clear();
		studentContent.add(empty);
	}
	
	private void drawList(){
		//get a hold of the roster student
				JsArray<RosterStudentJson> students = $("#classroom").data("classroom", RosterJson.class).getRosterStudents();
				//if its empty show empty content
				if(students.length() < 1){
					showEmpty();
				}
				for(int i = 0; i < students.length(); i++){
					RosterStudentItem rsi = new RosterStudentItem(students.get(i));
					studentCollection.add(rsi);
					
				}
	}
	
	

}
