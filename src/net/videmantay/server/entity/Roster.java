package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;




import java.util.UUID;

import net.videmantay.shared.GradeLevel;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Serialize;

@Cache
@Entity
public class Roster extends DBObj implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/*
	 * Roster should only send back what is immediately needed by roster view???
	 * Well if we cache it then we can have a great deal available and not worry about 
	 * DB  calls??? Anything that will be over a hundred will not be included
	 */

	
	@Id
	private Long id;
	
	@Index
	private String ownerId;
	
	private String title;
	
	private String description;
	
	private String roomNum;

	@Serialize
	private TeacherInfo teacherInfo;
	
	private GradeLevel gradeLevel;
	
	private Date startDate;
	
	
	private Date endDate;
	
	@Serialize
	private List<GoogleService> googleCalendars = new ArrayList<GoogleService>();
	
	@Serialize 
	private List<GoogleService> googleTasks = new ArrayList<GoogleService>();

	
	//maybe a sorted set by last name???
	private Set<Key<RosterStudent>>students = new HashSet<Key<RosterStudent>>();
		
	//Set<AppContact> contacts;
	private List<Key<StudentJob>> studentJobs = new ArrayList<Key<StudentJob>>();
	
	private Set<Key<AppContact>> contacts = new HashSet<Key<AppContact>>();
	
	private List<Key<StudentGroup>> studentGroups = new ArrayList<Key<StudentGroup>>();
	
	private  String rosterFolderId;
	
	///Constructors
	
	public Roster(){
	
	}
	
	public Set<Key<RosterStudent>> getStudents() {
		return students;
	}

	public void setStudents(Set<Key<RosterStudent>> students) {
		this.students = students;
	}

	public List<Key<StudentJob>> getStudentJobs() {
		return studentJobs;
	}

	public void setStudentJobs(List<Key<StudentJob>> studentJobs) {
		this.studentJobs = studentJobs;
	}

	public Set<Key<AppContact>> getContacts() {
		return contacts;
	}

	public void setContacts(Set<Key<AppContact>> contacts) {
		this.contacts = contacts;
	}

	public Long getId() {
		return id;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
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

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public TeacherInfo getTeacherInfo() {
		return teacherInfo;
	}

	public void setTeacherInfo(TeacherInfo teacherInfo) {
		this.teacherInfo = teacherInfo;
	}

	public List<Key<StudentGroup>> getStudentGroups() {
		return studentGroups;
	}

	public void setStudentGroups(List<Key<StudentGroup>> studentGroups) {
		this.studentGroups = studentGroups;
	}
	
	public GradeLevel getGradeLevel(){
		return this.gradeLevel;
	}
	
	public void setGradeLevel(GradeLevel grdLvl){
		this.gradeLevel = grdLvl;
	}
	
	public List<GoogleService> getGoogleCalendars(){
		return this.googleCalendars;
	}
	
	public void setGoogleCalendars(List<GoogleService> googleCals){
		this.googleCalendars = googleCals;
	}
	
	public List<GoogleService> getGoogleTasks(){
		return this.googleTasks;
	}
	
	public void setGoogleTasks(List<GoogleService> googleTasks){
		this.googleTasks = googleTasks;
	}
	
	public String getRosterFolderId() {
		return rosterFolderId;
	}

	public void setRosterFolderId(String rosterFolderId) {
		this.rosterFolderId = rosterFolderId;
	}
	
	public RosterDetail createDetail(){
		RosterDetail detail = new RosterDetail();
		detail.setId(this.id);
		detail.setDescription(this.description);
		detail.setTitle(this.title);
		detail.setGradeLevel(this.gradeLevel);
		detail.setTeacherInfo(this.teacherInfo);
		detail.setParent(Key.create(Roster.class, this.id));
		return detail;
	}



	
}
