package net.videmantay.server.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Cache
@Entity
public class RosterSetting extends DBObj implements Serializable{
	
	@Id
	public Long id;

	@Ignore
	public Boolean overrideDefault;
	
	@Index 
	public String acctId;
	
	@Parent
	public transient Key<AppUser> parent;
	
	public Set<String> folderNames = new HashSet<>();
	
	public Set<String> taskNames = new HashSet<>();
	
	public Set<String> calendarNames = new HashSet<>();
	
	public Boolean includeContacts = false;
	
	public RosterSetting(){
		defaultSetting();
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Key<AppUser> getParent() {
		return parent;
	}

	public void setParent(Key<AppUser> parent) {
		this.parent = parent;
	}

	public Set<String> getFolderNames() {
		return folderNames;
	}

	public void setFolderNames(Set<String> folderNames) {
		this.folderNames = folderNames;
	}

	public Set<String> getTaskNames() {
		return taskNames;
	}

	public void setTaskNames(Set<String> taskNames) {
		this.taskNames = taskNames;
	}

	public Set<String> getCalendarNames() {
		return calendarNames;
	}

	public void setCalendarNames(Set<String> calendarNames) {
		this.calendarNames = calendarNames;
	}
	
	public Boolean isOverrideDefault(){
		return overrideDefault;
	}
	
	public void setOverrideDefault(Boolean override){
		overrideDefault = override;
	}

	@Override
	public boolean valid() {
		// TODO Auto-generated method stub
		return false;
	}

	public RosterSetting defaultSetting(){
		
		folderNames.addAll(ImmutableSet.of("Students", "Forms", "Lessons","Assignments"));
		return this;
	}
	

}
