package net.videmantay.server.entity;

import java.io.Serializable;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

import net.videmantay.shared.GradeLevel;


@Entity
public class RosterDetail extends DBObj implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8560762171107782047L;

	@Id
	private Long id;
	
	@Parent
	private Key<Roster> parent;
	
	private String title;
	
	private String description;

	private TeacherInfo teacherInfo;
	
	private GradeLevel gradeLevel;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long rosterId) {
		this.id = rosterId;
	}

	public Key<Roster> getParent() {
		return parent;
	}

	public void setParent(Key<Roster> parent) {
		this.parent = parent;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	

	
}
