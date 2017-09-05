package gwtquery.plugins.ui;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;


public class DroppableUi extends JavaScriptObject {

	protected DroppableUi(){}
	
	public final native Draggable draggable()/*-{
		return this.draggable;
	}-*/;
	
	public final native Draggable helper()/*-{
			return this.helper;
	}-*/;
	
	public final native Offset position()/*-{
		return this.position;
	}-*/;
	
	public final native Offset offset()/*-{
			return this.offset;
	}-*/;
	
	public static class Offset extends JavaScriptObject{
		
		protected Offset(){}
		public final native int left()/*-{
			return this.left;
		}-*/;
		
		public final native int top()/*-{
			return this.top;
		}-*/;
	}
	public static class Draggable extends JavaScriptObject{
		protected Draggable(){}
		
		public native final String context()/*-{
				return this.context;
		}-*/;
		
		public native final Element get()/*-{
				return this[0];
		}-*/;
	}
}
