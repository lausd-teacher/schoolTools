 package net.videmantay.roster;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

import gwt.material.design.client.constants.ButtonSize;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialFAB;
import gwt.material.design.client.ui.MaterialFABList;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.roster.student.FistNameComparator;
import net.videmantay.roster.student.LastNameComparator;
import net.videmantay.student.json.RosterStudentJson;

import static com.google.gwt.query.client.GQuery.*;

import java.util.ArrayList;
import java.util.Collections;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;


public class ClassroomGrid extends Composite implements HasRosterDashboardView{

	
	private final MaterialPanel mainPanel = new MaterialPanel();
	private final RosterJson roster;
	private final JsArray<RosterStudentJson> students;
	private final ArrayList<RosterStudentJson> studentList = new ArrayList<RosterStudentJson>();
	private boolean sortByFirst=true;
	
	/*private final ClickHandler studentHandler = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			event.stopPropagation();
			event.preventDefault();
		RosterStudentPanel rosPanel = $(event.getNativeEvent().getCurrentEventTarget()).widgets(RosterStudentPanel.class).get(0);
		
			
		}};*/
		
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

	public ClassroomGrid(RosterJson ros){
		roster = ros;
		students = roster.getStudents();
		
		this.initWidget(mainPanel);
		mainPanel.setWidth("100%");
		if(students == null || students.length() <= 0){
			showEmpty();
		}else{
		for(int i = 0; i < students.length(); i++){
			studentList.add(students.get(i));
		}
		drawGrid(sortByFirst);
		}//end if else	
		
	}
	

	
	@Override
	public void onLoad(){
		home();
	}
	
	public void showEmpty(){
		HTMLPanel empty = new HTMLPanel("<h3>Your student list appears to be empty...</h3>"+
	                                     "<p>To manager you students just open the side menu"+
				                       "and click on students<p><p><a href='#students'>Just click here</a></p>");
		empty.setStylePrimaryName("emptyClassroom");
		mainPanel.clear();
		mainPanel.add(empty);
	}
	
	public void drawGrid(){
		mainPanel.clear();
		MaterialRow row = new MaterialRow();
		row.setPadding(5.0);
		mainPanel.add(row);
		MaterialColumn c;
		RosterStudentPanel rsp;
		
		if(sortByFirst){
			Collections.sort(studentList, new FistNameComparator());
		}else{
			Collections.sort(studentList, new LastNameComparator());
		}
		
		
			for(int i = 0; i < studentList.size(); i++){
					 c = new MaterialColumn();
					 c.setGrid("s6 m3 l2");
					 rsp = new RosterStudentPanel();
					 rsp.setData(studentList.get(i));
					 rsp.addStyleName("grid");
					 c.add(rsp);
					 row.add(c);
				}
			
		
	}
	
	public void drawGrid(boolean firstName){
		sortByFirst = firstName;
		drawGrid();
	}
	
	public void reverse(){
		Collections.reverse(studentList);
		MaterialRow row = new MaterialRow();
		mainPanel.add(row);
		MaterialColumn c;
		RosterStudentPanel rsp;
		
		int i = 0;
		do{
			 c = new MaterialColumn();
			 rsp = new RosterStudentPanel();
			 rsp.setData(studentList.get(i));
			 c.add(rsp);
			 ++i;
			 if(i%6==0){
				 row = new MaterialRow();
				 mainPanel.add(row);
			 }
			 row.add(c);
		}while(i < studentList.size());
		
	}
	
	public void drawGroups(){
		
	}
	public void showProcedures(){
		
	}
	
	public void hideProcedures(){
		
	}
	
	public void showStations(){
		
	}
	
	public void hideStations(){
		
	}

	@Override
	public void checkHW() {
		// TODO Auto-generated method stub
		
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
	public void pickRandom() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void multipleSelect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void home() {
		//here is where student panel is activated with
		//click to activate student action modal.
		//find all .rosterStudent panel and add click event;
		$(".rosterStudent").click(studentClick);
		
	}
	public void unHome(){
		
		$(".rosterStudent").off("click");
	}

	@Override
	public void arrangeFurniture() {
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
	public void doneSelectAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deselectAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneMultipleSelect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneArrangeFurniture() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void arrangeStudents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void manageStations() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneArrangeStudents() {
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
	public void cancel(final String state) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void procedures() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void doneProcedures() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void stations() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void doneStations() {
		// TODO Auto-generated method stub
		
	}

	
	
}
