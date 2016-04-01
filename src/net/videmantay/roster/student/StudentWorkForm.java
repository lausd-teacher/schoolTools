package net.videmantay.roster.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class StudentWorkForm extends Composite {

	private static StudentWorkFormUiBinder uiBinder = GWT.create(StudentWorkFormUiBinder.class);

	interface StudentWorkFormUiBinder extends UiBinder<Widget, StudentWorkForm> {
	}

	public StudentWorkForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
