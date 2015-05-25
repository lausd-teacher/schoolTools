package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.Set;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;



@Entity
public class AppContact implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2474373332893705362L;
	@Id
	
	
	private Long id;
	private String email;
	private String firstName;
	private String lastName;
	private String address;
	private Set<String> phoneNumbers;
	private ContactType contactType;
	private String ownerId;

	
	
	enum ContactType{PARENT, STUDENT, COWORKER,OTHER}



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



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public Set<String> getPhoneNumbers() {
		return phoneNumbers;
	}



	public void setPhoneNumbers(Set<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}



	public ContactType getContactType() {
		return contactType;
	}



	public void setContactType(ContactType contactType) {
		this.contactType = contactType;
	}



	public String getOwnerId() {
		return ownerId;
	}



	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}



	public Long getId() {
		return id;
	}
	

}
