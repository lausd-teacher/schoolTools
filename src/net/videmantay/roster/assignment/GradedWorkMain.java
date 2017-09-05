package net.videmantay.roster.assignment;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import static com.google.gwt.query.client.GQuery.*;

import java.util.ArrayList;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.html.Div;
import net.videmantay.roster.RosterUrl;
import net.videmantay.roster.json.GradedWorkJson;

public class GradedWorkMain extends Composite {

	private static GradedWorkMainUiBinder uiBinder = GWT.create(GradedWorkMainUiBinder.class);

	interface GradedWorkMainUiBinder extends UiBinder<Widget, GradedWorkMain> {
	}
	private final static Integer pageNum = 1;
	private final AsyncDataProvider<GradedWorkJson> providesKey = new AsyncDataProvider<GradedWorkJson>(){

		@Override
		protected void onRangeChanged(final HasData<GradedWorkJson> display) {
			Ajax.get(RosterUrl.LIST_ASSIGNMENTS, $$("pageNum:" + pageNum))
			.done(new Function(){
				@Override
				public void f(){
					JsArray<GradedWorkJson> list = JsonUtils.safeEval((String)this.arguments(0)).cast();
					ArrayList<GradedWorkJson> data = new ArrayList<GradedWorkJson>();
					for(int i = 0; i < list.length(); i++){
						data.add(list.get(i));
					}//end for
					display.setRowData(0, data);
					display.setRowCount(data.size());
				}
			});
			
		}
		
		@Override
		public Long getKey(GradedWorkJson value){
			return value.getId();
		}
		
	};
	
	private final AssignmentGrid assignmentGrid = new AssignmentGrid(providesKey);
	private final SimplePager pager = new SimplePager(){};
	private final GradedWorkForm form = new GradedWorkForm();
	
	@UiField
	HTMLPanel wrapper;
	
	
	@UiField
	Div gradedWorkViz;
	
	@UiField
	Div gradedWorkPager;
	
	@UiField
	MaterialAnchorButton fab;
	
	public GradedWorkMain() {
		initWidget(uiBinder.createAndBindUi(this));
		pager.setDisplay(assignmentGrid);
		gradedWorkViz.add(assignmentGrid);
		gradedWorkPager.add(pager);
		wrapper.add(form);
		
		//do fab
		fab.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				form.show();
				
			}});
	}//end const
	
	@Override
	public void onLoad(){
		gradedWorkViz.setSize("100%", "35em");
		assignmentGrid.setSize("100%","100%");
		assignmentGrid.setRowCount(0);
		$(body).on("", new Function(){
			@Override
			public boolean f(Event e, Object...o){
				
				
				return true;
			}
		});
	}

}
