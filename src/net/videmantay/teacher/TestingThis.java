package net.videmantay.teacher;

import gwtquery.plugins.ui.interactions.Draggable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.plugins.effects.PropertiesAnimation.EasingCurve;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.*;
import static gwtquery.plugins.ui.Ui.Ui;
public class TestingThis extends Composite {

	private static TestingThisUiBinder uiBinder = GWT
			.create(TestingThisUiBinder.class);

	interface TestingThisUiBinder extends UiBinder<Widget, TestingThis> {
	}

	public TestingThis() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	@Override
	public void onLoad(){
		$(".what").as(Ui).rotatable();
		$(".what").as(Ui).draggable().on(Draggable.Event.stop, new Function(){
			@Override
			public boolean f(Event e, Object...objects){
			$(e.getEventTarget()).animate($$("{border:2px solid Red; background-color:Maroon; color:White; font-size:2em}"), 1000, EasingCurve.easeInOutExpo);			
			return true;
			}
		});
		
	}//onLoad
	
}
