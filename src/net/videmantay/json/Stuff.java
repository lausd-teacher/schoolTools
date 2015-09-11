package net.videmantay.json;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.query.client.builders.JsonBuilder;

import  net.videmantay.shared.StuffType;

public  interface   Stuff extends JsonBuilder {

public List<AppUser> getStuff();
	
}
