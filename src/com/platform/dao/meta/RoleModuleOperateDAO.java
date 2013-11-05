package com.platform.dao.meta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.platform.constants.SQLConstant;
import com.platform.constants.StringConstant;
import com.platform.dao.GenericDAO;
import com.platform.domain.Role;
import com.platform.domain.RoleModuleField;
import com.platform.domain.RoleModuleOperate;
import com.platform.domain.RoleUsers;
import com.platform.domain.VmetaModule;
import com.platform.exception.CRUDException;
import com.platform.util.CacheUtil;
import com.platform.util.UUIDGenerator;
import com.platform.util.Validate;
import com.platform.vo.RoleCache;


public class RoleModuleOperateDAO extends GenericDAO{

	private static final long serialVersionUID = 9202447484806054849L;
	private static RoleModuleOperateDAO instance;
	private JdbcTemplate jdbcTemplate;
	
	public static RoleModuleOperateDAO getInstance(JdbcTemplate jdbcTemplate) {
        if(instance == null) {
            instance =  new RoleModuleOperateDAO(jdbcTemplate);
        }
        return instance;
    }
	
    /**
     * 构造函数
     * 
     * @param sessionFactory
     */
    private RoleModuleOperateDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
    }
    
    /**
     * 查找用户的所有操作组件权限
     * 
     * @param ruList
     * @return
     * @throws CRUDException
     */
    public HashMap<String, String> findUsersOperates(List<RoleUsers> ruList, String userId) throws CRUDException {
    	//从缓存中取出现有的权限
    	HashMap<String, String> operates = new HashMap<String, String>();
    	RoleCache roleCache = (RoleCache) CacheUtil.get(RoleCache.CACHE_NAME, userId);
    	if(roleCache != null) {
    		operates = roleCache.getOperate();
    	}
        if (Validate.collectionNotNull(ruList)) {
            for (RoleUsers ru : ruList) {
                List<RoleModuleOperate> roList = findAll(SQLConstant.ROLEOPERATE_SELECT_BY_ROLEID_SQL, new String[]{ru.getRole().getId()});
                if (Validate.collectionNotNull(roList)) {
                    for (RoleModuleOperate operate : roList) {
                        String useable = operates.get(operate.getWebId());
                        if (Validate.notNull(useable)) {
                            if (StringConstant.FALSE.equals(useable))
                                operates.put(operate.getWebId(), operate.getUseable());
                        } else {
                            operates.put(operate.getWebId(), operate.getUseable());
                        }
                    }
                }
            }
        }
        return operates;
    }

	/**
	 * 根据sql和查询条件查询所有
	 * @param sql
	 * @param args
	 * @return
	 */
	public List<RoleModuleOperate> findAll(String sql , Object[] args){
		return jdbcTemplate.query(sql, args, new RowMapper<RoleModuleOperate>(){
			@Override
			public RoleModuleOperate mapRow(ResultSet rs, int rowNum) throws SQLException {
				RoleModuleOperate rmo = new RoleModuleOperate();
				rmo.setId(rs.getString("id"));
				Role r = new Role();
				r.setId(rs.getString("roleid"));
				VmetaModule m = new VmetaModule();
				m.setId(rs.getString("moduleId"));
				rmo.setRole(r);
				rmo.setModule(m);
				rmo.setUseable(rs.getString("useable"));
				rmo.setWebId(rs.getString("webId"));
				return rmo;
			}
		});
	}
	
	public List<RoleModuleOperate> findAll(String sql){
		return findAll(sql,null);
	}
	
	/**
	 * 保存
	 * @param roleModuleOperate
	 * @return
	 */
	public int save(RoleModuleOperate roleModuleOperate){
		return jdbcTemplate.update(SQLConstant.ROLEOPERATE_SAVE_SQL, new Object[]{
			UUIDGenerator.generate(),
			roleModuleOperate.getWebId(),
			roleModuleOperate.getUseable(),
			roleModuleOperate.getRole().getId(),
			roleModuleOperate.getModule().getId()
		});
	}
	
	/**
	 * 更新
	 * @param roleModuleOperate
	 * @return
	 */
	public int update(RoleModuleOperate roleModuleOperate){
		return jdbcTemplate.update(SQLConstant.ROLEOPERATE_UPDATE_SQL, new Object[]{
			roleModuleOperate.getWebId(),
			roleModuleOperate.getUseable(),
			roleModuleOperate.getRole().getId(),
			roleModuleOperate.getModule().getId(),
			roleModuleOperate.getId()
		});
	}
	
	/**
	 * 删除集合
	 * @param collection
	 * @return
	 */
	public int deleteAll(Collection<RoleModuleOperate> collection){
		int deleteCount = 0;
		if(!Validate.collectionNotNull(collection)) return deleteCount;
		
		for(RoleModuleOperate roleModuleOperate : collection){
			if(roleModuleOperate!=null&&roleModuleOperate.getId()!=null)
			deleteCount += deleteByProperty(SQLConstant.ROLEOPERATE_DELETE_BY_ID_SQL,roleModuleOperate.getId());
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
		return jdbcTemplate.update(SQLConstant.ROLEOPERATE_DELETE_BY_ROLEID, roleId);
	}
}
