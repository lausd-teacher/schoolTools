package net.videmantay.roster.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.html.Span;

import static com.google.gwt.query.client.GQuery.*;

import net.videmantay.roster.json.RosterJson;
import net.videmantay.student.json.RosterStudentJson;

public class RosterStudentItem extends Composite {

	private static RosterStudentItemUiBinder uiBinder = GWT.create(RosterStudentItemUiBinder.class);

	interface RosterStudentItemUiBinder extends UiBinder<Widget, RosterStudentItem> {
	}

	@UiField
	MaterialImage studentImg;
	
	@UiField
	Span firstName;
	
	@UiField
	Span lastName;
	
	private final RosterStudentJson student;
	
	public RosterStudentItem(RosterStudentJson rs) {
		initWidget(uiBinder.createAndBindUi(this));
		student = rs;
		
		String url = (student.getThumbnails() == null)? "/img/user.svg": student.getThumbnails().get(1).getUrl();
		studentImg.setUrl(url);
		firstName.setText(student.getFirstName());
		lastName.setText(student.getLastName());
		$(this).id(rs.getId() +"");
	}
	
	
	@Override
	public void onLoad(){
		this.addDomHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if(!$(".studentItem").filter(".selectedStudentItem").equals(this)){
					$(".studentItem").filter(".selectedStudentItem").removeClass(".selectedStudentItem");
					$(this).addClass(".selectedStudentItem");
					}
					//This is handled by Roster(Entry point)
					//roster is a window object
					RosterJson roster = window.getPropertyJSO("roster").cast();
					if(roster == null || roster.getId() == null){
						//what todo?
					}
					History.newItem("roster/" + roster.getId()+
							"/students/" + student.getId());
				
			}}, ClickEvent.getType());
	}
	
	public void showStudentPage(){
		
	}

}
