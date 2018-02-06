package net.videmantay.roster.work;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import net.videmantay.student.json.RosterStudentJson;

public class AssignToGridItem extends Composite {

	private static AssignToGridItemUiBinder uiBinder = GWT.create(AssignToGridItemUiBinder.class);

	interface AssignToGridItemUiBinder extends UiBinder<Widget, AssignToGridItem> {
	}

	@UiField
	MaterialImage studentImg;
	
	@UiField
	MaterialLabel firstName;
	
	@UiField
	MaterialLabel lastName;
	
	public AssignToGridItem() {
		initWidget(uiBinder.createAndBindUi(this));
		this.setSize("5em", "6em");
		this.getElement().addClassName("item-student");
	}
	
	public void setStudent(RosterStudentJson student){
		String url = student.getThumbnails()== null? "img/user.svg": student.getThumbnails().get(1).getUrl();
		studentImg.setUrl(url);
		if(student.getFirstName() == null){
			firstName.setText(student.getId());
		}else{
			firstName.setText(student.getFirstName());
			lastName.setText(student.getLastName());
		}
	}

}
