package net.videmantay.teacher.seatingChart;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SeatingChartStudentPanel extends Composite {

	private static SeatingChartStudentPanelUiBinder uiBinder = GWT.create(SeatingChartStudentPanelUiBinder.class);

	interface SeatingChartStudentPanelUiBinder extends UiBinder<Widget, SeatingChartStudentPanel> {
	}

	public SeatingChartStudentPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
