package net.videmantay.roster.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.core.client.JsArrayString;

public class StationsJson extends JavaScriptObject {
	protected StationsJson(){}

	public final native String getStationDuration()/*-{
		return this.stationDuration;
	}-*/;
	public final native  String getTransitionTime()/*-{
			return this.transitionTime;
	}-*/;
	public final native  int getRotationNum()/*-{
			return this.rotationNum;
	}-*/;
	public final native  JsArray<SlotJson> getSlots()/*-{
			return this.slots;
	}-*/;
	public final native  JsArray<StationAreaJson> getAreas()/*-{
			return this.areas;
	}-*/;
	
	//setters
	public final native  StationsJson setStationDuration(String duration)/*-{
			this.stationDuration = duration;
			return this;
	}-*/;
	public final native  StationsJson setTransitionTime(String time)/*-{
				this.transitionTime = time;
				return this;
	}-*/;
	public final native  StationsJson setRotationNum(int num)/*-{
		this.ratationNum = num;
		return this;
	}-*/;
	public final native  StationsJson setSlots(JsArray<SlotJson> slots)/*-{
		this.slots = slots;
		return this;
	}-*/;
	public final native  StationsJson setAreas(JsArray<StationAreaJson> areas)/*-{
		this.areas = areas;
		return this;
	}-*/;
	
	
	public static class SlotJson extends JavaScriptObject{
		protected SlotJson(){}
		public final native   JsArrayNumber getStationNums()/*-{
			return this.stationNums;
		}-*/;
		public final native   JsArrayString getStudentIds()/*-{
			return this.studentIds;
		}-*/;
		
		/* if group id is set
		 * studentIds will be loaded from the group.
		 */
		public final native Integer getGroupNum()/*-{
			return this.groupNum;
		}-*/;
		/////settterss
		public final native void setStationNums(JsArrayNumber num)/*-{
				this.stationNums = nums;
		}-*/;
		public final native void setStudentIds(JsArrayString ids)/*-{
				this.studentIds = ids;
		}-*/;
		public final native void setGroupNum(int num)/*-{
		 this.groupNum = num;
	}-*/;
	}
	
	public static class StationAreaJson extends JavaScriptObject{
		protected StationAreaJson(){};
		public final native   String getTitle()/*-{
				return this.title
		}-*/;
		public final native   String getColor()/*-{
			return this.color;
		}-*/;
		public final native   String getDescription()/*-{
				return this.description;
		}-*/;
		public final native   String getX()/*-{
				return this.x;
		}-*/;
		public final native   String getY()/*-{
			return this.y;
		}-*/ ;
		public final native   String getWidth()/*-{
				return this.width;
		}-*/;
		public final native   String getHeight()/*-{
				return this.height;
		}-*/;
		public final native   String getRotate()/*-{
				return this.rotate;
		}-*/; 
		public final native   String getImage()/*-{
				return this.image;
		}-*/;
		public final native   int getNum()/*-{
			return this.num;
		}-*/;
		public final native   String getZIndex()/*-{
			return this.zIndex;
				}-*/;
		public final native   String getType()/*-{
				return this.type;
		}-*/;
		
		/////setters
		public final native   void setTitle(String title)/*-{
					this.title = title;
		}-*/;
		public final native   void setColor(String color)/*-{
				this.color = color;
		}-*/;
		public final native   void setDescription(String descript)/*-{
				this.description = descript;
		}-*/;
		public final native   void setX(String x)/*-{
				this.x = x;
		}-*/;
		public final native   void setY(String y)/*-{
			this.y = y;
		}-*/ ;
		public final native   void setWidth(String w)/*-{
			this.width = w;
		}-*/;
		public final native   void setHeight(String h)/*-{
			this.height = h;
		}-*/;
		public final native   void setRotate(String r)/*-{
			this.rotate = r;
		}-*/; 
		public final native   void setImage(String img)/*-{
			this.image = img;
		}-*/;
		public final native   void setNum(int num)/*-{
				this.num = num;
		}-*/;
		public final native   void setZIndex(String z)/*-{
			this.zIndex = z;
		}-*/;
		public final native   void setType(String type)/*-{
				this.type = type;
		}-*/;
		
	}	
}
