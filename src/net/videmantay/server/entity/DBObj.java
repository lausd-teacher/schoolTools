package net.videmantay.server.entity;

import java.util.Date;

public abstract class DBObj {
	
	private Long id;
	private Date createOn;
	private String createdBy;
	private Date lastUpdate;
	private Long versionNum;

}
