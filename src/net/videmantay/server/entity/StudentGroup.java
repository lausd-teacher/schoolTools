package net.videmantay.server.entity;

import java.util.Set;

import com.google.api.services.tasks.model.Tasks;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Parent;

import net.videmantay.shared.GroupingType;



public class StudentGroup {
	
	
	private Long id;
	
	private String title;
	
	private String objective;
	
	private GroupingType type;
	
	private String backGroundColor;
	
	private String textColor;
	
	private String borderColor;
	
	private Set<String> students;
	
}
