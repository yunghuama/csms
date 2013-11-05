package com.platform.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 角色表
 * 
 * @author mayh
 */
public class Role extends BaseDomain {

	private static final long serialVersionUID = -1818969330595062292L;
    private String name;
    private String remark;
    private String superId;
    private Set<RoleModuleOperate> roleModuleOperates = new HashSet<RoleModuleOperate>(0);
    private Set<RoleModuleField> roleModuleFields = new HashSet<RoleModuleField>(0);
    private Set<RoleModuleUsers> roleModuleUsers = new HashSet<RoleModuleUsers>(0);
    private Set<RoleModuleDepartments> roleModuleDepartments = new HashSet<RoleModuleDepartments>(0);
    private Set<RoleUsers> roleUsers = new HashSet<RoleUsers>(0);
    private Set<ScopeDataVisible> scopeDataVisibles = new HashSet<ScopeDataVisible>(0);
    
    
    
	public String getSuperId() {
		return superId;
	}
	public void setSuperId(String superId) {
		this.superId = superId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Set<RoleModuleOperate> getRoleModuleOperates() {
		return roleModuleOperates;
	}
	public void setRoleModuleOperates(Set<RoleModuleOperate> roleModuleOperates) {
		this.roleModuleOperates = roleModuleOperates;
	}
	public Set<RoleModuleField> getRoleModuleFields() {
		return roleModuleFields;
	}
	public void setRoleModuleFields(Set<RoleModuleField> roleModuleFields) {
		this.roleModuleFields = roleModuleFields;
	}
	public Set<RoleModuleUsers> getRoleModuleUsers() {
		return roleModuleUsers;
	}
	public void setRoleModuleUsers(Set<RoleModuleUsers> roleModuleUsers) {
		this.roleModuleUsers = roleModuleUsers;
	}
	public Set<RoleModuleDepartments> getRoleModuleDepartments() {
		return roleModuleDepartments;
	}
	public void setRoleModuleDepartments(
			Set<RoleModuleDepartments> roleModuleDepartments) {
		this.roleModuleDepartments = roleModuleDepartments;
	}
	public Set<RoleUsers> getRoleUsers() {
		return roleUsers;
	}
	public void setRoleUsers(Set<RoleUsers> roleUsers) {
		this.roleUsers = roleUsers;
	}
	public Set<ScopeDataVisible> getScopeDataVisibles() {
		return scopeDataVisibles;
	}
	public void setScopeDataVisibles(Set<ScopeDataVisible> scopeDataVisibles) {
		this.scopeDataVisibles = scopeDataVisibles;
	}

}