package com.platform.web.ajax;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.platform.constants.StringConstant;
import com.platform.domain.Role;
import com.platform.service.RoleService;
import com.platform.service.RoleUsersService;
import com.platform.service.meta.RoleCacheService;
import com.platform.service.meta.RoleModuleDepartmentsService;
import com.platform.service.meta.RoleModuleFieldService;
import com.platform.service.meta.RoleModuleOperateService;
import com.platform.service.meta.RoleModuleUsersService;
import com.platform.service.meta.ScopeDataVisibleService;
import com.platform.service.meta.ScopeDeptVisibleService;

@Controller
@Scope("prototype")
public class RoleAjaxAction {

    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleModuleOperateService roleModuleOperateService;
    @Autowired
    private RoleUsersService roleUsersService;
    @Autowired
    private RoleModuleUsersService roleModuleUsersService;
    @Autowired
    private RoleModuleFieldService roleModuleFieldService;
    @Autowired
    private RoleModuleDepartmentsService roleModuleDepartmentsService;
    @Autowired
    private ScopeDataVisibleService scopeDataVisibleService;
    @Autowired
    private RoleCacheService roleChangedService;
    @Autowired
    private ScopeDeptVisibleService scopeDeptVisibleService;
    
    private String roleId;
    private String roleName;
    private String roleRemark;

    private List<String> idList;
    private String useable;

    private String moduleId;
    private List<String> webIdList;

    private String webId;
    private String usersId;
    private String visible;
    private String departmentId;
    private String state;

    /**
     * 角色名称唯一性验证
     * @return
     * @throws Exception
     */
    public String validateRoleName() throws Exception {
    	try{
    		if(roleService.nameExist(roleId, roleName)) {
    			state = StringConstant.FALSE;
    		} else {
    			state = StringConstant.TRUE;
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		return Action.SUCCESS;
	}
    
    /**
     * 保存角色
     * @return
     * @throws Exception
     */
    public String saveRole() throws Exception {
    	try{
    		Role role = new Role();
            role.setName(roleName);
            role.setRemark(roleRemark);
            roleService.saveRole(role);
            roleId = role.getId();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return Action.SUCCESS;
    }

    /**
     * 保存角色的操作
     * @return
     * @throws Exception
     */
    public String saveRoleOperate() throws Exception {
    	try{
    		 webIdList = roleModuleOperateService.saveRoleOperate(idList, roleId, moduleId, useable);
    	     roleChangedService.updateRoleCache(roleId);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return Action.SUCCESS;
    }

    /**
     * 保存数据范围用户
     * @return
     * @throws Exception
     */
    public String saveRoleModuleUsers() throws Exception {
    	try{
    		roleModuleUsersService.saveRoleModuleUsers(idList, roleId, moduleId, useable);
            roleChangedService.updateRoleCache(roleId);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return Action.SUCCESS;
    }

    /**
     * 角色与用户关联
     * @return
     * @throws Exception
     */
    public String saveRoleUsers() throws Exception {
    	try{
    		roleUsersService.saveRoleUsers(idList, roleId, useable);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return Action.SUCCESS;
    }

    /**
     * 删除字段权限
     * @return
     * @throws Exception
     */
    public String deleteRoleModuleField() throws Exception {
    	try{
    		webIdList = roleModuleFieldService.deleteRoleModuleField(roleId, moduleId, idList);
            roleChangedService.updateRoleCache(roleId);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return Action.SUCCESS;
    }

    /**
     * 更新部门
     * @return
     * @throws Exception
     */
    public String saveRoleModuleDepartments() throws Exception {
    	try{
    		roleModuleDepartmentsService.saveRoleModuleDepartments(idList, roleId, moduleId);
            roleChangedService.updateRoleCache(roleId);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return Action.SUCCESS;
    }

    /**
     * 更新数据范围的操作权限
     * @return
     * @throws Exception
     */
    public String updateScopeDataVisible() throws Exception {
    	try{
    		idList = scopeDataVisibleService.updateUsersScopeDataVisible(roleId, moduleId, usersId, idList, visible);
            roleChangedService.updateRoleCache(roleId);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return Action.SUCCESS;
    }
    
    /**
     * 更新部门数据范围的操作权限
     * @return
     * @throws Exception
     */
    public String updateScopeDeptVisible() throws Exception {
    	try{
        idList = scopeDeptVisibleService.updateScopeDeptVisible(roleId, moduleId, departmentId, idList, visible);
        roleChangedService.updateRoleCache(roleId);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return Action.SUCCESS;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setRoleRemark(String roleRemark) {
        this.roleRemark = roleRemark;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public void setUseable(String useable) {
        this.useable = useable;
    }

    public List<String> getWebIdList() {
        return webIdList;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public List<String> getIdList() {
        return idList;
    }

    public void setUsersId(String usersId) {
        this.usersId = usersId;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public void setWebId(String webId) {
        this.webId = webId;
    }

    public String getWebId() {
        return webId;
    }

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	
}