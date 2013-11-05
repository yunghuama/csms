package com.platform.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

public class SysMessage extends BaseDomain {

    private static final long serialVersionUID = 3148487850386722505L;
    private String contents; // 内容
    private String type; // 类型
    private Date sendTime; // 发送时间
    private Set sysMesUsers = new HashSet(0);

    public String getContents() {
        return this.contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JSON(format = "yyyy-MM-dd HH:mm:ss")
    public Date getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @JSON(serialize = false)
    public Set getSysMesUsers() {
        return this.sysMesUsers;
    }

    public void setSysMesUsers(Set sysMesUsers) {
        this.sysMesUsers = sysMesUsers;
    }

}