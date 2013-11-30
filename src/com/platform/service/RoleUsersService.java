package com.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.constants.SQLConstant;
import com.platform.constants.StringConstant;
import com.platform.dao.meta.RoleUsersDAO;
import com.platform.domain.Role;
import com.platform.domain.RoleUsers;
import com.platform.domain.Users;
import com.platform.exception.CRUDException;
import com.platform.service.meta.RoleCacheService;
import com.platform.service.meta.RoleModuleDepartmentsService;
import com.platform.service.meta.RoleModuleUsersService;
import com.platform.util.Validate;

@Service
public class RoleUsersService implements IService {

    private RoleUsersDAO roleUsersDAO;
    @Autowired
    private RoleCacheService roleChangedService;
    @Autowired
    private RoleModuleUsersService roleModuleUsersService;
    @Autowired
    private RoleModuleDepartmentsService roleModuleDepartmentsService;
    @Autowired
    private UsersService usersService;
    
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        roleUsersDAO = RoleUsersDAO.getInstance(jdbcTemplate);
    }
    
    public List<RoleUsers> findByRoelUsrsId(List<Role> roleList, String id) throws CRUDException {
    	
    	return roleUsersDAO.findAll(SQLConstant.ROLEUSERS_SELECT_BY_ROLE_USERS_SQL,new String[]{roleList.get(0).getId(),roleList.get(1).getId(),roleList.get(2).getId(),roleList.get(3).getId(),id});
    }

    /**
     * 根据角色ID查找用户权限
     * 
     * @param roleId
     * @return
     * @throws CRUDException
     */
    public List<RoleUsers> findRoleUsersByRole(String roleId) throws CRUDException {
        return roleUsersDAO.findAll(SQLConstant.ROLEUSERS_SELECT_BY_ROLE_SQL, new String[]{roleId});
    }

    /**
     * 根据用户ID和角色ID查找用户角色的关联记录
     * 
     * @param usersId
     * @param roleId
     * @return
     * @throws CRUDException
     */
    public RoleUsers findUsersRoleByUsersAndRole(String usersId, String roleId) throws CRUDException {
        return roleUsersDAO.find(SQLConstant.ROLEUSERS_BY_USERS_ROLE_SQL, new String[]{usersId, roleId});
    }

    /**
     * 保存用户角色
     * 
     * @param idList
     * @param usersId
     * @param state
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void saveUsersRole(List<String> idList, String usersId, String state) throws CRUDException {
        if (Validate.collectionNotNull(idList))
            // 保存
            if (StringConstant.TRUE.equals(state)) {
                for (String roleId : idList) {
                    // 当用户角色未添加时,添加用户角色
                    if (!Validate.idNotNull(findUsersRoleByUsersAndRole(usersId, roleId))) {
                    	roleUsersDAO.save(roleId, usersId);
                    }
                }
                //刷新用户角色
                roleChangedService.flushRoleCache(usersId);
            }
            // 删除
            else {
                for (String roleId : idList) {
                    // 当用户角色已添加时,删除用户角色
                    RoleUsers ru = findUsersRoleByUsersAndRole(usersId, roleId);
                    if (Validate.idNotNull(ru)) {
                        roleUsersDAO.deleteByProperty(SQLConstant.ROLEUSERS_DELETE_BY_ID,ru.getId());
                    }
                }
                // 刷新用户角色
                roleChangedService.flushRoleCache(usersId);
            }
    }

    /**
     * 角色关联用户
     * 
     * @param idListimport java.util.Date;

     * @param roleId
     * @param state
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void saveRoleUsers(List<String> idList, String roleId, String state) throws CRUDException {
        if (Validate.collectionNotNull(idList)) {
            // 添加用户
            if (StringConstant.TRUE.equals(state)) {
                for (String usersId : idList) {
                    // 当用户角色未添加时,添加用户角色
                    if (!Validate.idNotNull(findUsersRoleByUsersAndRole(usersId, roleId))) {
                        Role r = new Role();
                        r.setId(roleId);

                        Users u = new Users();
                        u.setId(usersId);

                        RoleUsers ru = new RoleUsers();
                        ru.setRole(r);
                        ru.setUsers(u);

                        roleUsersDAO.save(ru);

                        // 将用户ID放入角色变更集合中
                        roleChangedService.flushRoleCache(usersId);
                    }
                }
            }
            // 删除用户
            else {
                for (String usersId : idList) {
                    // 当用户角色已添加时,删除用户角色
                    RoleUsers ru = findUsersRoleByUsersAndRole(usersId, roleId);
                    if (Validate.idNotNull(ru)) {
                    	 roleUsersDAO.deleteByProperty(SQLConstant.ROLEUSERS_DELETE_BY_ID,ru.getId());

                        // 将用户ID放入角色变更集合中
                        roleChangedService.flushRoleCache(usersId);
                    }
                }
            }
        }
    }
    
    
    /**
     * 角色关联用户
     * 
     * @param idListimport java.util.Date;

     * @param roleId
     * @param state
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void saveRoleUsers(String usersId, String roleId, String state) throws CRUDException {
        if (Validate.notNull(usersId)) {
    		roleUsersDAO.deleteByProperty(SQLConstant.ROLEUSERS_DELETE_BY_USERSID,usersId);
            if (!Validate.idNotNull(findUsersRoleByUsersAndRole(usersId, roleId))) {
                Role r = new Role();
                r.setId(roleId);

                Users u = new Users();
                u.setId(usersId);

                RoleUsers ru = new RoleUsers();
                ru.setRole(r);
                ru.setUsers(u);

                roleUsersDAO.save(ru);
                // 将用户ID放入角色变更集合中
                roleChangedService.flushRoleCache(usersId);
                }
            }
       }
    
    /**
     * 根据用户ID查找用户权限
     * 
     * @param usersId
     * @return
     * @throws CRUDException
     */
    public List<RoleUsers> findUsersRoleByUsers(String usersId) throws CRUDException {
        return roleUsersDAO.findUsersRoleByUsers(usersId);
    }
    /**
     * 根据记录所属人,模块id,判断该角色有无权限访问
     * @param roleId
     * @param userId
     * @param objectType
     * @return
     * @throws CRUDException 
     */
    public boolean findIsPower(String roleId,String userId,String objectType) throws CRUDException{ 
    	Users user = usersService.findById(userId);
    	if(roleModuleUsersService.findIsPower(roleId,userId,
    			com.platform.constants.OAStringConstant.MODULE_TABLE_MAP.get(objectType))
    			||roleModuleDepartmentsService.findIsPower(roleId, user.getDepartment().getId(), 
    	    			com.platform.constants.OAStringConstant.MODULE_TABLE_MAP.get(objectType)))
    		return true;
    	return false;
    }
    
}
