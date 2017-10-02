package net.videmantay.student;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import static com.google.gwt.query.client.GQuery.*;
public class Student implements EntryPoint {

	@Override
	public void onModuleLoad() {
		$("#loader").remove();
		RootPanel.get().clear();
		RootPanel.get().add(new StudentMain());

	}

}
