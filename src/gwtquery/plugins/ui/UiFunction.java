package gwtquery.plugins.ui;

import com.google.gwt.query.client.Function;
import com.google.gwt.user.client.Event;

public abstract class UiFunction extends Function {

	
public  boolean f(Event e, RotatableUi ui){
	return f(e, ui);
}


public boolean f(Event e, DroppableUi ui){
	
	return f(e,ui);
}


@Override
public boolean f(Event e, Object... o){
	
	
	return true;
}
 
}
