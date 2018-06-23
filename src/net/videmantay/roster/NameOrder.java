package net.videmantay.roster;



import java.util.Comparator;

import net.videmantay.roster.json.Student;

public class NameOrder implements Comparator<Student> {

	private boolean byFirstName = true;
	
	public NameOrder() {}
	public NameOrder(boolean byFirstName) {
		this.byFirstName = byFirstName;
	}
	@Override
	public int compare(Student arg0, Student arg1) {
		if(byFirstName){
		return arg0.getName_first().compareToIgnoreCase(arg1.getName_first());
	}else {
		return arg0.getName_last().compareToIgnoreCase(arg1.getName_last());
	}
	}
}
