package net.videmantay.server.entity;

import java.io.Serializable;

import net.videmantay.shared.BadgeType;

public class Badge implements Serializable{

	private String title;
	private String description;
	private String url;
	private BadgeType type;
	
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
	public BadgeType getType() {
		return type;
	}
	public void setType(BadgeType type) {
		this.type = type;
	}
}
