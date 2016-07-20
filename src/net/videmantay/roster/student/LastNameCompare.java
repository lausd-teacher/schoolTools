package net.videmantay.roster.student;

import java.util.Comparator;

import net.videmantay.student.json.RosterStudentJson;

public class LastNameCompare implements Comparator<RosterStudentJson> {

	@Override
	public int compare(RosterStudentJson o1, RosterStudentJson o2) {
		
		return o1.getLastName().compareToIgnoreCase(o2.getLastName());
	}

}
