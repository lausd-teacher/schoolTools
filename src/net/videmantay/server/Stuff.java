package net.videmantay.server;

import java.util.ArrayList;
import java.util.List;

import net.videmantay.shared.StuffType;

public class Stuff<T> {

	private List<T> stuff = new ArrayList<T>();
	private StuffType type = StuffType.MESSAGE;
	
	
	
	private Stuff(){
		
	}
	
	public Stuff(List<T> stuff){
		setStuff(stuff);
		this.type = StuffType.LIST;
	}
	
	public Stuff(List<T> list, StuffType type){
		setStuff(list);
		setType(type);
	}
	
	public void setStuff(List<T> stuff){
		this.stuff = stuff;
		this.type = StuffType.LIST;
	}
	
	public Stuff(T thing, StuffType type){
		this.stuff.add(thing);
		this.setType(type);
	}
	
	public Stuff(T thing){
		this.stuff.add(thing);
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
	
		this.stuff.add(t);
	}
	
}
