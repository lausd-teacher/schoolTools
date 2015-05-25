package net.videmantay.server.entity;

import java.util.Date;
import java.util.List;

import net.videmantay.shared.StudentWorkStatus;
import net.videmantay.shared.SubjectType;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;


@Entity
public class StudentWork {

	@Id
	private Long id;
	// query by gradedwork Key;
	
	@Parent
	private transient Key<GradedWork> gradedWorkKey;
	// query by studentId;
	
	@Ignore
	private RosterStudent student;
	
	@Load(RosterStudent.class)
	private Ref<RosterStudent> studentRef;
	
	
	@Index
	private Long rosterStudentId;
	private Double percentage;
	private Double pointsEarned;
	private String letterGrade;
	private String message;
	private Long gradedWork;
	private StudentWorkStatus studentWorkStatus = StudentWorkStatus.NOT_TURNED_IN;
	@Index
	private Date dateTaken;
	
	@Index
	private SubjectType subject;
	// url of accompanying video or audio
	private String mediaUrl;
	
	//url of student actual work
	private String studentDocUrl;
	
	//List of standard to review with accomany links;
	private List<StandardReview> standardReviews;
	
	public StudentWork(){
		
	}

	public Key<GradedWork> getGradedWorkKey() {
		return gradedWorkKey;
	}

	public void setGradedWorkKey(Key<GradedWork> gradedWork) {
		this.gradedWorkKey = gradedWork;
	}

	public Long getGradedWork() {
		return gradedWork;
	}

	public void setGradedWork(Long gradedWork) {
		this.gradedWork = gradedWork;
	}

	public Long getRosterStudentId() {
		return rosterStudentId;
	}

	public void setRosterStudentId(Long rosterStudent) {
		this.rosterStudentId = rosterStudent;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public String getLetterGrade() {
		return letterGrade;
	}

	public void setLetterGrade(String letterGrade) {
		this.letterGrade = letterGrade;
	}
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public String getStudentDocUrl() {
		return studentDocUrl;
	}

	public void setStudentDocUrl(String studentDocUrl) {
		this.studentDocUrl = studentDocUrl;
	}

	public List<StandardReview> getStandardReviews() {
		return standardReviews;
	}

	public void setStandardReviews(List<StandardReview> standardReviews) {
		this.standardReviews = standardReviews;
	}

	public Long getId() {
		return id;
	}

	public Double getPointsEarned() {
		return pointsEarned;
	}

	public void setPointsEarned(Double pointsEarned) {
		this.pointsEarned = pointsEarned;
	}
	
	public StudentWorkStatus getStudentWorkStatus() {
		return studentWorkStatus;
	}

	public void setStudentWorkStatus(StudentWorkStatus studentWorkStatus) {
		this.studentWorkStatus = studentWorkStatus;
	}

	
	public RosterStudent getStudent(){
		return this.student;
	}
	
	public void setStudent(RosterStudent student){
		this.student = student;
	}
	public Ref<RosterStudent> getStudentRef(){
		return studentRef;
	}
	
	public void setStudentRef(RosterStudent student){
		this.studentRef = Ref.create(student);
	}
	
	public void setStudentRef(Key<RosterStudent> student){
		this.studentRef = Ref.create(student);
	}
	
	
}
