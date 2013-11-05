package com.platform.web.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.platform.domain.AttachedFile;
import com.platform.vo.Page;

public abstract class GenericAction<T> extends ActionSupport {

    protected String tabId;
    protected String windowPanelId;
    protected List<String> idList;
    protected String result = "success";
    protected String ok = "F";
    protected Page<T> page = new Page<T>();

    protected String azparam;
    protected String sortProperty;
    protected String sortType;

    protected List<String> uploadTitle;
    protected List<File> upload;
    protected List<String> uploadFileName;
    protected List<String> uploadContentType;
    protected String savePath;

    protected List<AttachedFile> attachedFileList;

    protected String jsessionid;
    protected String searchType;
    protected List<String> searchValue = new ArrayList<String>();
    
    protected Date SYS_DATE = new Date(); 

    public Page<T> getPage() {
        return page;
    }

    public void setPage(Page<T> page) {
        this.page = page;
    }

    public String getTabId() {
        return tabId;
    }

    public void setTabId(String tabId) {
        this.tabId = tabId;
    }

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public String getWindowPanelId() {
        return windowPanelId;
    }

    public void setWindowPanelId(String windowPanelId) {
        this.windowPanelId = windowPanelId;
    }

    public List<File> getUpload() {
        return upload;
    }

    public void setUpload(List<File> upload) {
        this.upload = upload;
    }

    public List<String> getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(List<String> uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    public List<String> getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(List<String> uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public List<String> getUploadTitle() {
        return uploadTitle;
    }

    public void setUploadTitle(List<String> uploadTitle) {
        this.uploadTitle = uploadTitle;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getAzparam() {
        return azparam;
    }

    public void setAzparam(String azparam) {
        this.azparam = azparam;
    }

    public String getSortProperty() {
        return sortProperty;
    }

    public void setSortProperty(String sortProperty) {
        this.sortProperty = sortProperty;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public List<AttachedFile> getAttachedFileList() {
        return attachedFileList;
    }

    public String getJsessionid() {
        return jsessionid;
    }

    public void setJsessionid(String jsessionid) {
        this.jsessionid = jsessionid;
    }

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public List<String> getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(List<String> searchValue) {
		this.searchValue = searchValue;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public Date getSYS_DATE() {
		return SYS_DATE;
	}

	public void setSYS_DATE(Date sys_date) {
		SYS_DATE = sys_date;
	}
	
}