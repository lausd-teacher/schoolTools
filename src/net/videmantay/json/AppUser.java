package net.videmantay.json;


import com.google.gwt.core.client.JavaScriptObject;

public class AppUser extends JavaScriptObject {
	

	protected AppUser(){}
	
	public final native Long getId()/*-{
	   return this.id;
	}-*/;
	
	public final native String getAcctId()/*-{
	 return this.acctId;
	}-*/;

	public final native AppUser setAcctId(String googleId)/*-{
	 this.acctId = googleId;
	 return this;
	}-*/;

	
	public final native String getEmail()/*-{
		return this.email;
	}-*/;

	public final native AppUser setEmail(String email)/*-{this.email = email;
															return this;}-*/;
	
	public final native String getFirstName()/*-{ return this.firstName;}-*/;

	public final native AppUser setFirstName(String firstName)/*-{this.firstName = firstName;
																	return this;}-*/;

	public final native String getLastName()/*-{return this.lastName;}-*/;

	public final native AppUser setLastName(String lastName)/*-{this.lastName = lastName;
	                                                            return this;}-*/;

	public final native String getMiddleName()/*-{return this.middleName;}-*/;

	public final native AppUser setMiddleName(String middleName)/*-{this.middleName = middleName;
																	return this;}-*/;

	public final native String getExtendedName()/*-{return this.extendedName;}-*/;

	public final native AppUser setExtendedName(String extendedName)/*-{ this.extendedName = extendedName;
																			return this;}-*/;

	public final native String getTitle()/*-{
		return this.title;
	}-*/;

	public final native AppUser setTitle(String title)/*-{this.title= title;
															return this;}-*/;

	public final native String[] getRoles()/*-{return this.roles;}-*/;

	public final native AppUser addRole(String role)/*-{this.roles.push(role);
														return this;}-*/;

	public final native String getPicUrl()/*-{return this.picUrl;}-*/;

	public final native AppUser setPicUrl(String picUrl) /*-{this.picUrl = picUrl;
																	return this;}-*/;

	public final native String getAuthToken()/*-{return this.authToken;}-*/;

	public final native AppUser setAuthToken(String authToken)/*-{this.authToken = authToken;
																	return this;}-*/;

	public final native boolean isLoggedIn()/*-{return this.loggedIn;}-*/;

	public final native AppUser setLoggedIn(boolean isLoggedin)/*-{this.loggedIn = isLoggedin;
												return this;}-*/;
	
	public final native String getMainDriveFolderId()/*-{return this.mainDriveFolderId;}-*/;

	public final native AppUser setMainDriveFolderId(String mainDriveFolderId)/*-{ this.mainDriveFolderId = mainDriveFolderId;
																					return this;}-*/;
	public final native boolean getStatus()/*-{ return this.userStatus; }-*/;
	
	public final native AppUser setStatus(String status)/*-{ this.userStatus = status; return this;}-*/;
	
	public static  final native AppUser create()/*-{ return {}; }-*/;
	}