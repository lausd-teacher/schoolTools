package net.videmantay.roster;

import net.videmantay.roster.json.RosterJson;
import static com.google.gwt.query.client.GQuery.window;

public class Factory {
	private Factory(){}
	
	public static RosterJson roster;
	
	public static Factory get(){
		roster = window.getPropertyJSO("roster").cast();
		return new Factory();
		
	}
}
