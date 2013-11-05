package com.platform.service.meta;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platform.constants.SQLConstant;
import com.platform.constants.StringConstant;
import com.platform.dao.meta.RoleDAO;
import com.platform.dao.meta.RoleModuleUsersDAO;
import com.platform.dao.meta.ScopeDataVisibleDAO;
import com.platform.domain.Role;
import com.platform.domain.RoleModuleUsers;
import com.platform.domain.ScopeDataVisible;
import com.platform.domain.VmetaModule;
import com.platform.exception.CRUDException;
import com.platform.service.IService;
import com.platform.util.LoginBean;
import com.platform.util.Validate;

@Service
public class RoleModuleUsersService implements IService {

    private RoleModuleUsersDAO roleModuleUsersDAO;
    private RoleDAO roleDAO;
    private ScopeDataVisibleDAO scopeDataVisibleDAO;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        roleModuleUsersDAO = RoleModuleUsersDAO.getInstance(jdbcTemplate);
        roleDAO = RoleDAO.getInstance(jdbcTemplate);
        scopeDataVisibleDAO = ScopeDataVisibleDAO.getInstance(jdbcTemplate);
    }

    /**
     * 根据角色和模块查找角色模块用户
     * 
     * @param roleId
     * @param moduleId
     * @return
     * @throws CRUDException
     */
    public List<String> findRoleModuleUsers(String roleId, String moduleId) throws CRUDException {
        List<String> userList = new ArrayList<String>();
        List<RoleModuleUsers> rmuList = roleModuleUsersDAO.findAll(SQLConstant.ROLEMODULEUSERS_SELECT_BY_ROLE_MODULE_SQL, new String[]{roleId, moduleId});
        if (Validate.collectionNotNull(rmuList)) {
            for (RoleModuleUsers users : rmuList) {
                String userString = users.getUsers();
                String[] split = userString.split(",");
                for (String user : split) {
                    userList.add(user);
                }
            }
        }
        return userList;
    }
    public boolean findIsPower(String roleId,String userId,String moduleId) throws CRUDException{
    	
    	List<RoleModuleUsers> rmuList = roleModuleUsersDAO.findAll(SQLConstant.ROLEMODULEUSERS_SELECT_BY_ROLE_MODULE_SQL, new String[]{roleId, moduleId});
    	for(int i=0;i<rmuList.size();i++){
    		if(rmuList.get(i).getUsers().indexOf(userId)!=-1)
    			return true;
    	}
    	return false;
    }

    /**
     * 保存角色模块用户
     * 
     * @param idList
     * @param roleId
     * @param moduleId
     * @param useable
     * @throws CRUDException
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public void saveRoleModuleUsers(List<String> idList, String roleId, String moduleId, String useable) throws CRUDException {
        if (Validate.collectionNotNull(idList)) {
        	//如果是取消数据范围，先删除范围中的操作权限记录
            if(StringConstant.FALSE.equals(useable)) {
            	for (String usersId : idList) {
            		List<String> ids = scopeDataVisibleDAO.findAllIds(SQLConstant.SCOPEDATAVISIBLE_SELECT_ID_BY_ROLE_MODULE_USERS_SQL, new String[]{roleId, moduleId, usersId});
            		if(Validate.collectionNotNull(ids))
            		scopeDataVisibleDAO.deleteByIdList(SQLConstant.SCOPEDATAVISIBLE_DELETE_BY_IDS_SQL, ids);
				}
            }
        	
        	//查找唯一数据，因为findUnique会报错，因此使用find再进行判断
            List<RoleModuleUsers> rmuList = roleModuleUsersDAO.findAll(SQLConstant.ROLEMODULEUSERS_SELECT_BY_ROLE_MODULE_SQL,new String[]{roleId, moduleId});
            if (Validate.collectionNotNull(rmuList)) {
                RoleModuleUsers rmu = rmuList.get(0);
                String users = getUsersString(idList, rmu.getUsers(), useable);
                //如果没有或者全部取消了，就不保存了
                if(Validate.notNull(users)) {
                	rmu.setUsers(users);
                    roleModuleUsersDAO.update(rmu);
                } else {
                	roleModuleUsersDAO.deleteByProperty(SQLConstant.ROLEMODULEUSERS_DELETE_BY_ID,rmu.getId());
                }
            } else {
            	String users = getUsersString(idList, "", useable);
            	//如果没有或者全部取消了，就不保存了
            	if(Validate.notNull(users)) {
            		Role r = new Role();
                    r.setId(roleId);

                    VmetaModule m = new VmetaModule();
                    m.setId(moduleId);

                    RoleModuleUsers rmu = new RoleModuleUsers();
                    rmu.setRole(r);
                    rmu.setModule(m);
                    rmu.setUsers(users);
                    roleModuleUsersDAO.save(rmu);
            	}
            }

            Role oldRole = roleDAO.find(SQLConstant.ROLE_SELECT_BY_ID,new String[]{roleId});
            oldRole.setEditor(LoginBean.getLoginBean().getUser());
            oldRole.setEditTime(new Date());
            roleDAO.update(oldRole);
        }
    }

    /**
     * 根据权限获得相关用户
     * 返回格式为[ID+","]组合
     * 
     * @param idList
     * @param usersList
     * @param useable
     * @return
     */
    private String getUsersString(List<String> idList, String usersList, String useable) {
        if (!Validate.notNull(usersList) && StringConstant.FALSE.equals(useable))
            return "";
        else {
            for (String id : idList) {
                if (StringConstant.TRUE.equals(useable)) {
                    if (usersList.indexOf(id) < 0)
                        usersList = usersList + id + ",";
                } else {
                    usersList = usersList.replaceAll(id + ",", "");
                }
            }
            return usersList;
        }
    }
}
