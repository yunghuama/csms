package com.platform.domain;

import java.io.Serializable;

/**
 * 角色的模块中字段的约束
 * 
 * @author Marker
 */
public class RoleModuleField implements Serializable {

    private static final long serialVersionUID = -5088719482335985828L;

    private String id;
    private Role role;
    private VmetaModule module;
    private String webId; // 前台组件ID
    private String rules; // 约束规则

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

    public String getRules() {
        return this.rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public VmetaModule getModule() {
        return module;
    }

    public void setModule(VmetaModule module) {
        this.module = module;
    }

}