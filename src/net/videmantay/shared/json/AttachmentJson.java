package net.videmantay.shared.json;

import com.google.gwt.core.client.JavaScriptObject;

public class AttachmentJson extends JavaScriptObject{
	protected AttachmentJson(){}
	
	public final native String getFileId()/*-{
		return this.fileId;
	}-*/;
	
	public final native String getFileUrl()/*-{
	return this.fileUrl;
}-*/;
	
	public final native String getIconLink()/*-{
	return this.iconLink;
}-*/;
	
	public final native String getMimetype()/*-{
	return this.mimeType;
}-*/;
	public final native String getTitle()/*-{
	return this.title;
}-*/;
}//end AttachmentJson
