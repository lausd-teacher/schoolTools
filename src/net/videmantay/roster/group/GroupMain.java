package net.videmantay.roster.group;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class GroupMain extends Composite {

	private static GroupMainUiBinder uiBinder = GWT.create(GroupMainUiBinder.class);

	interface GroupMainUiBinder extends UiBinder<Widget, GroupMain> {
	}

	public GroupMain() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
