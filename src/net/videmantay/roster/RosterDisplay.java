package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;

import net.videmantay.roster.json.RosterJson;

public class RosterDisplay extends Composite implements RosterMain.Display{

	private static RosterDisplayUiBinder uiBinder = GWT.create(RosterDisplayUiBinder.class);

	@UiField
	RosterGrid rosterGrid;
	
	@UiField
	RosterForm rosterForm;
	
	interface RosterDisplayUiBinder extends UiBinder<Widget, RosterDisplay> {
	}

	public RosterDisplay() {
		initWidget(uiBinder.createAndBindUi(this));
		
		
	}
	
	@Override
	public void onLoad(){
		$(this).css($$("width:'100%', height:'100%'"));
		$(rosterForm).hide();
		rosterGrid.drawGrid();
		$(body).on("rosterredraw", new Function(){
			@Override
			public void f(){
				$(rosterForm).hide();
				rosterGrid.showEmptyList();
				Timer timer = new Timer(){
					@Override
					public void run(){
						rosterGrid.drawGrid();
					}
					
				};
				
				timer.schedule(3000);
			}
		});
	}

	@Override
	public void fab() {
		$(rosterGrid).hide();
		$(rosterForm).show();
		RosterJson roster = RosterJson.createObject().cast();
		rosterForm.edit(roster);
	}
	
	
}
