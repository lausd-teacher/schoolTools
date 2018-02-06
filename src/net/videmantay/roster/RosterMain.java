package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import static com.google.gwt.query.client.GQuery.*;

import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialFAB;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialToast;
import net.videmantay.shared.json.RosterInfoJson;
/* Roster Main is just for layout no Ajax calls here
 * Ajax is done on RosterGrid and call the list written to window.
 */
public class RosterMain extends Composite {

	private static RosterMainUiBinder uiBinder = GWT.create(RosterMainUiBinder.class);

	interface RosterMainUiBinder extends UiBinder<Widget, RosterMain> {
	}

	@UiField
	MaterialFAB FAB;
	
	@UiField
	RosterGrid grid;
	
	@UiField
	RosterForm form;
	
	@UiField
	MaterialModal deleteRosterModal;
	
	@UiField
	MaterialAnchorButton deleteCancelBtn;
	
	@UiField
	MaterialAnchorButton deleteOkBtn;
	
	
	final JsArray<RosterInfoJson> rosterList = window.getPropertyJSO("rosterList").cast();
	
	ClickHandler createRosterHandler = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			//hide grid show form
			showForm();
		}};
	
	ClickHandler  formSubmitHanlder = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			
			form.submit().done(new Function(){
				@Override
				public void f(){
					form.reset();
					boolean noMatch = true;
					RosterInfoJson rosterInfo = (RosterInfoJson)this.arguments(0);
					
					for(int i = 0;  i < rosterList.length(); i++){
						if(rosterList.get(i).getId() == rosterInfo.getId()){
							noMatch = false;
							break;
						}
					}
					
					if(noMatch){
					rosterList.push(rosterInfo);
					}
					
					//end loading
					MaterialLoader.loading(false);
					grid.drawGrid();
				}
			});
			showGrid();
			
		}};
	ClickHandler formCancelHandler = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			form.reset();
			showGrid();
			
		}};
		
Function promtDeleteRoster = new Function(){
	@Override
	public boolean f(Event e, Object...objects){
		deleteRosterModal.open();
		RosterInfoJson rosterInfo = (RosterInfoJson) objects[0];
		$(deleteRosterModal).data("data", rosterInfo);
		return true;
	}
};

Function showRosterForm = new Function(){
	@Override
	public boolean f(Event e, Object...o){
		e.stopPropagation();
		e.preventDefault();
		console.log("from rosterFormUpdate receive roster as: ");
		console.log(o);
		RosterInfoJson data = (RosterInfoJson)o[0];
		form.setData(data);
		showForm();
		return true;
	}
};

ClickHandler deleteCancelHandler = new ClickHandler(){

	@Override
	public void onClick(ClickEvent event) {
		event.stopPropagation();
		event.preventDefault();
		
		deleteRosterModal.close();
		//MaterialLoader.loading(true);
		
	}};
	
ClickHandler deleteOkHandler = new ClickHandler(){

	@Override
	public void onClick(ClickEvent event) {
		event.stopPropagation();
		event.preventDefault();
		RosterInfoJson rosterData = $(deleteRosterModal).data("data", RosterInfoJson.class);
		String json = JsonUtils.stringify(rosterData);
		deleteRosterModal.close();
		MaterialLoader.loading(true);
		RequestBuilder rb = new RequestBuilder(RequestBuilder.DELETE, "/roster/" +rosterData.getId());
		rb.setHeader("Content-Type", "application/json");
		rb.setHeader("Data-Type", "json");
		rb.setRequestData(json);
		rb.setCallback(new RequestCallback(){

			@Override
			public void onResponseReceived(Request request, Response response) {
				Long rosterId = Long.valueOf(response.getText());
				for(int i  = 0; i < rosterList.length(); i++){
					if(rosterList.get(i).getId() == rosterId){
						rosterList.set(i, null);
					}
				}//end for
				grid.drawGrid();
				MaterialLoader.loading(false);
				MaterialToast.fireToast("Roster successfully deleted");
				
			}

			@Override
			public void onError(Request request, Throwable exception) {
				// TODO Auto-generated method stub
				
			}});
		try {
			rb.send();
		} catch (RequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*Ajax.ajax(Ajax.createSettings()
				.setContentType("application/json")
				.setType("delete")
				.setDataType("json")
				.setData(rosterData)
				.setUrl("/roster/" + rosterData.getId()))
				.done(new Function(){
					@Override
					public void f(){
						
						Long rosterId = Long.valueOf(this.arguments(0));
						
					}
				});*/
		
	}
	
};


	public RosterMain() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void onLoad(){
		FAB.addClickHandler(createRosterHandler);
		grid.emptyState.getIcon().addClickHandler(createRosterHandler);
		
		form.submitBtn.addClickHandler(formSubmitHanlder);
		form.cancelBtn.addClickHandler(formCancelHandler);
		deleteCancelBtn.addClickHandler(deleteCancelHandler);
		deleteOkBtn.addClickHandler(deleteOkHandler);
		
		$(body).on("promtDeleteRoster", promtDeleteRoster);
		$(body).on("showRosterForm", showRosterForm);
		
	}
	
	@Override
	public void onUnload(){
		$(body).off("promtDeleteRoster");
		$(body).off("showRosterForm");
	}
	
	private void showGrid(){
		form.setVisible(false);
		grid.getElement().getStyle().setDisplay(Style.Display.BLOCK);
		FAB.setVisible(true);
	}
	
	private void showForm(){
		form.setVisible(true);
		grid.getElement().getStyle().setDisplay(Style.Display.NONE);
		FAB.setVisible(false);
	}
	
	
	

}
