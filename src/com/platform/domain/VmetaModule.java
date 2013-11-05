package com.platform.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 模块表
 * 
 * @author Marker
 */
public class VmetaModule implements Serializable {

    private static final long serialVersionUID = -7012827856662915000L;
    private String id;
    private String name;
    private String superId;
    private Date createTime;
    private Date editTime;
    private String createIP;
    private String editIP;
    private Set operates = new HashSet(0);
    
    private String hasDepartment;
    private String hasUsers;
    private String hasField;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getCreateIP() {
        return createIP;
    }

    public void setCreateIP(String createIP) {
        this.createIP = createIP;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEditIP() {
        return editIP;
    }

    public void setEditIP(String editIP) {
        this.editIP = editIP;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public Set getOperates() {
        return operates;
    }

    public void setOperates(Set operates) {
        this.operates = operates;
    }

	public String getHasDepartment() {
		return hasDepartment;
	}

	public void setHasDepartment(String hasDepartment) {
		this.hasDepartment = hasDepartment;
	}

	public String getHasUsers() {
		return hasUsers;
	}

	public void setHasUsers(String hasUsers) {
		this.hasUsers = hasUsers;
	}

	public String getHasField() {
		return hasField;
	}

	public void setHasField(String hasField) {
		this.hasField = hasField;
	}

}