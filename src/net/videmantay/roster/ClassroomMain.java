package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ClassroomMain extends Composite {

	private static ClassroomMainUiBinder uiBinder = GWT.create(ClassroomMainUiBinder.class);

	interface ClassroomMainUiBinder extends UiBinder<Widget, ClassroomMain> {
	}

	public ClassroomMain() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setClassroom(String id){
		
	}

}
