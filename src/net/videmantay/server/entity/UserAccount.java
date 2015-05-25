package net.videmantay.server.entity;

import java.io.Serializable;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Cache
public class UserAccount implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 773966179513705901L;

	public UserAccount(){}
	
	public UserAccount(String id, String email, UserStatus userStatus){
		this.setId(id);
	}

	
	@Id
	private String id;
	private Long loginAttempts = 0L;
	private boolean isFirstLogin = true;
	private String firstName;
	private String LastName;
	private String middleName;
	private String extendedName;
	private String pic;
	private String defaultPicUrl;
	private Key<AppUser> appUser;
	
	@Index
	private String acctId;
	private UserStatus userStatus;

	public String getAcctId() {
		return acctId;
	}

	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Long getLoginAttempts() {
		return loginAttempts;
	}

	public void setLoginAttempts(Long loginAttempts) {
		this.loginAttempts = loginAttempts;
	}

	public boolean isFirstLogin() {
		return isFirstLogin;
	}

	public void setFirstLogin(boolean isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getPic() {
		return pic;
	}

	public String getExtendedName() {
		return extendedName;
	}

	public void setExtendedName(String extendedName) {
		this.extendedName = extendedName;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getDefaultPicUrl() {
		return defaultPicUrl;
	}

	public void setDefaultPicUrl(String defaultPicUrl) {
		this.defaultPicUrl = defaultPicUrl;
	}
	
	public Key<AppUser> getAppUser(){
		return appUser;
	}
	
	public void setAppUser(Key<AppUser> appUser){
		this.appUser = appUser;
	}
	
	public Key<UserAccount> generateKey(){
		return Key.create(null, UserAccount.class, this.id);
	}
	
}
