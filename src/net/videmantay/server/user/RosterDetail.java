package net.videmantay.server.user;

import java.io.Serializable;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

import net.videmantay.server.entity.TeacherInfo;
import net.videmantay.shared.GradeLevel;

@Cache
@Entity
public class RosterDetail extends DBObj implements Serializable {

	/**
	 * 
	 */
	public static final long serialVersionUID = -8560762171107782047L;

	@Id
	public Long id;
	
	@Parent
	public transient Key<Roster> parent;
	
	public String title;
	
	public String description;

	public TeacherInfo teacherInfo;
	
	public GradeLevel gradeLevel;
	
	@Index
	public transient String ownerId;
	

	public Long getId() {
		return id;
	}

	public void setId(Long rosterId) {
		this.id = rosterId;
		this.parent = Key.create(Roster.class, id);
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

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
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

	@Override
	public boolean valid() {
		// TODO Auto-generated method stub
		return false;
	}

	

	
}
