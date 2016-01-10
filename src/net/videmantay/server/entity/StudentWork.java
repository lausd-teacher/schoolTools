package net.videmantay.server.entity;

import java.util.List;

import net.videmantay.shared.GradedWorkType;
import net.videmantay.shared.StudentWorkStatus;
import net.videmantay.shared.SubjectType;

import com.googlecode.objectify.annotation.Index;



public class StudentWork {

	private Long id;
	// query by gradedwork Key;
	
	
	@Index
	private Long rosterStudentId;
	private Double percentage;
	private Double pointsEarned;
	private String letterGrade;
	private String message;
	private GradedWorkType type;
	private StudentWorkStatus studentWorkStatus = StudentWorkStatus.NOT_TURNED_IN;
	private String dateTaken;
	private String mediaUrl;
	
	private SubjectType subject;
	
	//List of standard to review with accomany links;
	private List<StandardReview> standardReviews;
	
	public StudentWork(){
		
	}
	
	public Long getRosterStudentId() {
		return rosterStudentId;
	}

	public void setRosterStudentId(Long rosterStudent) {
		this.rosterStudentId = rosterStudent;
	}

	public String getDateTaken() {
		return dateTaken;
	}

	public void setDateTaken(String dateTaken) {
		this.dateTaken = dateTaken;
	}

	public SubjectType getSubject() {
		return subject;
	}

	public void setSubject(SubjectType subject) {
		this.subject = subject;
	}

	public void setId(Long id) {
		this.id = id;
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
	

	public GradedWorkType getType() {
		return type;
	}

	public void setType(GradedWorkType type) {
		this.type = type;
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

	
}
