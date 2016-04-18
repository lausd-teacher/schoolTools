package net.videmantay.shared;

import com.google.gwt.core.client.JavaScriptObject;

public class LoginInfo extends JavaScriptObject {
	
		protected LoginInfo(){}
		
		public final native String getAuthToken()/*-{return this.authToken;}-*/;
		public final native String getAcctId()/*-{return this.acctId;}-*/;//Google Id may be different than email 
								// in cases where teacher want to use their email
								//and not a videmantay.net acct
								//acct id will reflect videmantay.net
		public final native  String getEmail()/*-{return this.email;}-*/;
		public final native LoginInfo setEmail(String email)/*-{ this.email = email; return this}-*/;
		public final native String getFirstName()/*-{return this.firstName;}-*/;
		public final native LoginInfo setFirstName(String string)/*-{ this.firstName = string; return this}-*/;
		public final native String getLastName()/*-{return this.lastName;}-*/;
		public final native LoginInfo setLastName(String string)/*-{ this.lastName = string; return this}-*/;
		public final native String getMiddleName()/*-{return this.middleName;}-*/;
		public final native LoginInfo setMiddleName(String string)/*-{ this.middleName = string; return this}-*/;
		public final native String getExtendedName()/*-{return this.extendedName;}-*/;
		public final native LoginInfo setExtendedName(String string)/*-{ this.extendedName = string; return this}-*/;
		public final native String getTitle()/*-{return this.title;}-*/;
		public final native LoginInfo setTitle(String string)/*-{ this.title = string; return this}-*/;
		public final native String getPicUrl()/*-{return this.picUrl;}-*/;
		public final native LoginInfo setPicUrl(String string)/*-{ this.picUrl = string; return this}-*/;
		public final native boolean getLoggedIn()/*-{return this.loggedIn;}-*/;		
		public final native Integer getLoginTimes()/*-{return this.loginTimes;}-*/;
		public final native LoginInfo setLoginTimes(Integer num)/*-{ this.loginTimes = num; return this}-*/;
		public final  native Boolean getIsFirstLogin()/*-{return this.isFirstLogin;}-*/;
		public final native LoginInfo setIsFirstLogin(Boolean maybe)/*-{ this.isFirstLogin = maybe; return this}-*/;
		public final native String getMainDriveFolder()/*-{return this.mainDriveFolder;}-*/;
		public final native LoginInfo setMainDriveFolder(String string)/*-{ this.mainDriveFolder = string; return this}-*/;
		public static final native LoginInfo create()/*-{ return $wnd.loginInfo;}-*/;

}
