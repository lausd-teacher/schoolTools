package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.query.client.builders.JsonBuilder;

public class Student extends JavaScriptObject{
	protected Student() {};
		public final native  String getId()/*-{
			return this.id;
		}-*/;
		public final native String getUid()/*-{
			return this.uid;
		}-*/;
		public final native String getName_first()/*-{
			return this.name_first;
		}-*/;
		public final native String getName_last()/*-{
			return this.name_last;
		}-*/;
		public final native String getName_middle()/*-{
			return this.name_middle;
		}-*/;
		public final native String getName_display()/*-{
			return this.name_display;
		}-*/;
		public final native String getStatus()/*-{
			return this.status;
		}-*/;
		public final native String getPicture_url()/*-{
			return this.picture_url;
		}-*/;
		public final native int getAdmin()/*-{
			return this.admin;
		}-*/;

}
