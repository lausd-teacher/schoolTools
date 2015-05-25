package gwtquery.plugins.ui.interactions;

import com.google.gwt.core.client.JavaScriptObject;

import gwtquery.plugins.ui.UiPlugin;
import gwtquery.plugins.ui.UiWidget;
import gwtquery.plugins.ui.WidgetOptions;



public class Rotatable  extends UiWidget<Rotatable, Rotatable.Options>{

	

	public static class Options extends WidgetOptions<Options> {

	    protected Options() {

	    }
	}
	
	 public static native final Options create()
	    /*-{
	      return {};
	    }-*/;
	 
	 
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
