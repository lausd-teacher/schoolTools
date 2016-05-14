package net.videmantay.roster.seatingchart;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ClassTimeMain extends Composite {

	private static ClassTimeMainUiBinder uiBinder = GWT.create(ClassTimeMainUiBinder.class);

	interface ClassTimeMainUiBinder extends UiBinder<Widget, ClassTimeMain> {
	}

	public ClassTimeMain() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
