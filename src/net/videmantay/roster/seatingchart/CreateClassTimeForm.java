package net.videmantay.roster.seatingchart;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CreateClassTimeForm extends Composite {

	private static CreateClassTimeFormUiBinder uiBinder = GWT.create(CreateClassTimeFormUiBinder.class);

	interface CreateClassTimeFormUiBinder extends UiBinder<Widget, CreateClassTimeForm> {
	}

	public CreateClassTimeForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
