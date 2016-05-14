package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import static com.google.gwt.query.client.GQuery.*;

import com.google.common.primitives.Ints;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSwitch;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.roster.seatingchart.SeatingChartPanel;
import net.videmantay.roster.seatingchart.json.ClassTimeJson;

public class RosterDashboardPanel extends Composite implements ClassroomMain.HasUpdateClassTime {

	private static RosterDashboardPanelUiBinder uiBinder = GWT.create(RosterDashboardPanelUiBinder.class);

	interface RosterDashboardPanelUiBinder extends UiBinder<Widget, RosterDashboardPanel> {
	}

	@UiField
	MaterialSwitch gridSwitch;
	
	@UiField
	MaterialIcon hwBtn;
	
	@UiField
	MaterialIcon seatingChartEditIcon;
	
	@UiField
	HTMLPanel tab1Main;
	
	@UiField
	MaterialRow toolbar;
	
	@UiField
	MaterialRow doneToolbar;
	
	@UiField
	MaterialAnchorButton classtimeBtn;
	@UiField
	MaterialDropDown classtimeDrop;
	
	@UiField
	MaterialLink manageFurnitureLink;
	@UiField
	MaterialLink arrangeStudentsLink;
	@UiField
	MaterialLink manageStationsLink;
	
	private final RosterJson roster =window.getPropertyJSO("roster").cast();

	
	private View view = View.GRID;
	private State state = State.DASHBOARD;
	
	private HasRosterDashboardView display;
	RunAsyncCallback runAsync = new RunAsyncCallback(){

		@Override
		public void onFailure(Throwable reason) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess() {
			display = new SeatingChartPanel();
			tab1Main.clear();
			tab1Main.add(display);
		}};
	//enum for state
	enum View{GRID,CHART};
	enum State{DASHBOARD,ROLL, HW,GROUP,SELECT_ALL,RANDOM}
	
	
	public RosterDashboardPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		switch(view){
		case GRID: showDisplay();break;
		case CHART: showChart();break;
		
		}
		
		doneToolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
		hwBtn.addClickHandler( new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				toolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
				doneToolbar.getElement().getStyle().setDisplay(Style.Display.BLOCK);
				display.checkHW();
				
			}});
	
		gridSwitch.addValueChangeHandler(new ValueChangeHandler<Boolean>(){

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if(view == View.GRID){
					view = View.CHART;
					showChart();
				}else{
					view = View.GRID;
					showDisplay();
				}
			}});
		seatingChartEditIcon.addClickHandler( new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				// update state for furniture edit
				display.arrangeFurniture();
			}});
		classtimeDrop.addSelectionHandler(new SelectionHandler<Widget>(){

			@Override
			public void onSelection(SelectionEvent<Widget> event) {
				MaterialLink link = (MaterialLink)event.getSelectedItem();
				if(link.getText().equalsIgnoreCase("Manage...")){
					//goto classtime management page
				}else{
					int index =Ints.tryParse(link.getDataAttribute("data-index"));
					ClassTimeJson classTime = roster.getClassTimes().get(index);
					window.setPropertyJSO("classtime", classTime);
					classtimeBtn.setText(classTime.getTitle());
				}	
			}});
		
		manageFurnitureLink.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
			 $(".seatingChart").trigger("manageFurniture");
				
			}});
		arrangeStudentsLink.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				$(".seatingChart").trigger("arrangeStudents");
			}});
		manageStationsLink.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				$(".seatingChart").trigger("manageStations");
			}});
		showDisplay();
	}
	
	private void roll(){
		
	}
	
	private void showChart(){
		GWT.runAsync(runAsync);
	}
	private void showDisplay(){
		display = new ClassroomGrid();
		tab1Main.clear();
		tab1Main.add(display); 
	}

	@Override
	public void updateClassTime() {
		switch(state){
		//most likely case is dashboard so should be default
		//classtime only affects groups and which assignments are 
		//listed by default and which seating chart is shown
		case GROUP:display.groups(); break;
		case HW: display.checkHW();break;
		default: display.home(); break;
		}
		
	}
	
	@Override
	public void onLoad(){
		//load the classTimedrop
		
		JsArray<ClassTimeJson> classTimes = roster.getClassTimes();
		
		for(int i = 0; i< classTimes.length(); i++){
			MaterialLink link = new MaterialLink();
			link.setText(classTimes.get(i).getTitle());
			link.setDataAttribute("data-index", ""+i);
			classtimeDrop.add(link);
			if(classTimes.get(i).getIsDefault()){
			classtimeBtn.setText(classTimes.get(i).getTitle());
			window.setPropertyJSO("classtime", classTimes.get(i));
			console.log("... window called from rsosterdassh bord panel for classtime is .... ");
			console.log(window.getPropertyJSO("classtime"));
			}
		}//end for
		
	}

}
