package net.videmantay.teacher.seatingChart;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class DoubleDeskPanel extends Composite {


	private static DoubleDeskPanelUiBinder uiBinder = GWT.create(DoubleDeskPanelUiBinder.class);

	interface DoubleDeskPanelUiBinder extends UiBinder<Widget, DoubleDeskPanel> {
	}

	
	public DoubleDeskPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
}
