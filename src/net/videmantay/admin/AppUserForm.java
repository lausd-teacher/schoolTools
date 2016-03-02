package net.videmantay.admin;


import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.query.client.Function;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialInput;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.html.Label;

import net.videmantay.admin.json.AppUserJson;
import net.videmantay.shared.UserRoles;
import net.videmantay.shared.UserStatus;

import java.util.HashSet;


public class AppUserForm extends Composite {

	private static AppUserFormUiBinder uiBinder = GWT.create(AppUserFormUiBinder.class);

	interface AppUserFormUiBinder extends UiBinder<Widget, AppUserForm> {
	}
	private AppUserJson copy = null;
	
	@UiField
	FormPanel form;
	
	@UiField
	MaterialListBox title;
	
	@UiField
	MaterialInput acctId;
	
	@UiField
	MaterialInput firstName;
	
	@UiField
	MaterialInput lastName;
	
	@UiField
	MaterialInput middleName;
	
	@UiField
	MaterialInput extendedName;
	
	@UiField
	MaterialCard rolesSelectCard;
	
	@UiField
	MaterialCheckBox inactiveCheck;
	
	@UiField
	MaterialCheckBox studentCheck;
	
	@UiField
	MaterialCheckBox teacherCheck;
	
	@UiField
	MaterialCheckBox facultyCheck;
	
	@UiField
	MaterialCheckBox adminCheck;
	
	@UiField
	MaterialAnchorButton submitBtn;
	
	@UiField
	MaterialAnchorButton cancelBtn;
	
	@UiField
	MaterialCard card;
	
	@UiField
	Label firstNameError;
	
	@UiField
	Label lastNameError;
	
	@UiField
	Label acctIdError;
	
	@UiField
	Label roleError;
	
	HashSet<String> checkBoxState = new HashSet<String>();
	private final String STUDENT = "student";
	private final String TEACHER = "teacher";
	private final String FACULTY = "faculty";
	private final String ADMIN = "admin";
	//handy functions
	Function validateInput = new Function(){
		@Override
		public void f(){
			if($(this).is(":invalid")){
				$(this).next(".errorLabel").show();
				$(this).addClass("inputError");
			}else{
				$(this).next(".errorLabel").hide();
			}
			
			
		}
	};
	
	public AppUserForm() {
		initWidget(uiBinder.createAndBindUi(this));
		title.addItem("Mr.", "MR");
		title.addItem("Mrs.", "MRS");
		title.addItem("Ms.","MS");
		this.setData(null);
		
	}
	
	
	
	
	
	
	@Override
	public void onLoad(){
		$("input").blur(validateInput);
		$(".errorLabel").hide();

		
		studentCheck.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
			if(studentCheck.getValue()){
				checkBoxState.add(STUDENT);
				if(checkBoxState.contains(TEACHER)){
					checkBoxState.remove(TEACHER);
				}
				if(checkBoxState.contains(FACULTY)){
					checkBoxState.remove(FACULTY);
				}
				if(checkBoxState.contains(ADMIN)){
					checkBoxState.remove(ADMIN);
				}
				console.log("student true hide all others");
				$(".gwt-CheckBox").not(studentCheck.getElement()).not(inactiveCheck.getElement()).hide();
			}else{
				checkBoxState.remove(STUDENT);
				$(".gwt-CheckBox").show();
			}	
			}});
		teacherCheck.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if(teacherCheck.getValue()){
					checkBoxState.add(TEACHER);
					if(checkBoxState.contains(STUDENT)){
						checkBoxState.remove(STUDENT);
					}
					if(checkBoxState.contains(FACULTY)){
						checkBoxState.remove(FACULTY);
					}
				$(studentCheck,facultyCheck).hide();
				}else{
					checkBoxState.remove(TEACHER);
					if(adminCheck.getValue()){
						facultyCheck.setVisible(true);
					}else{
					$(studentCheck,facultyCheck).show();
					}
				}
				
			}});
		facultyCheck.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if(facultyCheck.getValue()){
					checkBoxState.add(FACULTY);
					if(checkBoxState.contains(TEACHER)){
						checkBoxState.remove(TEACHER);
						teacherCheck.setValue(false);
					}
					if(checkBoxState.contains(STUDENT)){
						checkBoxState.remove(STUDENT);
						studentCheck.setValue(false);
					}
				$(studentCheck,teacherCheck).hide();
				}else{
					checkBoxState.remove(FACULTY);
					if(adminCheck.getValue()){
						teacherCheck.setVisible(true);
					}else{
					$(studentCheck,teacherCheck).show();
					}
				}	
			}});
		adminCheck.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if(adminCheck.getValue()){
					checkBoxState.add(ADMIN);
					if(checkBoxState.contains(STUDENT)){
						checkBoxState.remove(STUDENT);
					}
				$(studentCheck).hide();
				}else{
					checkBoxState.remove(ADMIN);
					if(teacherCheck.getValue() || facultyCheck.getValue()){
						studentCheck.setVisible(false);
					}else{
					$(studentCheck).show();
					}
				}
				
			}});
		
		submitBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				$(body).trigger(AdminEvent.SAVE_USER);
				
			}});
		
		cancelBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				hide();
				
				
			}});	
	}
	
		public void show(){
			$(body).trigger(AdminEvent.SHOW_SAVE);
		}
		public void hide(){
			form.reset();
			$(".gwt-CheckBox").show();
			$(body).trigger(AdminEvent.HIDE_SAVE);
			
		}
		
		public boolean hasErrors(){
			try {
			copy.setAcctId(acctId.getValueOrThrow());
			console.log("acctId is equal to : " + copy.getAcctId());
			if(copy.getAcctId() == null ||copy.getAcctId().equalsIgnoreCase("")){
				$(acctId).addClass("inputError");
				$(acctIdError).show();
				throw new Exception();
				
			}
			copy.setFirstName(firstName.getValueOrThrow());
			if(copy.getFirstName() == null || copy.getFirstName().equalsIgnoreCase("")){
				$(firstName).addClass("inputError");
				$(firstNameError).show();
				throw new Exception();
			}
			copy.setLastName(lastName.getValueOrThrow());
			if(copy.getLastName() == null || copy.getLastName().equals("")){
				$(lastName).addClass("inputError");
				$(lastNameError).show();
				throw new Exception();
			}
			copy.setMiddleName(middleName.getValue());
			copy.setExtendedName(extendedName.getValue());
			copy.setTitle(title.getSelectedValue());
			String status;
			status = inactiveCheck.getValue()?UserStatus.INACTIVE.name():UserStatus.ACTIVE.name();
			copy.setUserStatus(status);
			//clear the roles and set again
			copy.clearRoles();
			if(checkBoxState.size() <= 0){
				$(roleError).show();
				throw new Exception();
			}else{
				$(roleError).hide();
			}
			for(String s:checkBoxState){
				copy.addRole(s.toUpperCase());
			}
			
			
			if($(".errorLabel").is(":visible")){
				return true;
			}
			
				if($(firstName,lastName).hasClass("inputError")){
					$(firstName,lastName).removeClass("inputError");
				}
				
				
				console.log("user from form is " + JsonUtils.stringify(copy));
				return false;
			} catch (Exception e) {
				if($(acctId).hasClass("inputError")){
				
				}
				
				return true;
			}
		}
		
		
		
		public void setData(AppUserJson data){
			if(data == null){
				data = AppUserJson.createObject().cast();
			}
			copy = data;
			$(".gwt-CheckBox").hide();
			if(data.getAcctId()== null){
				data.defaults();
			}
			
			int index = 0;
			switch(data.getTitle().toLowerCase()){
			case "ms":index =0;break;
			case "mrs":index = 1;break;
			case "mr":index = 2;break;
			}
			title.setSelectedIndex(index);
			acctId.setValue(data.getAcctId());
			firstName.setValue(data.getFirstName());
			lastName.setValue(data.getLastName());
			middleName.setValue(data.getMiddleName());
			inactiveCheck.setVisible(true);
			if(data.getUserStatus() == null || data.getUserStatus().equalsIgnoreCase(UserStatus.ACTIVE.name())){
				inactiveCheck.setValue(false);
			}else{
				inactiveCheck.setValue(true);
			}
			
			if(data.getRoles() == null || data.getRoles().length <= 0){
				String[] roles= new String[0];
				data.setRoles(roles);
				studentCheck.setVisible(true);studentCheck.setValue(false);
				facultyCheck.setVisible(true);facultyCheck.setValue(false);
				teacherCheck.setVisible(true);teacherCheck.setValue(false);
				adminCheck.setVisible(true);adminCheck.setValue(false);
			}else{
			for(String s: data.getRoles()){
				checkBoxState.add(s.toLowerCase());
				switch(s.toLowerCase()){
				case "student": studentCheck.setValue(true);studentCheck.setVisible(true);break;
				case "faculty": facultyCheck.setValue(true);facultyCheck.setVisible(true);break;
				case "teacher":teacherCheck.setValue(true);teacherCheck.setVisible(true);break;
				case "admin":adminCheck.setValue(true);adminCheck.setVisible(true);break;
				}
		
			}//end else
			}
		}
		
		public void cancel(){
			console.log("form was canceled");
			copy = null;
			form.reset();
			$(".errorLabel").hide();
			$(form).find("input").each(new Function(){@Override public void f(){console.log("Removing error classes");$(this).removeClass("inputError");}});
			 checkBoxState.clear();
		}
		
		public AppUserJson submit(){
			form.reset();
			AppUserJson retrieve = copy;
			copy = null;
			checkBoxState.clear();
			return retrieve;
			
		}
}
