package com.csms.domain;

import com.platform.domain.Users;

public class Rule {
	
	private String id;
	
	private String name;
	
	private String ruleDay;
	
	private String ruleStartTime;
	
	private String ruleEndTime;
	
	private String content;
	
	private String department;
	
	private String state;
	
	private String timeType;
	
	private Users creator;
	
	private long createTime;
	
	private String remark;
	
	private String type;
	
	//所属部门
	private Group group;
	

	
	
	
	public Group getGroup() {
		return group;
	}



	public void setGroup(Group group) {
		this.group = group;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getId() {
		return id;
	}
	
	

	public String getDepartment() {
		return department;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	public void setDepartment(String department) {
		this.department = department;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getRuleDay() {
		return ruleDay;
	}

	public void setRuleDay(String ruleDay) {
		this.ruleDay = ruleDay;
	}

	public String getRuleStartTime() {
		return ruleStartTime;
	}

	public void setRuleStartTime(String ruleStartTime) {
		this.ruleStartTime = ruleStartTime;
	}

	public String getRuleEndTime() {
		return ruleEndTime;
	}

	public void setRuleEndTime(String ruleEndTime) {
		this.ruleEndTime = ruleEndTime;
	}



	public String getTimeType() {
		return timeType;
	}



	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	
}
