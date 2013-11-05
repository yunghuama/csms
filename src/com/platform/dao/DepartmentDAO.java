package com.platform.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.platform.constants.SQLConstant;
import com.platform.domain.Department;
import com.platform.domain.Users;
import com.platform.exception.CRUDException;
import com.platform.util.CodeHelper;
import com.platform.util.ListHelper;
import com.platform.util.Meta;
import com.platform.util.PageHelper;
import com.platform.util.UUIDGenerator;
import com.platform.util.Validate;
import com.platform.vo.Page;

/**
 * <p>程序名称：       DepartmentDAO.java</p>
 * <p>程序说明：       部门DAO</p>
 * <p>时间：          2012-12-14 16:53 雨夹雪</p>	
 * 
 * @author：          cheney.mydream
 * @version：         Ver 0.1
 */
public class DepartmentDAO extends GenericDAO{

	private static final long serialVersionUID = 376038639038947152L;
	private static DepartmentDAO instance;
	private JdbcTemplate jdbcTemplate;
	
	public static DepartmentDAO getInstance(JdbcTemplate jdbcTemplate) {
        if(instance == null) {
        	instance =  new DepartmentDAO(jdbcTemplate);
        }
        return instance;
    }
	
	public DepartmentDAO(JdbcTemplate jdbcTemplate){
		super(jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 查找下一个编号
	 * @param superId
	 * @return
	 * @throws CRUDException
	 */
	public String findNextCode(String superId) throws CRUDException {
		Page<Department> page = new Page<Department>();
		page.setCurrPage(1);
		page.setPageSize(1);
		page = pagination(page, SQLConstant.DEPARTMENT_CHILD_SQL, new String[]{superId});

		String nextCode = "";
		if(page.getList().size() > 0) {
			nextCode = CodeHelper.getNextCode(page.getList().get(0).getCode(), CodeHelper.DEPARTMENT_CODE_LENGTH);
		} else {
			Department d = find(SQLConstant.DEPARTMENT_SELECTALL_BY_ID,new String[]{superId});
			if(d == null) {
				nextCode = CodeHelper.getFirstCode(null, CodeHelper.DEPARTMENT_CODE_LENGTH);
			} else {
				nextCode = CodeHelper.getFirstCode(d.getCode(), CodeHelper.DEPARTMENT_CODE_LENGTH);
			}
		}
		
		return nextCode;
	}
	
	/**
	 * 分页获取部门数据
	 */
	public Page<Department> pagination(final Page<Department> page,String sql,Object[] args){
		Object[] preparedArgs = Meta.addPageToParameters(args, page);
		page.setList(jdbcTemplate.query(sql + SQLConstant.LIMIT, preparedArgs, new RowMapper<Department>(){
			@Override
			public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
				Department dep = new Department();
				dep.setId(rs.getString("id"));
				dep.setCode(rs.getString("code"));
				dep.setName(rs.getString("name"));
				dep.setSuperId(rs.getString("superid"));
				dep.setRemark(rs.getString("remark"));
				Users u = new Users();
				u.setId(rs.getString("creator"));
				u.setRealName(rs.getString("realname"));
				Department d = new Department();
				d.setId(rs.getString("creatordepartment"));
				u.setDepartment(d);
				dep.setCreator(u);
				return dep;
			}}));
		int rowCount = queryForInt(Meta.getRowCountSQL(SQLConstant.DEPARTMENT_ROWCOUNT_SQL , sql),args);
		page.setRowCount(rowCount);
		page.setMaxPage(PageHelper.getMaxPage(rowCount, page.getPageSize()));
		return page;
	}
	
	/**
	 * 根据sql 和 args 查询
	 * 
	 */
	public List<Department> findAll(String sql,Object[] args){
		return jdbcTemplate.query(sql, args, new RowMapper<Department>(){
			@Override
			public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
				Department dep = new Department();
				dep.setId(rs.getString("id"));
				dep.setCode(rs.getString("code"));
				dep.setName(rs.getString("name"));
				dep.setSuperId(rs.getString("superid"));
				dep.setRemark(rs.getString("remark"));
				return dep;
			}
		});
	}
	
	/**
	 * 根据sql 和 args 查询
	 * 
	 */
	public List<Department> findAllDepAndUsers(String sql,Object[] args){
		return jdbcTemplate.query(sql, args, new RowMapper<Department>(){
			@Override
			public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
				Department dep = new Department();
				dep.setId(rs.getString("id"));
				dep.setCode(rs.getString("code"));
				dep.setName(rs.getString("name"));
				dep.setSuperId(rs.getString("superid"));
				dep.setRemark(rs.getString("remark"));
				dep.setUserses(findAllUsersByDep(dep.getId()));
				return dep;
			}
		});
	}
	public List<Users> findAllUsersByDep(String depId){
		return jdbcTemplate.query(SQLConstant.USERS_ALL_AND_DEP_SELECT_SQL, new String[]{depId}, new RowMapper<Users>(){
			@Override
			public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
				Users users = new Users();
				users.setAccountName(rs.getString("accountName"));
				users.setRealName(rs.getString("realname"));
				users.setState(rs.getString("state"));
				users.setId(rs.getString("id"));
				users.setSex(rs.getString("sex"));
				return users;
			}
			
		});
	}
	
	/**
	 * 根据sql 查询
	 * 
	 */
	public List<Department> findAll(String sql){
		return findAll(sql,null);
	}
	
	/**
	 * 保存部门数据
	 * @param department
	 * @return
	 */
	public int save(Department department){
     return jdbcTemplate.update(SQLConstant.DEPARTMENT_SAVE_SQL, new Object[]{
    		department.getId(),
			department.getName(),
			department.getCode(),
			department.getSuperId(),
			department.getIsStore(),
			department.getRemark(),
			department.getCreator().getId(),
			department.getCreateTime()
		});
	}
	
	/**
	 * 更新部门
	 */
	public int update(Department department){
	     return jdbcTemplate.update(SQLConstant.DEPARTMENT_UPDATE_SQL, new Object[]{
				department.getName(),
				department.getCode(),
				department.getSuperId(),
				department.getIsStore(),
				department.getRemark(),
				department.getEditor().getId(),
				department.getEditTime(),
				department.getId()
			});
		}
	
	/**
	 * 根据ID 查询部门
	 * @param id
	 */
	public Department find(String sql,Object[] args){
		List<Department> list = findAll(sql,args);
		if(list!=null&&list.size()>0)
			return list.get(0);
		return null;
	}
	
	/**
	 * 查询不能删除的部门
	 * 
	 */
	public List<List<String>> findCannotDeleteList(List<String> idList, List<String> sqlList) throws CRUDException{
		// 声明Set集合
		Set<String> cannotDeleteData = new HashSet<String>();
		// 循环SQL常量类中删除用到的SQL集合
		for (String sql : sqlList) {
			// 以ID为参数循环查询数据
			for (String id : idList) {
				// 如果查找到数据,则将这个ID放入不能被删除的集合中
				if (queryForInt(sql, new String[]{id})>0)
					cannotDeleteData.add(id);
			}
		}
		// 返回不能被删除的List集合
		return ListHelper.getDifferent(idList, new ArrayList<String>(cannotDeleteData));
	}
	
	
	/**
	 * 获得部门权限的where语句
	 * @param idList
	 * @return
	 * @throws CRUDException
	 */
	public String findDepartmentWhere(List<String> idList) throws CRUDException {
		StringBuffer sb = new StringBuffer();
		for (Iterator<String> iterator = idList.iterator(); iterator.hasNext();) {
			String id = iterator.next();
			sb.append(" or code like '");
			sb.append(find(SQLConstant.DEPARTMENT_SELECTALL_BY_ID,new String[]{id}).getCode());
			sb.append("%'");
		}
		return sb.toString();
	}
	
	/**
     * 根据部门id获得该部门下（包括该部门）所有部门id列表
     * @param idList
     * @return
     * @throws CRUDException
     */
    public List<String> findChildDepartIds(String id) throws CRUDException {
        Department dpt = find(SQLConstant.DEPARTMENT_SELECTALL_BY_ID,new String[]{id});
        return findIds(SQLConstant.DEPARTMENT_ALL_LIKE_SQL,new String[]{dpt.getCode()});
    }

}