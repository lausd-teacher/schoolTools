package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCardContent;

import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.query.client.*;

import net.videmantay.roster.json.RosterJson;

public class RosterPanel extends Composite {

	private static RosterPanelUiBinder uiBinder = GWT.create(RosterPanelUiBinder.class);

	interface RosterPanelUiBinder extends UiBinder<Widget, RosterPanel> {
	}
	@UiField
	public MaterialCardContent cardContent;
	public  RosterJson roster;
	

	public RosterPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void onLoad(){
		$(cardContent).click(new Function(){
			@Override
			public void f(){
				History.newItem(roster.getId().toString());
			}
		});
	}
	public void setData(RosterJson data){
		//get data associated with panel
		roster = data;
		
		
	}
	
	

}
