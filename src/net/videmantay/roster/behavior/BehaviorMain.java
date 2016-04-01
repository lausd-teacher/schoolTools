package net.videmantay.roster.behavior;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class BehaviorMain extends Composite {

	private static BehaviorMainUiBinder uiBinder = GWT.create(BehaviorMainUiBinder.class);

	interface BehaviorMainUiBinder extends UiBinder<Widget, BehaviorMain> {
	}

	public BehaviorMain() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
