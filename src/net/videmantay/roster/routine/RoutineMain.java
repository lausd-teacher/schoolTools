package net.videmantay.roster.routine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
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
import gwt.material.design.client.ui.MaterialFAB;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialModal;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.roster.json.RoutineConfigJson;

public class RoutineMain extends Composite {

	private static RoutineMainUiBinder uiBinder = GWT.create(RoutineMainUiBinder.class);

	interface RoutineMainUiBinder extends UiBinder<Widget, RoutineMain> {
	}

	@UiField
	MaterialCollapsible routineList;
	
	@UiField
	MaterialFAB FAB;
	
	@UiField
	MaterialModal routineForm;
	
	ClickHandler fabClick = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			routineForm.open();
			
		}};
	private  final RosterJson roster = window.getPropertyJSO("currentClassroom").cast();
	
	public RoutineMain() {
		initWidget(uiBinder.createAndBindUi(this));
		console.log("routine main called");
		FAB.addClickHandler(fabClick);
		
	}
	
	@Override
	public void onLoad(){
		drawGrid();
	}
	
	
	private void drawGrid(){
		console.log("called RoutineMain-drawGrid()");
		
		
		for(int i = 0; i < roster.getRoutines().length(); i++){
			MaterialCollapsibleItem item = new MaterialCollapsibleItem();
			 MaterialCollapsibleHeader header = new MaterialCollapsibleHeader();
			MaterialLabel routineTitle =new MaterialLabel();
			routineTitle.setText(roster.getRoutines().get(i).getTitle());
			routineTitle.setFontSize("1.5em");
			routineTitle.setTruncate(true);
			routineTitle.getElement().getStyle().setPosition(Position.RELATIVE);
			routineTitle.setCenterOn(CenterOn.CENTER_ON_SMALL);
			header.add(routineTitle);
			item.add(header);
			item.getElement().setId(roster.getRoutines().get(i).getId() +"");
			if(roster.getRoutines().get(i).getIsDefault()){

				 MaterialCollapsibleBody body =new MaterialCollapsibleBody();
				 body.add(new RoutineItem(roster.getRoutineConfig()));
				 item.add(body);
				 item.setActive(true);
				 routineList.insert(item, 0);
			}else{
				item.addFocusHandler(new FocusHandler(){

					@Override
					public void onFocus(FocusEvent event) {
						event.stopPropagation();
						event.preventDefault();
						if(item.getBody()== null || !item.getBody().isAttached()){
						MaterialCollapsibleBody body = new MaterialCollapsibleBody();
						item.add(body);
						MaterialLoader.loading(true, body);
						//now Ajax to get RosterConfig
						Ajax.ajax(Ajax.createSettings().setUrl("/roster/"+roster.getId() +"/routine/"+item.getId())
								.setType("GET").setContentType("application/json")).then(new Function(){
									@Override
									public void f(){
										RoutineConfigJson data = JsonUtils.safeEval((String)this.arguments(0)).cast();
										console.log("after ajax call to routine /id the routine config is " );
										console.log(data);
										if(data!=null){
											MaterialLoader.loading(false, body);
											RoutineItem ri = new RoutineItem(data);
											body.clear();
											body.add(ri);
										}
										
									}
								});
						}
						
						
					}});
			routineList.add(item);
			}
		}
	}
	

}
