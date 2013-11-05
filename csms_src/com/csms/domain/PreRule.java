package com.csms.domain;

import com.platform.domain.Users;

public class PreRule {
	
	private String id;
	
	private String name;
	
	private long executeDate;
	
	private String rule;
	
	private String content;
	
	private Users creator;
	
	private long createTime;

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

	public long getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(long executeDate) {
		this.executeDate = executeDate;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}
	
}
