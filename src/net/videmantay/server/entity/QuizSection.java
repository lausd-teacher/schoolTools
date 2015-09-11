package net.videmantay.server.entity;

import java.util.ArrayList;
import java.util.List;

public class QuizSection {

	private Long id;
	
	private SectionType type;
	
	private List<Long> questions = new ArrayList<Long>();
	
	private String description;
	
	private Boolean randomOrder;
	
	private String instructions;
	
	
	
	private enum SectionType{}
	
}

