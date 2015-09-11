package net.videmantay.admin;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class Admin implements EntryPoint {

	@Override
	public void onModuleLoad() {
		AdminView view = new AdminView();
		RootPanel.get().add(view);

	}

}
