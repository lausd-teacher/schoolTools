package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import static com.google.gwt.query.client.GQuery.*;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSwitch;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.student.json.RosterStudentJson;

public class DashboardPanel extends Composite {

	private static DashboardPanelUiBinder uiBinder = GWT.create(DashboardPanelUiBinder.class);

	interface DashboardPanelUiBinder extends UiBinder<Widget, DashboardPanel> {
	}

	
	@UiField
	MaterialSwitch gridSwitch;
	
	@UiField
	MaterialIcon procIcon;
	
	@UiField
	MaterialIcon groupsIcon;
	
	@UiField
	MaterialIcon stationsIcon;
	
	@UiField
	MaterialIcon rollIcon;
	
	@UiField
	MaterialIcon multipleIcon;
	
	@UiField
	MaterialIcon randomIcon;
	
	@UiField
	MaterialIcon seatingChartEditIcon;
	
	@UiField
	MaterialContainer tab1Main;
	
	@UiField
	MaterialRow toolbar;
	
	@UiField
	MaterialRow doneToolbar;
	
	@UiField
	MaterialAnchorButton routineBtn;
	
	@UiField
	MaterialAnchorButton routineBar;
	
	@UiField
	MaterialDropDown routineDrop;
	
	@UiField
	MaterialDropDown routineDrop2;
	
	@UiField
	MaterialAnchorButton doneBtn;
	
	@UiField
	MaterialButton smDoneBtn;
	
	@UiField
	MaterialAnchorButton doneBarCancelBtn;
	
	@UiField
	MaterialButton smDoneBarCancelBtn;
	
	@UiField
	MaterialButton undoBtn;
	
	@UiField
	MaterialButton smUndoBtn;
	
	private  final StudentActionModal studentActionModal = new StudentActionModal();
	
	private final RosterJson roster;

	
	private View view = View.GRID;
	private State state = State.DASHBOARD;
	
	private HasRosterDashboardView display;

	final RunAsyncCallback runAsync = new RunAsyncCallback(){

		@Override
		public void onFailure(Throwable reason) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess() {
			tab1Main.clear();
			display = new SeatingChartPanel(roster);
			tab1Main.add(display);
		}};
		
	private final Function doneEditFunc = new Function(){
		@Override
		public void f(){
			showToolBar();
		}
	};
	//enum for state
	public enum View{GRID,CHART};
	public enum State{DASHBOARD,ROLL, HW, MULTIPLE_SELECT,RANDOM, FURNITURE_EDIT, STUDENT_EDIT, STATIONS_EDIT}
	public boolean procView = false;
	public boolean groupView = false;
	public boolean stationView = false;
	
	
	//constructor
	public DashboardPanel(RosterJson ros) {
		this.roster = ros;
		console.log("roster passed to dashboard is :  ");
		console.log(roster);
		initWidget(uiBinder.createAndBindUi(this));
		switch(view){
		case GRID: showDisplay();break;
		case CHART: showChart();break;
		
		}
		
		doneToolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
		
	
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
		
		routineDrop.addSelectionHandler(new SelectionHandler<Widget>(){

			@Override
			public void onSelection(SelectionEvent<Widget> event) {
				MaterialLink link = (MaterialLink)event.getSelectedItem();
				if(link.getText().equalsIgnoreCase("Manage...")){
					//goto classtime management page
				}else{
					int index =Integer.parseInt(link.getDataAttribute("data-index"));
					

				}	
			}});
		/////////////////seatingChartEditLinks events
	
		seatingChartEditIcon.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				((SeatingChartPanel)display).edit();
				toolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
			}});
		////// toolbar buttons events//////////////////////
		rollIcon.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				display.takeRoll();
				state = State.ROLL;
				showDoneBar();
			}});
		
		procIcon.addClickHandler( new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				display.checkHW();
				if(procView){
					display.doneProcedures();
					procView = false;
				}else{
					display.procedures();
					procView = true;
				}
			}});
		
		groupsIcon.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if(groupView){
					display.doneGroups();
				groupView = false;}
				else{
					display.groups();
					groupView = true;
				}
			}});
		stationsIcon.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if(stationView){
					stationView = false;
					display.doneStations();
				}else{
					stationView = true;
					display.stations();
				}
				
			}});
		randomIcon.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				display.pickRandom();
				state = State.RANDOM;
				showDoneBar();
			}});
		multipleIcon.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				display.multipleSelect();
				state = State.MULTIPLE_SELECT;
				showDoneBar();
			}});
		////////////
		doneBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				switch(state){
				case FURNITURE_EDIT:display.doneArrangeFurniture(); break;
				case STUDENT_EDIT: display.doneArrangeStudents(); break;
				case STATIONS_EDIT: display.doneManageStations();break;
				case HW: display.doneCheckHW();break;
				case ROLL: display.doneTakeRoll();break;
				case RANDOM: display.donePickRandom(); break;
				case MULTIPLE_SELECT: display.doneMultipleSelect(); break;
				default: display.home();
				}
				display.home();
				showToolBar();
			}});
		smDoneBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				switch(state){
				case FURNITURE_EDIT:display.doneArrangeFurniture(); break;
				case STUDENT_EDIT: display.doneArrangeStudents(); break;
				case STATIONS_EDIT: display.doneManageStations();break;
				case HW: display.doneCheckHW();break;
				case ROLL: display.doneTakeRoll();break;
				case RANDOM: display.donePickRandom(); break;
				case MULTIPLE_SELECT: display.doneMultipleSelect(); break;
				default: display.home();
				}
				display.home();
				showToolBar();
			}});
		
		undoBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				display.undo();
				
			}});
		
		smUndoBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				display.undo();
				
			}});
		doneBarCancelBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				display.cancel(state.name());
				display.home();
				showToolBar();
			}});
		
		smDoneBarCancelBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				display.cancel(state.name());
				display.home();
				showToolBar();
			}});
		
		showDisplay();
	}
	
	private void showDoneBar(){
		toolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
		doneToolbar.getElement().getStyle().setDisplay(Style.Display.BLOCK);
	}
	private void showToolBar(){
		doneToolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
		toolbar.getElement().getStyle().setDisplay(Style.Display.BLOCK);
	}
	private void showChart(){
		GWT.runAsync(runAsync);
		seatingChartEditIcon.setVisible(true);
	}
	private void showDisplay(){
		display = new ClassroomGrid(roster);
		tab1Main.clear();
		tab1Main.add(display); 
		seatingChartEditIcon.setVisible(false);
	}

	
	
	@Override
	public void onLoad(){
		
		//load the classTimedrop
		console.log("dashboard loaded");
		$(body).on("studentAction", new Function(){
			public boolean f(Event e, Object...studen){
				RosterStudentJson student = (RosterStudentJson) studen[0];
				showStudentAction(student);
				return true;
			}
		});
		
		$("#dashboard").on("doneEdit", doneEditFunc);
		
		RootPanel.get().add(studentActionModal);
	}//end onLoad
	
	@Override
	public void onUnload(){
		RootPanel.get().remove(studentActionModal);
	}
	
	private void showStudentAction(RosterStudentJson student){
		console.log("Student action modal should be open");
		console.log("student id  is: " + student.getAcct());
	
			studentActionModal.setData(student);
		
		studentActionModal.modal.open();
		GQuery $overlay = $("div.lean-overlay");
		if($overlay.size() > 1){
			int i = $overlay.size();
			while( i > 1){
				$overlay.get(i).removeFromParent();
				--i;
			}// end while
		}
	}

}
