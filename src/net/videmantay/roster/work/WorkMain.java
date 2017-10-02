package net.videmantay.roster.work;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.query.client.Function;
import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.emptystate.MaterialEmptyState;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialRow;
import net.videmantay.roster.json.GradedWorkJson;


public class WorkMain extends Composite {

	private static WorkMainUiBinder uiBinder = GWT.create(WorkMainUiBinder.class);

	interface WorkMainUiBinder extends UiBinder<Widget, WorkMain> {
	}
	
	@UiField
	MaterialEmptyState emptyState;
	@UiField
	DivElement assignmentPageWrpaper;
	@UiField
	MaterialCollapsible assignmentCollapsible;

	public WorkMain() {
		initWidget(uiBinder.createAndBindUi(this));
		Ajax.get("/roster/work").then(new Function(){
			@Override
			public void f(){
				window.setPropertyJSO("assignments", JsonUtils.safeEval((String)this.arguments(0)));
				console.log(window.getPropertyJSO("assignments"));
				drawGrid();
			}
		});
	}
	
	private void drawGrid(){
		JsArray<GradedWorkJson> assignments =  window.getPropertyJSO("assignments").cast();
		if(assignments.length() <= 0){
			emptyState.setVisible(true);
		}else{
			assignmentPageWrpaper.getStyle().setDisplay(Display.BLOCK);
			for(int i = 0; i < assignments.length(); i++){
				console.log("from assignments array");
				console.log(assignments);
				assignmentCollapsible.add(new GradedWorkItem(assignments.get(i), i));
				
			}
			
		}
	}
	
	@Override
	public void onUnload(){
		window.setPropertyJSO("assignments", null);
	}

}
