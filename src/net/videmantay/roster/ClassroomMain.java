package net.videmantay.roster;


import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.client.ui.MaterialSideNavDrawer;
import gwt.material.design.client.ui.MaterialToast;
import net.videmantay.roster.incident.IncidentMain;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.roster.routine.RoutineMain;
import net.videmantay.roster.student.RosterStudentMain;
import net.videmantay.roster.work.WorkMain;
import net.videmantay.shared.json.RosterInfoJson;

import java.util.List;

import static com.google.gwt.query.client.GQuery.*;

public class ClassroomMain extends  Composite{

	private static ClassroomMainUiBinder uiBinder = GWT.create(ClassroomMainUiBinder.class);

	interface ClassroomMainUiBinder extends UiBinder<Widget, ClassroomMain> {
	}
	public interface HasUpdateClassTime{public void updateClassTime();}

	final ClassroomMain $this;
	
	@UiField
	MaterialContainer mainPanel;
	
	@UiField
	MaterialNavBrand rosterTitle;
	
	
	
	@UiField
	MaterialSideNavDrawer sideNav;
	//side nav links here ///////
	@UiField
	MaterialLink studentLink;
	
	@UiField
	MaterialLink dashboardLink;
	
	@UiField
	MaterialLink assignmentLink;
	
	@UiField
	MaterialLink incidentLink;
	
	@UiField
	MaterialLink goalLink;
	
	@UiField
	MaterialLink routineLink;
	
	@UiField
	MaterialLink lessonPlanLink;
	
	@UiField
	MaterialLink jobLink;
	
	private  RosterInfoJson rosterInfo;
	private  RosterJson roster;
	
	//end side nav links/////////
		
	
	
	public ClassroomMain() {
		this.initWidget(uiBinder.createAndBindUi(this));
		//classroom.setId("classroom");
			$this = this;
			//set side nav links/////////
			dashboardLink.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					History.newItem("classroom");
					sideNav.hide();
				}
				
			});
			studentLink.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				
				History.newItem("classroom/students");
				sideNav.hide();
				
			}});
			
			assignmentLink.addClickHandler( new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					History.newItem("classroom/assignments");
					sideNav.hide();
					
				}});
			routineLink.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					History.newItem("classroom/routines");
					routineView();
					sideNav.hide();
					
				}});
			
			incidentLink.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					History.newItem("classroom/incidents");
					incidentView();
					sideNav.hide();
					
				}});
		// End set up Side nav Links///////////////////
			
			
		
	}
	

	
	public  void dashboardView(){
		console.log("classmain dashboard called");
		mainPanel.clear();
		mainPanel.add(new DashboardPanel(roster));
		sideNav.hide();
	}
	public void studentView(){
		
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				
				
			}

			@Override
			public void onSuccess() {
				RosterStudentMain studentMain = new RosterStudentMain(roster.getStudents());
				mainPanel.clear();mainPanel.add(studentMain); 
				//use the path to get student id and have that student selected
			}});
		
	}
	
	public void assignmentView(){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				// TODO Auto-generated method stub
				MaterialToast.fireToast("unable to reach assignements");
				History.back();
			}

			@Override
			public void onSuccess() {
				mainPanel.clear();
				mainPanel.add(new WorkMain());
			}});
	}
	
	public  void routineView(){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess() {
				mainPanel.clear();
				mainPanel.add(new RoutineMain());
				
			}});
	}
	
	public void incidentView(){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess() {
					mainPanel.clear();
					mainPanel.add(new IncidentMain());
				
			}});
}
	private void jobView(List<String> path){
		
}
	private void goalView(List<String> path){
	
}
	
	@Override
	public void onLoad(){
		rosterInfo =(RosterInfoJson) window.getPropertyJSO("currentRoster").cast();
		console.log("RosterMain:  onLoad ... here is the current Roster Info");
		console.log(rosterInfo);
		
		//Ajax get roster then dashboard
		Ajax.ajax(Ajax.createSettings().setType("GET")
				.setContentType("application/json")
				.setUrl("/roster/"+ rosterInfo.getId())).done(new Function(){
					@Override
					public void f(){
						String json =  (String)this.arguments(0);
						console.log("ClassroomMain: onload ajax response is " +json);
						window.setPropertyJSO("currentClassroom",(RosterJson)JsonUtils.safeEval(json).cast());
						roster = window.getPropertyJSO("currentClassroom").cast();
						dashboardView();
					}
				});
	}

}
