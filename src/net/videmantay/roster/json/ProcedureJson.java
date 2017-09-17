package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;

public class ProcedureJson extends JavaScriptObject {
	protected ProcedureJson(){}
	
	public final native Integer getStep()/*-{}-*/;
	public final native String getProcedure()/*-{}-*/;
	
	public final native ProcedureJson setStep(Integer step)/*-{
			this.step = step;
	}-*/;
	public final native ProcedureJson setProcedure(String proc)/*-{
			this.procedure = proc;
	}-*/;
}
