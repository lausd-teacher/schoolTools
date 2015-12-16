package net.videmantay.admin;

import com.google.common.base.Preconditions;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Widget;
import static gwtquery.plugins.ui.Ui.Ui;

import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Form;
import org.gwtbootstrap3.client.ui.Input;
import org.gwtbootstrap3.client.ui.ListBox;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.SubmitButton;

import net.videmantay.json.AppUser;

public class AppUserEditor extends Composite implements Editor<AppUser> {

	@Ignore
	@UiField
	Form userForm;
	
	@Ignore
	@UiField
	Button submit;
	
	@Ignore
	@UiField
	Hidden userInput;
	
	@Ignore
	@UiField
	Button cancelSave;
	
	@UiField
	Input firstName;
	
	@UiField
	Input lastName;
	
	@UiField
	Input acctId;
	
	@UiField
	ListBox title;
	
	
	
	private AppUser oldValue ;
	private boolean userUpdate = true;
	private static AppUserEditorUiBinder uiBinder = GWT.create(AppUserEditorUiBinder.class);

	interface AppUserEditorUiBinder extends UiBinder<Widget, AppUserEditor> {
	}
	
	interface UserDriver extends SimpleBeanEditorDriver<AppUser,AppUserEditor>{
		}

	UserDriver driver = GWT.create(UserDriver.class);
	public AppUserEditor() {
		initWidget(uiBinder.createAndBindUi(this));
		driver.initialize(this);
		
		this.setSize("100%", "100%");
	}
	
	@Override
	public void onLoad(){
		//cancel
		$(cancelSave).click(new Function(){
			@Override
			public boolean f(Event e){
				userForm.reset();
		$(this).as(Ui).trigger(AdminEvent.FORM_CANCEL);
		return true;
	}
		});
		
		//submit
		$(submit).click(new Function(){
			@Override
			public void f(){
				save();
			}
		});
	}
	
	public void edit(AppUser user){
		console.log("edit user called");
		if(user == null){
			user =  AppUser.create();
			userUpdate = false;
		}else{
			oldValue = user;
			userUpdate = true;
		}
		console.log("userUpdate is " + userUpdate + " set by edit()");
		driver.edit(user);
	}
	
	public void save(){
		console.log("save called");
		
			//show driver errors
			
			final AppUser userEdit = driver.flush();
			userEdit.setTitle(title.getSelectedValue());
		//	userInput.setValue(userEdit.toJson());
			console.log("user after flush : " + JsonUtils.stringify(userEdit));
			//console.log("user edit to query string is : " + userEdit.toQueryString());
			Ajax.post(AdminUrl.USER_SAVE, $$("user:" + JsonUtils.stringify(userEdit)))
			.done(new Function(){
				@Override 
				public void f(){
					AppUser recieved = JsonUtils.<AppUser>unsafeEval((String)this.getArgument(0));
						$(window).trigger(AdminEvent.SAVE_USER, recieved, userUpdate);
				}
			} );
			
				
			
			
		}	
	
	private void showErrors(){
	
	}
	
}
