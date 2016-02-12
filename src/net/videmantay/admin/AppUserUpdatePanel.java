package net.videmantay.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AppUserUpdatePanel extends Composite {

	private static AppUserUpdatePanelUiBinder uiBinder = GWT.create(AppUserUpdatePanelUiBinder.class);

	interface AppUserUpdatePanelUiBinder extends UiBinder<Widget, AppUserUpdatePanel> {
	}

	public AppUserUpdatePanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
