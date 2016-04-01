package net.videmantay.roster.group;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class GroupForm extends Composite {

	private static GroupFormUiBinder uiBinder = GWT.create(GroupFormUiBinder.class);

	interface GroupFormUiBinder extends UiBinder<Widget, GroupForm> {
	}

	public GroupForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
