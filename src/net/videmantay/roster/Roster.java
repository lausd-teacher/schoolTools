package net.videmantay.roster;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;

import net.videmantay.student.json.RosterDetailJson;

import static com.google.gwt.query.client.GQuery.*;

import java.util.ArrayList;
import java.util.Iterator;
import com.google.common.base.Splitter;
import com.google.common.primitives.Longs;

public class Roster implements EntryPoint , ValueChangeHandler<String> {
	private RosterMain main;
	private ClassroomMain classroom;
	private final JsArray<RosterDetailJson> rosterList;
	private boolean mainState = true;

	
	Roster(){
		rosterList = window.getPropertyJSO("rosterList").cast();
	}
	@Override
	public void onModuleLoad() {
		//populate rosterList all app essential data
		//actually the list of ready rosters are hard coded by the server.
		
		
		History.addValueChangeHandler(this);
		
		String token = History.getToken();	
		if(token == null || token.isEmpty() || token.equals("rosters")){
			History.newItem("rosters");
		}
	History.fireCurrentHistoryState();
		
	}
	

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		//for some reason rosterList becomes null??
		
		//parse token and see if there are subpaths
		ArrayList<String> token = new ArrayList<String>();
		Iterator<String> iter  = Splitter.on("/").split(event.getValue()).iterator();
		while(iter.hasNext()){
			token.add(iter.next());
		}
		if(token.size() == 1){console.log("token size = " + token.size());
		switch(event.getValue()){
		case "books": return;
		case "settings":return;
		case "calendars":return;
		case "lessons":return;
		default: loadMain();
			}//end switch
		}//end if single token 
				if(token.size()>= 2 && token.get(0).equalsIgnoreCase("roster")){
					console.log("If token size 2 or greated called");
					//first check id is kosher
					//and then load display with id
					for(int i =0; i < rosterList.length(); i++){
						console.log("looping to compare ids");
						if(Longs.tryParse(token.get(1))==rosterList.get(i).getId()){
							console.log("If ids matched is called");
							loadDisplay(token);
							break;
						}
					}
				}
	}//change handler
	
	private void loadMain(){
		console.log("loaded main");
		//ensure History token is correct
		History.newItem("rosters");
		main = new RosterMain();
		RootPanel.get().clear();
		RootPanel.get().add(main);
		console.log("this is just before main rosters is called!");
		main.rosters();
		classroom = null;
		mainState = true;
	}
	
	private void loadDisplay(ArrayList<String> token){
		
		console.log("load display called");
		//convert this to a switch when more displays or added
		//ie lesson creator display
		
		
		if(mainState){
		classroom = new ClassroomMain();
		}
			classroom.setClassroom(token);
		

		RootPanel.get().clear();
		RootPanel.get().add(classroom);
		main = null;
		mainState = false;
	}

}
