package net.videmantay.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class StudentMain extends Composite {

	private static StudentMainUiBinder uiBinder = GWT.create(StudentMainUiBinder.class);

	interface StudentMainUiBinder extends UiBinder<Widget, StudentMain> {
	}

	public StudentMain() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
