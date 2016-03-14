package net.videmantay.roster.seatingchart;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialContainer;

import static gwtquery.plugins.ui.Ui.Ui;
import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.query.client.Function;
import net.videmantay.roster.HasRosterDashboardView;
import net.videmantay.roster.RosterStudentPanel;
import net.videmantay.roster.seatingchart.json.DeskJson;
import net.videmantay.roster.seatingchart.json.FurnitureJson;
import net.videmantay.roster.seatingchart.json.SeatingChartJson;
import net.videmantay.roster.seatingchart.json.StudentSeatJson;

public class SeatingChartPanel extends Composite implements HasRosterDashboardView {

	private static SeatingChartPanelUiBinder uiBinder = GWT.create(SeatingChartPanelUiBinder.class);

	interface SeatingChartPanelUiBinder extends UiBinder<Widget, SeatingChartPanel> {
	}

	private SeatingChartJson data;
	
	@UiField
	MaterialContainer floorPlan;
	
	
	
	public SeatingChartPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void onLoad(){
		if(window.getPropertyJSO("JQuery.ui") == null){
			//load script
			ScriptInjector.fromUrl("/js/load.js").setWindow(ScriptInjector.TOP_WINDOW).inject();
		}
	}
	
	public void setData(SeatingChartJson seatingChart){
		data = seatingChart;
		drawChart();
	}
	
	public void drawChart(){
		// go through list of desk and place them on floorPlan
		for(int i =0; i< data.getFurniture().length; i++){		
			DivElement deskPanel = Desk.draw(data.getFurniture()[i].getKind());
			$(deskPanel).data("desk", data.getFurniture()[i]);
			$(floorPlan).append(deskPanel)
			.css("left", data.getFurniture()[i].getLeft())
			.css("top", data.getFurniture()[i].getTop())
			.css("transform", "rotate("+data.getFurniture()[i].getRotate()+"rad)");
			
		if(data.getFurniture()[i].getType().equals(FurnitureType.DESK)){
			DeskJson desk = data.getFurniture()[i].cast();
			for(StudentSeatJson s:desk.getSeats()){
				if(!s.isEmpty()){
					RosterStudentPanel rsp = new RosterStudentPanel();
					//set the student data here
					
				}
			}
		}
			
			
		}// end for///
		
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
	public void doneSelectAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deselectAll() {
		// TODO Auto-generated method stub
		
	}
	
	public final native void arrangeStudents()/*-{}-*/;
	
	public final native void doneArrangeStudents()/*-{}-*/;
	
	public final native void arrangeFurniture()/*-{}-*/;
	
	public final native void doneArrangeFurniture()/*-{}-*/;


}
