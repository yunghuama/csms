package com.platform.domain;

import java.io.Serializable;

 /**
 * <p>程序名称：       ScopeDeptVisible.java</p>
 * <p>程序说明：       TODO</p>
 * <p>版权信息：       Copyright 深圳市维远泰克科技有限公司</p>
 * <p>时间：          Jun 3, 2011</p>	
 * 
 * @author：          YaoDW
 * @version：         Ver 0.1
 */

public class ScopeDeptVisible implements Serializable {
	
	private static final long serialVersionUID = -6589750617325989577L;
	private String id;
	private Role role; // 所属角色
	private VmetaModule module; // 所属模块
	private Department department;
	private String webId; // 前台组件ID
	private String visible; // 是否可操作
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public VmetaModule getModule() {
		return module;
	}
	public void setModule(VmetaModule module) {
		this.module = module;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public String getWebId() {
		return webId;
	}
	public void setWebId(String webId) {
		this.webId = webId;
	}
	public String getVisible() {
		return visible;
	}
	public void setVisible(String visible) {
		this.visible = visible;
	}
	
	
	    
}
