package net.videmantay.admin;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class Admin implements EntryPoint {

	@Override
	public void onModuleLoad() {
		RootPanel root = RootPanel.get();
		root.add(new AdminMain());

	}

}
