package com.csms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.csms.constants.CSMSSQLConstant;
import com.csms.domain.Content;
import com.csms.domain.Enterprise;
import com.platform.dao.GenericDAO;
import com.platform.util.Meta;
import com.platform.util.PageHelper;
import com.platform.util.UUIDGenerator;
import com.platform.vo.Page;

/**
 * <p>程序名称：       ContentDAO.java</p>
 * <p>程序说明：       集团管理DAO</p>
 * <p>时间：          2013-1-13 16:53 雨夹雪</p>	
 * 
 * @author：          cheney.mydream
 * @version：         Ver 0.1
 */
public class EnterpriseDAO extends GenericDAO{

	private static final long serialVersionUID = 376038639038947152L;
	private static EnterpriseDAO instance;
	private JdbcTemplate jdbcTemplate;
	
	public static EnterpriseDAO getInstance(JdbcTemplate jdbcTemplate) {
        if(instance == null) {
        	instance =  new EnterpriseDAO(jdbcTemplate);
        }
        return instance;
    }
	
	public EnterpriseDAO(JdbcTemplate jdbcTemplate){
		super(jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 分页获取内容
	 */
	public Page<Enterprise> pagination(final Page<Enterprise> page,String sql,Object[] args){
		Object[] preparedArgs = Meta.addPageToParameters(args, page);
		page.setList(jdbcTemplate.query(sql + CSMSSQLConstant.LIMIT, preparedArgs, new RowMapper<Enterprise>(){
			@Override
			public Enterprise mapRow(ResultSet rs, int rowNum) throws SQLException {
				Enterprise enterprise = new Enterprise();
				enterprise.setId(rs.getString("id"));
				enterprise.setCode(rs.getString("code"));
				enterprise.setName(rs.getString("name"));
				enterprise.setNumber(rs.getString("number"));
				return enterprise;
			}}));
		int rowCount = queryForInt(Meta.getRowCountSQL(CSMSSQLConstant.ENTERPRISE_ROWCOUNT_SQL , sql),args);
		page.setRowCount(rowCount);
		page.setMaxPage(PageHelper.getMaxPage(rowCount, page.getPageSize()));
		return page;
	}
	
	
	
	/**
	 * 根据sql 和 args 查询
	 * 
	 */
	public List<Enterprise> findAll(String sql,Object[] args){
		return jdbcTemplate.query(sql, args, new RowMapper<Enterprise>(){
			@Override
			public Enterprise mapRow(ResultSet rs, int rowNum) throws SQLException {
				Enterprise enterprise = new Enterprise();
				enterprise.setId(rs.getString("id"));
				enterprise.setCode(rs.getString("code"));
				enterprise.setName(rs.getString("name"));
				enterprise.setNumber(rs.getString("number"));
				return enterprise;
			}
		});
	}
	
	/**
	 * 根据sql 查询
	 * 
	 */
	public List<Enterprise> findAll(String sql){
		return findAll(sql,null);
	}
	
	/**
	 * 保存部门数据
	 * @param department
	 * @return
	 */
	public int save(Enterprise enterprise){
     return jdbcTemplate.update(CSMSSQLConstant.ENTERPRISE_SAVE_SQL, new Object[]{
			UUIDGenerator.generate(),
			enterprise.getName(),
			enterprise.getDistrict().getId(),
			enterprise.getNumber(),
			enterprise.getCode(),
			new Date()
		});
	}
	
	
	public int update(Enterprise enterprise){
	     return jdbcTemplate.update(CSMSSQLConstant.ENTERPRISE_UPDATE_SQL, new Object[]{
	    		enterprise.getName(),
	 			enterprise.getDistrict().getId(),
	 			enterprise.getNumber(),
	 			enterprise.getCode(),
	 			enterprise.getId()
			});
		}
	
	/**
	 * 根据ID 查询部门
	 * @param id
	 */
	public Enterprise find(String sql,Object[] args){
		List<Enterprise> list = findAll(sql,args);
		if(list!=null&&list.size()>0)
			return list.get(0);
		return null;
	}

}