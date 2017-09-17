package net.videmantay.roster.routine;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class RoutineForm extends Composite {

	private static RoutineFormUiBinder uiBinder = GWT.create(RoutineFormUiBinder.class);

	interface RoutineFormUiBinder extends UiBinder<Widget, RoutineForm> {
	}

	public RoutineForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
