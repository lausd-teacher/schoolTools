package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Properties;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import static com.google.gwt.query.client.GQuery.*;


import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import net.videmantay.roster.json.Course;
import net.videmantay.roster.seatingchart.json.SeatingChartJson;

public class DashboardPanel extends Composite {

	private static DashboardPanelUiBinder uiBinder = GWT.create(DashboardPanelUiBinder.class);

	interface DashboardPanelUiBinder extends UiBinder<Widget, DashboardPanel> {
	}

	@UiField
	MaterialAnchorButton editBtn;
	
	@UiField
	MaterialLink editLink;
	
	@UiField
	MaterialLink newLink;
	
	@UiField
	MaterialLink deleteLink;
	
	@UiField
	MaterialLink printLink;
	
	@UiField
	MaterialIcon hwIcon;
	
	@UiField
	MaterialIcon groupsIcon;
	
	@UiField
	MaterialIcon rollIcon;
	
	@UiField
	MaterialIcon procIcon;
		
	@UiField
	HTMLPanel mainPanel;
	
	@UiField
	MaterialRow toolbar;
	
	@UiField
	MaterialRow doneToolbar;
	
	@UiField
	MaterialAnchorButton routineBtn;
		
	@UiField
	MaterialDropDown routineDrop;
	
	@UiField
	MaterialAnchorButton doneBtn;
	

	
	@UiField
	MaterialAnchorButton doneBarCancelBtn;
	
	
	
	@UiField
	MaterialAnchorButton clearAllBtn;
	
	@UiField
	ImageElement sectionProfIcon;
	
	@UiField
	SpanElement sectionTitleSpan;
	
	@UiField
	MaterialModal createSeatingChartModal;
	
	@UiField
	MaterialModal deleteSeatingChartModal;
	
	@UiField
	MaterialTextBox titleInput;
	
	@UiField
	MaterialTextArea descriptInput;
	
	@UiField
	MaterialCheckBox defaultInput;
	

	private State state = State.DASHBOARD;
	
	private SeatingChartPanel display = new SeatingChartPanel();

	public enum State{DASHBOARD,GROUPS,PROCEEDURES,STATIONS}
	
	private SeatingChartOptions options = new SeatingChartOptions();
	
	//constructor
	public DashboardPanel() {
		console.log("roster passed to dashboard is :  ");
		initWidget(uiBinder.createAndBindUi(this));
		doneToolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
		
		//ajax call to other seating chart and assign to window.curChart
		routineDrop.addSelectionHandler(new SelectionHandler<Widget>(){

			@Override
			public void onSelection(SelectionEvent<Widget> event) {
				MaterialLink link = (MaterialLink)event.getSelectedItem();
					int index =Integer.parseInt(link.getDataAttribute("data-index"));
				
			}});
		/////////////////seatingChartEditLinks events
	
		
		////// toolbar buttons events//////////////////////
		hwIcon.addClickHandler( new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				display.checkHW();
				showDoneBar();
			}});
		rollIcon.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				display.takeRoll();
				showDoneBar();
			}});
		
		editLink.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				display.edit();
				showDoneBar();
				
			}});
		newLink.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				event.stopPropagation();
				event.preventDefault();
				createSeatingChartModal.open();
			}});
		deleteLink.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				event.stopPropagation();
				event.preventDefault();
				deleteSeatingChartModal.open();
			}});
	
	
		////////////
		doneBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				
				display.home();
				showToolBar();
			}});
	
		
		clearAllBtn.addClickHandler(new ClickHandler(){

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
		
			
	}//end constructor
	
	private void showCreateChartForm() {
		console.log("show create form called");
		$("div.create-option-form").each(new Function() {
			@Override
			public void f() {
				GQuery $this = $(this);
				if($this.id().equalsIgnoreCase("infoOption")) {
					$this.show();
				}else {
					$this.hide();
				}
			}
		});
	}
	
	
	private void chooseDeskType() {
		
	}
	
private void submitCreateForm() {
		options.title = titleInput.getValue();
		options.descript = descriptInput.getValue();
		options.isDefault = defaultInput.getValue();
		
		$("div.create-option-form").each(new Function() {
			@Override
			public void f() {
				GQuery $this = $(this);
				if($this.id().equalsIgnoreCase("chartOption")) {
					$this.show();
				}else {
					$this.hide();
				}
			}
		});
		
		createSeatingChartModal.close();
		display.manageNewChart(options);
	}
	
	private void cancelCreateForm() {
		
	}
	
	private void showDoneBar(){
		toolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
		doneToolbar.getElement().getStyle().setDisplay(Style.Display.BLOCK);
	}
	private void showToolBar(){
		doneToolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
		toolbar.getElement().getStyle().setDisplay(Style.Display.BLOCK);
	}
	


	@Override
	public void onLoad(){
		//load the classTimedrop
		Course course = CONST.course();
		sectionProfIcon.setSrc(course.section().get(0).getProfileURL());
		sectionTitleSpan.setInnerText(course.section().get(0).getCourse_title());
		console.log("dashboard loaded");
		SeatingChartJson scj = window.getPropertyJSO("curChart").cast();
		mainPanel.add(display);
		display.setSeatingChart(scj);
		
		Function createOptFunc = new Function(){
			@Override
			public boolean f(Event e) {
				e.stopPropagation();
				e.preventDefault();
				String id = $(e.getEventTarget()).id();
				console.log(id);
				switch(id) {
				case"dublicate-create-option":options.chartType = "duplicate";
											  showCreateChartForm();
				                               break;
				/*case"row-create-option": createOption[0] = "row";
				                          chooseDeskType();break;
				case"group-create-option":createOption[0] = "group";
										chooseDeskType();break;*/
				default: options.chartType = "blank";
						showCreateChartForm();
				}//end switch
				
				return true;
			}
		};
		
		$("div.create-option").click(createOptFunc);
		
		
	}

}