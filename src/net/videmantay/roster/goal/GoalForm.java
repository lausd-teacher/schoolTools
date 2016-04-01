package net.videmantay.roster.goal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class GoalForm extends Composite {

	private static GoalFormUiBinder uiBinder = GWT.create(GoalFormUiBinder.class);

	interface GoalFormUiBinder extends UiBinder<Widget, GoalForm> {
	}

	public GoalForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
