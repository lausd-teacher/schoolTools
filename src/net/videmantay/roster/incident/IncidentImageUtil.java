package net.videmantay.roster.incident;

public class IncidentImageUtil {

	public static final String imageHTML(String image){
		//set up the html
				String html = "<svg data-incident-image='" + image + "'  viewBox='0 0 150 175' class='incidentImage' style='width:5em; height:6em'>"
						+"<use  xmlns:xlink='http://www.w3.org/1999/xlink' xlink:href='../img/allIcons.svg#" 
						+ image+"' /></svg>";
		return html;
	}
}
