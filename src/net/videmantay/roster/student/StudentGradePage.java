package net.videmantay.roster.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class StudentGradePage extends Composite {

	private static StudentGradePageUiBinder uiBinder = GWT.create(StudentGradePageUiBinder.class);

	interface StudentGradePageUiBinder extends UiBinder<Widget, StudentGradePage> {
	}

	public StudentGradePage() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
