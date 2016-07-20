package net.videmantay.roster;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.client.ui.MaterialSideNav;
import net.videmantay.roster.assignment.GradedWorkMain;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.roster.seatingchart.json.ClassTimeJson;
import net.videmantay.roster.student.RosterStudentMain;
import java.util.ArrayList;
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
	MaterialLink classTimeLink;
	
	@UiField
	MaterialLink lessonPlanLink;
	
	@UiField
	MaterialLink jobLink;
	
	//end side nav links/////////
		
	private  RosterJson classRoster;
	
	
	
	public ClassroomMain() {
		this.initWidget(uiBinder.createAndBindUi(this));
		//classroom.setId("classroom");
			$this = this;
			//set side nav links/////////
			dashboardLink.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					History.newItem("roster/" + classRoster.getId());
					sideNav.hide();
				}
				
			});
			studentLink.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				
				History.newItem("roster/" + classRoster.getId() +"/students");
				sideNav.hide();
				
			}});
			
			assignmentLink.addClickHandler( new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					History.newItem("roster/" + classRoster.getId() +"/assignments");
					sideNav.hide();
					
				}});
		// End set up Side nav Links///////////////////
			
			$(body).on("setclasstime", new Function(){
						@Override
						public  boolean f(Event e, Object... o){
							Properties p= (Properties) o[0];
							int index = Ints.tryParse(p.getStr("data-index"));
							RosterJson  roster = window.getPropertyJSO("roster").cast();
							ClassTimeJson 	classTime = roster.getClassTimes().get(index);
							$this.setClasstime(classTime);
							
							
							return true;
						}
			});
		
	}
	

	
	public void setClassroom(final ArrayList<String> token){
		console.log("set classroom called");
		//need this to hold path
		final ArrayList<String> path = new ArrayList<String>();
		//parse the path to get the roster id
		Long id = Longs.tryParse(token.get(1));
		console.log("The roster's id is " + id);
		if(id == null){
			History.back();
		}
		
		if($this.getRosterId() == null ||id != $this.getRosterId()){
			console.log("Roster ajax called made from classroom main");
			//Asyncall to get my roster with id of id
			//setView must be called after roster is set
			Ajax.post(RosterUrl.GET_ROSTER, $$("roster:" + id))
			.done( new Function(){
					@Override
					public void f(){
						classRoster = JsonUtils.safeEval((String) this.arguments(0)).cast();
						rosterTitle.setText(classRoster.getTitle());
						window.setPropertyJSO("roster", classRoster);
						setClasstime(classRoster.getClassTimes().get(0));
						
							if(token.size()>= 3){
							 path.addAll(token.subList(2, token.size()));

							 setView(path);
							}else{dashboardView();}
						
					}
			});}else{
					//no need to wait for id to load view just doit
					//need to check that classtime is set if not get default
				if(window.getPropertyJSO("classtime") == null){
					console.log("classTime was null is called");
					window.setPropertyJSO("classtime", classRoster.getClassTimes().get(0));
				}
						if(token.size()>= 3){
					 path.addAll(token.subList(2, token.size()));
					 //classtime may have been set
					 
					 setView(path);
					}else{dashboardView();}
						}// end else roster will be here
	}
	
	private void setView(List<String> path){
		
		switch(path.get(0)){
		case "students":studentView(path); break;
		case "groups": groupView(path);break;
		case "assignments":assignmentView(path); break;
		case "behaviors":behaviorView(path); break;
		case "jobs": jobView(path);break;
		case "goals":goalView(path); break;
		default: dashboardView();
		}
	}
	
	private void dashboardView(){
		console.log("classmain dashboard called");
		if(mainPanel.getWidget(0) instanceof RosterDashboardPanel){
			console.log("Main panel had instance of roster dashboard already");
			return;
		}
		mainPanel.clear();
		mainPanel.add(new RosterDashboardPanel());
		sideNav.hide();
	}
	private void studentView(final List<String>path){
		
		GWT.runAsync(new RunAsyncCallback(){

			@Override
			public void onFailure(Throwable reason) {
				
				
			}

			@Override
			public void onSuccess() {
				RosterStudentMain studentMain = new RosterStudentMain();
				switch(path.size()){
				case 1: mainPanel.clear();mainPanel.add(studentMain); break;
				
				//rosterStudent main must handle the view of students
				case 2: mainPanel.clear();
				mainPanel.add(studentMain.setStudent(Longs.tryParse(path.get(1))));break;
				}
				
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

	
	private void setClasstime(ClassTimeJson classTime){
		window.setPropertyJSO("classtime", classTime);
		
	}
	
	public Long getRosterId(){
		if(classRoster == null){
			return null;
		}
			return classRoster.getId();
			
		
	}
}
