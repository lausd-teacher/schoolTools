package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;


@Entity
public class StudentJob implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8098272788361413932L;
	@Id
	private Long id;
	private String iconUrl;
	private String title;
	private String jobDescription;
	private Date startDate;
	private Date endDate;
	private List<Long> assignedStudent;
	private String category;
	@Parent
	private transient Key<Roster> rosterKey;
	
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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public List<Long> getAssignedStudent() {
		return assignedStudent;
	}
	public void setAssignedStudent(List<Long> assignedStudent) {
		this.assignedStudent = assignedStudent;
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
	public Key<Roster> getRosterKey() {
		return rosterKey;
	}
	public void setRosterKey(Key<Roster> rosterKey) {
		this.rosterKey = rosterKey;
	}

	

}
