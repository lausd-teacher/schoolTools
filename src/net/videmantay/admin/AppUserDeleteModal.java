package net.videmantay.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialToast;
import net.videmantay.admin.json.AppUserJson;

public class AppUserDeleteModal extends Composite {

	private static AppUserDeleteModalUiBinder uiBinder = GWT.create(AppUserDeleteModalUiBinder.class);

	interface AppUserDeleteModalUiBinder extends UiBinder<Widget, AppUserDeleteModal> {
	}

	@UiField
	MaterialModal deleteModal;
	
	@UiField
	MaterialAnchorButton confirmDeleteBtn;
	
	@UiField
	MaterialAnchorButton cancelDeleteBtn;
	
	@UiField
	HeadingElement deletedUserHeading;
	
	private AppUserJson user;
	
	public AppUserDeleteModal() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setdata(AppUserJson user){
		this.user = user;
		$(deletedUserHeading).html("<span>" + this.user.getAcctId() + "<br/>" 
				+ this.user.getFirstName() +"&nbsp;" + this.user.getLastName() 
				+"</span>");
	}
	
	public void show(){
		deleteModal.openModal();
	}
	
	public void close(){
		deleteModal.closeModal();
		user = null;
	}
	
	@Override
	public void onLoad(){
		confirmDeleteBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				String appUser = JsonUtils.stringify(user);
				Ajax.post(AdminUrl.USER_DELETE, $$("user:" + appUser))
				.done(new Function(){
					@Override
					public void f(){
						deleteModal.closeModal();
						user = null;
						MaterialToast.fireToast("User deleted", 2000);
						$(body).trigger(AdminEvent.DELETE_USER);
					}
				});
				
			}});
		
		cancelDeleteBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				deleteModal.closeModal();
				user=null;
				$(body).trigger(AdminEvent.HIDE_DELETE);
				
			}});
	}

}
