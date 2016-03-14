package net.videmantay.roster;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;

import net.videmantay.roster.json.RosterJson;
import net.videmantay.roster.seatingchart.SeatingChartPanel;

import static com.google.gwt.query.client.GQuery.*;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.common.base.Splitter;
import com.google.gwt.query.client.*;
import com.google.gwt.query.client.plugins.ajax.Ajax;

public class Roster implements EntryPoint , ValueChangeHandler<String> {
	private RosterMain main;
	private ClassroomMain classroom;
	
	private boolean mainState = true;

	
	@Override
	public void onModuleLoad() {
	
		
		RootPanel.get().add(new ClassroomMain());
	///all the history and events must be handled here
		History.addValueChangeHandler(this);
		String token = History.getToken();
		if(token == null || token.equals("")){
			History.newItem("rosters", true);
		}
			History.fireCurrentHistoryState();
			
			//handle all events for Roster here including rosterdisplay etc
			//actually the displays should handle the lesser events
		
	}
	
	@Override
	public void onValueChange(ValueChangeEvent<String> e){
		
	}

	/*@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		//parse token and see if there are subpaths
		ArrayList<String> token = new ArrayList<String>();
		Iterator<String> iter  = Splitter.on("/").split(event.getValue()).iterator();
		while(iter.hasNext()){
			token.add(iter.next());
		}
		if(token.size() <= 1){console.log("token size = " + token.size());
		loadMain();
		switch(event.getValue()){
		case "books": return;
		case "settings":return;
		case "calendars":return;
	//	default: main.rosters(); return;
			}//end switch
		}//end if single token 
		//now handle the times where you must parse path
		
		//according to the size of the array there are only so many possibilities
		//size two means you are at dashboard
		if(token.size() >= 2 && token.get(0).equalsIgnoreCase("roster")){
		//set the mainview to false
			if(mainState){
				loadDisplay(token.get(1));
			}else{
				classroom.setClassroom(token.get(1));
			}//load the class regardless
			
			//size of three means either a list of assignments, books, students,
			//jobs,groups,goals,showcase,lessons,quizes,vocab,behaviors,seatingChart
			
			if(token.size() >= 3){
				classroom.showView(token.get(2));
			}
			
			//size of four means specific items in one of the list above
			if(token.size() >= 4){
				classroom.showViewItem(token.get(3));
			}
			//size of five means state ie seatinchar , grid , list, gradebook etc
			//may just handle with tabs
		}//end roster path
				
	}//change handler
	
	private void loadMain(){
		console.log("loaded main");
		main = new RosterMain();
		RootPanel.get().clear();
		RootPanel.get().add(main);
		console.log("this is just before main rosters is called!");
		main.rosters();
		classroom = null;
		mainState = true;
	}
	
	private void loadDisplay(String rosterId){
		classroom = new ClassroomMain();
		classroom.setClassroom(rosterId);
		RootPanel.get().clear(true);
		RootPanel.get().add(classroom);
		main = null;
		mainState = false;
	}
*/
}
