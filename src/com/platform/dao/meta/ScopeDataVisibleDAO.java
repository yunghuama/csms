package com.platform.dao.meta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.platform.constants.SQLConstant;
import com.platform.constants.StringConstant;
import com.platform.dao.GenericDAO;
import com.platform.domain.Role;
import com.platform.domain.RoleUsers;
import com.platform.domain.ScopeDataVisible;
import com.platform.domain.Users;
import com.platform.domain.VmetaModule;
import com.platform.exception.CRUDException;
import com.platform.util.CacheUtil;
import com.platform.util.UUIDGenerator;
import com.platform.util.Validate;
import com.platform.vo.RoleCache;


public class ScopeDataVisibleDAO extends GenericDAO {

	private static final long serialVersionUID = 6128176141458285091L;
	private static ScopeDataVisibleDAO instance;
	private JdbcTemplate jdbcTemplate;
	
	public static ScopeDataVisibleDAO getInstance(JdbcTemplate jdbcTemplate) {
        if(instance == null) {
            instance =  new ScopeDataVisibleDAO(jdbcTemplate);
        }
        return instance;
    }
	
    /**
     * 构造函数
     * 
     * @param sessionFactory
     */
    private ScopeDataVisibleDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
    }
    
    /**
     * 查找其他用户的可操作权限
     * 
     * @param ruList
     * @return
     * @throws CRUDException
     */
    public HashMap<String, HashMap<String, String>> findOtherUsersOperates(List<RoleUsers> ruList, String userId) throws CRUDException {
    	//从缓存中取出现有权限
        HashMap<String, HashMap<String, String>> operates = new HashMap<String, HashMap<String,String>>();
        RoleCache roleCache = (RoleCache) CacheUtil.get(RoleCache.CACHE_NAME, userId);
        if(roleCache != null) {
        	operates = roleCache.getOtherOperates();
        }
        if (Validate.collectionNotNull(ruList)) {
            for (RoleUsers ru : ruList) {
                List<ScopeDataVisible> sdvList = this.findAll(SQLConstant.SCOPEDATAVISIBLE_SELECT_BY_ROLE_SQL, new String[]{ru.getRole().getId()});
                if (Validate.collectionNotNull(sdvList)) {
                    for (ScopeDataVisible sdv : sdvList) {
                        HashMap<String, String> usersOperates = operates.get(sdv.getUsers().getId());
                        if (usersOperates == null) {
                            usersOperates = new HashMap<String, String>();
                            usersOperates.put(sdv.getWebId(), sdv.getVisible());
                        } else {
                        	String usable = usersOperates.get(sdv.getWebId());
                            if (Validate.notNull(usable)) {
                                if (StringConstant.FALSE.equals(usable))
                                    usersOperates.put(sdv.getWebId(), sdv.getVisible());
                            } else {
                                usersOperates.put(sdv.getWebId(), sdv.getVisible());
                            }
                        }
                        operates.put(sdv.getUsers().getId(), usersOperates);
                    }
                }
            }
        }
        return operates;
    }

    /**
	 * 查询所有
	 * @return
	 */
	public List<ScopeDataVisible> findAll(String sql,Object[] args){
		 return jdbcTemplate.query(sql, args, new RowMapper<ScopeDataVisible>(){
			@Override
			public ScopeDataVisible mapRow(ResultSet rs, int rowNum) throws SQLException {
				ScopeDataVisible sdv = new ScopeDataVisible();
				Role r = new Role();
				r.setId(rs.getString("roleId"));
				VmetaModule m = new VmetaModule();
				m.setId(rs.getString("moduleid"));
				Users u = new  Users();
				u.setId(rs.getString("usersid"));
				sdv.setId(rs.getString("id"));
				sdv.setModule(m);
				sdv.setRole(r);
				sdv.setUsers(u);
				sdv.setVisible(rs.getString("visible"));
				sdv.setWebId(rs.getString("webid"));
				return sdv;
			}
		});
	}
	
	public List<String> findAllIds(String sql,Object[] args){
		return jdbcTemplate.query(sql, args, new RowMapper<String>(){
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("id");
			}
		});
	}
	
	/**
	 * 保存
	 * @param scopeDataVisible
	 * @return
	 */
	public int save(ScopeDataVisible scopeDataVisible){
	 return	jdbcTemplate.update(SQLConstant.SCOPEDATAVISIBLE_SAVE_SQL, new Object[]{
				UUIDGenerator.generate(),
				scopeDataVisible.getRole().getId(),
				scopeDataVisible.getModule().getId(),
				scopeDataVisible.getUsers().getId(),
				scopeDataVisible.getWebId(),
				scopeDataVisible.getVisible()
		});
	}
	
	/**
	 * 更新
	 * @param scopeDataVisible
	 * @return
	 */
	public int update(ScopeDataVisible scopeDataVisible){
	 return	jdbcTemplate.update(SQLConstant.SCOPEDATAVISIBLE_UPDATE_SQL, new Object[]{
				scopeDataVisible.getRole().getId(),
				scopeDataVisible.getModule().getId(),
				scopeDataVisible.getUsers().getId(),
				scopeDataVisible.getWebId(),
				scopeDataVisible.getVisible(),
				scopeDataVisible.getId()
		});
	}
	
	/**
	 * 删除集合
	 * @param collection
	 * @return
	 */
	public int deleteAll(Collection<ScopeDataVisible> collection){
		int deleteCount = 0;
		if(!Validate.collectionNotNull(collection)) return deleteCount;
		
		for(ScopeDataVisible scopeDataVisible : collection){
			if(scopeDataVisible!=null&&scopeDataVisible.getId()!=null)
			deleteCount += deleteByProperty(SQLConstant.SCOPEDATAVISIBLE_DELETE_BY_ID_SQL,scopeDataVisible.getId());
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
		return jdbcTemplate.update(SQLConstant.SCOPEDATAVISIBLE_DELETE_BY_ROLEID, roleId);
	}
}