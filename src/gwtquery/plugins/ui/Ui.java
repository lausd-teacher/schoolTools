package gwtquery.plugins.ui;

import static gwtquery.plugins.ui.interactions.Draggable.Draggable;
import static gwtquery.plugins.ui.interactions.Droppable.Droppable;
import static gwtquery.plugins.ui.interactions.Resizable.Resizable;
import static gwtquery.plugins.ui.interactions.Rotatable.Rotatable;
import static gwtquery.plugins.ui.interactions.Selectable.Selectable;
import static gwtquery.plugins.ui.interactions.Sortable.Sortable;
import static gwtquery.plugins.ui.utilities.Position.Position;
import gwtquery.plugins.ui.interactions.Draggable;
import gwtquery.plugins.ui.interactions.Droppable;
import gwtquery.plugins.ui.interactions.Resizable;
import gwtquery.plugins.ui.interactions.Rotatable;
import gwtquery.plugins.ui.interactions.Selectable;
import gwtquery.plugins.ui.interactions.Sortable;
import gwtquery.plugins.ui.utilities.Position;

import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.js.JsMap;
import com.google.gwt.query.client.plugins.Plugin;

import com.google.gwt.dom.client.Element;


/**
 * Experimental Gwt Query plugin for integrating JQuery UI.
 * 
 * @author Philippe Laflamme
 */
public class Ui extends GQuery {

  public static final Class<Ui> Ui = Ui.class;

  /**
   * Used to register the plugin.
   */
  static {
    GQuery.registerPlugin(Ui.class, new Plugin<Ui>() {
      public Ui init(GQuery gq) {
        return new Ui(gq);
      }
    });
  }

  public Ui(GQuery gq) {
    super(gq);
  }

  public static void registerPlugin(Class<? extends Ui> plugin, UiPlugin<? extends Ui> pluginFactory) {
    if(plugins == null) {
      plugins = JsMap.createObject().cast();
    }
    plugins.put(plugin, pluginFactory);
  }

  private static JsMap<Class<? extends Ui>, UiPlugin<? extends Ui>> plugins;

  public final Draggable draggable() {
    return asWidget(Draggable);
  }

  public final Draggable draggable(Draggable.Options options) {
    return asWidget(Draggable, options);
  }

  public final Draggable draggable(String options) {
    return asWidget(Draggable, options);
  }

  public final Droppable droppable() {
    return asWidget(Droppable);
  }

  public final Droppable droppable(Droppable.Options options) {
    return asWidget(Droppable, options);
  }

  public final Droppable droppable(String options) {
    return asWidget(Droppable, options);
  }

  public final Position position(Position.Options options) {
    return asWidget(Position, options);
  }

  public final Position position(String options) {
    return asWidget(Position, options);
  }

  public final Resizable resizable() {
    return asWidget(Resizable);
  }

  public final Resizable resizable(Resizable.Options options) {
    return asWidget(Resizable, options);
  }

  public final Resizable resizable(String options) {
    return asWidget(Resizable, options);
  }

  public final Selectable selectable() {
    return asWidget(Selectable);
  }

  public final Selectable selectable(Selectable.Options options) {
    return asWidget(Selectable, options);
  }

  public final Selectable selectable(String options) {
    return asWidget(Selectable, options);
  }


  public final Sortable sortable() {
    return asWidget(Sortable);
  }

  public final Sortable sortable(Sortable.Options options) {
    return asWidget(Sortable, options);
  }

  public final Sortable sortable(String options) {
    return asWidget(Sortable, options);
  }

  public final Rotatable rotatable(){
	  return asWidget(Rotatable);
  }
  
  public final Rotatable rotatable(Rotatable.Options options){
	  return asWidget(Rotatable, options);
  }
   

  
  public <T extends Ui> T asWidget(Class<T> plugin) {
    return asWidget(plugin, (WidgetOptions<?>) null);
  }

  public <T extends Ui> T asWidget(Class<T> plugin, String options) {
    return asWidget(plugin, (WidgetOptions<?>) JsonUtils.unsafeEval(options));
  }

  /**
   * Convert to the widget's interface provided by Class literal and using the specified options (may be null).
   */
  @SuppressWarnings("unchecked")
  public <T extends Ui> T asWidget(Class<T> plugin, WidgetOptions<?> options) {
    // GQuery is not a plugin for itself
    if(plugin == Ui) {
      return (T) $(this);
    } else if(plugins != null) {
      return (T) plugins.get(plugin).init(this, options);
    }
    throw new RuntimeException("No plugin registered for class " + plugin);
  }



}
