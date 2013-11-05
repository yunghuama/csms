package com.csms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.csms.constants.CSMSSQLConstant;
import com.csms.domain.Content;
import com.platform.dao.GenericDAO;
import com.platform.domain.Users;
import com.platform.util.Meta;
import com.platform.util.PageHelper;
import com.platform.util.UUIDGenerator;
import com.platform.vo.Page;

/**
 * <p>程序名称：       ContentDAO.java</p>
 * <p>程序说明：       名片内容DAO</p>
 * <p>时间：          2013-1-13 16:53 雨夹雪</p>	
 * 
 * @author：          cheney.mydream
 * @version：         Ver 0.1
 */
public class ContentDAO extends GenericDAO{

	private static final long serialVersionUID = 376038639038947152L;
	private static ContentDAO instance;
	private JdbcTemplate jdbcTemplate;
	
	public static ContentDAO getInstance(JdbcTemplate jdbcTemplate) {
        if(instance == null) {
        	instance =  new ContentDAO(jdbcTemplate);
        }
        return instance;
    }
	
	public ContentDAO(JdbcTemplate jdbcTemplate){
		super(jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 分页获取内容
	 */
	public Page<Content> pagination(final Page<Content> page,String sql,Object[] args){
		Object[] preparedArgs = Meta.addPageToParameters(args, page);
		page.setList(jdbcTemplate.query(sql + CSMSSQLConstant.LIMIT, preparedArgs, new RowMapper<Content>(){
			@Override
			public Content mapRow(ResultSet rs, int rowNum) throws SQLException {
				Content content = new Content();
				content.setContent(rs.getString("content"));
				content.setId(rs.getString("id"));
				content.setReason(rs.getString("reason"));
				content.setRemark(rs.getString("remark"));
				content.setState(rs.getString("state"));
				content.setCreateTime(rs.getLong("createtime"));
				Users user = new Users();
				user.setId(rs.getString("creator"));
				user.setRealName(rs.getString("realname"));
				content.setCreator(user);
				return content;
			}}));
		int rowCount = queryForInt(Meta.getRowCountSQL(CSMSSQLConstant.CONTENT_ROWCOUNT_SQL , sql),args);
		page.setRowCount(rowCount);
		page.setMaxPage(PageHelper.getMaxPage(rowCount, page.getPageSize()));
		return page;
	}
	
	
	/**
	 * 分页获取内容
	 */
	public Page<Content> paginationA(final Page<Content> page,String sql,Object[] args){
		Object[] preparedArgs = Meta.addPageToParameters(args, page);
		page.setList(jdbcTemplate.query(sql + CSMSSQLConstant.LIMIT, preparedArgs, new RowMapper<Content>(){
			@Override
			public Content mapRow(ResultSet rs, int rowNum) throws SQLException {
				Content content = new Content();
				content.setContent(rs.getString("content"));
				content.setId(rs.getString("id"));
				content.setReason(rs.getString("reason"));
				content.setRemark(rs.getString("remark"));
				content.setDepartment(rs.getString("departmentname"));
				content.setState(rs.getString("state"));
				content.setCreateTime(rs.getLong("createtime"));
				Users user = new Users();
				user.setId(rs.getString("creator"));
				user.setRealName(rs.getString("realname"));
				content.setCreator(user);
				return content;
			}}));
		int rowCount = queryForInt(Meta.getRowCountSQL(CSMSSQLConstant.CONTENT_ROWCOUNT_SQL , sql),args);
		page.setRowCount(rowCount);
		page.setMaxPage(PageHelper.getMaxPage(rowCount, page.getPageSize()));
		return page;
	}
	
	/**
	 * 根据sql 和 args 查询
	 * 
	 */
	public List<Content> findAll(String sql,Object[] args){
		return jdbcTemplate.query(sql, args, new RowMapper<Content>(){
			@Override
			public Content mapRow(ResultSet rs, int rowNum) throws SQLException {
				Content content = new Content();
				content.setId(rs.getString("id"));
				content.setContent(rs.getString("content"));
				return content;
			}
		});
	}
	
	/**
	 * 根据sql 查询
	 * 
	 */
	public List<Content> findAll(String sql){
		return findAll(sql,null);
	}
	
	/**
	 * 保存部门数据
	 * @param department
	 * @return
	 */
	public int save(Content content){
     return jdbcTemplate.update(CSMSSQLConstant.CONTENT_SAVE_SQL, new Object[]{
			UUIDGenerator.generate(),
			content.getContent(),
			content.getDepartment(),
			content.getRemark(),
			content.getReason(),
			content.getState(),
			content.getCreator().getId(),
			new Date().getTime()
		});
	}
	
	
	public int update(Content content){
	     return jdbcTemplate.update(CSMSSQLConstant.CONTENT_UPDATE_BY_ID_SQL, new Object[]{
				content.getReason(),
				content.getState(),
				content.getId()
			});
		}
	
	/**
	 * 根据ID 查询部门
	 * @param id
	 */
	public Content find(String sql,Object[] args){
		List<Content> list = findAll(sql,args);
		if(list!=null&&list.size()>0)
			return list.get(0);
		return null;
	}

}