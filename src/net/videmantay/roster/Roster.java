package net.videmantay.roster;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.user.client.ui.RootPanel;

import static com.google.gwt.query.client.GQuery.*;
import com.google.gwt.query.client.Function;

public class Roster implements EntryPoint {
	
	public Roster(){
		
	}
	@Override
	public void onModuleLoad() {
		//laod the object
		Ajax.get("/roster").done(new Function(){			
			public void f(){
				$("div#loader").remove();
				window.setPropertyJSO("roster",JsonUtils.safeEval((String)this.arguments(0)));
				ClassroomMain classroom = new ClassroomMain();
				RootPanel.get().add(classroom);
			}
		});
		
		
	}

}
