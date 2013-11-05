package com.platform.web.action;

import java.io.File;
import java.io.InputStream;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.platform.domain.AttachedFile;
import com.platform.service.AttachedFileService;

@Controller
@Scope("prototype")
public class FileDownloadAction extends ActionSupport {

    private static final long serialVersionUID = -8615201347467363096L;
    @Autowired
    private AttachedFileService attachedFileService;
    private String id;
    private String path;
    private String fileName;
    private String contentType;

    public String execute() throws Exception {
        AttachedFile file = attachedFileService.findFileById(id);
        path = file.getFilePath();
        if(new File(ServletActionContext.getServletContext().getRealPath(path)).exists()){
        	contentType = file.getContentType();
            fileName = new String((file.getTitle() + "." + file.getFileType()).getBytes(), "ISO-8859-1");
            return SUCCESS;
        }else
        	return "failure";
        
    }

    public InputStream getInputStream() throws Exception {
        return ServletActionContext.getServletContext().getResourceAsStream(path);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

	public String getContentType() {
		return contentType;
	}
}