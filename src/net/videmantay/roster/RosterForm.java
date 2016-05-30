package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.i18n.shared.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialInput;
import gwt.material.design.client.ui.MaterialLoader;
import net.videmantay.admin.json.AppUserJson;
import net.videmantay.roster.json.RosterJson;
import static com.google.gwt.query.client.GQuery.*;

import java.text.ParseException;
import java.util.Date;

import com.google.common.base.Preconditions;
import com.google.gwt.query.client.*;
import com.google.gwt.query.client.plugins.ajax.Ajax;


public class RosterForm extends Composite{

	private static RosterFormUiBinder uiBinder = GWT.create(RosterFormUiBinder.class);

	interface RosterFormUiBinder extends UiBinder<Widget, RosterForm> {
	}
	
	
	@UiField
	MaterialCard card;
	
	@UiField
	FormPanel form;
	
	@UiField
	MaterialInput title;
	
	@UiField
	MaterialInput description;
	
	@UiField
	MaterialInput roomNum;
	
	@UiField
	MaterialDatePicker startDate;
	
	@UiField
	MaterialDatePicker endDate;
	
	@UiField
	MaterialAnchorButton submitBtn;
	
	@UiField
	MaterialAnchorButton cancelBtn;
	
	private final RosterForm $this;
	
	private RosterJson data;

	public RosterForm() {
		initWidget(uiBinder.createAndBindUi(this));
		$this = this;
		submitBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				$this.submit();
			}});
		cancelBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				$this.canel();
				
			}});
	}
	
	@Override
	public void onLoad(){
	
	}
	
	public void edit(RosterJson r){
		DateTimeFormat df = DateTimeFormat.getFormat("yyyy-MM-dd");
		
		this.data = Preconditions.checkNotNull(r);
		title.setValue(data.getTitle());
		description.setValue(data.getDescription());
		roomNum.setValue(data.getRoomNum());
		
		
		
		startDate.setDate(df.parse(data.getStartDate()));
		endDate.setDate(df.parse(data.getEndDate()));	
	}
	
	public boolean hasErrors(){
		DateTimeFormat df = DateTimeFormat.getFormat("yyyy-MM-dd");
		try {
			data.setTitle(title.getValueOrThrow());
		} catch (ParseException e) {
			return true;
		}
		data.setDescription(description.getValue());
		data.setRoomNum(roomNum.getValue());
		data.setStartDate(df.format(startDate.getDate()));
		data.setEndDate(df.format(endDate.getDate()));
		
		return false;
		
	}
	
	public void submit(){
		if(hasErrors()){
			//show the errors
			return;
		}
		MaterialLoader.showLoading(true);
		Ajax.post(RosterUrl.CREATE_ROSTER, $$("roster:" + JsonUtils.stringify(data)))
		.done(new Function(){
			@Override
			public void f(){
				$(body).trigger("rosterredraw", this.getArgument(0));
				MaterialLoader.showLoading(false);
			}
		});
		//look into RosterDisplay to handle or Roster???
		
	}
	
	public void canel(){
		form.reset();
		$(this).hide();
		//look inot RosterDisplay to handle or Roster???
		$(body).trigger("rostercancel");
	}

}
