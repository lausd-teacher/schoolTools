package net.videmantay.roster.routine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.CenterOn;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialCollapsibleHeader;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialLabel;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.roster.json.RoutineConfigJson;

public class RoutineMain extends Composite {

	private static RoutineMainUiBinder uiBinder = GWT.create(RoutineMainUiBinder.class);

	interface RoutineMainUiBinder extends UiBinder<Widget, RoutineMain> {
	}

	@UiField
	MaterialCollapsible routineList;
	private JsArray<RoutineConfigJson> configs;
	private  final RosterJson roster = window.getPropertyJSO("roster").cast();
	
	public RoutineMain() {
		initWidget(uiBinder.createAndBindUi(this));
		console.log("routine main called");
		//make a call to get rosterConfigs
		Ajax.ajax(Ajax.createSettings().setUrl("/roster/routinefull")
				.setType("GET")).done(new Function(){
			@Override
			public void f(){
				
				configs = JsonUtils.safeEval((String)this.arguments[0]).cast();
				for(int i= 0; i < roster.getRoutines().length(); i++){
					String routineId = roster.getRoutines().get(i).getId()+"";
					for(int j=0; j < configs.length();j++){
						String configId = configs.get(j).getId()+"";
						if(routineId.equalsIgnoreCase(configId)){
							configs.get(j).setRoutine(roster.getRoutines().get(i));
							break;
						}
					}
				}
				drawGrid();
			}
		});
	}
	
	private void drawGrid(){
		console.log("called RoutineMain-drawGrid()");
		for(int i = 0; i < configs.length(); i++){
			MaterialCollapsibleItem item = new MaterialCollapsibleItem();
			 MaterialCollapsibleHeader header = new MaterialCollapsibleHeader();
			 MaterialCollapsibleBody body =new MaterialCollapsibleBody();
			MaterialLabel routineTitle =new MaterialLabel();
			routineTitle.setText(configs.get(i).getRoutine().getTitle());
			routineTitle.setFontSize("1.5em");
			routineTitle.setTruncate(true);
			routineTitle.getElement().getStyle().setPosition(Position.RELATIVE);
			routineTitle.setCenterOn(CenterOn.CENTER_ON_SMALL);
			header.add(routineTitle);
			item.add(header);
			body.add(new RoutineItem(configs.get(i)));
			item.add(body);
			routineList.add(item);
		}
	}

}
