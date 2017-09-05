package net.videmantay.roster;

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
import net.videmantay.roster.student.FistNameCompare;
import net.videmantay.roster.student.LastNameCompare;
import net.videmantay.student.json.RosterStudentJson;

import static com.google.gwt.query.client.GQuery.*;

import java.util.ArrayList;
import java.util.Collections;
import com.google.gwt.core.client.JsArray;


public class ClassroomGrid extends Composite implements HasRosterDashboardView{

	
	private final MaterialPanel mainPanel = new MaterialPanel();
	private final RosterJson roster;
	private final JsArray<RosterStudentJson> students;
	private final ArrayList<RosterStudentJson> studentList = new ArrayList<RosterStudentJson>();
	private boolean sortByFirst=true;
	//create fab
	MaterialFAB fab = new MaterialFAB();
	MaterialAnchorButton fabBtn = new MaterialAnchorButton();
	MaterialFABList fabList = new MaterialFABList();
	MaterialAnchorButton addStudentBtn = new MaterialAnchorButton();
	MaterialAnchorButton addAssignmentBtn = new MaterialAnchorButton();
	
	public ClassroomGrid(RosterJson ros){
		roster = ros;
		students = roster.getRosterStudents();
		
		this.initWidget(mainPanel);
		if(students.length() <= 0){
			showEmpty();
		}else{
		for(int i = 0; i < students.length(); i++){
			studentList.add(students.get(i));
		}
		drawGrid(sortByFirst);
		}//end if else	
		//do fab
		fabBtn.setIconType(IconType.ADD);
		fabBtn.setIconColor(Color.WHITE);
		fabBtn.setBackgroundColor(Color.RED_DARKEN_4);
		fabBtn.setSize(ButtonSize.LARGE);
		fabBtn.setType(ButtonType.FLOATING);
		
		addStudentBtn.setIconType(IconType.SCHOOL);
		addStudentBtn.setSize(ButtonSize.MEDIUM);
		addStudentBtn.setType(ButtonType.FLOATING);
		addStudentBtn.setIconColor(Color.WHITE);
		
		addAssignmentBtn.setIconColor(Color.WHITE);
		addAssignmentBtn.setIconType(IconType.ASSIGNMENT);
		addAssignmentBtn.setSize(ButtonSize.MEDIUM);
		addAssignmentBtn.setType(ButtonType.FLOATING);
		
		
		fab.add(fabBtn);
		fab.add(fabList);
		fabList.add(addStudentBtn);
		fabList.add(addAssignmentBtn);
		mainPanel.add(fab);
	}
	
	@Override
	public void onLoad(){
		
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
		mainPanel.add(row);
		MaterialColumn c;
		RosterStudentPanel rsp;
		
		if(sortByFirst){
			Collections.sort(studentList, new FistNameCompare());
		}else{
			Collections.sort(studentList, new LastNameCompare());
		}
		
		console.log(studentList);
			int i = 0;
			console.log("student 0 is:");
			console.log(studentList.get(0));
				do{
					 c = new MaterialColumn();
					 rsp = new RosterStudentPanel(studentList.get(i));
					 rsp.addStyleName("grid");
					 c.add(rsp);
					 ++i;
					 row.add(c);
				}while(i < studentList.size());
			
		
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
			 rsp = new RosterStudentPanel(studentList.get(i));
			 c.add(rsp);
			 ++i;
			 if(i%6==0){
				 row = new MaterialRow();
				 mainPanel.add(row);
			 }
			 row.add(c);
		}while(i < studentList.size());
		
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
		// TODO Auto-generated method stub
		
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

	
	
}
