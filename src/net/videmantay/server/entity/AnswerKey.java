package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;


@Entity
public abstract class AnswerKey implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5305068704166594004L;
	
	
	@Id
	private Long id;
	private Date createOn;
	private String createdBy;
	private Date lastUpdate;
	private Long versionNum;
	abstract void score(QuizQuestion question);
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
	public Long getId() {
		return id;
	}
	

}
