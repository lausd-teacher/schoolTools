package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;

public class Teacher extends JavaScriptObject {
	protected Teacher(){};
	public final native String gePicture_url()/*-{
		return this.picture_url;
	}-*/;
	public final native String getPrimary_email()/*-{
		return this.primary_email;
	}-*/;
	public final native String getName_first()/*-{
			return this.name_first;
	}-*/;
	public final native String getName_last()/*-{
		return this.name_last;
	}-*/;
}
