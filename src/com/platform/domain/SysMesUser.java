package com.platform.domain;

import java.io.Serializable;

import org.apache.struts2.json.annotations.JSON;

public class SysMesUser implements Serializable {

    private static final long serialVersionUID = -8261977006316238045L;
    private String id;
    private Users users; // 用户对象
    private SysMessage sysMessage; // 系统消息对象
    private String readFlag; // 已读状态(T:已读,F:未读)

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Users getUsers() {
        return this.users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @JSON(serialize = false)
    public SysMessage getSysMessage() {
        return this.sysMessage;
    }

    public void setSysMessage(SysMessage sysMessage) {
        this.sysMessage = sysMessage;
    }

    public String getReadFlag() {
        return this.readFlag;
    }

    public void setReadFlag(String readFlag) {
        this.readFlag = readFlag;
    }
}