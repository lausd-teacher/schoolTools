package net.videmantay.roster.assignment;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;
import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.query.client.Function;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialDoubleBox;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import net.videmantay.roster.RosterUrl;
import net.videmantay.roster.json.GradedWorkJson;
import net.videmantay.roster.json.RosterJson;

public class GradedWorkForm extends Composite {

	private static GradedWorkFormUiBinder uiBinder = GWT.create(GradedWorkFormUiBinder.class);
	private final GradedWorkForm $this = this;
	private JsArrayString studentList = JsArrayString.createArray().cast();
	private GradedWorkJson data;
	private String url = "";
	
	@UiField
	MaterialModal modal;
	
	@UiField
	FormPanel form;
	
	@UiField
	MaterialTextBox  title;
	
	@UiField
	MaterialTextArea description;
	
	@UiField
	MaterialListBox lang;
	
	@UiField
	MaterialListBox subject;
	
	@UiField
	MaterialListBox type;
	
	@UiField
	MaterialDatePicker dueDate;
	
	@UiField
	MaterialDatePicker assignedDate;
	
	@UiField
	MaterialDoubleBox pointsPossible;
	
	@UiField
	MaterialCheckBox selectAllBox;
	
	@UiField
	MaterialRow assignToGrid;
	
	@UiField
	MaterialButton okBtn;
	
	@UiField
	MaterialButton cancelBtn;
	
	
	interface GradedWorkFormUiBinder extends UiBinder<Widget, GradedWorkForm> {
	}

	public GradedWorkForm() {
		
		initWidget(uiBinder.createAndBindUi(this));
		okBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				$this.submit();
				
			}});
		
		cancelBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				$this.hide();
			}});
	}
	
	public void show(){
		modal.open();
	}
	
	public void hide(){
		form.reset();
		data = null;
		modal.close();
	}
	public void reset(){
		form.reset();
		data = JavaScriptObject.createObject().cast();
	}
	
	public void submit(){
		data = JavaScriptObject.createObject().cast();
		
		data.setTitle(title.getValue());
		;
		data.setDescription(description.getValue());
	
		DateTimeFormat dtf = DateTimeFormat.getFormat("yyyy-MM-dd");
		data.setAssignedDate(dtf.format(assignedDate.getValue()));
		data.setDueDate(dtf.format(dueDate.getValue()));
		
		data.setAssignedTo(studentList);
		data.setMediaUrl(url);
		RosterJson roster = window.getPropertyJSO("roster").cast();
		data.setRosterId(roster.getId());
		
		if(selectAllBox.getValue()){
			for(int i = 0; i <roster.getRosterStudents().length(); i++){
				studentList.push(roster.getRosterStudents().get(i).getAcct());
			}
		}
		MaterialLoader.showLoading(true);
		Ajax.post(RosterUrl.CREATE_ASSIGNMENT, $$("assignment:" + JsonUtils.stringify(data)))
		.done(new Function(){
			@Override
			public void f(){
				GradedWorkJson assignment = JsonUtils.safeEval((String)this.arguments(0)).cast();
				$(body).trigger("redrawAssignmentGrid", assignment );	
			}//end f
		});//end done
		//hide form even  before ajax is back
		this.hide();
	}//end submit

}
