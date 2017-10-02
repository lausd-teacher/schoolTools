package net.videmantay.roster.work;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class StudentWorkItem extends Composite {

	private static StudentWorkItemUiBinder uiBinder = GWT.create(StudentWorkItemUiBinder.class);

	interface StudentWorkItemUiBinder extends UiBinder<Widget, StudentWorkItem> {
	}

	public StudentWorkItem() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
