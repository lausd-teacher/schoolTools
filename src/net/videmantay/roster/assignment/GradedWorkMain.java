package net.videmantay.roster.assignment;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class GradedWorkMain extends Composite {

	private static GradedWorkMainUiBinder uiBinder = GWT.create(GradedWorkMainUiBinder.class);

	interface GradedWorkMainUiBinder extends UiBinder<Widget, GradedWorkMain> {
	}

	public GradedWorkMain() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
