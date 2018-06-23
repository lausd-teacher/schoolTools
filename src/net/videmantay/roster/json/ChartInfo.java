package net.videmantay.roster.json;


import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class ChartInfo extends JavaScriptObject {
	
	protected ChartInfo() {}
	
		public final native Long getDefaultId()/*-{
				return this.defaultId;
		}-*/;
		
		public final native void setDefaultId(Long id)/*-{
			this.defaultId = id;
		}-*/;
		public final native JsArray<Info> getList()/*-{
			return this.list;
		}-*/;

		public class Info extends JavaScriptObject{
			public final native String getTitle()/*-{
				return this.title;
			}-*/;
			
			public final native void setTitle(String title)/*-{
				this.title = title;
			}-*/;
			public final native Long getId()/*-{
				return this.id;
			}-*/;
			
			public final native void setId(Long id)/*-{
				this.id = id;
			}-*/;
		}

}
