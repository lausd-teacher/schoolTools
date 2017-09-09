package net.videmantay.roster.student;

import java.util.Arrays;

import com.google.gwt.core.client.JsArray;

import net.videmantay.student.json.RosterStudentJson;

public class NameOrder {

	public static FistNameComparator firstName = new FistNameComparator();
	public static LastNameComparator lastName = new LastNameComparator();
	
	static JsArray<RosterStudentJson> byFirstName(JsArray<RosterStudentJson> students){
		RosterStudentJson[] temp = new RosterStudentJson[students.length()];
		for(int i =0; i<students.length(); i++){
			temp[i] = students.get(i);
		}
				Arrays.sort(temp, firstName);
		JsArray<RosterStudentJson> response = JsArray.createArray().cast();
		for(int i =0; i < temp.length; i++){
			response.push(temp[i]);
		}
		temp = null;
		students = null;
		return response;
	}
	
	static JsArray<RosterStudentJson> byLastName(JsArray<RosterStudentJson> students){
		RosterStudentJson[] temp = new RosterStudentJson[students.length()];
		for(int i =0; i<students.length(); i++){
			temp[i] = students.get(i);
		}
				Arrays.sort(temp, lastName);
		JsArray<RosterStudentJson> response = JsArray.createArray().cast();
		for(int i =0; i < temp.length; i++){
			response.push(temp[i]);
		}
		temp = null;
		students = null;
		return response;
	}
}
