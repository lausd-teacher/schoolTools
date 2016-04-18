package net.videmantay.roster.seatingchart;


import com.google.common.html.HtmlEscapers;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.plugins.effects.PropertiesAnimation.Easing;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwtquery.plugins.ui.DroppableUi;
import gwtquery.plugins.ui.UiFunction;
import gwtquery.plugins.ui.interactions.CursorAt;
import gwtquery.plugins.ui.interactions.Draggable;
import gwtquery.plugins.ui.interactions.Droppable;

import java.util.ArrayList;
import static com.google.gwt.query.client.GQuery.*;
import static gwtquery.plugins.ui.Ui.Ui;
import net.videmantay.roster.HasRosterDashboardView;
import net.videmantay.roster.RosterStudentPanel;
import net.videmantay.roster.json.RosterJson;
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
	
	
	@UiField
	AbsolutePanel floorPlan;
	
	@UiField
	MaterialPanel furnitureTools;
	
	@UiField
	MaterialCollection studentList;
	
	//Pull roster from window//
	private final RosterJson roster;
	//action list
	private ArrayList<Action> actions = new ArrayList<Action>();
	
	
	
	private UiFunction dropFurniture = new UiFunction(){
										@Override
										public boolean f(Event e, DroppableUi ui){
											
											
											return true;
										}
										};
	private UiFunction dropStudentOnDesk = new UiFunction(){
											@Override
											public boolean f(Event e,DroppableUi ui){
												
												return true;
											}
											};
	private UiFunction dropStudentOnList = new UiFunction(){
											@Override
											public boolean f(Event e,DroppableUi ui){
												
												return true;
											}
											};
	
	public SeatingChartPanel() {
		//first check for jQuery ui
		if(window.getPropertyJSO("JQuery.ui") == null){
			//load script
			ScriptInjector.fromUrl("/loader.js").setWindow(ScriptInjector.TOP_WINDOW).inject();
		}
		initWidget(uiBinder.createAndBindUi(this));
		roster = $(window).data("classroom", RosterJson.class);
		$(this).css("position", "absolute");
		
	}
	
	@Override
	public void onLoad(){
		$(furnitureTools).css($$("display:'none'"));
	}
	
	public void setData(SeatingChartJson seatingChart){
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
			HTMLPanel deskPanel = Desk.getDouble();
			$(deskPanel).data("desk", data.getFurniture().get(i));
			$(floorPlan).append(deskPanel.getElement())
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
									$(deskPanel).find(".pos"+(j+1))
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
			
			HTMLPanel drop = Desk.getDouble();
			console.log(drop);
			console.log("screenY is " + e.getScreenY() + " client height is " + body.getOffsetHeight() + "px scroll height is " + body.getScrollHeight());
			$(drop).css($$("position:'absolute', width:'11em',height:'5em'"));
			$(drop).css("left",e.getScreenX()  - floorPlan.getAbsoluteLeft()+"px");
			//screen change when drop 
			int top = (body.getScrollHeight() - body.getOffsetHeight()) - (body.getScrollTop());
			$(drop).css("top",e.getScreenY()- top -floorPlan.getAbsoluteTop() + "px");
			//$(floorPlan).append(drop.toString());
			floorPlan.add(drop);
			Draggable.Options dragOpt2 =Draggable.Options.create();
			CursorAt cursorAt = CursorAt.create();
			cursorAt.setTop(10).setLeft(10);
			dragOpt2.containment(".floorPlan").cursorAt(cursorAt);
			$(drop).as(Ui).draggable(dragOpt2).rotatable();
				return true;
			}
		});
	};
	
	public void doneArrangeFurniture(){};

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
