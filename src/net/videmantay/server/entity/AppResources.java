package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Serialize;

@Entity
public class AppResources implements Serializable {
	/**
	 * Keeps track of all the important id 
	 * the user needs to interact with.
	 */
	private static final long serialVersionUID = 3485381670116706285L;
	@Id
	private Long id;
	@Index
	private String acctId;//AppUser acct Id key
	
	private  String KimchiFolder;
	private  String rootSharedFolder;
	private  String rootWorksheetFolder;
	private  String rootShowcaseFolder;
	private  String rootSchoolFolder;
	private String sharedCalendar;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAcctId() {
		return acctId;
	}
	public void setAppUserId(String appUserId) {
		this.acctId = appUserId;
	}
	public String getKimchiFolder() {
		return KimchiFolder;
	}
	public void setKimchiFolder(String kimchiFolder) {
		KimchiFolder = kimchiFolder;
	}
	public String getRootSharedFolder() {
		return rootSharedFolder;
	}
	public void setRootSharedFolder(String rootSharedFolder) {
		this.rootSharedFolder = rootSharedFolder;
	}
	public String getRootWorksheetFolder() {
		return rootWorksheetFolder;
	}
	public void setRootWorksheetFolder(String rootWorksheetFolder) {
		this.rootWorksheetFolder = rootWorksheetFolder;
	}
	public String getRootShowcaseFolder() {
		return rootShowcaseFolder;
	}
	public void setRootShowcaseFolder(String rootShowcaseFolder) {
		this.rootShowcaseFolder = rootShowcaseFolder;
	}
	public String getSharedCalendar() {
		return sharedCalendar;
	}
	public void setSharedCalendar(String sharedCalendar) {
		this.sharedCalendar = sharedCalendar;
	}
	public String getRootSchoolFolder() {
		return rootSchoolFolder;
	}
	public void setRootSchoolFolder(String rootSchoolFolder) {
		this.rootSchoolFolder = rootSchoolFolder;
	}
	
	
	
	

}
