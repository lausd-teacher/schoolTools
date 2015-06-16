package net.videmantay.server;

import java.util.ArrayList;
import java.util.List;

import net.videmantay.shared.StuffType;

public class Stuff<T> {

	private List<T> stuff;
	private String message;
	private StuffType type = StuffType.MESSAGE;
	
	
	private Stuff(){
		
	}
	
	public Stuff(List<T> stuff){
		setStuff(stuff);
	}
	
	public Stuff(List<T> list, StuffType type){
		setStuff(list);
		setType(type);
	}
	
	public void setStuff(List<T> stuff){
		this.stuff = stuff;
	}
	
	public Stuff(T thing, StuffType type){
		this.stuff = new ArrayList<T>();
		this.stuff.add(thing);
		this.setType(type);
	}
	
	public List<T> getStuff(){
		return this.stuff;
	}
	
	public StuffType getType() {
		return type;
	}

	public void setType(StuffType type) {
		this.type = type;
	}
	
	public void add(T t){
		if(this.stuff == null){
			this.stuff = new ArrayList<T>();
		}
		this.stuff.add(t);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
