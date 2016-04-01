package net.videmantay.roster.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class StudentBehaviorPage extends Composite {

	private static StudentBehaviorPageUiBinder uiBinder = GWT.create(StudentBehaviorPageUiBinder.class);

	interface StudentBehaviorPageUiBinder extends UiBinder<Widget, StudentBehaviorPage> {
	}

	public StudentBehaviorPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
