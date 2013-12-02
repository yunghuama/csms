package com.platform.web.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.csms.domain.District;
import com.csms.domain.Enterprise;
import com.csms.service.DistrictService;
import com.csms.service.EnterpriseService;
import com.platform.domain.Department;
import com.platform.domain.Role;
import com.platform.domain.RoleUsers;
import com.platform.domain.Users;
import com.platform.service.DepartmentService;
import com.platform.service.RoleService;
import com.platform.service.RoleUsersService;
import com.platform.service.UsersService;
import com.platform.util.FileHelper;
import com.platform.util.LoginBean;
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
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private DistrictService districtService;
    

    private List<Department> departmentList;
    private List<Enterprise> enterpriseList;
    private List<District> districtList;
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
    	Users users = LoginBean.getLoginBean().getUser();
    	roleList = new ArrayList<Role>();
    	if("402881c92ca0977f012ca09978a30001".equals(users.getRole().getId())){
    		Role role1 = new Role();
    		role1.setId("ff8080813c55b78c013c55cc91690005");
    		role1.setName("省级管理员");
    		Role role2 = new Role();
    		role2.setId("ff8080813c56e38d013c56e38d290000");
    		role2.setName("审核管理员");
    		Role role3 = new Role();
    		role3.setId("ff8080813c55b78c013c55df0e67003f");
    		role3.setName("市级管理员");
    		Role role4 = new Role();
    		role4.setId("ff8080813c55b78c013c55df64c50040");
    		role4.setName("区级管理员");
    		Role role5 = new Role();
    		role5.setId("ff8080813c55b78c013c55e0649d0042");
    		role5.setName("企业管理员");
    		roleList.add(role1);
    		roleList.add(role2);
    		roleList.add(role3);
    		roleList.add(role4);
    		roleList.add(role5);
    	}
    	if("ff8080813c55b78c013c55cc91690005".equals(users.getRole().getId())){
    		Role role3 = new Role();
    		role3.setId("ff8080813c55b78c013c55df0e67003f");
    		role3.setName("市级管理员");
    		roleList.add(role3);
    	}
    	if("ff8080813c55b78c013c55df0e67003f".equals(users.getRole().getId())){
    		Role role4 = new Role();
    		role4.setId("ff8080813c55b78c013c55df64c50040");
    		role4.setName("区级管理员");
    		roleList.add(role4);
    	}
        try{
        	//如果是省级管理员
        	if("ff8080813c55b78c013c55cc91690005".equals(users.getRole().getId())){
        		if(searchValue.size()==0){
	        		searchValue.add(0, "ff8080813c55b78c013c55df0e67003f");
	        		searchValue.add(1,"");
	        		searchType = "and";
        		}
        	}
        	//如果是市级管理员
        	if("ff8080813c55b78c013c55df0e67003f".equals(users.getRole().getId())){
        		if(searchValue.size()==0){
	        		searchValue.add(0, "ff8080813c55b78c013c55df64c50040");
	        		searchValue.add(1,"");
	        		searchType = "and";
        		}
        	}
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
    	Users users = LoginBean.getLoginBean().getUser();
    	//如果是管理员
    	if("402881c92ca0977f012ca09978a30001".equals(users.getRole().getId())){
    		districtList = districtService.findAll();
    	}
    	//如果是省级管理员
    	if("ff8080813c55b78c013c55cc91690005".equals(users.getRole().getId())){
    		districtList = districtService.findByType(District.DISTRICTTYPE.CITY.toString());
    	}
    	//如果是市级管理员
    	if("ff8080813c55b78c013c55df0e67003f".equals(users.getRole().getId())){
    		districtList = districtService.findByType(District.DISTRICTTYPE.AREA.toString());
    	}
        return SUCCESS;
    }

    /**
     * 预新建
     * 
     * @return
     * @throws Exception
     */
    public String toSave() throws Exception {
    	Users users = LoginBean.getLoginBean().getUser();
    	roleList = new ArrayList<Role>();
    	if("402881c92ca0977f012ca09978a30001".equals(users.getRole().getId())){
    		Role role1 = new Role();
    		role1.setId("ff8080813c55b78c013c55cc91690005");
    		role1.setName("省级管理员");
    		Role role2 = new Role();
    		role2.setId("ff8080813c56e38d013c56e38d290000");
    		role2.setName("审核管理员");
    		roleList.add(role1);
    		roleList.add(role2);
    		districtList = districtService.findByType(District.DISTRICTTYPE.PROVINCE.toString());
    	}
    	//获得区域
    	//如果是省级管理员
    	if("ff8080813c55b78c013c55cc91690005".equals(users.getRole().getId())){
    		Role role1 = new Role();
    		role1.setId("ff8080813c55b78c013c55df0e67003f");
    		role1.setName("市级管理员");
    		roleList.add(role1);
    		districtList = districtService.findByType(District.DISTRICTTYPE.CITY.toString());
    	}
    	//如果是市级管理员
    	if("ff8080813c55b78c013c55df0e67003f".equals(users.getRole().getId())){
    		Role role1 = new Role();
    		role1.setId("ff8080813c55b78c013c55df64c50040");
    		role1.setName("区级管理员");
    		roleList.add(role1);
    		districtList = districtService.findByType(District.DISTRICTTYPE.AREA.toString());
    	}
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
    	Users users = LoginBean.getLoginBean().getUser();
    	//如果是省管理员
    	if("ff8080813c55b78c013c55cc91690005".equals(users.getRole().getId())){
    		user.setRoleId("ff8080813c55b78c013c55df0e67003f");
    	}
    	//如果是市级管理员
    	if("ff8080813c55b78c013c55df0e67003f".equals(users.getRole().getId())){
    		user.setRoleId("ff8080813c55b78c013c55df64c50040");
    	}
        usersService.saveUsers(user, imagePath, targetPath);
        roleUsersService.saveRoleUsers(user.getId(), user.getRoleId(), "T");
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
    	Users users = LoginBean.getLoginBean().getUser();
    	roleList = new ArrayList<Role>();
    	if("402881c92ca0977f012ca09978a30001".equals(users.getRole().getId())){
    		Role role1 = new Role();
    		role1.setId("ff8080813c55b78c013c55cc91690005");
    		role1.setName("省级管理员");
    		Role role2 = new Role();
    		role2.setId("ff8080813c56e38d013c56e38d290000");
    		role2.setName("审核管理员");
    		roleList.add(role1);
    		roleList.add(role2);
    		districtList = districtService.findAll();
    	}
    	//获得区域
    	//如果是省级管理员
    	if("ff8080813c55b78c013c55cc91690005".equals(users.getRole().getId())){
    		Role role1 = new Role();
    		role1.setId("ff8080813c55b78c013c55df0e67003f");
    		role1.setName("市级管理员");
    		roleList.add(role1);
    		districtList = districtService.findByType(District.DISTRICTTYPE.CITY.toString());
    	}
    	//如果是市级管理员
    	if("ff8080813c55b78c013c55df0e67003f".equals(users.getRole().getId())){
    		Role role1 = new Role();
    		role1.setId("ff8080813c55b78c013c55df64c50040");
    		role1.setName("区级管理员");
    		roleList.add(role1);
    		districtList = districtService.findByType(District.DISTRICTTYPE.AREA.toString());
    	}
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
    	Users users = LoginBean.getLoginBean().getUser();
    	//如果是省管理员
    	if("ff8080813c55b78c013c55cc91690005".equals(users.getRole().getId())){
    		user.setRoleId("ff8080813c55b78c013c55df0e67003f");
    	}
    	//如果是市级管理员
    	if("ff8080813c55b78c013c55df0e67003f".equals(users.getRole().getId())){
    		user.setRoleId("ff8080813c55b78c013c55df64c50040");
    	}
        usersService.updateUsers(user, imagePath, null);
        roleUsersService.saveRoleUsers(user.getId(), user.getRoleId(), "T");
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

	public List<Enterprise> getEnterpriseList() {
		return enterpriseList;
	}

	public void setEnterpriseList(List<Enterprise> enterpriseList) {
		this.enterpriseList = enterpriseList;
	}

	public List<District> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<District> districtList) {
		this.districtList = districtList;
	}
	
}