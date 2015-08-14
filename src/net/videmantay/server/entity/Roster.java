package net.videmantay.server.entity;

import java.io.Serializable;
import java.sql.Date;
import net.videmantay.shared.GradeLevel;


public class Roster implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Long idroster;
	
	private String mainTeacher;
	//storing email id for user
	//maybe useful when calling services

	
	private GradeLevel gradeLevel;
	

	private Date startDate;
	

	private Date endDate;
	
	
	private String title;
	
	private String folderId;
		
	private String contacts;
	
	private String description;

	

	///Constructors
	
	public Roster(){
		
	}
	
	public Roster(AppUser acct){
	
		if(acct.getRole().contains("teacher")){
		this.setMainTeacher(acct.getTitle() + " " + acct.getLastName());
		} //end if
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contactGroupId) {
		this.contacts = contactGroupId;
	}

	public Long getId() {
		return this.getIdroster();
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

	public String getGradeLevel(){
		return this.gradeLevel.toString();
	}
	
	public void setGradeLevel(GradeLevel grdLvl){
		this.gradeLevel = grdLvl;
	}
	
	public void setGradeLevel(String grdLvl){
		this.gradeLevel = GradeLevel.valueOf(grdLvl);
	}
	
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getTitle(){
		return title;
	}

	public String getMainTeacher() {
		return mainTeacher;
	}

	public void setMainTeacher(String mainTeacher) {
		this.mainTeacher = mainTeacher;
	}

	public Long getIdroster() {
		return idroster;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	
	
}
