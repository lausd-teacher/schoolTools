package net.videmantay.shared;

import com.google.gwt.core.client.JavaScriptObject;

public class LoginInfo extends JavaScriptObject {
	
		protected LoginInfo(){}
		
		public final native String getAuthToken()/*-{return this.token;}-*/;
		public final native  String getEmail()/*-{return this.email;}-*/;
		public final native String getFirstName()/*-{return this.firstName;}-*/;;
		public final native String getLastName()/*-{return this.lastName;}-*/;
		public final native String getImg()/*-{return this.img;}-*/;
		public final native String getLogout()/*-{return this.logout}-*/;
		public static final native LoginInfo create()/*-{ return $wnd.info;}-*/;

}
