package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.Set;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Serialize;


@Entity
public class SeatingChart implements Serializable {
	

	private static final long serialVersionUID = 2656470746976405698L;
	
	@Id
	public Long id;
	public String title;
	public String descript;
	@Serialize
	public Furniture[] furniture;
	
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
	
	public Long getId() {
		return id;
	}
	
	

}
