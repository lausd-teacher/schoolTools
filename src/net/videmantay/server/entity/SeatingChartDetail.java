package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.Date;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;


@Entity
public class SeatingChartDetail implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2845019435811983254L;
	
	
	@Id
	public Long id;
	public String title;
	public String descript;
	public Date lastUpdate;
	//have html svg converter so that
	// we can have a thumbnail of the seatingChart
	public String thumbnailUrl;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	public Long getId() {
		return id;
	}
	

}
