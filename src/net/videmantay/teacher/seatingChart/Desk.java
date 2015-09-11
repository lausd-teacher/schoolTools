package net.videmantay.teacher.seatingChart;

import java.util.Arrays;

import com.google.gwt.core.client.JavaScriptObject;


class Desk extends JavaScriptObject{
	protected Desk(){};
	public final native Desk create()/*-{
		return {seats: new Array(),
				type:'',
				rotation: '', 
				left:'',
				top:''};
	}-*/;
	
	public final native String getType()/*-{
		return this.type;
	}-*/;
	
	public final native Seat[] getSeats()/*-{
		return this.seats;
	}-*/;
	
	public final native String getBackgroundImg()/*-{
		return this.backgroundImg;
	}-*/;
	
	public final native String getRotation()/*-{
		return this.rotation;
	}-*/;
	public final native String getLeft()/*-{
		return this.left;
	}-*/;
	public final native String getTop()/*-{
		return this.top;
	}-*/;
	
	public final native Desk setType(String type)/*-{
		this.type = type;
		switch(type){
		case 'double':this.@net.videmantay.teacher.seatingChart.Desk::createDouble();break;
		case 'kidney':this.@net.videmantay.teacher.seatingChart.Desk::createKidney();break;
		case 'carpetSm':this.@net.videmantay.teacher.seatingChart.Desk::createCarpetSm();break;
		};
	}-*/;
	
	public final native Desk setRotation(String rot)/*-{
		this.rotation = rot;
		return this;
	}-*/;
	
	public final native Desk setLeft(String left)/*-{
	this.left = left;
	return this;
}-*/;
	
	public final native Desk setTop(String top)/*-{
	this.top = top;
	return this;
}-*/;
	
	
	
	public Seat getSeat(int posistion){
		return this.getSeats()[posistion - 1];
	}
	private  Desk addSeat(Seat seat){
		Arrays.asList(this.getSeats()).add(seat);
		return this;
	}
	
	private Desk removeSeat(Seat seat){
		Arrays.asList(this.getSeats()).remove(seat);
		return this;
	}
	
	private Desk createDouble(){
		int twice = 0;
		while(twice < 2){
			Seat seat = Seat.create();
			seat.setPosition(twice + 1); 
			this.addSeat(seat);
			twice++;
		}//end while
		
		return this;
	}
	
	private Desk createKidney(){
		
		return this;
	}
	
	private Desk createCarpetSm(){
	
	return this;
}

}
