package net.videmantay.server.entity;

public class GroupNotes {

	private Long id;
	private Long groupId;
	private String dateTime;
	private String description;
	private String activity;
	private Boolean metObjetive;
	private Double duration;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getMetObjetive() {
		return metObjetive;
	}
	public void setMetObjetive(Boolean metObjetive) {
		this.metObjetive = metObjetive;
	}
	public Double getDuration() {
		return duration;
	}
	public void setDuration(Double duration) {
		this.duration = duration;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	
	
}
