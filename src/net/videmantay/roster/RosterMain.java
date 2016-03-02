package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.query.client.Function;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;

public class RosterMain extends Composite {

	
	private static RosterMainUiBinder uiBinder = GWT.create(RosterMainUiBinder.class);

	interface RosterMainUiBinder extends UiBinder<Widget, RosterMain> {
	}
	
	public interface Display extends IsWidget{
		public void fab();
		
	}
	private Display display;
	
	@UiField
	MaterialContainer main;
	
	@UiField
	MaterialButton	fab;
	
	@UiField
	MaterialLink rosterLink;
	
	@UiField
	MaterialLink calendarLink;
	
	@UiField
	MaterialLink libraryLink;
	
	@UiField
	MaterialLink lessonsLink;
	
	@UiField
	MaterialTooltip calendarTooltip;
	
	@UiField
	MaterialTooltip todoTooltip;
	
	@UiField
	MaterialTooltip notificationTooltip;

	ClickHandler fabClick = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
		display.fab();
		$(fab).hide();
			
		}};
	
	public RosterMain() {
		initWidget(uiBinder.createAndBindUi(this));
		this.rosters();
	}
	
	@Override 
	public void onLoad(){
		fab.addClickHandler(fabClick);
		
	}
	
	public void rosters(){
		main.clear();
		setDisplay(new RosterDisplay());
	}
	
	private void setDisplay(Display display){
		this.display = display;
		main.add(display);
	}

}
