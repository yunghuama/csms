package com.platform.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.csms.domain.Enterprise;
import com.platform.constants.SQLConstant;
import com.platform.constants.StringConstant;
import com.platform.domain.Department;
import com.platform.domain.Users;
import com.platform.util.Meta;
import com.platform.util.PageHelper;
import com.platform.util.UUIDGenerator;
import com.platform.util.Validate;
import com.platform.vo.Page;

/**
 * <p>程序名称：       UsersDAO.java</p>
 * <p>程序说明：       用户DAO</p>
 * <p>时间：          2012-12-12 16:36 </p>	
 * 
 * @author：          cheney.mydream
 * @version：         Ver 0.2
 */
public class UsersDAO extends GenericDAO{

	private static final long serialVersionUID = -2510323844187127893L;
	private static UsersDAO instance;
	private JdbcTemplate jdbcTemplate;

	public static UsersDAO getInstance(JdbcTemplate jdbcTemplate) {
        if(instance == null) {
        	instance =  new UsersDAO(jdbcTemplate);
        }
        return instance;
    }
	
	public UsersDAO(JdbcTemplate jdbcTemplate){
		super(jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 
	 * @param users
	 * @return
	 */
	public int save(Users users){
		return jdbcTemplate.update(SQLConstant.USERS_SAVE_SQL, new Object[]{
			users.getId(),
			users.getAccountName(),
			users.getRealName(),
			users.getPassword(),
			users.getSex(),
			users.getBirthday(),
			users.getEdu(),
			users.getState(),
			users.getDepartment().getId(),
			users.getCreator().getId(),
			users.getCreateTime(),
			users.getBigImage(),
			users.getNormalImage(),
			users.getSmallImage(),
			users.getArea(),
			users.getCellNo(),
			users.getEnterprise().getId()
		});
	}
	
	/**
	 * 更新
	 * @param users
	 * @return
	 */
	public int update(Users users){
		return jdbcTemplate.update(SQLConstant.USERS_UPDATE_SQL, new Object[]{
				users.getAccountName(),
				users.getRealName(),
				users.getPassword(),
				users.getSex(),
				users.getBirthday(),
				users.getEdu(),
				users.getState(),
				users.getDepartment().getId(),
				users.getEditor().getId(),
				users.getEditTime(),
				users.getBigImage(),
				users.getNormalImage(),
				users.getSmallImage(),
				users.getArea(),
				users.getCellNo(),
				users.getEnterprise().getId(),
				users.getId()
			});
	}
	
	/**
	 * 标记删除
	 */
	public int updateDelete(String id){
		return jdbcTemplate.update(SQLConstant.USERS_UPDATE_TO_DELETE, new Object[]{
			StringConstant.FALSE,
			id
		});
	}
	
	/**
	 * 查询用户信息
	 * @param sql 要查询的sql
	 * @param args sql的匹配条件
	 * @return
	 */
	public Users find(String sql,Object[] args){
		List<Users> list  = findAll(sql,args);
		if(Validate.collectionNotNull(list)){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据sql查询用户
	 * @param sql
	 * @return
	 */
	public Users find(String sql){
		return find(sql,null);
	}
	
	/**
	 * 根据sql和查询条件查询所有
	 * @param sql
	 * @param args
	 * @return
	 */
	public List<Users> findAll(String sql , Object[] args){
		return jdbcTemplate.query(sql, args, new RowMapper<Users>(){
			@Override
			public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
				Users users = new Users();
				users.setAccountName(rs.getString("accountName"));
				users.setArea(rs.getString("area"));
				users.setBigImage(rs.getString("bigimage"));
				users.setCellNo(rs.getString("cellno"));
				users.setEdu(rs.getString("edu"));
				users.setPassword(rs.getString("password"));
				users.setRealName(rs.getString("realname"));
				users.setState(rs.getString("state"));
				users.setId(rs.getString("id"));
				users.setSex(rs.getString("sex"));
				Department d = new Department();
				d.setId(rs.getString("departmentid"));
				users.setDepartment(d);
				Enterprise enterprise = new Enterprise();
				enterprise.setId(rs.getString("enterprise"));
				users.setEnterprise(enterprise);
				return users;
			}
		});
	}
	
	public List<Users> findAll(String sql){
		return findAll(sql,null);
	}
	
	/**
	 * 分页查询
	 * @param page
	 * @param sql
	 * @param args
	 * @return
	 */
	public Page<Users> pagination(final Page<Users> page,String sql,Object[] args){
		Object[] preparedArgs = Meta.addPageToParameters(args, page);
		page.setList(jdbcTemplate.query(sql + SQLConstant.LIMIT, preparedArgs, new RowMapper<Users>(){
			@Override
			public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
				Users user = new Users();
				user.setId(rs.getString("id"));
				user.setAccountName(rs.getString("accountname"));
				user.setRealName(rs.getString("realName"));
				user.setArea(rs.getString("area"));
				user.setBigImage(rs.getString("bigimage"));
				user.setCellNo(rs.getString("cellno"));
				user.setEdu(rs.getString("edu"));
				user.setSex(rs.getString("sex"));
				user.setState(rs.getString("state"));
				Users u = new Users();
				u.setId(rs.getString("creator"));
				u.setRealName(rs.getString("creatorName"));
				Department d = new Department();
				d.setId(rs.getString("creatorDepartment"));
				u.setDepartment(d);
				Department d2 = new Department();
				d2.setId(rs.getString("departmentid"));
				d2.setName(rs.getString("departmentName"));
				user.setDepartment(d2);
				user.setCreator(u);
				return user;
			}}));
		int rowCount = queryForInt(Meta.getRowCountSQL(SQLConstant.USERS_ALL_ROWCOUNT_SQL , sql),args);
		page.setRowCount(rowCount);
		page.setMaxPage(PageHelper.getMaxPage(rowCount, page.getPageSize()));
		return page;
	}
}
