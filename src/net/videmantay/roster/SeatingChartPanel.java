package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.query.client.plugins.effects.PropertiesAnimation.EasingCurve;

import static com.google.gwt.query.client.GQuery.*;
import static gwtquery.plugins.ui.Ui.Ui;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import gwtquery.plugins.ui.DraggableUi;
import gwtquery.plugins.ui.DroppableUi;
import gwtquery.plugins.ui.RotatableUi;
import gwtquery.plugins.ui.interactions.CursorAt;
import gwtquery.plugins.ui.interactions.Draggable;
import gwtquery.plugins.ui.interactions.Droppable;
import gwtquery.plugins.ui.interactions.Rotatable;
import java.util.ArrayList;
import java.util.Collections;

import net.videmantay.roster.json.RosterJson;
import net.videmantay.roster.seatingchart.FurnitureUtils;
import net.videmantay.roster.seatingchart.json.FurnitureJson;
import net.videmantay.roster.seatingchart.json.SeatingChartJson;
import net.videmantay.roster.seatingchart.json.StudentSeatJson;
import net.videmantay.roster.student.FistNameComparator;
import net.videmantay.student.json.RosterStudentJson;

public class SeatingChartPanel extends Composite implements HasRosterDashboardView {

	

 private static SeatingChartPanelUiBinder uiBinder = GWT.create(SeatingChartPanelUiBinder.class);
	 

	interface SeatingChartPanelUiBinder extends UiBinder<Widget, SeatingChartPanel> {
	}

	private SeatingChartJson data;
	private SeatingChartJson oldData = SeatingChartJson.createObject().cast();
	private boolean isEditing = false;
	//use arrayList to make editing easier;
	private ArrayList<FurnitureJson> tempFurnitureList;
	//save draggableParent for reference
	private GQuery $dragParent;
	private enum State{DASHBOARD, STUDENT_EDIT,FURNITURE_EDIT,STATION_EDIT,GROUP_EDIT};
	
	State state = State.DASHBOARD;
	
	@UiField
	DivElement floorPlan;
	
	@UiField
	MaterialCollection studentList;
	
	@UiField
	HTMLPanel seatingChart;
	
	@UiField
	MaterialRow seatingChartToolbar;
	
	@UiField
	MaterialButton stateSelectBtn;
	
	@UiField
	MaterialDropDown scDropDown;
	
	@UiField
	MaterialAnchorButton doneBtn;
	
	@UiField
	MaterialAnchorButton cancelBtn;
	
	@UiField
	DivElement homeTools;
	
	@UiField
	DivElement editTools;
	
	@UiField
	MaterialCollapsible editCollapsible;
	
	@UiField
	HTMLPanel editStudentEmptyMessage;
	
	@UiField
	MaterialCollapsibleItem studentCollapseItem;
	
	@UiField
	MaterialCollapsibleBody studentCollapseBody;
	
	@UiField
	MaterialCollapsibleItem groupCollapseItem;
	
	@UiField
	MaterialCollapsibleItem furnitureCollapseItem;
	
	@UiField
	DivElement stationLayer;
	
	@UiField
	MaterialCollapsibleItem stationCollapseItem;
	
	private final RosterJson roster;

	SelectionHandler<Widget> handler = new SelectionHandler<Widget>(){

		@Override
		public void onSelection(SelectionEvent<Widget> event) {
			String text = ((MaterialLink)event.getSelectedItem()).getText();
			stateSelectBtn.setText(text);
			stateSelectBtn.setIconType(((MaterialLink)event.getSelectedItem()).getIcon().getIconType());
			stateChange();
			switch(text){
			case "Students": arrangeStudents(); editCollapsible.setActive(2);break;
			case "Groups": groups();editCollapsible.setActive(3);break;
			case "Stations": manageStations();editCollapsible.setActive(4);break;
			default:arrangeFurniture() ;editCollapsible.setActive(1);
			}
			
		}};
		
	ClickHandler clickHandler = new ClickHandler(){
		@Override
		public void onClick(ClickEvent event) {
			
		new Timer(){
			@Override 
			public void run(){
				
				String id = $(".active", editCollapsible).id();
				if(id == null || id.isEmpty()){
					return;
				}
				
				stateChange();
				
				switch(id){
				case"studentCollapseItem": stateSelectBtn.setText("Students");
				stateSelectBtn.setIconType(IconType.SCHOOL);arrangeStudents();break;
				case"groupCollapseItem":stateSelectBtn.setText("Groups");
				stateSelectBtn.setIconType(IconType.GROUP_WORK);groups();break;
				case"stationCollapseItem":stateSelectBtn.setText("Stations");
				stateSelectBtn.setIconType(IconType.WIDGETS);manageStations();break;
				default: stateSelectBtn.setText("Furniture"); 
				stateSelectBtn.setIconType(IconType.EVENT_SEAT); arrangeFurniture();break;
				}
			}
		}.schedule(300);
		
		}};	
		
	ClickHandler doneHandler = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			event.stopPropagation();
			event.preventDefault();
			confirmEdit();
			
		}};
	ClickHandler cancelHandler = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			event.stopPropagation();
			event.preventDefault();
			cancelEdit();
			
		}};
		private final Function studentClick = new Function(){
			@Override 
			public boolean f(Event e, Object...objects){
				e.stopPropagation();
				e.preventDefault();
				RosterStudentPanel rosPanel = $(e.getCurrentEventTarget()).widgets(RosterStudentPanel.class).get(0);
				$(body).trigger("studentAction", rosPanel.getData());
				console.log("student " + rosPanel.getElement().getId() + " was clicked for action modal");
				return true;
			}
		};
		
		//constructor////
	public SeatingChartPanel(RosterJson ros) {
		this.roster = ros;
		console.log("on seating chart const roster is");
		
		Ajax.get("/roster/"+ roster.getId()+"/routine/"+ roster.getRoutineConfig().getId()+ "/seatingchart/" )
		.done(new Function(){
			@Override
			public void f(){
				data = JsonUtils.safeEval((String)this.arguments(0)).cast();
				oldData.setDescript(data.getDescript());
				oldData.setFurniture(data.getFurniture());
				oldData.setId(data.getId());
				oldData.setTitle(data.getTitle());
				drawGrid();	
			}
		});
		
		
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void onLoad(){
		scDropDown.addSelectionHandler(handler);
		editCollapsible.addDomHandler(clickHandler, ClickEvent.getType());
		doneBtn.addClickHandler(doneHandler);
		cancelBtn.addClickHandler(cancelHandler);
		home();
		
	}//end onLoad
	
	@Override
	public void onUnload(){
		
	}
	
	public void setSeatingChart(SeatingChartJson seatingChart){
		data = seatingChart;
		oldData.setDescript(data.getDescript());
		oldData.setFurniture(data.getFurniture());
		oldData.setId(data.getId());
		oldData.setTitle(data.getTitle());
		drawGrid();
	}
	

	public void drawGrid(){
		floorPlan.removeAllChildren();
		studentList.clear();
		
		
		//make a copy of the student list then pop as they are put in place
		ArrayList<RosterStudentPanel> stuPanels = new ArrayList<RosterStudentPanel>();
		ArrayList<RosterStudentJson>rosterStudentList = new ArrayList<>();
		for(int i = 0 ; i< roster.getStudents().length();i++) {
			rosterStudentList.add(roster.getStudents().get(i));
		}
		Collections.sort(rosterStudentList, new FistNameComparator());
		for(RosterStudentJson rsj: rosterStudentList){
			//might as well setup the studentList here too
			RosterStudentPanel sp =new RosterStudentPanel();
			sp.setData(rsj);
			stuPanels.add(sp);
		}
		console.log("We've cycled through students here is array of panels ");
		console.log(stuPanels);
		// go through list of furniture and place them on floorPlan
		console.log("Here is the furniture json ");
		console.log(data.getFurniture());
		if(data.getFurniture() == null || data.getFurniture().length() <= 0){
		for(int i =0; i< data.getFurniture().length(); i++){
			console.log("draw(): furniture kind is ");
			console.log(data.getFurniture().get(i).getKind());
			//Gquery only operates on DOM so make and place 
			HTMLPanel furniturePanel = FurnitureUtils.byKind(data.getFurniture().get(i).getKind());
			floorPlan.appendChild(furniturePanel.getElement());
			
			final FurnitureJson furniture = data.getFurniture().get(i);
			GQuery $furniturePanel = $(furniturePanel);
			$furniturePanel.data("desk", furniture);
			$furniturePanel.css("top",furniture.getTop());
			$furniturePanel.css("left", furniture.getLeft());
			//now spin the desk
			$furniturePanel.find(".desk")
			.css("transform", "rotate(" + furniture.getRotate() +"rad)");
			
			///if this is a desk and it's not empty make a place student of student ids;
			if(furniture.getType() != null && !furniture.getType().isEmpty() &&furniture.getType().equals("desk")){
				console.log("If furniture desk called");
				JsArray<StudentSeatJson>studentSeats = furniture.getSeats();
				console.log("The seat of the desk are");
				console.log(studentSeats);
				for(int j = 0; j < studentSeats.length(); j++){
					$furniturePanel.find(".pos" +(j+1)).data("seat", studentSeats.get(j));
					if(!studentSeats.get(j).isEmpty()){
						console.log("Student seat was not empty and \n studnet Id is " + studentSeats.get(j).getRosterStudent());
						//iterate through students for equal ids
						//then pop to make iterations shorter
						for(RosterStudentPanel rsp : stuPanels){
							console.log("student element id is " + rsp.getElement().getId());
							if(rsp.getElement().getId().equalsIgnoreCase(studentSeats.get(j).getRosterStudent())){
								console.log("studentId is equals rosterstudent seat called ");
									$furniturePanel.find("tr>td>div.seat.pos"+(j+1))
										.append(rsp.toString()).find(".counterRotate").css("transform", "rotate("+ (-furniture.getRotate()) +"rad)");
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
		}//end else
			//the rest of students panel go in the sideNav
			console.log("We finished with drawn seats and mathcing student id's lis of array is ");
			console.log(stuPanels);
			if(stuPanels.size() > 0){
				//place the rest in side
				for(RosterStudentPanel rsp : stuPanels){
				MaterialCollectionItem mci =new MaterialCollectionItem();
			
				mci.add(rsp);
				mci.setPadding(5);
				studentList.add(mci);
				}//end for
				console.log("Here is the student collection ");
				console.log(studentList);
			}//end if
	
		//everything should be in place now add home state
		home();
	}	
	
	public void stateChange(){
		switch(state){
		case FURNITURE_EDIT: doneArrangeFurniture();break;
		case STUDENT_EDIT: doneArrangeStudents();break;
		case STATION_EDIT: doneManageStations();break;
		case GROUP_EDIT: break;
		default:home();
		}
	}

	@Override
	public void checkHW() {
		
		
	}

	//this is to show groups not manage them
	@Override
	public void groups() {
		// TODO Auto-generated method stub
		
	}
	
	//this is two show stations not edit
		public void stations(){
			
		}
		
// this shows procedures not edit them
		public void procedures(){
			
		}

	@Override
	public void takeRoll() {
		$(".rosterStudent").click(new Function(){
			@Override 
			public void f(){
				//show student behavior form for
				// specific student
				//showBehaviorForm( studentId);
			}
		
		});
		
	}

	@Override
	public void home() {
		//right now home is for clicking on students
		//and generating a dialog for behavior management or
		// seeing more info on that student will calll it management dialog
		state = State.DASHBOARD;
		if(studentList.getElement().getChildCount() > 0){
			$(studentList).show();
		}
		
		$(".rosterStudent").click(studentClick);
	}
	
	
	public void unHome(){
		$(".rosterStudent").off("click");
		$(studentList).hide();
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
	


	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}


	
	public boolean isEditing() {
		return isEditing;
	}
	

	public void edit(){
		unHome();
		homeTools.getStyle().setDisplay(Style.Display.NONE);
		seatingChartToolbar.getElement().getStyle().setDisplay(Display.BLOCK);
		editTools.getStyle().setDisplay(Display.BLOCK);
		this.setIsEditing(true);
		if(studentList.getWidgetCount() >= 1){
			studentList.removeFromParent();
			studentCollapseBody.clear();
			studentCollapseBody.add(studentList);
			studentList.getElement().getStyle().setDisplay(Style.Display.BLOCK);
		}
		
		arrangeFurniture();
	}
	
	public void setIsEditing(boolean isEditing){
		this.isEditing = isEditing;
	}
	
	public void doneEditing(){
		stateChange();
		setIsEditing(false);
		//hide the edit -tools
		seatingChartToolbar.getElement().getStyle().setDisplay(Style.Display.NONE);
		editTools.getStyle().setDisplay(Style.Display.NONE);
		homeTools.getStyle().setDisplay(Style.Display.BLOCK);
		$("#dashboard").trigger("doneEdit");
	}
	
	public void confirmEdit(){
		
		doneEditing();
		//persist the data 
		
		//oldData = data persist
		
	}

	@Override
	public void cancel(String state) {
		//set data to old data.
		//then finish editing.
		
		
	}
	
	public void cancelEdit(){
		doneEditing();
		//data = oldData
		//redraw
	}

	public  void arrangeStudents(){
		//set state to student edit
		state = State.STUDENT_EDIT;
		//make students draggable
		//constraint to seatingchart
		Draggable.Options dragOpts = Draggable.Options.create();
		dragOpts.containment(".seatingChart").revert("invalid")
		.helper("clone").appendTo(body)
		//set the original to be lighter on start of drag
		.start(new Function(){
			@Override
			public void f(){
				$(this).css("opacity", "0.3");
				$dragParent = $(this).parent();
			}
		})
		.stop(new Function(){
			@Override
			public boolean f(Event e, Object...o){
				
				$(e.getEventTarget()).css("opacity", "1");
				return true;
			}
		})
		//while draggind
		.drag(new Function(){
			@Override
			public boolean f(Event e, Object...o){
				DraggableUi ui = (DraggableUi)o[0];
				$(ui.getHelper().get()).find(".counterRotate").css("transform","rotate(0.0rad)");
				return true;
			}
		});
		$(".rosterStudent").as(Ui).draggable(dragOpts);
		//what happens once students are dropped
		Droppable.Options dropOpts = Droppable.Options.create();
		dropOpts.accept(".rosterStudent").greedy(true).hoverClass("seat-over");
		//make seat droppable event target is seaat
		$("td > div.seat", floorPlan).as(Ui).droppable(dropOpts)
			.on("drop", new Function(){
				@Override
				public boolean f(Event e, Object...o){
					DroppableUi ui =(DroppableUi)o[0];
					//once drop the draggable the target as parent how to get previous parent?
					GQuery $dropSeat = $(e.getEventTarget());
					GQuery $draggable = $(ui.draggable().get());
					//if the seatPanel has a student swap
					final GQuery $rosStudent = $dropSeat.find(".rosterStudent");
					if($rosStudent.length() > 0){
						if($dragParent.is("li")){
							$rosStudent.animate($$("{left: '+=75em' , opacity: 0}"),1000, EasingCurve.easeInSine, 
									new Function(){
								@Override
								public void f(){
									$rosStudent.appendTo($dragParent);
									$rosStudent.css($$("opacity:1;position:relative;left:0px"));
									
									
								}
							});
						}
						
							if($dragParent.hasClass("seat")){
							GQuery $pWrapper = $dropSeat.closest("div.desk-wrapper");
							final GQuery $cloneWrap = $("<div id='clone'></div>")
									.css($$("overflow:visible;width:4em;height:8em;position:absolute;left:"
							+ $pWrapper.left()  +"px;top:" + $pWrapper.top() +"px")).appendTo(floorPlan);
						
							console.log($cloneWrap);
							$rosStudent.appendTo($cloneWrap);
							$cloneWrap.animate($$("left:'+=" +($dragParent.offset().left -$pWrapper.offset().left)
									+"',top:'+=" +($dragParent.offset().top-$pWrapper.offset().top)+"'"), 350, EasingCurve.easeIn, 
							new Function(){
								@Override
								public void f(){
									$cloneWrap.remove();
									$rosStudent.appendTo($dragParent);
									
									StudentSeatJson stuSeat = $dragParent.data("seat", StudentSeatJson.class);
									//parent desk
									FurnitureJson furJ =  $dragParent.closest("div.desk-wrapper").data("desk", FurnitureJson.class);
									if(stuSeat == null){
									
										//find pos class of target  until positions match
										//more practically 2 will be the highest number revie when we revise desks.
											if($dragParent.hasClass("pos1")){
												stuSeat = furJ.getSeatByNum(1);
											}else{
												stuSeat = furJ.getSeatByNum(2);
											}//end if-else
											
											$dragParent.data("seat", stuSeat);
										
									}
									stuSeat.setRosterStudent($rosStudent.id());
									$rosStudent.find("div.counterRotate").css("transform", "rotate("+(-furJ.getRotate())+"rad)");
									
								}
							});
						}
					}
						
					
					
					
					//now detach and append to new seat
					$draggable.css("opacity","1");
				
					
					$draggable.appendTo($dropSeat.get(0));
					//remove former paren
					//actual seat
					StudentSeatJson stuSeat = $dropSeat.data("seat", StudentSeatJson.class);
					//parent desk
					FurnitureJson furJ =  $dropSeat.closest("div.desk-wrapper").data("desk", FurnitureJson.class);
					if(stuSeat == null){
					
						//find pos class of target  until positions match
						//more practically 2 will be the highest number revie when we revise desks.
							if($(e.getEventTarget()).hasClass("pos1")){
								stuSeat = furJ.getSeatByNum(1);
							}else{
								stuSeat = furJ.getSeatByNum(2);
							}//end if-else
							
							$(e.getEventTarget()).data("seat", stuSeat);
						
					}
					stuSeat.setRosterStudent(ui.draggable().get().getId());
					$(e.getEventTarget()).find("div.counterRotate").css("transform", "rotate("+(-furJ.getRotate())+"rad)");
					console.log("seat json");
					console.log($(e.getEventTarget()).data("seat", StudentSeatJson.class));
					return true;
				}
			});
		
		
	};
	
	public void doneArrangeStudents(){
		MaterialLoader.loading(true);
		SeatingChartJson seatingChart = window.getPropertyJSO("seatingChart").cast();
		//iterate through the seats and update data
		$(".seat").each(new Function(){
			@Override
			public void f(){
				StudentSeatJson seat = $(this).data("seat",StudentSeatJson.class);
				if($(this).find(".rosterStudent").length() > 0){
					
					seat.setRosterStudent($(this).find(".rosterStudent").get(0).getId());
				}else{
					seat.setRosterStudent("");
				}
			}
		});
		console.log("This is the data sent by done arrage furniture: " +  JsonUtils.stringify(seatingChart));
		$(".rosterStudent").as(Ui).draggable().destroy();
		$("td > div.seat",floorPlan).as(Ui).droppable().destroy();
		MaterialLoader.loading(false);
	};
	
	
	
	public  void arrangeFurniture(){
		state = State.FURNITURE_EDIT;
		tempFurnitureList = new ArrayList<FurnitureJson>();
		if(data.getFurniture().length() > 0){
		for(int i = 0; i < data.getFurniture().length(); i++){
			tempFurnitureList.add(data.getFurniture().get(i));
		}
		}//end if
		
	//make icons draggable and floorPlan drop target
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
		$(floorPlan).as(Ui).droppable(dropOpts).on(Droppable.Event.drop, new Function(){
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
			final FurnitureJson desk = FurnitureJson.create();
			desk.setSeats(iconId);
			console.log(desk);
			
			String left ;
			String top;
			if(Window.Navigator.getUserAgent().matches("//iPad|Android//i")) {
				console.log("its a tablet");
				 left =  (e.getScreenX() - floorPlan.getAbsoluteLeft() + document.getScrollLeft())  +"px";
				 top =   (e.getScreenY() - floorPlan.getAbsoluteTop() + document.getScrollTop() ) + "px";
			}else{
				console.log("not a tablet");
				left =  (e.getClientX() - floorPlan.getAbsoluteLeft() + document.getScrollLeft())  +"px";
				top =   (e.getClientY() - floorPlan.getAbsoluteTop() + document.getScrollTop() ) + "px";
			}
			//added to browser
			floorPlan.appendChild(drop.getElement());
			GQuery $drop = $(drop);
			$drop.css("left",left);
			$drop.css("top",top );
			
			
			//push to temp list
			desk.setLeft($drop.css("left"));
			desk.setTop($drop.css("top"));
			tempFurnitureList.add(desk);
			
			//push data to desk
			$drop.data("desk", desk);
			GQuery $seats = $drop.find(".seat");
			$seats.each(new Function() {
				@Override
				public void f() {
					console.log("checking the seat now");
					GQuery $this = $(this);
					if($this.hasClass("pos1")) {
						$this.data("seat", desk.getSeats().get(0));
					}else {
						if(desk.getSeats().length() >1) {
							$this.data("seat", desk.getSeats().get(1));
						}
					}
				}
			});
			
			console.log("The furniture list array");
			console.log(tempFurnitureList);
			makeDragRotateDelete($drop);
				return true;
			}
		});
		
		//make furniture on floorPlan draggable and rotatable and deletable
		$(".desk-wrapper", floorPlan).each(new Function(){
			@Override
			public void f(){
				final GQuery $this = $(this);
				makeDragRotateDelete($this);
				
			}
		});
	};
	
	private void makeDragRotateDelete(final GQuery $this){
		final FurnitureJson thisDesk = $this.data("desk");
		Draggable.Options dragOpt3 =Draggable.Options.create();
		CursorAt cursorAt = CursorAt.create();
		
		if(Window.Navigator.getUserAgent().matches("//iPad|Android//i")) {
			console.log("its a tablet");
			 cursorAt.setTop($this.width()/2 - floorPlan.getAbsoluteLeft() + document.getScrollLeft());
			 cursorAt.setLeft($this.height()/2 - floorPlan.getAbsoluteTop() + document.getScrollTop() );
		}else{
			cursorAt.setTop((int) Math.floor($this.height()/2)).setLeft((int)Math.floor($this.width()/2));
		}
		
		dragOpt3.containment(floorPlan).cursorAt(cursorAt)
		.stop(new Function(){
			@Override
			public boolean f(Event e, Object...o){
				thisDesk.setTop($this.css("top"));
				thisDesk.setLeft($this.css("left"));
				//get true left
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
				GQuery $desk = $this.find(".desk");
				thisDesk.setRotate(ui.angle().current());
				thisDesk.setTransform($desk.css("transform"));
				console.log(thisDesk);
				return true;
			}
		})
		.rotate(new Function(){
			@Override
			public boolean f(Event e, Object...o){
				console.log("rotate called");
				RotatableUi ui = (RotatableUi)o[0];
				console.log("rotatable ui is ");
				console.log(ui);
				GQuery $counter = $(ui.element()).find("div.counterRotate");
				console.log(ui.element());
				console.log("all counter roatate found " + $counter.size());
				if($counter.length() > 0){
					$counter.css("transform","rotate(" + -ui.angle().current() +"rad)");
				}
				return true;
			}
		});
		$this.as(Ui).draggable(dragOpt3);
		$this.find(".desk").as(Ui).rotatable(rotOpt2);
		$(".deskDeleter").css("display", "block").on("click", new Function(){
										@Override 
										public boolean f(Event e){ 
											
											GQuery $desk = $(this).closest(".desk-wrapper");
											deleteFurniture($desk);
											return true;}});
	}
	
	public void doneArrangeFurniture(){
		
		JsArray<FurnitureJson> finalList = JsArray.createArray().cast();
		$(".desk-wrapper",floorPlan).as(Ui).draggable().destroy();
		$(".furnitureIcon").as(Ui).draggable().destroy();
		$(".desk", floorPlan).as(Ui).rotatable().destroy();
		$(floorPlan).as(Ui).droppable().destroy();
		$(".deskDeleter").off("click").css("display", "none");
		
		//set the temp to permanent and redraw
		//iterate through templist exclude nulls 
		//may have no seats
		if(tempFurnitureList.size() >0){
		for(int i = 0; i < tempFurnitureList.size() ; i++){
			if(tempFurnitureList.get(i) == null){
				continue;
			}else{
				finalList.push(tempFurnitureList.get(i));
			}
		}
		data.setFurniture(finalList);
		
		//drawGrid();
		}
	};
	
	

	public void deleteFurniture(final GQuery $this){
		//first check to see if it's a desk and if the desk has students
		//if so these student must be put in the studentList alphabetically??
		GQuery $desk = $this;
		
			GQuery $rosStu = $desk.find(".rosterStudent");
			if($rosStu.length() > 0){
				for(int i = 0; i < $rosStu.length(); i++){
				MaterialCollectionItem link = new MaterialCollectionItem();
				link.add($rosStu.widget());
				}//end for
			}//end if
	
		tempFurnitureList.remove($desk.data("desk"));
		$desk.remove();
	}

	public void manageStations() {
		/*state = State.STATION_EDIT;
	final GQuery $stations =	$(stationLayer);
	
	Function stClick = new Function(){};
	//drag opts just get x and y on stop
	final Function dragStop = new Function(){
		@Override
		public boolean f(Event e, Object...o){
			
			
			return true;
		}
	};
	
	final Draggable.Options stDragOpt = Draggable.Options.create();
	stDragOpt.stop(dragStop).containment(floorPlan);
	
	
	
	//make stations draggable and resizable
	$("div.station").as(Ui).draggable().resizable();
	Function stationCreate = new Function(){
		@Override
		public boolean f(Event e){
			String left = e.getClientX()  - stationLayer.getAbsoluteLeft()+ body.getScrollLeft()+"px";
			String top = e.getClientY() - stationLayer.getAbsoluteTop()+ body.getScrollTop() + "px";
			//added to browser
			final GQuery $drop = $stations.html("<div></div");
			$drop.css("left",left);
			$drop.css("top",top );
			$drop.css("width", "150px").css("height", "150px");
			$drop.as(Ui).resizable().draggable();
			return true;
		}
	};
	$stations.click();
	
		*/
	}

	public void doneManageStations() {
		// TODO Auto-generated method stub
		
	}


	public State getState() {
		return this.state;
	}


	public void setState(State state) {
		this.state = state;
	}

	@Override
	public void doneProcedures() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneStations() {
		// TODO Auto-generated method stub
		
	}
	
}