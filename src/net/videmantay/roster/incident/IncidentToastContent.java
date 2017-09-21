package net.videmantay.roster.incident;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AudioElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import net.videmantay.student.json.StudentIncidentJson;

public class IncidentToastContent extends Composite {

	private static IncidentToastContentUiBinder uiBinder = GWT.create(IncidentToastContentUiBinder.class);

	interface IncidentToastContentUiBinder extends UiBinder<Widget, IncidentToastContent> {
	}

	public IncidentToastContent() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiField 
	AudioElement audio;
	
	@UiField
	MaterialImage studentImage;
	
	@UiField
	HTMLPanel incidentImage;
	
	@UiField
	MaterialLabel summary;
	
	public void init( StudentIncidentJson report){
				
	}

}
