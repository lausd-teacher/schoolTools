package net.videmantay.server.user;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Serialize;

import net.videmantay.shared.UserRoles;
import net.videmantay.shared.UserStatus;


@Entity
@Cache
public  class AppUser extends DBObj implements Serializable{


	/**
	 * 
	 */
	public static final long serialVersionUID = -6653229961000100210L;
	
	@Id
	public Long id;
	
	@Index
	public String acctId;//Google Id may be different than email 
							// in cases where teacher want to use their email
							//and not a videmantay.net acct
							//acct id will reflect videmantay.net
	
	public String email;
	
	public String firstName;
	public String lastName;
	public String middleName;
	public String extendedName;
	public UserTitle title;

	public String picUrl;
	public String authToken;
	public boolean loggedIn;

	public  UserStatus userStatus;
	public  Integer loginTimes;
	public  Boolean isFirstLogin;
	public String mainDriveFolder;
	
	public  Set<UserRoles> roles = new  HashSet<UserRoles>();
	
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

	public UserTitle getTitle() {
		return title;
	}

	public void setTitle(UserTitle title) {
		this.title = title;
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

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public Set<UserRoles> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRoles> roles) {
		this.roles = roles;
	}

	public Integer getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean isLoggedin) {
		this.loggedIn = isLoggedin;
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
	

	public String getMainDriveFolder() {
		return mainDriveFolder;
	}

	public void setMainDriveFolder(String mainDriveFolder) {
		this.mainDriveFolder = mainDriveFolder;
	}
	public enum UserTitle{MR, MS, MRS}
	@Override
	public boolean valid() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public LoginInfo getLoginInfo(){
		return new LoginInfo(acctId, firstName,lastName, extendedName,title, 
				picUrl, authToken,mainDriveFolder,loggedIn, isFirstLogin);
	}
	
	public static class LoginInfo{
		private LoginInfo(){}
		
		public LoginInfo(String acctId, String firstName, String lastName, String extendedName, UserTitle title,
				String picUrl, String authToken,String mainDriveFolder, boolean loggedIn, Boolean isFirstLogin){
			this.acctId = acctId;
			this.firstName = firstName;
			this.lastName = lastName;
			this.extendedName = extendedName;
			this.authToken = authToken;
			this.mainDriveFolder = mainDriveFolder;
			this.loggedIn = loggedIn;
			this.isFirstLogin = isFirstLogin;
			this.title = title;
			this.picUrl = picUrl;
		}
		
		@Index
		public String acctId;//Google Id may be different than email 
								// in cases where teacher want to use their email
								//and not a videmantay.net acct
								//acct id will reflect videmantay.net
		
		public String email;
		
		public String firstName;
		public String lastName;
		public String middleName;
		public String extendedName;
		public UserTitle title;

		public String picUrl;
		public String authToken;
		public boolean loggedIn;
		public  Boolean isFirstLogin;
		public String mainDriveFolder;
		
	}
	
	
}
