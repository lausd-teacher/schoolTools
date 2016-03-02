package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuizSection implements Serializable {

	public Long id;
	
	public SectionType type;
	
	public List<Long> questions = new ArrayList<Long>();
	
	public String description;
	
	public Boolean randomOrder;
	
	public String instructions;
	
	
	
	public enum SectionType{}
	
}

