package net.videmantay.roster.seatingchart;

import com.google.gwt.dom.client.DivElement;

import net.videmantay.shared.Action;

public abstract class FurnitureAction implements Action {

	protected String id;
	protected DivElement furniture;
	
	@Override
	public abstract void exec();

	@Override
	public abstract void undo();
	
	public  String getId(){
		return this.id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public DivElement getFurniture(){
		return furniture;
	}
	
	public void setFurniture(DivElement furniture){
		this.furniture = furniture;
	}
	
}
