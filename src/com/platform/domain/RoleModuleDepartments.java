package com.platform.domain;

import java.io.Serializable;

/**
 * 角色的模块中有哪些部门数据权限的表
 * 
 * @author Marker
 */
public class RoleModuleDepartments implements Serializable {

    private static final long serialVersionUID = 5131513129177294491L;
    private String id;
    private Role role;
    private VmetaModule module;
    private String departments;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getDepartments() {
        return this.departments;
    }

    public void setDepartments(String departments) {
        this.departments = departments;
    }

    public VmetaModule getModule() {
        return module;
    }

    public void setModule(VmetaModule module) {
        this.module = module;
    }

}