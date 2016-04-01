package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialContainer;
import net.videmantay.roster.json.RosterJson;
import net.videmantay.shared.LoginInfo;
import net.videmantay.student.json.RosterStudentJson;

import static com.google.gwt.query.client.GQuery.*;

public class ClassroomMain extends Composite {

	private static ClassroomMainUiBinder uiBinder = GWT.create(ClassroomMainUiBinder.class);

	interface ClassroomMainUiBinder extends UiBinder<Widget, ClassroomMain> {
	}

	@UiField
	MaterialContainer mainPanel;
	private final Element classroom = this.getElement();
	
	private final Function studentCreate = new Function(){
		@Override
		public void f(){
			RosterStudentJson student = this.arguments(0);
			Ajax.post(RosterUrl.CREATE_STUDENT, $$("student:" + JsonUtils.stringify(student)))
			.done(new Function(){
				@Override
				public void f(){
					RosterStudentJson students = this.arguments(0);
					
					$(body).trigger(RosterEvent.STUDENT_LIST_UPDATED);
				}
			});
		}
	};
	
	private final LoginInfo user = window.getPropertyJSO("loginInfo").cast();
	
	
	public ClassroomMain() {
		initWidget(uiBinder.createAndBindUi(this));
		classroom.setId("classroom");
		mainPanel.add(new RosterDashboardPanel());
		
	}
	
	public void setClassroom(Long rosterId){
		//Asyncall to get my roster with id of id
		Ajax.post(RosterUrl.GET_ROSTER, $$("roster:" + rosterId))
		.done( new Function(){
				@Override
				public void f(){
					RosterJson roster = JsonUtils.safeEval((String) this.arguments(0)).cast();
					$(classroom).data("classroom", roster);
				}
		});
	}
	
	public void showView(String view){
		
	}
	
	public void showViewItem(String item){
		
	}
	
	@Override
	public void onLoad(){
		$(window).on(RosterEvent.STUDENT_CREATE, studentCreate);
	}//end load
	
	

}
