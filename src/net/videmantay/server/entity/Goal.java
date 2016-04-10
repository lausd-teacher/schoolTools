package net.videmantay.server.entity;

import java.io.Serializable;

import net.videmantay.shared.GoalType;

public class Goal implements Serializable{

	public  String title;
	public  String description;
	public  String url;
	public GoalType type;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public GoalType getType() {
		return type;
	}
	public void setType(GoalType type) {
		this.type = type;
	}
}
