package net.videmantay.shared.json;

import com.google.gwt.core.client.JavaScriptObject;

public class EventJson extends JavaScriptObject {

		protected EventJson(){}
		
		
	public final native String getId()/*-{
		return this.id;
		}-*/;	
		
	public final native AttachmentJson[] getAttachments()/*-{
			return this.attachments;
	}-*/;
	
	public final native String getColorId()/*-{
	return this.colorId;
	}-*/;
	
	public final native String getCreated()/*-{
	return this.created;
	}-*/;
	
	public final native String getDescription()/*-{
	return this.description;
	}-*/;
	
	public final native TimeJson getStart()/*-{
		return this.start;
	}-*/;
	
	public final native TimeJson getEnd()/*-{
	return this.end;
	}-*/;
	
	public final native String getSummary()/*-{
	return this.end;
	}-*/;
	
	public final native String getRecurringEventId()/*-{
	return this.recurringEventId;
	}-*/;
	
	public final native String getUpdate()/*-{
	return this.update;
	}-*/;
	
	public final native String getVisibility()/*-{
	return this.visibility;
	}-*/;
	
	public final native ExtProp getExtProp()/*-{
	return this.extendedProperties;
	}-*/;
	
	public final native EventJson setColorId(String str)/*-{
			this.colorId = str;
			return this;
	}-*/;
	
	public final native EventJson setDescription(String str)/*-{
	this.description = str;
	return this;
	}-*/;
	
	public final native EventJson setSummary(String str)/*-{
	this.summary = str;
	return this;
	}-*/;
	
	public final native EventJson setEnd(TimeJson time)/*-{
	this.end = time;
	return this;
	}-*/;
	
	public final native EventJson setStart(TimeJson time)/*-{
	this.start = time;
	return this;
	}-*/;
	
	public final native EventJson setAttachements(AttachmentJson[] attachements)/*-{
	this.attachements = attachements;
	return this;
	}-*/;
	
	public final native Reminder[] getReminders()/*-{
	return this.reminders.overrides;
		}-*/;

		public final native EventJson setReminders(Reminder[] overrides)/*-{
			this.reminders.overrides = overrides;
		}-*/;

		public final native Boolean getUseDefalut()/*-{
			return this.useDefalut;
		}-*/;

		public final native EventJson setUseDefault(Boolean useDefault)/*-{
			return this.useDefault = useDefault;
		}-*/;

	public final native Gadget getGadget()/*-{
		return this.gadget;
	}-*/;
	
	
}
