package net.videmantay.server.entity;

import java.io.Serializable;

public class StudentSeat implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4912335951117569448L;
	
	private String matrix;
	private String top;
	private String left;
	private boolean isEmpty;
	private Integer seatNum;
	private RosterStudent rosterStudent;
	private StudentGroup studentGroup;
	
	public String getMatrix() {
		return matrix;
	}
	public void setMatrix(String matrix) {
		this.matrix = matrix;
	}
	public String getTop() {
		return top;
	}
	public void setTop(String top) {
		this.top = top;
	}
	public String getLeft() {
		return left;
	}
	public void setLeft(String left) {
		this.left = left;
	}
	public boolean isEmpty() {
		return isEmpty;
	}
	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}
	public Integer getSeatNum() {
		return seatNum;
	}
	public void setSeatNum(Integer seatNum) {
		this.seatNum = seatNum;
	}
	public RosterStudent getRosterStudent() {
		return rosterStudent;
	}
	public void setRosterStudent(RosterStudent rosterStudent) {
		this.rosterStudent = rosterStudent;
	}
	public StudentGroup getStudentGroup() {
		return studentGroup;
	}
	public void setStudentGroup(StudentGroup studentGroup) {
		this.studentGroup = studentGroup;
	}

}