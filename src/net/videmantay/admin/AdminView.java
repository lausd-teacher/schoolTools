package net.videmantay.admin;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Icon;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.PanelBody;

import com.google.common.collect.ImmutableList;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQ;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import net.videmantay.json.AppUser;
import net.videmantay.json.AppUserList;
import net.videmantay.json.Stuff;

import static com.google.gwt.query.client.GQuery.*;
import static gwtquery.plugins.ui.Ui.Ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminView extends Composite {
	
	
	private final AppUserTable table = new AppUserTable();
	
	private final AppUserListProvider listProvider = new AppUserListProvider();
	

	@UiField
	PanelBody mainPanel;
	
	@UiField
	Modal modal;
	
	@UiField
	Modal deleteModal;
	
	@UiField
	Button deleteButton;
	
	@UiField
	Button cancelButton;
	
	@UiField(provided=true)
	AppUserEditor editor = new AppUserEditor();
	
	@UiField
	Icon userAddButton;

	private static AdminViewUiBinder uiBinder = GWT.create(AdminViewUiBinder.class);

	interface AdminViewUiBinder extends UiBinder<Widget, AdminView> {
	}

	public AdminView() {
	
		
		
		initWidget(uiBinder.createAndBindUi(this));
		modal.hide();
		deleteModal.hide();
		
	}
	
	public void onLoad(){
		mainPanel.add(table);
		listProvider.addDataDisplay(table);
		
		try{
		
		}catch(NumberFormatException e){
			console.log(e.getLocalizedMessage());
		}
		 $(window).resize(new Function(){
			 @Override
			 public boolean f(Event e){
	
				 return true;
			 }
		 });
		//set button action add, update , delete
		$(userAddButton).on("click", new Function(){
			@Override
			public void f(){
				editor.edit(null);
				modal.show();
			}
		});
		
		$(window).on(AdminEvent.UPDATE_USER, new Function(){
			@Override
			public boolean f(Event e, Object... o){
				AppUser user = (AppUser)o[0];
				console.log("Data from AdminView update: " + JsonUtils.stringify(user));
				editor.edit(user);
				modal.show();
				return true;
			}
		});
		
		$(window).on(AdminEvent.DELETE_USER, new Function(){
		
			@Override
			public boolean f(Event e, Object... o){
				AppUser deleteMe = (AppUser)o[0];
				console.log("The data retrieve from table size is: " + JsonUtils.stringify(deleteMe));
				$(deleteModal).data("user", deleteMe);
				deleteModal.show();
				return false;
			}
		});
		$(this).as(Ui).on(AdminEvent.FORM_CANCEL, new Function(){
			@Override
			public boolean f(Event e){
				e.preventDefault();
				
				modal.hide();
				return true;
			}
		});
		
		$(window).on(AdminEvent.SAVE_USER, new Function(){
			@Override
			public boolean f(Event e, Object...o){
				AppUser user = (AppUser)o[0];
				modal.hide();
				Window.alert("User "+ user.getFirstName() + "was saved");
				boolean update = (boolean)o[1];
				if(!update){
				listProvider.getList().add(user);
				}else{
					for(AppUser appUser: listProvider.getList()){
						if(appUser.getId() == user.getId()){
							listProvider.getList().remove(appUser);
							break;
						}
					}
					
					listProvider.getList().add(user);
				}
				return true;
			}
		});
		$(deleteButton).click(new Function(){
			@Override
			public void f(){
				AppUser deleteMe = $(deleteModal).data("user", AppUser.class);
				console.log("user json is : " + JsonUtils.stringify(deleteMe));
				Ajax.post(AdminUrl.USER_DELETE, $$("user:" + JsonUtils.stringify(deleteMe)))
				.done(new Function(){
					@Override
					public void f(){
						String check = (String)this.getArgument(0);
						console.log("check is equal to: " + check);
						if(check.equals("true")){
							listProvider.getList().remove($(deleteModal).data("user",AppUser.class));
							
						}else{
						 Window.alert("unable to delete user");
						}
						$(deleteModal).data("user", null);
					}
				});
				deleteModal.hide();
			}
		});
		
		$(cancelButton).click(new Function(){
			@Override
			public void f(){
				deleteModal.hide();
				$(deleteModal).data("user", null);
			}
		});
		
		
		//load the data list provider//
		Ajax.get(AdminUrl.USER_LIST).done(new Function(){
			@Override
			public void f(){
				AppUserList list = JsonUtils.<AppUserList>unsafeEval((String)this.getArgument(0));
				listProvider.getList().clear();
				listProvider.getList().addAll(list.getList());
			}
		});
		
	}

	

	

	

}
