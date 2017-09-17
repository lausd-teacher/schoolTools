package net.videmantay.student.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class RubricJson extends JavaScriptObject {

	protected RubricJson(){}
	
	public final native JsArray<RubricItemJson> getItems()/*-{
		return this.items;
	}-*/;
	
	public final native void setItems(JsArray<RubricItemJson> items)/*-{
	 this.items = items;
}-*/;
	
	public final native void addItem(RubricItemJson item)/*-{
	 this.items.push(item);
}-*/;
	public final native void removeItem(int index)/*-{
	 this.items.splice(index,1);
}-*/;
	public final native String getName()/*-{
	return this.name;
}-*/;
	
	public final native void setName(String name)/*-{
	 this.name = name;
}-*/;
	public final native Long id()/*-{
	return this.id;
}-*/;
	
}
