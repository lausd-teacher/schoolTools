package net.videmantay.server.entity;

import java.io.Serializable;

import net.videmantay.server.user.RosterStudent;

public class StudentSeat implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4912335951117569448L;
	
	
	public Integer seatNum;
	public Long rosterStudent;
	public Long studentGroup;
	public String color;
	public String url;
	public boolean isEmpty;
	
	
	public boolean isEmpty() {
		if(rosterStudent == null ||rosterStudent == 0L){
			this.isEmpty = false;
		}else{
			this.isEmpty = true;
		}
		
		return isEmpty;
	}
	
	public Integer getSeatNum() {
		return seatNum;
	}
	public void setSeatNum(Integer seatNum) {
		this.seatNum = seatNum;
	}
	public Long getRosterStudent() {
		return rosterStudent;
	}
	public void setRosterStudent(Long rosterStudent) {
		this.rosterStudent = rosterStudent;
	}
	public Long getStudentGroup() {
		return studentGroup;
	}
	public void setStudentGroup(Long studentGroup) {
		this.studentGroup = studentGroup;
	}

}
