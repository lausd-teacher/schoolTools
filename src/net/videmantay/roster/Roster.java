package net.videmantay.roster;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;

import net.videmantay.roster.json.RosterJson;

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
	
	///all the history and events must be handled here
			
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		//parse token and see if there are subpaths
		ArrayList<String> token = new ArrayList<String>();
		Iterator<String> iter  = Splitter.on("/").split(event.getValue()).iterator();
		while(iter.hasNext()){
			token.add(iter.next());
		}
		if(token.size() <= 1){
		if(!mainState){
			loadMain();
		}
		switch(event.getValue()){
		case "books": return;
		case "settings":return;
		case "calendars":return;
		default: main.rosters(); return;
			}//end switch
		}//end if single token 
		//now handle the times where you must parse path
		
		//according to the size of the array there are only so many possibilities
		//size two mean you are at dashboard
		
		
		//size of three means either a list of assignments, books, students,
		//jobs,groups,badges,showcase,lessons,quizes,vocab,behaviors,seatingChart
		
		
		//size of four means specific items in one of the list above
		
		//size of five means state ie seatinchar , grid , list, gradebook etc
		
	}
	
	private void loadMain(){
		main = new RosterMain();
		RootPanel.get().clear(true);
		RootPanel.get().add(main);
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

}
