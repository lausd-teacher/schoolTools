package net.videmantay.roster.seatingchart;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.UIObject;

public class Desk extends UIObject {
	
	

	private static DeskUiBinder uiBinder = GWT.create(DeskUiBinder.class);

	interface DeskUiBinder extends UiBinder<Element, Desk> {
	}
	
	@UiField
	static DivElement doubleDesk;
	
	@UiField
	static DivElement singleDesk;
	
	@UiField
	static DivElement kidneyTable;
	
	@UiField
	static DivElement carpetFull;
	
	@UiField
	static DivElement carpetSm;

	public Desk() {
		setElement(uiBinder.createAndBindUi(this));
	}
	
	public static DivElement draw(String type){
		switch(type){
		case FurnitureKind.DOUBLE_DESK: return getDouble();
		case FurnitureKind.CARPET:return getCarpet();
		default: return getDouble();
		}
	}
	
	public static DivElement getDouble(){
		return doubleDesk.cloneNode(true).cast();
	}
	
	public static DivElement getSingle(){
		return singleDesk.cloneNode(true).cast();
	}
	
	public static DivElement getKidney(){
		return kidneyTable.cloneNode(true).cast();
	}
	
	public static DivElement getCarpet(){
		return carpetFull.cloneNode(true).cast();
	}
	
	public static DivElement getCarpetSm(){
		return carpetSm.cloneNode(true).cast();
	}
	
	

}
