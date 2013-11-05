package com.platform.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.platform.constants.IntercomConstant;
import com.platform.domain.AttachedFile;
import com.platform.domain.Intercom;
import com.platform.domain.Users;
import com.platform.service.AttachedFileService;
import com.platform.service.IntercomService;
import com.platform.service.UsersService;
import com.platform.util.UploadHelper;
import com.platform.util.Validate;

@Controller
@Scope("prototype")
public class IntercomAction extends GenericAction<Intercom> {

    private static final long serialVersionUID = -4380027258095334424L;

    @Autowired
    private IntercomService intercomService;
    @Autowired
    private AttachedFileService attachedFileService;
    @Autowired
    private UsersService usersService;
    private List<Users> userList;
    private Intercom intercom;
    private String intercomId;
    private String deptId;
    private String type;
    private String flagDelete;

    /**
     * 预加载新增页面
     * 
     * @return
     */
    public String toSave() throws Exception {
        if (Validate.notNull(intercomId)) {
            intercom = intercomService.packageIntercom(intercomId);
            attachedFileList = attachedFileService.findAttachedFileBySuperId(intercomId);
        }
        userList = usersService.findAllUsers();
        return SUCCESS;
    }

    /**
     * 新增
     * 
     * @return
     * @throws Exception
     */
    public String save() throws Exception {
        UploadHelper uploadHelper = new UploadHelper(upload, uploadFileName, uploadTitle, uploadContentType, getSavePath(), UploadHelper.UID);
        List<AttachedFile> listAttachedFile = uploadHelper.getAttachedFiles();
        intercomService.saveIntercom(intercom, listAttachedFile, intercomId);
        return SUCCESS;
    }

    /**
     * 修改
     * 
     * @return
     * @throws Exception
     */
    public String update() throws Exception {
        intercomService.updateIntercom(intercom);
        return SUCCESS;
    }

    /**
     * 删除
     * 
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {
    	if("flag".equals(flagDelete) && Validate.collectionNotNull(idList)){
    		for(String id: idList){
    			Intercom intercom = intercomService.findById(id);
    			intercom.setReadFlag(IntercomConstant.HasDeleted);
    			intercomService.updateIntercom(intercom);
    		}
    	}else{
    		intercomService.deleteIntercomBatch(idList);
    	}
        return SUCCESS;
    }

    /**
     * 查看
     * 
     * @return
     * @throws Exception
     */
    public String view() throws Exception {
        intercom = intercomService.findById(intercomId);
        attachedFileList = attachedFileService.findAttachedFileBySuperId(intercom.getId());
        return SUCCESS;
    }

    /**
     * 批量将未读更新成已读
     * 
     * @return
     * @throws Exception
     */
    public String updateReadFlag() throws Exception {
        intercomService.updateHasReadedBatch(idList);
        return SUCCESS;
    }

    public String updateAllReadFlag() throws Exception {
        intercomService.updateAllHasReaded();
        return SUCCESS;
    }

    /** 总列表 */
    public String list() throws Exception {
        return SUCCESS;
    }

    /** 树形菜单 */
    public String listTree() throws Exception {
        return SUCCESS;
    }

    /** 分页 */
    public String listPagination() throws Exception {
        // 默认显示未读
        if (!Validate.notNull(type))
            type = "receive";
        page = intercomService.listPagination(page, type, searchType, searchValue);
        return SUCCESS;
    }
    
    public String listViewIntercom() throws Exception {

        return SUCCESS;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public Intercom getIntercom() {
        return intercom;
    }

    public void setIntercom(Intercom intercoms) {
        this.intercom = intercoms;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIntercomId() {
        return intercomId;
    }

    public void setIntercomId(String intercomId) {
        this.intercomId = intercomId;
    }

    public List<Users> getUserList() {
        return userList;
    }

    public void setUserList(List<Users> userList) {
        this.userList = userList;
    }

	public String getFlagDelete() {
		return flagDelete;
	}

	public void setFlagDelete(String flagDelete) {
		this.flagDelete = flagDelete;
	}
    
}