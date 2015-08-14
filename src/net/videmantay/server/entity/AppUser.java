package net.videmantay.server.entity;

import java.io.Serializable;
import java.sql.Timestamp;





public  class AppUser implements Serializable{




	private Long idappUser;

	private String acctId;//Google Id may be different than email 
							// in cases where teacher want to use their email
							//and not a videmantay.net acct
							//acct id will reflect videmantay.net
	private String email;
	private String firstName;
	private String lastName;
	private String middleName;
	private String extendedName;
	private String title;
	private String picUrl;
	private String defaultPicUrl;
	private String authToken;
	private boolean isLoggedin;
	private  String role;
	private  Boolean isFirstLogin;
	private  Long loginAttempts = 0L;
	private  String mainDriveFolderId;
	private Timestamp lastLogin;
	
	public AppUser(){}
		
	public Long getIdAppUser() {
		return idappUser;
	}

	public void setIdAppUser(Long id) {
		this.idappUser = id;
	}

	public String getAcctId() {
		return acctId;
	}

	public void setAcctId(String googleId) {
		this.acctId = googleId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getExtendedName() {
		return extendedName;
	}

	public void setExtendedName(String extendedName) {
		this.extendedName = extendedName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role){
		this.role = role;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getDefaultPicUrl() {
		return defaultPicUrl;
	}

	public void setDefaultPicUrl(String defaultPicUrl) {
		this.defaultPicUrl = defaultPicUrl;
	}

	
	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}


	public boolean isLoggedin() {
		return isLoggedin;
	}

	public void setLoggedin(boolean isLoggedin) {
		this.isLoggedin = isLoggedin;
	}

	public Boolean getIsFirstLogin() {
		return isFirstLogin;
	}

	public void setIsFirstLogin(Boolean isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}
	
	public Long getLoginAttempts() {
		return loginAttempts;
	}

	public void setLoginAttempts(Long loginAttempts) {
		this.loginAttempts = loginAttempts;
	}

	public String getMainDriveFolderId() {
		return mainDriveFolderId;
	}

	public void setMainDriveFolderId(String mainDriveFolderId) {
		this.mainDriveFolderId = mainDriveFolderId;
	}

	public Timestamp getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}
	


	
	
	
}
