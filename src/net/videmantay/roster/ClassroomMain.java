package net.videmantay.roster;


import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.client.ui.MaterialSideNav;
import gwt.material.design.client.ui.MaterialToast;
import net.videmantay.roster.assignment.GradedWorkMain;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.roster.routine.RoutineMain;
import net.videmantay.roster.student.RosterStudentMain;
import java.util.List;

import static com.google.gwt.query.client.GQuery.*;

public class ClassroomMain extends Composite{

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
	MaterialSideNav sideNav;
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
	
	private final RosterJson roster;
	
	//end side nav links/////////
		
	
	
	public ClassroomMain() {
		roster = window.getPropertyJSO("roster").cast();
		this.initWidget(uiBinder.createAndBindUi(this));
		//classroom.setId("classroom");
			$this = this;
			//set side nav links/////////
			dashboardLink.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					History.newItem("classroom/");
					sideNav.hide();
				}
				
			});
			studentLink.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				
				History.newItem("classroom/students");
				studentView();
				sideNav.hide();
				
			}});
			
			assignmentLink.addClickHandler( new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					History.newItem("roster/assignments");
					assignmentView();
					sideNav.hide();
					
				}});
			routineLink.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					History.newItem("classroom/routines");
					routineView();
					sideNav.hide();
					
				}});
		// End set up Side nav Links///////////////////
			
			
		
	}
	

	
	private void dashboardView(){
		console.log("classmain dashboard called");
		mainPanel.clear();
		mainPanel.add(new DashboardPanel(roster));
		sideNav.hide();
	}
	private void studentView(){
		
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				
				
			}

			@Override
			public void onSuccess() {
				RosterStudentMain studentMain = new RosterStudentMain(roster.getRosterStudents());
				mainPanel.clear();mainPanel.add(studentMain); 
				//use the path to get student id and have that student selected
			}});
		
	}
	
	private void assignmentView(){
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
				mainPanel.add(new GradedWorkMain());
			}});
	}
	
	private  void routineView(){
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
	private void assignmentView(List<String> path){
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess() {
				mainPanel.clear();
				mainPanel.add(new GradedWorkMain());
				
			}
			
		});
	}
	
	private void groupView(List<String> path){
		
	}
	/*private void assignmentView(List<String> path){
		switch(path.size()){
		case 1:  ; break;//do assignmentList;
		case 2:  ; break;//show assignment page by id;//TODO:we could do more subdivision on other versions
		}
	}*/
	private void behaviorView(List<String> path){
		
}
	private void jobView(List<String> path){
		
}
	private void goalView(List<String> path){
	
}
	
	@Override
	public void onLoad(){
		//TODO: history control
		dashboardView();
	}

}
