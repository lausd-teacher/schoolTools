package net.videmantay.teacher;

import org.gwtbootstrap3.client.ui.Alert;

import com.google.gwt.core.client.GWT;
import static com.google.gwt.query.client.GQuery.*;

import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class TeacherDashboard extends Composite {

	private static TeacherDashboardUiBinder uiBinder = GWT
			.create(TeacherDashboardUiBinder.class);

	interface TeacherDashboardUiBinder extends
			UiBinder<Widget, TeacherDashboard> {
	}

	public TeacherDashboard() {
		initWidget(uiBinder.createAndBindUi(this));
		
		
	}

}
