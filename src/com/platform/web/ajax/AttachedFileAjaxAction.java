package com.platform.web.ajax;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.platform.constants.StringConstant;
import com.platform.domain.AttachedFile;
import com.platform.service.AttachedFileService;
import com.platform.util.UploadHelper;
import com.platform.web.action.GenericAction;

@Controller
@Scope("prototype")
public class AttachedFileAjaxAction extends GenericAction<AttachedFile>{
    private static final long serialVersionUID = -7651297351212443171L;

    @Autowired
    private AttachedFileService attachedFileService;

    private String attachedFileId;
    private String deleteSuccess;
    private List<AttachedFile> listAttachedFile;
    private String superId;

    /** 删除附件 */
    public String deleteAttachedFile() throws Exception {
        attachedFileService.delete(attachedFileId);      
        deleteSuccess = StringConstant.TRUE;
        return Action.SUCCESS;
    }
    
    /** 保存附件 */
    public String saveAttachedFile() throws Exception{
    	try{
    		UploadHelper uploadHelper = new UploadHelper(upload, uploadFileName, uploadTitle, uploadContentType,"/uploads", UploadHelper.UID);
            listAttachedFile = uploadHelper.getAttachedFiles();
        	attachedFileService.save(listAttachedFile, superId);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return Action.SUCCESS;
    }
    
    
    public String getSuperId() {
		return superId;
	}

	public void setSuperId(String superId) {
		this.superId = superId;
	}

	public void setAttachedFileId(String attachedFileId) {
        this.attachedFileId = attachedFileId;
    }

    public String getDeleteSuccess() {
        return deleteSuccess;
    }

	public AttachedFileService getAttachedFileService() {
		return attachedFileService;
	}

	public void setAttachedFileService(AttachedFileService attachedFileService) {
		this.attachedFileService = attachedFileService;
	}

	public List<AttachedFile> getListAttachedFile() {
		return listAttachedFile;
	}

	public void setListAttachedFile(List<AttachedFile> listAttachedFile) {
		this.listAttachedFile = listAttachedFile;
	}

	public String getAttachedFileId() {
		return attachedFileId;
	}

	public void setDeleteSuccess(String deleteSuccess) {
		this.deleteSuccess = deleteSuccess;
	}
    
}