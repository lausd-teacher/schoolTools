package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Display;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSwitch;
import net.videmantay.roster.seatingchart.SeatingChartPanel;

public class RosterDashboardPanel extends Composite {

	private static RosterDashboardPanelUiBinder uiBinder = GWT.create(RosterDashboardPanelUiBinder.class);

	interface RosterDashboardPanelUiBinder extends UiBinder<Widget, RosterDashboardPanel> {
	}

	@UiField
	MaterialSwitch gridSwitch;
	
	@UiField
	MaterialIcon hwBtn;
	
	@UiField
	HTMLPanel tab1Main;
	
	@UiField
	MaterialLink furnitureSetLink;
	
	@UiField
	MaterialRow toolbar;
	
	@UiField
	MaterialRow doneToolbar;
	
	private View view;
	private State state;
	private HasRosterDashboardView display;
	//enum for state
	enum View{GRID,CHART};
	enum State{DASHBOARD,ROLL, HW,GROUP,SECTION,SELECT_ALL,RANDOM}
	
	
	public RosterDashboardPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		display = new SeatingChartPanel();
		tab1Main.add(display);
		doneToolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
		hwBtn.addClickHandler( new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				toolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
				doneToolbar.getElement().getStyle().setDisplay(Style.Display.BLOCK);
				display.checkHW();
				
			}});
		
		furnitureSetLink.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				display.arrangeFurniture();
				
			}});
	}
	
	private void roll(){
		
	}

}
