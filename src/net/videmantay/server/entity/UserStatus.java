package net.videmantay.server.entity;

public enum UserStatus{TEACHER("TEACHER"), STUDENT("STUDENT"), ADMIN("ADMIN"), AIDE("AIDE");
private String status = "STUDENT";
UserStatus(String status){
	this.status = status;
}

public String toString(){
	return status;
}

}// end UserStatus 

