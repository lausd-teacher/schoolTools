package net.videmantay.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class StudentView extends Composite  {

	private static StudentViewUiBinder uiBinder = GWT
			.create(StudentViewUiBinder.class);

	interface StudentViewUiBinder extends UiBinder<Widget, StudentView> {
	}

	public StudentView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	
}
