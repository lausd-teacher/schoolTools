package net.videmantay.admin.json;

import com.google.gwt.core.client.JavaScriptObject;



public class AppUserJson extends JavaScriptObject {

public final native Long getId()/*-{return this.id;}-*/;
public final native String getAcctId()/*-{return this.acctId;}-*/;//Google Id may be different than email 
							// in cases where teacher want to use their email
							//and not a videmantay.net acct
							//acct id will reflect videmantay.net
	public final native AppUserJson setAcctId(String acctId)/*-{this.acctId = acctId; return this;}-*/;
	public final native  String getEmail()/*-{return this.email;}-*/;
	public final native AppUserJson setEmail(String email)/*-{ this.email = email; return this}-*/;
	public final native String getFirstName()/*-{return this.firstName;}-*/;
	public final native AppUserJson setFirstName(String string)/*-{ this.firstName = string; return this}-*/;
	public final native String getLastName()/*-{return this.lastName;}-*/;
	public final native AppUserJson setLastName(String string)/*-{ this.lastName = string; return this}-*/;
	public final native String getMiddleName()/*-{return this.middleName;}-*/;
	public final native AppUserJson setMiddleName(String string)/*-{ this.middleName = string; return this}-*/;
	public final native String getExtendedName()/*-{return this.extendedName;}-*/;
	public final native AppUserJson setExtendedName(String string)/*-{ this.extendedName = string; return this}-*/;
	public final native String getTitle()/*-{return this.title;}-*/;
	public final native AppUserJson setTitle(String string)/*-{ this.title = string; return this}-*/;
	public final native String getPicUrl()/*-{return this.picUrl;}-*/;
	public final native AppUserJson setPicUrl(String string)/*-{ this.picUrl = string; return this}-*/;
	public final native boolean getLoggedIn()/*-{return this.loggedIn;}-*/;

	public final native String getUserStatus()/*-{return this.userStatus;}-*/;
	public final native AppUserJson setUserStatus(String string)/*-{ this.userStatus = string; return this}-*/;
	public final native Integer getLoginTimes()/*-{return this.loginTimes;}-*/;
	public final native AppUserJson setLoginTimes(Integer num)/*-{ this.loginTimes = num; return this}-*/;
	public final  native Boolean getIsFirstLogin()/*-{return this.isFirstLogin;}-*/;
	public final native AppUserJson setIsFirstLogin(Boolean maybe)/*-{ this.isFirstLogin = maybe; return this}-*/;
	public final native String getMainDriveFolder()/*-{return this.mainDriveFolder;}-*/;
	public final native AppUserJson setMainDriveFolder(String string)/*-{ this.mainDriveFolder = string; return this}-*/;

	public final native String[] getRoles()/*-{return this.roles;}-*/;
	public final native AppUserJson setRoles(String[] roles)/*-{this.roles = roles;return this;}-*/;
	public final native AppUserJson addRole(String role)/*-{ this.roles.push(role);return this;}-*/;
	public final native AppUserJson clearRoles()/*-{this.roles = new Array();}-*/;
	
	public final void defaults(){
		this.setTitle("mr");
		this.setAcctId("");
		this.setEmail("");
		this.setFirstName("");
		this.setLastName("");
		this.setExtendedName("");
		this.setMiddleName("");
		this.setPicUrl("");
		this.setMainDriveFolder("");
	}
	
	protected AppUserJson(){}
}
