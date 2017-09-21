package net.videmantay.roster.incident;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialChip;
import gwt.material.design.client.ui.MaterialLabel;
import net.videmantay.student.json.IncidentJson;

public class IncidentItem extends Composite{

	private static IncidentItemUiBinder uiBinder = GWT.create(IncidentItemUiBinder.class);

	interface IncidentItemUiBinder extends UiBinder<Widget, IncidentItem> {
	}
	
	@UiField
	public DivElement svgImage;
	
	@UiField
	public MaterialLabel name;
	
	@UiField
	public MaterialChip value;
	
	@UiField
	public MaterialAnchorButton editIncidentBtn;
	
	@UiField
	public MaterialAnchorButton deleteIncidentBtn;

	public IncidentItem() {
		initWidget(uiBinder.createAndBindUi(this));	
	}
	
	private IncidentJson incident;
	
	public void setIncident(IncidentJson incident){
		this.incident = incident;
		draw();
	}
	
	public IncidentJson getIncident(){
		return this.incident;
		
	}
	
	private void draw(){
		
		name.setText(incident.getName());
		if(incident.getPoints() < 0){
			//badge color red;
			value.setBackgroundColor(Color.RED);
		}else{
			//badge color green
			value.setBackgroundColor(Color.GREEN);
		}
		
		value.setText(""+ incident.getPoints());
		///set up the badge size and center the text
		svgImage.setInnerHTML(IncidentImageUtil.imageHTML(incident.getImageUrl()));
	}
	
	@Override
	public void onLoad(){
		
	}

}
