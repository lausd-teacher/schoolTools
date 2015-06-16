package net.videmantay.shared;

public class RosterAssignmentAction {
	
	private Action action;
	private Long rosterStudentId;
	
	
	public Long getRosterStudentId() {
		return rosterStudentId;
	}
	public void setRosterStudentId(Long rosterStudentId) {
		this.rosterStudentId = rosterStudentId;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	
	public enum Action{ASSIGN, UNASSIGN}
}
