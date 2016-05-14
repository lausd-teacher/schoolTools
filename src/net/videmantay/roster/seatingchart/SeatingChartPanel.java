package net.videmantay.roster.seatingchart;


import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.html.Span;
import gwtquery.plugins.ui.DroppableUi;
import gwtquery.plugins.ui.interactions.CursorAt;
import gwtquery.plugins.ui.interactions.Draggable;
import gwtquery.plugins.ui.interactions.Droppable;

import java.util.ArrayList;
import java.util.Stack;

import static com.google.gwt.query.client.GQuery.*;
import static gwtquery.plugins.ui.Ui.Ui;
import net.videmantay.roster.HasRosterDashboardView;
import net.videmantay.roster.RosterEvent;
import net.videmantay.roster.RosterStudentPanel;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.roster.seatingchart.json.ClassTimeJson;
import net.videmantay.roster.seatingchart.json.DeskJson;
import net.videmantay.roster.seatingchart.json.FurnitureJson;
import net.videmantay.roster.seatingchart.json.SeatingChartJson;
import net.videmantay.roster.seatingchart.json.StudentSeatJson;
import net.videmantay.shared.Action;

public class SeatingChartPanel extends Composite implements HasRosterDashboardView {

	private static SeatingChartPanelUiBinder uiBinder = GWT.create(SeatingChartPanelUiBinder.class);

	interface SeatingChartPanelUiBinder extends UiBinder<Widget, SeatingChartPanel> {
	}

	private SeatingChartJson data;
	private JsArray<FurnitureJson> tempFurnitureList;
	
	@UiField
	AbsolutePanel floorPlan;
	
	@UiField
	MaterialPanel furnitureTools;
	
	@UiField
	MaterialCollection studentList;
	
	@UiField
	HTMLPanel seatingChart;
	
	@UiField
	Span emptyFurnitureLabel;
	
	//Pull roster from window//
	private final RosterJson roster;
	
	//action list for undos
	private final Stack<Action> stack = new Stack<Action>();
	
	
	public SeatingChartPanel() {
		//first check for jQuery ui
		if(window.getPropertyJSO("JQuery.ui") == null){
			//load script
			ScriptInjector.fromUrl("/loader.js").setWindow(ScriptInjector.TOP_WINDOW).inject();
		}
		initWidget(uiBinder.createAndBindUi(this));
		roster = window.getPropertyJSO("roster").cast();
		console.log("The roster from seating chart is : ");
		console.log(roster);
	
		$(this).css("position", "absolute");
		
	}
	
	@Override
	public void onLoad(){
		console.log("seating chart onload called");
		//first check if there is a class time if that is null create a temporary one then 
		//create a new temp seating chart.
		$(seatingChart).on(RosterEvent.DRAW_SEATINGCHART, new Function(){
				@Override
				public  boolean f(Event e){
					SeatingChartJson s = window.getPropertyJSO("seatingChart").cast();
					setSeatingChart(s);
					
					return true;
				}
		});
		
		//$(".seatingChart").on("managerFurniture", null);
		
			//check if a seating chart has already been designedated in the window
			ClassTimeJson classTime = window.getPropertyJSO("classtime").cast();
			console.log("The class time from seating chart is...");
			console.log(classTime);
			
				//we are going to see if seating chart has been loaded and if not load
				data = window.getPropertyJSO("seatingChart").cast();
				if(data == null){
					//load from class time
					if(classTime.getId() == null){
						//get create classtime form/////////
						
					}else{
					Ajax.post("/teacher/getseatingchart/" , $$("classTime:" + JsonUtils.stringify(classTime)))
					.done(new Function(){
						@Override
						public void f(){
							SeatingChartJson seatingChartJson = JsonUtils.safeEval((String) this.getArgument(0));
							window.setPropertyJSO("seatingChart", seatingChartJson);
							$(seatingChart).trigger(RosterEvent.DRAW_SEATINGCHART);
							setSeatingChart(seatingChartJson);
						}
					});
					}//end else
				}
			
	}//end onLoad
	
	public void setSeatingChart(SeatingChartJson seatingChart){
		data = seatingChart;
		drawChart();
	}
	
	public void drawChart(){
		
		//make a copy of the student list then pop as they are put in place
		ArrayList<RosterStudentPanel> stuPanels = new ArrayList<RosterStudentPanel>();
		for(int i =0; i < roster.getRosterStudents().length(); i++){
			//might as well setup the studentList here too
			RosterStudentPanel sp =new RosterStudentPanel();
			sp.setData(roster.getRosterStudents().get(i));
			MaterialLink link = new MaterialLink();
			link.add(sp);
			stuPanels.add(sp);
		}
		
		// go through list of furniture and place them on floorPlan
		for(int i =0; i< data.getFurniture().length(); i++){		
			HTMLPanel furniturePanel = FurnitureUtils.byKind(data.getFurniture().get(i).getKind());
			$(furniturePanel).data("furniture", data.getFurniture().get(i));
			$(floorPlan).append(furniturePanel.getElement())
			.css("left", data.getFurniture().get(i).getLeft())
			.css("top", data.getFurniture().get(i).getTop())
			.css("transform", "rotate("+data.getFurniture().get(i).getRotate()+"rad)");
			
			///if this is a desk and it's not empty make a place student of student ids;
			if(data.getFurniture().get(i).getType().equals(FurnitureType.DESK)){
				DeskJson desk = data.getFurniture().get(i).cast();
				JsArray<StudentSeatJson>studentSeats = desk.getSeats();
				for(int j = 0; i < studentSeats.length(); i++){
					if(!studentSeats.get(j).isEmpty()){
						//iterate through students for equal ids
						//then pop to make iterations shorter
						for(RosterStudentPanel rsp : stuPanels){
							if(rsp.getElement().getId().equals(studentSeats.get(j).getRosterStudent().toString())){
									$(furniturePanel).find(".pos"+(j+1))
										.append($(rsp.getElement()).clone());
									
									rsp.setVisible(false);
									//remove ref so list is short to cycle
									stuPanels.remove(rsp);
							}// end if
						}//end for iterate panel and hide ones that seats	
					}//end if
				}//end for j
			}//end if
		}// end for i///	
		//the rest of students panel go in the sideNav
		if(stuPanels.size() > 0){
			//place the rest in side
			for(RosterStudentPanel rsp : stuPanels){
			MaterialLink link = new MaterialLink();
			link.add(rsp);
			studentList.add(link);
			}//end for
		}//end if
		
		//everything should be in place now add home state
		home();
	}	
	

	@Override
	public void checkHW() {
		
		
	}

	@Override
	public void groups() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void takeRoll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void home() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pickRandom() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneCheckHW() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneGroups() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneTakeRoll() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void donePickRandom() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void multipleSelect(){
		
	}
	@Override
	public void doneMultipleSelect(){
		
	}
	@Override
	public void doneSelectAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deselectAll() {
		// TODO Auto-generated method stub
		
	}
	
	public  void arrangeStudents(){};
	
	public void doneArrangeStudents(){};
	
	public  void arrangeFurniture(){
		
		tempFurnitureList = JsArray.createArray().cast();
		for(int i = 0; i < data.getFurniture().length(); i++){
			tempFurnitureList.push(data.getFurniture().get(i));
		}
		
		$(furnitureTools).as(Effects).clipAppear( );
		
		Draggable.Options dragOpts = Draggable.Options.create();
	    
		dragOpts.zIndex(500)
		.appendTo(body)
		.cursorAt(CursorAt.create())
		.revert("invalid")
		.containment(".seatingChart")
		.helper("clone");
		
		$("img.furnitureIcon").as(Ui).draggable(dragOpts);
		
		Droppable.Options dropOpts = Droppable.Options.create();
		dropOpts.accept(".furnitureIcon")
		.hoverClass("floorplan-hover");
		$(floorPlan).as(Ui).droppable(dropOpts).bind(Droppable.Event.drop, new Function(){
			@Override
			public boolean f(Event e,Object...ui){
			DroppableUi dropUi = (DroppableUi) ui[0];
			//get the kind from the droppable helper
			String iconId = dropUi.draggable().get().getId();
			HTMLPanel drop = FurnitureUtils.byIconId(iconId);
			$(drop).css($$("position:'absolute', width:'8em',height:'4em'"));
			String left = e.getClientX()  - floorPlan.getAbsoluteLeft()+ body.getScrollLeft()+"px";
			String top = e.getClientY() - floorPlan.getAbsoluteTop()+ body.getScrollTop() + "px";
			$(drop).css("left",left);
			$(drop).css("top",top );
			floorPlan.add(drop);
			
			
			stack.push(new FurnitureAddAction(drop, tempFurnitureList));
			Draggable.Options dragOpt2 =Draggable.Options.create();
			CursorAt cursorAt = CursorAt.create();
			cursorAt.setTop((int) Math.floor(drop.getOffsetHeight()/2)).setLeft((int)Math.floor(drop.getOffsetWidth()/2));
			dragOpt2.containment(".floorPlan").cursorAt(cursorAt);
			$(drop).as(Ui).draggable(dragOpt2).rotatable();
				return true;
			}
		});
	};
	
	public void doneArrangeFurniture(){
		//here we need a way to keep track of temporary furniture
		//and then finalize it.
		
		
	};
	
	public void onCancel(){
		stack.clear();
		
	}

	public void deleteFurniture(DivElement furniture){
		//first check to see if it's a desk and if the desk has students
		//if so these student must be put in the studentList alphabetically??
		if((furniture).hasClassName(".desk")){
			GQuery rosStu = $(furniture).find(".rosterStudent");
			if(rosStu.hasClass("rosterStudent")){
				MaterialLink link = new MaterialLink();
				$(link.getElement()).append(rosStu);
				studentList.add(link);
			}
		}
		$(furniture).data("desk",null);
		$(furniture).remove();
	}


}
