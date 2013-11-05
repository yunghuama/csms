package com.platform.web.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.platform.domain.Department;
import com.platform.domain.Role;
import com.platform.domain.RoleUsers;
import com.platform.domain.Users;
import com.platform.service.DepartmentService;
import com.platform.service.RoleService;
import com.platform.service.RoleUsersService;
import com.platform.service.UsersService;
import com.platform.util.FileHelper;
import com.platform.util.UploadHelper;
import com.platform.util.Validate;

@Controller
@Scope("prototype")
public class UsersAction extends GenericAction<Users> {

    private static final long serialVersionUID = -4380027258095334424L;

    @Autowired
    private UsersService usersService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleUsersService roleUsersService;

    private List<Department> departmentList;
    private Users user;
    private String imagePath;
    private String userId;
    private String deptId;

    private List<Role> roleList;
    private List<RoleUsers> usersRoleList;
    
    private UploadHelper uploadHelper;

    /**
     * 分页查询
     * 
     * @return
     * @throws Exception
     */
    public String listPagination() throws Exception {
        try{
        if(Validate.notNull(azparam))
            page = usersService.paginationByPY(page, "realName", azparam);
        else
            page = usersService.listPagination(page, searchType, searchValue, deptId);
        }catch(Exception e){
        	e.printStackTrace();
        }
        return SUCCESS;
    }

    // /** 
    // * 结果集分页示例
    // *
    // * @return
    // * @throws Exception
    // */
    // public String listAll() throws Exception {
    // JSPPageHelper.init(usersService.findAllUsers());
    // return SUCCESS;
    // }

    /**
     * 部门树
     * 
     * @return
     * @throws Exception
     */
    public String listTree() throws Exception {
        departmentList = departmentService.findAllDepartment();
        return SUCCESS;
    }

    /**
     * 预新建
     * 
     * @return
     * @throws Exception
     */
    public String toSave() throws Exception {
        // 加载部门列表
        departmentList = departmentService.findAllDepartment();
        return SUCCESS;
    }
    
    /**
     * 新建
     * 
     * @return
     * @throws Exception
     */
    public String save() throws Exception {
    	try{
    	
    	String targetPath = null;
    	if(Validate.notNull(imagePath)) {
    		imagePath = ServletActionContext.getServletContext().getRealPath(imagePath);
    		targetPath = ServletActionContext.getServletContext().getRealPath(FileHelper.SEPARATOR + Users.PIC_FOLDER);
    	}
        usersService.saveUsers(user, imagePath, targetPath);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    /**
     * 浏览
     * 
     * @return
     * @throws Exception
     */
    public String view() throws Exception {
        user = usersService.findById(userId);
        return SUCCESS;
    }

    /**
     * 预修改
     * 
     * @return
     * @throws Exception
     */
    public String toUpdate() throws Exception {
        // 加载部门列表
        departmentList = departmentService.findAllDepartment();
        user = usersService.findById(userId);
        return SUCCESS;
    }

    /**
     * 修改
     * 
     * @return
     * @throws Exception
     */
    public String update() throws Exception {
    	String targetPath = null;
    	if(Validate.notNull(imagePath)) {
    		imagePath = ServletActionContext.getServletContext().getRealPath(imagePath);
    		targetPath = ServletActionContext.getServletContext().getRealPath(FileHelper.SEPARATOR + Users.PIC_FOLDER);
    	}
        usersService.updateUsers(user, imagePath, targetPath);
        return SUCCESS;
    }

    /**
     * 删除
     * 
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {
        usersService.deleteUsers(idList);
        return SUCCESS;
    }

    /**
     * 用户角色关联
     * 
     * @return
     * @throws Exception
     */
    public String listUsersRole() throws Exception {
    	try{
    		roleList = roleService.findUsersWhereRole();
            usersRoleList = roleUsersService.findUsersRoleByUsers(userId);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }
    
    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public List<RoleUsers> getUsersRoleList() {
        return usersRoleList;
    }

	public UploadHelper getUploadHelper() {
		return uploadHelper;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}