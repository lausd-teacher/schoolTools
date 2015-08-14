package net.videmantay.server.entity;

import com.google.api.services.tasks.model.Tasks;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Parent;



public class StudentGroup {
	
	@Id
	private Long id;
	
	private String title;
	
	private String descript;
	
	@Ignore
	private Tasks tasks;
	
	private String backGroundColor;
	
	private String textColor;
	
	private String borderColor;
		
}
