package net.videmantay.roster;

import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.query.client.Function;
import java.util.ArrayList;

import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;

import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialRow;
import net.videmantay.student.json.RosterDetailJson;

public class RosterGrid extends MaterialContainer{
	final ArrayList<RosterDetailJson> rosterList = new ArrayList<RosterDetailJson>();
	final HTMLPanel emptyList = new HTMLPanel("<h5 class='emptyRosterListHeading'>Your Roster List Is Empty </h5>"+
												"<h6 class='emptyRosterListContent'>you can begin pressing the big  plus button at" +
												"the botton of the screen.</h6>");
	
	@Override
	public void onLoad(){
		MaterialLoader.showLoading(true);
		JsArray<RosterDetailJson> rosters = window.getPropertyJSO("rosterList").cast();
		for(int i = 0; i < rosters.length(); i++){
			rosterList.add(rosters.get(i));
		}
		drawGrid();
	}
	
	public void drawGrid(){
		MaterialLoader.showLoading(false);
		if(rosterList == null || rosterList.size() <= 0){
			showEmptyList();
		}else{
		this.clear();
		MaterialRow row = new MaterialRow();
		for(int i= 0; i< rosterList.size(); i++){
			
			
			MaterialColumn col = new MaterialColumn(12,6,4);
			RosterPanel panel = new RosterPanel();
			panel.setData(rosterList.get(i));
			col.add(panel);
			row.add(col);
			this.add(row);
			}//end for
		}//end else
	}
	
	public void showEmptyList(){
		this.clear();
		this.add(this.emptyList);
	}



}
