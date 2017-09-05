package gwtquery.plugins.ui.interactions;

import gwtquery.plugins.ui.Ui;
import gwtquery.plugins.ui.UiPlugin;
import gwtquery.plugins.ui.UiWidget;
import gwtquery.plugins.ui.WidgetOptions;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayInteger;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;

/**
 * Implements jQuery-UI's Draggable
 * 
 * @see <a href="http://docs.jquery.com/UI/Draggable">jQuery-UI's Draggable</a>
 * 
 * @author Philippe Laflamme
 */
public class Draggable extends UiWidget<Draggable, Draggable.Options> {

  public static class Options extends WidgetOptions<Options> {

    protected Options() {

    }

    public static native final Options create()
    /*-{
      return {};
    }-*/;

    public native final Options addClasses(boolean addClasses)
    /*-{
      this["addClasses"] = addClasses;
      return this;
    }-*/;

    public native final boolean addClasses()
    /*-{
      return this["addClasses"];
    }-*/;

    public native final Options appendTo(String selector)
    /*-{
      this["appendTo"] = selector;
      return this;
    }-*/;

    public native final Options appendTo(Element element)
    /*-{
      this["appendTo"] = element;
      return this;
    }-*/;

    public native final Options appendTo(NodeList<?> value)
    /*-{
      this["appendTo"] = value;
      return this;
    }-*/;

    public final Options of(GQuery of) {
      this.appendTo(of.get());
      return this;
    };

    public native final Options axis(String axis)
    /*-{
      this["axis"] = axis;
      return this;
    }-*/;

    public native final String axis()
    /*-{
      return this["axis"];
    }-*/;

    public native final Options cancel(String cancel)
    /*-{
      this["cancel"] = cancel;
      return this;
    }-*/;

    public native final String cancel()
    /*-{
      return this["cancel"];
    }-*/;

    public native final Options connectToSortable(String connectToSortable)
    /*-{
      this["connectToSortable"] = connectToSortable;
      return this;
    }-*/;

    public native final String connectToSortable()
    /*-{
      return this["connectToSortable"];
    }-*/;

    public native final Options containment(String containment)
    /*-{
      this["containment"] = containment;
      return this;
    }-*/;

    public native final Options containment(Element containment)
    /*-{
      this["containment"] = containment;
      return this;
    }-*/;

    public native final Options containment(JsArrayInteger containment)
    /*-{
      this["containment"] = containment;
      return this;
    }-*/;

    public final Options containment(GQuery gquery) {
      this.containment(gquery.get(0));
      return this;
    }

    public native final Options cursor(String cursor)
    /*-{
      this["cursor"] = cursor;
      return this;
    }-*/;

    public native final String cursor()
    /*-{
      return this["cursor"];
    }-*/;

    public native final Options cursorAt(CursorAt cursorAt)
    /*-{
      this["cursorAt"] = cursorAt;
      return this;
    }-*/;

    public native final CursorAt cursorAt()
    /*-{
      return this["cursorAt"];
    }-*/;

    public native final Options delay(int delay)
    /*-{
      this["delay"] = delay;
      return this;
    }-*/;

    public native final int delay()
    /*-{
      return this["delay"];
    }-*/;

    public native final Options distance(int distance)
    /*-{
      this["distance"] = distance;
      return this;
    }-*/;

    public native final int distance()
    /*-{
      return this["distance"];
    }-*/;

    public native final Options grid(JsArrayInteger grid)
    /*-{
      this["grid"] = grid;
      return this;
    }-*/;

    public native final Options grid(int x, int y)
    /*-{
      this["grid"] = [x,y];
      return this;
    }-*/;

    public native final JsArrayInteger grid()
    /*-{
      return this["grid"];
    }-*/;

    public native final Options handle(String handle)
    /*-{
      this["handle"] = handle;
      return this;
    }-*/;

    public native final Options handle(Element handle)
    /*-{
      this["handle"] = handle;
      return this;
    }-*/;

    public final Options handle(GQuery gquery) {
      this.handle(gquery.get(0));
      return this;
    }

    public native final Options helper(String helper)
    /*-{
      this["helper"] = helper;
      return this;
    }-*/;

    public native final String helper()
    /*-{
      return this["helper"];
    }-*/;

    public native final Options iframeFix(boolean iframeFix)
    /*-{
      this["iframeFix"] = iframeFix;
      return this;
    }-*/;

    public native final boolean iframeFix()
    /*-{
      return this["iframeFix"];
    }-*/;

    public native final Options opacity(double opacity)
    /*-{
      this["opacity"] = opacity;
      return this;
    }-*/;

    public native final double opacity()
    /*-{
      return this["opacity"];
    }-*/;

    public native final Options refreshPositions(boolean refreshPositions)
    /*-{
      this["refreshPositions"] = refreshPositions;
      return this;
    }-*/;

    public native final boolean refreshPositions()
    /*-{
      return this["refreshPositions"];
    }-*/;

    public native final Options revert(boolean revert)
    /*-{
      this["revert"] = revert;
      return this;
    }-*/;

    public native final Options revert(String revert)
    /*-{
      this["revert"] = revert;
      return this;
    }-*/;

    public native final String revert()
    /*-{
      return this["revert"];
    }-*/;

    public native final boolean isRevert()
    /*-{
      return this["revert"];
    }-*/;

    public native final Options revertDuration(int revertDuration)
    /*-{
      this["revertDuration"] = revertDuration;
      return this;
    }-*/;

    public native final int revertDuration()
    /*-{
      return this["revertDuration"];
    }-*/;

    public native final Options scope(String scope)
    /*-{
      this["scope"] = scope;
      return this;
    }-*/;

    public native final String scope()
    /*-{
      return this["scope"];
    }-*/;

    public native final Options scroll(boolean scroll)
    /*-{
      this["scroll"] = scroll;
      return this;
    }-*/;

    public native final boolean scroll()
    /*-{
      return this["scroll"];
    }-*/;

    public native final Options scrollSensitivity(int scrollSensitivity)
    /*-{
      this["scrollSensitivity"] = scrollSensitivity;
      return this;
    }-*/;

    public native final int scrollSensitivity()
    /*-{
      return this["scrollSensitivity"];
    }-*/;

    public native final Options scrollSpeed(int scrollSpeed)
    /*-{
      this["scrollSpeed"] = scrollSpeed;
      return this;
    }-*/;

    public native final Integer scrollSpeed()
    /*-{
      return this["scrollSpeed"];
    }-*/;

    public native final Options snap(boolean snap)
    /*-{
      this["snap"] = snap;
      return this;
    }-*/;

    public native final Options snap(String snap)
    /*-{
      this["snap"] = snap;
      return this;
    }-*/;

    public native final Options snapMode(String snapMode)
    /*-{
      this["snapMode"] = snapMode;
      return this;
    }-*/;

    public native final String snapMode()
    /*-{
      return this["snapMode"];
    }-*/;

    public native final Options snapTolerance(int snapTolerance)
    /*-{
      this["snapTolerance"] = snapTolerance;
      return this;
    }-*/;

    public native final int snapTolerance()
    /*-{
      return this["snapTolerance"];
    }-*/;

    public native final Options stack(String stack)
    /*-{
      this["stack"] = stack;
      return this;
    }-*/;

    public native final Options stack(Element stack)
    /*-{
      this["stack"] = stack;
      return this;
    }-*/;

    public final Options stack(GQuery gquery) {
      this.stack(gquery.get(0));
      return this;
    }

    public native final Options zIndex(int zIndex)
    /*-{
      this["zIndex"] = zIndex;
      return this;
    }-*/;

    public native final int zIndex()
    /*-{
      return this["zIndex"];
    }-*/;
    
    //new added events
    public final native Options start(Function f)/*-{
   	this["start"] = function(event, ui){
   		 f.@com.google.gwt.query.client.Function::f(Lcom/google/gwt/user/client/Event;[Ljava/lang/Object;)(event, [ui]);
   	}
   		return this;
   }-*/;
       
       public final native Options stop(Function f)/*-{
   	this["stop"] = function(event, ui){
   		 f.@com.google.gwt.query.client.Function::f(Lcom/google/gwt/user/client/Event;[Ljava/lang/Object;)(event, [ui]);
   	}
   		return this;
   }-*/;
       
       public final native Options drag(Function f)/*-{
   	this["drag"] = function(event, ui){
   		 f.@com.google.gwt.query.client.Function::f(Lcom/google/gwt/user/client/Event;[Ljava/lang/Object;)(event, [ui]);
   	}
   		return this;
   }-*/;
       
       public final native Options create(Function f)/*-{
   	this["create"] = function(event, ui){
   		 f.@com.google.gwt.query.client.Function::f(Lcom/google/gwt/user/client/Event;[Ljava/lang/Object;)(event, [ui]);
   	}
   		return this;
   }-*/;

  }

  public static class Event extends JavaScriptObject {

    public static final String drag = "drag";

    public static final String start = "dragstart";

    public static final String stop = "dragstop";

    protected Event() {

    }
  }

  /**
   * Used to register the plugin.
   */
  private static class DraggablePlugin implements UiPlugin<Draggable> {

    @Override
	public Draggable init(Ui ui, WidgetOptions<?> options) {
      return new Draggable(ui, (Draggable.Options) options.cast());
    }

  }

  public static final Class<Draggable> Draggable = Draggable.class;

  static {
    registerPlugin(Draggable.class, new DraggablePlugin());
  }

  public Draggable(Ui ui, Draggable.Options options) {
    super(ui, "draggable", options);
  }
}