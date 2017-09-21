package net.videmantay.roster.work;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class RubricForm extends Composite {

	private static RubricFormUiBinder uiBinder = GWT.create(RubricFormUiBinder.class);

	interface RubricFormUiBinder extends UiBinder<Widget, RubricForm> {
	}

	public RubricForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
