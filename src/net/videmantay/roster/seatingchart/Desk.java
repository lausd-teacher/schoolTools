package net.videmantay.roster.seatingchart;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class Desk{
	
	protected Desk() {
		
	}
	
	
	
	public static HTMLPanel getDouble(){
		return new HTMLPanel("<div class='desk doubledesk'><table style='width:100%;height:100%'><tr>"
				+"<td><div class='seat pos1'></div></td><td><div class='seat pos2'></div></td></tr>"
		+"</table></div>");
	}
	
}
