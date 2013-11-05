package com.csms.domain;

import com.platform.domain.Users;

public class GloRule {
	
	private String id;
	
	private String name;
	
	private String content;
	
	private String department;
	
	private String state;
	
	private Users creator;
	
	private long createTime;
	
	

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Users getCreator() {
		return creator;
	}

	public void setCreator(Users creator) {
		this.creator = creator;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
