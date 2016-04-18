package gwtquery.plugins.ui.interactions;

import com.google.gwt.core.client.JavaScriptObject;

public class CursorAt extends JavaScriptObject {

	protected CursorAt(){
		
		
	}
	
	
	
	public final native CursorAt setTop(int top)/*-{
	this.top = top;
	return this;
}-*/;
	

public final native CursorAt setLeft(int left)/*-{
this.left = left;
return this;
}-*/;

public final native int getTop()/*-{
	return this.top;
}-*/;

public final native int getLeft()/*-{
return this.left;
}-*/;

public final native CursorAt setRight(int right)/*-{
this.right = right;
return this;
}-*/;

public final native int getRight()/*-{
	return this.right;
}-*/;

public final native CursorAt setBottom(int bottom)/*-{
this.bottom = bottom;
return this;
}-*/;

public final native int getBottom()/*-{
	return this.bottom;
}-*/;
	
public static native final CursorAt create()/*-{
	return {top:0, left:0};
		
		
}-*/;
}
