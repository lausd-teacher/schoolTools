package net.videmantay.roster.assignment;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class GradedWorkForm extends Composite {

	private static GradedWorkFormUiBinder uiBinder = GWT.create(GradedWorkFormUiBinder.class);

	interface GradedWorkFormUiBinder extends UiBinder<Widget, GradedWorkForm> {
	}

	public GradedWorkForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
