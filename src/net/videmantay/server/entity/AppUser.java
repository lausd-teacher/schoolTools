package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.appengine.api.datastore.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Serialize;


@Entity
public  class AppUser implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -6653229961000100210L;
	
	@Id
	private Long id;
	
	private Date createOn;
	private String createdBy;
	private Date lastUpdate;
	private Long versionNum;
	//user key
	
	@Index
	private String acctId;//Google Id may be different than email 
							// in cases where teacher want to use their email
							//and not a videmantay.net acct
							//acct id will reflect videmantay.net
	private String email;
	private String firstName;
	private String lastName;
	private String middleName;
	private String extendedName;
	private String picUrl;
	private String defaultPicUrl;
	private String authToken;
	private boolean isLoggedin;
	private  UserStatus userStatus;
	private  Integer loginTimes;
	private  Boolean isFirstLogin;
	private  Long loginAttempts = 0L;
	
	@Ignore
	private transient Set<Long> rosterIds = new HashSet<Long>();
	//List of google apps id
	//main drive folder
	private transient String mainDriveFolderId;
	
	public AppUser(){}
	
	public AppUser(String email){
		this.setEmail(email);
	}
	
	public AppUser(Long id, String email){
		this.setId(id);
		this.setEmail(email);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAcctId() {
		return acctId;
	}

	public void setAcctId(String googleId) {
		this.acctId = googleId;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Long getVersionNum() {
		return versionNum;
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

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
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

	public Integer getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
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

	public void setVersionNum(Long versionNum) {
		this.versionNum = versionNum;
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
	
	public boolean isAuthRoster(Long rosterId){
		return this.rosterIds.contains(rosterId);
	}

	
	
	
}
