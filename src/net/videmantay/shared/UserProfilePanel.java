package net.videmantay.shared;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import static com.google.gwt.query.client.GQuery.*;

import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;

public class UserProfilePanel extends Composite {

	private static UserProfilePanelUiBinder uiBinder = GWT.create(UserProfilePanelUiBinder.class);
	private final LoginInfo profile = window.getPropertyJSO("info").cast();
	interface UserProfilePanelUiBinder extends UiBinder<Widget, UserProfilePanel> {
	}

	@UiField
	MaterialLabel name;
	@UiField
	MaterialImage img;
	
	public UserProfilePanel() {
		initWidget(uiBinder.createAndBindUi(this));
		name.setText(profile.getFirstName() + " " + profile.getLastName());
		
		if(profile.getImg()!=null && !profile.getImg().isEmpty()){
			img.setUrl(profile.getImg());
		}else{
			img.setVisibility(Visibility.HIDDEN);
			
		}
		
	}
	

}
