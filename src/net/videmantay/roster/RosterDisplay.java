package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;

import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;

import net.videmantay.roster.json.RosterJson;

public class RosterDisplay extends Composite{

	private static RosterDisplayUiBinder uiBinder = GWT.create(RosterDisplayUiBinder.class);

	@UiField
	RosterGrid rosterGrid;
	
	@UiField
	RosterForm rosterForm;
	
	@UiField
	MaterialButton	fab;
	
	ClickHandler fabClick = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
		
		$(fab).hide();
		$(rosterGrid).hide();
		$(rosterForm).show();
		RosterJson roster = RosterJson.createObject().cast();
		rosterForm.edit(roster);
			
		}};
	Function rosterRedraw = new Function(){
		@Override
		public void f(){
			$(rosterForm).hide();
			rosterGrid.drawGrid();
			$(rosterGrid).show();
			$(fab).show();
		}
	};
	Function rosterCancel = new Function(){
		@Override
		public void f(){
			$(rosterGrid).show();
			$(fab).show();
		}
	};
	
	interface RosterDisplayUiBinder extends UiBinder<Widget, RosterDisplay> {
	}

	public RosterDisplay() {
		initWidget(uiBinder.createAndBindUi(this));
		this.setSize("100%", "100%");
	}
	
	@Override
	public void onLoad(){
		
		$(rosterForm).hide();
		fab.addClickHandler(fabClick);
		//handle the form and grid events here
		$(body).on("rosterredraw", rosterRedraw);
		$(body).on("rostercancel", rosterCancel);
		
	}
	
	@Override 
	public void onUnload(){
		//clear up all the events on the bus with off
	}

	
	
}
