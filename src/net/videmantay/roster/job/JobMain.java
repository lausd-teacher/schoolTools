package net.videmantay.roster.job;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class JobMain extends Composite {

	private static JobMainUiBinder uiBinder = GWT.create(JobMainUiBinder.class);

	interface JobMainUiBinder extends UiBinder<Widget, JobMain> {
	}

	public JobMain() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
