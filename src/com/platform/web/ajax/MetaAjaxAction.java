package com.platform.web.ajax;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.platform.constants.StringConstant;
import com.platform.util.Meta;

@Controller
@Scope("prototype")
public class MetaAjaxAction {

    private String usersId;
    private String webId;
    private String useable;
    private String departmentId;
    private String ownerDeptId;
    private String ownerId;
    /**
     * 查询其他用户的操作权限
     * @return
     * @throws Exception
     */
    public String findOtherUsersOperate() throws Exception {
    	try{
        String userOperate = Meta.getOtherOperate(usersId, webId);
        String ownerOperate = Meta.getOtherOperate(ownerId, webId);
        String deptOperate = Meta.getDepartmentOperate(departmentId, webId);
        String ownerDeptOperate = Meta.getDepartmentOperate(ownerDeptId, webId);
        if(userOperate.equals(StringConstant.TRUE)||deptOperate.equals(StringConstant.TRUE)
        		||ownerOperate.equals(StringConstant.TRUE)||ownerDeptOperate.equals(StringConstant.TRUE))
        	useable = StringConstant.TRUE;
        else 
        	useable = StringConstant.FALSE;
    	
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return Action.SUCCESS;
    }

    public void setUsersId(String usersId) {
        this.usersId = usersId;
    }

    public void setWebId(String webId) {
        this.webId = webId;
    }

    public String getUseable() {
        return useable;
    }

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getOwnerDeptId() {
		return ownerDeptId;
	}

	public void setOwnerDeptId(String ownerDeptId) {
		this.ownerDeptId = ownerDeptId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
    
}