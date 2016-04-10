package net.videmantay.server.entity;

import java.io.Serializable;

import net.videmantay.shared.GoalType;

public class StudentGoal implements Serializable {

	public String title;
	
	public String description;
	
	public String objective;
	
	public GoalType type;
	
	public String dueDate;
	
	public Long studentId;
	
	public int attempts;
	
	public boolean met;
	
	public double percentAccomplished;
	
}
