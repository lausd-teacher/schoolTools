package net.videmantay.server.user;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public abstract class DBObj implements Serializable, Validate {
	
	protected Date createOn;
	protected String createdBy;
	protected Date lastUpdate;
	protected Long versionNum;
	protected transient Set<String> access = new HashSet<String>();
	
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
	public void setVersionNum(Long versionNum) {
		this.versionNum = versionNum;
	}
	public Set<String> getAccess() {
		return access;
	}
	public void setAccess(Set<String> access) {
		this.access = access;
	}
	

}
