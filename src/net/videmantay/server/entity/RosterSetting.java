package net.videmantay.server.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

public class RosterSetting {
	
	@Id
	private Long id;

	@Ignore
	private Boolean overrideDefault;
	
	@Index 
	private String acctId;
	
	@Parent
	private transient Key<AppUser> parent;
	
	private Set<String> folderNames = new HashSet<>();
	
	private Set<String> taskNames = new HashSet<>();
	
	private Set<String> calendarNames = new HashSet<>();

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

	
	

}
