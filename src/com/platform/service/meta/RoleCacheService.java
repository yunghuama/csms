package com.platform.service.meta;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.constants.SQLConstant;
import com.platform.dao.meta.RoleModuleDepartmentsDAO;
import com.platform.dao.meta.RoleModuleFieldDAO;
import com.platform.dao.meta.RoleModuleOperateDAO;
import com.platform.dao.meta.RoleModuleUsersDAO;
import com.platform.dao.meta.RoleUsersDAO;
import com.platform.dao.meta.ScopeDataVisibleDAO;
import com.platform.dao.meta.ScopeDeptVisibleDAO;
import com.platform.domain.RoleUsers;
import com.platform.exception.CRUDException;
import com.platform.service.IService;
import com.platform.util.CacheUtil;
import com.platform.util.Validate;
import com.platform.vo.RoleCache;

/**
 * 权限缓存服务类
 * 
 * @author MarkerKing
 *
 */
@Service
public class RoleCacheService implements IService {

    private RoleUsersDAO roleUsersDAO;
    private RoleModuleOperateDAO roleModuleOperateDAO;
    private RoleModuleFieldDAO roleModuleFieldDAO;
    private RoleModuleUsersDAO roleModuleUsersDAO;
    private RoleModuleDepartmentsDAO roleModuleDepartmentsDAO;
    private ScopeDataVisibleDAO scopeDataVisibleDAO;
    private ScopeDeptVisibleDAO scopeDeptVisibleDAO;
    
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        roleUsersDAO = RoleUsersDAO.getInstance(jdbcTemplate);
        roleModuleOperateDAO = RoleModuleOperateDAO.getInstance(jdbcTemplate);
        roleModuleFieldDAO = RoleModuleFieldDAO.getInstance(jdbcTemplate);
        roleModuleUsersDAO = RoleModuleUsersDAO.getInstance(jdbcTemplate);
        roleModuleDepartmentsDAO = RoleModuleDepartmentsDAO.getInstance(jdbcTemplate);
        scopeDataVisibleDAO = ScopeDataVisibleDAO.getInstance(jdbcTemplate);
        scopeDeptVisibleDAO = ScopeDeptVisibleDAO.getInstance(jdbcTemplate);
    }
    
    /**
     * 刷新用户权限缓存
     * 
     * @param userId 用户ID
     * @throws CRUDException
     */
    public void flushRoleCache(String userId) throws CRUDException {
        List<RoleUsers> ruList = roleUsersDAO.findUsersRoleByUsers(userId);
        //清空缓存
        CacheUtil.put(RoleCache.CACHE_NAME, userId, null);
        RoleCache roleCache = new RoleCache();
        //设置该角色有哪些模块的(基础)操作权限 vmeta_role_module_operate(webId,useable,roleId,moduleId);
        roleCache.setOperate(roleModuleOperateDAO.findUsersOperates(ruList, userId));
        //设置该角色能看到这个模块下面哪些用户的数据 vmeta_role_module_users(roleId,moduleId,users);
        roleCache.setModuleUsers(roleModuleUsersDAO.findRoleModuleUsers(ruList, userId));
        //设置该角色能看到这个模块下面哪些部门的数据 vmeta_role_module_departments(roleid,moduleid,departments);
        roleCache.setModuleDepartments(roleModuleDepartmentsDAO.findRoleModuleDepartments(ruList, userId));
        //角色的模块中字段的约束
        roleCache.setModuleFields(roleModuleFieldDAO.findUsersFields(ruList, userId));
        //设置该角色对其他用户用户数据的操作权限 vmeta_role_module_scopedatavisible(roleid,moduleid,usersid,webid,visible);
        roleCache.setOtherOperates(scopeDataVisibleDAO.findOtherUsersOperates(ruList, userId));
        //设置该角色对其他部门用户数据的操作权限 vmeta_role_module_scopedeptvisible(roleid,moduleid,departmentid,webid,visible);
        roleCache.setDepartmentOperates(scopeDeptVisibleDAO.findDepartmentOperates(ruList, userId));
        CacheUtil.put(RoleCache.CACHE_NAME, userId, roleCache);
    }

    /**
     * 刷新用户权限缓存
     * 
     * @param roleId 角色ID
     * @throws CRUDException
     */
    public void updateRoleCache(String roleId) throws CRUDException {
    	List<RoleUsers> list = roleUsersDAO.findAll(SQLConstant.ROLEUSERS_SELECT_BY_ROLE_SQL,new String[]{roleId});
    	if (Validate.collectionNotNull(list)) {
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                RoleUsers ru = (RoleUsers) iter.next();
                flushRoleCache(ru.getUsers().getId());
            }
        }
    }
    
    /**
     * 获得用户权限缓存
     * 
     * @param usersId
     * @return
     */
    public RoleCache getUsersRoleCache(String usersId) {
        return (RoleCache) CacheUtil.get(RoleCache.CACHE_NAME, usersId);
    }

}