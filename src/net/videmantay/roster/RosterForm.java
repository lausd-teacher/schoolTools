package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialInput;
import net.videmantay.admin.json.AppUserJson;
import net.videmantay.roster.json.RosterJson;
import static com.google.gwt.query.client.GQuery.*;

import java.text.ParseException;

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
	MaterialInput startDate;
	
	@UiField
	MaterialInput endDate;
	
	@UiField
	MaterialAnchorButton submitBtn;
	
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
	}
	
	@Override
	public void onLoad(){
	
	}
	
	public void edit(RosterJson r){
		this.data = Preconditions.checkNotNull(r);
		title.setValue(data.getTitle());
		description.setValue(data.getDescription());
		roomNum.setValue(data.getRoomNum());
		startDate.setValue(data.getStartDate());
		endDate.setValue(data.getEndDate());	
	}
	
	public boolean hasErrors(){
		try {
			data.setTitle(title.getValueOrThrow());
		} catch (ParseException e) {
			return true;
		}
		
		data.setDescription(description.getValue());
		
		data.setRoomNum(roomNum.getValue());
		try {
			data.setStartDate(startDate.getValueOrThrow());
		} catch (ParseException e) {
			return true;
		}
		try {
			data.setEndDate(endDate.getValueOrThrow());
		} catch (ParseException e) {
			return true;
		}
		
		return false;
		
	}
	
	public void submit(){
		if(hasErrors()){
			//show the errors
			return;
		}
		Ajax.post(RosterUrl.CREATE_RSOTER, $$("roster:" + JsonUtils.stringify(data)));
		$(body).trigger("rosterredraw");
	}

}
