package net.videmantay.student.json;

public class TeacherInfoJson {

	protected TeacherInfoJson(){}
	
	public final native String getTitle()/*-{
		return this.title;
	}-*/;
	public final native TeacherInfoJson setTitle(String title)/*-{
		this.title = title;
		return this;
	}-*/;
	public final native String getLastName()/*-{
		return this.lastName;
	}-*/;
	public final native TeacherInfoJson setLastName(String lastName)/*-{
		this.lastName = lastName;
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
