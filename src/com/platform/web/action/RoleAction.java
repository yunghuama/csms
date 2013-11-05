package com.platform.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.platform.domain.Department;
import com.platform.domain.Role;
import com.platform.domain.RoleModuleDepartments;
import com.platform.domain.RoleModuleField;
import com.platform.domain.RoleModuleOperate;
import com.platform.domain.RoleUsers;
import com.platform.domain.ScopeDataVisible;
import com.platform.domain.ScopeDeptVisible;
import com.platform.domain.Users;
import com.platform.domain.VmetaField;
import com.platform.domain.VmetaModule;
import com.platform.domain.VmetaOperate;
import com.platform.service.DepartmentService;
import com.platform.service.RoleService;
import com.platform.service.RoleUsersService;
import com.platform.service.meta.FieldService;
import com.platform.service.meta.ModuleService;
import com.platform.service.meta.OperateService;
import com.platform.service.meta.RoleModuleDepartmentsService;
import com.platform.service.meta.RoleModuleFieldService;
import com.platform.service.meta.RoleModuleOperateService;
import com.platform.service.meta.RoleModuleUsersService;
import com.platform.service.meta.ScopeDataVisibleService;
import com.platform.service.meta.ScopeDeptVisibleService;
import com.platform.util.Validate;

@Controller
@Scope("prototype")
public class RoleAction extends GenericAction<Role> {

    private static final long serialVersionUID = -4351376812588576760L;

    @Autowired
    private RoleService roleService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private OperateService operateService;
    @Autowired
    private FieldService fieldService;
    @Autowired
    private RoleModuleOperateService roleModuleOperateService;
    @Autowired
    private RoleModuleFieldService roleModuleFieldService;
    @Autowired
    private RoleModuleUsersService roleModuleUsersService;
    @Autowired
    private RoleModuleDepartmentsService roleModuleDepartmentsService;
    @Autowired
    private ScopeDataVisibleService scopeDataVisibleService;
    @Autowired
    private ScopeDeptVisibleService scopeDeptVisibleService;
    @Autowired
    private RoleUsersService roleUsersService;
    @Autowired
    private DepartmentService departmentService;

    private List<Role> roleList;
    private List<RoleUsers> roleUsersList;
    private List<VmetaModule> moduleList;
    private List<VmetaOperate> operateList;
    private List<VmetaField> fieldList;
    private List<RoleModuleOperate> roleOperateList;
    private List<RoleModuleField> roleFieldList;
    private List<ScopeDataVisible> scopeDataVisibleList;
    private List<ScopeDeptVisible> scopeDeptVisibleList;
    private List<Users> usersList;
    private List<String> roleModuleUsersList;
    private List<Department> departmentList;
    private RoleModuleDepartments roleModuleDepartments;
    private Role role;
    private VmetaModule module;
    private VmetaField field;
    private RoleModuleField roleModuleField;
    private String moduleId;
    private String roleId;
    private String usersId;
    private String departmentId;

    private List<String> typeList;
    private List<String> valueList;
    private List<String> messageList;
    private String departmentListJson;
    private String canNull;

    public String list(){
    	
    	return SUCCESS;
    }
    
    public String listTree(){
    	try{
    		roleList = roleService.findAllRole();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return SUCCESS;
    }
    
    /** 分页 */
    public String listPagination() throws Exception {
    	try{
    		page = roleService.listPagination(page,roleId);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    /** 预加载保存 */
    public String toSave() throws Exception {
    	roleList = roleService.findAllRole();
        return SUCCESS;
    }

    /** 保存 */
    public String save() throws Exception {
    	try{
    		roleService.saveRole(role);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        
        return SUCCESS;
    }

    /** 预加载修改 */
    public String toUpdate() throws Exception {
    	try{
    		roleList = roleService.findAllRole();
    		role = roleService.findRoleById(role.getId());
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    /** 修改 */
    public String update() throws Exception {
    	try{
    		roleService.update(role);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    /** 预加载权限设置 */
    public String toSaveUFO() throws Exception {
    	try{
    		role = roleService.findRoleById(roleId);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    /** 加载模块树 */
    public String listModuleTree() throws Exception {
    	try{
    		moduleList = moduleService.findAllModule();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    /** 预加载UFO设置 */
    public String listRoleUFO() throws Exception {
        if (Validate.notNull(moduleId)) {
        	module = moduleService.findModuleById(moduleId);
            return SUCCESS;
        }
        else
            return "help";
    }

    /** 加载模块操作 */
    public String listModuleOperate() throws Exception {
    	try{
		 // 加载该模块的所有操作
         operateList = operateService.listOperateByModuleId(moduleId);
         // 加载该角色的模块的所有已保存操作
         roleOperateList = roleModuleOperateService.listRoleOperateByModuleAndRole(moduleId, roleId);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    /** 加载模块字段 */
    public String listModuleField() throws Exception {
    	try{
    		 // 加载该模块所有字段
            fieldList = fieldService.listFieldByModuleId(moduleId);
            // 加载该角色的模块已设置的字段
            roleFieldList = roleModuleFieldService.listRoleModuleFieldByModuleAndRole(moduleId, roleId);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    /** 加载关联用户 */
    public String listRoleUsers() throws Exception {
    	try{
    		 departmentList = departmentService.findAllDepartmentAndUsers();
    	     roleUsersList = roleUsersService.findRoleUsersByRole(roleId);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }
    
    /** 加载数据范围关联的用户 */
    public String listRoleDataUsers() throws Exception {
    	try{
    		departmentList = departmentService.findAllDepartmentAndUsers();
        	roleModuleUsersList = roleModuleUsersService.findRoleModuleUsers(roleId, moduleId);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return SUCCESS;
    }
    
    //-----------------------------------------MARK
    /** 加载数据范围关联的部门 */
    public String listRoleDataDepartments() throws Exception {
    	try{
    		departmentList = departmentService.findAllDepartment("code");
        	roleModuleDepartments = roleModuleDepartmentsService.findRoleModuleDepartments(roleId, moduleId);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return SUCCESS;
    }

    /** 预加载设置约束 */
    public String toUpdateRoleModuleField() throws Exception {
    	try{
		 field = fieldService.findFieldById(field.getId());
	        roleModuleField = roleModuleFieldService.findRoleModuleFieldByRoleAndWebId(roleId, field.getWebId());
	        // 前台需要空值的时候必须为[]
	        if (!Validate.idNotNull(roleModuleField)) {
	            roleModuleField = new RoleModuleField();
	            roleModuleField.setRules("[]");
	        }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    /** 更新约束 */
    public String updateRoleModuleField() throws Exception {
    	try{
		    roleModuleFieldService.updateRoleModuleField(roleId, field, canNull, typeList, valueList, messageList);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    /** 预加载数据范围权限 */
    public String toUpdateScopeDataVisible() throws Exception {
    	try{
            operateList = operateService.listOperateByModuleIdAndVisible(moduleId);
            scopeDataVisibleList = scopeDataVisibleService.findRoleModuleUsersScopeDataVisible(roleId, moduleId, usersId);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }
    /** 预加载部门数据范围权限 */
    public String toUpdateScopeDeptVisible() throws Exception {
    	try{
    		 operateList = operateService.listOperateByModuleIdAndVisible(moduleId);
    	     scopeDeptVisibleList = scopeDeptVisibleService.findRoleModuleDepartmentVisible(roleId, moduleId, departmentId);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    public String delete() throws Exception {
    	try{
    		roleService.delete(idList);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<VmetaModule> getModuleList() {
        return moduleList;
    }

    public List<VmetaOperate> getOperateList() {
        return operateList;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public List<RoleModuleOperate> getRoleOperateList() {
        return roleOperateList;
    }

    public void setRoleOperateList(List<RoleModuleOperate> roleOperateList) {
        this.roleOperateList = roleOperateList;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<Users> getUsersList() {
        return usersList;
    }

    public List<String> getRoleModuleUsersList() {
        return roleModuleUsersList;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public List<RoleUsers> getRoleUsersList() {
        return roleUsersList;
    }

    public String getUsersId() {
        return usersId;
    }

    public void setUsersId(String usersId) {
        this.usersId = usersId;
    }

    public List<VmetaField> getFieldList() {
        return fieldList;
    }

    public List<RoleModuleField> getRoleFieldList() {
        return roleFieldList;
    }

    public VmetaField getField() {
        return field;
    }

    public void setField(VmetaField field) {
        this.field = field;
    }

    public RoleModuleField getRoleModuleField() {
        return roleModuleField;
    }

    public void setRoleModuleField(RoleModuleField roleModuleField) {
        this.roleModuleField = roleModuleField;
    }

    public String getCanNull() {
        return canNull;
    }

    public void setCanNull(String canNull) {
        this.canNull = canNull;
    }

    public void setTypeList(List<String> typeList) {
        this.typeList = typeList;
    }

    public void setValueList(List<String> valueList) {
        this.valueList = valueList;
    }

    public void setMessageList(List<String> messageList) {
        this.messageList = messageList;
    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public List<ScopeDataVisible> getScopeDataVisibleList() {
        return scopeDataVisibleList;
    }

	public VmetaModule getModule() {
		return module;
	}

	public String getDepartmentListJson() {
		return departmentListJson;
	}
	public void setDepartmentListJson(String departmentListJson) {
		this.departmentListJson = departmentListJson;
	}

	public RoleModuleDepartments getRoleModuleDepartments() {
		return roleModuleDepartments;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public List<ScopeDeptVisible> getScopeDeptVisibleList() {
		return scopeDeptVisibleList;
	}

	public void setScopeDeptVisibleList(List<ScopeDeptVisible> scopeDeptVisibleList) {
		this.scopeDeptVisibleList = scopeDeptVisibleList;
	}
	
	
}