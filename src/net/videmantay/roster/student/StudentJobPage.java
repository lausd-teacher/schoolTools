package net.videmantay.roster.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class StudentJobPage extends Composite {

	private static StudentJobPageUiBinder uiBinder = GWT.create(StudentJobPageUiBinder.class);

	interface StudentJobPageUiBinder extends UiBinder<Widget, StudentJobPage> {
	}

	public StudentJobPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
