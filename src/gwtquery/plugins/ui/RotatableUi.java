package gwtquery.plugins.ui;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;

public class RotatableUi extends JavaScriptObject {

	protected RotatableUi(){}
	
	public final native RotatableUi create()/*-{
	
			return {};
	}-*/;
	
	public final native Element element()/*-{
			return this["element"];
	}-*/;
	
	public final native Angle angle()/*-{
			return this["angle"];
	}-*/;
	
	public static class Angle extends JavaScriptObject{
		protected Angle(){}
		
		public final native Double start()/*-{
				return this["start"];
		}-*/;
		
		public final native Double current()/*-{
		return this["current"];
	}-*/;
		public final native Double stop()/*-{
		return this["stop"];
	}-*/;
	}
}
