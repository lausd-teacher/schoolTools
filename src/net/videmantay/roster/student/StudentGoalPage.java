package net.videmantay.roster.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class StudentGoalPage extends Composite {

	private static StudentGoalPageUiBinder uiBinder = GWT.create(StudentGoalPageUiBinder.class);

	interface StudentGoalPageUiBinder extends UiBinder<Widget, StudentGoalPage> {
	}

	public StudentGoalPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
