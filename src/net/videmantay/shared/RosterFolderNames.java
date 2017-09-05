package net.videmantay.shared;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;

public class RosterFolderNames {

	public static final String STUDENTS = "Students";
	public static final String SHARED = "Shared";
	public static final String SHARED_ASSIGNMENTS= "Assignements";
	public static final String SHARED_SHOWCASE = "Showcase";
	public static final String FORMS = "Forms";
	public static final String PUBLIC = "Public";
	
	public static List<String> getNames(){
		String[] names = {STUDENTS, SHARED, SHARED_ASSIGNMENTS,SHARED_SHOWCASE,FORMS,PUBLIC};
		
		return Arrays.asList(names);
	}
	
	private RosterFolderNames(){}
	
}
