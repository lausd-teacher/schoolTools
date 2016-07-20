package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ManageStudentDialog extends Composite {

	private static ManageStudentDialogUiBinder uiBinder = GWT.create(ManageStudentDialogUiBinder.class);

	interface ManageStudentDialogUiBinder extends UiBinder<Widget, ManageStudentDialog> {
	}

	public ManageStudentDialog() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
