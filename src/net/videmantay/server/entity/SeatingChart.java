package net.videmantay.server.entity;

import java.io.Serializable;
import java.util.ArrayList;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Serialize;

import net.videmantay.server.user.Roster;


@Entity
public class SeatingChart implements Serializable {
	

	private static final long serialVersionUID = 2656470746976405698L;
	
	@Id
	public Long id;
	
	@Parent
	public transient Key<Roster> rosterKey;
	
	@Serialize
	public ArrayList<Furniture> furniture = new ArrayList<Furniture>();

	public Long getId() {
		return id;
	}
	
	public Key<SeatingChart> getKey(Key<Roster> rosterKey){
		return Key.create(rosterKey, SeatingChart.class, id);
	}
	
	

}
