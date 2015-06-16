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
	
	@Index
	private String title;
	
	@Serialize
	private Map<String , SeatingChartDetail> seatingCharts = new HashMap<String , SeatingChartDetail>();

	@Serialize
	private Map<String, String> calendars = new HashMap<String, String>();
	
	@Serialize 
	private Map<String, String> tasks = new HashMap<String, String>();
	
	private String rosterContacts;

	
	//maybe a sorted set by last name???
	private Set<Key<RosterStudent>>students = new HashSet<Key<RosterStudent>>();
		

	private List<Key<StudentJob>> studentJobs = new ArrayList<Key<StudentJob>>();
	
	
	
	private List<Key<StudentGroup>> studentGroups = new ArrayList<Key<StudentGroup>>();
	
	private  String rosterFolderId;
	
	private  Map<String , String > folders = new HashMap<String, String>();
	
	///Constructors
	
	public Roster(){
		this.id = UUID.randomUUID().getLeastSignificantBits();
	}
	
	public Roster(AppUser acct){
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

	public String getContacts() {
		return rosterContacts;
	}

	public void setContacts(String contactGroupId) {
		this.rosterContacts = contactGroupId;
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
	
	public Map<String, String> getCalendars(){
		return this.calendars;
	}
	
	public void setCalendars(Map<String, String> googleCals){
		this.calendars = googleCals;
	}
	
	public Map<String, String> getTasks(){
		return this.tasks;
	}
	
	public String getRosterFolderId() {
		return rosterFolderId;
	}

	public void setRosterFolderId(String rosterFolderId) {
		this.rosterFolderId = rosterFolderId;
	}

	public Map<String, String> getFolders() {
		return folders;
	}

	public void setFolders(Map<String, String> folders) {
		this.folders = folders;
	}

	public void setTasks(Map<String, String> googleTasks){
		this.tasks = googleTasks;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getTitle(){
		return title;
	}
	
	
}
