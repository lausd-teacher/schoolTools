package net.videmantay.roster;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;

import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.query.client.Function;

public class Roster implements EntryPoint, ValueChangeHandler<String> {
	
	private final RosterMain main = new RosterMain();
	private final ClassroomMain classroom = new ClassroomMain();
	private boolean isRoster = true;
	
	public Roster(){
		
	}
	@Override
	public void onModuleLoad() {
		
		$("div#loader").remove();
		RootPanel.get().add(main);
		History.newItem("roster", false);	
		History.addValueChangeHandler(this);
	}
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String view = event.getValue();
		if(view.startsWith("classroom")){
			if(!isRoster){
				changeClassroomView(view);
			}else{
				RootPanel.get().clear();
				isRoster = false;
				RootPanel.get().add(classroom);
				History.replaceItem("classroom", false);
			}
		}else{
			if(isRoster){
				return;
			}else{
				isRoster = true;
				RootPanel.get().clear();
				RootPanel.get().add(main);
				History.replaceItem("roster", false);
			}
			
		}
		
	}
	
	private void changeClassroomView(String view){
		switch(view){
		case "classroom/students": classroom.studentView();break;
		case "classroom/assignments": classroom.assignmentView();break;
		case "classroom/routines":classroom.routineView();break;
		case "classroom/incidents":classroom.incidentView();break;
		case "classroom/goals":break;
		case "classroom/jobs":break;
		default: classroom.dashboardView(); History.replaceItem("classroom", false);break;
		}
	}
	

	

}
