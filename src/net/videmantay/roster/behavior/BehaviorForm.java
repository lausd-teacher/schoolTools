package net.videmantay.roster.behavior;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class BehaviorForm extends Composite {

	private static BehaviorFormUiBinder uiBinder = GWT.create(BehaviorFormUiBinder.class);

	interface BehaviorFormUiBinder extends UiBinder<Widget, BehaviorForm> {
	}

	public BehaviorForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
