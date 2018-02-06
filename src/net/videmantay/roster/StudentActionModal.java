package net.videmantay.roster;

import java.util.ArrayList;
import java.util.Collections;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;

import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialToast;
import net.videmantay.roster.incident.IncidentValueCompare;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.student.json.IncidentJson;
import net.videmantay.student.json.RosterStudentJson;
import net.videmantay.student.json.StudentIncidentJson;

public class StudentActionModal extends Composite {

	private static StudentActionModalUiBinder uiBinder = GWT.create(StudentActionModalUiBinder.class);

	interface StudentActionModalUiBinder extends UiBinder<Widget, StudentActionModal> {
	}
	
	private final RosterJson roster ;
	private RosterStudentJson student;
	@UiField
	MaterialLabel studentName;
	
	@UiField
	MaterialImage studentActionImage;
	
	@UiField
	public MaterialIcon gotoStudentPageIcon;
	@UiField
	public MaterialModal modal;
	
	@UiField
	public MaterialAnchorButton cancelStudentActionBtn;
	
	@UiField
	public MaterialAnchorButton compareBtn;
	@UiField
	public MaterialAnchorButton incidentQuickLink;
	
	@UiField
	public MaterialAnchorButton formQuickLink;
	
	@UiField
	public MaterialAnchorButton noteQuickLink;
	
	@UiField
	public MaterialAnchorButton assignmentQuickLink;
	
	
	//incidentPanel components////////////
	@UiField
	public DivElement incidentActionPanel;
	@UiField
	public MaterialRow posIncidents;
	@UiField
	public MaterialRow negIncidents;
	
	
	@UiField
	public DivElement noteActionPanel;
	
	@UiField
	public DivElement assignmentActionPanel;
	
	@UiField
	public DivElement formActionPanel;
	
	
	public StudentActionModal() {
		roster = window.getPropertyJSO("currentClassroom").cast();
		initWidget(uiBinder.createAndBindUi(this));
		
		//add close handler to modal to clean up overlays
		modal.addCloseHandler(new CloseHandler<MaterialModal>(){

			@Override
			public void onClose(CloseEvent<MaterialModal> event) {
				$("div.lean-overlay").remove();
				
			}});
		//add click to cancelbtn
		cancelStudentActionBtn.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				modal.close();
			}});
		
		drawIncidentGrid(roster.getIncidents());
	}
	
	public void setData(RosterStudentJson student){
		studentName.setFontSize("1.5em");
		this.student = student;
		if(student.getFirstName()== null || student.getFirstName().isEmpty()){
			studentName.setText(student.getId());
		}else{
		studentName.setText(student.getFirstName() + " " + student.getLastName());
		}
		
		String url= student.getThumbnails() == null || student.getThumbnails().length() < 1 ? "../img/user.svg":student.getThumbnails().get(1).getUrl();
		studentActionImage.setUrl(url);
		
		
	}
	
	public void drawIncidentGrid(JsArray<IncidentJson> incidents){
		ArrayList<IncidentJson> incidentList = new ArrayList<>();
		for(int i = 0; i < incidents.length(); i++){
			incidentList.add(incidents.get(i));
		}
		
		Collections.sort(incidentList, new IncidentValueCompare());
		
		for(IncidentJson incident: incidentList){
			final  IncidentActionItem item = new IncidentActionItem();
			item.setData(incident);
			
			item.addDomHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					//create student incident based on studnt chosen
					
					StudentIncidentJson stuInc = StudentIncidentJson.create(student.getId(), item.getData());
					
					console.log(stuInc);
					Ajax.ajax(Ajax.createSettings().setContentType("json/application").setDataType("json")
							.setData(stuInc).setType("POST")
							.setUrl("/roster/"+roster.getId()+"/student/"+student.getId()+"/incident"))
							.done(new Function(){
								@Override
								public void f(){
								JsArray<StudentIncidentJson> stuIncs = this.arguments(0);
							//trigger incident response
								$(body).trigger("incidentResponse", stuIncs);
							
								}
							});
					modal.close();
				}}, ClickEvent.getType());
			
			
			MaterialColumn col = new MaterialColumn();
			col.setGrid("s6 m4 l3");
			col.add(item);
			if(incident.getPoints() < 0){
				negIncidents.add(col);
			}else{
				posIncidents.add(col);
			}
		}
	}
	

}
