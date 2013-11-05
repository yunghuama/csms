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
 * <p>程序名称：       VmetaFieldDAO.java</p>
 * <p>程序说明：       字段DAO</p>
 * <p>版权信息：       Copyright 深圳市维远泰克科技有限公司</p>
 * <p>时间：          Jan 20, 2011 3:10:48 PM</p>	
 * 
 * @author：          Marker.King
 * @version：         Ver 0.1
 */
public class FieldDAO extends GenericDAO{

	private static final long serialVersionUID = -9210267659175090467L;
	private static FieldDAO instance;
	private static JdbcTemplate jdbcTemplate;
	
	public static FieldDAO getInstance(JdbcTemplate jdbcTemplate) {
        if(instance == null) {
            instance =  new FieldDAO(jdbcTemplate);
        }
        return instance;
    }
	
	private FieldDAO(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}

	
	public List<VmetaField> findAll(String sql,Object[] args){
		return jdbcTemplate.query(sql, args, new RowMapper<VmetaField>(){
			@Override
			public VmetaField mapRow(ResultSet rs, int rowNum) throws SQLException {
				VmetaField field = new VmetaField();
				
				return field;
			}
		});
	}
	
	public VmetaField find(String sql,Object[] args){
		List<VmetaField> list = findAll(sql,args);
		return Validate.collectionNotNull(list)? list.get(0) : null;
	}
}
