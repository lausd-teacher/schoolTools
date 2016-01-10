package net.videmantay.server.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;




import java.util.UUID;

import net.videmantay.server.entity.AppContact;
import net.videmantay.server.entity.Badge;
import net.videmantay.server.entity.GoogleService;
import net.videmantay.server.entity.SeatingChart;
import net.videmantay.server.entity.StudentGroup;
import net.videmantay.server.entity.StudentJob;
import net.videmantay.server.entity.TeacherInfo;
import net.videmantay.shared.GradeLevel;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.OnLoad;
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
	
	//all map to a spreadsheet
	private String rollBook;
	private String gradeBook;
	private String behaviorReport;
	
	@Serialize
	private List<GoogleService> googleCalendars = new ArrayList<GoogleService>();
	
	@Serialize 
	private List<GoogleService> googleTasks = new ArrayList<GoogleService>();

	
	//maybe a sorted set by last name???
	private Set<Key<RosterStudent>>studentKeys = new HashSet<Key<RosterStudent>>();
	
	@Ignore
	private Set<RosterStudent> rosterStudents = new HashSet<RosterStudent>();
		
	@Serialize
	private StudentJob[] studentJobs;
	
	@Serialize
	private SeatingChart[] seatingCharts;
	
	@Serialize
	private Badge[] badges;
	
	private Set<Key<AppContact>> contacts = new HashSet<Key<AppContact>>();
	
	private List<Key<StudentGroup>> studentGroups = new ArrayList<Key<StudentGroup>>();
	
	private  String rosterFolderId;
	
	///Constructors
	
	public Roster(){
	
	}
	
	public Set<Key<RosterStudent>> getStudentKeys() {
		return studentKeys;
	}

	public void setStudentKeys(Set<Key<RosterStudent>> students) {
		this.studentKeys = students;
	}

	public StudentJob[] getStudentJobs() {
		return studentJobs;
	}

	public void setStudentJobs(StudentJob[] studentJobs) {
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
	
	public Set<RosterStudent> getRosterStudents() {
		return rosterStudents;
	}

	public void setRosterStudents(Set<RosterStudent> rosterStudents) {
		this.rosterStudents = rosterStudents;
	}

	public String getRollBook() {
		return rollBook;
	}

	public void setRollBook(String rollBook) {
		this.rollBook = rollBook;
	}

	public String getGradeBook() {
		return gradeBook;
	}

	public void setGradeBook(String gradeBook) {
		this.gradeBook = gradeBook;
	}

	public String getBehaviorReport() {
		return behaviorReport;
	}

	public void setBehaviorReport(String behaviorReport) {
		this.behaviorReport = behaviorReport;
	}

	public RosterDetail createDetail(){
		RosterDetail detail = new RosterDetail();
		detail.setId(this.id);
		detail.setDescription(this.description);
		detail.setTitle(this.title);
		detail.setGradeLevel(this.gradeLevel);
		detail.setTeacherInfo(this.teacherInfo);
		detail.setParent(Key.create(Roster.class, this.id));
		detail.setOwnerId(this.getOwnerId());
		return detail;
	}

	@Override
	public boolean valid() {
		// TODO Auto-generated method stub
		return false;
	}

	@OnLoad void onLoad(){
		//load students from keys
		this.getRosterStudents().addAll(DB.db().load().keys(this.getStudentKeys()).values());
	}

	
}
