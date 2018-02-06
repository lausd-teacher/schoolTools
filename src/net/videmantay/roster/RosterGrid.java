package net.videmantay.roster;

import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.core.client.JsArray;
import gwt.material.design.addins.client.emptystate.MaterialEmptyState;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialRow;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.shared.json.RosterInfoJson;


public class RosterGrid extends MaterialContainer{

	MaterialRow row = new MaterialRow();
	public MaterialEmptyState emptyState = new MaterialEmptyState();
	
	
	
	RosterGrid(){
		this.setWidth("100%");
		this.setHeight("100%");
		this.setPadding(15.5);
		emptyState.setTitle("There are no registered rosters.");
		emptyState.setDescription("Begin by clicking on the roster button. or going to Schoology and syncing a course from there");
		emptyState.setIconType(IconType.LIST);
		emptyState.setIconColor(Color.RED_DARKEN_2);
		emptyState.setSize("100%", "100%");
		row.setGrid("s3");
		this.add(row);
		drawGrid();
	}
	
	public void showEmptyList(){
		this.clear();
		this.add(this.emptyState);
	}
	
	
	public void drawGrid(){
		row.clear();
		//get the list and check if it is more than zero
		JsArray<RosterInfoJson> rosterList = window.getPropertyJSO("rosterList").cast();
		console.log("roster info list is: ");
		console.log(rosterList);
		if(rosterList.length() <1){
			showEmptyList();
		}else{
			for(int i = 0; i < rosterList.length(); i++){
				if(rosterList.get(i) != null){
				RosterPanel rp = new RosterPanel();
				MaterialColumn col = new MaterialColumn();
				
				rp.setData(rosterList.get(i));
				col.add(rp);
				row.add(col);
				}
			}
		}
	}
	
	
	

	

}
