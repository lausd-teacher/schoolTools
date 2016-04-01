package net.videmantay.roster.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class StudentReportPage extends Composite {

	/* This is a dynamic page that changes with the context of the view
	 * for example when on the graded work view this is a report of student
	 * work. if on student jobs this will reflect how many jobs the student has had
	 * etc
	 */
	private static StudentReportPageUiBinder uiBinder = GWT.create(StudentReportPageUiBinder.class);

	interface StudentReportPageUiBinder extends UiBinder<Widget, StudentReportPage> {
	}

	public StudentReportPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
