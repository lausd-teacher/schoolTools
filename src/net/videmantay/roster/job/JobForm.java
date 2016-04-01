package net.videmantay.roster.job;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class JobForm extends Composite {

	private static JobFormUiBinder uiBinder = GWT.create(JobFormUiBinder.class);

	interface JobFormUiBinder extends UiBinder<Widget, JobForm> {
	}

	public JobForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
