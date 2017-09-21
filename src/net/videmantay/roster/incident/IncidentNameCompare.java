package net.videmantay.roster.incident;

import java.util.Comparator;

import net.videmantay.student.json.IncidentJson;

public class IncidentNameCompare implements Comparator<IncidentJson> {

	@Override
	public int compare(IncidentJson o1, IncidentJson o2) {
		return o1.getName().compareToIgnoreCase(o2.getName());
	}

	

}
