package net.videmantay.roster.assignment;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class EmptyAssignmentGrid extends Composite {

	private static EmptyAssignmentGridUiBinder uiBinder = GWT.create(EmptyAssignmentGridUiBinder.class);

	interface EmptyAssignmentGridUiBinder extends UiBinder<Widget, EmptyAssignmentGrid> {
	}

	public EmptyAssignmentGrid() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
