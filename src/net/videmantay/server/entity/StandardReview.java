package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;


@Entity
public class StandardReview implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8102253140861257292L;
	
	@Id
	private Long id;
	private Long ccStandard;
	private List<Long> links;
	public Long getCcStandard() {
		return ccStandard;
	}
	public void setCcStandard(Long ccStandard) {
		this.ccStandard = ccStandard;
	}
	public List<Long> getLinks() {
		return links;
	}
	public void setLinks(List<Long> links) {
		this.links = links;
	}
	public Long getId() {
		return id;
	}
	

}
