package net.videmantay.server;

public class UserInfo {
	private String firstName;
	private String lastName;
	private String picUrl = "Smiley.png";
	private String thumbPic;
	private String hwColumn;
	
	public UserInfo(){};
	
	public UserInfo(String firstName, String lastName){
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public UserInfo(String firstName, String lastName, String picUrl){
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setPicUrl(picUrl);
		
	}
	
	public UserInfo(String firstName, String lastName, String picUrl, String col){
		this(firstName, lastName, picUrl);
		this.setHwColumn(col);
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getThumbPic() {
		return thumbPic;
	}

	public void setThumbPic(String thumbPic) {
		this.thumbPic = thumbPic;
	}
	
	public String getWebName(){
		return "/"+this.getFirstName()+"/"+this.getLastName()+"/";
	}
	
	public String getHwColumn(){
		return this.hwColumn;
	}
	
	public void setHwColumn(String col){
		this.hwColumn = col;
	}
}

