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
import com.platform.dao.GenericDAO;
import com.platform.domain.RoleModuleField;
import com.platform.domain.RoleModuleOperate;
import com.platform.domain.RoleUsers;
import com.platform.exception.CRUDException;
import com.platform.util.CacheUtil;
import com.platform.util.LoginBean;
import com.platform.util.UUIDGenerator;
import com.platform.util.Validate;
import com.platform.vo.RoleCache;


public class RoleModuleFieldDAO extends GenericDAO{

	private static final long serialVersionUID = 1914379646816013718L;
	private static RoleModuleFieldDAO instance;
	private JdbcTemplate jdbcTemplate;
	
	public static RoleModuleFieldDAO getInstance(JdbcTemplate jdbcTemplate) {
        if(instance == null) {
            instance =  new RoleModuleFieldDAO(jdbcTemplate);
        }
        return instance;
    }
	
    /**
     * 构造函数
     * 
     * @param sessionFactory
     */
    private RoleModuleFieldDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
    }
    
    /**
     * 根据用户角色用户列表查找字段的约束
     * 
     * @param ruList
     * @return
     * @throws CRUDException
     */
    public HashMap<String, String> findUsersFields(List<RoleUsers> ruList, String userId) throws CRUDException {
    	//从缓存中取出现有权限
        HashMap<String, String> fields = new HashMap<String, String>();
        RoleCache roleCache = (RoleCache) CacheUtil.get(RoleCache.CACHE_NAME, LoginBean.getLoginBean().getUser().getId());
        if(roleCache != null) {
        	fields = roleCache.getModuleFields();
        }
        if (Validate.collectionNotNull(ruList)) {
            for (RoleUsers ru : ruList) {
                List<RoleModuleField> rmfList = findAll(SQLConstant.ROLEMODULEFIELD_SELECT_BY_ROLEID_SQL,new String[]{ru.getRole().getId()});
                if (Validate.collectionNotNull(rmfList)) {
                    for (RoleModuleField field : rmfList) {
                        fields.put(field.getWebId(), field.getRules());
                    }
                }
            }
        }
        return fields;
    }
    
    /**
     * 
     * @param sql
     * @param args
     * @return
     */
    public RoleModuleField find(String sql,Object[] args){
    	List<RoleModuleField> list = findAll(sql,args);
    	return Validate.collectionNotNull(list) ? list.get(0) : null;
    }
    
	/**
	 * 根据sql和查询条件查询所有
	 * @param sql
	 * @param args
	 * @return
	 */
	public List<RoleModuleField> findAll(String sql , Object[] args){
		return jdbcTemplate.query(sql, args, new RowMapper<RoleModuleField>(){
			@Override
			public RoleModuleField mapRow(ResultSet rs, int rowNum) throws SQLException {
				RoleModuleField rmf = new RoleModuleField();
				
				return rmf;
			}
		});
	}
	
	public List<RoleModuleField> findAll(String sql){
		return findAll(sql,null);
	}
	
	/**
	 * 保存
	 * @param roleModuleField
	 * @return
	 */
	public int save(RoleModuleField roleModuleField){
		return jdbcTemplate.update(SQLConstant.ROLEMODULEFIELD_SAVE_SQL, new Object[]{
				UUIDGenerator.generate(),
				roleModuleField.getRole().getId(),
				roleModuleField.getModule().getId(),
				roleModuleField.getWebId(),
				roleModuleField.getRules()
		});
	}
	
	/**
	 * 更新
	 * @param roleModuleField
	 * @return
	 */
	public int update(RoleModuleField roleModuleField){
		return jdbcTemplate.update(SQLConstant.ROLEMODULEFIELD_UPDATE_SQL, new Object[]{
				UUIDGenerator.generate(),
				roleModuleField.getRole().getId(),
				roleModuleField.getModule().getId(),
				roleModuleField.getWebId(),
				roleModuleField.getRules()
		});
	}
	
	/**
	 * 删除集合
	 * @param collection
	 * @return
	 */
	public int deleteAll(Collection<RoleModuleField> collection){
		int deleteCount = 0;
		if(!Validate.collectionNotNull(collection)) return deleteCount;
		
		for(RoleModuleField roleModuleField : collection){
			if(roleModuleField!=null&&roleModuleField.getId()!=null)
			deleteCount += deleteByProperty(SQLConstant.ROLEMODULEFIELD_DELETE_BY_ID_SQL,roleModuleField.getId());
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
		return jdbcTemplate.update(SQLConstant.ROLEMODULEFIELD_DELETE_BY_ROLEID, roleId);
	}
}