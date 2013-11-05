package com.platform.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.platform.util.Meta;

public class GenericDAO {

	private JdbcTemplate jdbcTemplate;
	
	public GenericDAO(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 根据指定的sql和参数进行条数查询
	 * @return
	 */
	public int queryForInt(String sql,Object[] args){
		return jdbcTemplate.queryForInt(sql, args);
	}
	
	/**
	 * 根据sql 查询
	 * @param sql
	 * @return
	 */
	public int queryForInt(String sql){
		return queryForInt(sql,null);
	}
	
	/**
	 * 根据ID删除
	 */
	public int deleteByProperty(String sql,String id){
		return jdbcTemplate.update(sql, id);
	}
	
	/**
	 * 根据ID集合删除
	 */
	public int deleteByIdList(String sql,List<String> ids){
		return jdbcTemplate.update(sql+Meta.idsToSQLSubString(ids));
	}
	
	
	/**
	 * 根据sql和args 查询id
	 */
	public List<String> findIds(String sql,Object[] args){
		return jdbcTemplate.query(sql, args, new RowMapper<String>(){
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("id");
			}
		});
	}
}
