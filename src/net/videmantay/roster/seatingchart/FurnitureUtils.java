package net.videmantay.roster.seatingchart;

import com.google.gwt.user.client.ui.HTMLPanel;

public class FurnitureUtils {

	private FurnitureUtils(){}
	
	public static HTMLPanel doubleDesk(){
		return new HTMLPanel("<div class='desk doubledesk'><span class='deskDeleter'></span><table style='width:100%;height:100%'><tr>"
							+"<td><div class='seat pos1'></div></td><td><div class='seat pos2'></div></td></tr>"
							+"</table></div>");
	}
	
	public static HTMLPanel singleDesk(){
		return new HTMLPanel("<div class='desk doubledesk'><span class='deskDeleter'></span><table style='width:100%;height:100%'><tr>"
							+"<td><div class='seat pos1'></div></td><td><div class='seat pos2'></div></td></tr>"
							+"</table></div>");
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
		switch(kind){
		case FurnitureKind.DOUBLE_DESK: return doubleDesk();
		default: return doubleDesk();
		
		}
	}
	
	public static HTMLPanel byIconId(String iconId){
		switch(iconId){
		case  "doubleDeskIcon" : return doubleDesk();
		case "singleDeskIcon":return singleDesk();
		default: return doubleDesk();
		}
	}
}
