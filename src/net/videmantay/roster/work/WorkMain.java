package net.videmantay.roster.work;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class WorkMain extends Composite {

	private static WorkMainUiBinder uiBinder = GWT.create(WorkMainUiBinder.class);

	interface WorkMainUiBinder extends UiBinder<Widget, WorkMain> {
	}
	public final GradedWorkForm form = new GradedWorkForm();
		
	@UiField
	HTMLPanel tab1Content;
	
	public WorkMain() {
		initWidget(uiBinder.createAndBindUi(this));

	}

}
