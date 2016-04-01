package gwtquery.plugins.ui.interactions;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.query.client.Function;

import gwtquery.plugins.ui.RotatableUi;
import gwtquery.plugins.ui.UiFunction;
import gwtquery.plugins.ui.UiPlugin;
import gwtquery.plugins.ui.UiWidget;
import gwtquery.plugins.ui.WidgetOptions;



public class Rotatable  extends UiWidget<Rotatable, Rotatable.Options>{

	

	public static class Options extends WidgetOptions<Options> {

	    protected Options() {
	    	
	        }
	    public static native final Options create()
	    /*-{
	      return {};
	    }-*/;
	    
	    public final native String handle()/*-{
	    	return this["handle"];
	    }-*/;
   	 	public final native Options handle(String handle)/*-{
   	 		this["handle"] = handle;
   	 		return this;
   	 	}-*/;
       
   	 public final native Double angle()/*-{
   	 	return this["angle"];
   	 }-*/;
	 public final native Options angle(Double rad)/*-{
	 		this["angle"] = rad;
	 		return this;
	 	}-*/;
    public final native JavaScriptObject handleOffset()/*-{
    		return this["handleOffset"];
    }-*/;
    
    public final native Options handleOffset(JavaScriptObject handleOffset)/*-{
    		this["handleOffset"] = handleOffset;
    		return this;
    }-*/;
    
    public final native Options rotate(UiFunction f)/*-{
    		this["rotate"] = 
    			f.@gwtquery.plugins.ui.UiFunction::f(Lcom/google/gwt/user/client/Event;Lgwtquery/plugins/ui/RotatableUi;);
    }-*/;
    
    public final native Options start(UiFunction f)/*-{
	this["start"] = 
		f.@gwtquery.plugins.ui.UiFunction::f(Lcom/google/gwt/user/client/Event;Lgwtquery/plugins/ui/RotatableUi;);
}-*/;
    
    public final native Options stop(UiFunction f)/*-{
	this["stop"] = 
		f.@gwtquery.plugins.ui.UiFunction::f(Lcom/google/gwt/user/client/Event;Lgwtquery/plugins/ui/RotatableUi;);
}-*/;
	}

	
	 
	 
	 public static class Event extends JavaScriptObject {

		    public static final String stop = "stop";

		    public static final String start = "start";

		    public static final String rotate = "rotate";

		    protected Event() {

		    }
		  }
	 
	 /*
	  * Used to register plugin
	  */
	 public static class RotatablePlugin implements UiPlugin<Rotatable>{

		@Override
		public Rotatable init(gwtquery.plugins.ui.Ui ui,
				WidgetOptions<?> options) {
			return new Rotatable(ui, (Rotatable.Options)options.cast());
		}
	 }
		
		public static final Class<Rotatable> Rotatable = Rotatable.class;
		
		static{
			registerPlugin(Rotatable.class, new RotatablePlugin());
		}
		
		public Rotatable(gwtquery.plugins.ui.Ui ui, 
				Options options) {
			super(ui, "rotatable", options);
		
		}
		
		 
	 

}