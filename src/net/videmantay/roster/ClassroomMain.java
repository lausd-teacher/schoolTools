package net.videmantay.roster;

import com.google.gwt.core.client.GWT;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.ajax.Ajax;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.HeadingSize;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.html.Heading;
import gwtquery.plugins.ui.RotatableUi;
import gwtquery.plugins.ui.UiFunction;
import gwtquery.plugins.ui.interactions.Rotatable;
import gwtquery.plugins.ui.interactions.Rotatable.Options;

import static com.google.gwt.query.client.GQuery.*;
import static gwtquery.plugins.ui.Ui.Ui;

public class ClassroomMain extends Composite {

	private static ClassroomMainUiBinder uiBinder = GWT.create(ClassroomMainUiBinder.class);

	interface ClassroomMainUiBinder extends UiBinder<Widget, ClassroomMain> {
	}

	@UiField
	MaterialContainer mainPanel;
	
	public ClassroomMain() {
		initWidget(uiBinder.createAndBindUi(this));
		mainPanel.add(new RosterDashboardPanel());
	}
	
	public void setClassroom(String id){
		
	}
	
	public void showView(String view){
		
	}
	
	public void showViewItem(String item){
		
	}
	
	@Override
	public void onLoad(){
		Rotatable.Options opt = Options.create();
		opt.rotate(new UiFunction(){
			@Override
			public boolean f(Event e, RotatableUi ui){
				console.log("angle current is " + ui.angle().current());
				return true;
			}
		});
		$("h2").as(Ui).rotatable().on(Rotatable.Event.rotate, new Function(){
				@Override
				public void f(){
					console.log("On works now");
				}
		});
	}

}
