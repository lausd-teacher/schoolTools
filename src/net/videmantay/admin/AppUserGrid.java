package net.videmantay.admin;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.IdentityColumn;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTMLPanel;

import java.util.ArrayList;
import java.util.Collections;
import gwt.material.design.client.ui.MaterialPreLoader;
import gwt.material.design.client.ui.MaterialSpinner;
import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.query.client.Function;

import net.videmantay.admin.json.AppUserJson;
import net.videmantay.shared.UserRoles;

public class AppUserGrid extends DataGrid<AppUserJson> {

	private static final AppUserAsyncDataProvider dataProv = new AppUserAsyncDataProvider();
	
	TextColumn<AppUserJson> acctIdCol = new TextColumn<AppUserJson>(){

		@Override
		public String getValue(AppUserJson object) {
			return object.getAcctId();
		}};
		
	TextColumn<AppUserJson> firstNameCol = new TextColumn<AppUserJson>(){

			@Override
			public String getValue(AppUserJson object) {
				return object.getFirstName();
			}};
	TextColumn<AppUserJson> lastNameCol = new TextColumn<AppUserJson>(){

				@Override
				public String getValue(AppUserJson object) {
					return object.getLastName();
				}};	
	TextColumn<AppUserJson> titleCol = new TextColumn<AppUserJson>(){

		@Override
		public String getValue(AppUserJson object) {
			return object.getTitle();
		}};
	TextColumn<AppUserJson> rolesCol = new TextColumn<AppUserJson>(){
		@Override
		public String getValue(AppUserJson object){
			StringBuilder sb = new StringBuilder();
			ArrayList<String> list = new ArrayList<String>();
			for(String s: object.getRoles()){
				list.add(s);
			}
			Collections.sort(list);
			
			for(String s: list){
				sb.append(s +"\n");
			}//end for
			return sb.toString().trim();
		}};
	TextColumn<AppUserJson> statusCol = new TextColumn<AppUserJson>(){

		@Override
		public String getValue(AppUserJson object) {
			return object.getUserStatus();
		}};
	AbstractCell<AppUserJson> actionCell = new AbstractCell<AppUserJson>("click"){

		@Override
		public void onBrowserEvent(Cell.Context context, Element parent, AppUserJson value, NativeEvent event, ValueUpdater<AppUserJson> valueUpdater){
			
			
			if($(event.getEventTarget()).hasClass("delete-user")){
				//fire a show deleteModal
				console.log("delete button hit");
				$(body).trigger(AdminEvent.SHOW_DELETE, value);
			}
			if($(event.getEventTarget()).hasClass("update-user")){
				//fire show updateModal
				$(body).trigger(AdminEvent.SHOW_SAVE, value);
			}
			
		}
		
		@Override
		public void render(com.google.gwt.cell.client.Cell.Context context, AppUserJson value, SafeHtmlBuilder sb) {
			String html = "<div><span class='action-icon'><i class='delete-user material-icons'/>delete</span><span class='action-icon'><i class='update-user material-icons'/>edit</span></div>";
			sb.appendHtmlConstant(html);
			
		}};
	IdentityColumn<AppUserJson> actionCol = new IdentityColumn<AppUserJson>(actionCell){};
	//loader
	MaterialSpinner spinner = new MaterialSpinner();
	MaterialPreLoader pre = new MaterialPreLoader();
	//empty table
	HTMLPanel emptyTable = new HTMLPanel("<div class='errorTable'><h2>No Data</h2><p>It appears that you have no registered user."
										+"<br/>No problem just hit the big plus button at the bottom of the screen"
										+"</p></div>");
	
	private final AppUserGrid $this = this;
	
	public AppUserGrid(){
		
		super(dataProv);
		dataProv.addDataDisplay(this);
		this.addStyleName("striped responsive-table");
		
		
		//setup col to be sortable and misc interactions
		/*titleCol.setSortable(true);
		acctIdCol.setSortable(true);
		firstNameCol.setSortable(true);
		lastNameCol.setSortable(true);
		statusCol.setSortable(true);*/
		
		this.addColumn(acctIdCol, SafeHtmlUtils.fromSafeConstant("<h6 class='header'>Acct ID</h6>"));
		this.addColumn(titleCol, SafeHtmlUtils.fromSafeConstant("<h6 class='header'>Title</h6>"));
		this.addColumn(firstNameCol, SafeHtmlUtils.fromSafeConstant("<h6 class='header'>First Name</h6>"));
		this.addColumn(lastNameCol, SafeHtmlUtils.fromSafeConstant("<h6 class='header'>Last Name</h6>"));
		this.addColumn(rolesCol, SafeHtmlUtils.fromSafeConstant("<h6 class='header'>Roles</h6>"));
		this.addColumn(statusCol, SafeHtmlUtils.fromSafeConstant("<h6 class='header'>Status</h6>"));
		this.addColumn(actionCol);	
		pre.add(spinner);
		this.setLoadingIndicator(pre);
		this.setEmptyTableWidget(emptyTable);
		
		this.setMinimumTableWidth(350, Unit.PX);
		this.setHeight(body.getClientHeight() +"px");
		
		
	}
	
	@Override
	public void onLoad(){
		
	}//end onLoad
	
	
	public void reset(){
		
		console.log("refreshing the grid");
		dataProv.updateRowCount(0, false);
		
		Timer timer = new Timer(){

			@Override
			public void run() {
				Ajax.get(AdminUrl.USER_LIST).done(new Function(){
					@Override
					public void f(){
						JsArray<AppUserJson> jsList = JsonUtils.unsafeEval((String)this.arguments(0)).cast();
						ArrayList<AppUserJson> list = new ArrayList<AppUserJson>();
						for(int i=0; i< jsList.length(); i++){
							list.add(jsList.get(i));
						}
						dataProv.updateRowData(0, list);
						$this.redraw();
						
					}
				});//end Ajax call
				
			}};//end timer
			
		timer.schedule(4000);
	}
	
	

}
