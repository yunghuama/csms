package com.platform.domain;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.mysql.jdbc.Connection;

public class Intercom {

    private static final long serialVersionUID = 1559425612087906435L;
    private String id;
    private String title; // 标题
    private String contents; // 内容
    private Users sender; // 发送人
    private String replier; // 接收人集合(多个UsersId以","分隔)
    private String copier; // 抄送人集合(多个UsersId以","分隔)
    private String readFlag; // 针对自己的已读状态(T:已读,F:未读,S:已发送)
    private Date sendTime; // 发送时间
    private Users owner; // 所属人
    private String inform; // 针对发件人的已读状态,收件人阅读后前台弹出XXX被打开(T:已读,F:未读)
    private String af; // 是否有附件(T:有,F:无)
    private String type; // 消息类型(0普通内部通信，1审批提醒，2日志批注) 
    private String flagType;
    
    public String getFlagType() {
		return flagType;
	}

	public void setFlagType(String flagType) {
		this.flagType = flagType;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return this.contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Users getSender() {
        return this.sender;
    }

    public void setSender(Users sender) {
        this.sender = sender;
    }

    public String getReplier() {
        return this.replier;
    }

    public void setReplier(String replier) {
        this.replier = replier;
    }

    public String getCopier() {
        return this.copier;
    }

    public void setCopier(String copier) {
        this.copier = copier;
    }

    public String getReadFlag() {
        return this.readFlag;
    }

    public void setReadFlag(String readFlag) {
        this.readFlag = readFlag;
    }

    @JSON(format = "yyyy-MM-dd HH:mm:ss")
    public Date getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Users getOwner() {
        return this.owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }

    public String getInform() {
        return this.inform;
    }

    public void setInform(String inform) {
        this.inform = inform;
    }

    public String getAf() {
        return af;
    }

    public void setAf(String af) {
        this.af = af;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
    
}