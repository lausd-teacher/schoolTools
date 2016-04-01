package net.videmantay.roster.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class StudentAbsencePage extends Composite {

	private static StudentAbsencePageUiBinder uiBinder = GWT.create(StudentAbsencePageUiBinder.class);

	interface StudentAbsencePageUiBinder extends UiBinder<Widget, StudentAbsencePage> {
	}

	public StudentAbsencePage() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
