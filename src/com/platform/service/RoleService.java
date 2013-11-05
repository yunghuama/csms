package com.platform.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.constants.SQLConstant;
import com.platform.constants.StringConstant;
import com.platform.dao.meta.RoleDAO;
import com.platform.dao.meta.RoleModuleDepartmentsDAO;
import com.platform.dao.meta.RoleModuleFieldDAO;
import com.platform.dao.meta.RoleModuleOperateDAO;
import com.platform.dao.meta.RoleModuleUsersDAO;
import com.platform.dao.meta.RoleUsersDAO;
import com.platform.dao.meta.ScopeDataVisibleDAO;
import com.platform.domain.Role;
import com.platform.domain.RoleUsers;
import com.platform.exception.CRUDException;
import com.platform.util.LoginBean;
import com.platform.util.Meta;
import com.platform.util.Validate;
import com.platform.vo.Page;

@Service
public class RoleService implements IService {

    private RoleDAO roleDAO;
    private RoleModuleOperateDAO roleOperateDAO;
    private RoleModuleFieldDAO roleModulFieldDAO;
    private RoleModuleUsersDAO roleModuleUsersDAO;
    private RoleModuleDepartmentsDAO roleModuleDepartmentsDAO;
    private RoleUsersDAO roleUsersDAO;
    private ScopeDataVisibleDAO scopeDataVisibleDAO;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		roleDAO = RoleDAO.getInstance(jdbcTemplate);
		roleOperateDAO = RoleModuleOperateDAO.getInstance(jdbcTemplate);
		roleModulFieldDAO = RoleModuleFieldDAO.getInstance(jdbcTemplate);
		roleModuleUsersDAO = RoleModuleUsersDAO.getInstance(jdbcTemplate);
		roleModuleDepartmentsDAO = RoleModuleDepartmentsDAO.getInstance(jdbcTemplate);
		roleUsersDAO = RoleUsersDAO.getInstance(jdbcTemplate);
		scopeDataVisibleDAO = ScopeDataVisibleDAO.getInstance(jdbcTemplate);
	}

    public List<Role> findByName()throws CRUDException{
    	return roleDAO.findAll(SQLConstant.ROLE_Name);
    }
    
    public List<Role> findName(String name)throws CRUDException{
    	
    	return roleDAO.findAll(SQLConstant.ROLE_VALIDATE_NAME,new String[]{name});
    }
  
    /**
     * 分页角色列表
     * 
     * @param page
     * @return
     * @throws CRUDException
     */
    public Page<Role> listPagination(Page<Role> page,String roleId) throws CRUDException {
        return roleDAO.pagination(
        		page, 
        		SQLConstant.ROLE_SELECT_BY_PAGE_SQL + SQLConstant.getUsersAndDeptWhere("r.creator","u.departmentid",Meta.getUsers(StringConstant.ROLE_ID),Meta.getDepartments(StringConstant.ROLE_ID)),
        		new String[]{roleId});
    }

    /**
     * 保存角色
     * 
     * @param role
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void saveRole(Role role) throws CRUDException {
        role.setCreateTime(new Date());
        role.setCreator(LoginBean.getLoginBean().getUser());
        roleDAO.save(role);
    }

    /**
     * 查找所有角色
     * 
     * @return
     * @throws CRUDException
     */
    public List<Role> findAllRole() throws CRUDException {
        return roleDAO.findAll(SQLConstant.ROLE_SELECT_ALL_SQL);
    }

    /**
     * 查找权限范围内的角色记录
     * 
     * @return
     * @throws CRUDException
     */
    public List<Role> findUsersWhereRole() throws CRUDException {
    	List<RoleUsers> ruList = roleUsersDAO.findUsersRoleByUsers(LoginBean.getLoginBean().getUser().getId());
        return roleDAO.findAll(SQLConstant.ROLE_SELECT_ALL_SQL + SQLConstant.getUsersAndSuperid("creator","superid",Meta.getUsers(StringConstant.ROLE_ID),Meta.getRoleIds(ruList)));
    }

    /**
     * 获得角色
     * 
     * @param id
     * @return
     * @throws CRUDException
     */
    public Role findRoleById(String id) throws CRUDException {
        return roleDAO.find(SQLConstant.ROLE_SELECT_BY_ID,new String[]{id});
    }

    /**
     * 修改角色
     * 
     * @param role
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void update(Role role) throws CRUDException {
        Role oldRole = roleDAO.find(SQLConstant.ROLE_SELECT_BY_ID,new String[]{role.getId()});
        oldRole.setName(role.getName());
        oldRole.setSuperId(role.getSuperId());
        oldRole.setRemark(role.getRemark());
        oldRole.setEditor(LoginBean.getLoginBean().getUser());
        oldRole.setEditTime(new Date());
        roleDAO.update(oldRole);
    }

    /**
     * 删除角色
     * 
     * @param idList
     * @throws CRUDException
     */
    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void delete(List<String> idList) throws CRUDException {
        if (Validate.collectionNotNull(idList)) {
            for (String id : idList) {
                Role r = roleDAO.find(SQLConstant.ROLE_SELECT_BY_ID,new String[]{id});
                if(r==null)
                	return;
                // 删除相关记录
                roleOperateDAO.deleteByRole(id);
                roleModulFieldDAO.deleteByRole(id);
                roleModuleUsersDAO.deleteByRole(id);
                roleUsersDAO.deleteByRole(id);
                scopeDataVisibleDAO.deleteByRole(id);
                roleModuleDepartmentsDAO.deleteByRole(id);
                roleDAO.deleteByProperty(SQLConstant.ROLE_DELETE_BY_ID, id);
            }
        }
    }
    
    /**
     * 判断同类下是否有相同的名称
     * 
     * @param name
     * @param superId
     * @return
     * @throws CRUDException
     */
    public boolean nameExist(String roletId, String name) throws CRUDException {
    	if(Validate.notNull(roletId)) {
    		return roleDAO.queryForInt(SQLConstant.ROLE_UPDATE_VALIDATE_NAME_SQL, new String[]{roletId, name}) > 0;
    	} else {
    		return roleDAO.queryForInt(SQLConstant.ROLE_VALIDATE_NAME_SQL, new String[]{name}) > 0;
    	}
    }

}