package com.platform.dao.meta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.platform.constants.SQLConstant;
import com.platform.dao.GenericDAO;
import com.platform.domain.Department;
import com.platform.domain.Role;
import com.platform.domain.Users;
import com.platform.util.Meta;
import com.platform.util.PageHelper;
import com.platform.util.UUIDGenerator;
import com.platform.util.Validate;
import com.platform.vo.Page;

/**
 * <p>程序名称：       RoleDAO.java</p>
 * <p>程序说明：       角色DAO</p>
 * <p>时间：        	 2012-12-15 11:05</p>	
 * 
 * @author：          cheney.mydream
 * @version：         Ver 0.1
 */
public class RoleDAO extends GenericDAO {
	
	private static final long serialVersionUID = -641799930567495444L;
	private static RoleDAO instance;
	private JdbcTemplate jdbcTemplate;
	
	public static RoleDAO getInstance(JdbcTemplate jdbcTemplate) {
        if(instance == null) {
            instance =  new RoleDAO(jdbcTemplate);
        }
        return instance;
    }

	private RoleDAO(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public Role find(String sql,Object[] args){
		List<Role> list = findAll(sql,args);
		return Validate.collectionNotNull(list) ? list.get(0) : null;
	}
	
	
	/**
	 * 分页获取角色
	 */
	public Page<Role> pagination(final Page<Role> page,String sql,Object[] args){
		Object[] preparedArgs = Meta.addPageToParameters(args, page);
		page.setList(jdbcTemplate.query(sql + SQLConstant.LIMIT, preparedArgs, new RowMapper<Role>(){
			@Override
			public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
				Role role = new Role();
				role.setId(rs.getString("id"));
				role.setName(rs.getString("name"));
				role.setRemark(rs.getString("remark"));
				Users u = new Users();
				u.setId(rs.getString("creator"));
				u.setRealName(rs.getString("realname"));
				Department d = new Department();
				d.setId(rs.getString("departmentid"));
				role.setCreator(u);
				return role;
			}}));
		int rowCount = queryForInt(Meta.getRowCountSQL(SQLConstant.ROLE_ROWCOUNT_SQL , sql),args);
		page.setRowCount(rowCount);
		page.setMaxPage(PageHelper.getMaxPage(rowCount, page.getPageSize()));
		return page;
	}
	
	
	/**
	 * 根据sql 和 args 查询
	 * 
	 */
	public List<Role> findAll(String sql,Object[] args){
		return jdbcTemplate.query(sql, args, new RowMapper<Role>(){
			@Override
			public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
				Role role = new Role();
				role.setId(rs.getString("id"));
				role.setName(rs.getString("name"));
				role.setRemark(rs.getString("remark"));
				role.setSuperId(rs.getString("superid"));
				return role;
			}
		});
	}
	
	public List<Role> findAll(String sql){
		return findAll(sql,null);
	}
	
	/**
	 * 保存
	 * @param role
	 * @return
	 */
	public int save(Role role){
		return jdbcTemplate.update(SQLConstant.ROLE_SAVE_SQL, new Object[]{
			UUIDGenerator.generate(),
			role.getName(),
			role.getSuperId(),
			role.getRemark(),
			role.getCreateTime(),
			role.getCreator().getId()
		});
	}
	
	/**
	 * 更新
	 * @param role
	 * @return
	 */
	public int update(Role role){
		return jdbcTemplate.update(SQLConstant.ROLE_UPDATE_SQL, new Object[]{
				role.getName(),
				role.getSuperId(),
				role.getRemark(),
				role.getEditTime(),
				role.getEditor().getId(),
				role.getId()
		});
	}
}
