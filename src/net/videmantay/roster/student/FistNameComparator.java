package net.videmantay.roster.student;

import java.util.Comparator;

import net.videmantay.student.json.RosterStudentJson;

public class FistNameComparator implements Comparator<RosterStudentJson> {

	@Override
	public int compare(RosterStudentJson ros1, RosterStudentJson ros2) {
		return ros1.getFirstName().compareToIgnoreCase(ros2.getFirstName());
	
	}

}
