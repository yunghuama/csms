package com.platform.domain;

import java.io.Serializable;

/**
 * 角色的模块可操作表
 * 
 * @author Marker
 */
public class RoleModuleOperate implements Serializable {

    private static final long serialVersionUID = 6332890550413631401L;
    private String id;
    private Role role;
    private VmetaModule module;
    private String webId; // 前台组件ID
    private String useable; // 是否可用

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

    public VmetaModule getModule() {
        return module;
    }

    public void setModule(VmetaModule module) {
        this.module = module;
    }

    public String getUseable() {
        return useable;
    }

    public void setUseable(String useable) {
        this.useable = useable;
    }

}