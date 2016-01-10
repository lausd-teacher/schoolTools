package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

public class StudentJob implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8098272788361413932L;
	private Long id;
	private String iconUrl;
	private String title;
	private String jobDescription;
	private String startDate;
	private String endDate;
	private List<String> assignedStudents;
	private String category;
	
	
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getJobDescription() {
		return jobDescription;
	}
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Long getId() {
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	public List<String> getAssignedStudents() {
		return assignedStudents;
	}
	public void setAssignedStudents(List<String> assignedStudents) {
		this.assignedStudents = assignedStudents;
	}

	

}
