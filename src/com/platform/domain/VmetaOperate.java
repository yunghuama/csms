package com.platform.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作表
 * 
 * @author Marker
 */
public class VmetaOperate implements Serializable {

    private static final long serialVersionUID = -658592704453725677L;
    private String id;
    private String name;
    private String webId; // 前台操作组件的ID
    private String scopeDataVisible; // 涉及到的其他用户记录是否可操作
    private String remark;
    private Date createTime;
    private String createIP;
    private Date editTime;
    private String editIP;
    private VmetaModule module; // 所属模块

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

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateIP() {
        return this.createIP;
    }

    public void setCreateIP(String createIP) {
        this.createIP = createIP;
    }

    public Date getEditTime() {
        return this.editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getEditIP() {
        return this.editIP;
    }

    public void setEditIP(String editIP) {
        this.editIP = editIP;
    }

    public VmetaModule getModule() {
        return module;
    }

    public void setModule(VmetaModule module) {
        this.module = module;
    }

    public String getWebId() {
        return webId;
    }

    public void setWebId(String webId) {
        this.webId = webId;
    }

    public String getScopeDataVisible() {
        return scopeDataVisible;
    }

    public void setScopeDataVisible(String scopeDataVisible) {
        this.scopeDataVisible = scopeDataVisible;
    }

}