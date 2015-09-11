package net.videmantay.admin;

import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.ModalBody;
import org.gwtbootstrap3.client.ui.gwt.DataGrid;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.IdentityColumn;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;

import static com.google.gwt.query.client.GQuery.*;

import static gwtquery.plugins.ui.Ui.Ui;

import net.videmantay.json.*;

public class AppUserTable extends DataGrid<AppUser> {

	TextColumn<AppUser> acctIdCol = new TextColumn<AppUser>(){

		@Override
		public String getValue(AppUser object) {
			return object.getAcctId();
		}};
		
	TextColumn<AppUser> titleCol = new TextColumn<AppUser>(){

			@Override
			public String getValue(AppUser object) {
				return object.getTitle();
			}};
			
	TextColumn<AppUser> firstNameCol = new TextColumn<AppUser>(){

				@Override
				public String getValue(AppUser object) {
					return object.getFirstName();
				}};
	TextColumn<AppUser> lastNameCol = new TextColumn<AppUser>(){

		@Override		
		public String getValue(AppUser object) {
		return object.getLastName();
		}};
		
	TextColumn<AppUser> middleNameCol = new TextColumn<AppUser>(){
			@Override		
			public String getValue(AppUser object) {
			return object.getMiddleName();
			}};
			
	TextColumn<AppUser> rolesCol = new TextColumn<AppUser>(){
				@Override		
				public String getValue(AppUser object) {
					StringBuilder sb = new StringBuilder();
					for(int i = 0 ; i < object.getRoles().length; i++){
						if(i >0){
							sb.append(";");
						}// end if
						sb.append(object.getRoles()[i]);
					}//end for
				return sb.toString();
				}};
	IdentityColumn<AppUser> actionCol = new IdentityColumn<AppUser>(new AbstractCell<AppUser>("click", "mouseover", "touch"){

		@Override
		public void render(com.google.gwt.cell.client.Cell.Context context, AppUser value, SafeHtmlBuilder sb) {
			sb.appendHtmlConstant("<table><tr><td><i class='user-grid-delete fa fa-trash-o'></i></td><td><i class='user-grid-update fa fa-pencil'></i></td><td><i class='user-grid-more fa fa-ellipsis-v'></i></td></tr></table>");
			
		}
		public void onBrowserEvent(Cell.Context context,
                Element parent,
                AppUser value,
                NativeEvent event,
                ValueUpdater<AppUser> valueUpdater){
			if(event.getType().equals("click")){
				if($(event.getEventTarget()).hasClass("user-grid-delete")){
					AppUser[] users = {value};
					$(window).trigger(AdminEvent.DELETE_USER, users);
					console.log("value passed is: " + JsonUtils.stringify(value));
				}//end if user-grid-delete
			if($(event.getEventTarget()).hasClass("user-grid-update")){
				$(window).trigger(AdminEvent.UPDATE_USER, value);
			}//end user-grid-update
			}//end if click
		}	
	});
					
		
			
		
	AppUserTable(){
		//set Columns
		this.addColumn(acctIdCol, "Acct Id");
		this.addColumn(titleCol, "Title");
		this.addColumn(firstNameCol, "First Name");
		this.addColumn(lastNameCol, "Last Name");
		this.addColumn(rolesCol, "Roles");
		this.addColumn(actionCol);
		
		//set Style
		this.setBordered(true);
		this.setStriped(true);
		this.setHover(true);
		//set Size
		this.setWidth("100%");
		this.setHeight("350px");
		
	
	}
}
