package net.videmantay.roster.routine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class RoutinePanel extends Composite {

	private static RoutinePanelUiBinder uiBinder = GWT.create(RoutinePanelUiBinder.class);

	interface RoutinePanelUiBinder extends UiBinder<Widget, RoutinePanel> {
	}

	public RoutinePanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
