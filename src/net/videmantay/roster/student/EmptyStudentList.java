package net.videmantay.roster.student;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class EmptyStudentList extends Composite {

	private static EmptyStudentListUiBinder uiBinder = GWT.create(EmptyStudentListUiBinder.class);

	interface EmptyStudentListUiBinder extends UiBinder<Widget, EmptyStudentList> {
	}

	public EmptyStudentList() {
		this.initWidget(uiBinder.createAndBindUi(this));
	}

}
