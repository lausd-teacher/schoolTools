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
public class Roster implements Serializable{
	
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
	
	
	//storing email id for user
	//maybe useful when calling services
	
	@Index
	private String teacherGoogleId;
	
	//db id of the teacher
	private Long teacherId;
	
	private GradeLevel gradeLevel;
	
	@Index
	private Date startDate;
	
	@Index
	private Date endDate;
	
	@Serialize
	private Map<String , SeatingChartDetail> seatingCharts = new HashMap<String , SeatingChartDetail>();

	@Serialize
	private Map<String, String> googleCalendars = new HashMap<String, String>();
	
	@Serialize 
	private Map<String, String> googleTasks = new HashMap<String, String>();

	
	//maybe a sorted set by last name???
	private Set<Key<RosterStudent>>students = new HashSet<Key<RosterStudent>>();
		
	//Set<AppContact> contacts;
	private List<Key<StudentJob>> studentJobs = new ArrayList<Key<StudentJob>>();
	
	private Set<Key<AppContact>> contacts = new HashSet<Key<AppContact>>();
	
	private List<Key<StudentGroup>> studentGroups = new ArrayList<Key<StudentGroup>>();
	
	private  String rosterFolderId;
	
	private  Map<String , String > studentFolders = new HashMap<String, String>();
	
	///Constructors
	
	public Roster(){
		this.id = UUID.randomUUID().getLeastSignificantBits();
	}
	
	public Roster(UserAccount acct){
		this.id = UUID.randomUUID().getLeastSignificantBits();
		
		if(acct.getUserStatus() == UserStatus.TEACHER){
		this.setTeacherGoogleId(acct.getAcctId());
		} //end if
	}


	public String getTeacherGoogleId() {
		return teacherGoogleId;
	}

	public void setTeacherGoogleId(String teacherGoogleId) {
		this.teacherGoogleId = teacherGoogleId;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public Map<String, SeatingChartDetail> getSeatingCharts() {
		return seatingCharts;
	}

	public void setSeatingCharts( Map<String, SeatingChartDetail> seatingCharts) {
		this.seatingCharts = seatingCharts;
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
	
	public Map<String, String> getGoogleCalendars(){
		return this.googleCalendars;
	}
	
	public void setGoogleCalendars(Map<String, String> googleCals){
		this.googleCalendars = googleCals;
	}
	
	public Map<String, String> getGoogleTasks(){
		return this.googleTasks;
	}
	
	public String getRosterFolderId() {
		return rosterFolderId;
	}

	public void setRosterFolderId(String rosterFolderId) {
		this.rosterFolderId = rosterFolderId;
	}

	public Map<String, String> getStudentFolders() {
		return studentFolders;
	}

	public void setStudentFolders(Map<String, String> studentFolders) {
		this.studentFolders = studentFolders;
	}

	public void setGoogleTasks(Map<String, String> googleTasks){
		this.googleTasks = googleTasks;
	}
	
	
}
