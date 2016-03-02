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
	private static final long serialVersionUID = -6653229961000100210L;
	
	@Id
	private Long id;
	
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
	private UserTitle title;

	private String picUrl;
	private String authToken;
	private boolean loggedIn;

	private  UserStatus userStatus;
	private  Integer loginTimes;
	private  Boolean isFirstLogin;
	private String mainDriveFolder;
	
	private  Set<UserRoles> roles = new  HashSet<UserRoles>();
	
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
	
	
}
