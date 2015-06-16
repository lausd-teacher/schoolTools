package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Serialize;


@Entity
public class SeatingChart implements Serializable {
	

	private static final long serialVersionUID = 2656470746976405698L;
	
	@Id
	private Long id;
	private String title;
	private String descript;
	@Parent
	Key<Roster> rsoterKey;
	
	@Serialize
	private Set<StudentSeat> seats;
	@Serialize
	private TeacherDesk teacherDesk;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	
	public Set<StudentSeat> getSeats() {
		return seats;
	}
	public void setSeats(Set<StudentSeat> seats) {
		this.seats = seats;
	}
	public TeacherDesk getTeacherDesk() {
		return teacherDesk;
	}
	public void setTeacherDesk(TeacherDesk teacherDesk) {
		this.teacherDesk = teacherDesk;
	}

	public Long getId() {
		return id;
	}
	
	

}
