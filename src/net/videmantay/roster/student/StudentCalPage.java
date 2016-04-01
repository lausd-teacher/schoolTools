package net.videmantay.roster.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/* Special Consideration for the type of view
 * Must be taken under consideration for this page
 * for example if we are view student jobs then this page must display the logevity of 
 * said job.  grades: time of test in other words this page is dynamic
 */
public class StudentCalPage extends Composite {

	private static StudentCalPageUiBinder uiBinder = GWT.create(StudentCalPageUiBinder.class);

	interface StudentCalPageUiBinder extends UiBinder<Widget, StudentCalPage> {
	}

	public StudentCalPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
