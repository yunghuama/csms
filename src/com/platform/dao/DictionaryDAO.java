package com.platform.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.platform.constants.SQLConstant;
import com.platform.domain.Dictionary;
import com.platform.domain.Users;
import com.platform.exception.CRUDException;
import com.platform.util.Meta;
import com.platform.util.PageHelper;
import com.platform.util.UUIDGenerator;
import com.platform.util.Validate;
import com.platform.vo.Page;

public class DictionaryDAO extends GenericDAO{

	private static final long serialVersionUID = 6105230161780625369L;
	private static DictionaryDAO instance = null;
	private JdbcTemplate jdbcTemplate;
	
	public static DictionaryDAO getInstance(JdbcTemplate jdbcTemplate) {
        if(instance == null) {
            instance =  new DictionaryDAO(jdbcTemplate);
        }
        return instance;
    }
	
	private DictionaryDAO(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 *同步修改所有业务对象的字典字段值
	 * 
	 * @throws Exception
	 */
	public void syncUpdate(String superId, String newDictName, String oldDicName) throws CRUDException {
		List<String> updateList = SQLConstant.DICT_UPDATE_MAP.get(superId);
		if(Validate.collectionNotNull(updateList)) {
			for (String sql : updateList) {
//				executeUpdate(sql, newDictName, oldDicName);
			}
		}
	}

	/**
	 * 查找某字典下最大编号
	 * 
	 * @param superId
	 * @return
	 * @throws CRUDException
	 */
	public Integer findMaxSortNum(String superId) throws CRUDException {
		try {
			return queryForInt(SQLConstant.DICTIONARY_SELECT_NEXT_SORTNUM_SQL, new String[]{superId}) + 1;
		} catch (Exception e) {
			return 1;
		}
	}
	
	/***
	 * 
	 * @return
	 */
	public int save(Dictionary dictionary){
		return jdbcTemplate.update(SQLConstant.DICTIONARY_SAVE_SQL, new Object[]{
			UUIDGenerator.generate(),
			dictionary.getName(),
			dictionary.getSuperId(),
			dictionary.getSortNum(),
			dictionary.getRemark(),
			dictionary.getCreator().getId(),
			dictionary.getCreateTime()
		});
	}
	
	/***
	 * 
	 * @return
	 */
	public int update(Dictionary dictionary){
		return jdbcTemplate.update(SQLConstant.DICTIONARY_UPDATE_SQL, new Object[]{
			dictionary.getName(),
			dictionary.getSuperId(),
			dictionary.getSortNum(),
			dictionary.getRemark(),
			dictionary.getEditor().getId(),
			dictionary.getEditTime(),
			dictionary.getId()
		});
	}
	
	/**
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public Dictionary find(String sql,Object[] args){
		List<Dictionary>  list = findAll(sql, args);
		return Validate.collectionNotNull(list) ? list.get(0) : null;
	}
	
	/**
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public List<Dictionary> findAll(String sql){
		return findAll(sql,null);
	}
	
	
	/**
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public List<Dictionary> findAll(String sql,Object[] args){
		return jdbcTemplate.query(sql, args, new RowMapper<Dictionary>(){
			@Override
			public Dictionary mapRow(ResultSet rs, int rowNum) throws SQLException {
				Dictionary dictionary = new Dictionary();
				dictionary.setId(rs.getString("id"));
				dictionary.setName(rs.getString("name"));
				dictionary.setSuperId(rs.getString("superid"));
				dictionary.setRemark(rs.getString("remark"));
				dictionary.setSortNum(rs.getInt("sortNum"));
				return dictionary;
			}
		});
	}
	
	public Page<Dictionary> pagination(final Page<Dictionary> page,String sql,String orderby,Object[] args){
		Object[] preparedArgs = Meta.addPageToParameters(args, page);
		page.setList(jdbcTemplate.query(sql + orderby + SQLConstant.LIMIT, preparedArgs, new RowMapper<Dictionary>(){
			@Override
			public Dictionary mapRow(ResultSet rs, int rowNum) throws SQLException {
				Dictionary dictionary = new Dictionary();
				dictionary.setId(rs.getString("id"));
				dictionary.setName(rs.getString("name"));
				dictionary.setSuperId(rs.getString("superid"));
				dictionary.setRemark(rs.getString("remark"));
				dictionary.setSortNum(rs.getInt("sortNum"));
				Users users = new Users();
				users.setRealName(rs.getString("realname"));
				users.setId(rs.getString("creator"));
				dictionary.setCreator(users);
				
				return dictionary;
			}}));
		int rowCount = queryForInt(Meta.getRowCountSQL(SQLConstant.DICTIONARY_ROWCOUNT_SQL , sql),args);
		page.setRowCount(rowCount);
		page.setMaxPage(PageHelper.getMaxPage(rowCount, page.getPageSize()));
		return page;
	}
}