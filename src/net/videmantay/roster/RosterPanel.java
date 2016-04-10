package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCardContent;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialLabel;
import static com.google.gwt.query.client.GQuery.*;
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
	private RosterDetailJson rosterDetail;
	public RosterPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setData(RosterDetailJson data){
		console.log("Set data called for " + data.getTitle());
		rosterDetail = data;
		cardTitle.setText(data.getTitle());
		cardDescript.setText(data.getDescription());	
		//set click functions now
	}
	
	@Override
	public void onLoad(){
		$(this).find(".card-title").click(new Function(){
			@Override
			public boolean f(Event e){
				console.log("roster panel clicked");
				History.newItem("roster/" + rosterDetail.getId());
				return true;
			}
		});
	}
	
}
