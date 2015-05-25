package net.videmantay.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class Kimchi implements EntryPoint {
	
	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		RootPanel.get().add(new Welcome());

	}

}
