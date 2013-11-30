package com.csms.domain;

import java.util.Date;

public class District {

	
	private String id;
	
	private String parentId;
	
	private String name;
	
	private Date cteateTime;
	
	public static enum DISTRICTTYPE {
		PROVINCE,CITY,AREA
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCteateTime() {
		return cteateTime;
	}

	public void setCteateTime(Date cteateTime) {
		this.cteateTime = cteateTime;
	}
	
}
