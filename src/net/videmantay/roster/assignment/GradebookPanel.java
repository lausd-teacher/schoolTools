package net.videmantay.roster.assignment;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class GradebookPanel extends Composite {

	private static GradebookPanelUiBinder uiBinder = GWT.create(GradebookPanelUiBinder.class);

	interface GradebookPanelUiBinder extends UiBinder<Widget, GradebookPanel> {
	}

	public GradebookPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
