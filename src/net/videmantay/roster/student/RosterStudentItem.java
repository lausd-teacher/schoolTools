package net.videmantay.roster.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
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
		
		String url = (student.getPicUrl() == null)? "default String": student.getPicUrl();
		studentImg.setUrl(url);
		firstName.setText(student.getFirstName());
		lastName.setText(student.getLastName());
		$(this).id(rs.getId().toString());
	}
	
	
	@Override
	public void onLoad(){
		$(this).click(new Function(){
			@Override
			public boolean f(Event e){
				if($(".studentItem").filter(".selectedStudentItem").equals(this)){
				}else{
				$(".studentItem").filter(".selectedStudentItem").removeClass(".selectedStudentItem");
				$(this).addClass(".selectedStudentItem");
				}
				//This is handled by Roster(Entry point)
				History.newItem("roster/" + $("#classroom").data("classroom", RosterJson.class).getId()+
						"/student" + student.getId());
				return true;
			}
		});
	}
	
	public void showStudentPage(){
		
	}

}
