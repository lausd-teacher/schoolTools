package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class RosterGrid extends Composite {

	private static RosterGridUiBinder uiBinder = GWT.create(RosterGridUiBinder.class);

	interface RosterGridUiBinder extends UiBinder<Widget, RosterGrid> {
	}

	public RosterGrid() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
