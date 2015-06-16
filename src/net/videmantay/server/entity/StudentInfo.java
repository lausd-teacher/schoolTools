package net.videmantay.server.entity;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

public class StudentInfo {
/*
 * I'm calling it student info but it is basically the cums
 */
	
	
	
	@Id
	public Long id;
	
	@Index
	public String appUserId;
	
	
	
}
