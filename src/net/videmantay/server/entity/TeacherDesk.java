package net.videmantay.server.entity;

import java.io.Serializable;

public class TeacherDesk implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5006652809201296034L;
	
	private Integer top;
	private Integer left;
	private Double rotate;
	private String imgBackgroundUrl;
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
	public String getImgBackgroundUrl() {
		return imgBackgroundUrl;
	}
	public void setImgBackgroundUrl(String imgBackgroundUrl) {
		this.imgBackgroundUrl = imgBackgroundUrl;
	}
	

}
