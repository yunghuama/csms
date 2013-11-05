package com.platform.dao.meta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.platform.dao.GenericDAO;
import com.platform.domain.VmetaOperate;
import com.platform.util.Validate;

/**
 * <p>程序名称：       VmetaOperateDAO.java</p>
 * <p>程序说明：       操作DAO</p>
 * <p>时间：          2012-12-15 11:19</p>	
 * 
 * @author：          cheney.mydream
 * @version：         Ver 0.1
 */
public class OperateDAO extends GenericDAO {

	private static final long serialVersionUID = 5098038639512180724L;
	private static OperateDAO instance;
	private static JdbcTemplate jdbcTemplate;
	
	public static OperateDAO getInstance(JdbcTemplate jdbcTemplate) {
        if(instance == null) {
            instance =  new OperateDAO(jdbcTemplate);
        }
        return instance;
    }
	
	private OperateDAO(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	/**
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public VmetaOperate find(String sql,Object[] args){
		List<VmetaOperate> list = findAll(sql,args);
		return Validate.collectionNotNull(list) ? list.get(0) : null;
	}
	
	
	/**
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public List<VmetaOperate> findAll(String sql,Object[] args){
		return jdbcTemplate.query(sql, args, new RowMapper<VmetaOperate>(){
			@Override
			public VmetaOperate mapRow(ResultSet rs, int rowNum) throws SQLException {
				VmetaOperate operate = new VmetaOperate();
				operate.setId(rs.getString("id"));
				operate.setName(rs.getString("name"));
				operate.setWebId(rs.getString("webid"));
				operate.setScopeDataVisible(rs.getString("scopeDataVisible"));
				return operate;
			}
		});
	}

}
