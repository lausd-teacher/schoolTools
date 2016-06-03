package gwtquery.plugins.ui;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;

public class DraggableUi extends JavaScriptObject {

	protected DraggableUi(){}
	public final native Helper getHelper()/*-{
		return this.helper;
	}-*/;

	public static class Helper extends JavaScriptObject{
		protected Helper(){}
		
		public native final String context()/*-{
				return this.context;
		}-*/;
		
		public native final Element get()/*-{
				return this[0];
		}-*/;
	}
}
