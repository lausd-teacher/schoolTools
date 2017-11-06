package net.videmantay.student.goal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class StudentGoalView extends Composite {

	private static StudentGoalViewUiBinder uiBinder = GWT.create(StudentGoalViewUiBinder.class);

	interface StudentGoalViewUiBinder extends UiBinder<Widget, StudentGoalView> {
	}

	public StudentGoalView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
