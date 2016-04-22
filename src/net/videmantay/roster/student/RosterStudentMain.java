package net.videmantay.roster.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;

import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.IsProperties;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.query.client.plugins.widgets.WidgetsUtils;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLoader;
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
	
	@UiField
	CreateStudentForm stuForm;
	@UiField
	HTMLPanel emptyStudentList;
	private final RosterJson roster = window.getPropertyJSO("roster").cast();
	
	private final JsArray<RosterStudentJson> students = roster.getRosterStudents();
	
	private Function studentListUpdated = new Function(){
		@Override
		public void f(){
			console.log((String)this.getArgument(0));
			RosterStudentJson stu = JsonUtils.safeEval((String)this.getArgument(0)).cast();
			students.push(stu);
			drawList();
			stuForm.form.reset(); 
			stuForm.hide();
			MaterialLoader.showLoading(false);
		}
	};
	
	private Function createStudent = new Function(){
		@Override 
		public boolean  f(Event e, Object...o){
			console.log("create student called + rosterStudent is :");
			RosterStudentJson student = (RosterStudentJson) o[0];
			console.log(o[0]);
			Ajax.post(RosterUrl.CREATE_STUDENT, $$("student:" + JsonUtils.stringify(student)))
			.done(studentListUpdated);
			
			return true;
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
		$(window).on("studentcreate", createStudent);
		emptyStudentList.setVisible(false);
		
	}
	
	public void showEmpty(){
		$(emptyStudentList).show();
		$(studentCollection).hide();
	}
	
	private void drawList(){
		
				//if its empty show empty content
				if(students == null ||students.length() < 1){
					showEmpty();
					return;
				}
				for(int i = 0; i < students.length(); i++){
					RosterStudentItem rsi = new RosterStudentItem(students.get(i));
					studentCollection.add(rsi);
					
				}
				$(studentCollection).show();
	}
	}
