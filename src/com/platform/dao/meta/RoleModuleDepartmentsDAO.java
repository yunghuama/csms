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
import com.platform.domain.RoleModuleDepartments;
import com.platform.domain.RoleUsers;
import com.platform.domain.ScopeDataVisible;
import com.platform.domain.VmetaModule;
import com.platform.exception.CRUDException;
import com.platform.util.CacheUtil;
import com.platform.util.UUIDGenerator;
import com.platform.util.Validate;
import com.platform.vo.RoleCache;

/**
 * <p>程序名称：       RoleModuleDepartmentsDAO.java</p>
 * <p>程序说明：       角色模块的部门权限</p>
 * <p>版权信息：       Copyright 深圳市维远泰克科技有限公司</p>
 * <p>时间：          Jan 11, 2011 8:44:03 AM</p>	
 * 
 * @author：          Marker.King
 * @version：         Ver 0.1
 */
public class RoleModuleDepartmentsDAO extends GenericDAO{

	private static final long serialVersionUID = -4555796369325957225L;
	private static RoleModuleDepartmentsDAO instance;
	private JdbcTemplate jdbcTemplate;
	
	public static RoleModuleDepartmentsDAO getInstance(JdbcTemplate jdbcTemplate) {
        if(instance == null) {
            instance =  new RoleModuleDepartmentsDAO(jdbcTemplate);
        }
        return instance;
    }
	
	private RoleModuleDepartmentsDAO(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * session用到的部门权限
	 * @param ruList
	 * @return
	 * @throws CRUDException 
	 */
	public HashMap<String, String> findRoleModuleDepartments(List<RoleUsers> ruList, String userId) throws CRUDException {
		//从缓存中取出现有权限
		HashMap<String, String> moduleDepartments = new HashMap<String, String>();
		RoleCache roleCache = (RoleCache) CacheUtil.get(RoleCache.CACHE_NAME, userId);
		if(roleCache != null) {
			moduleDepartments = roleCache.getModuleDepartments();
		}
		if (Validate.collectionNotNull(ruList)) {
            for (RoleUsers ru : ruList) {
                for (Iterator<RoleModuleDepartments> iter = findAll(SQLConstant.ROLEMODULEDEPARTMENT_SELECT_BY_ROLE_SQL, new String[]{ru.getRole().getId()}).iterator(); iter.hasNext();) {
                	RoleModuleDepartments rmd = iter.next();
                    String sessionDepartments = moduleDepartments.get(rmd.getModule().getId());
                    StringBuffer departmentIds = new StringBuffer();
                    if (Validate.notNull(sessionDepartments))
                    	departmentIds.append(sessionDepartments);
                    String[] nextDepartmentsIds = rmd.getDepartments().split(",");
                    for (int i = 0; i < nextDepartmentsIds.length; i++) {
                        if (Validate.notNull(nextDepartmentsIds[i])) {
                            if (departmentIds.indexOf(nextDepartmentsIds[i]) < 0)
                            	departmentIds.append(nextDepartmentsIds[i] + ",");
                        }
                    }
                    moduleDepartments.put(rmd.getModule().getId(), departmentIds.toString());
                }
            }
        }
		return moduleDepartments;
	}

	/**
	 * 查询所有
	 * @return
	 */
	public List<RoleModuleDepartments> findAll(String sql,Object[] args){
		 return jdbcTemplate.query(sql, args, new RowMapper<RoleModuleDepartments>(){
			@Override
			public RoleModuleDepartments mapRow(ResultSet rs, int rowNum) throws SQLException {
				RoleModuleDepartments rd = new RoleModuleDepartments();
				rd.setId(rs.getString("id"));
				Role r = new Role();
				r.setId(rs.getString("roleid"));
				VmetaModule m = new VmetaModule();
				m.setId(rs.getString("moduleid"));
				rd.setRole(r);
				rd.setModule(m);
				rd.setDepartments(rs.getString("departments"));
				return rd;
			}
		});
	}
	
	/**
	 * 保存
	 * @param sql
	 * @param args
	 * @return
	 */
	public int save(RoleModuleDepartments roleModuleDepartments){
		return jdbcTemplate.update(SQLConstant.ROLEMODULEDEPARTMENT_SAVE_SQL,new Object[]{
			UUIDGenerator.generate(),
			roleModuleDepartments.getRole().getId(),
			roleModuleDepartments.getModule().getId(),
			roleModuleDepartments.getDepartments()
		});
	}
	
	/**
	 * 更新
	 * @param sql
	 * @param args
	 * @return
	 */
	public int update(RoleModuleDepartments roleModuleDepartments){
		return jdbcTemplate.update(SQLConstant.ROLEMODULEDEPARTMENT_UPDATE_SQL,new Object[]{
			roleModuleDepartments.getRole().getId(),
			roleModuleDepartments.getModule().getId(),
			roleModuleDepartments.getDepartments(),
			roleModuleDepartments.getId()
		});
	}
	
	/**
	 * 删除集合
	 * @param collection
	 * @return
	 */
	public int deleteAll(Collection<RoleModuleDepartments> collection){
		int deleteCount = 0;
		if(!Validate.collectionNotNull(collection)) return deleteCount;
		
		for(RoleModuleDepartments roleModuleDepartments : collection){
			if(roleModuleDepartments!=null&&roleModuleDepartments.getId()!=null)
			deleteCount += deleteByProperty(SQLConstant.ROLEMODULEDEPARTMENT_DELETE_BY_ID_SQL,roleModuleDepartments.getId());
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
		return jdbcTemplate.update(SQLConstant.ROLEMODULEDEPARTMENT_DELETE_BY_ROLEID, roleId);
	}
}
