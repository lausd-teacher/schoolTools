package net.videmantay.server.entity;

import java.util.Set;

import com.google.api.services.tasks.model.Tasks;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Parent;

import net.videmantay.shared.GroupingType;



public class StudentGroup {
	
	
	private Long id;
	
	private String title;
	
	private String objective;
	
	private GroupingType type;
	
	private String backGroundColor;
	
	private String textColor;
	
	private String borderColor;
	
	private Set<String> students;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public GroupingType getType() {
		return type;
	}

	public void setType(GroupingType type) {
		this.type = type;
	}

	public String getBackGroundColor() {
		return backGroundColor;
	}

	public void setBackGroundColor(String backGroundColor) {
		this.backGroundColor = backGroundColor;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	public Set<String> getStudents() {
		return students;
	}

	public void setStudents(Set<String> students) {
		this.students = students;
	}
	
}
