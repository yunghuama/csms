package com.platform.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.platform.domain.Department;
import com.platform.exception.CRUDException;
import com.platform.service.DepartmentService;

@Controller
@Scope("prototype")
public class DepartmentAction extends GenericAction<Department> {

    private static final long serialVersionUID = -4867867482994497216L;

    @Autowired
    private DepartmentService departmentService;
    private List<Department> departmentList;
    private Department department;
    private String deptId;
    private String inputId;
    private String contentId;
    private String addGroup;
    /**
     * 部门树
     * 
     * @return
     * @throws Exception
     */
    public String listTree() throws Exception {
    	try{
    		departmentList = departmentService.findAllDepartment();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    public String listPagination() throws Exception {
    	try{
    	    page = departmentService.listPagination(page, deptId);	
    	}catch(Exception e){
    		e.printStackTrace();
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
    	try{
            departmentList = departmentService.findAllDepartment();	
    	}catch(Exception e){
    		e.printStackTrace();
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
    		departmentService.saveDepartment(department,addGroup);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    /**
     * 预修改
     * 
     * @return
     * @throws Exception
     */
    public String toUpdate() throws Exception {
    	try{
    		 department = departmentService.findById(deptId);
    	     departmentList = departmentService.findDepartmentNotLike(department.getCode());
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    /**
     * 修改
     * 
     * @return
     * @throws Exception
     */
    public String update() throws Exception {
    	try{
    		departmentService.updateDepartment(department);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }

    /**
     * 删除
     * 
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {
    	try{
    		 departmentService.deleteDepartment(idList);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return SUCCESS;
    }
    
    public String selectDepartment() throws CRUDException{
    	try{
    		departmentList = departmentService.findAllDepartment();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        
        return SUCCESS;
    }
    
    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getInputId() {
        return inputId;
    }

    public void setInputId(String inputId) {
        this.inputId = inputId;
    }
    
    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

	public String getAddGroup() {
		return addGroup;
	}

	public void setAddGroup(String addGroup) {
		this.addGroup = addGroup;
	}
    
    
}