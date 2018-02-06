package net.videmantay.student.json;

public class TeacherInfoJson {

	protected TeacherInfoJson(){}
	
	public final native String getName()/*-{
		return this.name;
	}-*/;
	public final native TeacherInfoJson setLastName(String name)/*-{
		this.name = name;
		return this;
	}-*/;
	public final native String getPicUrl()/*-{
		return this.picUrl;
	}-*/;
	public final native TeacherInfoJson setPicUrl(String picUrl)/*-{
		this.picUrl = picUrl;
		return this;
	}-*/;
}
