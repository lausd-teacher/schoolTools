package net.videmantay.server.entity;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Serialize;


@Entity
public class RosterStudent implements Comparator<RosterStudent> {
	
	@Id
	private Long id;
	private Date createOn;
	public String key;
	
	///Key to roster detail not parent roster
	//why not set actual key???
	private Long roster;
	private String firstName;
	private String lastName;
	private String extName;
	private String picUrl;
	private Date DOB;
	private String contactsId;
	
	@Serialize
	private transient Map<String, String> taskList = new HashMap<String, String>();
	
	@Index
	private String studentGoogleId;
	
	private Integer positviePoints;
	private Integer NegativePoints;
	private Set<String> badges;
	private List<StudentGroup> studentGroups ;
	@Parent
	private transient Key<Roster> rosterKey;
	
	
	private String studentFolderId;
	
	//this map uses subject as Key value to drive folders
	private transient Map<String, String> subjectFolders = new HashMap<String, String>();
	
	public RosterStudent(){
	
	}
	public Date getCreateOn() {
		return createOn;
	}
	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public Long getRoster() {
		return roster;
	}
	public void setRoster(Long roster) {
		this.roster = roster;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getExtName(){
		return this.extName;
	}
	public void setExtName(String extName){
		this.extName = extName;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public Date getDOB() {
		return DOB;
	}
	public void setDOB(Date dOB) {
		DOB = dOB;
	}
	public String getStudentGoogleId() {
		return studentGoogleId;
	}
	public void setStudentGoogleId(String studentGoogleId) {
		this.studentGoogleId = studentGoogleId;
	}

	public Integer getPositviePoints() {
		return positviePoints;
	}
	public void setPositviePoints(Integer positviePoints) {
		this.positviePoints = positviePoints;
	}
	public Integer getNegativePoints() {
		return NegativePoints;
	}
	public void setNegativePoints(Integer negativePoints) {
		NegativePoints = negativePoints;
	}
	public Set<String> getBadges() {
		return badges;
	}
	public void setBadges(Set<String> badges) {
		this.badges = badges;
	}
	public Long getId() {
		return id;
	}
	
	public Key<Roster> getRosterKey() {
		return rosterKey;
	}
	public void setRosterKey(Key<Roster> rosterKey) {
		this.rosterKey = rosterKey;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setStudentGroups(List<StudentGroup> studentGroups){
		this.studentGroups = studentGroups;
	}
	
	public List<StudentGroup> getStudentGroups(){
		return this.studentGroups;
	}
	
	public String getStudentFolderId() {
		return studentFolderId;
	}
	public void setStudentFolderId(String studentFolderId) {
		this.studentFolderId = studentFolderId;
	}
	public Map<String, String> getTaskList(){
		return taskList;
	}
	
	public void setTaskList(Map<String, String> taskList){
		this.taskList = taskList;
	}
	public String getContactsId(){
		return contactsId;
	}
	public void setContactsId(String id){
		this.contactsId = id;
	}
	public Map<String, String> getSubjectFolders() {
		return subjectFolders;
	}
	public void setSubjectFolders(Map<String, String> subjectFolders) {
		this.subjectFolders = subjectFolders;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((DOB == null) ? 0 : DOB.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((roster == null) ? 0 : roster.hashCode());
		result = prime * result
				+ ((studentGoogleId == null) ? 0 : studentGoogleId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RosterStudent))
			return false;
		RosterStudent other = (RosterStudent) obj;
		if (DOB == null) {
			if (other.DOB != null)
				return false;
		} else if (!DOB.equals(other.DOB))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (roster == null) {
			if (other.roster != null)
				return false;
		} else if (!roster.equals(other.roster))
			return false;
		if (studentGoogleId == null) {
			if (other.studentGoogleId != null)
				return false;
		} else if (!studentGoogleId.equals(other.studentGoogleId))
			return false;
		return true;
	}
	@Override
	public int compare(RosterStudent student1, RosterStudent student2) {
		
		
		switch(student1.getLastName().compareToIgnoreCase(student2.getLastName())){
			case 0: compareFirstName(student1, student2)  ;break;
			case 1: return 1;
			case -1: return -1;
		}
		return 0;
	}
	
	private int compareFirstName(RosterStudent student1, RosterStudent student2){
		switch(student1.getFirstName().compareToIgnoreCase(student2.getFirstName())){
		case 0: compareDOB(student1, student2) ;break;
		case 1: return 1;
		case -1: return -1;
		
		}
		return 0;
		
	}
	
	private int compareDOB(RosterStudent student1, RosterStudent student2){
		
		switch(student1.getDOB().compareTo(student2.getDOB())){
		case 0: compareId(student1, student2); break;
		case 1: return 1;
		case -1: return -1;
		}
		
		return 0;
	}
	
	private int compareId(RosterStudent student1, RosterStudent student2){
		switch(student1.getId().compareTo(student2.getId())){
		case 1: return 1;
		case -1: return -1;
		case 0: return 0;
		default: return 0;
		
		}
	}
	
	
	
}
