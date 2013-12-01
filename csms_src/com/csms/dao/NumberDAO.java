package com.csms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.csms.constants.CSMSSQLConstant;
import com.csms.domain.CsmsNumber;
import com.platform.dao.GenericDAO;
import com.platform.domain.Users;
import com.platform.util.Meta;
import com.platform.util.PageHelper;
import com.platform.util.UUIDGenerator;
import com.platform.vo.Page;

/**
 * <p>程序名称：       NumberDAO.java</p>
 * <p>程序说明：       号码DAO</p>
 * <p>时间：          2013-1-18 16:53 雨夹雪</p>	
 * 
 * @author：          cheney.mydream
 * @version：         Ver 0.1
 */
public class NumberDAO extends GenericDAO{

	private static final long serialVersionUID = 376038639038947152L;
	private static NumberDAO instance;
	private JdbcTemplate jdbcTemplate;
	
	public static NumberDAO getInstance(JdbcTemplate jdbcTemplate) {
        if(instance == null) {
        	instance =  new NumberDAO(jdbcTemplate);
        }
        return instance;
    }
	
	public NumberDAO(JdbcTemplate jdbcTemplate){
		super(jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 分页获取内容
	 */
	public Page<CsmsNumber> pagination(final Page<CsmsNumber> page,String sql,Object[] args){
		Object[] preparedArgs = Meta.addPageToParameters(args, page);
		page.setList(jdbcTemplate.query(sql + CSMSSQLConstant.LIMIT, preparedArgs, new RowMapper<CsmsNumber>(){
			@Override
			public CsmsNumber mapRow(ResultSet rs, int rowNum) throws SQLException {
				CsmsNumber number = new CsmsNumber();
				number.setId(rs.getString("id"));
				number.setNumber(rs.getString("number"));
				number.setRemark(rs.getString("remark"));
				number.setGroup(rs.getString("groupname"));
				number.setCreateTime(rs.getLong("createtime"));
				number.setName(rs.getString("name"));
				return number;
			}}));
		int rowCount = queryForInt(Meta.getRowCountSQL(CSMSSQLConstant.NUMBER_ROWCOUNT_SQL , sql),args);
		page.setRowCount(rowCount);
		page.setMaxPage(PageHelper.getMaxPage(rowCount, page.getPageSize()));
		return page;
	}
	
	
	/**
	 * 根据sql 和 args 查询
	 * 
	 */
	public List<CsmsNumber> findAll(String sql,Object[] args){
		return jdbcTemplate.query(sql, args, new RowMapper<CsmsNumber>(){
			@Override
			public CsmsNumber mapRow(ResultSet rs, int rowNum) throws SQLException {
				CsmsNumber number = new CsmsNumber();
				number.setId(rs.getString("id"));
				number.setName(rs.getString("name"));
				number.setNumber(rs.getString("number"));
				number.setRemark(rs.getString("remark"));
				number.setGroup(rs.getString("smsgroup"));
				return number;
			}
		});
	}
	
	/**
	 * 根据sql 查询
	 * 
	 */
	public List<CsmsNumber> findAll(String sql){
		return findAll(sql,null);
	}
	
	/**
	 * 保存部门数据
	 * @param department
	 * @return
	 */
	public int save(CsmsNumber number){
     return jdbcTemplate.update(CSMSSQLConstant.NUMBER_SAVE_BY_GROUP_SQL, new Object[]{
			UUIDGenerator.generate(),
			number.getNumber(),
			number.getGroup(),
			number.getDepartment(),
			number.getRemark(),
			number.getName(),
			number.getCreator().getId(),
			new Date().getTime()
		});
	}
	
	
	public int update(CsmsNumber number){
	     return jdbcTemplate.update(CSMSSQLConstant.NUMBER_UPDATE_ALL_SQL, new Object[]{
				number.getNumber(),
				number.getGroup(),
				number.getRemark(),
				number.getName(),
				number.getId()
			});
		}
	
	public int update(String number,String group,String name,String remark,String department){
	     return jdbcTemplate.update(CSMSSQLConstant.NUMBER_UPDATE_GROUP_NAME_REMARK, new Object[]{
	    		 group,
				 name,
				 remark,
				 number,
				 department
			});
		}
	
	public int updateDefaultGroup(String defaultGroup,String group){
	     return jdbcTemplate.update(CSMSSQLConstant.NUMBER_UPDATE_TODEFAULT_GROUP, new Object[]{
	    		 defaultGroup,
	    		 group
			});
		}
	
	public int updateA(CsmsNumber number){
	     return jdbcTemplate.update(CSMSSQLConstant.NUMBER_UPDATE_GROUP_SQL, new Object[]{
				number.getGroup(),
				number.getId()
			});
		}

		
	
	/**
	 * 根据ID 查询部门
	 * @param id
	 */
	public CsmsNumber find(String sql,Object[] args){
		List<CsmsNumber> list = findAll(sql,args);
		if(list!=null&&list.size()>0)
			return list.get(0);
		return null;
	}

	
	public int findCountByNumber(String number){
		return jdbcTemplate.queryForInt(CSMSSQLConstant.NUMBER_SELECT_COUNT_SQL, new String[]{number});
	}
}