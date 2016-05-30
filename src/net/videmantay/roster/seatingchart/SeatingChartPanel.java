package net.videmantay.roster.seatingchart;


import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.html.Div;
import gwt.material.design.client.ui.html.Span;
import gwtquery.plugins.ui.DroppableUi;
import gwtquery.plugins.ui.RotatableUi;
import gwtquery.plugins.ui.interactions.CursorAt;
import gwtquery.plugins.ui.interactions.Draggable;
import gwtquery.plugins.ui.interactions.Droppable;
import gwtquery.plugins.ui.interactions.Rotatable;

import java.util.ArrayList;
import java.util.Stack;

import static gwtquery.plugins.ui.Ui.Ui;
import net.videmantay.roster.HasRosterDashboardView;
import net.videmantay.roster.RosterEvent;
import net.videmantay.roster.RosterStudentPanel;
import net.videmantay.roster.RosterUrl;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.roster.seatingchart.json.ClassTimeJson;
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
	Div floorPlan;
	
	@UiField
	MaterialPanel furnitureTools;
	
	@UiField
	MaterialPanel studentTools;
	
	@UiField
	MaterialCollection studentList;
	
	@UiField
	HTMLPanel seatingChart;
	
	@UiField
	Span emptyFurnitureLabel;
	
	private ClassTimeForm classtimeForm = new ClassTimeForm();
	
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
	
		
		
	}
	
	@Override
	public void onLoad(){
		console.log("seating chart onload called");
		//first check if there is a class time if that is null create a temporary one then 
		//create a new temp seating chart.
		/*$(seatingChart).on(RosterEvent.DRAW_SEATINGCHART, new Function(){
				@Override
				public  boolean f(Event e){
					e.stopPropagation();
					SeatingChartJson s = window.getPropertyJSO("seatingChart").cast();
					setSeatingChart(s);
					
					return true;
				}
		});*/
		
		//$(".seatingChart").on("managerFurniture", null);
		
			//check if a seating chart has already been designedated in the window
			ClassTimeJson classTime = window.getPropertyJSO("classtime").cast();
			console.log("The class time from seating chart is...");
			console.log(classTime);
			
				//we are going to see if seating chart has been loaded and if not load
				data = window.getPropertyJSO("seatingChart").cast();
				console.log("data in seatingchart panel is Null? ");
				console.log(data);
				if(data == null){
					console.log("if data null called");
					//load from class time
					if(classTime == null || classTime.getId() == null){
						console.log("If classtime id is null called");
						//get create classtime form/////////
					classtimeForm.classtimeModal.openModal();
						
					}else{
						console.log("seating chart should make a network call here");
						Properties prop = Properties.create();
						prop.set("classTime", JsonUtils.stringify(classTime));
						prop.set("roster", roster.getId());
					Ajax.get(RosterUrl.GET_SEATINGCHART, prop)
					.done(new Function(){
						@Override
						public void f(){
							SeatingChartJson seatingChartJson = JsonUtils.safeEval((String) this.getArgument(0));
							window.setPropertyJSO("seatingChart", seatingChartJson);
							setSeatingChart(seatingChartJson);
						}
					});
					}//end else
				}else{
					drawChart();
				}
		seatingChart.add(classtimeForm);	
		
	}//end onLoad
	
	public void setSeatingChart(SeatingChartJson seatingChart){
		data = seatingChart;
		drawChart();
	}
	
	public void drawChart(){
		console.log("Roster students is ");
		console.log(roster.getRosterStudents());
		//make a copy of the student list then pop as they are put in place
		ArrayList<RosterStudentPanel> stuPanels = new ArrayList<RosterStudentPanel>();
		for(int i =0; i < roster.getRosterStudents().length(); i++){
			//might as well setup the studentList here too
			RosterStudentPanel sp =new RosterStudentPanel();
			sp.setData(roster.getRosterStudents().get(i));
			/*MaterialLink link = new MaterialLink();
			link.add(sp);*/
			stuPanels.add(sp);
		}
		console.log("We've cycled through students here is array of panels ");
		console.log(stuPanels);
		// go through list of furniture and place them on floorPlan
		if(data.getFurniture() != null && data.getFurniture().length() > 0){
		for(int i =0; i< data.getFurniture().length(); i++){
			console.log("draw(): furniture kind is ");
			console.log(data.getFurniture().get(i).getKind());
			//Gquery only operates on DOM so make and place 
			HTMLPanel furniturePanel = FurnitureUtils.byKind(data.getFurniture().get(i).getKind());
			floorPlan.add(furniturePanel);
			
			final FurnitureJson furniture = data.getFurniture().get(i);
			
			$(furniturePanel).data("desk", furniture);
			$(furniturePanel).css("top",furniture.getTop());
			$(furniturePanel).css("left", furniture.getLeft());
			//now spin the desk
			$(furniturePanel.getElement()).find(".desk")
			.css("transform", furniture.getTransform());
			
			///if this is a desk and it's not empty make a place student of student ids;
			if(data.getFurniture().get(i).getType().equals(FurnitureType.DESK)){
				JsArray<StudentSeatJson>studentSeats = data.getFurniture().get(i).getSeats();
				for(int j = 0; j < studentSeats.length(); j++){
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
		
		}else{// furniture array is null so make a new one
			JsArray<FurnitureJson> furniture = JsArray.createArray().cast();
			data.setFurniture(furniture);
			
			//the rest of students panel go in the sideNav
			console.log("We finished with drawn seats and mathcing student id's lis of array is ");
			console.log(stuPanels);
			if(stuPanels.size() > 0){
				//place the rest in side
				for(RosterStudentPanel rsp : stuPanels){
				MaterialLink link = new MaterialLink();
				link.add(rsp);
				studentList.add(link);
				}//end for
				console.log("Here is the student collection ");
				console.log(studentList);
			}//end if
		}
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
	
	public  void arrangeStudents(){
		$(studentTools).css("display","block");
		
	};
	
	public void doneArrangeStudents(){
		$(studentTools).css("display","none");
		SeatingChartJson seatingChart = window.getPropertyJSO("seatingChart").cast();
		console.log("This is the data sent by done arrage furniture: " +  JsonUtils.stringify(seatingChart));
		Ajax.post(RosterUrl.UPDATE_SEATINGCHART, $$("seatingChart:" + JsonUtils.stringify(seatingChart)));
	};
	
	public  void arrangeFurniture(){
		
		$(furnitureTools).css($$("display: block"));
		
		tempFurnitureList = JsArray.createArray().cast();
		if(data.getFurniture().length() > 0){
		for(int i = 0; i < data.getFurniture().length(); i++){
			tempFurnitureList.push(data.getFurniture().get(i));
		}
		}//end if
		
	//make icons draggable and floorplan drop target
		Draggable.Options dragOpts = Draggable.Options.create();
	    
		dragOpts.zIndex(500)
		.appendTo(body)
		.cursorAt(CursorAt.create())
		.revert("invalid")
		.containment(".seatingChart")
		.helper("clone");
		
		$("img.furnitureIcon").as(Ui).draggable(dragOpts);
		
		Droppable.Options dropOpts = Droppable.Options.create();
		dropOpts.accept(".furnitureIcon").greedy(true);
		$(floorPlan.getElement()).as(Ui).droppable(dropOpts).on(Droppable.Event.drop, new Function(){
			@Override
			public boolean f(Event e,Object...ui){
				e.stopPropagation();
				e.preventDefault();
			DroppableUi dropUi = (DroppableUi) ui[0];
			//get the kind from the droppable helper
			String iconId = dropUi.draggable().get().getId();
			console.log("droppable iconId is : " + iconId);
			//html rep of desk
			final HTMLPanel drop = FurnitureUtils.byIconId(iconId);
			//object rep of desk
			//we should have a furniture object here////////
			final FurnitureJson desk = FurnitureJson.createObject().cast();
			desk.setSeats(iconId);
			console.log(desk);
			
			String left = e.getClientX()  - floorPlan.getAbsoluteLeft()+ body.getScrollLeft()+"px";
			String top = e.getClientY() - floorPlan.getAbsoluteTop()+ body.getScrollTop() + "px";
			//added to browser
			floorPlan.add(drop);
			$(drop).css("left",left);
			$(drop).css("top",top );
			//push to temp list
			desk.setLeft($(drop).css("left"));
			desk.setTop($(drop).css("top"));
			tempFurnitureList.set(tempFurnitureList.length(), desk);
			
			//push data to desk
			$(drop).data("desk", desk);
			
			console.log("The furniture list array");
			console.log(tempFurnitureList);
			
			
			//stack.push(new FurnitureAddAction(drop,(JsArray<FurnitureJson>) tempFurnitureList));
			console.log(stack);
			Draggable.Options dragOpt2 =Draggable.Options.create();
			CursorAt cursorAt = CursorAt.create();
			cursorAt.setTop((int) Math.floor(drop.getOffsetHeight()/2)).setLeft((int)Math.floor(drop.getOffsetWidth()/2));
			dragOpt2.containment(".floorPlan").cursorAt(cursorAt)
			.stop(new Function(){
				@Override
				public boolean f(Event e, Object...o){
					console.log(desk);
					desk.setLeft($(drop).css("left"));
					desk.setTop($(drop).css("top"));
					
					return true;
				}
			});
			
			Rotatable.Options rotOpt = Rotatable.Options.create();
			rotOpt.stop(new Function(){
				@Override
				public boolean f(Event e, Object...o){
					RotatableUi ui = (RotatableUi)o[0];
					desk.setRotate(ui.angle().current());
					desk.setTransform($(drop).find(".desk").css("transform"));
					console.log(desk);
					return true;
				}
			});
			$(drop.getElement()).as(Ui).draggable(dragOpt2);
			$(drop.getElement()).find(".desk").as(Ui).rotatable(rotOpt);
				return true;
			}
		});
		
		//make furniture on floorPlan draggable and rotatable and deletable
		$(".desk-wrapper", floorPlan).each(new Function(){
			@Override
			public void f(){
				final GQuery $this = $(this);
				final FurnitureJson thisDesk = $this.data("desk");
				Draggable.Options dragOpt3 =Draggable.Options.create();
				CursorAt cursorAt = CursorAt.create();
				cursorAt.setTop((int) Math.floor($this.height()/2)).setLeft((int)Math.floor($this.width()/2));
				dragOpt3.containment(".floorPlan").cursorAt(cursorAt)
				.stop(new Function(){
					@Override
					public boolean f(Event e, Object...o){
						thisDesk.setTop($this.css("top"));
						thisDesk.setLeft($this.css("left"));
						console.log(thisDesk);
						return true;
					}
				});
				
				Rotatable.Options rotOpt2 = Rotatable.Options.create();
				console.log("rotate of desk is " + thisDesk.getRotate());
				
				rotOpt2.angle(thisDesk.getRotate()).stop(new Function(){
					@Override
					public boolean f(Event e, Object...o){
						RotatableUi ui = (RotatableUi)o[0];
						thisDesk.setRotate(ui.angle().current());
						thisDesk.setTransform($this.find(".desk").css("transform"));
						console.log(thisDesk);
						return true;
					}
				});
				$this.as(Ui).draggable(dragOpt3);
				$this.find(".desk").as(Ui).rotatable(rotOpt2);
			}
		});
	};
	
	public void doneArrangeFurniture(){
		$(".desk-wrapper",floorPlan).as(Ui).draggable().destroy();
		$(".furnitureIcon").as(Ui).draggable().destroy();
		$(".desk", floorPlan).as(Ui).rotatable().destroy();
		$(floorPlan).as(Ui).droppable().destroy();
		$(furnitureTools).as(Effects).clipDisappear( );
		
		//set the temp to permanent and redraw
		//iterate throug templist exclude nulls 
		
		data.setFurniture(tempFurnitureList);
		$(studentTools).css("display","none");
		
		console.log("This is the data sent by done arrage furniture: " +  JsonUtils.stringify(data));
		Properties prop = Properties.create();
		prop.set("seatingChart", JsonUtils.stringify(data));
		prop.set("roster", roster.getId());
		
		Ajax.post(RosterUrl.UPDATE_SEATINGCHART, prop);
		
		home();
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

	@Override
	public void manageStations() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneManageStations() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}
	


}
