package net.videmantay.roster;

import static com.google.gwt.query.client.GQuery.*;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.i18n.shared.DateTimeFormat;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardContent;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialRow;
import net.videmantay.shared.json.RosterInfoJson;

public class RosterPanel extends Composite {

	private static RosterPanelUiBinder uiBinder = GWT.create(RosterPanelUiBinder.class);

	interface RosterPanelUiBinder extends UiBinder<Widget, RosterPanel> {
	}

	@UiField
	MaterialCard card;
	
	@UiField
	public MaterialCardContent cardContent;
	@UiField
	public MaterialCardTitle cardTitle;
	@UiField
	public MaterialLabel cardDescript;
	@UiField
	public MaterialLabel rosDate;
	@UiField
	public MaterialLabel rosRoom;
	private RosterInfoJson rosterInfo;
	
	private MouseOverHandler mouseOver = new MouseOverHandler(){
		@Override
		public void onMouseOver(MouseOverEvent e){
			e.stopPropagation();
			$(card).addClass("z-depth-5", "rosterPanel-active")
			.find(".card-title").css("fontWeight", "500");
			
		}
	};
	private MouseOutHandler mouseOut = new MouseOutHandler(){
		@Override
		public void onMouseOut(MouseOutEvent e){
			e.stopPropagation();
			$(card).removeClass("z-depth-5", "rosterPanel-active")
			.find(".card-title").css("fontWeight", "300");
		}
	};
	
	
	/* Handler this in RosterMain */
	private ClickHandler promptDelete = new ClickHandler(){
		@Override
		public void onClick(ClickEvent e){
			
			e.stopPropagation();
			e.preventDefault();
			$(body).trigger("promtDeleteRoster", rosterInfo);
			console.log("Prompt delete click this should trigger with data of: ");
			console.log(rosterInfo);
		}
	};
	
	/*Handler this in RosterMain */
	private ClickHandler updateForm = new ClickHandler(){
		@Override
		public void onClick(ClickEvent e){
			e.stopPropagation();
			e.preventDefault();
			console.log("going to trigger update with roster:");
			console.log(rosterInfo);
			$(body).trigger("showRosterForm",rosterInfo);
		}
	};
	
	
	
	
	
	public RosterPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		this.addStyleName("rosterPanel");
		card.addDomHandler(mouseOver, MouseOverEvent.getType());
		card.addDomHandler(mouseOut, MouseOutEvent.getType());
		
	
	}
	
	public void setData(RosterInfoJson data){
		rosterInfo = data;
		console.log("Set data called for " + data.getName());
		cardTitle.setText(data.getName());
		String descript = data.getDescription() ==null || data.getDescription().isEmpty()? "This roster was imported from Schoology.": data.getDescription();
		cardDescript.setText(descript);
		this.setColor(data.getColor());
		
		//use the id as dropdown activator
		cardTitle.getIcon().setActivates("r-"+data.getId());
		console.log("activator for " + cardTitle.getIcon().getActivates());
		final MaterialDropDown rosterDropdown = new MaterialDropDown();
		rosterDropdown.setActivator("r-"+data.getId());
		console.log("activator by " + rosterDropdown.getActivator());
		final MaterialLink deleteRosterLink = new MaterialLink();
		deleteRosterLink.setText("Delete");
		deleteRosterLink.setIconType(IconType.DELETE);
		deleteRosterLink.setIconPosition(IconPosition.LEFT);
		deleteRosterLink.setIconFontSize(0.95, Unit.EM);
		deleteRosterLink.setFontSize("0.5em");
		deleteRosterLink.addClickHandler(promptDelete);
		
		final MaterialLink updateRosterLink = new MaterialLink();
		updateRosterLink.setText("Edit");
		updateRosterLink.setIconType(IconType.EDIT);
		updateRosterLink.setIconPosition(IconPosition.LEFT);
		updateRosterLink.setIconFontSize(0.95, Unit.EM);
		updateRosterLink.setFontSize("0.5em");
		updateRosterLink.addClickHandler(updateForm);
		
		//color row
		MaterialRow row = new MaterialRow();
		row.setPadding(0);
		MaterialColumn col;
		for(final String s: RosterColors.colors){
			col = new MaterialColumn();
			col.setGrid("s1");
			col.setMargin(2);
			MaterialButton button = new MaterialButton();
			button.setBackgroundColor(Color.fromStyleName(s));
			button.setSize("20px", "20px");
			button.setPadding(3);
			button.setMarginRight(5);
			button.addClickHandler(new ClickHandler(){
				@Override
				public void onClick(ClickEvent e){
					e.stopPropagation();
					e.preventDefault();
					setColor(s);
					rosterInfo.setColor(s);
					//save to db ///
					Ajax.ajax(Ajax.createSettings()
							.setContentType("application/json")
							.setType("post")
							.setDataType("json")
							.setData(rosterInfo)
							.setUrl("/roster/" + rosterInfo.getId()));
				}
			});
			col.add(button);
			row.add(col);
		}
		MaterialLink colorLink = new MaterialLink();
		colorLink.add(row);
		
		rosterDropdown.add(updateRosterLink);
		rosterDropdown.add(deleteRosterLink);
		rosterDropdown.add(colorLink);
		rosterDropdown.setConstrainWidth(true);
		card.add(rosterDropdown);	
		return;	
	}
	
	@Override
	public void onLoad(){
		$(card).css("minWidth","100px").css("minHeight", "75px").css("cursor", "pointer").click(new Function(){
			@Override
			public boolean f(Event e){
				e.stopPropagation();
				e.preventDefault();
				console.log("click a panel with roster name of " + rosterInfo.getName());
				window.setPropertyJSO("currentRoster", rosterInfo);	
				
				History.newItem("classroom");
				return true;
			}
		});
		
	}
	
	public void setColor(String color){
		if(color == null || color.isEmpty() || Color.fromStyleName(color) == null ){
			color = Color.RED.getCssName();
		}
		card.setBackgroundColor(Color.fromStyleName(color));
		cardContent.setTextColor(Color.fromStyleName(color));
	}
	
	

}
