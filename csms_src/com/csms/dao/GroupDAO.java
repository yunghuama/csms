package com.csms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.csms.constants.CSMSSQLConstant;
import com.csms.domain.Group;
import com.platform.dao.GenericDAO;
import com.platform.domain.Users;
import com.platform.util.Meta;
import com.platform.util.PageHelper;
import com.platform.util.UUIDGenerator;
import com.platform.vo.Page;

/**
 * <p>程序名称：       GROUPDAO.java</p>
 * <p>程序说明：       组群DAO</p>
 * <p>时间：          2013-1-17 16:53 雨夹雪</p>	
 * 
 * @author：          cheney.mydream
 * @version：         Ver 0.1
 */
public class GroupDAO extends GenericDAO{

	private static final long serialVersionUID = 376038639038947152L;
	private static GroupDAO instance;
	private JdbcTemplate jdbcTemplate;
	
	public static GroupDAO getInstance(JdbcTemplate jdbcTemplate) {
        if(instance == null) {
        	instance =  new GroupDAO(jdbcTemplate);
        }
        return instance;
    }
	
	public GroupDAO(JdbcTemplate jdbcTemplate){
		super(jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 分页获取内容
	 */
	public Page<Group> pagination(final Page<Group> page,String sql,Object[] args){
		Object[] preparedArgs = Meta.addPageToParameters(args, page);
		page.setList(jdbcTemplate.query(sql + CSMSSQLConstant.LIMIT, preparedArgs, new RowMapper<Group>(){
			@Override
			public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
				Group group = new Group();
				group.setId(rs.getString("id"));
				group.setRemark(rs.getString("remark"));
				group.setName(rs.getString("name"));
				group.setRule(rs.getString("rulename"));
				group.setCreateTime(rs.getLong("createtime"));
				Users user = new Users();
				user.setId(rs.getString("creator"));
				user.setRealName(rs.getString("realname"));
				group.setCreator(user);
				return group;
			}}));
		int rowCount = queryForInt(Meta.getRowCountSQL(CSMSSQLConstant.GROUP_ROWCOUNT_SQL , sql),args);
		page.setRowCount(rowCount);
		page.setMaxPage(PageHelper.getMaxPage(rowCount, page.getPageSize()));
		return page;
	}
	
	/**
	 * 根据sql 和 args 查询
	 * 
	 */
	public List<Group> findAll(String sql,Object[] args){
		return jdbcTemplate.query(sql, args, new RowMapper<Group>(){
			@Override
			public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
				Group group = new Group();
				group.setId(rs.getString("id"));
				group.setName(rs.getString("name"));
				group.setRemark(rs.getString("remark"));
				group.setRule(rs.getString("rule"));
				group.setType(rs.getString("type"));
				return group;
			}
		});
	}
	
	/**
	 * 根据sql 查询
	 * 
	 */
	public List<Group> findAll(String sql){
		return findAll(sql,null);
	}
	
	/**
	 * 保存部门数据
	 * @param department
	 * @return
	 */
	public int save(Group group){
     return jdbcTemplate.update(CSMSSQLConstant.GROUP_SAVE_SQL, new Object[]{
			UUIDGenerator.generate(),
			group.getName(),
			group.getRule(),
			group.getDepartment(),
			group.getRemark(),
			group.getType(),
			group.getCreator().getId(),
			new Date().getTime()
		});
	}
	
	/**
	 * 保存部门数据
	 * @param department
	 * @return
	 */
	public int update(Group group){
     return jdbcTemplate.update(CSMSSQLConstant.GROUP_UPDATE_SQL, new Object[]{
			group.getName(),
			group.getRule(),
			group.getRemark(),
			group.getId()
		});
	}
	
	/**
	 * 根据ID 查询部门
	 * @param id
	 */
	public Group find(String sql,Object[] args){
		List<Group> list = findAll(sql,args);
		if(list!=null&&list.size()>0)
			return list.get(0);
		return null;
	}

}