package com.platform.vo;

import java.io.Serializable;
import java.util.HashMap;


public class RoleCache implements Serializable{
    
    private static final long serialVersionUID = -6850723316438259171L;
    public static final String CACHE_NAME = "ROLE_CACHE";
    private HashMap<String, String> operate;
    private HashMap<String, String> moduleUsers;
    private HashMap<String, String> moduleDepartments;
    private HashMap<String, String> moduleFields;
    private HashMap<String, HashMap<String, String>> otherOperates;
    private HashMap<String, HashMap<String, String>> departmentOperates;
    
    public HashMap<String, String> getOperate() {
        return operate;
    }
    
    public void setOperate(HashMap<String, String> operate) {
        this.operate = operate;
    }
    
    public HashMap<String, String> getModuleUsers() {
        return moduleUsers;
    }
    
    public void setModuleUsers(HashMap<String, String> moduleUsers) {
        this.moduleUsers = moduleUsers;
    }
    
    public HashMap<String, String> getModuleFields() {
        return moduleFields;
    }
    
    public void setModuleFields(HashMap<String, String> moduleFields) {
        this.moduleFields = moduleFields;
    }
    
    public HashMap<String, HashMap<String, String>> getOtherOperates() {
        return otherOperates;
    }
    
    public void setOtherOperates(HashMap<String, HashMap<String, String>> otherOperates) {
        this.otherOperates = otherOperates;
    }

	public HashMap<String, String> getModuleDepartments() {
		return moduleDepartments;
	}

	public void setModuleDepartments(HashMap<String, String> moduleDepartments) {
		this.moduleDepartments = moduleDepartments;
	}

	public HashMap<String, HashMap<String, String>> getDepartmentOperates() {
		return departmentOperates;
	}

	public void setDepartmentOperates(HashMap<String, HashMap<String, String>> departmentOperates) {
		this.departmentOperates = departmentOperates;
	}
	
    
}