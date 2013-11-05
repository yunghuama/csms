package com.platform.domain;

import java.util.Date;

public class AttachedFile {
	
	private String id;
	private String superId; // 所属对象ID
	private String title; // 自定义标题
	private String fileName; // 文件原名
	private String filePath; // 保存路径
	private String fileType; // 后缀名
	private String contentType; // 文件流类型
	private Date createTime;
	private String isConvert;//是否已经转换过0: 转换完成,1：没转换
	private String extendType; //扩展类型分类（根据需要自己定义）
	
	public AttachedFile(){}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSuperId() {
		return superId;
	}

	public void setSuperId(String superId) {
		this.superId = superId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getIsConvert() {
		return isConvert;
	}

	public void setIsConvert(String isConvert) {
		this.isConvert = isConvert;
	}

	public String getExtendType() {
		return extendType;
	}

	public void setExtendType(String extendType) {
		this.extendType = extendType;
	}
	
}