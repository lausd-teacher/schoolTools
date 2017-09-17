package net.videmantay.student.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class RubricItemJson extends JavaScriptObject {

	protected RubricItemJson(){}

	public String category;
	public JsArray<CriterionJson>criteria;
	
	public class CriterionJson extends JavaScriptObject{
		protected CriterionJson(){}
		
		public final native boolean getMet()/*-{
			return this.met;
		}-*/;
		
		public final native void setMet(boolean met)/*-{
			this.met = met;
		}-*/;
		
		public final native String description()/*-{
		return this.description;
	}-*/;
		
		public final native void setDescription(String descript)/*-{
		this.description = descript;
	}-*/;
		
		public final native int value()/*-{
		return this.value;
	}-*/;
		
		public final native void setValue(int value)/*-{
		this.value = value;
	}-*/;
	}
}
