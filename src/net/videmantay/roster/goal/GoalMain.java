package net.videmantay.roster.goal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class GoalMain extends Composite {

	private static GoalMainUiBinder uiBinder = GWT.create(GoalMainUiBinder.class);

	interface GoalMainUiBinder extends UiBinder<Widget, GoalMain> {
	}

	public GoalMain() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
