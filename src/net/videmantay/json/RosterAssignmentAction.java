package net.videmantay.json;

import net.videmantay.shared.RosterAssignmentAction.Action;

import com.google.gwt.query.client.builders.JsonBuilder;

public interface RosterAssignmentAction extends JsonBuilder {
	
	public void setAction(Action action);
	
	public void setRosterStudentId(Long id);
	
	public Long getRosterStudentId();
	
	public Action getAction();

}
