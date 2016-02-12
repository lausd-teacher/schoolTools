package net.videmantay.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialToast;
import net.videmantay.admin.json.AppUserJson;

import static com.google.gwt.query.client.GQuery.*;

import org.gwtbootstrap3.extras.animate.client.ui.Animate;
import org.gwtbootstrap3.extras.animate.client.ui.constants.Animation;

import com.google.gwt.query.client.*;
import com.google.gwt.query.client.plugins.ajax.Ajax;

public class AdminMain extends Composite {

	private static AdminMainUiBinder uiBinder = GWT.create(AdminMainUiBinder.class);

	interface AdminMainUiBinder extends UiBinder<Widget, AdminMain> {
	}
	
	@UiField
	MaterialContainer main;
	
	private final AppUserGrid grid = new AppUserGrid();
	private final AppUserForm form =new AppUserForm();
	private final MaterialModal modal = new MaterialModal();
	private final AppUserDeleteModal deleteModal = new AppUserDeleteModal();
	
	
	@UiField
	MaterialButton fab;
	

	public AdminMain() {
		initWidget(uiBinder.createAndBindUi(this));
		main.add(grid);
		main.add(form);
		main.add(modal);
		main.add(deleteModal);
		$(form).hide();
		
		fab.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				$(body).trigger(AdminEvent.SHOW_SAVE);
			}});
	}
	
	@Override
	public void onLoad(){
		$(body).on(AdminEvent.SHOW_SAVE, new Function(){
			@Override
			public boolean f(Event e){
				$(grid).hide();
				form.setData((AppUserJson)this.arguments(0));
				$(form).fadeIn( );
				$(fab).hide();
				return true;
				
			}
		});
		
		$(body).on(AdminEvent.HIDE_SAVE, new Function(){
						@Override
						public void f(){
							$(grid).fadeIn(1000);
							$(fab).show();
							form.cancel();
							$(form).hide();
						}
		});
		
		$(body).on(AdminEvent.SAVE_USER, new Function(){
			@Override
			public void f(){
				if(form.hasErrors()){
					Animate.animate(form, Animation.SHAKE);
					Animate.removeAnimationOnEnd(form, "shake");
				}else{
					String user = JsonUtils.stringify(form.submit());
					
							Ajax.post(AdminUrl.USER_SAVE, $$("user:" + user))
							.done(new Function(){
								@Override
								public void f(){
									form.hide();
									grid.reset();
									MaterialToast.fireToast("User Success " + this.arguments(0), 2000);
								}
							});//end Ajax
							
				}
			}
		});
		
		$(body).on(AdminEvent.SHOW_DELETE,new Function(){
			@Override
			public boolean f(Event e, Object... o){
				AppUserJson user = (AppUserJson) o[0];
				deleteModal.setdata(user);
				deleteModal.show();
				return true;
			}
		});
		
		$(body).on(AdminEvent.DELETE_USER, new Function(){
			@Override
			public void f(){
				grid.reset();
			}
		});
	}
	
	

}
