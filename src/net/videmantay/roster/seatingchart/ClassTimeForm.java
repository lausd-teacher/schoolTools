package net.videmantay.roster.seatingchart;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import static com.google.gwt.query.client.GQuery.*;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import net.videmantay.roster.RosterUrl;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.roster.seatingchart.json.ClassTimeJson;

public class ClassTimeForm extends Composite {

	private static ClassTimeFormUiBinder uiBinder = GWT.create(ClassTimeFormUiBinder.class);

	interface ClassTimeFormUiBinder extends UiBinder<Widget, ClassTimeForm> {
	}
	
	@UiField
	MaterialButton submitBtn;
	
	@UiField
	MaterialButton cancelBtn;
	
	@UiField
	MaterialTextBox title;
	
	@UiField
	MaterialTextArea description;
	
	@UiField
	FormPanel classtimeForm;
	
	@UiField
	MaterialModal classtimeModal;
	private final RosterJson roster =window.getPropertyJSO("roster").cast();
	private ClassTimeJson classTime;

	public ClassTimeForm() {
		console.log("ClassTime Form constructor");
		initWidget(uiBinder.createAndBindUi(this));
		
	}
	
	@Override
	public void onLoad(){
		submitBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				MaterialLoader.showLoading(true);
				classTime = ClassTimeJson.createObject().cast();
				classTime.setTitle(title.getValue());
				classTime.setDescript(description.getValue());
				classtimeForm.reset();
				classtimeModal.closeModal();
				Ajax.post(RosterUrl.CREATE_SEATINGCHART, $$("classTime:" + JsonUtils.stringify(classTime)+
															",roster:" +roster.getId()))
				.done(new Function(){
					@Override
					public void f(){
						ClassTimeJson classTimeData = JsonUtils.safeEval((String) this.arguments(0));
						
						roster.getClassTimes().push(classTimeData);
						int index = roster.getClassTimes().length() - 1;
						$(body).trigger("setclasstime",$$("data-index:" + index));
						MaterialLoader.showLoading(false);
						
						
					}
				});
				
			}});
		
		cancelBtn.addClickHandler( new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				classtimeForm.reset();
				classtimeModal.closeModal();
			}});
	}

}
