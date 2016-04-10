package net.videmantay.server.entity;

public class Furniture {

	public Integer top;
	public Integer left;
	public Double rotate;
	public String type;
	public String kind;
	public Integer zIndex;
	public String iconUrl;
	public String backgroundColor;
	
	
	public Integer getTop() {
		return top;
	}
	public void setTop(Integer top) {
		this.top = top;
	}
	public Integer getLeft() {
		return left;
	}
	public void setLeft(Integer left) {
		this.left = left;
	}
	public Double getRotate() {
		return rotate;
	}
	public void setRotate(Double rotate) {
		this.rotate = rotate;
	}
	
	public Integer getZIndex(){
		return zIndex;
	}
	
	public void setZIndex(Integer zIndex){
		this.zIndex = zIndex;
	}
}
