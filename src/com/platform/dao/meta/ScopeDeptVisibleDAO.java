package com.platform.dao.meta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.platform.constants.SQLConstant;
import com.platform.constants.StringConstant;
import com.platform.dao.GenericDAO;
import com.platform.domain.Department;
import com.platform.domain.Role;
import com.platform.domain.RoleUsers;
import com.platform.domain.ScopeDataVisible;
import com.platform.domain.ScopeDeptVisible;
import com.platform.domain.VmetaModule;
import com.platform.exception.CRUDException;
import com.platform.util.CacheUtil;
import com.platform.util.UUIDGenerator;
import com.platform.util.Validate;
import com.platform.vo.RoleCache;


public class ScopeDeptVisibleDAO extends GenericDAO{

	
	private static final long serialVersionUID = -9195347518046473325L;
	private static ScopeDeptVisibleDAO instance;
	private JdbcTemplate jdbcTemplate;
	
	public static ScopeDeptVisibleDAO getInstance(JdbcTemplate jdbcTemplate) {
        if(instance == null) {
            instance =  new ScopeDeptVisibleDAO(jdbcTemplate);
        }
        return instance;
    }
	
    /**
     * 构造函数
     * 
     * @param sessionFactory
     */
    private ScopeDeptVisibleDAO(JdbcTemplate jdbcTemplate) {
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
    public HashMap<String, HashMap<String, String>> findDepartmentOperates(List<RoleUsers> ruList, String userId) throws CRUDException {
    	//从缓存中取出现有权限
        HashMap<String, HashMap<String, String>> operates = new HashMap<String, HashMap<String,String>>();
        RoleCache roleCache = (RoleCache) CacheUtil.get(RoleCache.CACHE_NAME, userId);
        if(roleCache != null) {
        	operates = roleCache.getDepartmentOperates();
        }
        if (Validate.collectionNotNull(ruList)) {
            for (RoleUsers ru : ruList) {
                List<ScopeDeptVisible> sdvList = this.findAll(SQLConstant.SCOPEDEPTVISIBLE_SELECT_BY_ROLE_SQL, new String[]{ru.getRole().getId()});
                if (Validate.collectionNotNull(sdvList)) {
                    for (ScopeDeptVisible sdv : sdvList) {
                        HashMap<String, String> departmentOperates = operates.get(sdv.getDepartment().getId());
                        if (departmentOperates == null) {
                            departmentOperates = new HashMap<String, String>();
                            departmentOperates.put(sdv.getWebId(), sdv.getVisible());
                        } else {
                        	String usable = departmentOperates.get(sdv.getWebId());
                            if (Validate.notNull(usable)) {
                                if (StringConstant.FALSE.equals(usable))
                                    departmentOperates.put(sdv.getWebId(), sdv.getVisible());
                            } else {
                                departmentOperates.put(sdv.getWebId(), sdv.getVisible());
                            }
                        }
                        operates.put(sdv.getDepartment().getId(), departmentOperates);
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
	public List<ScopeDeptVisible> findAll(String sql,Object[] args){
		 return jdbcTemplate.query(sql, args, new RowMapper<ScopeDeptVisible>(){
			@Override
			public ScopeDeptVisible mapRow(ResultSet rs, int rowNum) throws SQLException {
				ScopeDeptVisible sdv = new ScopeDeptVisible();
				sdv.setId(rs.getString("id"));
				Role r = new Role();
				r.setId(rs.getString("roleid"));
				VmetaModule m = new VmetaModule();
				m.setId(rs.getString("moduleid"));
				Department d = new Department();
				d.setId(rs.getString("departmentid"));
				sdv.setRole(r);
				sdv.setModule(m);
				sdv.setDepartment(d);
				sdv.setVisible(rs.getString("visible"));
				sdv.setWebId(rs.getString("webid"));
				return sdv;
			}
		});
	}
	
	
	/**
	 * 保存
	 * @param scopeDataVisible
	 * @return
	 */
	public int save(ScopeDeptVisible scopeDataVisible){
	 return	jdbcTemplate.update(SQLConstant.SCOPEDEPTVISIBLE_SAVE_SQL, new Object[]{
				UUIDGenerator.generate(),
				scopeDataVisible.getRole().getId(),
				scopeDataVisible.getModule().getId(),
				scopeDataVisible.getDepartment().getId(),
				scopeDataVisible.getWebId(),
				scopeDataVisible.getVisible()
		});
	}
	
	/**
	 * 更新
	 * @param scopeDataVisible
	 * @return
	 */
	public int update(ScopeDeptVisible scopeDataVisible){
	 return	jdbcTemplate.update(SQLConstant.SCOPEDEPTVISIBLE_UPDATE_SQL, new Object[]{
				scopeDataVisible.getRole().getId(),
				scopeDataVisible.getModule().getId(),
				scopeDataVisible.getDepartment().getId(),
				scopeDataVisible.getWebId(),
				scopeDataVisible.getVisible(),
				scopeDataVisible.getId()
		});
	}
}