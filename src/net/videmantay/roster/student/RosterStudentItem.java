package net.videmantay.roster.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Position;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialTooltip;
import gwt.material.design.client.ui.html.Span;

import static com.google.gwt.query.client.GQuery.*;

import net.videmantay.student.json.RosterStudentJson;

public class RosterStudentItem extends Composite {

	private static RosterStudentItemUiBinder uiBinder = GWT.create(RosterStudentItemUiBinder.class);

	interface RosterStudentItemUiBinder extends UiBinder<Widget, RosterStudentItem> {
	}

	@UiField
	MaterialImage studentImg;
	@UiField
	MaterialTooltip tooltip;
	
	
	
	
	private final RosterStudentJson student;
	
	public RosterStudentItem(RosterStudentJson rs) {
		student = rs;
		console.log("RosterSTudentItem -Constructor studnet is:");
		console.log(student);
		initWidget(uiBinder.createAndBindUi(this));
		String url = (student.getThumbnails() == null)? "../img/user.svg": student.getThumbnails().get(2).getUrl();
		studentImg.setUrl(url);
		tooltip.setText(student.getFirstName() + " " + student.getLastName());
		tooltip.setDelayMs(100);
	}
	

	
	
	@Override
	public void onLoad(){
		console.log("Student RosterStudentItem-onLoad");
		console.log(student);
	
		
		$(this).id(student.getId()+"");
		this.addDomHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if(!$(".studentItem").filter(".selectedStudentItem").equals(this)){
					$(".studentItem").filter(".selectedStudentItem").removeClass(".selectedStudentItem");
					$(this).addClass(".selectedStudentItem");
					}
					//if history is changed consider using thaat as trigger
					//History.newItem("roster/students/" + student.getId());
					$(body).trigger("studentselected", student);
			}}, ClickEvent.getType());
		
	}

}
