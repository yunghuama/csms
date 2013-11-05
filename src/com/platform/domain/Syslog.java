package com.platform.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class Syslog implements Serializable {

    private static final long serialVersionUID = 4483369924191293455L;
    private String id;
    private String contents; // 内容
    private Date opTime; // 操作时间
    private Users optor; // 操作人
    private String ipAdd; // IP地址
    private String type; // 操作类型

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContents() {
        return this.contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @JSON(format = "yyyy-MM-dd HH:mm:ss")
    public Date getOpTime() {
        return this.opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    public Users getOptor() {
        return this.optor;
    }

    public void setOptor(Users optor) {
        this.optor = optor;
    }

    public String getIpAdd() {
        return this.ipAdd;
    }

    public void setIpAdd(String ipAdd) {
        this.ipAdd = ipAdd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}