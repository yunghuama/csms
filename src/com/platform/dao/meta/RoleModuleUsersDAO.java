package com.platform.dao.meta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.platform.constants.SQLConstant;
import com.platform.dao.GenericDAO;
import com.platform.domain.Role;
import com.platform.domain.RoleModuleUsers;
import com.platform.domain.RoleUsers;
import com.platform.domain.VmetaModule;
import com.platform.exception.CRUDException;
import com.platform.util.CacheUtil;
import com.platform.util.UUIDGenerator;
import com.platform.util.Validate;
import com.platform.vo.RoleCache;


public class RoleModuleUsersDAO extends GenericDAO {

	private static final long serialVersionUID = 3406989631364286984L;
	private static RoleModuleUsersDAO instance;
	private JdbcTemplate jdbcTemplate;
	
	public static RoleModuleUsersDAO getInstance(JdbcTemplate jdbcTemplate) {
        if(instance == null) {
            instance =  new RoleModuleUsersDAO(jdbcTemplate);
        }
        return instance;
    }
	
    /**
     * 构造函数
     * 
     * @param sessionFactory
     */
    private RoleModuleUsersDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
    }
    
    /**
     * session用到的角色模块用户集合
     * 
     * @param ruList
     * @return
     * @throws CRUDException
     */
    public HashMap<String, String> findRoleModuleUsers(List<RoleUsers> ruList, String userId) throws CRUDException {
    	//从缓存中取出现有权限
        HashMap<String, String> moduleUsers = new HashMap<String, String>();
        RoleCache roleCache = (RoleCache) CacheUtil.get(RoleCache.CACHE_NAME, userId);
        if(roleCache != null) {
        	moduleUsers = roleCache.getModuleUsers();
        }
        if (Validate.collectionNotNull(ruList)) {
            for (RoleUsers ru : ruList) {
                for (Iterator<RoleModuleUsers> iter = findAll(SQLConstant.ROLEMODULEUSERS_SELECT_BY_ROLEID_SQL,new String[]{ru.getRole().getId()}).iterator(); iter.hasNext();) {
                    RoleModuleUsers rmu = iter.next();
                    String sessionUsers = moduleUsers.get(rmu.getModule().getId());
                    StringBuffer userIds = new StringBuffer();
                    if (Validate.notNull(sessionUsers))
                        userIds.append(sessionUsers);
                    String[] nextUsersIds = rmu.getUsers().split(",");
                    for (int i = 0; i < nextUsersIds.length; i++) {
                        if (Validate.notNull(nextUsersIds[i])) {
                            if (userIds.indexOf(nextUsersIds[i]) < 0)
                                userIds.append(nextUsersIds[i] + ",");
                        }
                    }
                    moduleUsers.put(rmu.getModule().getId(), userIds.toString());
                }
            }
        }
        return moduleUsers;
    }

	/**
	 * 查询所有
	 * @return
	 */
	public List<RoleModuleUsers> findAll(String sql,Object[] args){
		 return jdbcTemplate.query(sql, args, new RowMapper<RoleModuleUsers>(){
			@Override
			public RoleModuleUsers mapRow(ResultSet rs, int rowNum) throws SQLException {
				RoleModuleUsers rmu = new RoleModuleUsers();
				rmu.setId(rs.getString("id"));
				Role r = new Role();
				r.setId(rs.getString("roleid"));
				VmetaModule m = new VmetaModule();
				m.setId(rs.getString("moduleid"));
				rmu.setRole(r);
				rmu.setModule(m);
				rmu.setUsers(rs.getString("users"));
				return rmu;
			}
		});
	}
	
	/**
	 * 
	 * @param roleModuleUsers
	 * @return
	 */
	public int save(RoleModuleUsers roleModuleUsers){
		return jdbcTemplate.update(SQLConstant.ROLEMODULEUSERS_SAVE_SQL,new Object[]{
				UUIDGenerator.generate(),
				roleModuleUsers.getRole().getId(),
				roleModuleUsers.getModule().getId(),
				roleModuleUsers.getUsers()
		});
	}
	
	public int update(RoleModuleUsers roleModuleUsers){
		return jdbcTemplate.update(SQLConstant.ROLEMODULEUSERS_UPDATE_SQL, new Object[]{
				roleModuleUsers.getRole().getId(),
				roleModuleUsers.getModule().getId(),
				roleModuleUsers.getUsers(),
				roleModuleUsers.getId()
		});
	}
	
	
	/**
	 * 删除集合
	 * @param collection
	 * @return
	 */
	public int deleteAll(Collection<RoleModuleUsers> collection){
		int deleteCount = 0;
		if(!Validate.collectionNotNull(collection)) return deleteCount;
		
		for(RoleModuleUsers roleModuleUsers : collection){
			if(roleModuleUsers!=null&&roleModuleUsers.getId()!=null)
			deleteCount += deleteByProperty(SQLConstant.ROLEMODULEUSERS_DELETE_BY_ID,roleModuleUsers.getId());
		}
		return deleteCount;
	}
	
	/**
	 * 根据角色删除
	 * @param role
	 * @return
	 */
	public int deleteByRole(String roleId){
		if(!Validate.notNull(roleId))
			return 0;
		return jdbcTemplate.update(SQLConstant.ROLEMODULEUSERS_DELETE_BY_ROLEID, roleId);
	}
}
