package net.videmantay.roster.student;

import java.util.Comparator;

import net.videmantay.student.json.RosterStudentJson;

public class FistNameCompare implements Comparator<RosterStudentJson> {

	@Override
	public int compare(RosterStudentJson o1, RosterStudentJson o2) {
		// TODO Auto-generated method stub
		return o1.getFirstName().compareToIgnoreCase(o2.getFirstName());
	}

}
