package com.platform.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 字段表
 * 
 * @author Marker
 */
public class VmetaField implements Serializable {

    private static final long serialVersionUID = -8717557107481501369L;
    private String id;
    private VmetaModule module; // 所属模块
    private String name; // 字段名称
    private String type; // 字段类型
    private Integer maxSize; // 最大长度
    private String canNull; // 是否可以为空
    private String webId; // 前台组件ID
    private String remark;
    private Date createTime;
    private String createIP;
    private Date editTime;
    private String editIP;

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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getMaxSize() {
        return this.maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public String getCanNull() {
        return this.canNull;
    }

    public void setCanNull(String canNull) {
        this.canNull = canNull;
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

    public Date getEditTime() {
        return this.editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public VmetaModule getModule() {
        return module;
    }

    public void setModule(VmetaModule module) {
        this.module = module;
    }

    public String getCreateIP() {
        return createIP;
    }

    public void setCreateIP(String createIP) {
        this.createIP = createIP;
    }

    public String getEditIP() {
        return editIP;
    }

    public void setEditIP(String editIP) {
        this.editIP = editIP;
    }

    public String getWebId() {
        return webId;
    }

    public void setWebId(String webId) {
        this.webId = webId;
    }

}