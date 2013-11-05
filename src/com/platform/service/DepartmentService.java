package com.platform.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csms.dao.GroupDAO;
import com.csms.domain.Group;
import com.platform.constants.SQLConstant;
import com.platform.constants.StringConstant;
import com.platform.dao.DepartmentDAO;
import com.platform.dao.UsersDAO;
import com.platform.domain.Department;
import com.platform.exception.CRUDException;
import com.platform.util.LoginBean;
import com.platform.util.Meta;
import com.platform.util.Uuid;
import com.platform.util.Validate;
import com.platform.vo.Page;

@Service
public class DepartmentService implements IService {

    private DepartmentDAO departmentDAO;
    private UsersDAO usersDAO;
    private GroupDAO groupDAO;
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    	Log log = LogFactory.getLog(DepartmentService.class);
    	log.debug(jdbcTemplate);
        departmentDAO = DepartmentDAO.getInstance(jdbcTemplate);
        usersDAO = UsersDAO.getInstance(jdbcTemplate);
        groupDAO = GroupDAO.getInstance(jdbcTemplate);
    }

    /**
     * 分页
     * 
     * @param page
     * @param superId
     * @return
     * @throws CRUDException
     */
    public Page<Department> listPagination(Page<Department> page, String superId) throws CRUDException {
        if (Validate.notNull(superId))
            return departmentDAO.pagination(page, SQLConstant.DEPARTMENT_CHILD_SQL + SQLConstant.getUsersAndDeptWhere(" d.creator "," u.departmentid ",Meta.getUsers(StringConstant.DEPARTMENT_ID),Meta.getDepartments(StringConstant.DEPARTMENT_ID)),new String[]{superId});
        else
            return departmentDAO.pagination(page, SQLConstant.DEPARTMENT_PAGE_SQL + SQLConstant.getUsersAndDeptWhere(" d.creator "," u.departmentid ",Meta.getUsers(StringConstant.DEPARTMENT_ID),Meta.getDepartments(StringConstant.DEPARTMENT_ID)), null);
    }

    /**
     * 根据上级部门获取下级部门
     * 
     * @param superId
     * @return
     * @throws CRUDException
     */
    public List<Department> findDepartmentBySuperId(String superId) throws CRUDException {
        List<Department> list = departmentDAO.findAll(SQLConstant.DEPARTMENT_CHILD_SQL, new String[]{superId});
        return list;
    }

    /**
     * 获取所有部门
     * @return
     * @throws CRUDException
     */
    public List<Department> findAllDepartment(String orderBy) throws CRUDException {
        List<Department> list = departmentDAO.findAll(SQLConstant.DEPARTMENT_TREE_SQL + SQLConstant.getUsersAndDeptWhere(" d.creator "," u.departmentid ",Meta.getUsers(StringConstant.DEPARTMENT_ID),Meta.getDepartments(StringConstant.DEPARTMENT_ID)) +" order by "+orderBy);
        return list;
    }
    
    /**
     * 获取所有部门包括用户
     * @return
     * @throws CRUDException
     */
    public List<Department> findAllDepartmentAndUsers(String orderBy) throws CRUDException {
        List<Department> list = departmentDAO.findAllDepAndUsers(SQLConstant.DEPARTMENT_TREE_SQL+" order by "+orderBy,null);
        return list;
    }
    
    
    /**
     * (overload)获取所有部门,按默认规则(创建时间)排序
     * @return
     * @throws CRUDException
     */
    public List<Department> findAllDepartment() throws CRUDException {
    	return findAllDepartment("d.createTime");
    }
    
    /**
     * (overload)获取所有部门,按默认规则(创建时间)排序
     * @return
     * @throws CRUDException
     */
    public List<Department> findAllDepartmentAndUsers() throws CRUDException {
    	return findAllDepartmentAndUsers("d.createTime");
    }
    
    /**
     * 查找所有库房
     * @return
     * @throws CRUDException
     */
//    public List<Department> findAllWarehouse() throws CRUDException {
//    	return departmentDAO.findByProperty("isStore", StringConstant.TRUE);
//    }
    
    public List<Department> findWarehouseLike(String code) throws CRUDException {
    	return departmentDAO.findAll(SQLConstant.DEPARTMENT_STORE_LIKE_SQL, new String[]{code+"%"});
    }
    
    public List<Department> findDepartmentNotLike(String code) throws CRUDException {
    	return departmentDAO.findAll(SQLConstant.DEPARTMENT_NOT_LIKE_SQL, new String[]{code+"%"});
    }
    
    /**
     * 根据id查询部门
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public Department findById(String id) throws Exception {
        Department department = departmentDAO.find(SQLConstant.DEPARTMENT_SELECTALL_BY_ID,new String[]{id});
        return department;
    }

    /**
     * 新建部门
     * 
     * @param department
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void saveDepartment(Department department,String addGroup) throws CRUDException {
    	if(StringConstant.TRUE.equals(department.getIsStore())) {
    		department.setIsStore(StringConstant.TRUE);
        } else {
        	department.setIsStore(StringConstant.FALSE);
        }
    	//设置编号
    	department.setCode(departmentDAO.findNextCode(department.getSuperId()));
        department.setCreateTime(new Date());
        department.setCreator(LoginBean.getLoginBean().getUser());
        department.setId(Uuid.getUuid());
        departmentDAO.save(department);
        //如果是重客则创建一个默认分组
        if("1".equals(addGroup)){
        	Group group = new Group();
        	group.setName("默认分组");
        	group.setDepartment(department.getId());
        	group.setCreateTime(new Date().getTime());
        	group.setCreator(department.getCreator());
        	group.setType("0");
        	groupDAO.save(group);
        }
    }

    /**
     * 修改部门
     * 
     * @param department
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void updateDepartment(Department department) throws CRUDException {
        Department oldDepartment = departmentDAO.find(SQLConstant.DEPARTMENT_SELECTALL_BY_ID,new String[]{department.getId()});
        oldDepartment.setName(department.getName());
        //判断是否重新设置编号
        if(!department.getSuperId().equals(oldDepartment.getSuperId())) {
        	String oldCode = oldDepartment.getCode();
        	oldDepartment.setCode(departmentDAO.findNextCode(department.getSuperId()));
        	oldDepartment.setSuperId(department.getSuperId());
        	List<Department> childList = departmentDAO.findAll(SQLConstant.DEPARTMENT_CHILD_LIKE, new String[]{oldCode+"%"});
        	for (Department child : childList) {
        		child.setCode(child.getCode().replace(oldCode, oldDepartment.getCode()));
			}
        }
        //判断是否为库房
        if(StringConstant.TRUE.equals(department.getIsStore())) {
        	oldDepartment.setIsStore(StringConstant.TRUE);
        } else {
        	oldDepartment.setIsStore(StringConstant.FALSE);
        }
        oldDepartment.setRemark(department.getRemark());
        oldDepartment.setEditor(LoginBean.getLoginBean().getUser());
        oldDepartment.setEditTime(new Date());
        departmentDAO.update(oldDepartment);
    }

    /**
     * 移动部门到其他部门下
     * 
     * @param departmentId
     * @param newSuperId
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void updateMoveDepartment(String departmentId, String newSuperId) throws CRUDException {
        Department department = departmentDAO.find(SQLConstant.DEPARTMENT_SELECTALL_BY_ID,new String[]{departmentId});
        department.setSuperId(newSuperId);
        departmentDAO.update(department);
    }

    /**
     * 移动某部门下所有员工到其他部门
     * 
     * @param departmentId
     * @param newDepartmentId
     * @throws CRUDException
     */
    public void updateMoveDepartmentUsers(String departmentId, String newDepartmentId) throws CRUDException {
//        Department department = departmentDAO.get(departmentId);
//        Department newDepartment = departmentDAO.get(newDepartmentId);
//        List<Users> userList = usersDAO.findByProperty("department", department);
//        for (Users users : userList) {
//            users.setDepartment(newDepartment);
//            usersDAO.update(users);
//        }
    }

    /**
     * 批量删除部门
     * 
     * @param idList
     * @return
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public List<List<String>> deleteDepartment(List<String> idList) throws CRUDException {
        if (Validate.collectionNotNull(idList)) {
            // 查找可删和不可删的集合
            List<List<String>> returnList = departmentDAO.findCannotDeleteList(idList, SQLConstant.DEPARTMENT_DELETE_LIST);
            // 如果都可以删除则执行删除操作
            if (returnList.get(1).size() == 0) {
                departmentDAO.deleteByIdList(SQLConstant.DEPARTMENT_DELETE_BY_IDS,idList);
                return null;
            }
            // 否则返回可删和不可删的双集合
            else
                return returnList;
        } else
            return null;
    }
    
    /**
     * 同步部门
     * 
     * @param department
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void saveSynDepartment(Department department) throws CRUDException {
        departmentDAO.save(department);
    }
    
}