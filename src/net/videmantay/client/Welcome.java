package net.videmantay.client;

import java.util.ArrayList;

import net.videmantay.json.RosterAssignmentAction;
import net.videmantay.shared.RosterAssignmentAction.Action;

import com.google.gwt.core.client.GWT;
import com.google.gwt.query.client.GQ;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;


import static com.google.gwt.query.client.GQuery.*;
public class Welcome extends Composite {

	private static WelcomeUiBinder uiBinder = GWT.create(WelcomeUiBinder.class);

	interface WelcomeUiBinder extends UiBinder<Widget, Welcome> {
	}

	public Welcome() {
		initWidget(uiBinder.createAndBindUi(this));
		
		
	}
	
	@Override
	public void onLoad(){
		

		
ArrayList<RosterAssignmentAction> array = new ArrayList<RosterAssignmentAction>();
		
		
		for(int i= 0; i < 10; i++){
			RosterAssignmentAction ra = GQ.create(RosterAssignmentAction.class);
			ra.setAction(Action.ASSIGN);
			ra.setRosterStudentId(98097L);
			array.add(ra);
		}
		
		$("#rightHere").html("<h2>" +array.toString()+"</h2>");
		
		
	}

}
