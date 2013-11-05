package com.platform.domain;

import java.io.Serializable;

/**
 * 涉及其他用户记录是否可操作表
 * 
 * @author Marker
 */
public class ScopeDataVisible implements Serializable {

    private static final long serialVersionUID = 5012776068823176196L;
    private String id;
    private Role role; // 所属角色
    private VmetaModule module; // 所属模块
    private Users users;
    private String webId; // 前台组件ID
    private String visible; // 是否可见

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

    public String getWebId() {
        return this.webId;
    }

    public void setWebId(String webId) {
        this.webId = webId;
    }

    public String getVisible() {
        return this.visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public VmetaModule getModule() {
        return module;
    }

    public void setModule(VmetaModule module) {
        this.module = module;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

}