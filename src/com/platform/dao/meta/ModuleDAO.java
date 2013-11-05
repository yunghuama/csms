package com.platform.dao.meta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.platform.dao.GenericDAO;
import com.platform.domain.VmetaField;
import com.platform.domain.VmetaModule;
import com.platform.util.Validate;

/**
 * <p>程序名称：       VmetaModule.java</p>
 * <p>程序说明：       模块DAO</p>
 * <p>时间：          2012-12-15 11:17</p>	
 * 
 * @author：          cheney.mydream
 * @version：         Ver 0.1
 */
public class ModuleDAO extends GenericDAO {

	private static final long serialVersionUID = -3733151107843802188L;
	private static ModuleDAO instance;
	private static JdbcTemplate jdbcTemplate;
	
	public static ModuleDAO getInstance(JdbcTemplate jdbcTemplate) {
        if(instance == null) {
            instance =  new ModuleDAO(jdbcTemplate);
        }
        return instance;
    }
	
	private ModuleDAO(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public VmetaModule find(String sql,Object[] args){
		List<VmetaModule>  list = findAll(sql, args);
		return Validate.collectionNotNull(list) ? list.get(0) : null;
	}
	
	/**
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public List<VmetaModule> findAll(String sql){
		return findAll(sql,null);
	}
	
	
	/**
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public List<VmetaModule> findAll(String sql,Object[] args){
		return jdbcTemplate.query(sql, args, new RowMapper<VmetaModule>(){
			@Override
			public VmetaModule mapRow(ResultSet rs, int rowNum) throws SQLException {
				VmetaModule module = new VmetaModule();
				module.setId(rs.getString("id"));
				module.setName(rs.getString("name"));
				module.setSuperId(rs.getString("superid"));
				module.setHasDepartment(rs.getString("hasDepartment"));
				module.setHasField(rs.getString("hasfield"));
				module.setHasUsers(rs.getString("hasusers"));
				return module;
			}
		});
	}
}
