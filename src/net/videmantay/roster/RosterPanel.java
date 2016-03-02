package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCardContent;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialLabel;

import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.query.client.*;

import net.videmantay.roster.json.RosterJson;
import net.videmantay.student.json.RosterDetailJson;

public class RosterPanel extends Composite {

	private static RosterPanelUiBinder uiBinder = GWT.create(RosterPanelUiBinder.class);

	interface RosterPanelUiBinder extends UiBinder<Widget, RosterPanel> {
	}
	@UiField
	public MaterialCardContent cardContent;
	@UiField
	public MaterialCardTitle cardTitle;
	@UiField
	public MaterialLabel cardDescript;
	
	public RosterPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setData(RosterDetailJson data){
		$(this).data("roster", data);
		cardTitle.setText($(this).data("roster", RosterDetailJson.class).getTitle());
		cardDescript.setText($(this).data("roster", RosterDetailJson.class).getDescription());
		$(this).data("roster", RosterDetailJson.class).getId();
		
		
		//set click functions now
		$(cardContent).click(new Function(){
			@Override
			public void f(){
				$(body).trigger(RosterEvent.ROSTER_VIEW, $(this).data("roster", RosterDetailJson.class).getId());
			}
		});
	}
	
}
