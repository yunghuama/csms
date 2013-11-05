package com.platform.domain;

import java.io.Serializable;

/**
 * 可以查看角色的模块中哪些用户记录的表
 * 
 * @author Marker
 */
public class RoleModuleUsers implements Serializable {

    private static final long serialVersionUID = -2606702155940952579L;
    private String id;
    private Role role;
    private VmetaModule module;
    private String users;

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

    public String getUsers() {
        return this.users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public VmetaModule getModule() {
        return module;
    }

    public void setModule(VmetaModule module) {
        this.module = module;
    }

}