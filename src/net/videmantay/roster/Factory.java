package net.videmantay.roster;

import net.videmantay.roster.json.RosterJson;

public class Factory {
	private Factory(){}
	
	public static RosterJson roster;
	
	public static Factory get(){
		return new Factory();
	}
}
