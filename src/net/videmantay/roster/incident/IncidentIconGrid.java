package net.videmantay.roster.incident;

import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.plugins.effects.PropertiesAnimation.EasingCurve;

import static com.google.gwt.query.client.GQuery.*;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardContent;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialRow;

public class IncidentIconGrid  extends Composite{

	
	private final String[] iconList = {"doctor","scientist","pharmacist","xrayMain" ,"happyGirl","happyBoy","goldMedal",
			"rocket" ,"yellowBeaker", "redBeaker", "happyRainbowCloud" , "sadRainCloud","hardLinedBell", "hornedOwl",
			"podium", "deskTop", "owlComp",  "mathGirl", "scienceBoy", "restroom", "biohazardWarning", "radioactiveWarning",
			"brokenglassWarning", "yellowRadioactive","noHW",
			};
	public String selectedIcon = iconList[0];
	public  MaterialRow grid = new MaterialRow();
	
	
	public IncidentIconGrid(){
		this.initWidget(grid);
		grid.setId("iconGrid");
		$(grid).css($$("border:1px solid Silver;height:25em;padding:1em;position:absolute;z-index:5;overflow-y:scroll;"));
	}
	
	@Override
	public void onLoad(){
		for(String s: iconList){
			MaterialColumn c = new MaterialColumn();
			final MaterialCard card = new MaterialCard();
			card.setHoverable(true);
			MaterialCardContent content = new MaterialCardContent();
			
			c.setGrid("s6 m4 l3");
			$(c).css("cursor", "pointer");
			String html = IncidentImageUtil.imageHTML(s);
			final HTMLPanel image = new HTMLPanel(html);
			content.add(image);
			card.add(content);
			c.add(card);
			grid.add(c);
			c.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					GQuery svg = $(event.getSource().toString()).find("svg");
					$(body).trigger("incidentIconChange", $(svg).attr("data-incident-image"));
					$("#iconGrid").hide();
				}});
		}
		$("#iconGrid").hide();
	}
	
	@Override
	public void onUnload(){
		$("div > svg.incidentImage").off();
	}	
}
