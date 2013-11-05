package com.platform.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

public class Department extends BaseDomain {

    private static final long serialVersionUID = 3038918604365202580L;
    private String id;
    private String name; // 部门名称
    private String code; // 层级编号
    private String superId; // 上级部门ID
    private String remark; // 说明
    private String isStore; //是否厂房
    private List<Users> userses = new ArrayList<Users>(0);

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuperId() {
        return this.superId;
    }

    public void setSuperId(String superId) {
        this.superId = superId;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @JSON(serialize = false)
    public List<Users> getUserses() {
        return this.userses;
    }

    public void setUserses(List<Users> userses) {
        this.userses = userses;
    }

	public String getIsStore() {
		return isStore;
	}

	public void setIsStore(String isStore) {
		this.isStore = isStore;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}