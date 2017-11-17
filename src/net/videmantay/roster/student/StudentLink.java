package net.videmantay.roster.student;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import static com.google.gwt.query.client.GQuery.*;

import gwt.material.design.client.constants.CollectionType;
import gwt.material.design.client.constants.ImageType;
import gwt.material.design.client.constants.Position;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialImage;
import net.videmantay.student.json.RosterStudentJson;

public class StudentLink extends MaterialImage {

	private final RosterStudentJson student;
	private final ClickHandler click = new ClickHandler(){

		@Override
		public void onClick(ClickEvent event) {
			$(body).trigger("studentselected", student);
			
		}};
	public StudentLink(RosterStudentJson stu){
		super();
		student = stu;
		this.setType(ImageType.CIRCLE);
		this.setUrl(student.getThumbnails() == null || student.getThumbnails().length() < 1? "/../img/user.svg":student.getThumbnails().get(2).getUrl());
		this.setHoverable(true);
		this.setTooltip(student.getFirstName()+" "+ student.getLastName());
		this.setTooltipPosition(Position.RIGHT);
		this.setTooltipDelayMs(100);
		this.addClickHandler(click);
		this.addStyleName("studentItem");
		this.setSize("50px", "50px");
		
		
	}
}
