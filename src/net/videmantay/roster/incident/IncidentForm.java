package net.videmantay.roster.incident;

import static com.google.gwt.query.client.GQuery.*;

import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.Promise;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialIntegerBox;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialTextBox;
import net.videmantay.student.json.IncidentJson;

public class IncidentForm extends Composite {

	private static IncidentFormUiBinder uiBinder = GWT.create(IncidentFormUiBinder.class);

	interface IncidentFormUiBinder extends UiBinder<Widget, IncidentForm> {
	}

	@UiField
	public FormPanel form;
	
	@UiField
	public HTMLPanel formContainer;

	@UiField
	public MaterialModal modal;
	
	@UiField
	public MaterialButton incidentImageBtn;
    
	@UiField
    public MaterialTextBox nameInput;
	
	@UiField
	public MaterialTextBox imageName;

	@UiField
	public MaterialIntegerBox valueInput;

	@UiField
	public MaterialButton doneBtn;

	@UiField
	public MaterialButton cancelBtn;
	

	public final IncidentIconGrid iconGrid = new IncidentIconGrid();
	
	private IncidentJson data = IncidentJson.createObject().cast();
	
	private Function iconChangeFunc = new Function(){
		@Override
		public boolean f(Event e, Object...o){
			String iconName = (String)o[0];
			console.log("incident name is " +iconName);
			
			incidentImageBtn.clear();
			incidentImageBtn.add(new HTML(IncidentImageUtil.imageHTML(iconName)));
			imageName.setValue(iconName);
			return true;
		}
	};


	public IncidentForm() {
		initWidget(uiBinder.createAndBindUi(this));
		formContainer.getElement().setId("incidentForm");
		valueInput.setValue(0);
		incidentImageBtn.add(new HTML(IncidentImageUtil.imageHTML("doctor")));
		incidentImageBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
			$("#iconGrid").show();
				console.log("incident Image button clicked");
				
			}});
	}
	
	private IncidentJson getFormData(){
		
		data.setName(nameInput.getValue());
		data.setImageUrl(imageName.getValue());
		
		if(valueInput.getText().isEmpty() || valueInput.getText() == null )
		      data.setPoints(0);
		else
			data.setPoints(valueInput.getValue());
		
		reset();
		return data;
	}
	
	

	@Override
	public void onLoad() {
		$("#incidentForm input").blur(getValidationFunction());
		$(".errorLabel").hide();
		$(body).on("incidentIconChange", iconChangeFunc);
	}


	public void show() {
		modal.open();
	}
	
	public void hide() {
		modal.close();
	}

	private Function getValidationFunction() {

		return new Function() {
			@Override
			public boolean f(Event e, Object...o) {
				NativeEvent nEvent = e.cast();
				EventTarget target = nEvent.getEventTarget();
				
				if ($(target).is(":invalid")) {
					$(target).next(".errorLabel").show();
					$(target).addClass("inputError");
					doneBtn.setEnabled(false);
				} else {
					$(target).next(".errorLabel").hide();
					doneBtn.setEnabled(true);
				}
				
				return true;
			}
		};

	}
	
	public void cancel(){
		modal.close();
		reset();
	}
	
	public Promise submit(){
		modal.close();
		return Ajax.ajax(Ajax.createSettings()
				.setContentType("application/json")
				.setType("post")
				.setDataType("json")
				.setData(getFormData())
				.setUrl(""));
	}
	
	public void setData(IncidentJson data){
		if(data == null){
			data = IncidentJson.create();
		}else{
		this.data = data;
		}
		//populate the form
		this.imageName.setValue(data.getImageUrl());
		this.nameInput.setValue(data.getName());
		this.valueInput.setValue(data.getPoints());
		incidentImageBtn.clear();
		incidentImageBtn.add(new HTML(IncidentImageUtil.imageHTML(data.getImageUrl())));
	}
	
	private void reset(){
		form.reset();
		incidentImageBtn.clear();
		incidentImageBtn.add(new HTML(IncidentImageUtil.imageHTML("doctor")));
	}
}
