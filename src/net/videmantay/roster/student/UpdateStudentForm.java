package net.videmantay.roster.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class UpdateStudentForm extends Composite {

	private static UpdateStudentFormUiBinder uiBinder = GWT.create(UpdateStudentFormUiBinder.class);

	interface UpdateStudentFormUiBinder extends UiBinder<Widget, UpdateStudentForm> {
	}

	public UpdateStudentForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
