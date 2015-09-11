package net.videmantay.server.entity;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import net.videmantay.shared.GradeLevel;


public class RosterDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8560762171107782047L;
	
	//this parameter either points to a roster or
	// to a roster student depending on the user;
	private Long rosterId;
	
	private String title;
	
	private String description;

	private TeacherInfo teacherInfo;
	
	private GradeLevel gradeLevel;
	
	

	public Long getRosterId() {
		return rosterId;
	}

	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
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

	public TeacherInfo getTeacherInfo() {
		return teacherInfo;
	}

	public void setTeacherInfo(TeacherInfo teacherInfo) {
		this.teacherInfo = teacherInfo;
	}

	public GradeLevel getGradeLevel() {
		return gradeLevel;
	}

	public void setGradeLevel(GradeLevel gradeLevel) {
		this.gradeLevel = gradeLevel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rosterId == null) ? 0 : rosterId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof RosterDetail)) {
			return false;
		}
		RosterDetail other = (RosterDetail) obj;
		if (rosterId == null) {
			if (other.rosterId != null) {
				return false;
			}
		} else if (!rosterId.equals(other.rosterId)) {
			return false;
		}
		return true;
	}

	

	
}
