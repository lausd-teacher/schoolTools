package net.videmantay.shared;

public enum SubjectType {
	READING("reading"), WRITING("writing"), LISTENING("listening"),
	SPEAKING ("speaking"), MATH("math"), SOCIAL_STUDIES("socail studies"), SCIENCE("science"),
	PE("p.e."), ART("art"), GENERAL("general");
	private String type;
	private SubjectType(String type){
		this.type = type;
	}
	public String toString(){
		return this.type;
	}

}
