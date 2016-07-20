package net.videmantay.roster.seatingchart;

import com.google.gwt.user.client.ui.HTMLPanel;

public class FurnitureUtils {

	private FurnitureUtils(){}
	
	public static HTMLPanel doubleDesk(){
		HTMLPanel html= new HTMLPanel("<div class='desk doubledesk'><span class='deskDeleter material-icons'>cancel</span><table style='width:100%;height:100%'><tr>"
							+"<td><div class='seat pos1'></div></td><td><div class='seat pos2'></div></td></tr>"
							+"</table></div>");
		html.setStylePrimaryName("desk-wrapper");
		html.setSize("7.2em", "3.6em");
		return html;
		
	}
	
	public static HTMLPanel singleDesk(){
		HTMLPanel html = new HTMLPanel("<div class='desk singledesk'><span class='deskDeleter material-icons'>cancel</span><table style='width:100%;height:100%'><tr>"
				+"<td><div class='seat pos1'></div></td></tr>"
				+"</table></div>");
		html.setStylePrimaryName("desk-wrapper");
		html.setSize("3.6em", "3.6em");
		return html;
	}
	
	public static HTMLPanel kidneyTable(){
		return new HTMLPanel("<div class='desk doubledesk'><span class='deskDeleter'></span><table style='width:100%;height:100%'><tr>"
							+"<td><div class='seat pos1'></div></td><td><div class='seat pos2'></div></td></tr>"
							+"</table></div>");
	}
	
	public static HTMLPanel carpet(){
		return new HTMLPanel("<div class='desk doubledesk'><span class='deskDeleter'></span><table style='width:100%;height:100%'><tr>"
							+"<td><div class='seat pos1'></div></td><td><div class='seat pos2'></div></td></tr>"
							+"</table></div>");
	}
	
	public static HTMLPanel teacherDesk(){
		return new HTMLPanel("<div class='desk doubledesk'><span class='deskDeleter'></span><table style='width:100%;height:100%'><tr>"
							+"<td><div class='seat pos1'></div></td><td><div class='seat pos2'></div></td></tr>"
							+"</table></div>");
	}
	
	public static HTMLPanel bookshelf(){
		return new HTMLPanel("<div class='desk doubledesk'><span class='deskDeleter'></span><table style='width:100%;height:100%'><tr>"
							+"<td><div class='seat pos1'></div></td><td><div class='seat pos2'></div></td></tr>"
							+"</table></div>");
	}
	
	public static HTMLPanel byKind(String kind){
		HTMLPanel html;
		switch(kind){
		case "double": html = doubleDesk();break;
		case "single": html = singleDesk();break;
		default: html= doubleDesk();
		}
		
		return html;
	}
	
	public static HTMLPanel byIconId(String iconId){
		switch(iconId){
		case  "doubleDeskIcon" : return doubleDesk();
		case "singleDeskIcon":return singleDesk();
		default: return doubleDesk();
		}
	}
}
