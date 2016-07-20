package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Serialize;

import net.videmantay.shared.GoalType;

@Entity
public class StudentGoal implements Serializable {

	@Id
	public Long id;
	
	public String title;
	
	public String description;
	
	public String objective;
	
	public GoalType type;
	
	public String dueDate;
	
	@Index
	public Long studentId;
	
	@Serialize
	public List<Attempt> attempts;
	
	public boolean met;
	
	public double percentAccomplished;
	
	public double value;
	
	public class Attempt{
		public String url;
		public Boolean metGoal;
		public String date;
		public String notes;
	}
	
}
