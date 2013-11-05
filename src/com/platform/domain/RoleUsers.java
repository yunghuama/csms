package com.platform.domain;

import java.io.Serializable;

/**
 * 角色用户中间表
 * 
 * @author Marker
 */
public class RoleUsers implements Serializable {

    private static final long serialVersionUID = 3723777217273529624L;
    private String id;
    private Users users;
    private Role role;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Users getUsers() {
        return this.users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}